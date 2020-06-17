package SalaryReportStuff;
// Carl Mastny
// ITPRG247
// Lab 3 - 12.25 p640
// This program creates a class Staff which extends employee to add first name last name and position modifiers
// Sources: https://stackoverflow.com/questions/4898263/multiple-delimiters-in-scanner-class-of-java
//          https://www.geeksforgeeks.org/list-interface-java-examples/
//          https://www.leveluplunch.com/java/examples/remove-newline-carriage-return-from-string/
//          http://tutorials.jenkov.com/java-collections/list.html#get-elements-from-a-java-list// Sources: 

import util.Employee;

public class Staff extends Employee {
	private String fname = new String();
	private String lname = new String();
	private String position = new String();
	
	public Staff(String fname, String lname, String position, double s) {
		super(s);
		this.fname = fname;
		this.lname = lname;
		this.position = position;
	}
	
	public String getFirstName() {
		return fname;
	}
	
	public String getLastName() {
		return lname;
	}
	
	public String getPosition() {
		return position;
	}
}
