package weblistener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import utilities.LoggingGeneral;

@WebListener()
public class UserTrackingListener implements HttpSessionListener {
	private static int users = 0;
	private static int userId = 1000;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		LoggingGeneral.setSessionInit(se.getSession().getId());//record
		userId++;
		users++;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		LoggingGeneral.setSessionInit(se.getSession().getId());//record
		users--;
	}

	public static int getActiveSessions() {
		return users;
	}

	public static int getUserId() {
		return userId;
	}
}