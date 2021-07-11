import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ValidateManageLogic;

public class ServletForwardValidate {
	
	public static String action(HttpServletRequest request, HttpServletResponse response) {

		String action=request.getParameter("action");
		request.setAttribute("action",action);
		if(action.compareTo("getAutoId")==0){
			return "getAutoId"; 
		}else if(action.compareTo("view")==0){
			return "view"; 
		}else if(action.compareTo("add")==0)
			return "add";
		else if(action.compareTo("update")==0)
			return "update";
		else if(action.compareTo("delete")==0)
			return "delete";
		else
			return null; //abnormal value
	}
	
	public static String table(HttpServletRequest request, HttpServletResponse response) {

		String table=request.getParameter("table");
		request.setAttribute("table",table);
		if(table.compareTo("department")==0)
		{
			return "department"; 
		}else if(table.compareTo("departmentemployee")==0)
			return "departmentemployee";
		else if(table.compareTo("employee")==0)
			return "employee";
		else
			return null; //abnormal value
	}
	
	public static boolean departmentView(HttpServletRequest request, HttpServletResponse response) {
		String direction=request.getParameter("direction");
		
		if(direction==null)
			request.setAttribute("direction", "ASC");
		else if(direction.equals("ASC")||direction.equals("DESC"))
			request.setAttribute("direction", direction);
		else
			return false; //abnormal value

		return true;
	}
	
	public static boolean departmentUpdateRemove(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		request.setAttribute("id", id);
		return true;
	}
	
	public static boolean employeeView(HttpServletRequest request, HttpServletResponse response) {
		String direction=request.getParameter("direction");
		String currentPage=request.getParameter("currentPage");
		String recordsPerPage=request.getParameter("recordsPerPage");
		String nOfPages=request.getParameter("nOfPages");
		String keyword=request.getParameter("keyword");

		if(direction==null)
			request.setAttribute("direction", "ASC");
		else if(direction.equals("ASC")||direction.equals("DESC"))
			request.setAttribute("direction", direction);
		else
			return false; //abnormal value

		if(currentPage==null)
			request.setAttribute("currentPage", 1);
		else {
			try { 
		        int cp=Integer.parseInt(currentPage); 
		        request.setAttribute("currentPage", cp);
		    } catch(NumberFormatException e) { 
		        return false; 
		    } 
		}
	
		if(recordsPerPage==null)
			request.setAttribute("recordsPerPage", 50);
		else {
			try { 
		        int rpp=Integer.parseInt(recordsPerPage); 
		        request.setAttribute("recordsPerPage", rpp);
		    } catch(NumberFormatException e) { 
		        return false; 
		    } 
		}
		
	
		if(nOfPages==null)
			request.setAttribute("nOfPages", 1);
		else {
			try { 
		        int npp=Integer.parseInt(nOfPages); 
		        request.setAttribute("nOfPages", npp);
		    } catch(NumberFormatException e) { 
		        return false; 
		    } 
		}
		
		if(keyword==null)
			request.setAttribute("keyword", "");
		else
			request.setAttribute("keyword", keyword);
		
		return true;
	}
	
	public static boolean employeeUpdateRemove(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		request.setAttribute("id", id);
		return true;
	}
	
}
