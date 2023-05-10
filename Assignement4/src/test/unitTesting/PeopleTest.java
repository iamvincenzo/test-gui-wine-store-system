package test.unitTesting;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.communication.People;

/**
 * 
 * The class {@code PeopleTest} defines the test for the methods of
 * the People class.
 *
 */

public class PeopleTest {
	
	/**
	 * 
	 * Class fields.
	 * 
	 * EMAIL - It is the people email.
	 * PASSWORD - It is the people password.
	 * ROLE - It is the people role.
	 * 
	 * REQUEST - It is the type of the request.
	 */
	
	private static final String EMAIL = "vincenzo.fraello@studenti.unipr.it";
	private static final String PASSWORD = "12345678";
	private static final String ROLE = "customer";
	
	private static final String REQUEST = "login";
	
	
	/**
	 * 
	 * This method is used to test the class constructor used by 
	 * person to make a request for login to the server.
	 * 
	 */
	
	@DisplayName("Standard People constructor")
	@Test
	public void standardPeopleConstructorTest() {
		
		People p = new People(EMAIL, PASSWORD, ROLE);
		
		assertAll(() -> assertTrue(EMAIL == p.getEmail()), 
				() -> assertTrue(PASSWORD == p.getPassword()), 
					() -> assertTrue(ROLE == p.getRole()));
	}
	

	/**
	 * 
	 * This method is used to test the method to get/set the type of request.
	 * 
	 */
	
	@DisplayName("Standard set/get People request")
	@Test
	public void standardOrderSetGetRequestTest() {
	
		People p = new People();
		
		p.setRequest(REQUEST);
		
		assertEquals(p.getRequest(), REQUEST);
	}
}
