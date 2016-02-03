package main;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author modkomi
 * @since 21.01.2016
 */
public class Main {
    public static void main(String[] args) {
        try {

//            DBService dbService = new DBServiceImpl();
//            dbService.create();
//            dbService.check();

//            AccountService accountService = new AccountServiceImpl(dbService);


//            context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
//            context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
//            context.setContextPath("/");


//            ResourceHandler resource_handler = new ResourceHandler();
//            resource_handler.setDirectoriesListed(true);
//            resource_handler.setResourceBase("public_html");

//            AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
//
//            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//
//            context.addServlet(new ServletHolder(allRequestsServlet), "/");
//
//            Server server = new Server(8080);
//            server.setHandler(context);


            final Server server = new Server(8080);
            ServletContextHandler handler = new ServletContextHandler();
            handler.setResourceBase("WEB-INF");

            SessionManager sm = new HashSessionManager();
            SessionHandler sh = new SessionHandler(sm);
            handler.setSessionHandler(sh);

            DefaultServlet defaultServlet = new DefaultServlet();
            ServletHolder holder = new ServletHolder(defaultServlet);
            holder.setInitParameter("useFileMappedBuffer", "false");
            handler.addServlet(holder, "/");

            server.setHandler(handler);


            server.start();
            System.out.println("Server started!");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
