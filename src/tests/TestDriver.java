package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nuber.students.Driver;
import nuber.students.Passenger;



class TestDriver {
	
	Passenger testPassenger;
	Driver testDriver;
	
	
	@BeforeEach
	void setup() {
		testPassenger = new Passenger("Alex", 100);
		testDriver = new Driver("Barbara", 100);
	}
	
	
	@Test
	void notNull() {
		assertNotNull(testPassenger);
		assertNotNull(testDriver);
	}
	
	
	@Test
	void passengerSaved() {		
		testDriver.pickUpPassenger(testPassenger);
		assertEquals("Alex", testDriver.currentPassenger.name);			
	}
	
	@Test
	void travelTime() {		
		assertTrue(testDriver.randomWithRange(0, testDriver.maxSleep) <= testDriver.maxSleep);			
	}
	


}
