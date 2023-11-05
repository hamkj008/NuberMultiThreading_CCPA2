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
	private NuberRegion[] regionsArray;
	
	// Array blocking queue chosen for fixed size, additional guarantees of thread safety and possible extensibility. 
	// Offer() and poll() used instead of put() and take() because of return values and felt it was clearer to implement blocking more explicitly by using wait().
	private final BlockingQueue<Driver> queue;
	
	private static int jobId = 0;
	private int pendingJobs = 0;
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
		
		System.out.println("Creating Nuber Dispatch");
		
		this.logEvents = logEvents;
		
		// Queue size set to the max amount of drivers. Queue will block if full.
		queue = new ArrayBlockingQueue<Driver>(MAX_DRIVERS);
		regionsArray = new NuberRegion[regionInfo.size()];
		
		// Display number of regions
		String regionPlurality = regionsArray.length > 1 ? "regions" : "region";	
		System.out.println("Creating " + regionsArray.length + " " + regionPlurality);
		
		// Create Regions according to the map provided
		int i = 0;		
		for(Map.Entry<String, Integer> entry : regionInfo.entrySet()) {
			regionsArray[i] = new NuberRegion(this, entry.getKey(), entry.getValue());
			i++;
		}	
		
		System.out.println("Done creating " + regionsArray.length + " " + regionPlurality);
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
				
		// Book passenger into the correct region
		if(acceptingBookings) {
			for(int i = 0; i < regionsArray.length; i++) {
				if(regionsArray[i].getRegionName() == region) {
					logPendingJob(true);
					jobId = jobId + 1;
					future = regionsArray[i].bookPassenger(passenger);
				}
			}
		}
		return future;	
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
		if(newDriver == null) {
			System.out.println("Driver can't be added to queue because it is null");
			return false;
		}
		
		// If offer() succeeds, queue has free spot. Add driver to the queue and notify all threads that driver has been found.
		if(queue.offer(newDriver)) {
			notifyAll();
			return true;
		}
		// Can't add driver to full queue
		else {
			System.out.println("Maximum number of drivers exceeded! The driver could not be added to the queue because it is full");	
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
		// If the queue is empty no drivers are available. Thread waits untill one is found.
		while(queue.peek() == null) {
			try {
				wait();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Driver found. Deduct a pending job from the count.
		logPendingJob(false);
		return queue.poll();	
	}
	
	

	/**
	 * Gets the number of non-completed bookings that are awaiting a driver from dispatch
	 * Once a driver is given to a booking, the value in this counter should be reduced by one
	 * 
	 * @return Number of bookings awaiting driver, across ALL regions
	 */
	public int getBookingsAwaitingDriver()
	{
		return pendingJobs;
	}
	
	
	
	/**
	 * Tells all regions to finish existing bookings already allocated, and stop accepting new bookings
	 */
	public void shutdown() 
	{
		for(int i = 0; i < this.regionsArray.length; i++) {
			this.regionsArray[i].shutdown();
		}
		acceptingBookings = false;
	}
	
	
	
	/**
	 * Prints out the string booking + ": " + message
	 * to the standard output only if the logEvents variable passed into the constructor was true
	 * 
	 * @param booking The booking that's responsible for the event occurring
	 * @param message The message to show
	 */
	public void logEvent(Booking booking, String message) 
	{
		if (!logEvents) return;
		
		System.out.println(booking + ": " + message);		
	}
	
	
	
	/**
	 * Adjusts the pending job counter if a job is pending.
	 * Increments if boolean increment is true, else decrements.
	 * @param increment
	 */
	public synchronized void logPendingJob(boolean increment) {
		
		if(increment) {
			pendingJobs++;			
		}
		else {
			pendingJobs--;
		}
	}
	
	
	
	/*
	 * Booking objects will self-assign the jobID that dispatch has assigned to the booking.
	 */
	public int getJobId() {
		return jobId;
	}

}
