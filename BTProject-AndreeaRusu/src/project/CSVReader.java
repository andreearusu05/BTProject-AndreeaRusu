package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Parse a CSV file and find routers that meet criteria
 * 
 * @author Andreea Rusu
 */

public class CSVReader {

	// all routers in the tabel
	ArrayList<Router> routers;

	public CSVReader(String csvFile) {
		this.routers = new ArrayList<>();
		read(csvFile);
	}

	/**
	 * Read the file and add all routers in the arrayList
	 * 
	 * @param csvFile
	 *            csv file to be read
	 */
	public void read(String csvFile) {
		String line = null;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			// ignore the first line with field names
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] row = line.split(",");

				// if length == 5 => there is a note
				if (row.length == 5)
					routers.add(new Router(row[0], row[1], row[2].toLowerCase(), row[3], row[4]));
				else if (row.length == 4) // no note
					routers.add(new Router(row[0], row[1], row[2].toLowerCase(), row[3], ""));
				else // blank line
					continue;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Check the file name and the path!");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Get array of all routers
	 * 
	 * @return routers
	 */
	public ArrayList<Router> getRouters() {
		return routers;
	}

	/**
	 * Return valid routers
	 * 
	 * @return valid routers
	 */
	public ArrayList<Router> findRouters() {
		// arraylist with valid routers
		ArrayList<Router> validRouters = new ArrayList<>();

		// sort routers by hostname and find routers with unique hostname
		routers = MergeSort.mergeSort(routers, true);
		for (int i = 0; i < routers.size() - 1; i++) {
			int j = i + 1;
			String hn1 = routers.get(i).getElement(true).toLowerCase();
			String hn2 = routers.get(j).getElement(true).toLowerCase();
			if (hn1.equals(hn2)) {
				// set router as not valid
				routers.get(i).setNotValid();
				while (j < routers.size() && hn1.equals(hn2)) {
					routers.get(j).setNotValid();
					j++;
					hn2 = routers.get(j).getElement(true).toLowerCase();
				}
			}
			i = j - 1;
		}

		// sort routers by IP address, check all criteria
		routers = MergeSort.mergeSort(routers, false);
		for (int i = 0; i < routers.size() - 1; i++) {
			int j = i + 1;
			String hn1 = routers.get(i).getElement(false).toLowerCase();
			String hn2 = routers.get(j).getElement(false).toLowerCase();

			if (hn1.equals(hn2)) {
				// set router as invalid
				routers.get(i).setNotValid();
				while (j < routers.size() && hn1.equals(hn2)) {
					routers.get(j).setNotValid();
					j++;
					hn2 = routers.get(j).getElement(false).toLowerCase();
				}
			}

			// if j == i + 1 => the previous if clause was false => the 2
			// routers are different;
			// thus, if the first router (with index i) meets all the other
			// criteria, we add it in our validRouters arraylist
			if (j == i + 1 && isValid(routers.get(i))) {
				validRouters.add(routers.get(i));
			}

			if (j == routers.size() - 1 && isValid(routers.get(j))) {
				validRouters.add(routers.get(j));
			}

			i = j - 1;
		}
		return validRouters;
	}

	/**
	 * Print valid routers
	 */
	public void printValidRouters() {
		ArrayList<Router> result = findRouters();
		for (Router r : result) {
			System.out.println(r);
		}
	}

	/**
	 * Check criteria: 1.all elements in fields have to be valid 2. router has
	 * unique hostname and IP Address 3. version of the router OS is 12 or above
	 * 4. patched = no
	 * 
	 * @param router
	 *            router
	 * @return true if valid, false otherwise
	 */
	public boolean isValid(Router router) {
		return validData(router) && router.isUnique() && router.getOS().compareTo("12") > 0
				&& router.getPatched().equals("no");
	}

	/**
	 * Check if all fields in a router are valid
	 * 
	 * @param router
	 *            the router
	 * @return true if all fields are valid, false otherwise
	 */
	public boolean validData(Router router) {
		return validHostname(router.getHostname()) && validIpAddress(router.getIP()) && validOSVersion(router.getOS())
				&& validPatched(router.getPatched());
	}

	/**
	 * Check if hostname has a valid format: long the entire hostname has a
	 * maximum of 255 characters, each label is 1 and 63 characters, all labels
	 * are made up of letters/digits/a hyphen, no label ends/begins with hyphen.
	 * 
	 * @param hostname
	 *            the hostname
	 * @return true if it is valid, false otherwise
	 */
	public boolean validHostname(String hostname) {
		if (hostname.length() > 255)
			return false;

		// split hostname by dot into an array of labels
		String[] hostnameArray = hostname.split("\\.");

		// iterate through labels
		for (int i = 0; i < hostnameArray.length; i++) {
			int labelLength = hostnameArray[i].length();
			if (labelLength > 63)
				return false;
			if (hostnameArray[i].charAt(0) == '-' || hostnameArray[i].charAt(labelLength - 1) == '-')
				return false;

			// iterate thorugh chars in a label
			for (int j = 0; j < labelLength; j++) {
				char ch = hostnameArray[i].charAt(j);
				// check if each char is a digit/letter/dash
				if (!Character.isDigit(ch) && !Character.isLetter(ch) && ch != '-')
					return false;
			}
		}
		return true;
	}

	/**
	 * Check if IP Addreess has a valid format: it must be made up of 4 numbers
	 * separated by dot, each number must be between 0 and 255
	 * 
	 * @param ip
	 *            the IP Address
	 * @return true if it is valid, false otherwise
	 */
	public boolean validIpAddress(String ip) {
		String[] ipArray = ip.split("\\.");

		// array length must be 4
		if (ipArray.length != 4)
			return false;

		// check if each string is a number between 0 and 255
		for (int i = 0; i < ipArray.length; i++) {
			try {
				int number = Integer.parseInt(ipArray[i]);
				if (number < 0 || number > 255)
					return false;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if patched has a valid format: it is equal to either "yes" or "no"
	 * 
	 * @param patched
	 *            the patched field
	 * @return true if it is valid, false otherwise
	 */
	public boolean validPatched(String patched) {
		return (patched.equals("yes") || patched.equals("no"));
	}

	/**
	 * Check if OS Version had a valid format: it is a sequence of numbers
	 * separated by dot
	 * 
	 * @param os
	 *            the OS Version
	 * @return true if it is valid, false otherwise
	 */
	public boolean validOSVersion(String os) {
		String[] osArray = os.split("\\.");
		for (int i = 0; i < osArray.length; i++) {
			try {
				Integer.parseInt(osArray[i]);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
}