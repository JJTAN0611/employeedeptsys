package log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "log", urlPatterns = { "/log" })
public class log extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	
		OutputStream ost = null;
		BufferedInputStream buffIn = null;

		try {
			File reportFile = new File("C:\\Users\\Public" + File.separator + "OEDRS.log");
			ost = response.getOutputStream();
			response.addHeader("Content-Disposition", "attachment;filename=" + "OEDRS.log");
			buffIn = new BufferedInputStream(new FileInputStream(reportFile));
			int iBuf;
			while ((iBuf = buffIn.read()) != -1) {
				ost.write((byte) iBuf);
			}
			ost.flush();

		} finally {
			buffIn.close();
			ost.close();
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
	}// </editor-fold>
}