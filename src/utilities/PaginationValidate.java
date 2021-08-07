package utilities;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PaginationValidate {
	/*
	 * This class is to validate related pagination parameter (if have) to attribute
	 * for preprocess parameter is direction, current page, recordperPage, nOfPages,
	 * keyword If there have extra parameter is fine. If the program found it
	 * already have in the session AND not exist in the request, then this program
	 * will use the one in session.
	 */

	public static String getAppend(HttpServletRequest request) {
		switch ((String) request.getAttribute("target")) {
		case "employee":
			return "e";
		case "department":
			return "d";
		case "departmentemployee":
			return "de";
		}
		return "";
	}

	public static boolean singlePageView(HttpServletRequest request, HttpServletResponse response) {
		String direction = request.getParameter("direction");

		if (direction == null) {
			if (request.getSession().getAttribute(getAppend(request) + "direction") == null)
				request.getSession().setAttribute(getAppend(request) + "direction", "ASC"); // do default
			// else no need do anything. Because already exist
		} else if (direction.equals("ASC") || direction.equals("DESC"))
			request.getSession().setAttribute(getAppend(request) + "direction", direction);
		else
			return false; // abnormal value

		return true;
	}

	public static boolean multiplePageView(HttpServletRequest request, HttpServletResponse response) {
		String direction = request.getParameter("direction");
		String currentPage = request.getParameter("currentPage");
		String recordsPerPage = request.getParameter("recordsPerPage");
		String keyword = request.getParameter("keyword");

		if (direction == null) {
			if (request.getSession().getAttribute(getAppend(request) + "direction") == null)
				request.getSession().setAttribute(getAppend(request) + "direction", "ASC"); // do default
			// else no need do anything. Because already exist
		} else if (direction.equals("ASC") || direction.equals("DESC")) // from user
			request.getSession().setAttribute(getAppend(request) + "direction", direction);
		else
			return false; // abnormal value

		if (currentPage == null) {
			if (request.getSession().getAttribute(getAppend(request) + "currentPage") == null)
				request.getSession().setAttribute(getAppend(request) + "currentPage", 1); // do default
			// else no need do anything. Because already exist
		} else {
			try {
				int cp = Integer.parseInt(currentPage);
				request.getSession().setAttribute(getAppend(request) + "currentPage", cp); // from user
			} catch (NumberFormatException e) {
				return false;
			}
		}

		if (recordsPerPage == null) {
			if (request.getSession().getAttribute(getAppend(request) + "recordsPerPage") == null)
				request.getSession().setAttribute(getAppend(request) + "recordsPerPage", 50); // do default
			// else no need do anything. Because already exist
		} else {
			try {
				int rpp = Integer.parseInt(recordsPerPage);
				if (rpp <= 100 && rpp >= 1)
					request.getSession().setAttribute(getAppend(request) + "recordsPerPage", rpp); // from user
				else
					return false;
			} catch (NumberFormatException e) {
				return false;
			}

		}

		if (keyword == null) {
			if (request.getSession().getAttribute(getAppend(request) + "keyword") == null)
				request.getSession().setAttribute(getAppend(request) + "keyword", ""); // do default
			// else no need do anything. Because already exist
		} else {
			request.getSession().setAttribute(getAppend(request) + "keyword", keyword); // from user
			// accept any input as it is search
		}
		return true;
	}

}
