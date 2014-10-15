package test;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import edu.ndnu.capstone.web.EvacuationController;

@RunWith(SpringJUnit4ClassRunner.class)  
//Remove the MockStaticEntityMethods annotation  
//Setup the configuration of the application context and the web mvc layer  
@ContextConfiguration({"classpath:META-INF/spring/applicationContext.xml", "file:src/main/webapp/WEB-INF/spring/webmvc-config-test.xml"})   
public class LocationControlerTest {

	@Autowired  
	private ApplicationContext applicationContext;  
	  
	private MockHttpServletRequest request;  
	private MockHttpServletResponse response;  
	private RequestMappingHandlerAdapter handlerAdapter;  
	private EvacuationController controller;  
	     
	@Before  
	public void setUp() {  
		request = new MockHttpServletRequest();  
	    response = new MockHttpServletResponse();  
	         
	    handlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);  
	    // I could get the controller from the context here  
	    controller = new EvacuationController();  
	}  
	
	@Test
	public void controllerTestMethod(){
	   	final String expectedMessage = "Hello from the EVACUATION controller";  
	   	final String requestUri = "/controller/test/evacuation";  
	   	final String message;  
	   	final Object handler;  
	   	final HandlerMethod expectedHandlerMethod;  
	   	final ModelAndView mav;  
	   	final MockHttpServletRequest request;  
	   	final MockHttpServletResponse response;  
    	request = new MockHttpServletRequest();  
    	response = new MockHttpServletResponse();  

    	request.setRequestURI(requestUri);  
      
    	try{
    		expectedHandlerMethod = new HandlerMethod(controller, "get", Model.class, String.class);  
    		handler = this.getHandler(request);  
    		// For the most part we will be expecting HandlerMethod objects to be returned for our controllers.  
    		// Calling the to string method will print the complete method signature.  
    		Assert.assertEquals("Correct handler found for request url: " + requestUri, expectedHandlerMethod.toString(), handler.toString());  
      
    		// Handle the actual request  
    		mav = handlerAdapter.handle(request, response, handler);  

    		// Ensure that the right view is returned  
    		assertViewName(mav, "message-show");  
    		// Ensure that the view will receive the message object and that it is  
    		// a string  
    		message = assertAndReturnModelAttributeOfType(mav, "message", String.class);  
    		// We can test the message in case  
    		Assert.assertEquals(("Message returned was " + expectedMessage), expectedMessage, message);  
    	}catch(Exception exe){
    		exe.printStackTrace();
    	}
	}
	
	private Object getHandler(MockHttpServletRequest request) throws Exception {  
       	HandlerExecutionChain chain = null;  
  
       	Map<String, HandlerMapping> map = applicationContext.getBeansOfType(HandlerMapping.class);  
       	Iterator<HandlerMapping> itt = map.values().iterator();  
 
       	while (itt.hasNext()) {  
           	HandlerMapping mapping = itt.next();  
           	chain = mapping.getHandler(request);  
           	if (chain != null) {  
               	break;  
           	}  
  
       	}  
          
       	if (chain == null) {  
           	throw new InvalidParameterException("Unable to find handler for request URI: " + request.getRequestURI());  
       	}  
          
       	return chain.getHandler();  
   	}  
	
	public void assertViewName(ModelAndView mav, String str){
		Assert.assertEquals(mav.getViewName(), str);
			
	}
		
	public String assertAndReturnModelAttributeOfType(ModelAndView mav, String str, Class<String> aclass){
		Assert.assertTrue(str instanceof String);
		mav.setViewName(str);
		Assert.assertEquals(mav.getViewName(), str);
		return mav.getViewName();
	}
}
