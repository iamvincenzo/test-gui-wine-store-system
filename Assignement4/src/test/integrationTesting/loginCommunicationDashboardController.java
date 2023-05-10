package test.integrationTesting;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import main.controller.CustomerDashboardController;
import main.controller.LoginController;

/**
 * 
 * The class {@code loginCommunicationDashboardController} is used to test
 * the communication between LoginController class and CustomerDashboardController 
 * class.
 *
 */

@TestInstance(Lifecycle.PER_CLASS) // only one instance of the class
public class loginCommunicationDashboardController {
	
	/**
	 * Class fields.
	 *
	 * custDashContr - It is the CustomerDashboardController instance. 
	 * logContr - It is the LoginController instance. 
	 * 
	 * SHOST - It is the host on which the server resides.
	 * SPORT - It is the port where the server listens.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 */
	
	private static CustomerDashboardController custDashContr;
	private static LoginController logContr = new LoginController();
	
	private final static String SHOST = "localhost";
	private final static int SPORT = 4444;
	
	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;

	
	/**
	 * This method is used to test the communication between
	 * LoginController and CustomerDashboardController modules.
	 * 
	 * @throws IOException It signals that an I/O exception of some sort has occurred.
	 */
	
	@Test
	public void communicationTransferSocketIO() throws IOException {
		
		this.client = new Socket(SHOST, SPORT); // socket creation
		this.os = new ObjectOutputStream(client.getOutputStream()); // creation of an object output stream from a stream (client.getOutputStream()) to write an object into the object output stream (it is used to send object over the network)
		this.is = null; // definition of object input stream to receive object (response) from the server
	
		logContr.setClient(this.client);
		logContr.setOs(this.os);
		logContr.setIs(this.is);
		
		logContr.changeScene(1, this.os, this.is, this.client);
		
		custDashContr = logContr.getContr();
						
		assertAll(() -> assertEquals(this.client, custDashContr.getClient()), 
				() -> assertEquals(this.os, custDashContr.getOs()), 
						() -> assertEquals(this.is, custDashContr.getIs()));
	}
	
	
	/**
	 * 
	 * This method is used to test the closeSocket method.
	 * It is also used to close the socket after the test.
	 *
	 */
	
	@AfterAll
	public void closeSocketTest() {
		
		custDashContr.closeSocket();
		
		int response = custDashContr.getRespSer();
		assertTrue(response > 0, "value " + response + " is positive");
	}
}
