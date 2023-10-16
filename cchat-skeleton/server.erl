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
            RequestJoin = genserver:request(list_to_atom(Chanel), {join, Client}), %try to join
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


handel(Server, kill_channels) ->
    lists:foreach(fun(Ch) -> genserver:stop(list_to_atom(Ch)) end, Server),
    {reply, ok, []}.


chanel_handeler(Channel, {join, Client}) ->
    case lists:member(Client, Channel) of
        %Client is in channel
        true -> {reply, anything_but_approved, Channel};
        %Client not in channel
        false -> {reply, approved, [Client | Channel]}
    end;

chanel_handeler(Channel, {leave, Client}) -> 
    case lists:member(Client, Channel) of
    %Client is in channel
    true -> {reply, success, lists:delete(Client,Channel)};
    %Client not in channel
    false -> {reply, fail, Channel}
    end;


chanel_handeler(Channel, {Sender, Nick, Msg, ChannelID}) ->
    lists:foreach(fun(Pid) when Pid =/= Sender ->
        genserver:request(Pid, {message_receive,ChannelID,Nick,Msg});
    (Pid) when Pid == Sender ->
        ok %<=>ignore when
    end,Channel),
    {reply, ok, Channel}.
        

% Stop the server process registered to the given name,
% together with any other associated processes
stop(ServerAtom) ->
    % TODO Implement function
    genserver:request(ServerAtom, kill_channels),
    genserver:stop(ServerAtom).
