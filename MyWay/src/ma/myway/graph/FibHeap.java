package ma.myway.graph;

import ma.myway.graph.data.*;
public class FibHeap {
	Node min;
	int n;
	boolean trace;
	Node found;

	public boolean get_trace() {
		return trace;
	}

	public void set_trace(boolean t) {
		this.trace = t;
	}

	public FibHeap() {
		min = null;
		n = 0;
		trace = false;
	}

	public void insert(Node x) {
		if (min == null) {
			min = x;
			x.set_left(min);
			x.set_right(min);
		} else {
			x.set_right(min);
			x.set_left(min.get_left());
			min.get_left().set_right(x);
			min.set_left(x);
			if (x.get_key() < min.get_key())
				min = x;
		}
		n += 1;
	}

	public Node extract_min() {
		Node z = this.min;
		if (z != null) {
			Node c = z.get_child();
			Node k = c, p;
			if (c != null) {
				do {
					p = c.get_right();
					insert(c);
					c.set_parent(null);
					c = p;
				} while (c != null && c != k);
			}
			z.get_left().set_right(z.get_right());
			z.get_right().set_left(z.get_left());
			z.set_child(null);
			if (z == z.get_right())
				this.min = null;
			else {
				this.min = z.get_right();
				this.consolidate();
			}
			this.n -= 1;
			return z;
		}
		return null;
	}

	public void consolidate() {
		double phi = (1 + Math.sqrt(5)) / 2;
		int Dofn = (int) (Math.log(this.n) / Math.log(phi));
		Node[] A = new Node[Dofn + 1];
		for (int i = 0; i <= Dofn; ++i)
			A[i] = null;
		Node w = min;
		if (w != null) {
			Node check = min;
			do {
				Node x = w;
				int d = x.get_degree();
				while (A[d] != null) {
					Node y = A[d];
					if (x.get_key() > y.get_key()) {
						Node temp = x;
						x = y;
						y = temp;
						w = x;
					}
					fib_heap_link(y, x);
					check = x;
					A[d] = null;
					d += 1;
				}
				A[d] = x;
				w = w.get_right();
			} while (w != null && w != check);
			this.min = null;
			for (int i = 0; i <= Dofn; ++i) {
				if (A[i] != null) {
					insert(A[i]);
				}
			}
		}
	}

	// Linking operation
	private void fib_heap_link(Node y, Node x) {
		y.get_left().set_right(y.get_right());
		y.get_right().set_left(y.get_left());

		Node p = x.get_child();
		if (p == null) {
			y.set_right(y);
			y.set_left(y);
		} else {
			y.set_right(p);
			y.set_left(p.get_left());
			p.get_left().set_right(y);
			p.set_left(y);
		}
		y.set_parent(x);
		x.set_child(y);
		x.set_degree(x.get_degree() + 1);
		y.set_mark(false);
	}

	// Search operation
	private void find(double key, Node c) {
		if (found != null || c == null)
			return;
		else {
			Node temp = c;
			do {
				if (key == temp.get_key())
					found = temp;
				else {
					Node k = temp.get_child();
					find(key, k);
					temp = temp.get_right();
				}
			} while (temp != c && found == null);
		}
	}

	public Node find(double k) {
		found = null;
		find(k, this.min);
		return found;
	}

	public void decrease_key(double key, double nval) {
		Node x = find(key);
		decrease_key(x, nval);
	}

	// Decrease key operation
	public void decrease_key(Node x, double k) {
		if (k > x.get_key())
			return;
		x.set_key(k);
		Node y = x.get_parent();
		if (y != null && x.get_key() < y.get_key()) {
			cut(x, y);
			cascading_cut(y);
		}
		if (x.get_key() < min.get_key())
			min = x;
	}

	// Cut operation
	private void cut(Node x, Node y) {
		x.get_right().set_left(x.get_left());
		x.get_left().set_right(x.get_right());

		y.set_degree(y.get_degree() - 1);

		x.set_right(null);
		x.set_left(null);
		insert(x);
		x.set_parent(null);
		x.set_mark(false);
	}

	private void cascading_cut(Node y) {
		Node z = y.get_parent();
		if (z != null) {
			if (y.get_mark() == false)
				y.set_mark(true);
			else {
				cut(y, z);
				cascading_cut(z);
			}
		}
	}

	// Delete operations
	public void delete(Node x) {
		decrease_key(x, Integer.MIN_VALUE);
		extract_min();
	}
}

// insert
// smallest
// decreaseKey
//
