package nuber.students;



public class Driver extends Person {

	
	public Passenger currentPassenger;
	
	
	public Driver(String driverName, int maxSleep)
	{
		super(driverName, maxSleep);
	}
	
	
	
	/**
	 * Stores the provided passenger as the driver's current passenger and then
	 * sleeps the thread for between 0-maxDelay milliseconds.
	 * 
	 * @param newPassenger Passenger to collect
	 * @throws InterruptedException
	 */
	public synchronized void pickUpPassenger(Passenger newPassenger)
	{
		this.currentPassenger = newPassenger;		
		int pickupDuration = randomWithRange(0, maxSleep);
		
		try {	
			Thread.sleep(pickupDuration);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Sleeps the thread for the amount of time returned by the current 
	 * passenger's getTravelTime() function
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void driveToDestination() {
		
		try {
			Thread.sleep(this.currentPassenger.getTravelTime());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	

	public synchronized int randomWithRange(int min, int max)
	{
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}



}
