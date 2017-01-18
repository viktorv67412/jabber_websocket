package com.ws;

import com.beans.Message;
import com.jabber.JabberManager;
import com.jabber.JabberManagerImpl;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.websocket.WebSocket;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.RosterEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class XmppWebSocket implements WebSocket.OnTextMessage {

    private Connection connection;
    private AbstractXMPPConnection abstractXMPPConnection;
    private List<String> list;

    public XmppWebSocket() {
        list = new ArrayList<String>();
    }

    @Override
    public void onOpen(Connection arg0) {
        this.connection = arg0;
    }

    @Override
    public void onClose(int arg0, String arg1) {
        abstractXMPPConnection.disconnect();
    }

    @Override
    public void onMessage(String arg0) {
        ObjectMapper mapper = new ObjectMapper();
        JabberManager jabberManager = new JabberManagerImpl();

        try {
            Message recMsg = mapper.readValue(arg0, Message.class);

            if (recMsg.getType().equals("login")) {

                System.out.println(recMsg.getData());

                abstractXMPPConnection = jabberManager.performConnect(recMsg.getData().getUsername(), recMsg.getData().getPassword());
                abstractXMPPConnection.login();

                Message sndMsg = new Message("login");
                connection.sendMessage(mapper.writeValueAsString(sndMsg));


            } else if (recMsg.getType().equals("chat")) {

                System.out.println(recMsg.getData());

                jabberManager.addUserToRoster(recMsg.getData().getUsername(), "firstRostergroup", abstractXMPPConnection);
                Set<RosterEntry> rosterEntries = jabberManager.listContacts(abstractXMPPConnection);
                System.out.println(rosterEntries);

                for (RosterEntry rosterEntry : rosterEntries) {
                    list.add(rosterEntry.getUser());
                }

                Message sndRoster = new Message(list, "roster");
                connection.sendMessage(mapper.writeValueAsString(sndRoster));
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
