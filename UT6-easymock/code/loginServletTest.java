package easyMockDemo;
//��̬������������ֱ��ʹ��expect�ȷ���
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
	//setup���ڻ����Ľ�����������mock��Ҫ�õ��Ľӿ�
	public void setUp() throws Exception{
		request = EasyMock.createMock(HttpServletRequest.class);
		context = EasyMock.createMock(ServletContext.class);
		dispatcher = EasyMock.createMock(RequestDispatcher.class);
		servlet = new loginServlet();
	}
	@After
	//����ûʲôҪ�ͷŵģ����Բ�д������Ϊ��������
	public void tearDown() {
		
	}
	@Test
	public void testLoginFailed() throws Exception {
		//���������õ���ֵ
		expect(request.getParameter("username")).andReturn("admin");
		expect(request.getParameter("password")).andReturn("1234");
		//setup��ɼ���mock
		replay(request);     
        try {
            servlet.doPost(request, null);
            fail("Not caught exception!");
        }
        catch(RuntimeException re) {
            assertEquals("Login failed.", re.getMessage());
        }
        //���mock�����״̬
        verify(request);
    }
	@Test
    public void testLoginOK() throws Exception {
		//���������õ���ֵ����ð���doPost�����˳����
		expect(request.getParameter("username")).andReturn("admin");
		expect(request.getParameter("password")).andReturn("123456");
		expect(context.getNamedDispatcher("dispatcher")).andReturn(dispatcher);
		dispatcher.forward(request, null);
		//setup��ɼ���mock
		replay(request);  
		replay(context);
		replay(dispatcher);
		/*Ϊ����getServletContext()�����������Ǵ�����ServletContext Mock����
		 ���Ƕ���һ�������ಢ��дgetServletContext()����*/
        loginServlet servlet = new loginServlet() {
            public ServletContext getServletContext() {
                return context;
            }
        };
        //����doPost����
        servlet.doPost(request, null);
        //���mock�����״̬
        verify(request);
        verify(context);
        verify(dispatcher);
    }
}
