package csi311;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List; 
import java.util.Vector; 

public class RegexCo {

	private int countWest    = 0; 
	private int countEast    = 0; 
	private int countAve     = 0;
	private int countBway    = 0; 
	private int greater50lbs = 0; 
	private List<String> badIds = new Vector<String>(); 
	
	public RegexCo() {
	}
	
    public void run(String filename) throws Exception {
    	System.out.println("Regex Co");
    	if (filename != null) {
    		processFile(filename); 
    	}
    	printReport(); 
    }
    
    
    private void processFile(String filename) throws Exception {
    	System.out.println("Processing file: " + filename); 
    	BufferedReader br = new BufferedReader(new FileReader(filename));  
    	String line = null;  
    	while ((line = br.readLine()) != null) {
    		processRecord(line); 
    	} 
    	br.close();
    }

    
    private void processRecord(String line) {
    	String pkgId = "???"; 
    	try { 
    		String[] result = line.split(",");
    		pkgId = result[0].trim().toUpperCase();
    		if (pkgId.isBlank()) {
    			pkgId = "???";
    		}
    		testValidPkgId(pkgId); 
    		if (!processAddress(result[1].trim())) {
    			throw new Exception("Unknown address"); 
    		}
    		if (Float.valueOf(result[2]) > 50.0) {
    			greater50lbs++; 
    		}
    	}
    	catch (Exception e) {
    		badIds.add(pkgId); 
    	}
    }
    
    
    private void testValidPkgId(String pkgId) throws Exception {
    	String pattern = "^\\d\\d\\d-[A-Z][A-Z][A-Z]-\\d\\d\\d\\d";
    	if (!pkgId.matches(pattern)) {
    		throw new Exception("Invalid pkgId");
    	}
    }
    
    
    private boolean processAddress(String address) {
		if (address.contains("Bway")) {
			countBway++;
		}
		else if (address.contains("B'way")) {
			countBway++;
		}
		else if (address.contains("Broadway")) {
			countBway++;
		}
		else if (address.contains("West ")) {
			countWest++;
		}
		else if (address.contains("W. ")) {
			countWest++;
		}
		else if (address.contains("W ")) {
			countWest++;
		}
		else if (address.contains("East ")) {
			countEast++;
		}
		else if (address.contains("E. ")) {
			countEast++;
		}
		else if (address.contains("E ")) {
			countEast++;
		}
		else if (address.contains("Ave")) {
			countAve++;
		}
		else if (address.contains("Ave.")) {
			countAve++;
		}
		else if (address.contains("Avenue")) {
			countAve++;
		}
		else {
			return false;
		}
		return true; 
    }
    
    
    private void printReport() {
    	System.out.println("West:   " + countWest);
    	System.out.println("East:   " + countEast);
    	System.out.println("Ave:    " + countAve); 
    	System.out.println("Bway:   " + countBway);
    	System.out.println(">50lbs: " + greater50lbs); 
    	System.out.println("Ids?:   " + badIds.toString());
    }
    
    
    public static void main(String[] args) {
    	RegexCo theApp = new RegexCo();
    	String filename = null; 
    	if (args.length > 0) {
    		filename = args[0]; 
    	}
    	try { 
    		theApp.run(filename);
    	}
    	catch (Exception e) {
    		System.out.println("Something bad happened!");
    		e.printStackTrace();
    	}
    }	
	
	

}
