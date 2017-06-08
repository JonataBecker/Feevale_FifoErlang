-module(scripts).
-export([main/0, accept/1, loop/1, run/1, fila/2, produtor/2, consumidor/2]).

-define(SIZE, 10).
-define(TIME_PRODUTOR, 5).
-define(TIME_CONSUMIDOR, 5).
-define(MAX_LINE_SIZE, 512).
-define(TCP_OPTIONS, [list, {packet, line}, {active, false}, {reuseaddr, true}, {recbuf, ?MAX_LINE_SIZE}]).
-define(COMMAND_INIT, "init\n").
-define(COMMAND_CLOSE, "close\n").

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
            if
                Data == ?COMMAND_INIT ->
                    io:format("Iniando processos!~n"),
                    spawn(scripts, run, [Socket]);
                Data == ?COMMAND_CLOSE ->
                   io:format("Encerrando processos!~n");
               true ->
                   true
            end,
            loop(Socket);
        {error, closed} ->
            ok
    end.

run(Socket) ->
    Fifo = [],
    PidFila = spawn(scripts, fila, [Fifo, 1]),
    PidProdutor = spawn(scripts, produtor, [PidFila, Socket]),
    gen_tcp:send(Socket, io_lib:format("createProdutor-~p~n", [PidProdutor])),
    PidConsumidor = spawn(scripts, consumidor, [PidFila, Socket]),
    gen_tcp:send(Socket, io_lib:format("createConsumidor-~p~n", [PidConsumidor])).

% Processo responsável pelo gerenciamento da fila
fila(Fifo, Key) ->
    receive
        {Pid, push, Obj} ->
            {Status, NewFifo} = pushFifo(Fifo, [Key,Obj]),
            io:format("[push] Buffer atual: "),
            io:write(NewFifo),
            io:format("~n"),
            if
               Status =:= full ->
                   Pid ! {self(), Status, 0};
               true ->
                   Pid ! {self(), Status, Key}
            end,
            fila(NewFifo, Key + 1);
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
            fila(NewFifo, Key);
         _ ->
            fila(Fifo, Key)
    end.

% Adicio elemento na fila (Fila está vazia)
pushFifo([], Obj) ->
    {ok, [Obj]};

% Adicio elemento na fila
pushFifo(Fifo, Obj) when length(Fifo) < ?SIZE ->
    {ok, Fifo ++ [Obj]};

% Adicio elemento na fila (Fila está cheia)
pushFifo(Fifo, _) ->
    {full, Fifo}.

% Remove primeiro elemento da lista
pop(Fifo) when length(Fifo) > 0 ->
    [_|T] = Fifo,
    [First | _] = Fifo,
    {ok, T, First};
% Remove primeiro elemento da lista (Fila está vazia)
pop(Fifo) ->
    {empty, Fifo, 0}.

% Processo responsável pela produção de elementos
produtor(PidFila, Socket) ->
    TimeProducao = rand:uniform(?TIME_PRODUTOR) * 1000,
    io:format("[~p] Inicio producao (~p)\n", [self(), TimeProducao]),
    gen_tcp:send(Socket, io_lib:format("iniciaProducao-~p~n", [self()])),
    timer:sleep(TimeProducao),
    PidFila ! {self(), push, rand:uniform(?TIME_CONSUMIDOR) * 1000},
    io:format("[~p] Fim producao\n", [self()]),
    receive
        {PidFila, full, Key} ->
             io:format("[~p] Fila cheia produtor dormindo (5000)\n", [self()]),
             gen_tcp:send(Socket, io_lib:format("finalizaProducao-~p-~p~n", [self(), Key])),
             timer:sleep(5000);
         {PidFila, ok, Key} ->
             gen_tcp:send(Socket, io_lib:format("finalizaProducao-~p-~p~n", [self(), Key])),
             io:format("[~p] Fila ok\n", [self()])
    end,
    produtor(PidFila, Socket).

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
            io:format("[~p] [~p] Consumindo (~p)\n", [self(), Key, Time]),
            timer:sleep(Time),
            gen_tcp:send(Socket, io_lib:format("finalizaConsumo-~p-~p~n", [self(), Key]))
    end,
    consumidor(PidFila, Socket).
