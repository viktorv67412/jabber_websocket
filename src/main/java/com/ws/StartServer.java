package com.ws;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartServer {

    public StartServer() throws Exception {

        Server server = new Server(8040);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(this.getClass().getClassLoader().getResource("pages").toExternalForm());

        XmppWebSocketServlet xmppWebSocketServlet = new XmppWebSocketServlet();

        ServletHolder servletHolder = new ServletHolder(xmppWebSocketServlet);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(servletHolder, "/websocket/*");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, servletContextHandler});
        server.setHandler(handlerList);

        server.start();

        Handler handler = server.getHandler();
        if (handler instanceof WebAppContext) {
            System.out.println("found wac");
            WebAppContext webAppContext = (WebAppContext) handler;
            webAppContext.getSessionHandler().getSessionManager().setMaxInactiveInterval(60);
        }
    }

    public static void main(String[] args) throws Exception {
        new StartServer();
    }
}