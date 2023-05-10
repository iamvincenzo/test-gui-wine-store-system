package test.unitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.MonitorDB;
import main.communication.Wine;

/**
 *
 * The class {@code MonitorDBTest} defines the test for the methods of
 * the MonitorDB class.
 *
 **/

public class MonitorDBTest {
	
	/**
	 * Class fields.
	 * 
	 * MonitorDB - It is the MonitorDB instance. 
	 */
	
	private static MonitorDB MonitorDB = new MonitorDB();
	
	
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
	 * @param usr It is the user email to validate.
	 * @param pwd It is the user password to validate.
	 * @param role It is the user role.
	 * @param id_thread It is the id thread.
	 * @param r It is the user id that is the expected result.
	 */
	
	@DisplayName("Login Monitor")
	@ParameterizedTest
	@CsvSource({"a@b,12345678,customer,1,4",
				"lorenzo.dipalma@gmail.com,29963647,customer,4,0",
				"vincenzo,29964712,customer,4,0",
				"'','',customer,4,0",
				"' ',' ',customer,0,0",
				"vincenzo,2996,customer,4,0"})
	public void	loginTest(String usr, String pwd, String role, String id_thread, int r) {
		
		int response = MonitorDB.login(usr, pwd, role, id_thread);
		
		assertEquals(response, r);
	}
	
	
	/**
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
	 * @param id_thread It is the id thread.
	 */
	
	@DisplayName("Search Monitor")
	@ParameterizedTest
	@CsvSource({"Tavernello,Bianchi,2020,2",
		"Tavernello,Bianchi,2018,2",
		"Merlot,'','',2", 
		"'',ProducerTest,'',2", 
		"'','',2020,2", 
		"Merlot,Bianchi,'',2",
		"Merlot,'',1970,2",
		"'',Bianchi,2020,2",
		"Yates,'',1980,2",
		"'','','',2",
		"' ',' ',' ',2",
		"'','',2022,2"
		})
	public void	searchTest(String n, String p, String y, String id_thread) {
		
		Wine rq = new Wine(n, p, y);
		
		rq.setRequest("search");
		
		List<Wine> response = MonitorDB.searchWine(n,p,y,id_thread,rq); 
		
		assertTrue(response != null);
	}
	
	
	/**
	 * 
	 * This method is used to test the BuyWine method.
	 * 
	 * Test-set:
	 * 
	 * 1. valid data: all fields valid
	 * 2. invalid data: row selected but invalid number of bottles less than 0
	 * 3. valid data: number of bottles required greater than those available
	 * 
	 * @param codC It is the customer id.
	 * @param codW It is the wine id.
	 * @param nB It is the number of bottles desired to validate.
	 * @param tot It is the wine total price. 
	 * @param id_thread It is the id thread.
	 * @param r It is the expected result.
	 */
	
	@DisplayName("Buy Monitor")
	@ParameterizedTest
	@CsvSource({"1,1,1,12,2,1",
				"1,1,-1,12,2,0",
				"1,1,50,12,2,0"
				})
	
	public void	buyTest(int codC, int codW, int nB, float tot, String id_thread, int r) {
		
		Timestamp d = new Timestamp(System.currentTimeMillis());
		
		int response = MonitorDB.buyWine(codC,codW,nB, d, tot, id_thread);
		
		assertEquals(response, r);
	}
}
