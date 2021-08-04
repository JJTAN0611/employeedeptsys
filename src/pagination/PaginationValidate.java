package pagination;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilities.ControllerManagement;

public class PaginationValidate {
	/*
	 * This class is to validate related pagination parameter (if have) to attribute
	 * for preprocess parameter is direction, current page, recordperPage, nOfPages,
	 * keyword If there have extra parameter is fine.
	 */

	public static boolean singlePageView(HttpServletRequest request, HttpServletResponse response) {
		String direction = request.getParameter("direction");

		if (direction == null)
			request.setAttribute("direction", "ASC");
		else if (direction.equals("ASC") || direction.equals("DESC"))
			request.setAttribute("direction", direction);
		else
			return false; // abnormal value

		return true;
	}

	public static boolean multiplePageView(HttpServletRequest request, HttpServletResponse response) {
		String direction = request.getParameter("direction");
		String currentPage = request.getParameter("currentPage");
		String recordsPerPage = request.getParameter("recordsPerPage");
		String nOfPages = request.getParameter("nOfPages");
		String keyword = request.getParameter("keyword");

		if (direction == null)
			request.setAttribute("direction", "ASC"); // default
		else if (direction.equals("ASC") || direction.equals("DESC")) // from user
			request.setAttribute("direction", direction);
		else
			return false; // abnormal value

		if (currentPage == null)
			request.setAttribute("currentPage", 1); // default
		else {
			try {
				int cp = Integer.parseInt(currentPage);
				request.setAttribute("currentPage", cp); // from user
			} catch (NumberFormatException e) {
				return false;
			}
		}

		if (recordsPerPage == null)
			request.setAttribute("recordsPerPage", 50); // default
		else {
			try {
				int rpp = Integer.parseInt(recordsPerPage);
				if (rpp <= 100 && rpp >= 1)
					request.setAttribute("recordsPerPage", rpp); // from user
				else
					return false;
			} catch (NumberFormatException e) {
				return false;
			}

		}

		if (keyword == null)
			request.setAttribute("keyword", ""); // default
		else
			request.setAttribute("keyword", keyword); // from user

		return true;
	}

}
