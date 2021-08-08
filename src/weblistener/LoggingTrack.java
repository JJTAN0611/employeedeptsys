package weblistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import utilities.LoggingGeneral;


@WebListener
public class LoggingTrack implements ServletContextListener {

	public LoggingTrack() {
// TODO Auto-generated constructor stub
	}


	public void contextDestroyed(ServletContextEvent sce) {
// TODO Auto-generated method stub
		LoggingGeneral.setDestroy();
	}


	public void contextInitialized(ServletContextEvent sce) {
// TODO Auto-generated method stub
		LoggingGeneral.setInit();
	}
}