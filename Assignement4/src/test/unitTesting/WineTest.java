package test.unitTesting;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.communication.Wine;

/**
 * 
 * The class {@code WineTest} defines the test for the methods of
 * the Wine class.
 *
 */

public class WineTest {
	
	/**
	 * 
	 * Class fields.
	 * 
	 * ID - It is the wine's id.
	 * NAME - It is the wine's name.
	 * PRODUCER - It is wine's producer.
	 * YEAR - It is wine's year.
	 * TECHNICALNOTES - They are wine's technicalNotes.
	 * VINES - It is a list that contains wine's vines.
	 * NUMBOTTLES - It is the number of bottles.
	 * PRICE - It is the wine price.
	 * 
	 * REQUEST - It is the type of request.
	 */
	
	private static final int ID = 1;
	private static final String NAME = "Tavernello";
	private static final String PRODUCER = "Bianchi";
	private static final String YEAR = "2020";
	private static final String TECHNICALNOTES = "c";
	private static final String VINES = "c";
	private static final int NUMBOTTLES = 20;
	private static final float PRICE = 12.3F;
	
	private static final String REQUEST = "search";
	
	
	/**
	 * 
	 * This method is used to test the class constructor used by
	 * customer to make request for search wine to the server.
	 * 
	 */
	
	@DisplayName("Standard Search Wine constructor")
	@Test
	public void standardSearchWineConstructorTest() {
		
		Wine w = new Wine(NAME, PRODUCER, YEAR);
				
		assertAll(() -> assertTrue(NAME == w.getName()), 
				() -> assertTrue(PRODUCER == w.getProducer()), 
					() -> assertTrue(YEAR == w.getYear()));
	}
	
	
	/**
	 * 
	 * This method is used to test the class constructor used by 
	 * customer to make a request to buy a specific wine to the server.
	 * 
	 */
	
	@DisplayName("Standard Wine constructor")
	@Test
	public void standardWineConstructorTest() {
		
		Wine w = new Wine(ID, NAME, PRODUCER, YEAR,TECHNICALNOTES,VINES,NUMBOTTLES,PRICE);
				
		assertAll(() -> assertTrue(ID == w.getId()),
				() -> assertTrue(NAME == w.getName()), 
				() -> assertTrue(PRODUCER == w.getProducer()), 
				() -> assertTrue(YEAR == w.getYear()),
				() -> assertTrue(TECHNICALNOTES == w.getTechnicalNotes()),
				() -> assertTrue(VINES == w.getVines()),
				() -> assertTrue(NUMBOTTLES == w.getNumBottles()),
				() -> assertTrue(PRICE == w.getPrice()));
	}
	
	
	/**
	 * 
	 * This method is used to test the class constructor used by
	 * customer to make a view wines request to the server.
	 * 
	 */
	
	@DisplayName("Standard Setter Wine constructor")
	@Test
	public void standardWineSetterConstructorTest() {
		
		Wine w = new Wine();
		w.setId(ID);
		w.setName(NAME);
		w.setProducer(PRODUCER);
		w.setYear(YEAR);
		w.setTechnicalNotes(TECHNICALNOTES);
		w.setVines(VINES);
		w.setNumBottles(NUMBOTTLES);
		w.setPrice(PRICE);
				
		assertAll(() -> assertTrue(ID == w.getId()),
				() -> assertTrue(NAME == w.getName()), 
				() -> assertTrue(PRODUCER == w.getProducer()), 
				() -> assertTrue(YEAR == w.getYear()),
				() -> assertTrue(TECHNICALNOTES == w.getTechnicalNotes()),
				() -> assertTrue(VINES == w.getVines()),
				() -> assertTrue(NUMBOTTLES == w.getNumBottles()),
				() -> assertTrue(PRICE == w.getPrice()));
	}

	
	/**
	 * 
	 * This method is used to test the method to get/set type of request.
	 * 
	 */
	
	@DisplayName("Standard set/get Wine request")
	@Test
	public void standardWineSetGetRequestTest() {
	
		Wine w = new Wine();
		
		w.setRequest(REQUEST);
		
		assertEquals(w.getRequest(), REQUEST);
	}
}
