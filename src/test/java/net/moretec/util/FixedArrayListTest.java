package net.moretec.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FixedArrayListTest {
	private static final int listSize = 10;
	private FixedArrayList<Character> list;

	@Before
	public void beforeEach() {
		list = new FixedArrayList<>(listSize);
	}

	@Test
	public void canAdd() {
		for (int i = 0; i < list.maxSize(); i++) {
			char addedElement = (char)('a' + i);
			boolean success = list.add(addedElement);

			assertTrue("Should be able to add ten elements. (" + addedElement + ")", success);
			assertEquals("The size should have change accordingly. (" + addedElement + ")", i + 1, list.size());

			assertTrue("The newly added element should be in the list. (" + addedElement + ")", list.contains(addedElement));
			assertEquals("The last element should be the newly added element. (" + addedElement + ")", addedElement, (char)list.get(list.size() - 1));

			assertEquals("The index of the newly added element should be the size - 1. (" + addedElement + ")", list.size() - 1, list.indexOf(addedElement));
			assertEquals("The last index of the newly added element should be the size - 1. (" + addedElement + ")", list.size() - 1, list.lastIndexOf(addedElement));
		}
	}

	@Test
	public void canRemove() {
		list.add('a');
		list.add('b');
		list.add('c');
		assertEquals("Add three element, and the list size should be three.", 3, list.size());

		list.remove(list.size() - 1);
		assertEquals("Remove the last, and the size should be two.", 2, list.size());

		list.add('d');
		list.remove(0);
		assertEquals("Add one and remove the first, and the size should still be two.", 2, list.size());
	}

	@Test
	public void canGoOverEdge() {
		for (int i = 0; i < list.maxSize(); i++) {
			list.add((char)('a' + i));
		}
		assertEquals("Add ten element, and the list size should be ten.", 10, list.size());

		list.remove(0);
		assertEquals("Remove the first element, and the size should be nine.", 9, list.size());

		list.add('k');
		assertEquals("Adding one element should go over the edge, but it should sill work.", 10, list.size());

		list.remove(0);
		list.remove(0);
		assertEquals("Remove the first element twice, and the size should be eight.", 8, list.size());

		list.add('l');
		list.add('m');
		assertEquals("Adding two element over the edge, and the size should be ten.", 10, list.size());

		for (int i = 0; i < list.size(); i++) {
			char element = (char)('d' + i);
			assertEquals("Making sure all the elements are where they should be. (" + element + ")", element, (char)list.get(i));
		}
	}

	@Test
	public void canAddInTheMiddle() {
		for (int i = 0; i < list.maxSize() - 1; i++) {
			list.add((char)('a' + i));
		}
		assertEquals("Add nine element, and the list size should be nine.", 9, list.size());

		list.add(4, 'j');
		assertEquals("Add 'j' at the fifth position, and the size should be ten.", 10, list.size());
		assertEquals("The fifth element should now be 'j'.", 'j', (char)list.get(4));
	}

	@Test
	public void canRemoveInTheMiddle() {
		for (int i = 0; i < list.maxSize(); i++) {
			list.add((char)('a' + i));
		}
		assertEquals("Add ten element, and the list size should be ten.", 10, list.size());

		list.remove(4);
		assertEquals("Remove the fifth element, and the size should be nine.", 9, list.size());
		assertEquals("The fifth element should now be 'f'.", 'f', (char)list.get(4));
	}
}