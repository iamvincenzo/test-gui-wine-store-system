package main.controller;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.communication.*;

/**
 * 
 * The class {@code LoginController} represents the controller that manages 
 * the actions that must be taken when a client triggers an action.
 *
 */

public class LoginController {
	
	/**
	 * Class fields.
	 * 
	 * SHOST - It is the host on which the server resides.
	 * SPORT - It is the port where the server listens.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 * 
	 * ID - It is the customer id.
	 * 
	 * userName - It is the field that contains the value of the user name.
	 * respServ - It is the field used to store the value of the server response to a close socket request. 
	 * contr - It is the field that contains the instance of the CustomerDashboardController used into the next window.
	 */

	private final String SHOST = "localhost"; // host on which the server resides
	private final int SPORT = 4444; // server port
	
	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	
	private int ID;
	
	private String userName;
	private int respServ;
	private CustomerDashboardController contr;
	

   /**
    * 
    * This method is used to change the scene when user clicks on a button 
    * (search/login/register).
    * 
    * @param id It is the customer id inside database.
    * @param os It is the Object Output Stream used to communicate with the server.
    * @param is It is the Object Input Stream used to communicate with the server.
    * @param client It is the socket used to communicate with the server.
    * @throws IOException Signals that an I/O exception has occurred.
    * 
    */

	public void changeScene(final int id, final ObjectOutputStream os, final ObjectInputStream is, final Socket client) throws IOException {
				
		// actions taken if the scene is some dashboard - passed parameters between windows are used for communication between the client and the server
		CustomerDashboardController controller = new CustomerDashboardController(); // loading controller to pass information
		
		this.setContr(controller); // storing the value of the controller
		
		controller.transferSocketIO(this.client, this.os, this.is); // this instruction is used to transfer socket, object output stream and object input stream to the next window
		controller.initialize(this.userName,id); // this instruction is used to pass the user name info to the next window.
		//controller.transferCustomer(id); // this instruction is used to pass the customer id inside database --> the id is used to make other operations such as search ecc.ecc
	}
	
	
	/**
	 * 
	 * This method is used to handles the login button.
	 * 
	 * @param userName It is the customer email.
	 * @param pass It is the customer password.
	 *
	 */

	public void handleLoginButton(final String userName, final String pass)  { // action taken when users clicks on log in button
	    	
    	try { // The client tries to connect to the server
			
			this.client = new Socket(SHOST, SPORT); // socket creation
			this.os = new ObjectOutputStream(client.getOutputStream()); // creation of an object output stream from a stream (client.getOutputStream()) to write an object into the object output stream (it is used to send object over the network)
			this.is = null; // definition of object input stream to receive object (response) from the server

			People rq = new People(userName, pass, "customer"); // creation of a log in request "customer"
			rq.setRequest("login"); // type of request: login

			os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			if (this.is == null) { // if object input stream has been never used
				
				this.is = new ObjectInputStream(new BufferedInputStream(client.getInputStream())); // creation of object input stream
			}
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("login")) { 
				
				Response rs = (Response) o; 
				
				this.setID(rs.getResp());
				
				if(rs.getResp() > 0) { // server sent true (customer id that is positive) to the client
					
					this.setUserName(userName);
					
					changeScene(rs.getResp(), this.os, this.is, this.client); // changing the scene after correct login
				}
				
				else { // server sent false to the client (wrong credential or user is not registered)

					this.client.close(); // if wrong login client closes the socket
				}
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    }
	

	/**
	 * This method is used to pass the role to the next window.
	 * 
	 * @param message The role.
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
	 */
		
	protected void transferRole(String message) throws FileNotFoundException { }
	
	
	// METHODS ADDED TO EXECUTE THE TEST
	
	/**
	 * 
	 * This method is used to close the socket with the server.
	 * 
	 */
	
	public void closeSocket() {
	 	
	 	try { // the client tries to connect to the server
	
			RequestClose rqs = new RequestClose(true); // creation of a close socket request
			this.os.writeObject(rqs); 
			this.os.flush(); 								
	
			if (is == null) {
				
				this.is = new ObjectInputStream(new BufferedInputStream(this.client.getInputStream()));
			}
	
			Object c = this.is.readObject();
	
			if (c instanceof Response && ((Response) c).getResponse().equals("close")) { 					
	
				Response rs_ = (Response) c;
				
				if(rs_.getResp() > 0) {
		
					this.setRespServ(rs_.getResp());
		
					this.client.close();
				}			
			}
		} 
	     
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * This method is used to set the user name.
	 * 
	 * @param userName2 It is the user name to set.
	 */
	
	private void setUserName(String userName2) { this.userName = userName2;	}
	
	
	/**
	 * This method is used to set the customer ID.
	 * 
	 * @param id It is the id to set.
	 */
	    
    public void setID(final int id) { this.ID = id; }
    
    
    /**
     * This method is used to get the customer ID.
     * 
     * @return It returns the customer ID.
     */
    
    public int getID() { return this.ID; }
    
    
    /**
     * 
     * This method is used to clear (reset) the customer ID field.
     * 
     */
    
    public void clearID() {	this.ID = 0; }
    
   
    /**
	 * This method is used to get the client socket.
	 * 
	 * @return It returns the client socket.
	 */

	public Socket getClient() { return client; }
	
	
	/**
	 * This method is used to set the client socket.
	 * 
	 * @param client It is the socket to set.
	 */

	public void setClient(Socket client) { this.client = client; }
	
	
	/**
	 * This method is used to get the client object output stream.
	 * 
	 * @return It returns the client object output stream.
	 */

	public ObjectOutputStream getOs() { return os; }
	
	
	/**
	 * This method is used to set the client object output stream.
	 * 
	 * @param os It is the object output stream to set.
	 */

	public void setOs(ObjectOutputStream os) { this.os = os; }
	
	
	/**
	 * This method is used to get the client object input stream.
	 * 
	 * @return It returns the client object input stream.
	 */

	public ObjectInputStream getIs() { return this.is; }
	
	
	/**
	 * This method is used to set the client object input stream.
	 * 
	 * @param is It is the object input stream to set.
	 */

	public void setIs(ObjectInputStream is) { this.is = is; }
	
	
	/**
	 * This method is used to get the CustomerDashboardController instance.
	 * 
	 * @return It returns the CustomerDashboardController instance.
	 */
	
	public CustomerDashboardController getContr() { return this.contr; }
	
	
	/**
	 * his method is used to set the CustomerDashboardController instance.
	 * 
	 * @param contr It is the CustomerDashboardController instance to set.
	 */
	
	public void setContr(CustomerDashboardController contr) { this.contr = contr; }
	
	
	/**
     * This method is used to get the server response to a close socket request.
     * 
     * @return It returns the value of the server response to a close socket request.
     */

	public int getRespSer() { return this.respServ; }
	
	
	/**
	 * This method is used to set the server response to a close socket request.
	 * 
	 * @param r It is the response value to set.
	 */
	
	public void setRespServ(int r) { this.respServ = r; }
}
