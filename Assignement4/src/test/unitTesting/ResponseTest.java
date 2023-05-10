package test.unitTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.communication.Response;
import main.communication.Wine;

/**
 * 
 * The class {@code ResponseTest} defines the test for the methods of
 * the Response class.
 *
 */

public class ResponseTest {
	
	/**
	 * Class fields.
	 * 
	 * VALUE - It is the value of the response.
	 * 
	 * RESPONSE - It is the type of the response.
	 */

	private static final int VALUE = 1;
	
	private static final String RESPONSE = "response";
	
	
	/**
	 * 
	 * This method is used to test the class constructor used by 
	 * server thread to make a response for login/search wine request 
	 * to the customer.
	 * 
	 */
	
	@DisplayName("Standard Response constructor")
	@Test
	public void standardResponseConstructorTest() {
		
		Response r = new Response(VALUE);
				
		assertEquals(VALUE, r.getResp());
	}
	
	
	/**
	 * 
	 * This method is used to test the class constructor used by 
	 * server thread to make a response for search wines request 
	 * to customers.
	 * 
	 */
	
	@DisplayName("Standard Response wine constructor")
	@Test
	public void standardResponseWineConstructorTest() {
		
		List<Wine> l = new LinkedList<>();
		
		Wine w = new Wine("Tavernello", "Bianchi", "2020");
		
		l.add(w);
		
		Response r = new Response(l, (byte) 0);
				
		assertEquals(l.size(), r.getRespListS().size());
	}	
	
	
	/**
	 * 
	 * This method is used to test the method to get/set type of request.
	 * 
	 */
	
	@DisplayName("Standard set/get Response response")
	@Test
	public void standardResponseSetGetResponseTest() {
	
		Response r = new Response(1);
		
		r.setResponseMDB(RESPONSE);
		
		assertTrue(r.getResponseMDB().equals(RESPONSE));
	}
}
