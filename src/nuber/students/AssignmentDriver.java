package nuber.students;

import java.util.HashMap;

public class AssignmentDriver {

	
	public static void main(String[] args) throws Exception {

		//turn this or off to enable/disable output from the dispatch's logEvent function
		//use the logEvent function to print out debug output when required.
		boolean logEvents = true;
		
		
		//@param regionInfo Map of region names and the max simultaneous bookings they can handle
		//@param logEvents Whether logEvent should print out events passed to it
		HashMap<String, Integer> testRegions = new HashMap<String, Integer>();
		testRegions.put("Test Region", 50);
		

		System.out.println("start");
		
		
		
		/**
		 * This driver has a number of different sections that you can uncomment as you progress through the assignment
		 * Once you have completed all parts, you should be able to run this entire function uncommented successfully
		 */

		Passenger testPassenger = new Passenger("Alex", 100);
		Driver testDriver = new Driver("Barbara", 100);
		
		
		try {
			//should store the passenger, and then sleep the thread for as long as the driver's random timeout takes
			testDriver.pickUpPassenger(testPassenger);

			//should sleep the thread for as long as the passenger's random timeout takes
			testDriver.driveToDestination();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//test creating a dispatch object
//		NuberDispatch dispatch = new NuberDispatch(testRegions, logEvents);
		
		//create two new bookings
//		Booking b1 = new Booking(dispatch, testPassenger);
//		Booking b2 = new Booking(dispatch, testPassenger);
		
		
		
		//test creating a new region
		// @param dispatch The central dispatch to use for obtaining drivers, and logging events
		// @param regionName The regions name, unique for the dispatch instance
		// @param maxSimultaneousJobs The maximum number of simultaneous bookings the region is allowed to process
//		NuberRegion region = new NuberRegion(dispatch, "Test Region", 10);
		
		
		

		//test adding a driver to dispatch
//		dispatch.addDriver(testDriver);
		
		//test booking a single passenger
//		dispatch.bookPassenger(testPassenger, "Test Region");

		//shutdown the dispatch when it's done
//		dispatch.shutdown();

		
		
		
		
		//create NuberDispatch for given regions and max simultaneous jobs per region
		//once you have the above running, you should be able to uncomment the Simulations below to start to put everything together
		
//		HashMap<String, Integer> regions = new HashMap<String, Integer>();
//		regions.put("North", 50);
//		regions.put("South", 50);
		
		
		
		
		 /** 
		 * @param regions The region names and maximum simultaneous active bookings allowed in that region
		 * @param maxDrivers The number of drivers to create
		 * @param maxPassengers The number of passengers to create
		 * @param maxSleep The maximum amount a thread will sleep (in millseconds)) to simulate driving to, or dropping off a passenger
		 * @param logEvents Whether to log booking events to the console
		 **/
		
		//new Simulation(regions, 1, 10, 1000, logEvents);
		//new Simulation(regions, 5, 10, 1000, logEvents);
		//new Simulation(regions, 10, 10, 1000, logEvents);
		//new Simulation(regions, 10, 100, 1000, logEvents);
		//new Simulation(regions, 1, 50, 1000, logEvents);
	}

}