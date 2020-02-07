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
	public void addToFullBlocks() {
		fillList(1, 70);

		assertEquals("The size should be 70.", 70, list.size());
		assertEquals("Element at last index should be 70.", Integer.valueOf(70), list.get(list.size() - 1));
		try {
			Integer ignored = list.get(list.size());
			fail("Should not be able to get an element at index =list.size().");
		} catch (Exception ignored) { }
		list.add(list.size(), -10);
		assertEquals("The size should be 71.", 71, list.size());
		assertEquals("After adding -10 at the last index, index 70 should be -10.", Integer.valueOf(-10), list.get(70));
	}

	@Test
	public void addInTheMiddleToFullBlocks() {
		fillList(1, 70);

		assertEquals("The size should be 70.", 70, list.size());
		assertEquals("Element with index 49 should be 50.", Integer.valueOf(50), list.get(49));
		assertEquals("Element with index 50 should be 51.", Integer.valueOf(51), list.get(50));
		list.add(49, -10);
		assertEquals("The size should be 71.", 71, list.size());
		assertEquals("After adding -10 at index 49, index 49 should be -10.", Integer.valueOf(-10), list.get(49));
		assertEquals("After adding -10 at index 49, index 50 should be 50.", Integer.valueOf(50), list.get(50));
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