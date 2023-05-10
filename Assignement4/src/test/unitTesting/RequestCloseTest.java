package test.unitTesting;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.communication.RequestClose;

/**
 * 
 * The class {@code RequestCloseTest} defines the test for the methods of
 * the RequestClose class.
 *
 */

public class RequestCloseTest {
	
	/**
	 * Class fields.
	 * 
	 * V - It is the value of the request.
	 */
	
	private static final boolean V = true;
	
	
	/**
	 * 
	 * This method is used to test the class constructor used by
	 * customer to make a request to the server for close socket.
	 * 
	 */
	
	@DisplayName("Standard Request Close constructor")
	@Test
	public void standardRequestCloseConstructorTest() {
		
		RequestClose o = new RequestClose(V);
		
		assertEquals(V, o.getV());
	}
}
