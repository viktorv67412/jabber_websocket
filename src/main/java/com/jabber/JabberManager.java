package com.jabber;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smackx.muc.RoomInfo;

import java.io.IOException;
import java.util.Set;

public interface JabberManager {
    AbstractXMPPConnection performConnect(String name, String password) throws IOException, XMPPException, SmackException;

    void setStatus(boolean isAvailable, String status, AbstractXMPPConnection connection) throws SmackException.NotConnectedException;

    Set<RosterEntry> listContacts(AbstractXMPPConnection connection) throws InterruptedException, SmackException.NotConnectedException, SmackException.NotLoggedInException;

    void addUserToRoster(String name, String groupName, AbstractXMPPConnection connection) throws SmackException, XMPPException.XMPPErrorException;

    void sendMsg(String receiverName, String msg, AbstractXMPPConnection connection) throws Exception;

    void addUser(String name, String password, AbstractXMPPConnection connection) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException;

    void removeUser(String name, String password, AbstractXMPPConnection connection) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException;

    RoomInfo info(String roomName, AbstractXMPPConnection connection) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException;

    boolean hasContact(String receiverName, AbstractXMPPConnection connection) throws SmackException.NotLoggedInException, InterruptedException, SmackException.NotConnectedException;

    void chat(String user, String msg, AbstractXMPPConnection connection) throws SmackException.NotConnectedException;
}
