// Carl Mastny
// ITPRG247
// Lab 3 - 12.25 p640
// This program creates a salary report based on a CSV text file
// Sources: https://stackoverflow.com/questions/4898263/multiple-delimiters-in-scanner-class-of-java
//          https://www.geeksforgeeks.org/list-interface-java-examples/
//          https://www.leveluplunch.com/java/examples/remove-newline-carriage-return-from-string/
//          http://tutorials.jenkov.com/java-collections/list.html#get-elements-from-a-java-list/

package SalaryReportStuff;

import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class SalaryReport {
	
	public static void generateReport(String[] file) {
		final String CSVFile = file[0];
		final String ReportFile = file[1] + "/2019_Faculty_Report.txt";
		String name = file[2];
		int staffCount = 1000;
		Date date = new Date();
		
		File inFile = new File(CSVFile);
		File outFile = new File(ReportFile);
		
		try (
			PrintWriter output = new PrintWriter(outFile);
			Scanner input = new Scanner(inFile);
		) {
			// Used what I learned to create multiple delimiters(for commas and returns) -- https://stackoverflow.com/questions/4898263/multiple-delimiters-in-scanner-class-of-java
			input.useDelimiter(",|\\r");
			
			/*
			 * Create an array of staff objects and instantiate them with the values from the data.txt file and sort the array
			 */
			Staff[] employeeData = new Staff[staffCount];
			
			for(int i = 0; i < staffCount; i++) {
				employeeData[i] = new Staff(input.next(), input.next(), input.next(), input.nextDouble());
			}
			
			Staff.sort(employeeData);
			
			/*
			 * Lists to hold a sorted array of all full staff, associate staff and assistant staff
			 */
			List<Staff> fullStaff = new ArrayList<Staff>();
			List<Staff> associateStaff = new ArrayList<Staff>();
			List<Staff> assistantStaff = new ArrayList<Staff>();
			
			fullStaff = sortRank("full", employeeData); //public List<Staff> sortRank(String rank, Staff[] data) sortRank Takes a String specifying what rank to be sorted and an array of Staff objects. Returns a List object with staff of that rank sorted by salary
			associateStaff = sortRank("associate", employeeData);
			assistantStaff = sortRank("assistant", employeeData);
			
			/*
			 * Used what I learned to get rid of new lines in strings -- https://www.leveluplunch.com/java/examples/remove-newline-carriage-return-from-string/
			 */
			
			// String values for names of max and min employees (max is last employee in sorted array, min is first)
			// All employees
			String maxEmployee = employeeData[999].getFirstName().replaceAll("\n", "") + " " + employeeData[999].getLastName();
			String minEmployee = employeeData[0].getFirstName().replaceAll("\n", "") + " " + employeeData[0].getLastName(); 
			// full employees
			String fullMinEmployee = fullStaff.get(0).getFirstName().replaceAll("\n", "") + " " + fullStaff.get(0).getLastName();
			String fullMaxEmployee = fullStaff.get(fullStaff.size() - 1).getFirstName().replaceAll("\n", "") + " " + fullStaff.get(0).getLastName();
			//associate employees
			String associateMinEmployee = associateStaff.get(0).getFirstName().replaceAll("\n", "") + " " + associateStaff.get(0).getLastName();
			String associateMaxEmployee = associateStaff.get(associateStaff.size() - 1).getFirstName().replaceAll("\n", "") + " " + associateStaff.get(0).getLastName();
			//assistant employees
			String assistantMinEmployee = assistantStaff.get(0).getFirstName().replaceAll("\n", "") + " " + assistantStaff.get(0).getLastName();
			String assistantMaxEmployee = assistantStaff.get(assistantStaff.size() - 1).getFirstName().replaceAll("\n", "") + " " + assistantStaff.get(0).getLastName();
			
			/*
			 * Output to the 2019_Faculty_Salary_Report.txt
			 *        V        V         V        V
			 */
			
			//heading
			output.println("Faculty Salary Report (2019)");
			output.println("Prepared by: " + name);
			output.println("Prepared on: " + date.toString());
			
			// output max values
			output.println();
			output.println("Maximum Salaries: " );
			output.println("   All employees: " + employeeData[999].getSalary() + " (" + maxEmployee + ", " + employeeData[999].getPosition() + ")");
			output.println("   Full employees: " + fullStaff.get(fullStaff.size() - 1).getSalary() + " (" + fullMaxEmployee + ")");
			output.println("   Associate employees: " + associateStaff.get(associateStaff.size() - 1).getSalary() + " (" + associateMaxEmployee + ")");
			output.println("   Assistant employees: " + assistantStaff.get(assistantStaff.size() - 1).getSalary() + " (" + assistantMaxEmployee + ")");
			
			//output min values
			output.println();
			output.println("Minimum Salaries: ");
			output.println("   All employees: " + employeeData[0].getSalary() + " (" + minEmployee + ", " + employeeData[0].getPosition() + ")");
			output.println("   Full employees: " + fullStaff.get(0).getSalary() + " (" + fullMinEmployee + ")");
			// getting list size -- http://tutorials.jenkov.com/java-collections/list.html#get-elements-from-a-java-list
			output.println("   Associate employees: " + associateStaff.get(0).getSalary() + " (" + associateMinEmployee + ")");
			output.println("   Assistant employees: " + assistantStaff.get(0).getSalary() + " (" + assistantMinEmployee + ")");
			
			// output the means
			output.println();
			output.println("Mean Salaries: ");
			output.println("   All employees: " + staffMean(employeeData)); //staffMean takes an array of Staff objects and returns the average salary
			output.println("   Full employees: " + staffMeanByRank(fullStaff)); //staffMeanByRank takes a List Object filled with Staff objects and returns the average salary
			output.println("   Associate employees: " + staffMeanByRank(associateStaff));
			output.println("   Assistant employees: " + staffMeanByRank(assistantStaff));
			
			// output the median salaries
			output.println();
			output.println("Median Salaries: ");
			output.println("   All employees: " + staffMedian(employeeData)); //staffMedian takes an array of Staff objects and returns the median of the salaries
			output.println("   Full employees: " + staffMedianByRank(fullStaff)); //staffMedianByRank takes a List Object filled with Staff objects and returns the median salary
			output.println("   Associate employees: " + staffMedianByRank(associateStaff));
			output.println("   Assistant employees: " + staffMedianByRank(assistantStaff));
			
			// output the number of faculty at each rank
			output.println();
			output.println("The number of faculty: ");
			output.println("   Full: " + fullStaff.size());
			output.println("   Associate: " + associateStaff.size());
			output.println("   Assistant: " + assistantStaff.size());
			
			// output the number of faculty that are within $1000 of the mean and median salary
			output.println();
			output.println("The number of faculty within $1000 of the: ");
			output.println("   Mean salary: " + countWithinMean(employeeData)); //withinMean takes an array of Staff objects and returns the number of faculty within $1000 of mean as an int value
			output.println("   Median salary: " + countWithinMedian(employeeData)); //withinMedian takes an array of Staff objects and returns the number of faculty within $1000 of median as an int value
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
	public static List<Staff> sortRank(String rank, Staff[] data) {
		/*
		 * Used List stuff from https://www.geeksforgeeks.org/list-interface-java-examples/
		 * If rank is full associate  or assistant, go through array of Staff objects adding any full associate or 
		 * assistant objects to their relative lists
		 */
		if (rank.equals("full")) {
			
			List<Staff> fullStaff = new ArrayList<Staff> ();
			
			for (int i = 0; i < data.length; i++) {
				if (data[i].getPosition().equals("full")) {
					fullStaff.add(data[i]);
				}
			}
			
			return fullStaff;
			
		} else if (rank.equals("associate")) {
			
			List<Staff> associateStaff = new ArrayList<Staff> ();
			
			for (int i = 0; i < data.length; i++) {
				if (data[i].getPosition().equals("associate")) {
					associateStaff.add(data[i]);
				}
			}
			
			return associateStaff;
			
		} else if (rank.equals("assistant")) {
			
			List<Staff> assistantStaff = new ArrayList<Staff> ();
			
			for (int i = 0; i < data.length; i++) {
				if (data[i].getPosition().equals("assistant")) {
					assistantStaff.add(data[i]);
				}
			}
			
			return assistantStaff;
			
		}
		return null;
	}
	
	
	public static double staffMean(Staff[] data) {
		int sum = 0;
		for (int i = 0; i < data.length; i++) {
			sum += data[i].getSalary();
		}
		
		return sum / data.length;
	}

	public static double staffMeanByRank(List<Staff> rankStaff) {
		int sum = 0;
		for (int i = 0; i < rankStaff.size(); i++) {
			sum += rankStaff.get(i).getSalary();
		}
		
		return sum / rankStaff.size();
	}

	public static double staffMedian(Staff[] data) {
		return data[(data.length / 2) + 1].getSalary();
	}

	public static double staffMedianByRank(List<Staff> rankStaff) {
		return rankStaff.get(rankStaff.size() / 2 + 1).getSalary();
	}

	public static int countWithinMean(Staff[] data) {
		int count = 0;
		
		for (int i = 0; i < data.length; i++) {
			if (data[i].getSalary() <= 1000 + staffMean(data) && data[i].getSalary() >= 1000 - staffMean(data)) {
				count++;
			}
		}
		
		return count;
	}

	public static int countWithinMedian(Staff[] data) {
		int count = 0;
		
		for (int i = 0; i < data.length; i++) {
			if (data[i].getSalary() <= 1000 + staffMedian(data) && data[i].getSalary() >= 1000 - staffMedian(data)) {
				count++;
			}
		}
		
		return count;
	}
}
