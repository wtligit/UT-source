package mockDemo;
import junit.framework.*;
import com.mockobjects.servlet.*;
import com.mockobjects.*;
import com.mockobjects.Verifiable;
public class TemperatureServletTest extends TestCase {
	public void testTrue() throws Exception {
		TemperatureServlet s = new TemperatureServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setupAddParameter("Fahrenheit", "212");
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		assertEquals("Fahrenheit: 212, centigrade: 100.0\n" , response.getOutputStreamContents());
	}
	public void testError() throws Exception {
		TemperatureServlet s = new TemperatureServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setupAddParameter("Fahrenheit", "wrong");
		response.setExpectedContentType("text/html");		
		s.doGet(request, response);
		response.verify();
		assertEquals("Invalid temperature: wrong\n",response.getOutputStreamContents());
	}
}
