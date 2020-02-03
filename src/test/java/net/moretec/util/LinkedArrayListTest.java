package net.moretec.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedArrayListTest {
	private LinkedArrayList<Integer> list;

	@Before
	public void beforeEach() {
		list = new LinkedArrayList<Integer>();
	}

	@Test
	public void addAndCheckSize() {
		assertEquals("New lists should be empty.", 0, list.size());

		for (int i = 1; i <= 100; i++) {
			list.add(i);
			assertEquals("Should have the added amount of elements", i, list.size());
		}
	}

	@Test
	public void get() {
		fillList(1, 100);

		assertEquals("Element with index 0 should be 1.", Integer.valueOf(1), list.get(0));
		assertEquals("Element with index 49 should be 50.", Integer.valueOf(50), list.get(49));
		assertEquals("Element with index 10 should be 11.", Integer.valueOf(11), list.get(10));
		assertEquals("Element with index 65 should be 66.", Integer.valueOf(66), list.get(65));
		assertEquals("Element with index 93 should be 94.", Integer.valueOf(94), list.get(93));
	}

	@Test
	public void removeAndCheckSize() {
		fillList(1, 100);
		assertEquals("Filled list should have 100 elements.", 100, list.size());

		assertEquals("First element should be 1.", Integer.valueOf(1), list.get(0));
		Integer removed = list.remove(0);
		assertEquals("Remove first makes it 99 elements.", 99, list.size());
		assertEquals("Removed element should be 1.", Integer.valueOf(1), removed);
		assertEquals("New first element should be 2.", Integer.valueOf(2), list.get(0));

		boolean succeeded = list.remove(Integer.valueOf(50));
		assertTrue("Should have succeeded in removing 50.", succeeded);
		assertEquals("Removing 50 makes it 98 elements.", 98, list.size());
		assertEquals("New element at 50's index (48) should be 51.", Integer.valueOf(51), list.get(48));
	}

	private void fillList(int from, int to) {
		for (int i = from; i <= to; i++) {
			list.add(i);
		}
	}
}