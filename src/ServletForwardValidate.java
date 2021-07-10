import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletForwardValidate {
	
	public static String actionValidate(HttpServletRequest request, HttpServletResponse response) {

		String action=request.getParameter("action");
		
		if(action.compareTo("view")==0)
		{
			return "view"; 
		}else if(action.compareTo("edit")==0)
			return "edit";
		else if(action.compareTo("remove")==0)
			return "remove";
		else
			return null; //abnormal value
	}
	
	public static String tableValidate(HttpServletRequest request, HttpServletResponse response) {

		String action=request.getParameter("table");
		
		if(action.compareTo("department")==0)
		{
			return "department"; 
		}else if(action.compareTo("departmentemployee")==0)
			return "departmentemployee";
		else if(action.compareTo("employee")==0)
			return "employee";
		else
			return null; //abnormal value
	}
	
	public static boolean departmentValidate(HttpServletRequest request, HttpServletResponse response) {
		String direction=request.getParameter("direction");
		
		if(direction==null)
			request.setAttribute("direction", "ASC");
		else if(direction.equals("ASC")||direction.equals("DESC"))
			request.setAttribute("direction", direction);
		else
			return false; //abnormal value

		return true;
	}
}
