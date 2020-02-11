package net.moretec.util;

import java.util.Objects;
import java.util.function.Predicate;

class FixedArrayList<T> {
	private final T[] array;
	private int size = 0;
	private int front = 0;

	@SuppressWarnings("unchecked")
	FixedArrayList(int maxSize) {
		array = (T[])new Object[maxSize];
	}

	int size() {
		return size;
	}
	int maxSize() {
		return array.length;
	}

	boolean isEmpty() {
		return size() == 0;
	}
	boolean isFull() {
		return size() == maxSize();
	}

	boolean contains(Object o) {
		return indexOf(o) != -1;
	}

	boolean add(T t) throws IndexOutOfBoundsException {
		try {
			return add(size(), t);
		}
		catch (IndexOutOfBoundsException ignored) {
			return false;
		}
	}

	boolean remove(Object o) {
		int index = indexOf(o);
		if (index != -1) {
			remove(index);

			return true;
		}
		else {
			return false;
		}
	}

	T get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || size() <= index) {
			throw new IndexOutOfBoundsException();
		}
		else {
			return array[positionFromStart(index)];
		}
	}

	T set(int index, T element) {
		if (index < 0 || size() <= index) {
			throw new IndexOutOfBoundsException();
		}
		else {
			T temp = array[positionFromStart(index)];
			array[positionFromStart(index)] = element;
			return temp;
		}
	}

	private int positionFromStart(int offset) {
		return ((front + offset) % maxSize() + maxSize()) % maxSize();
	}

	boolean add(int index, T element) throws IndexOutOfBoundsException {
		if (index < 0 || size() < index) {
			throw new IndexOutOfBoundsException();
		}
		else {
			if (!isFull()) {
				open(index);
				set(index, element);

				return true;
			}
			else {
				return false;
			}
		}
	}

	T remove(int index) {
		if (index < 0 || size() <= index) {
			throw new IndexOutOfBoundsException();
		}
		else {
			T temp = get(index);
			close(index);
			return temp;
		}
	}

	private void open(int index) {
		if (index < size() / 2) {
			front = (front - 1) % maxSize();
			size++;
			T previous = null;
			for (int i = index; i >= 0; i--) {
				previous = set(i, previous);
			}
		}
		else {
			size++;
			T previous = null;
			for (int i = index; i < size(); i++) {
				previous = set(i, previous);
			}
		}
	}
	private void close(int index) {
		if (index < size() / 2) {
			T previous = null;
			for (int i = 0; i <= index; i++) {
				previous = set(i, previous);
			}
			front = (front + 1) % maxSize();
			size--;
		}
		else {
			T previous = null;
			for (int i = size() - 1; i >= index; i--) {
				previous = set(i, previous);
			}
			size--;
		}
	}

	int indexOf(Object o) {
		Predicate<Object> isEqual = o == null ? Objects::isNull : o::equals;
		for (int i = 0; i < size(); i++) {
			if (isEqual.test(get(i))) {
				return i;
			}
		}

		return -1;
	}

	int lastIndexOf(Object o) {
		Predicate<Object> isEqual = o == null ? Objects::isNull : o::equals;
		for (int i = size() - 1; i >= 0; i--) {
			if (isEqual.test(get(i))) {
				return i;
			}
		}

		return -1;
	}
}