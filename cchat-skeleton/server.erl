-module(server).
-export([start/1,stop/1]).

% Start a new server process with the given name
% Do not change the signature of this function.




start(ServerAtom) ->
    % TODO Implement function
    % - Spawn a new process which waits for a message, handles it, then loops infinitely
    % - Register this process to ServerAtom
    % - Return the process ID
    genserver:start(ServerAtom, [],fun  handel/2).

handel(Server, {Client,join, Chanel}) ->
    ChannelExists = lists:member(Chanel, Server),
    if
        ChannelExists == true ->
            RequestJoin = genserver:request(list_to_atom(Chanel), {Client,join}), %try to join
            if
                RequestJoin == approved ->
                    {reply, approved, Server}; %Client not in channel
                true -> %else
                    {reply, denied, Server} %Client is in channel
            end;
        true -> %if channel doesn't exist: create a new channel
        genserver:start(list_to_atom(Chanel), [Client], fun chanel_handeler/2),
        {reply, approved, [Chanel | Server]}
    end;

handel(Server, stopChannels) ->
    lists:foreach(fun(Channel) -> 
        genserver:stop(list_to_atom(Channel)),
        Server = lists:delete(list_to_atom(Channel), Server)
    end, Server),
    {reply, ok, Server}.


chanel_handeler(Channel, {Client, join}) ->
    case lists:member(Client, Channel) of
        true -> {reply, anything_but_approved, Channel}; %Client is in channel
        false -> {reply, approved, [Client | Channel]} %Client not in channel
    end;
        
chanel_handeler(Channel, {Sender, Nick, Msg, ChannelID}) ->
    spawn(fun() -> lists:foreach(fun(Pid) when Pid =/= Sender -> %spawn/1 allows parallel execution (creates a new process for each element in the list)
        genserver:request(Pid, {message_receive,ChannelID,Nick,Msg});
    (Pid) when Pid == Sender ->
        ok %<=>ignore when
    end,Channel)end),
    {reply, ok, Channel};

chanel_handeler(Channel, {Client,leave}) -> 
    case lists:member(Client, Channel) of
    true -> {reply, success, lists:delete(Client,Channel)}; %Client is in channel
    false -> {reply, fail, Channel} %Client not in channel
    end.

% Stop the server process registered to the given name,
% together with any other associated processes
stop(ServerAtom) ->
    % TODO Implement function
    genserver:request(ServerAtom, stopChannels),
    genserver:stop(ServerAtom).
