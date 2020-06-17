package util;
/* Basic Employee Base Class
 * Implements a basic salary/wage earning employee
 * that is able to be sorted based upon salary/wage
 */
public class Employee {

	private static int qPart(Employee[] arr, int low, int high) {
		Employee pivot = arr[high];
		int idx = low - 1;
		for (int jdx = low; jdx < high; jdx++) {
			if (arr[jdx].salary < pivot.salary) {
				idx++;
				Employee temp = arr[idx];
				arr[idx] = arr[jdx];
				arr[jdx] = temp;
			}
		}
		Employee temp = arr[idx+1];
		arr[idx+1] = arr[high];
		arr[high] = temp;
		return idx + 1;
	}
	private static void qSort(Employee[] arr, int low, int high) {
		if (low < high) {
			int partIdx = Employee.qPart(arr,low,high);
			Employee.qSort(arr,low,partIdx-1);
			Employee.qSort(arr, partIdx+1, high);
		}
	}
	private double salary;

	/*
	 * Sorts an array of Employee objects in order of increasing salary
	 * @param emps the array of Employee objects to be sorted
	 * 
	 */
	public static void sort(Employee[] emps) {
		Employee.qSort(emps, 0, emps.length-1);
	}

	/*
	 * Creates an Employee with the specified salary
	 * @param s the salary of the the Employee
	 */
	public Employee(double s) {
		salary = s;
	}
	
	/*
	 * Returns the salary of the Employee 
	 * @return the salary of the Employee
	 */
	public double getSalary() {
		return salary;
	}
}
