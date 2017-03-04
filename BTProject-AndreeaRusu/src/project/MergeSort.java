package project;

import java.util.ArrayList;

/*
 * I took it from this website but I adapted it to my code
 * http://www.codexpedia.com/java/java-merge-sort-implementation/
 */

/**
 * Apply merge sort on elements in the array list of routers
 */
public class MergeSort {

	/**
	 * 
	 * @param routers
	 *            ArrayList of routers
	 * @param check
	 *            if true sort by hostname, otherwise sort by IP Address
	 * @return sorted arrayList of routers
	 */
	public static ArrayList<Router> mergeSort(ArrayList<Router> routers, boolean check) {
		ArrayList<Router> left = new ArrayList<>();
		ArrayList<Router> right = new ArrayList<>();
		int center;

		if (routers.size() <= 1) {
			return routers;
		} else {
			center = routers.size() / 2;
			// copy the left half of whole into the left.
			for (int i = 0; i < center; i++) {
				left.add(routers.get(i));
			}

			// copy the right half of whole into the new arraylist.
			for (int i = center; i < routers.size(); i++) {
				right.add(routers.get(i));
			}

			// Sort the left and right halves of the arraylist.
			left = mergeSort(left, check);
			right = mergeSort(right, check);

			// Merge the results back together.
			merge(left, right, routers, check);
		}
		return routers;
	}

	/**
	 * Merge 2 sorted array lists
	 * @param left the left arrayList
	 * @param right the right arrayList
	 * @param routers arrayList of routers
	 * @param check if true sort by hostname, otherwise sort by IP Address
	 */
	private static void merge(ArrayList<Router> left, ArrayList<Router> right, ArrayList<Router> routers,
			boolean check) {
		int leftIndex = 0;
		int rightIndex = 0;
		int wholeIndex = 0;

		// As long as neither the left nor the right ArrayList has
		// been used up, keep taking the smaller of left.get(leftIndex)
		// or right.get(rightIndex) and adding it at both.get(bothIndex).
		while (leftIndex < left.size() && rightIndex < right.size()) {
			String elem1 = left.get(leftIndex).getElement(check).toLowerCase();
			String elem2 = right.get(rightIndex).getElement(check).toLowerCase();
			if ((elem1.compareTo(elem2)) < 0) {
				routers.set(wholeIndex, left.get(leftIndex));
				leftIndex++;
			} else {
				routers.set(wholeIndex, right.get(rightIndex));
				rightIndex++;
			}
			wholeIndex++;
		}

		ArrayList<Router> rest;
		int restIndex;
		if (leftIndex >= left.size()) {
			// The left ArrayList has been use up...
			rest = right;
			restIndex = rightIndex;
		} else {
			// The right ArrayList has been used up...
			rest = left;
			restIndex = leftIndex;
		}

		// Copy the rest of whichever ArrayList (left or right) was not used up.
		for (int i = restIndex; i < rest.size(); i++) {
			routers.set(wholeIndex, rest.get(i));
			wholeIndex++;
		}
	}

}