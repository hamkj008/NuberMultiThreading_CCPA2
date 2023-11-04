package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nuber.students.Driver;
import nuber.students.NuberDispatch;
import nuber.students.Passenger;


class TestDispatch {
	
	HashMap<String, Integer> regions = new HashMap<String, Integer>();
	NuberDispatch dispatch;
	Driver testDriver;
	Passenger testPassenger;
	
	
	@BeforeEach
	void setup() {
		regions.put("North", 2);
		regions.put("South", 5);
		
		dispatch = new NuberDispatch(regions, true);		
		testDriver = new Driver("Barbara", 100);
		testPassenger = new Passenger("Alex", 100);
	}

	
	@Test
	void testBooking() {
		dispatch.bookPassenger(testPassenger, "North");
		assertEquals(1, dispatch.pendingJobs);
		assertEquals(1, dispatch.getBookingsAwaitingDriver());
	}
	
	@Test
	void testAddBookingAfterShutdown() {
		dispatch.bookPassenger(testPassenger, "North");
		dispatch.shutdown();
		assertNull(dispatch.bookPassenger(testPassenger, "North"));		
	}
	
	
	@Test
	void testAddDriver() {
		dispatch.addDriver(testDriver);
		assertTrue(dispatch.queue.peek() instanceof Driver);
		
		Driver driver = dispatch.queue.poll();
		
		assertNotNull(driver);		
		assertEquals("Barbara", driver.name);
	}

}
