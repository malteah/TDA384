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
    
handel(Server, stop_server) ->
genserver:stop(Server).


chanel_handeler(Clients, {join, Client}) ->
case lists:member(Client, Clients) of
    %Client is in channel
    true -> {reply, anything_but_approved, Clients};
    %Client not in channel
    false -> {reply, approved, [Client | Clients]}
end;

chanel_handeler(Clients, {msg, Channel, Nick, Msg, Sender}) ->
    case lists:member(Sender, Clients) of
        true ->
            spawn(fun() -> lists:foreach(fun(Pid) -> 
                        case Pid == Sender of
                            true -> skip;
                            false -> genserver:request(Pid, {message_receive, Channel, Nick, Msg}) 
                        end
                end, 
            Clients)end),
                
            {reply, ok, Clients};

        false -> {reply, failed, Clients}
    end.





% Funtion för varige komando?
%~ Chanel
%TODO Join   


%TODO Leave
%TODO Leave channel

%~ user
%ToDo who am i
% TODO nick
% TODO nick new nic
%TODo quit


            

    

  
    

% Stop the server process registered to the given name,
% together with any other associated processes
stop(ServerAtom) ->
    % TODO Implement function
    genserver:request(ServerAtom, disconk),
    genserver:stop(ServerAtom).
