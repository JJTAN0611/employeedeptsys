package utilities;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingGeneral {

	// The general logging class. Some static function use for easier log purposes

	private static Logger logger = LogManager.getRootLogger();

	public static void setInit() {
		logger.info("Initalised");
	}

	public static void setDestroy() {
		logger.info("Destroyed");
	}

	public static void setSessionInit(String sessionId) {
		logger.info("Session Initalised: " + sessionId);
	}

	public static void setSessionDestroy(String sessionId) {
		logger.info("Session Destroyed: " + sessionId);
	}

	public static void setEntryPoints(HttpServletRequest request) {
		logger.info("Entry Points. SessionID:" + request.getSession().getId());
	}

	public static void setContentPoints(HttpServletRequest request, String description) {
		logger.info("Content Points" + request.getServletPath() + " -> " + description);
	}

	public static void setExitPoints(HttpServletRequest request) {
		logger.info("Exit Points" + request.getServletPath());
	}
}