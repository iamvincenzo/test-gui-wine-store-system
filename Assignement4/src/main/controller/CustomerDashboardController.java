package main.controller;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import main.communication.Wine;
import main.communication.*;

/**
 * 
 * The class {@code CustomerDashboardController}  is used to manage
 * actions of CustomerDashboardController.fxml.
 *
 */

public class CustomerDashboardController  {
	
	
	/**
	 * Class fields.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 * 
	 * customer - It is the customer id.
	 * 
	 * usernameLabel - It is the name of the customer logged in.
	 * 
	 * VALID_BOTTLES_REGEX - It is a regular expression used to validate number of bottles format.
	 * VALID_YEAR_REGEX - It is a regular expression used to validate year format.
	 * 
	 * respServ - It is the field used to store the value of the server response to a close socket request.
	 * listWine - It is the field used to store the list that contains the server response to a search wine request.
	 * resultBuy - It is the field used to store the value of the server response to a buy wine request.
	 */

	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;
		
	private int customer;
	
	private String userNameLabel;
	
	private static final String VALID_BOTTLES_REGEX = "^[1-9][0-9]*$";
	private static final String VALID_YEAR_REGEX = "(19[6789][0-9]|20[01][0-9]|2020|2021)"; //dal 1960 al 2021
		
	
	private int respServ;
	private List<Wine> listWine;
	private int resultBuy;
	
	
	/**
	 * This method is used to validate the number of desired bottles entered by client.
	 * 
	 * @param bottles It is the bottles inserted by the client into the field.
	 * @return It returns a boolean value (true = valid bottles | false = not valid bottles).
	 */
	    
	public boolean validateBottles(String bottles) {
        
		String pattern = VALID_BOTTLES_REGEX;

        if (bottles.matches(pattern)) {
            return true;
        } 
        
        else {
            return false;
        }
    }
	    
	    
    /**
	 * This method is used to validate the year inserted by client.
	 * 
	 * @param year It is the year inserted by the client into the field.
	 * @return It returns a boolean value (true = valid year | false = not valid year).
	 */

    public boolean validateYear(String year) {
    	
    	String pattern = VALID_YEAR_REGEX;

	    if (year.matches(pattern)) {
	        return true;
	    } 
	        
	    else {
	        return false;
	    }
    }
	    
	    
    /**
	 * This method is used to pass information from LoginController 
	 * to CustomerDashboardController.
	 * It is used to pass the socket, Object output stream, Object input stream
	 * for client - server communication.
	 * 
	 * @param c It is the socket used by client to communicate with the server.
	 * @param o It is the object output stream used by client to sends messages (objects) to the server.
	 * @param i It is the object input stream used by client to receives messages (objects) form the server.
	 * @throws FileNotFoundException signals that an attempt to open the file denoted by a specified pathname has failed.
	 */
		
	public void transferSocketIO(final Socket c, final ObjectOutputStream o, final ObjectInputStream i) throws FileNotFoundException { // method used to pass  
		this.setClient(c);
		this.setOs(o);
	    this.setIs(i);
	}
	
	
	/**
	 * This method is used to initialize the customer dashboard.
	 * It makes appear the user name into the dashboard.
	 * 
	 * @param n It is the customer user name.
	 * @param id It is the customer id.
	 */

	public void initialize(final String n, final int id) { //final int id

		//usernameLabel.setText(n.split("@")[0]);
		
		this.setCustomer(id);
		this.setUserNameLabel(n);
	}
	
	
	/**
	 * 
	 * This method is used to handles the view all (customer) button.
	 * When customer clicks on this button, it will be shown searched wines.
	 * 
	 */
	
	public void handleSearchViewAllButton() {
		
		try { // the client tries to connect to the server
			
			Wine rq = new Wine(); // creation of a view wine request
			rq.setRequest("adminViewWine");
			
			List<Wine> tmp = new LinkedList<>();
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			if(this.is == null) {				
				this.is = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
			}
			
			Object o = this.is.readObject(); // client waits for a server response
					
			if (o instanceof Response && ((Response) o).getResponse().equals("search") ) { // if the server finds the wine the user is looking for
				
		        Response rs = (Response) o;
		        
		        // storing the result of the request sent by server
		        
		        for(Wine v: rs.getRespListS()) { 
					tmp.add(v);
				}
		        
		        this.setListWine(tmp);
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) { // the server does not find any results
				//this.setListWine(tmp1);
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * This method is used to get the list that contains wine store wines.
	 * 
	 * @return It returns the list that contains wine store wines.
	 */
	
	public List<Wine> getListWine() { return listWine; }
	
	
	/**
	 * This method is used to set the list that contains wine store wines.
	 * 
	 * @param l It is the list to set.
	 */
	
	public void setListWine(List<Wine> l) {	this.listWine = l; }
	
		
	/**
	 * This method is used to handles the search button.
	 * When customer clicks on this button, it will be shown 
	 * the searched wines.
	 * 
	 * @param name It is the wine name.
	 * @param producer It is the wine producer.
	 * @param year It is the wine year.
	 */
	
	public void handleSearchProductButton(String name, String producer, String year) { // actions take when client clicks on search button	
		
		if(name.isBlank() && producer.isBlank() && year.isBlank()) {
			
//			Alert alert = new Alert(Alert.AlertType.ERROR, "You must enter data in at least one field!"); // pop up
//	        alert.setHeaderText("Search failed");
//	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
//	        alert.showAndWait(); // it waits for a customer input
		}
		
		if (!producer.isBlank() && !validateYear(year)) { // if year is not blank it has to be validated
			//invalidYear.setVisible(true);
		} 
		
		else {
			//invalidYear.setVisible(false);
		}
		
		// client tries to connects to the server iff the user has entered at least data in one field
		
		if (!name.isBlank() || !producer.isBlank() || (!year.isBlank() && validateYear(year))) {
			
			try { // the client tries to connect to the server
				
				Wine rq = new Wine(name, producer, year); // creation of a search wine request
				rq.setRequest("search");
				
				List<Wine> tmp = new LinkedList<>();
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				if(this.is == null) {				
					this.is = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
				}
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("search")) { // if the server finds the wine the user is looking for
					
					Response rs = (Response) o;
					
					// storing the result of the response sent by server
					
					for(Wine v: rs.getRespListS()){
						tmp.add(v);
					}
			        
			        this.setListWine(tmp);
				}
				
				else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) { // the server does not found any results
					this.setListWine(tmp);				
				}
			}	 
			
			catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
		
	/**
	 *  This method is used to handles the buy button.
	 * 
	 * @param id It is the wine id.
	 * @param name It is the wine name.
	 * @param producer It is the wine producer.
	 * @param year It is the wine year.
	 * @param technicalNotes They are the wine technical notes.
	 * @param vines They are the wine vines.
	 * @param price It is the wine price.
	 * @param numBottlesRequired It is the number of bottles desired by customer.
	 * @param creditCardNumber It is the customer credit card number.
	 */
		
	public void handleBuyProductButton(final String id, final String name, final String producer, final String year, 
			final String technicalNotes, final String vines,
				final String price, final String numBottlesRequired, final String creditCardNumber) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // it takes the current date e time of the order
		
		int nBottles = 0;
		int nBottlesRequired = 0;
		
		if(name.isEmpty() && producer.isEmpty() && year.isEmpty() && technicalNotes.isEmpty() && 
				vines.isEmpty() && price.isEmpty()) { // if customer doesn't select any wine
			
			this.setResultBuy(0);
		}

		if(!numBottlesRequired.isEmpty() && validateBottles(numBottlesRequired)) {
			nBottlesRequired = Integer.parseInt(numBottlesRequired);
		}
		
		if(numBottlesRequired.isBlank() || !validateBottles(numBottlesRequired) || nBottlesRequired <= 0) { // if user entered an invalid number of bottles
			this.setResultBuy(0);
		}
		
		// it checks that the wine has been selected, the number of bottles has been entered and that it is valid
		
		if((!name.isEmpty() && !producer.isEmpty() && !year.isEmpty() && !technicalNotes.isEmpty() 
				&& !vines.isEmpty() && !price.isEmpty()) && 
					!numBottlesRequired.isEmpty() && validateBottles(numBottlesRequired) &&
						nBottlesRequired > 0) {
			
			this.handleSearchProductButton(name, producer, year);
			
			List<Wine> tmp = this.getListWine();
			
			if(!name.isEmpty() && !producer.isEmpty() && !year.isEmpty() && !tmp.isEmpty()) {
				
				for(Wine w: tmp) {
					if(w.getName().equals(name) && w.getProducer().equals(producer) && w.getYear().equals(year)) {
						nBottles = w.getNumBottles();
					}
				}
			}
		
			// it checks that the number of bottles inserted isn't greater than those available 
			
			if (nBottlesRequired > nBottles) {
				
				// user can subscribe notification
			
				this.setResultBuy(0); // storing the result of the operation
			}
			
			// it checks that the number of bottles inserted is less than or equal than those available 
			
			else if (nBottlesRequired <= nBottles) { // if the client enters all the parameters necessary to search for wine 
				
				if(!creditCardNumber.isBlank() || !creditCardNumber.isEmpty()) { // if user entered credit card number
					
					try { // the client tries to connect to the server
			
						float tot = nBottles * nBottlesRequired; // calculating the amount of money that customer have to pays
							
						Order rq = new Order(1, Integer.parseInt(id), nBottlesRequired, timestamp, tot); // creation of a buy wine request
						rq.setRequest("customerBuy");
						
						this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
						
						this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
						
						Object o = this.is.readObject(); // client waits for a server response
						
						if (o instanceof Response && ((Response) o).getResponse().equals("buy")) { 
							
							Response rs = (Response) o;
							
							this.setResultBuy(rs.getResp()); // storing the value of the server response
						}
					} 
			        
					catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				
				else { // customer doesn't want to make purchase
					this.setResultBuy(0); // storing the result of the operation
				}
			}
		}
	}
	
	
	 /**
	  * 
	  * This is a service method used to close the socket 
	  * used to communicate with the server. 
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
	 * This method is used to get the customer id.
	 * 
	 * @return It returns the customer user name.
	 */
	
	public int getCustomer() { return customer; }
	
	
	/**
	 * This method is used to set the customer user name.
	 * 
	 * @param c - It is the id to set.
	 */

	private void setCustomer(int c) { this.customer = c; }
	
	
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
	 * This method is used to get the user name label.
	 * 
	 * @return It returns the user name label.
	 */
	
	public String getUserNameLabel() { return userNameLabel; }


	/**
	 * This method is used to set the user name label.
	 * 
	 * @param userNameLabel It i the user name label to set.
	 */
	
	public void setUserNameLabel(String userNameLabel) { this.userNameLabel = userNameLabel; }

	
	// METHODS ADDED TO MAKE THE TEST
	
	/**
	 * This method is used to get the result of the buy wine operation.
	 * 
	 * @return It returns the result of the buy operation.
	 */

	public int getResultBuy() { return resultBuy; }


	/**
	 * This method is used to set the result of the buy wine operation.
	 * 
	 * @param resultBuy It is the result to set.
	 */
	
	public void setResultBuy(int resultBuy) { this.resultBuy = resultBuy; }
	
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
