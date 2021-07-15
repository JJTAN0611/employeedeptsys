package utilities;


import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingGeneral {
	private Logger logger = LogManager.getRootLogger();

	public Logger getLogger() {
		return logger;
	}

	public void setEntryPoints(HttpServletRequest request) {
		logger.info("Entry Points" + request.getServletPath());
	}
	
	public void setContentPoints(HttpServletRequest request, String description) {
		logger.info("Content Points" + request.getServletPath()+" -> "+ description);
	}

	public void setExitPoints(HttpServletRequest request) {
		logger.info("Exit Points" + request.getServletPath());
	}
}