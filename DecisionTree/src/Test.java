import java.io.IOException;

public class Test {
	
//	public int accuracy() {
//		
//		
//	}
//	
//	public prediction() {
//		
//	}
//	
	
//	public arrange() {
//		
//		
//	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// USER INPUT
		int l = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);
		//String trainfile = "/Users/Rachel/CISC484/data_sets1/training_set.csv";
		String trainfile = args[2];
		String validfile = args[3];
		String testfile = args[4];
		boolean toprint = false;
		String toprint_input = args[5];
		if(toprint_input.contains("yes")){
			toprint = true;
		} else if(toprint_input.contains("no")){
			toprint = false;
		} else{
			System.out.println("Error: please type 'yes' or 'no' for toprint argument");
			System.out.println("You typed '" + args[5] + "'");
		}
		
		
		UnitSet trainTree = new UnitSet(trainfile);
		UnitSet validTree = new UnitSet(validfile);
		UnitSet testTree = new UnitSet(testfile);
		
		
		//Generate tree by Entropy
		DTree tree = new DTree(trainTree, 0, validTree.unitList);
		
		if(toprint){
			System.out.println("Entropy Tree");
			System.out.print(tree.root);
		}
		
		System.out.println( "-----------------------------------");
		double Accuracy = 0.0;
		
		System.out.println(" This is for ENTRIOPY DT");
		System.out.println("Accuracy test file");
		
		
		
		
		Accuracy = tree.pacc(testTree.unitList);
//		
		System.out.println("Accuracy Train is : " + tree.pacc(trainTree.unitList));
		System.out.println("Accuracy Valid is : " + tree.pacc(validTree.unitList));
		System.out.println("Accuracy Test is : " + tree.pacc(testTree.unitList));
//		
		
		tree = tree.pruneTree(l, k);
		
		System.out.println("AfterPrune -----" );

		System.out.println("Accuracy Train is : " + tree.pacc(trainTree.unitList));
		System.out.println("Accuracy Valid is : " + tree.pacc(validTree.unitList));
		System.out.println("Accuracy Test is : " + tree.pacc(testTree.unitList));

		
		System.out.println( "-----------------------------------");
		
		System.out.println(" This is for VARIANCE DT");
		
		VTree vtree = new VTree(trainTree, 0, validTree.unitList);
		
		if(toprint){
			System.out.println("Variance Tree");
			System.out.print(vtree.root);
		}
		
		Accuracy = vtree.pacc(testTree.unitList);
//		
		System.out.println("Accuracy Train is : " + vtree.pacc(trainTree.unitList));
		System.out.println("Accuracy Valid is : " + vtree.pacc(validTree.unitList));
		System.out.println("Accuracy Test is : " + vtree.pacc(testTree.unitList));
//		
		
		vtree = vtree.pruneTree(l, k);
		
		System.out.println("AfterPrune -----" );

		System.out.println("Accuracy Train is : " + vtree.pacc(trainTree.unitList));
		System.out.println("Accuracy Valid is : " + vtree.pacc(validTree.unitList));
		System.out.println("Accuracy Test is : " + vtree.pacc(testTree.unitList));
		
		
		
	}

}
