package test.systemTesting;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.communication.Wine;
import main.controller.CustomerDashboardController;
import main.controller.LoginController;

/**
 * 
 * The class {@code SystemTest} defines the test of 
 * the entire system.
 *
 */

@TestInstance(Lifecycle.PER_CLASS) // only one instance of the class
public class SystemTest {

	/**
	 * Class fields.
	 * 
	 * logContr - It is the LoginController instance.
	 * custDash - It is the CustomerDashboardController instance.
	 */

	private static LoginController logContr = new LoginController();
	private static CustomerDashboardController custDash;
	
	
	/**
	 * 
	 * This method is used to test the login phase.
	 * 
	 */

	@DisplayName("Login")
	@Test
	public void testLogin() {
		
		logContr.handleLoginButton("a@b", "12345678");
		
		int response = logContr.getID();
		assertAll(() -> assertEquals(4, response),
				() -> assertTrue(response > 0, "value " + response + " is not positive"));
		
		custDash = logContr.getContr();
	}


	/**
	 *
	 * This method is used to test the transferSocketIO method.
	 * 
	 */

	@DisplayName("Transfer Socket - ObjInStr - ObjOuStr")
	@Test
	public void transferSocketIOTest() {
		
		assertAll(() -> assertEquals(logContr.getClient(), custDash.getClient()),
				() -> assertEquals(logContr.getIs(), custDash.getIs()), 
					() -> assertEquals(logContr.getOs(), custDash.getOs()));
	}
	
	
	/**
	 * This method is used to test the validateBottles method.
	 * 
	 * Test-set:
	 * 
	 * 1. valid number of bottles.
	 * 2. invalid number of bottles: negative number.
	 * 3. invalid number of bottles: float number.
	 * 4. invalid number of bottles: string.
	 * 5. invalid number of bottles: zero.
	 * 6. invalid number of bottles: empty ("''").
	 * 7. invalid number of bottles: blank ("' '").
	 * 
	 * @param b It is the number of bottles to validate.
	 * @param r It is the expected result.
	 */
	
	/* 
	 * 
	 *  ","       // null
	 *  
  	 *	"''"      // empty
  	 *
  	 *	"' '"     // blank
	 */
	
	@DisplayName("Validate bottles")  
	@ParameterizedTest
	@CsvSource({"4,true", 
				"-1,false", 
				"1.1,false", 
				"a,false", 
				"0,false", 
				"'',false", 
				"' ',false"
				})
	public void testNBottles(String b, boolean r) {
		
		boolean response = custDash.validateBottles(b);
		
		assertEquals(r, response);
	}


	/**
	 * This method is used to test the validateYear method.
	 * 
	 * Test-set:
	 * 
	 * 1. valid wine year (1960-2021).
	 * 2. invalid wine year: before 1960.
	 * 3. invalid wine year: after 2021.
	 * 4. invalid wine year: string.
	 * 5. invalid wine year: empty ("''").
	 * 6. invalid wine year: blank ("' '").
	 * 
	 * @param y It is the wine year to validate.
	 * @param r It is the expected result.
	 */
	
	/* 
	 *  ","       // null
	 *  
  	 *	"''"      // empty
  	 *
  	 *	"' '"     // blank
	 */
	
	@DisplayName("Validate year")  
	@ParameterizedTest
	@CsvSource({"1970,true", 
				"1400,false", 
				"2030,false", 
				"a,false", 
				"'',false", 
				"' ',false"
				})
	public void testYear(String y, boolean r) {
		
		boolean response = custDash.validateYear(y);
		
		assertEquals(r, response);
	}

	
	/**
	 * 
	 * This method is used to test the handleSearchViewAllButton method.
	 *  
	 */

	@DisplayName("View all wines")
	@Test
	public void handleSearchViewAllButtonTest() {

		Wine w1 = new Wine("Tavernello", "Bianchi", "2020");
		Wine w2 = new Wine("Merlot", "Verdi", "1970");
		Wine w3 = new Wine("Merlot", "Bianchi", "1970");
		Wine w4 = new Wine("Merlot", "L", "1970");
		Wine w5 = new Wine("a", "a", "2020");

		List<Wine> l1 = new LinkedList<>();
		l1.add(w1);
		l1.add(w2);
		l1.add(w3);
		l1.add(w4);
		l1.add(w5);

		custDash.handleSearchViewAllButton();

		List<Wine> l = custDash.getListWine();

		for(int i = 0; i < l1.size(); i++) {
			assertEquals(l.get(i).getName(), l1.get(i).getName());
			assertEquals(l.get(i).getProducer(), l1.get(i).getProducer());
			assertEquals(l.get(i).getYear(), l1.get(i).getYear());
		}
	}

	
	/**
	 * 
	 * This method is used to test the handleSearchProductButton method.
	 * 
	 * Test-set:
	 * 
	 * 1. valid data: all fields compiled.
	 * 2. valid data: only Name field compiled.
	 * 3. valid data: only Producer field compiled.
	 * 4. valid data: only Year field compiled.
	 * 5. valid data: only Name and Producer fields compiled.
	 * 6. valid data: only Name and Year fields compiled.
	 * 7. valid data: only Producer and Year fields compiled.
	 * 8. invalid data: all fields empty.
	 * 9. invalid data: all fields blank.
	 * 10. invalid data: invalid year.
	 * 
	 * @param n It is the wine name to validate.
	 * @param p It is the wine producer to validate.
	 * @param y It is the wine year to validate.
	 * @param r It is the expected result.
	 */

	@DisplayName("Search Wine")
	@ParameterizedTest
	@CsvSource({ "Tavernello,Bianchi,2020,1", 
				 "Tavernello,Bianchi,2018,0", 
				 "Merlot,'','',3", "'',ProducerTest,'',0",
				 "'','',2020,2", "Merlot,Bianchi,'',1", 
				 "Merlot,'',1970,3", "'',Bianchi,2020,1", 
				 "Yates,'',1980,0",
				 "'','','',0", 
				 "' ',' ',' ',0", 
				 "'','',2022,0"
				 })
	public void handleSearchProductButtonTest(String n, String p, String y, int r) {

		logContr.getContr().handleSearchProductButton(n, p, y);

		List<Wine> l = logContr.getContr().getListWine();

		assertEquals(l.size(), r);
	}

	
	/**
	 * 
	 * This method is used to test the handleBuyProductButton method.
	 * 
	 * Test-set:
	 * 
	 * 1. valid data: all fields valid.
	 * 2. invalid data: row not selected.
	 * 3. invalid data: row selected but number of bottles required not entered.
	 * 4. invalid data: row selected but invalid number of bottles required (float number).
	 * 5. invalid data: row selected but invalid number of bottles required (string).
	 * 6. invalid data: row selected but invalid number of bottles less than 0.
	 * 7. valid data: number of bottles required greater than those available.
	 * 
	 * @param id It is the wine id to validate.
	 * @param name It is the wine name to validate.
	 * @param producer It is the wine producer to validate.
	 * @param year It is the wine year to validate.
	 * @param technicalNotes It is the wine technical notes to validate.
	 * @param vines It is the wine vines to validate.
	 * @param price It is the wine price to validate.
	 * @param numBottlesRequired It is the number of bottles desired to validate.
	 * @param creditCardNumber It is the credit number to validate.
	 * @param r It is the expected result.
	 */
		
	@DisplayName("Buy Wine")
	@ParameterizedTest
	@CsvSource({ "1,Tavernello,Bianchi,2020,c,c,12,1,CreditCart,1", 
				 "'','','','','','','','','',0",
			     "1,Tavernello,Bianchi,2020,c,c,12,'',CreditCart,0", 
			     "3,Merlot,Bianchi,1970,c,c,10.5,1,CreditCart,0",
			     "1,Tavernello,Bianchi,2020,c,c,12,a,CreditCart,0",
			     "1,Tavernello,Bianchi,2020,c,c,12,-1,CreditCart,0",
			     "5,a,a,2020,c,c,0,1,CreditCart,0"
			     })
	public void handleBuyProductButtonTest(String id, String name, String producer, String year, String technicalNotes,
			String vines, String price, String numBottlesRequired, String creditCardNumber, int r) {

		logContr.getContr().handleBuyProductButton(id, name, producer, year, technicalNotes, vines, price, 
				numBottlesRequired, creditCardNumber);

		int result = logContr.getContr().getResultBuy();

		assertEquals(result, r);
	}

	
	/**
	 * 
	 * This method is used to test the closeSocket method.
	 * It is also used to close the socket at the end of the test.
	 * 
	 */
	
	@AfterAll
	public void closeSocketTest() {

		logContr.closeSocket();

		int response = logContr.getRespSer();
		assertTrue(response > 0, "value " + response + " is positive");
	}
}
