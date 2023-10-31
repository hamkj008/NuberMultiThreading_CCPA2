package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nuber.students.NuberDispatch;
import nuber.students.NuberRegion;



class TestDispatchAndRegion {

	HashMap<String, Integer> regions = new HashMap<String, Integer>();
	NuberDispatch dispatch;

	
	@BeforeEach
	void setup() {
		regions.put("North", 2);
		regions.put("South", 5);
		dispatch = new NuberDispatch(regions, true);
		

	}
	
	@Test
	void testNull() {
		assertNotNull(dispatch);
	}
	
	
	@Test
	void testDispatchRegionSize() {
		assertEquals(2, dispatch.regionsArray.length);
	}
	
	@Test
	void testNames() {
		assertEquals("South", dispatch.regionsArray[0].regionName);
		assertEquals("North", dispatch.regionsArray[1].regionName);
	}
	
	@Test
	void testInstance() {
		assertTrue(dispatch.regionsArray[0] instanceof NuberRegion);
		assertTrue(dispatch.regionsArray[1] instanceof NuberRegion);
	}
	
	
		


		
		
		
		

	

}
