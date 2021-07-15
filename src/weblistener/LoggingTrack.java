package weblistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import utilities.LoggingGeneral;


@WebListener
public class LoggingTrack implements ServletContextListener {
	/**
	 * Default constructor.
	 */
	public LoggingTrack() {
// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
// TODO Auto-generated method stub
		sce.getServletContext().removeAttribute("log");
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
// TODO Auto-generated method stub
		sce.getServletContext().setAttribute("log", new LoggingGeneral());
	}
}