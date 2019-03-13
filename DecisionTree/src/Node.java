import java.util.ArrayList;
import java.util.List;

public class Node {

	int depth; 
	int attriId; // attribute id = index of an attribute
	int classType = -1; // {0, 1}
	Node left, right;
	UnitSet uset;

	public Node(int item, int depth) {

		attriId = item;
		this.depth = depth;
		left = null;
		right = null;
		uset = null;

	}

	public Node clone() {
		Node n = new Node(this.attriId, this.depth);

		if (this.left != null) {
			n.setLeft(this.left.clone());
		}

		if (this.right != null) {
			n.setRight(this.right.clone());
		}

		n.setClassType(this.classType);

		return n;
	}

	public void setClassType(int num) {
		this.classType = num;
	}

	public int getClassType() {
		return this.classType;
	}

	public void setUset(UnitSet uset) {
		this.uset = uset;
	}

	public void setRight(Node rnode) {
		this.right = rnode;
	}

	public void setLeft(Node lnode) {
		this.left = lnode;
	}

	public List<Node> getList() {
		List<Node> l = new ArrayList<>();
		l.add(this);
		if (this.left != null && this.left.classType < 0) {
			l.addAll(this.left.getList());
		}
		if (this.right != null && this.right.classType < 0) {
			l.addAll(this.right.getList());
		}
		return l;
	}

	public List<Unit> getUnits() {

		return this.uset.unitList;
	}

	public int predict(Unit u) {
		if(this.attriId < 0)
			return this.classType;
		
		
		String uaVal = u.attriValue.get(attriId);

		if (uaVal.equals("1")) {
				return left.predict(u);
			
		} else {
				return right.predict(u);
			
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (this.attriId < 0) {
			return "ClassType = " + this.classType + " (A single node in Tree)";
		}

		// Print the left branch header
		for (int i = 0; i < depth; i++)
			sb.append("| ");
		sb.append(Util.list.get(attriId));
		sb.append(" = 0 : ");
		if (this.left != null) {
			if (this.left.classType >= 0) {
				// If left is a leaf node add class type
				sb.append(this.left.classType);
				sb.append("\n");
			} else {
				sb.append("\n");
				sb.append(this.left);
			}
		}
		// Print the right branch header
		for (int i = 0; i < depth; i++)
			sb.append("| ");
		sb.append(Util.list.get(attriId));
		sb.append(" = 1 : ");
		if (this.right != null) {
			if (this.right.classType >= 0) {
				// If right is a leaf node add class type
				sb.append(this.right.classType);
				sb.append("\n");
			} else {
				sb.append("\n");
				sb.append(this.right);
			}
		}
		return sb.toString();
	}

}
