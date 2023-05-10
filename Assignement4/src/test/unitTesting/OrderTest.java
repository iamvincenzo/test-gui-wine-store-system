package test.unitTesting;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.communication.Order;

/**
 *
 * The class {@code OrderTest} defines the test for the methods of
 * the Order class.
 *
 **/

public class OrderTest {
	
	/**
	 * Class fields.
	 * 
	 * CODC - It is the code of customer associated with order.
	 * CODW - It is is the code of wine associated with order.
	 * NB - It is the number of bottles of the ordered wine.
	 * D - It is the order timestamp.
	 * TOT - It is the total amount of money that customer have to pay.
	 * 
	 * REQUEST - It is the type of the request.
	 */
	
	private static final int CODC = 1;
	private static final int CODW = 1;
	private static final int NB = 1;
	private static final Timestamp D = new Timestamp(System.currentTimeMillis());
	private static final float TOT = 12.3F;
	
	private static final String REQUEST = "buy";
	
	
	/**
	 * 
	 * This method is used to test the class constructor used by 
	 * customer to make a request for buy wine to the server.
	 *
	 */
	
	@DisplayName("Standard Order constructor")
	@Test
	public void standardOrderConstructorTest() {
		
		Order o = new Order(CODC, CODW, NB, D, TOT);
				
		assertAll(() -> assertTrue(CODC == o.getCodCustomer()), 
				() -> assertTrue(CODW == o.getCodWine()), 
					() -> assertTrue(NB == o.getNumBottles()), 
						() -> assertTrue(D == o.getDate()), 
							() -> assertTrue(TOT == o.getTot()));
	}
	
	
	/**
	 * 
	 * This method is used to test the method to get/set type of request.
	 * 
	 */
	
	@DisplayName("Standard get/set Order request")
	@Test
	public void standardOrderSetGetRequestTest() {
	
		Order o = new Order();
		
		o.setRequest(REQUEST);
		
		assertEquals(o.getRequest(), REQUEST);
	}
}
