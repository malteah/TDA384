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


handel(Server , {join, Chanel, Client}) ->
    io:format("handel server join"),
    
    case lists:member(Chanel, Server) of
        % Join server
        true ->
            Join_server = genserver:request(list_to_atom(Chanel), {join, Client}),

            case Join_server of 
                joined -> {reply, joined, Server};
                failed -> {reply, failed, Server}
            end;

        false  -> 
        genserver:start(list_to_atom(Chanel), [Client], fun chanel_handeler/2),
        {reply, joined, [Chanel | Server]}

    end;
        

handel(Server, stop_server) ->
    io:format("Stop"),
    genserver:stop(Server).

chanel_handeler(Clients, {join, Client}) ->
  case lists:member(Client, Clients) of
    %Already in channel
    true -> {reply, failed, Clients};
    
    %Not in channel
    false -> {reply, joined, [Client | Clients]}
        
  end;

chanel_handeler(Clients, {msg, Channel, Nick, Msg, From}) ->
    case lists:member(From, Clients) of
        true ->
            spawn(fun() -> lists:foreach(fun(Pid) -> 
                        case Pid == From of
                            true -> skip;
                            false -> genserver:request(Pid, {message_receive, Channel, Nick, Msg}) 
                        end
                end, 
            Clients)end),
                
            {reply, ok, Clients};

        false -> {reply, failed, Clients}
    end.

% chanel_handeler(Clients, {msg, Channel, Nick, Msg, From}) ->
%   case lists:member(From, Clients) of
%     %In channel
%     true -> spawn(fun() -> lists:foreach(
%       fun(Pid) ->
%         if
%           Pid == From -> skip;
%           true -> genserver:request(Pid, {message_receive, Channel, Nick, Msg})
%         end
%       end,
%       Clients) end),
%       {reply, ok, Clients};
%     %Not in channel
%     false -> {reply, failed, Clients}
%   end.



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
    % Return ok
    exit(ServerAtom, ok).
