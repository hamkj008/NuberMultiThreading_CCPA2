package nuber.students;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;


/**
 * The core Dispatch class that instantiates and manages everything for Nuber
 * @author james
 */
public class NuberDispatch {
	

	// The maximum number of idle drivers that can be awaiting a booking 
	private final int MAX_DRIVERS = 999;
	private boolean logEvents = true;
	private NuberRegion[] region;
	private final BlockingQueue<Driver> queue;
	private boolean acceptingBookings = true;
	
	
	/**
	 * Creates a new dispatch objects and instantiates the required regions and any other objects required.
	 * It should be able to handle a variable number of regions based on the HashMap provided.
	 * 
	 * @param regionInfo Map of region names and the max simultaneous bookings they can handle
	 * @param logEvents Whether logEvent should print out events passed to it
	 */
	public NuberDispatch(HashMap<String, Integer> regionInfo, boolean logEvents)
	{
		this.logEvents = logEvents;
		
		queue = new ArrayBlockingQueue<Driver>(MAX_DRIVERS);
		region = new NuberRegion[regionInfo.size()];
		int i = 0;
		
		for(Map.Entry<String, Integer> entry : regionInfo.entrySet()) {
			region[i] = new NuberRegion(this, entry.getKey(), entry.getValue());
			i++;
		}
		
		
	}
	
	
	
	/**
	 * Adds drivers to a queue of idle driver.
	 * Must be able to have drivers added from multiple threads.
	 * 
	 * @param The driver to add to the queue.
	 * @return Returns true if driver was added to the queue
	 */
	public synchronized boolean addDriver(Driver newDriver)
	{
		if(newDriver != null) {
			queue.offer(newDriver);
			return true;
		}
		else {
			return false;
		}	
	}
	
	
	
	/**
	 * Gets a driver from the front of the queue.
	 * Must be able to have drivers added from multiple threads.
	 * 
	 * @return A driver that has been removed from the queue
	 */
	public synchronized Driver getDriver()
	{
		Driver d = null;
		
		if(queue.peek() != null) {
			d = queue.poll();
//			System.out.println("Driver found");
		}
//		else {
//			System.out.println("Queue is empty. No driver available");
//		}
//		
		return d;
		
	}

	
	
	
	/**
	 * Prints out the string booking + ": " + message
	 * to the standard output only if the logEvents variable passed into the constructor was true
	 * 
	 * @param booking The booking that's responsible for the event occurring
	 * @param message The message to show
	 */
	public synchronized void logEvent(Booking booking, String message) 
	{
		
		if (!logEvents) return;
		
		System.out.println(booking + ": " + message);
		
	}

	
	
	
	/**
	 * Books a given passenger into a given Nuber region.
	 * Once a passenger is booked, the getBookingsAwaitingDriver() should be returning one higher.
	 * If the region has been asked to shutdown, the booking should be rejected, and null returned.
	 * 
	 * @param passenger The passenger to book
	 * @param region The region to book them into
	 * @return returns a Future<BookingResult> object
	 */
	public synchronized Future<BookingResult> bookPassenger(Passenger passenger, String region) 
	{
		Future<BookingResult> future = null;
		
		if(acceptingBookings) {
			for(int i = 0; i < this.region.length; i++) {
				if(this.region[i].getRegionName() == region) {
					future = this.region[i].bookPassenger(passenger);
				}
			}
		}
		
		return future;
		
	}

	
	
	
	/**
	 * Gets the number of non-completed bookings that are awaiting a driver from dispatch
	 * Once a driver is given to a booking, the value in this counter should be reduced by one
	 * 
	 * @return Number of bookings awaiting driver, across ALL regions
	 */
	public int getBookingsAwaitingDriver()
	{
		
		for(int i = 0; i < this.region.length; i++) {
			
		}
		return 0;
	}
	
	
	
	/**
	 * Tells all regions to finish existing bookings already allocated, and stop accepting new bookings
	 */
	public synchronized void shutdown() 
	{
		for(int i = 0; i < this.region.length; i++) {
			this.region[i].shutdown();
		}
		
		acceptingBookings = false;
		
	}

}
