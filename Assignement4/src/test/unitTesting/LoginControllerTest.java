package test.unitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.controller.LoginController;

/**
 *
 * The class {@code LoginControllerTest} defines the test for the methods of
 * the LoginController class.
 *
 **/

public class LoginControllerTest {
	
	
	/**
	 * Class fields.
	 * 
	 * logContr - It is the LoginController instance. 
	 */

	private static LoginController logContr = new LoginController();
	
	
	/**
	 * 
	 * This method is executed before each test method to 
	 * clear the user id. 
	 * It resets the id before each test.
	 * 
	 */
	
	@BeforeEach
	public void clear() {
		logContr.clearID();
	}

	
	/**
	 * This method is used to test the login phase.
	 * 
	 * Test-set:
	 * 
	 * 1. valid e-mail and valid password.
	 * 2. valid e-mail and invalid password.
	 * 3. invalid e-mail and valid password.
	 * 4. Empty fields ("'','',0").
	 * 5. Blank fields ("' ',' ',0").
	 * 6. invalid e-mail and invalid password.
	 * 
	 * @param e It is the user email to validate.
	 * @param p It is the user password to validate.
	 * @param r It is the user id that is the expected result.
	 */
	
	/* 
	 * ",",      // null
  	 *	
  	 * "''"      // empty
  	 *	
  	 * "' '"     // blank
	 */
	
	@DisplayName("Login")  
	@ParameterizedTest
	@CsvSource({"a@b,12345678,4", 
				"lorenzo.dipalma@gmail.com,29963647,0", 
				"vincenzo,29964712,0", 
				"'','',0", 
				"' ',' ', 0", 
				"vincenzo,2996,0"})
	public void testLogin(String e, String p, int r)  { //  * @throws IOException throws IOException
	    		
		logContr.handleLoginButton(e, p);
		
		int response = logContr.getID();
		
		assertEquals(r, response);
	}
	
	
	/**
	 * 
	 * This method is used to test the close socket method.
	 * It is also used to close the socket after the test.
	 * 
	 */
	
	@AfterEach
	public void close() {
		logContr.closeSocket();
	}
}
