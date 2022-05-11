package com;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.Inquiry;



@WebServlet("/InquriesAPI")
public class InquriesAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Inquiry inquiryObj = new Inquiry();
	
   
    public InquriesAPI() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = inquiryObj.insertInquiry(request.getParameter("InquiryTitle"),
				 request.getParameter("InquiryDesc"),
				request.getParameter("ContactNum"));
				response.getWriter().write(output);
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
			scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params)
			{
				
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		 }
		  catch (Exception e)
		 {
			  
		 }
		
		return map;
		
	}
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				 Map paras = getParasMap(request);
				 
				
				 String output = inquiryObj.updateInquiry(paras.get("hidInquiryIDSave").toString(),
				 paras.get("InquiryTitle").toString(),
				 paras.get("InquiryDesc").toString(),
				 paras.get("ContactNum").toString());
				 response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Map paras = getParasMap(request);
		 String output = inquiryObj.deletInquiry(paras.get("inquiryID").toString());
		response.getWriter().write(output); 
	}

}
