-module(scripts).
-export([main/0, fila/1, produtor/1, consumidor/1]).

-define(SIZE, 10).
-define(TIME_PRODUTOR, 5).
-define(TIME_CONSUMIDOR, 5).

main() ->
    Fifo = [],
    PidFila = spawn(scripts, fila, [Fifo]),
    spawn(scripts, produtor, [PidFila]),
    spawn(scripts, consumidor, [PidFila]).

% Processo responsável pelo gerenciamento da fila
fila(Fifo) ->
    receive
        {Pid, push, Obj} ->
            {Status, NewFifo} = pushFifo(Fifo, Obj),
            io:format("[push] Buffer atual: "),
            io:write(NewFifo),
            io:format("~n"),
            Pid ! {self(), Status},
            fila(NewFifo);
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
            fila(NewFifo);
         _ ->
            fila(Fifo)
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
produtor(PidFila) ->
    TimeProducao = rand:uniform(?TIME_PRODUTOR) * 1000,
    io:format("[~p] Inicio producao (~p)\n", [self(), TimeProducao]),
    timer:sleep(TimeProducao),
    PidFila ! {self(), push, rand:uniform(?TIME_CONSUMIDOR) * 1000},
    io:format("[~p] Fim producao\n", [self()]),
    receive
        {PidFila, full} ->
             io:format("[~p] Fila cheia produtor dormindo (5000)\n", [self()]),
             timer:sleep(5000);
         {PidFila, ok} ->
             io:format("[~p] Fila ok\n", [self()])
    end,
    produtor(PidFila).

% Processo responsável pelo consumo de elementos na fila
consumidor(PidFila) ->
    PidFila ! {self(), get},
    receive
        {PidFila, empty} ->
             io:format("[~p] Fila vazia consumidor dormindo (5000)\n", [self()]),
             timer:sleep(5000);
        {PidFila, ok, Time} ->
             io:format("[~p] Consumindo (~p)\n", [self(), Time]),
             timer:sleep(Time)
    end,
    consumidor(PidFila).
