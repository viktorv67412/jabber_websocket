package com.ws;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;

public class XmppWebSocketServlet extends WebSocketServlet {
    private static final long serialVersionUID = 6096357638151030175L;

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
        return new XmppWebSocket();
    }
}
