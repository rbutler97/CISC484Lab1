import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DTree {
	Node root = null;
	int noneLeafNodeSize;
	List<Unit> vus;
	
	
	//Input:	UnitSet
	//			Depth --- to count how many "| " should add.
	//DTree:	To draw the whole tree.
	public DTree(UnitSet uset, int depth, List<Unit> us) {
		this.root = this.genDTNode(uset, depth);
		this.vus = us;
	}
	
	public DTree(Node root, int depth, List<Unit> us) {
		this.root = root;
		this.vus = us;
	}

	public Node genDTNode(UnitSet uset, int depth) {

		int attriId = -1; //Initialize to -1
		attriId = uset.getBestEntroAttriId(); //Calculate the root Id

		Node current = new Node(attriId, depth); //Initialize the Root
		
		if (attriId == -1) {
			// set is pure
			current.setClassType(uset.getMainClassType());
			
		} else {
			// recursive call left and right node
			current.setUset(uset);
			
			current.setLeft(genDTNode(uset.getLeft(attriId), depth + 1));
			
			current.setRight(genDTNode(uset.getRight(attriId), depth + 1));
		
		}
		return current;
	}
	
	
	public DTree pruneTree(int L, int K) {
		DTree bestTree = this;
		int BestAccuracy = 1000;
		
		for (int i = 1 ; i < L + 1; i++) {
			DTree tempTree = this.cloneTree();
			
			Random rand = new Random(System.currentTimeMillis());
			int M = rand.nextInt(K+1);
			
			for(int j = 1; j < M + 1; j++) {
				
				List<Node> nodes = this.root.getList();
				
				int size = nodes.size();
				int p = rand.nextInt(size);
				
				Node t = nodes.get(p);
				
				
				List<Unit> units = t.getUnits();
				
				// get majority class type
				int[] ctr = new int[2];
				for(Unit u: units) {
					ctr[u.getIntClassType()]++;				
				}
				
				if(ctr[0] >=  ctr[1]) {		
					//set class type
					t.setClassType(0);
					
				}else {	
					//set the other type
					t.setClassType(1);
					
				}
				
				t.left = null;
				t.right = null;		
				t.attriId = -1;
			}
			
			if(bestTree.pacc(bestTree.vus) < tempTree.pacc(tempTree.vus)) {
				
				bestTree = tempTree;
			}
			
		}
		
		return bestTree;
	}
	
	//predict Accuracy
	public double pacc(List<Unit> us) {
		//List<Unit> us = new ArrayList<>();		
		//us = root.uset.unitList;
		
		int ctr = 0;
		for(Unit u: us) {
			if(u.getIntClassType() == predictClassType(u)) {
				ctr++;
			}
		}
		
		
		
		return (ctr * 1.0)/(us.size() * 1.0);
	}
	
	
	public int predictClassType(Unit u) {
		return root.predict(u);
	}
	
	
	
	
	
	
	public DTree cloneTree() {
		Node cloneRoot = this.root.clone();
		DTree cloneTree = new DTree(cloneRoot, 0, this.vus);
		
		return cloneTree;
	}

}
