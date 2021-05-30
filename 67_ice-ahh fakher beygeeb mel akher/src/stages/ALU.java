package stages;

public class ALU {
	/** OPCODES
	 * a) Arithmetic Instructions:
	 * 1. Add. 0000 r1 + r2 --> r3
	 * 2. Add immediate. 0001 
	 * 3. Sub. 0010
	 * 4. Multiply. 0011
	 * 
	 * b) Logical Instructions:
	 * 1. And. 0100
	 * 2. Or immediate.0101
	 * 3. Shift left logical. 0110
	 * 4. Shift right logical. 0111
	 * 
	 * c) Data Transfer Instructions:
	 * 1. Load word. 1000
	 * 2. Store word.1001
	 * 
	 * d) Conditional Branch Instructions:
	 * 1. Branch on not equal. 1010
	 * 2. Branch on greater than. 1011
	 * 
	 * e) Comparison Instructions:
	 * 1. Set on less than. 1100
	 * 
	 * f) Unconditional Jump Instructions:
	 * 1. Jump. 1101
	 */
	public static String ALUControl(String opCode) throws InvalidOpcodeException {
		
		if(opCode.equals("0000") || opCode.equals("1000") || opCode.equals("1001") || opCode.equals("0001")){
			return "0000"; //add
		}
		else if(opCode.equals("0010") || opCode.equals("1010")) {
			return "0001"; //sub
		}
		else if(opCode.equals("0011")) {
			return "0010"; //mult
		}
		else if(opCode.equals("0111")) {
			return "0110"; //srl
		}
		else if(opCode.equals("0100")) {
			return "0100"; // and
		}
		else if(opCode.equals("0101")) {
			return "0011"; //or
		}
		else if(opCode.equals("1100")) {
			return "0111"; //slt
		}
		else if(opCode.equals("0110")) {
			return "0101"; //sll
		}
		else if(opCode.equals("1011")) {
			return "1000"; //sbt
		}
		else if(opCode.equals("1101")) {
			return"1001"; //jump
		}
		else{
			throw new InvalidOpcodeException();
		}
	}

}
