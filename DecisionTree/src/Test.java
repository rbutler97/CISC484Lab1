import java.io.IOException;

public class Test {
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// USER INPUT
		int l = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);

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
		
		
		// Converting CSV into a List of Units
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
		
		
		Accuracy = tree.pacc(testTree.unitList);
//		
		// Calculating accuracy of decision tree on training, validaation, and test data respectively
		System.out.println("Accuracy Train is : " + tree.pacc(trainTree.unitList));
		System.out.println("Accuracy Valid is : " + tree.pacc(validTree.unitList));
		System.out.println("Accuracy Test is : " + tree.pacc(testTree.unitList));
//		
		
		tree = tree.pruneTree(l, k);
		
		System.out.println("AfterPrune -----" );
		// Calculating accuracy of decision tree on training, validation, and test data respectively after pruning
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
//		// Calculating accuracy of decision tree on training, validaation, and test data respectively
		System.out.println("Accuracy Train is : " + vtree.pacc(trainTree.unitList));
		System.out.println("Accuracy Valid is : " + vtree.pacc(validTree.unitList));
		System.out.println("Accuracy Test is : " + vtree.pacc(testTree.unitList));
//		
		
		vtree = vtree.pruneTree(l, k);
		
		System.out.println("AfterPrune -----" );
		// Calculating accuracy of decision tree on training, validaation, and test data respectively after pruning
		System.out.println("Accuracy Train is : " + vtree.pacc(trainTree.unitList));
		System.out.println("Accuracy Valid is : " + vtree.pacc(validTree.unitList));
		System.out.println("Accuracy Test is : " + vtree.pacc(testTree.unitList));
		
		
		
	}

}
