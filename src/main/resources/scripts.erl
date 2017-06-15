-module(scripts).
-export([main/0, accept/1, loop/1, run/6, fila/3, produtor/4, consumidor/2]).
-import(lists, [map/2, filter/2, delete/2]).
-import(string, [tokens/2, len/1, substr/3]).

-define(MAX_LINE_SIZE, 512).
-define(TCP_OPTIONS, [list, {packet, line}, {active, false}, {reuseaddr, true}, {recbuf, ?MAX_LINE_SIZE}]).
-define(COMMAND_INIT, "init\n").

main() ->

    {ok, ListenSocket} = gen_tcp:listen(3000, ?TCP_OPTIONS),
    accept(ListenSocket).


accept(ListenSocket) ->
    {ok, Socket} = gen_tcp:accept(ListenSocket),
    spawn(fun() -> loop(Socket) end),
    accept(ListenSocket).

% Loop de controle do socket
loop(Socket) ->
    case gen_tcp:recv(Socket, 0) of
        {ok, Data} ->
            Tokens = tokens(substr(Data, 1, len(Data) - 1), "-"),
            if
                length(Tokens) > 1 ->
                    io:format("Iniando processos!~n"),
                    [_, TamanhoFila, QuantidadeConsumidores, QuantidadeProdutores, TempoProducao, TempoTabalho] = Tokens,
                    spawn(scripts, run, [Socket, list_to_integer(TamanhoFila), list_to_integer(QuantidadeConsumidores), list_to_integer(QuantidadeProdutores), list_to_integer(TempoProducao), list_to_integer(TempoTabalho)]);
               true ->
                   true
            end,
            loop(Socket);
        {error, closed} ->
            ok
    end.

% Inicia processos
run(Socket, TamanhoFila, QuantidadeConsumidores, QuantidadeProdutores, TempoProducao, TempoTabalho) ->
    Fifo = [],
    PidFila = spawn(scripts, fila, [Fifo, 1, TamanhoFila]),
    runProdutor(PidFila, Socket, QuantidadeProdutores, TempoProducao, TempoTabalho),
    runConsumidor(PidFila, Socket, QuantidadeConsumidores).

% Inicia processos de produção
runProdutor(PidFila, Socket, QuantidadeProdutores, TempoProducao, TempoTabalho) when QuantidadeProdutores > 0 ->
    PidProdutor = spawn(scripts, produtor, [PidFila, Socket, TempoProducao, TempoTabalho]),
    gen_tcp:send(Socket, io_lib:format("createProdutor-~p~n", [PidProdutor])),
    runProdutor(PidFila, Socket, QuantidadeProdutores - 1, TempoProducao, TempoTabalho);

% Inicia processos de produção
runProdutor(_, _, _, _, _)  ->
    true.

% Inicia processos de consumo
runConsumidor(PidFila, Socket, QuantidadeConsumidores)  when QuantidadeConsumidores > 0 ->
    PidConsumidor = spawn(scripts, consumidor, [PidFila, Socket]),
    gen_tcp:send(Socket, io_lib:format("createConsumidor-~p~n", [PidConsumidor])),
    runConsumidor(PidFila, Socket, QuantidadeConsumidores - 1);

% Inicia processos de produção
runConsumidor(_, _, _) ->
    true.

% Processo responsável pelo gerenciamento da fila
fila(Fifo, Key, TamanhoFila) ->
    receive
        {Pid, push} ->
            {Status, NewFifo} = pushFifo(Fifo, [Key, 0], TamanhoFila),
            io:format("[push] Buffer atual: "),
            io:write(NewFifo),
            io:format("~n"),
            if
               Status =:= full ->
                   Pid ! {self(), Status};
               true ->
                   Pid ! {self(), Status, Key}
            end,
            fila(NewFifo, Key + 1, TamanhoFila);
        {update, Obj, NKey} ->
            NewFifo = map(fun(X) ->
                [IKey, _] = X,
                if
                   IKey == NKey ->
                       [NKey, Obj];
                   true ->
                       X
                end
            end, Fifo),
            fila(NewFifo, Key, TamanhoFila);
        {Pid, get} ->
            {Status, NewFifo, Time} = pop(Fifo),
            io:format("[get] Buffer atual: "),
            io:write(NewFifo),
            io:write(Status),
            io:write(Time),
            io:format("~n"),
            if
               Status =:= ok ->
                   Pid ! {self(), Status, Time};
               Status =:= empty ->
                   Pid ! {self(), empty}
            end,
            fila(NewFifo, Key, TamanhoFila);
         _ ->
            fila(Fifo, Key,TamanhoFila)
    end.

% Adicio elemento na fila (Fila está vazia)
pushFifo([], Obj, _) ->
    {ok, [Obj]};

% Adicio elemento na fila
pushFifo(Fifo, Obj, TamanhoFila) when length(Fifo) < TamanhoFila ->
    {ok, Fifo ++ [Obj]};

% Adicio elemento na fila (Fila está cheia)
pushFifo(Fifo, _, _) ->
    {full, Fifo}.

% Remove primeiro elemento da lista
pop(Fifo) when length(Fifo) > 0 ->
    NewFifo = filter(fun(X) ->
        [_, ITime] = X,
         ITime /= 0
    end, Fifo),

    io:format("[pop] Buffer atual: "),
    io:write(NewFifo),
    io:format("~n"),


    if
       length(NewFifo) == 0 ->
           {empty, Fifo, 0};
       true ->

           [First | _] = NewFifo,
           T = delete(First, Fifo),
           {ok, T, First}
    end;
% Remove primeiro elemento da lista (Fila está vazia)
pop(Fifo) ->
    {empty, Fifo, 0}.

% Processo responsável pela produção de elementos
produtor(PidFila, Socket, TempoProducao, TempoTabalho) ->
    TimeProducao = rand:uniform(TempoProducao) * 1000,
    PidFila ! {self(), push},
    receive
        {PidFila, full} ->
             io:format("[~p] Fila cheia produtor dormindo (5000)\n", [self()]),
             timer:sleep(5000);
         {PidFila, ok, Key} ->
             TimeTrabalho = rand:uniform(TempoTabalho) * 1000,
             io:format("[~p] [~p] Inicio producao (~p)\n", [self(), TimeProducao, Key]),
             gen_tcp:send(Socket, io_lib:format("iniciaProducao-~p-~p-~p~n", [self(), Key, TimeTrabalho])),
             timer:sleep(TimeProducao),
             PidFila ! {update, TimeTrabalho, Key},
             io:format("[~p] [~p] Fim producao\n", [self(), Key]),
             gen_tcp:send(Socket, io_lib:format("finalizaProducao-~p-~p~n", [self(), Key])),
             io:format("[~p] Fila ok\n", [self()])
    end,
    produtor(PidFila, Socket, TempoProducao, TempoTabalho).

% Processo responsável pelo consumo de elementos na fila
consumidor(PidFila, Socket) ->
    PidFila ! {self(), get},
    receive
        {PidFila, empty} ->
             io:format("[~p] Fila vazia consumidor dormindo (5000)\n", [self()]),
             timer:sleep(5000);
        {PidFila, ok, Obj} ->
            [Key, Time] = Obj,
            gen_tcp:send(Socket, io_lib:format("iniciaConsumo-~p-~p~n", [self(), Key])),
            io:format("[~p] [~p] Inicia consumo (~p)\n", [self(), Key, Time]),
            timer:sleep(Time),
            io:format("[~p] [~p] Finaliza consumo (~p)\n", [self(), Key, Time]),
            gen_tcp:send(Socket, io_lib:format("finalizaConsumo-~p-~p~n", [self(), Key]))
    end,
    consumidor(PidFila, Socket).
