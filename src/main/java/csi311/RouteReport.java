package csi311;

import java.util.List;
import java.util.Vector;

/* 
 * Report bean.
 */
public class RouteReport {
	
	private int countWest    = 0; 
	private int countEast    = 0; 
	private int countAve     = 0;
	private int countBway    = 0; 
	private int greater50lbs = 0; 
	private List<String> badIds = new Vector<String>();	
	
	public int getCountWest() {
		return countWest;
	}
	public void incCountWest() {
		this.countWest++;
	}
	public int getCountEast() {
		return countEast;
	}
	public void incCountEast() {
		this.countEast++;
	}
	public int getCountAve() {
		return countAve;
	}
	public void incCountAve() {
		this.countAve++;
	}
	public int getCountBway() {
		return countBway;
	}
	public void incCountBway() {
		this.countBway++;
	}
	public int getGreater50lbs() {
		return greater50lbs;
	}
	public void incGreater50lbs() {
		this.greater50lbs++;
	}
	public List<String> getBadIds() {
		return badIds;
	}
	public void addBadId(String badId) {
		this.badIds.add(badId);
	}

}
