package project;

/**
 * Represent a router with all fields
 * 
 * @author Andreea Rusu
 *
 */
public class Router {
	private String hostname;
	private String ip;
	private String patched;
	private String os;
	private String note;

	// variable that shows if the routher meets criteria
	private boolean unique;

	/**
	 * @param hostname
	 *            Hostname
	 * @param ip
	 *            IP Address
	 * @param patched
	 *            Patched or not
	 * @param os
	 *            OS Version
	 * @param note
	 *            Note
	 */
	public Router(String hostname, String ip, String patched, String os, String note) {
		this.hostname = hostname;
		this.ip = ip;
		this.patched = patched;
		this.os = os;
		this.note = note;
		// we suppose that all routers meet have unique hostname and IP address
		this.unique = true;
	}

	public String toString() {
		if (!note.equals(""))
			note = " [" + note + "]";
		String s = hostname + " (" + ip + "), OS version " + os + note;
		return s;
	}

	/**
	 * Get hostname
	 * 
	 * @return hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Get IP Address
	 * 
	 * @return IP Address
	 */
	public String getIP() {
		return ip;
	}

	/**
	 * Get patched
	 * 
	 * @return patched
	 */
	public String getPatched() {
		return patched;
	}

	/**
	 * Get OS Version
	 * 
	 * @return OS Version
	 */
	public String getOS() {
		return os;
	}

	/**
	 * Get note
	 * 
	 * @return Note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Show if the router has unique hostname and IP Address
	 * 
	 * @return true if it does, false otherwise
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * Set that the router does not meed criteria
	 */
	public void setNotValid() {
		unique = false;
	}

	/**
	 * Return hostname or IP Address to be used for sorting
	 * 
	 * @param check
	 *            true or false
	 * @return host name if parameter is true or IP Address if parameter is
	 *         false
	 */
	public String getElement(boolean check) {
		return (check ? hostname : ip);
	}

}
