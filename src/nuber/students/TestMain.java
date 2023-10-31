package nuber.students;


import java.util.HashMap;



public class TestMain {

	public static void main(String[] args) {
		
		HashMap<String, Integer> regions = new HashMap<String, Integer>();
		
		regions.put("North", 2);
		regions.put("South", 5);
			
		NuberDispatch dispatch = new NuberDispatch(regions, true);
		
		for(int i = 0; i < dispatch.regionsArray.length; i++) {
			System.out.println(dispatch.regionsArray[i].regionName);
		}


		

	}

}
