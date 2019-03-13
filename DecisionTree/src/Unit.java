import java.util.ArrayList;
import java.util.List;

public class Unit {

	String classType; // 1 or 0
	List<String> attriValue = new ArrayList<>(); // a list of attribute values. Such as 1 1 0 1.. 1
	
	
	//Return Class Type 1 or 0
	public String getClassType() {
		int size = attriValue.size() - 1;
		classType = attriValue.get(size);
		return classType;
	}
	
	public int getIntClassType() {
		int x = -1;
		int size = attriValue.size() - 1;
		classType = attriValue.get(size);
		
		x = Integer.parseInt(classType);
		return x;		
	}
	
}
