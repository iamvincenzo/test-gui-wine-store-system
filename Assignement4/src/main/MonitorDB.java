package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import main.communication.Wine;

import java.util.LinkedList;

/**
 *
 * The class {@code MonitorDB} is used to manage the concurrency access to the
 * shared resource: database.
 * 
 */

public class MonitorDB {
	
	
	/**
	 * Class fields.
	 * 
	 * DB - It is the string to connect to the database. 
	 */
	
	private String DB = "jdbc:mysql://localhost:3306/winestore_dipalma_299636_fraello_299647?user=root&password=root&serverTimezone=Europe/Rome";
	
	
	/**
	 * This method is used to check that the user is registered.
	 * 
	 * @param usr It is the people email.
	 * @param pwd It is the people password.
	 * @param role It is the people role.
	 * @param id_thread It is the thread id.
	 * @return It returns the people id (login success) or zero (login failed).
	 */

	public synchronized int login(final String usr, final String pwd, final String role, final String id_thread) {

		String queryLogIn = "SELECT * FROM " + role + ";";
		char c = role.toUpperCase().charAt(0);

		if (role.equalsIgnoreCase("customer")) {
			
			// the flag Activated_C is used to indicates if account is active.
				// Administrator can't remove a user that made purchases because foreign key constraints
					// so when he clicks on remove he disables customer account
			
			// login can be made only by users whose account is active

			queryLogIn = "SELECT * FROM " + role + " WHERE Activated_C = 1"; 
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: %s, %s (request login) from its client%n", id_thread, usr, pwd);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(queryLogIn);

			while (myRs.next()) {

				if (myRs.getString("Email_" + c).equals(usr) && myRs.getString("Password_" + c).equals(pwd)) { // if credential supplied by user are correct the user can login

					System.out.format("thread %s sends: ok to its client%n", id_thread);
					return myRs.getInt("ID_" + c); // it returns the customer database id (positive number)
				}
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail login to its client%n", id_thread);
		return 0; // it returns zero if login fail
	}
	
	
	/**
	 * This method is used to search one or more wines into the database.
	 * This method is used to search finished wines stored into database.
	 * This method is used to show wines contained into system database to employee.
	 * This method is used to show to administrator wines contained into system database.
	 * 
	 * @param n It is the wine name.
	 * @param p It is the wine producer.
	 * @param y It is the wine year.
	 * @param id_thread It is the thread id.
	 * @param rq It is the wine object.
	 * @return It returns a list that contains the wine searched by customers or administrator or employee.
	 */

	public synchronized List<Wine> searchWine(final String n, final String p, final String y, final String id_thread, final Wine rq) {

		String search = "";
		
		if(rq.getRequest().equals("search")) {
		
			search = "SELECT * "
					+ "FROM wine "
					+ "WHERE Name_W = COALESCE(nullif('" + rq.getName() + "', ''), Name_W) AND "
					+ "Producer_W = COALESCE(nullif('" + rq.getProducer() + "', ''), Producer_W) AND "
					+ "Year_W = COALESCE(nullif('" + rq.getYear() + "', ''), Year_W);";
					
		}

		if (rq.getRequest().equals("adminViewWine")) {
			
			search = "SELECT * FROM wine;";
		}

		else if (rq.getRequest().equals("employeeViewOrderedWine")) {
			
			search = "SELECT * "
				   + "FROM orderedwineemployee "
				   + "WHERE Uploaded_W_E = 0"; // select only uncharged wines. This tables contains wines bought by employee
		}
		
		else if(rq.getRequest().equals("employeeViewBuyFinishedWine")) {
			
			// the flag Visualized_W is used to show the employee the notification until he 
				// purchase the bottles of that wine. Once purchased, this flag is set to one. 
					// When it is set to one, the employee is no longer notified of the lack
			
			search = "SELECT * "
				   + "FROM wine "
				   + "WHERE NumBottles_W = 0 "
				   + "AND Visualized_W = 0"; // this query select only finished wines.
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request search wine) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(search);

			List<Wine> l = new LinkedList<>();

			if (rq.getRequest().equals("search")) { // customer request

				while (myRs.next()) {

					l.add(new Wine(myRs.getInt("ID_W"), myRs.getString("Name_W"), myRs.getString("Producer_W"),
							myRs.getString("Year_W"), myRs.getString("TechnicalNotes_W"), myRs.getString("Vines_W"),
							myRs.getInt("NumBottles_W"), myRs.getFloat("Price_W")));
				}
			}

			else if (rq.getRequest().equals("adminViewWine") || 
					rq.getRequest().equals("employeeViewBuyFinishedWine")) { // employee or administrator request

				while (myRs.next()) {

					l.add(new Wine(myRs.getInt("ID_W"), myRs.getString("Name_W"), myRs.getString("Producer_W"),
							myRs.getString("Year_W"), myRs.getString("TechnicalNotes_W"), myRs.getString("Vines_W"),
							myRs.getInt("NumBottles_W"), myRs.getFloat("Price_W")));
				}
			}

			else if (rq.getRequest().equals("employeeViewOrderedWine")) { // employee request (ordered wine). It is used to view wines that have to be reloaded

				while (myRs.next()) {

					l.add(new Wine(myRs.getInt("ID_W_E"), myRs.getString("Name_W_E"), myRs.getString("Producer_W_E"),
							myRs.getString("Year_W_E"), myRs.getString("TechnicalNotes_W_E"),
							myRs.getString("Vines_W_E"), myRs.getInt("NumBottles_W_E"), myRs.getFloat("Price_W_E")));
				}
			}
			
			if (!l.isEmpty()) {
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return l;
			}
		}
		
		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail search wine to its client%n", id_thread);
		return null;
	}

	
	/**
	 * This method is used to decreases the number of bottles of a specific wine
	 * inserted into the database after a customer purchase. It is also used to save
	 * the customer order inside database.
	 * This method also tracks if the wine runs out.
	 * 
	 * @param codC It is the customer id.
	 * @param codW It is the wine id.
	 * @param nB It is the number of bottles desired by customer.
	 * @param d It is the timestamp that indicates the date-time of purchase.
	 * @param tot It is the amount of money paid by customer.
	 * @param id_thread It is the thread id.
	 * @return It returns one if the order was successful or zero if the order was unsuccessful.
	 */

	public synchronized int buyWine(final int codC, final int codW, final int nB, final Timestamp d, final float tot,
			final String id_thread) {

		String search = "SELECT * FROM wine WHERE ID_W = " + codW + ";";
		
		String updateW = "UPDATE wine SET NumBottles_W = NumBottles_W - " + nB + " WHERE ID_W = " + codW + ";";
		
		String insertOrd = "INSERT INTO orders(CodCustomer_O, CodWine_O, NumBOttles_O, Data_O, Tot_O) "
						 + "VALUES ('" + codC + "', '" + codW + "', '" + nB + "', '" + d + "', '" + tot + "');";

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request buy wine) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(search);

			while (myRs.next()) {

				if (myRs.getInt("NumBottles_W") - nB == 0) { 
					
					// if customer purchase all bottles than the flag Visualized_W is set
						// to zero so that the system notify employee that wine is finished

					updateW = "UPDATE wine SET NumBottles_W = NumBottles_W - " + nB + ", Visualized_W = 0  WHERE ID_W = " + codW + ";";
				}

				else {
					
					updateW = "UPDATE wine SET NumBottles_W = NumBottles_W - " + nB + " WHERE ID_W = " + codW + ";";
				}

				myStmt.executeUpdate(updateW);
				myStmt.executeUpdate(insertOrd);
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return 1;
			}
		} 
		
		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail buy wine to its client%n", id_thread);
		return 0;
	}
}
