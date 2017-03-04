package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import project.CSVReader;
import project.Router;

public class CSVReaderTest {
	CSVReader reader = null;

	private void assertArraysEqual(ArrayList<Router> expected, ArrayList<Router> outcome) {
		assertEquals(expected.size(), outcome.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).getHostname(), outcome.get(i).getHostname());
			assertEquals(expected.get(i).getIP(), outcome.get(i).getIP());
			assertEquals(expected.get(i).getOS(), outcome.get(i).getOS());
			assertEquals(expected.get(i).getPatched(), outcome.get(i).getPatched());
			assertEquals(expected.get(i).getNote(), outcome.get(i).getNote());
		}
	}

	@Before
	public void initialize() {
		reader = new CSVReader("files/sample2.csv");

	}

	@Test
	public void testRead() {
		ArrayList<Router> outcome = reader.getRouters();
		ArrayList<Router> expected = new ArrayList<>();
		expected.add(new Router("f.example.com", "1.1.1.7", "no", "12.2", ""));
		expected.add(
				new Router("g.example.com", "1.1.1.6", "no", "15", "Guarded by sharks with lasers on their heads"));
		expected.add(
				new Router("h.example.com", "1.1.1.6", "no", "15", "Guarded by sharks with lasers on their heads"));
		assertArraysEqual(expected, outcome);
	}

	@Test
	public void testValidHostname() {
		assertTrue(reader.validHostname("example12.com"));
		assertFalse(reader.validHostname("example-.com"));
		assertFalse(reader.validHostname("example.-com"));
		assertFalse(reader.validHostname("example.c@om"));
	}

	@Test
	public void testValidIP() {
		assertTrue(reader.validIpAddress("12.12.12.12"));
		assertFalse(reader.validIpAddress("12.12.12"));
		assertFalse(reader.validIpAddress("ip"));
		assertFalse(reader.validIpAddress("12.12.12.400"));
	}

	@Test
	public void testPatched() {
		assertTrue(reader.validPatched("no"));
		assertTrue(reader.validPatched("yes"));
		assertFalse(reader.validPatched("example"));
	}

	@Test
	public void testOS() {
		assertTrue(reader.validOSVersion("12.2.3"));
		assertFalse(reader.validOSVersion("12.a"));
		assertFalse(reader.validOSVersion("exmple"));
	}

	@Test
	public void testFindRouters() {
		// test for sample 2.csv
		ArrayList<Router> outcome = reader.findRouters();
		ArrayList<Router> expected = new ArrayList<>();
		expected.add(new Router("f.example.com", "1.1.1.7", "no", "12.2", ""));
		assertArraysEqual(expected, outcome);

		// test for sample.csv
		CSVReader reader1 = new CSVReader("files/sample.csv");
		outcome = reader1.findRouters();
		expected = new ArrayList<>();
		expected.add(new Router("b.example.com", "1.1.1.2", "no", "13", "Behind the other routers so no one sees it"));
		expected.add(
				new Router("J.example.com", "1.1.1.62", "no", "15", "Guarded by sharks with lasers on their heads"));
		expected.add(new Router("f.example.com", "1.1.1.7", "no", "12.2", ""));
		expected.add(new Router("i.example.com", "1.1.1.71", "no", "12.2", ""));
		assertArraysEqual(expected, outcome);

		// test for sample1.csv
		CSVReader reader2 = new CSVReader("files/sample1.csv");
		outcome = reader2.findRouters();
		assertEquals(new ArrayList<Router>(), outcome);
	}
}
