package ma.myway.graph;

/***********************************************************************
 * An implementation of a priority queue backed by a Fibonacci heap,
 * as described by Fredman and Tarjan.  Fibonacci heaps are interesting
 * theoretically because they have asymptotically good runtime guarantees
 * for many operations.  In particular, insert, peek, and decrease-key all
 * run in amortized O(1) time.  dequeueMin and delete each run in amortized
 * O(lg n) time.  This allows algorithms that rely heavily on decrease-key
 * to gain significant performance boosts.  For example, Dijkstra's algorithm
 * for single-source shortest paths can be shown to run in O(m + n lg n) using
 * a Fibonacci heap, compared to O(m lg n) using a standard binary or binomial
 * heap.
 *
 * Internally, a Fibonacci heap is represented as a circular, doubly-linked
 * list of trees obeying the min-heap property.  Each node stores pointers
 * to its parent (if any) and some arbitrary child.  Additionally, every
 * node stores its degree (the number of children it has) and whether it
 * is a "marked" node.  Finally, each Fibonacci heap stores a pointer to
 * the tree with the minimum value.
 *
 * To insert a node into a Fibonacci heap, a singleton tree is created and
 * merged into the rest of the trees.  The merge operation works by simply
 * splicing together the doubly-linked lists of the two trees, then updating
 * the min pointer to be the smaller of the minima of the two heaps.  Peeking
 * at the smallest element can therefore be accomplished by just looking at
 * the min element.  All of these operations complete in O(1) time.
 *
 * The tricky operations are dequeueMin and decreaseKey.  dequeueMin works
 * by removing the root of the tree containing the smallest element, then
 * merging its children with the topmost roots.  Then, the roots are scanned
 * and merged so that there is only one tree of each degree in the root list.
 * This works by maintaining a dynamic array of trees, each initially null,
 * pointing to the roots of trees of each dimension.  The list is then scanned
 * and this array is populated.  Whenever a conflict is discovered, the
 * appropriate trees are merged together until no more conflicts exist.  The
 * resulting trees are then put into the root list.  A clever analysis using
 * the potential method can be used to show that the amortized cost of this
 * operation is O(lg n), see "Introduction to Algorithms, Second Edition" by
 * Cormen, Rivest, Leiserson, and Stein for more details.
 *
 * The other hard operation is decreaseKey, which works as follows.  First, we
 * update the key of the node to be the new value.  If this leaves the node
 * smaller than its parent, we're done.  Otherwise, we cut the node from its
 * parent, add it as a root, and then mark its parent.  If the parent was
 * already marked, we cut that node as well, recursively mark its parent,
 * and continue this process.  This can be shown to run in O(1) amortized time
 * using yet another clever potential function.  Finally, given this function,
 * we can implement delete by decreasing a key to -\infty, then calling
 * dequeueMin to extract it.
 * 
 * source: https://keithschwarz.com/interesting/code/?dir=fibonacci-heap
 */

// For ArrayList
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class representing a Fibonacci heap.
 *
 * @param T The type of elements to store in the heap.
 */
public final class FibonacciHeap<T> {
	/*
	 * In order for all of the Fibonacci heap operations to complete in O(1),
	 * clients need to have O(1) access to any element in the heap. We make this
	 * work by having each insertion operation produce a handle to the node in the
	 * tree. In actuality, this handle is the node itself, but we guard against
	 * external modification by marking the internal fields private.
	 */
	public static final class Entry<T> {
		private int mDegree = 0; // Number of children
		private boolean mIsMarked = false; // Whether this node is marked

		private Entry<T> mNext; // Next and previous elements in the list
		private Entry<T> mPrev;

		private Entry<T> mParent; // Parent in the tree, if any.

		private Entry<T> mChild; // Child node, if any.

		public T mElem; // Element being stored here
		private double mPriority; // Its priority

		/**
		 * Returns the element represented by this heap entry.
		 *
		 * @return The element represented by this heap entry.
		 */
		public T getValue() {
			return mElem;
		}

		/**
		 * Sets the element associated with this heap entry.
		 *
		 * @param value The element to associate with this heap entry.
		 */
		public void setValue(T value) {
			mElem = value;
		}

		/**
		 * Returns the priority of this element.
		 *
		 * @return The priority of this element.
		 */
		public double getPriority() {
			return mPriority;
		}

		/**
		 * Constructs a new Entry that holds the given element with the indicated
		 * priority.
		 *
		 * @param elem     The element stored in this node.
		 * @param priority The priority of this element.
		 */
		private Entry(T elem, double priority) {
			mNext = mPrev = this;
			mElem = elem;
			mPriority = priority;
		}
	}

	/* Pointer to the minimum element in the heap. */
	private Entry<T> mMin = null;

	/* Cached size of the heap, so we don't have to recompute this explicitly. */
	private int mSize = 0;

	/**
	 * Inserts the specified element into the Fibonacci heap with the specified
	 * priority. Its priority must be a valid double, so you cannot set the priority
	 * to NaN.
	 *
	 * @param value    The value to insert.
	 * @param priority Its priority, which must be valid.
	 * @return An Entry representing that element in the tree.
	 */
	public Entry<T> enqueue(T value, double priority) {
		checkPriority(priority);
		Entry<T> result = new Entry<T>(value, priority);
		mMin = mergeLists(mMin, result);
		++mSize;
		return result;
	}

	/**
	 * Returns an Entry object corresponding to the minimum element of the Fibonacci
	 * heap, throwing a NoSuchElementException if the heap is empty.
	 *
	 * @return The smallest element of the heap.
	 * @throws NoSuchElementException If the heap is empty.
	 */
	public Entry<T> min() {
		if (isEmpty())
			throw new NoSuchElementException("Heap is empty.");
		return mMin;
	}

	/**
	 * Returns whether the heap is empty.
	 *
	 * @return Whether the heap is empty.
	 */
	public boolean isEmpty() {
		return mMin == null;
	}

	/**
	 * Returns the number of elements in the heap.
	 *
	 * @return The number of elements in the heap.
	 */
	public int size() {
		return mSize;
	}

	/**
	 * Given two Fibonacci heaps, returns a new Fibonacci heap that contains all of
	 * the elements of the two heaps. Each of the input heaps is destructively
	 * modified by having all its elements removed. You can continue to use those
	 * heaps, but be aware that they will be empty after this call completes.
	 *
	 * @param one The first Fibonacci heap to merge.
	 * @param two The second Fibonacci heap to merge.
	 * @return A new FibonacciHeap containing all of the elements of both heaps.
	 */
	public static <T> FibonacciHeap<T> merge(FibonacciHeap<T> one, FibonacciHeap<T> two) {
		FibonacciHeap<T> result = new FibonacciHeap<T>();

		result.mMin = mergeLists(one.mMin, two.mMin);

		result.mSize = one.mSize + two.mSize;

		one.mSize = two.mSize = 0;
		one.mMin = null;
		two.mMin = null;

		return result;
	}

	/**
	 * Dequeues and returns the minimum element of the Fibonacci heap. If the heap
	 * is empty, this throws a NoSuchElementException.
	 *
	 * @return The smallest element of the Fibonacci heap.
	 * @throws NoSuchElementException If the heap is empty.
	 */
	public Entry<T> dequeueMin() {
		if (isEmpty())
			throw new NoSuchElementException("Heap is empty.");
		--mSize;

		Entry<T> minElem = mMin;

		if (mMin.mNext == mMin) { // Case one
			mMin = null;
		} else { // Case two
			mMin.mPrev.mNext = mMin.mNext;
			mMin.mNext.mPrev = mMin.mPrev;
			mMin = mMin.mNext; // Arbitrary element of the root list.
		}

		if (minElem.mChild != null) {
			Entry<?> curr = minElem.mChild;
			do {
				curr.mParent = null;

				curr = curr.mNext;
			} while (curr != minElem.mChild);
		}

		mMin = mergeLists(mMin, minElem.mChild);

		if (mMin == null)
			return minElem;

		List<Entry<T>> treeTable = new ArrayList<Entry<T>>();

		List<Entry<T>> toVisit = new ArrayList<Entry<T>>();

		for (Entry<T> curr = mMin; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.mNext)
			toVisit.add(curr);

		for (Entry<T> curr : toVisit) {
			while (true) {
				while (curr.mDegree >= treeTable.size())
					treeTable.add(null);
				if (treeTable.get(curr.mDegree) == null) {
					treeTable.set(curr.mDegree, curr);
					break;
				}
				Entry<T> other = treeTable.get(curr.mDegree);
				treeTable.set(curr.mDegree, null); // Clear the slot

				Entry<T> min = (other.mPriority < curr.mPriority) ? other : curr;
				Entry<T> max = (other.mPriority < curr.mPriority) ? curr : other;

				max.mNext.mPrev = max.mPrev;
				max.mPrev.mNext = max.mNext;

				max.mNext = max.mPrev = max;
				min.mChild = mergeLists(min.mChild, max);

				max.mParent = min;
				max.mIsMarked = false;
				++min.mDegree;
				curr = min;
			}

			if (curr.mPriority <= mMin.mPriority)
				mMin = curr;
		}
		return minElem;
	}

	/**
	 * Decreases the key of the specified element to the new priority. If the new
	 * priority is greater than the old priority, this function throws an
	 * IllegalArgumentException. The new priority must be a finite double, so you
	 * cannot set the priority to be NaN, or +/- infinity. Doing so also throws an
	 * IllegalArgumentException.
	 *
	 * It is assumed that the entry belongs in this heap. For efficiency reasons,
	 * this is not checked at runtime.
	 *
	 * @param entry       The element whose priority should be decreased.
	 * @param newPriority The new priority to associate with this entry.
	 * @throws IllegalArgumentException If the new priority exceeds the old
	 *                                  priority, or if the argument is not a finite
	 *                                  double.
	 */
	public void decreaseKey(Entry<T> entry, double newPriority) {
		checkPriority(newPriority);
		if (newPriority > entry.mPriority)
			throw new IllegalArgumentException("New priority exceeds old.");

		decreaseKeyUnchecked(entry, newPriority);
	}

	/**
	 * Deletes this Entry from the Fibonacci heap that contains it.
	 *
	 * It is assumed that the entry belongs in this heap. For efficiency reasons,
	 * this is not checked at runtime.
	 *
	 * @param entry The entry to delete.
	 */
	public void delete(Entry<T> entry) {
		decreaseKeyUnchecked(entry, Double.NEGATIVE_INFINITY);
		dequeueMin();
	}

	/**
	 * Utility function which, given a user-specified priority, checks whether it's
	 * a valid double and throws an IllegalArgumentException otherwise.
	 *
	 * @param priority The user's specified priority.
	 * @throws IllegalArgumentException If it is not valid.
	 */
	private void checkPriority(double priority) {
		if (Double.isNaN(priority))
			throw new IllegalArgumentException(priority + " is invalid.");
	}

	/**
	 * Utility function which, given two pointers into disjoint circularly- linked
	 * lists, merges the two lists together into one circularly-linked list in O(1)
	 * time. Because the lists may be empty, the return value is the only pointer
	 * that's guaranteed to be to an element of the resulting list.
	 *
	 * This function assumes that one and two are the minimum elements of the lists
	 * they are in, and returns a pointer to whichever is smaller. If this condition
	 * does not hold, the return value is some arbitrary pointer into the
	 * doubly-linked list.
	 *
	 * @param one A pointer into one of the two linked lists.
	 * @param two A pointer into the other of the two linked lists.
	 * @return A pointer to the smallest element of the resulting list.
	 */
	private static <T> Entry<T> mergeLists(Entry<T> one, Entry<T> two) {
		/*
		 * There are four cases depending on whether the lists are null or not. We
		 * consider each separately.
		 */
		if (one == null && two == null) { // Both null, resulting list is null.
			return null;
		} else if (one != null && two == null) { // Two is null, result is one.
			return one;
		} else if (one == null && two != null) { // One is null, result is two.
			return two;
		} else { // Both non-null; actually do the splice.

			Entry<T> oneNext = one.mNext; // Cache this since we're about to overwrite it.
			one.mNext = two.mNext;
			one.mNext.mPrev = one;
			two.mNext = oneNext;
			two.mNext.mPrev = two;

			return one.mPriority < two.mPriority ? one : two;
		}
	}

	/**
	 * Decreases the key of a node in the tree without doing any checking to ensure
	 * that the new priority is valid.
	 *
	 * @param entry    The node whose key should be decreased.
	 * @param priority The node's new priority.
	 */
	private void decreaseKeyUnchecked(Entry<T> entry, double priority) {
		entry.mPriority = priority;

		if (entry.mParent != null && entry.mPriority <= entry.mParent.mPriority)
			cutNode(entry);

		if (entry.mPriority <= mMin.mPriority)
			mMin = entry;
	}

	/**
	 * Cuts a node from its parent. If the parent was already marked, recursively
	 * cuts that node from its parent as well.
	 *
	 * @param entry The node to cut from its parent.
	 */
	private void cutNode(Entry<T> entry) {
		entry.mIsMarked = false;

		if (entry.mParent == null)
			return;

		if (entry.mNext != entry) { // Has siblings
			entry.mNext.mPrev = entry.mPrev;
			entry.mPrev.mNext = entry.mNext;
		}

		if (entry.mParent.mChild == entry) {
			if (entry.mNext != entry) {
				entry.mParent.mChild = entry.mNext;
			}
			else {
				entry.mParent.mChild = null;
			}
		}

		--entry.mParent.mDegree;

		entry.mPrev = entry.mNext = entry;
		mMin = mergeLists(mMin, entry);

		if (entry.mParent.mIsMarked)
			cutNode(entry.mParent);
		else
			entry.mParent.mIsMarked = true;

		entry.mParent = null;
	}
}
