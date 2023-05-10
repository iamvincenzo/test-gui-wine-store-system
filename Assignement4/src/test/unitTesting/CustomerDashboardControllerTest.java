package test.unitTesting;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.communication.Wine;
import main.controller.CustomerDashboardController;

/**
 *
 * The class {@code CustomerDashboardControllerTest} defines the test for the methods of
 * the CustomerDashboardController class.
 *
 **/

@TestInstance(Lifecycle.PER_CLASS) // only one instance of the class
public class CustomerDashboardControllerTest {
	
	/**
	 * Class fields.
	 * 
	 * custDashContr - It is the CustomerDashboardController instance. 
	 * 
	 * SHOST - It is the host on which the server resides.
	 * SPORT - It is the port where the server listens.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 */

	private static CustomerDashboardController custDashContr;
	
	private final static String SHOST = "localhost";
	private final static int SPORT = 4444;
	
	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;
		
	
	/**
	 * This method is used to test the transferSocketIO method.
	 * It is also used to set the client socket and streams used to
	 * communicate with the server.
	 * 
	 * @throws IOException Signals that an I/O exception of some sort has occurred.
	 */

	@BeforeAll
	public void transferSocketIOTest() throws IOException { 
		
		custDashContr = new CustomerDashboardController();
		
		this.client = new Socket(SHOST, SPORT); // socket creation
		this.os = new ObjectOutputStream(client.getOutputStream()); // creation of an object output stream from a stream (client.getOutputStream()) to write an object into the object output stream (it is used to send object over the network)
		this.is = null; // definition of object input stream to receive object (response) from the server
		
		custDashContr.transferSocketIO(this.client, this.os, this.is);
		
		assertAll(() -> assertEquals(custDashContr.getClient(), this.client), 
				() -> assertEquals(custDashContr.getIs(), this.is), 
						() -> assertEquals(custDashContr.getOs(), this.os));
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
		
		boolean response = custDashContr.validateBottles(b);
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
		
		boolean response = custDashContr.validateYear(y);
		assertEquals(r, response);
	}

	
	/**
	 * 
	 * This method is used to test the initialize method.
	 * 
	 */
	
	@DisplayName("Initialize")
	@Test
	public void initializeTest() { 
		
		String n = "Paolo";
		int id = 4;
				
		custDashContr.initialize(n, id);
		
		assertAll(() -> assertEquals(custDashContr.getUserNameLabel(), n), 
				() -> assertEquals(custDashContr.getCustomer(), id));
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
		l1.add(w1); l1.add(w2); l1.add(w3); l1.add(w4); l1.add(w5);
		
		custDashContr.handleSearchViewAllButton();
		
		List<Wine> l = custDashContr.getListWine();
		
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
	@CsvSource({"Tavernello,Bianchi,2020,1",
				"Tavernello,Bianchi,2018,0",
				"Merlot,'','',3", 
				"'',ProducerTest,'',0", 
				"'','',2020,2", 
				"Merlot,Bianchi,'',1",
				"Merlot,'',1970,3",
				"'',Bianchi,2020,1",
				"Yates,'',1980,0",
				"'','','',0",
				"' ',' ',' ',0",
				"'','',2022,0"
				})
	public void handleSearchProductButtonTest(String n, String p, String y, int r) {
		
		custDashContr.handleSearchProductButton(n, p, y);
		
		List<Wine> l = custDashContr.getListWine();
		
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
	@CsvSource({"1,Tavernello,Bianchi,2020,c,c,12,1,CreditCart,1",
				"'','','','','','','','','',0",
				"1,Tavernello,Bianchi,2020,c,c,12,'',CreditCart,0",
				"3,Merlot,Bianchi,1970,c,c,10.5,1,CreditCart,0",
				"1,Tavernello,Bianchi,2020,c,c,12,a,CreditCart,0",
				"1,Tavernello,Bianchi,2020,c,c,12,-1,CreditCart,0",
				"5,a,a,2020,c,c,0,1,CreditCart,0"
				})
	public void handleBuyProductButtonTest(String id, String name, String producer, String year, String technicalNotes, 
			String vines, String price, String numBottlesRequired, String creditCardNumber, int r) {
		
		custDashContr.handleBuyProductButton(id, name, producer, year, technicalNotes, 
				vines, price, numBottlesRequired, creditCardNumber);
		
		int result = custDashContr.getResultBuy();
		
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

		custDashContr.closeSocket();
		
		int response = custDashContr.getRespSer();
		assertTrue(response > 0, "value " + response + " is positive");
	}
}
