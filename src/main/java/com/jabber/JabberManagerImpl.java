package com.jabber;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.RoomInfo;

import java.io.IOException;
import java.util.Set;

public class JabberManagerImpl implements JabberManager {

    private String server = "localhost";
    private int port = 5222;

    public JabberManagerImpl(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public JabberManagerImpl() {
    }

    public AbstractXMPPConnection performConnect(String name, String password) throws IOException, XMPPException, SmackException {
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setUsernameAndPassword(name, password);
        configBuilder.setPort(port);
        configBuilder.setHost(server);
        //configBuilder.setServiceName("conference." + server);
        configBuilder.setServiceName("localhost");
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        return new XMPPTCPConnection(configBuilder.build()).connect();
    }

    public void setStatus(boolean isAvailable, String status, AbstractXMPPConnection connection) throws SmackException.NotConnectedException {
        Presence.Type type = isAvailable ? Presence.Type.available : Presence.Type.unavailable;
        Presence presence = new Presence(type);
        presence.setStatus(status);
        connection.sendStanza(presence);
    }

    public Set<RosterEntry> listContacts(AbstractXMPPConnection connection) throws InterruptedException, SmackException.NotConnectedException, SmackException.NotLoggedInException {
        Roster roster = Roster.getInstanceFor(connection);
        if (!roster.isLoaded()) {
            roster.reloadAndWait();
        }
        return roster.getEntries();
    }

    public void addUserToRoster(String name, String groupName, AbstractXMPPConnection connection) throws SmackException, XMPPException.XMPPErrorException {
        Roster roster = Roster.getInstanceFor(connection);
        RosterGroup rosterGroup = roster.createGroup(groupName);
        String[] groups = new String[]{rosterGroup.getName()};
        roster.createEntry(name + "@localhost", name, groups);
    }

    public void sendMsg(String receiverName, String msg, AbstractXMPPConnection connection) throws Exception {
        if (hasContact(receiverName + "@localhost", connection)) {
            ChatManager chatManager = ChatManager.getInstanceFor(connection);
            Chat chat = chatManager.createChat(receiverName + "@localhost");
            chat.sendMessage(msg);
            System.out.println("Msg has been sent");
            //System.in.read();
        } else {
            System.out.println("This user is not in your roster");
        }
    }

    public void addUser(String name, String password, AbstractXMPPConnection connection) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException {
        AccountManager accountManager = AccountManager.getInstance(connection);
        accountManager.createAccount(name, password);
    }

    public void removeUser(String name, String password, AbstractXMPPConnection connection) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException {
        AccountManager accountManager = AccountManager.getInstance(connection);
        accountManager.deleteAccount();
    }

    public RoomInfo info(String roomName, AbstractXMPPConnection connection) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException {
        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
        return manager.getRoomInfo(roomName);
    }

    public boolean hasContact(String receiverName, AbstractXMPPConnection connection) throws SmackException.NotLoggedInException, InterruptedException, SmackException.NotConnectedException {
        Roster roster = Roster.getInstanceFor(connection);
        if (!roster.isLoaded()) {
            roster.reloadAndWait();
        }
        return roster.contains(receiverName);
    }

    public void chat(String user, String msg, AbstractXMPPConnection connection) throws SmackException.NotConnectedException {
        ChatManager chatmanager = ChatManager.getInstanceFor(connection);
        Chat newChat = chatmanager.createChat(user + "@localhost", new ChatMessageListener() {
            public void processMessage(Chat chat, Message message) {
                System.out.println(chat.getParticipant() + " -> " + message.getBody());
            }
        });
        newChat.sendMessage(msg);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
