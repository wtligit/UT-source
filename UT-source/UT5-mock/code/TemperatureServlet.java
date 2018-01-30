package mockDemo;
import junit.framework.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
public class TemperatureServlet {
	/*
	public void init() throws ServletException{
		String str_f = " ";
	}*/
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{
		String str_f = req.getParameter("Fahrenheit");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		try {
			int temp_f = Integer.parseInt(str_f);
			double temp_c = (temp_f - 32)*5.0/9.0;
			out.println( "Fahrenheit: " + temp_f + ", centigrade: " + temp_c);
		}catch(NumberFormatException e) {
			out.println( "Invalid temperature: "+str_f);
		}
	}
}
