package nuber.students;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * A single Nuber region that operates independently of other regions, other than getting 
 * drivers from bookings from the central dispatch.
 * 
 * A region has a maxSimultaneousJobs setting that defines the maximum number of bookings 
 * that can be active with a driver at any time. For passengers booked that exceed that 
 * active count, the booking is accepted, but must wait until a position is available, and 
 * a driver is available.
 * 
 * Bookings do NOT have to be completed in FIFO order.
 * 
 * @author james
 *
 */
public class NuberRegion {

	
	private NuberDispatch dispatch;
	private ExecutorService pool;
	private String regionName;
	private boolean acceptingBookings = true;
	
	
	/**
	 * Creates a new Nuber region
	 * 
	 * @param dispatch The central dispatch to use for obtaining drivers, and logging events
	 * @param regionName The regions name, unique for the dispatch instance
	 * @param maxSimultaneousJobs The maximum number of simultaneous bookings the region is allowed to process
	 */
	public NuberRegion(NuberDispatch dispatch, String regionName, int maxSimultaneousJobs)
	{
		
		System.out.println("Creating Region for " + regionName);
		
		this.dispatch = dispatch;
		this.regionName = regionName;
		
		// Each region manages its own thread pool of jobs.
		this.pool = Executors.newFixedThreadPool(maxSimultaneousJobs);		
	}
	
	
	
	/**
	 * Creates a booking for given passenger, and adds the booking to the 
	 * collection of jobs to process. Once the region has a position available, and a driver is available, 
	 * the booking should commence automatically. 
	 * 
	 * If the region has been told to shutdown, this function should return null, and log a message to the 
	 * console that the booking was rejected.
	 * 
	 * @param waitingPassenger
	 * @return a Future that will provide the final BookingResult object from the completed booking
	 */
	public Future<BookingResult> bookPassenger(Passenger waitingPassenger)
	{		
		
		Booking booking = new Booking(dispatch, waitingPassenger);			
		Future<BookingResult> future = null;
		
		// Submit the booking to the thread pool or reject additional bookings.
		if(acceptingBookings) {
			future = pool.submit(booking);
		}
		else {
			System.out.println("Booking rejected. This region is not accepting any more bookings.");
		}
			
		return future;
	}
	
	
	
	/**
	 * Called by dispatch to tell the region to complete its existing bookings and stop accepting any new bookings
	 */
	public void shutdown()
	{
		pool.shutdown();
		acceptingBookings = false;
	}

	
	
	public String getRegionName() {
		return regionName;
	}

		
}
