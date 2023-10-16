-module(client).
-export([handle/2, initial_state/3]).

% This record defines the structure of the state of a client.
% Add whatever other fields you need.
-record(client_st, {
    gui, % atom of the GUI process
    nick, % nick/username of the client
    server, % atom of the chat server
    channels
    %  TODO Här kan vi lägga till mer 
}).

% Return an initial state record. This is called from GUI.
% Do not change the signature of this function.
initial_state(Nick, GUIAtom, ServerAtom) ->
    #client_st{
        gui = GUIAtom,
        nick = Nick,
        server = ServerAtom,
        channels = []
    }.
% handle/2 handles each kind of request from GUI
% Parameters:
%   - the current state of the client (St)
%   - request data from GUI
% Must return a tuple {reply, Data, NewState}, where:
%   - Data is what is sent to GUI, either the atom `ok` or a tuple {error, Atom, "Error message"}
%   - NewState is the updated state of the client

% Join channel
handle(St, {join, Channel}) ->
    try
        ServerExists = lists:member(St#client_st.server,registered()),
        if 
            ServerExists == false ->
                {reply, {error,server_not_reached,"Server does not exist"},St};
            ServerExists == true -> 
                RequestJoin = genserver:request(St#client_st.server, {self(),join,Channel}),
                if
                    RequestJoin == approved -> %adds the channel to clients channels
                        {reply, ok, St#client_st{channels = [Channel | St#client_st.channels]}};
                    RequestJoin == denied -> %else (denied)
                        {reply, {error,user_already_joined,"You are already in this channel"}, St}
                end
            end
    catch
        throw:timeout_error ->
            {reply, {error,server_not_reached,"Attempt to join timed out"}, St}
    end;

% Leave channel
handle(St, {leave, Channel}) ->
    TryLeave = genserver:request(list_to_atom(Channel), {leave, self()}),
    if
        TryLeave == success -> 
            {reply, ok, St#client_st{channels = lists:delete(Channel,St#client_st.channels)}};
        TryLeave == fail ->
            {reply, {error,user_not_joined,"You have not joined this channel"}, St}
    end;



% Sending message (from GUI, to channel)
handle(St, {message_send, Channel, Msg}) ->

    ChannelExists = lists:member(list_to_atom(Channel),registered()),
    case ChannelExists of
        false -> {reply, {error,server_not_reached,"Server does not exist"},St};
        true ->
             SelfInChannel = lists:member(Channel,St#client_st.channels),
            case SelfInChannel of
                false -> {reply, {error, user_not_joined, "You have not joined this channel"}, St};
                true ->
                    try
                        genserver:request(list_to_atom(Channel), {self(), St#client_st.nick, Msg, Channel}),
                        {reply, ok, St} %In channel
                    catch
                        _:_ ->
                        {reply, {error,server_not_reached,"Not in server"}, St}
                    end
            end
    end;


    

% This case is only relevant for the distinction assignment!
% Change nick (no check, local only)
handle(St, {nick, NewNick}) ->
    {reply, ok, St#client_st{nick = NewNick}} ;

% ---------------------------------------------------------------------------
% The cases below do not need to be changed...
% But you should understand how they work!

% Get current nick
handle(St, whoami) ->
    {reply, St#client_st.nick, St} ;

% Incoming message (from channel, to GUI)
handle(St = #client_st{gui = GUI}, {message_receive, Channel, Nick, Msg}) ->
    gen_server:call(GUI, {message_receive, Channel, Nick++"> "++Msg}),
    {reply, ok, St} ;

% Quit client via GUI
handle(St, quit) ->
    % Any cleanup should happen here, but this is optional
    {reply, ok, St} ;

% Catch-all for any unhandled requests
handle(St, Data) ->
    {reply, {error, not_implemented, "Client does not handle this command"}, St}.
