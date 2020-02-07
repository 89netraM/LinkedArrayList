package net.moretec.util;

import java.util.*;


public class LinkedArrayList<T> implements List<T> {
	private static final int defaultInitialCapacity = 10;

	private LinkedArrayBlock<T> first;
	private LinkedArrayBlock<T> last;
	private int blockCount = 1;
	private int size = 0;

	public LinkedArrayList() {
		this(defaultInitialCapacity);
	}
	public LinkedArrayList(int initialCapacity) {
		first = last = new LinkedArrayBlock<>(new FixedArrayList<>(initialCapacity));
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		LinkedArrayBlock<T> block = first;
		while (block != null) {
			if (block.getArray().contains(o)) {
				return true;
			}

			block = block.getNext();
		}

		return false;
	}

	@Override
	public boolean add(T t) {
		FixedArrayList<T> arrayList = last.getArray();

		if (arrayList.isFull()) {
			addNewBlock();
			arrayList = last.getArray();
		}

		arrayList.add(t);

		size++;
		return true;
	}

	private void addNewBlock() {
		FixedArrayList<T> arrayList = new FixedArrayList<T>(last.getArray().size() * 2);
		LinkedArrayBlock<T> newLast = new LinkedArrayBlock<T>(arrayList);
		last.setNext(newLast);
		newLast.setPrevious(last);
		last = newLast;

		blockCount++;
	}

	@Override
	public boolean remove(Object o) {
		LinkedArrayBlock<T> block = first;

		while (block != null) { // Loop for finding and removing the specified element
			FixedArrayList<T> current = block.getArray();

			if (current.remove(o)) {
				FixedArrayList<T> previous = current;

				block = block.getNext();
				while (block != null) { // Loop for moving back all elements from coming blocks
					current = block.getArray();

					previous.add(current.remove(0));

					block = block.getNext();
				}

				removeBlock();
				size--;
				return true;
			}

			block = block.getNext();
		}

		return false;
	}

	private int getHalfBlockSize() {
		int halfSize = 0;
		int blockSize = first.getArray().size();
		for (int i = 1; i < blockCount / 2; i++) {
			halfSize += blockSize;
			blockSize *= 2;
		}

		return halfSize;
	}

	@Override
	public T get(int index) {
		if (index < 0 || size() <= index) {
			throw new IndexOutOfBoundsException();
		}
		else {
			if (index < getHalfBlockSize()) {
				LinkedArrayBlock<T> block = first;
				while (block != null) {
					FixedArrayList<T> arrayList = block.getArray();

					if (index < arrayList.size()) {
						return arrayList.get(index);
					}
					else {
						index -= arrayList.size();
					}

					block = block.getNext();
				}

				// We shouldn't be able to get here,
				// but if we do the index is too big.
				throw new IndexOutOfBoundsException();
			}
			else {
				index = index - size();

				LinkedArrayBlock<T> block = last;
				while (block != null) {
					FixedArrayList<T> arrayList = block.getArray();

					index += arrayList.size();
					if (index >= 0) {
						return arrayList.get(index);
					}

					block = block.getPrevious();
				}

				// We shouldn't be able to get here,
				// but if we do the index is too small.
				throw new IndexOutOfBoundsException();
			}
		}
	}

	@Override
	public T set(int index, T element) {
		if (index < 0 || size() < index) {
			throw new IndexOutOfBoundsException();
		}
		else {
			if (index < getHalfBlockSize()) {
				LinkedArrayBlock<T> block = first;
				while (block != null) {
					FixedArrayList<T> arrayList = block.getArray();

					if (index < arrayList.size()) {
						return arrayList.set(index, element);
					}
					else {
						index -= arrayList.size();
					}

					block = block.getNext();
				}

				// We shouldn't be able to get here,
				// but if we do the index is too big.
				throw new IndexOutOfBoundsException();
			}
			else {
				index = index - size();

				LinkedArrayBlock<T> block = last;
				while (block != null) {
					FixedArrayList<T> arrayList = block.getArray();

					index += arrayList.size();
					if (index >= 0) {
						return arrayList.set(index, element);
					}

					block = block.getPrevious();
				}

				// We shouldn't be able to get here,
				// but if we do the index is too small.
				throw new IndexOutOfBoundsException();
			}
		}
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || size() < index) {
			throw new IndexOutOfBoundsException();
		}
		else {
			T previous = null;
			LinkedArrayBlock<T> block;

			if (index < getHalfBlockSize()) {
				block = first;

				while (block != null) {
					FixedArrayList<T> arrayList = block.getArray();

					if (index < arrayList.size()) {
						if (arrayList.isFull()) {
							previous = arrayList.remove(arrayList.size() - 1);
						}

						arrayList.add(index, element);
						break;
					}
					else {
						index -= arrayList.size();
					}

					block = block.getNext();
				}
			}
			else {
				index = index - size();
				block = last;

				while (block != null) {
					FixedArrayList<T> arrayList = block.getArray();

					index += arrayList.size();
					if (index < arrayList.size()) {
						if (arrayList.isFull()) {
							previous = arrayList.remove(arrayList.size() - 1);
						}

						arrayList.add(index, element);
						break;
					}

					block = block.getPrevious();
				}
			}

			if (previous != null) {
				block = block.getNext();
				while (block != null) {
					var arrayList = block.getArray();

					if (arrayList.isFull()) {
						T next = arrayList.remove(arrayList.size() - 1);
						arrayList.add(0, previous);
						previous = next;

						block = block.getNext();
					}
					else {
						arrayList.add(0, previous);
						previous = null;
					}
				}

				if (previous != null) {
					addNewBlock();
					last.getArray().add(previous);
				}
			}
			else {
				addNewBlock();
				last.getArray().add(element);
			}

			size++;
		}
	}

	@Override
	public T remove(int index) {
		if (index < 0 || size() <= index) {
			throw new IndexOutOfBoundsException();
		}
		else {
			LinkedArrayBlock<T> block;
			FixedArrayList<T> previous = null;
			T removed = null;

			if (index < getHalfBlockSize()) {
				block = first;

				while (block != null) {
					previous = block.getArray();

					if (index < previous.size()) {
						removed = previous.remove(index);
						break;
					}
					else {
						index -= previous.size();
					}

					block = block.getNext();
				}
			}
			else {
				index = index - size();
				block = last;

				while (block != null) {
					previous = block.getArray();

					index += previous.size();
					if (index < previous.size()) {

						removed = previous.remove(index);
						break;
					}

					block = block.getPrevious();
				}
			}

			if (previous != null && removed != null) {
				block = block.getNext();
				while (block != null) {
					FixedArrayList<T> arrayList = block.getArray();

					if (arrayList.size() > 0) {
						previous.add(arrayList.remove(0));
					}

					previous = arrayList;
					block = block.getNext();
				}

				removeBlock();

				size--;
				return removed;
			}
			else {
				// We shouldn't be able to get here, but if
				// we do the index is too big or too small.
				throw new IndexOutOfBoundsException();
			}
		}
	}

	/**
	 * Removes the last block if necessary.
	 */
	private void removeBlock() {
		if (last.getArray().isEmpty()) {
			last = last.getPrevious();
			last.setNext(null);
			blockCount--;
		}
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		LinkedArrayBlock<T> block = first;
		while (block != null) {
			FixedArrayList<T> arrayList = block.getArray();

			int currentIndex = arrayList.indexOf(o);
			if (currentIndex != -1) {
				return index + currentIndex;
			}
			else {
				index += arrayList.size();
			}

			block = block.getNext();
		}

		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		LinkedArrayBlock<T> block = last;

		int index = size();
		while (block != null) {
			FixedArrayList<T> arrayList = block.getArray();

			index -= arrayList.size();
			int currentIndex = arrayList.lastIndexOf(o);
			if (currentIndex != -1) {
				return index + currentIndex;
			}

			block = block.getPrevious();
		}

		return -1;
	}

	private static class LinkedArrayBlock<T> {
		private LinkedArrayBlock<T> previous = null;
		private final FixedArrayList<T> array;
		private LinkedArrayBlock<T> next = null;

		private LinkedArrayBlock(FixedArrayList<T> array) {
			this.array = array;
		}

		private LinkedArrayBlock<T> getPrevious() {
			return previous;
		}
		private void setPrevious(LinkedArrayBlock<T> previous) {
			this.previous = previous;
		}
		private boolean hasPrevious() {
			return previous != null;
		}

		private FixedArrayList<T> getArray() {
			return array;
		}

		private LinkedArrayBlock<T> getNext() {
			return next;
		}
		private void setNext(LinkedArrayBlock<T> next) {
			this.next = next;
		}
		private boolean hasNext() {
			return next != null;
		}
	}

	// Unimplemented interface stuff bellow

	@Override
	public Iterator<T> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator() {
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return null;
	}
}