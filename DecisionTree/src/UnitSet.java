import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UnitSet {

	List<Unit> unitList = new ArrayList<>(); // a list of Unit
	int numAttri; // number of attributes

	String fileName; //file name

	
	//Read file
	public UnitSet(String csvFile) throws IOException {
		this.fileName = csvFile;
		unitList = this.convertToList(csvFile);
	}

	
	public UnitSet(List<Unit> units) {
		this.unitList = units;
	}

	
	//Convert file to a list of attributes
	public List<Unit> convertToList(String csvFile) throws IOException {

		this.fileName = csvFile;
		FileReader fileReader = new FileReader(csvFile);
		BufferedReader breader = new BufferedReader(fileReader);

		String line;

		// First row is attribute name
		line = breader.readLine();
		String[] cols_Name = line.split(",");

		// Store names in Util.list
		for (String x : cols_Name) {
			Util.list.add(x);
		}

		// Rest row is attribute view
		while ((line = breader.readLine()) != null) {
			String[] cols = line.split(",");

			Unit tempUnit = new Unit();
			numAttri = cols.length - 1; // give how many attributes

			for (String x : cols) {
				// System.out.print(x);
				tempUnit.attriValue.add(x);
			}
			unitList.add(tempUnit);
		}
		return unitList;
	}

	
	public int getBestEntroAttriId() {
		int attriId = -1; //Initialize to -1
		double entropy = 10000; //Initialize to 10000

		int size = unitList.get(0).attriValue.size() - 1; // all attributes' position in the list

		for (int i = 0; i < size; i++) {
			double tempEntropy = calculateEntro(i);
			if (tempEntropy < entropy) {
				entropy = tempEntropy;
				attriId = i;
			}
		}

		if (entropy > 1.0) {
			return -1;
		}
		return attriId;
	}

	// do the log based 2 calculation
	public double logb(double a, double b) {
		if (a == 0) {
			return 0;
		}
		return Math.log(a) / Math.log(b);
	}

	// calculate one attribute's entropy
	// the entropy is not the REAL entropy, is the latter half.
	// Thus,sub-entropy smaller is better here. -> for the larger TotalEntropy
	public double calculateEntro(int attri_position) {
		double positiveEntropy = -1; //CLASS = 1
		double negativeEntropy = -1; //CLASS = 0
		double totalEntropy = -1; // latter half entropy
		double yesPosiCount = 0; //Attribute = 1, based on CLASS = 1
		double noPosiCount = 0; //Attribute = 0, based on CLASS = 1
		double yesNegaCount = 0; //Attribute = 1, based on CLASS = 0
		double noNegaCount = 0; //Attribute = 0, based on CLASS = 0
		double totalSize = unitList.size(); // Total Rows
		
		//Divide the whole list into 2 after getting a best attribute
		List<Unit> positiveList = new ArrayList<>(); 
		List<Unit> negativeList = new ArrayList<>();

		for (Unit x : unitList) {
			if (x.attriValue.get(attri_position).equals("1")) {
				positiveList.add(x);
			} else {
				negativeList.add(x);
			}
		}

		int PositiveRowCount = positiveList.size();
		// System.out.println(PositiveRowCount);
		int NegativeRowCount = negativeList.size();
		// System.out.println(NegativeRowCount);

		for (Unit x : positiveList) {
			int length = x.attriValue.size();
			if (x.attriValue.get(length - 1).equals("1")) {
				yesPosiCount++;
				// System.out.println("PROCESS YES: " + yesPosiCount);
			} else {
				noPosiCount++;
				// System.out.println("PROCESS NO: " + noPosiCount);
			}
		}
		
		positiveEntropy = -yesPosiCount / PositiveRowCount * logb(yesPosiCount / PositiveRowCount, 2)
				- noPosiCount / PositiveRowCount * logb(noPosiCount / PositiveRowCount, 2);

		for (Unit x : negativeList) {
			int length = x.attriValue.size();
			if (x.attriValue.get(length - 1).equals("1")) {
				yesNegaCount++;
			} else {
				noNegaCount++;
			}
		}
		
		negativeEntropy = -yesNegaCount / NegativeRowCount * logb(yesNegaCount / NegativeRowCount, 2)
				- noNegaCount / NegativeRowCount * logb(noNegaCount / NegativeRowCount, 2);

		totalEntropy = (yesPosiCount + noPosiCount) / totalSize * positiveEntropy
				+ (yesNegaCount + noNegaCount) / totalSize * negativeEntropy;

		if (totalEntropy <= 0)
			totalEntropy = 0;

		if (0 <= totalEntropy && 1.0 >= totalEntropy && (yesPosiCount + yesNegaCount != 0)
				&& (noPosiCount + noNegaCount) != 0)
			return totalEntropy;
		else
			return 10.0;
	}

	public int getMainClassType() {
		int ClassType = -1;
		int positiveCount = 0;
		int negativeCount = 0;

		for (Unit x : unitList) {
			int length = x.attriValue.size();
			if (x.attriValue.get(length - 1).equals("1")) {
				positiveCount++;
			} else {
				negativeCount++;
			}
		}

		if (positiveCount >= negativeCount) {
			ClassType = 1;
		} else {
			ClassType = 0;
		}

		return ClassType;
	}

	// by using attribute to divide a UnitSet to two
	public List<Unit> getLeftSet(int bestAId) {

		List<Unit> positiveList = new ArrayList<>();

		for (Unit x : unitList) {
			if (x.attriValue.get(bestAId).equals("1")) {
				positiveList.add(x);
			}
		}

		//printUsetList(positiveList);

		return positiveList;
	}

	public void setLeftSet(int bestAId) {
		this.unitList = getLeftSet(bestAId);
	}

	public List<Unit> getRightSet(int bestAId) {

		List<Unit> negativeList = new ArrayList<>();

		for (Unit x : unitList) {
			if (x.attriValue.get(bestAId).equals("0")) {
				negativeList.add(x);

			}
		}
		//printUsetList(negativeList);

		return negativeList;
	}

	public void setRightSet(int bestAId) {
		this.unitList = getRightSet(bestAId);
	}

	public void printUsetList(List<Unit> list) {

		if (list.size() > 0) {
			int size = list.get(0).attriValue.size();
			for (Unit x : list) {
				for (int i = 0; i < size; i++) {
					System.out.print(x.attriValue.get(i));
				}
				System.out.println();
			}
		}
	}

	public UnitSet getRight(int bestAId) {
		return new UnitSet(getRightSet(bestAId));

	}

	public UnitSet getLeft(int bestAId) {
		return new UnitSet(getLeftSet(bestAId));

	}
	
	
	//------------------ Variance part ------------------
	
	public double calculateVariance(int attri_position) {
		double positiveVarianceSide = -1; //CLASS = 1
		double negativeVarianceSide = -1; //CLASS = 0
		double totalVariance = -1; // latter half entropy
		double yesPosiCount = 0; //Attribute = 1, based on CLASS = 1
		double noPosiCount = 0; //Attribute = 0, based on CLASS = 1
		double yesNegaCount = 0; //Attribute = 1, based on CLASS = 0
		double noNegaCount = 0; //Attribute = 0, based on CLASS = 0
		double totalSize = unitList.size(); // Total Rows
		
		
		
		//Divide the whole list into 2 after getting a best attribute
		List<Unit> positiveList = new ArrayList<>(); 
		List<Unit> negativeList = new ArrayList<>();

		for (Unit x : unitList) {
			if (x.attriValue.get(attri_position).equals("1")) {
				positiveList.add(x);
			} else {
				negativeList.add(x);
			}
		}

		int PositiveRowCount = positiveList.size();
		// System.out.println(PositiveRowCount);
		int NegativeRowCount = negativeList.size();
		// System.out.println(NegativeRowCount);
		
		

		for (Unit x : positiveList) {
			int length = x.attriValue.size();
			if (x.attriValue.get(length - 1).equals("1")) {
				yesPosiCount++;
				// System.out.println("PROCESS YES: " + yesPosiCount);
			} else {
				noPosiCount++;
				// System.out.println("PROCESS NO: " + noPosiCount);
			}
		}
		
		positiveVarianceSide = yesPosiCount / PositiveRowCount * noPosiCount/PositiveRowCount;
				

		for (Unit x : negativeList) {
			int length = x.attriValue.size();
			if (x.attriValue.get(length - 1).equals("1")) {
				yesNegaCount++;
			} else {
				noNegaCount++;
			}
		}
		
		negativeVarianceSide = yesNegaCount / NegativeRowCount * noNegaCount / NegativeRowCount;

		totalVariance = (yesPosiCount + noPosiCount) / totalSize * positiveVarianceSide
				+ (yesNegaCount + noNegaCount) / totalSize * negativeVarianceSide;

		if (totalVariance <= 0)
			totalVariance = 0;

		if (0 <= totalVariance && 1.0 >= totalVariance && (yesPosiCount + yesNegaCount != 0)
				&& (noPosiCount + noNegaCount) != 0)
			return totalVariance;
		else
			return 10.0;
	}
	
	public int getBestVarianceAttriId() {
		int attriId = -1; //Initialize to -1
		double varian = 10000; //Initialize to 10000

		int size = unitList.get(0).attriValue.size() - 1; // all attributes' position in the list

		for (int i = 0; i < size; i++) {
			double tempVarian = calculateVariance(i);
			if (tempVarian < varian) {
				varian = tempVarian;
				attriId = i;
			}
		}

		if (varian > 1.0) {
			return -1;
		}
		return attriId;
	}
	
	
	

}
