package com.sega.check_lunchbox.tools.struc;

public class struc_Boxlunch
{
	 public int total;
	 String uni, sport, branch, mod;
	 
	 public struc_Boxlunch()
	 {
		 total = 0;
		 uni = sport = branch = mod;
	 }
	 
	 public struc_Boxlunch(int total, String uni, String sport, String branch, String mod)
	 {
		 this.total = total;
		 this.uni = uni;
		 this.sport = sport;
		 this.branch = branch;
		 this.mod = mod;
	 }

}
