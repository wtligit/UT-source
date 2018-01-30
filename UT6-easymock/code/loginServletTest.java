package easyMockDemo;
//静态导入这样可以直接使用expect等方法
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import javax.servlet.*;
import javax.servlet.http.*;
import org.easymock.*;
import junit.framework.*;
import org.junit.*;
import org.junit.Test;
public class loginServletTest extends TestCase {
	private HttpServletRequest request;
	private loginServlet servlet;
	private ServletContext context;
	private RequestDispatcher dispatcher;
	@Before
	//setup用于环境的建立，在其中mock需要用到的接口
	public void setUp() throws Exception{
		request = EasyMock.createMock(HttpServletRequest.class);
		context = EasyMock.createMock(ServletContext.class);
		dispatcher = EasyMock.createMock(RequestDispatcher.class);
		servlet = new loginServlet();
	}
	@After
	//好像没什么要释放的，可以不写，这里为了完整性
	public void tearDown() {
		
	}
	@Test
	public void testLoginFailed() throws Exception {
		//设置期望得到的值
		expect(request.getParameter("username")).andReturn("admin");
		expect(request.getParameter("password")).andReturn("1234");
		//setup完成激活mock
		replay(request);     
        try {
            servlet.doPost(request, null);
            fail("Not caught exception!");
        }
        catch(RuntimeException re) {
            assertEquals("Login failed.", re.getMessage());
        }
        //检查mock对象的状态
        verify(request);
    }
	@Test
    public void testLoginOK() throws Exception {
		//设置期望得到的值，最好按照doPost的语句顺序来
		expect(request.getParameter("username")).andReturn("admin");
		expect(request.getParameter("password")).andReturn("123456");
		expect(context.getNamedDispatcher("dispatcher")).andReturn(dispatcher);
		dispatcher.forward(request, null);
		//setup完成激活mock
		replay(request);  
		replay(context);
		replay(dispatcher);
		/*为了让getServletContext()方法返回我们创建的ServletContext Mock对象，
		 我们定义一个匿名类并覆写getServletContext()方法*/
        loginServlet servlet = new loginServlet() {
            public ServletContext getServletContext() {
                return context;
            }
        };
        //测试doPost方法
        servlet.doPost(request, null);
        //检查mock对象的状态
        verify(request);
        verify(context);
        verify(dispatcher);
    }
}
