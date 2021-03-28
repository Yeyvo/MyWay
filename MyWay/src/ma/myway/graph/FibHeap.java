package ma.myway.graph;

// Operations on Fibonacci Heap in Java

// FibNode creation
class FibNode {
	Node node;
	FibNode parent;
	FibNode left;
	FibNode right;
	FibNode child;
	int degree;
	boolean mark;
	double key;

	public FibNode(Node node) {
		this.node = node;
		this.degree = 0;
		this.mark = false;
		this.parent = null;
		this.left = this;
		this.right = this;
		this.child = null;
		this.key = Integer.MAX_VALUE;
	}

	void set_parent(FibNode x) {
		this.parent = x;
	}

	FibNode get_parent() {
		return this.parent;
	}

	void set_left(FibNode x) {
		this.left = x;
	}

	FibNode get_left() {
		return this.left;
	}

	void set_right(FibNode x) {
		this.right = x;
	}

	FibNode get_right() {
		return this.right;
	}

	void set_child(FibNode x) {
		this.child = x;
	}

	FibNode get_child() {
		return this.child;
	}

	void set_degree(int x) {
		this.degree = x;
	}

	int get_degree() {
		return this.degree;
	}

	void set_mark(boolean m) {
		this.mark = m;
	}

	boolean get_mark() {
		return this.mark;
	}

	void set_key(int x) {
		this.key = x;
	}

	double get_key() {
		return this.key;
	}
}

public class FibHeap {
	FibNode min;
	int n;
	boolean trace;
	FibNode found;

	public boolean get_trace() {
		return trace;
	}

	public void set_trace(boolean t) {
		this.trace = t;
	}

	public static FibHeap create_heap() {
		return new FibHeap();
	}

	FibHeap() {
		min = null;
		n = 0;
		trace = false;
	}

	private void insert(FibNode x) {
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

	public double extract_min() {
		FibNode z = this.min;
		if (z != null) {
			FibNode c = z.get_child();
			FibNode k = c, p;
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
			return z.get_key();
		}
		return Integer.MAX_VALUE;
	}

	public void consolidate() {
		double phi = (1 + Math.sqrt(5)) / 2;
		int Dofn = (int) (Math.log(this.n) / Math.log(phi));
		FibNode[] A = new FibNode[Dofn + 1];
		for (int i = 0; i <= Dofn; ++i)
			A[i] = null;
		FibNode w = min;
		if (w != null) {
			FibNode check = min;
			do {
				FibNode x = w;
				int d = x.get_degree();
				while (A[d] != null) {
					FibNode y = A[d];
					if (x.get_key() > y.get_key()) {
						FibNode temp = x;
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
	private void fib_heap_link(FibNode y, FibNode x) {
		y.get_left().set_right(y.get_right());
		y.get_right().set_left(y.get_left());

		FibNode p = x.get_child();
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
	private void find(int key, FibNode c) {
		if (found != null || c == null)
			return;
		else {
			FibNode temp = c;
			do {
				if (key == temp.get_key())
					found = temp;
				else {
					FibNode k = temp.get_child();
					find(key, k);
					temp = temp.get_right();
				}
			} while (temp != c && found == null);
		}
	}

	public FibNode find(int k) {
		found = null;
		find(k, this.min);
		return found;
	}

	public void decrease_key(int key, int nval) {
		FibNode x = find(key);
		decrease_key(x, nval);
	}

	// Decrease key operation
	private void decrease_key(FibNode x, int k) {
		if (k > x.get_key())
			return;
		x.set_key(k);
		FibNode y = x.get_parent();
		if (y != null && x.get_key() < y.get_key()) {
			cut(x, y);
			cascading_cut(y);
		}
		if (x.get_key() < min.get_key())
			min = x;
	}

	// Cut operation
	private void cut(FibNode x, FibNode y) {
		x.get_right().set_left(x.get_left());
		x.get_left().set_right(x.get_right());

		y.set_degree(y.get_degree() - 1);

		x.set_right(null);
		x.set_left(null);
		insert(x);
		x.set_parent(null);
		x.set_mark(false);
	}

	private void cascading_cut(FibNode y) {
		FibNode z = y.get_parent();
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
	public void delete(FibNode x) {
		decrease_key(x, Integer.MIN_VALUE);
		int p = extract_min();
	}

	public static void main(String[] args) {
		FibHeap obj = create_heap();
		obj.insert(7);
		obj.insert(26);
		obj.insert(30);
		obj.insert(39);
		obj.insert(10);
		//obj.display();
		
		System.out.println(obj.extract_min());
		//obj.display();
		System.out.println(obj.extract_min());
		//obj.display();
		System.out.println(obj.extract_min());
		//obj.display();
		System.out.println(obj.extract_min());
		//obj.display();
		System.out.println(obj.extract_min());
		obj.display();
	}
}

// insert
// smallest
// decreaseKey
//
