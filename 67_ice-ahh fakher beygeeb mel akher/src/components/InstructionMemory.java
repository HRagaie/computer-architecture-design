package components;

public class InstructionMemory {

	public static String[] instructionMemory;
	public static int noOfInstructions=0;

	public InstructionMemory() {
		instructionMemory = new String[1024];
	}

	public void setInstruction(int index, String value) {
		instructionMemory[index] = value;
		noOfInstructions++;
	}

	public String getInstruction(int pc) {
	
		return instructionMemory[pc];

	}
	
	public static Boolean isEmpty( int pc) {
		if(instructionMemory[pc]==null) {
			return true;
		}
		return false;
	}
}
