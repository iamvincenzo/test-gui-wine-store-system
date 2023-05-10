package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import main.communication.*;

/**
 *
 * The class {@code WineStoreServerThread} is used to handles the communication
 * between client and server thread.
 *
 **/

public class WineStoreServerThread implements Runnable {

	
	/**
	 * Class fields.
	 * 
	 * socket - It is the socket used by server thread to communicates with the client.
	 * SlEEPTIME - It is a constant that defines the rest time for each thread.
	 * monitor - It is the shared resource between server threads. It is used to access database that is the shared resource.
	 */

	private Socket socket;
	private static final long SLEEPTIME = 200;
	private static MonitorDB monitor = new MonitorDB();

	
	/**
	 * Class constructor.
	 * 
	 * @param c it is the client socket.
	 **/

	public WineStoreServerThread(final Socket c) { this.socket = c; }

	
	/** {@inheritDoc} **/
	@Override
	public void run() {

		ObjectInputStream is = null; // definition of an object input stream used to receive messages (objects) from clients
		
		ObjectOutputStream os = null; // definition of an object output stream used to send messages (objects) to clients

		try {
			is = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream())); // creation of an object input stream from the stream passed as argument
		}

		catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String id_thread = String.valueOf(this.hashCode()); // thread id
		
		while (true) { 

			try { // when a thread is created by the server, it tries to communicate with the client
				
				Object i = is.readObject(); // thread waits for a request
				
				if (i instanceof People && ((People) i).getRequest().equals("login")) { // thread receives a request from the customer for login
	
					People rq = (People) i;
	
					Thread.sleep(SLEEPTIME); // between one operation and another there is a certain delay so, to ensure correct synchronization, the thread falls asleep for a while
	
					if (os == null) { // if object output stream has been never used
						
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream())); // creation of an object output stream
					}
	
					int value = monitor.login(rq.getEmail(), rq.getPassword(), rq.getRole(), id_thread);
					
					Response rs = new Response(value); // creation of the response to be sent to the client
					rs.setResponse("login"); // type of response: login
	
					os.writeObject(rs); // server thread sends response to the client
					os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
					if(rs.getResp() == 0) { // if wrong login then server closes the socket.
						
						this.socket.close();
						return;
					}
				}
					
				// server thread receives by unregistered user a request to search a wine	
					// server thread receives by administrator a request to view winestore's wines
						// server thread receives by employee a request to view wines he has ordered
							// server thread receives by employee a request to view finished wine
				
				else if (i instanceof Wine && (((Wine) i).getRequest().equals("search") || 
						((Wine) i).getRequest().equals("adminViewWine") || 
							((Wine) i).getRequest().equals("employeeViewOrderedWine") || 
								((Wine) i).getRequest().equals("employeeViewBuyFinishedWine"))) {
	
					Wine rq = (Wine) i;
	
					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					List<Wine> value = monitor.searchWine(rq.getName(), rq.getProducer(), rq.getYear(), id_thread, rq);					
					
					Response rs;
					
					if (value != null) { // if there are wine searched by user
						
						rs = new Response(value, (byte) 0); // it sends the wines as response
						rs.setResponse("search");
					}
	
					else {
						rs = new Response(0);						
						rs.setResponse("searchFail");
					}

					os.writeObject(rs);
					os.flush();
				}	
				
				else if (i instanceof Order && ((Order) i).getRequest().equals("customerBuy")) { // server thread receives by customer a request to buy wine
					
					Order rq = (Order) i;
	
					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					int value = monitor.buyWine(rq.getCodCustomer(), rq.getCodWine(), rq.getNumBottles(), rq.getDate(), rq.getTot(), id_thread);
					
					Response rs = new Response(value);
					rs.setResponse("buy");
					
					os.writeObject(rs);
					os.flush();
				}
					
				else if (i instanceof RequestClose) { // thread receives by client a request for closing socket
					
					RequestClose rq = (RequestClose) i;
					
					System.out.println("\n-----------");		
					System.out.println("|   LOG   |");
					System.out.println("-----------");	
					  
					System.out.format("thread %s receives: (request for close socket) from its client%n", id_thread);
					  
					Thread.sleep(SLEEPTIME);
					
					if (os == null) { 
						os = new ObjectOutputStream(new	BufferedOutputStream(this.socket.getOutputStream())); 
					}
					
					if(rq.getV()) {
						Response rs = new Response(1); // creation of the response to be sent to the client
						rs.setResponse("close");
						
						os.writeObject(rs);
						os.flush();	
						
						System.out.format("thread %s sends: ok to its client%n", id_thread);
						this.socket.close(); // server closes the socket 
						return;
					}
				}
			}
			
			catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}
