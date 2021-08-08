package log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilities.ControllerManagement;
import utilities.LoggingGeneral;

@WebServlet("/log")
public class log extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		if (action.compareTo("download") == 0) {
			// prepare file for download
			OutputStream ost = null;
			BufferedInputStream buffIn = null;

			try {
				File reportFile = new File("C:\\Users\\Public" + File.separator + "OEDRS.log");
				ost = response.getOutputStream();
				response.addHeader("Content-Disposition", "attachment;filename=" + "OEDRS.log");
				buffIn = new BufferedInputStream(new FileInputStream(reportFile));
				int iBuf;
				if ((iBuf = buffIn.read()) != -1)
					ost.write((byte) iBuf);
				else
					ost.write(new String("No record available.").getBytes());

				while ((iBuf = buffIn.read()) != -1) {
					ost.write((byte) iBuf);
				}
				ost.flush();
				LoggingGeneral.setContentPoints(request, "Downloaded log. Completed.");
			} catch (Exception e) {
				LoggingGeneral.setContentPoints(request, "Unsuccess download log: " + e.getMessage());
			} finally {
				buffIn.close();
				ost.close();
			}

		} else if (action.compareTo("delete") == 0) {
			// remove the old log
			File reportFile = new File("C:\\Users\\Public" + File.separator + "OEDRS.log");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile));
				bw.write("");
				bw.flush();
				bw.close();
				ControllerManagement.navigateJS(request, response);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				LoggingGeneral.setContentPoints(request, "Unsuccess remove log. Failed");
			}
		} else if (action.compareTo("view") == 0) {
			// prepare the view
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				Cookie cookie;
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					if (cookie.getName().equals("log_refresh")) {
						request.setAttribute("log_refresh", cookie.getValue());
						break;
					} else if (!cookie.getName().equals("log_refresh") && i == cookies.length - 1) {
						Cookie nc = new Cookie("log_refresh", String.valueOf("true"));
						request.setAttribute("log_refresh", "true"); // for jsp
						nc.setMaxAge(-1);
						response.addCookie(nc);
					}
				}
			} else {
				// This case may be first initialize or cookie is blocked. We dont care the
				// cookie block, url encoding will only be used for report as it is important to
				// have session.
				// Other feature may not available if cookie is blocked
				Cookie nc = new Cookie("log_refresh", String.valueOf(String.valueOf("true")));
				nc.setMaxAge(-1);
				response.addCookie(nc);
				request.setAttribute("log_refresh", "true");
			}

			RequestDispatcher req = request.getRequestDispatcher("log_view.jsp");
			req.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Success view log. Completed.");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}