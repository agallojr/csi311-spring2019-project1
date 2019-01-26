package csi311;

import java.io.BufferedReader;
import java.io.FileReader; 

/*
 * Accept a command line filename, process lines in the file as packages to be delivered
 * in the City.  Validate each line, and bulk sort them into streets / avenues / etc by their 
 * address.  Produce a summary report of the results.  
 */
public class RegexCo {

	// constructor 
	public RegexCo() {
	}
	
	
	// primary action method for this class - process the file and print a report 
    public void run(String filename) throws Exception {
    	System.out.println("Regex Co");
    	if (filename != null) {
    		printReport(processFile(filename)); 
    	}
    }
    
    
    // open the filename and iterate over the lines
    private RouteReport processFile(String filename) throws Exception {
    	System.out.println("Processing file: " + filename); 
    	BufferedReader br = new BufferedReader(new FileReader(filename));  
    	String line = null;  
    	RouteReport routeReport = new RouteReport(); 
    	while ((line = br.readLine()) != null) {
    		// process a line in the order file - any given line might fail, yet the 
    		// processing continues for *all* lines in the file 
    		processRecord(line, routeReport); 
    	} 
    	br.close();
    	return routeReport; 
    }

    
    // process a single line as a package record 
    private void processRecord(String line, RouteReport routeReport) {
    	// assume a bad package id until we have a valid one 
    	String pkgId = "???";   
    	try { 
    		// comma is the line delimeter 
    		String[] result = line.split(",");
    		// the package id should be the first field - get it and validate it 
    		pkgId = result[0].trim().toUpperCase();
    		if (pkgId.isBlank()) {
    			pkgId = "???";
    		}
    		testValidPkgId(pkgId);  // throws exception if invalid package id 
    		// sort the package into the right bucket, throw if unknown
    		// field 1 is the address 
    		processAddress(result[1].trim(), routeReport);
    		// field 2 is the weight - check if its over 50 lbs, if so, notate that
    		if (Float.valueOf(result[2]) > 50.0) {
    			routeReport.incGreater50lbs(); 
    		}
    	}
    	catch (Exception e) {
    		// if any part of processing this line fails, add it to the list of bad ones
    		routeReport.addBadId(pkgId); 
    	}
    }
    
    
    // is the package id valid / well-formed?  throws exception if not 
    private void testValidPkgId(String pkgId) throws Exception {
    	// well-formed 
    	String pattern = "^\\d\\d\\d-[A-Z][A-Z][A-Z]-\\d\\d\\d\\d";
    	if (!pkgId.matches(pattern)) {
    		throw new Exception("Invalid pkgId");
    	}
    }
    
    
    // for a given address, determine if it belongs to broadway, west streets, east streets, 
    // of avenues, or none of the above  
    private void processAddress(String address, RouteReport routeReport) throws Exception {
		if (address.contains("Bway") || address.contains("B'way") || address.contains("Broadway")) {
			routeReport.incCountBway();
		}
		else if (address.contains("West ") || address.contains("W. ") || address.contains("W ")) {
			routeReport.incCountWest();
		}
		else if (address.contains("East ") || address.contains("E. ") || address.contains("E ")) {
			routeReport.incCountEast();
		}
		else if (address.contains("Ave") || address.contains("Ave.") || address.contains("Avenue")) {
			routeReport.incCountAve();
		}
		else {
			throw new Exception("Invalid address");
		}
    }
    
    
    // print the report to stdout
    private void printReport(RouteReport routeReport) {
    	System.out.println("West:   " + routeReport.getCountWest());
    	System.out.println("East:   " + routeReport.getCountEast());
    	System.out.println("Ave:    " + routeReport.getCountAve()); 
    	System.out.println("Bway:   " + routeReport.getCountBway());
    	System.out.println(">50lbs: " + routeReport.getGreater50lbs()); 
    	System.out.println("Ids?:   " + routeReport.getBadIds().toString());
    }
    
    
    // the main() to be invoked in the executable jar (note this in pom.xml).
    public static void main(String[] args) {
    	RegexCo theApp = new RegexCo();
    	String filename = null; 
    	if (args.length > 0) {
    		// if there is a command line arg, assume its a filename
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
