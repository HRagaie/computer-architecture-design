package stages;

public class ControlUnit {

	static String regDst;
	static String branch;
	static String memRead;
	static String memToReg;
	static String memWrite;
	static String isImmediate;
	static String regWrite;
	static String jump;

	public ControlUnit() {
		regDst = "0";
		branch = "0";
		memRead = "0";
		memToReg = "0";
		memWrite = "0";
		isImmediate = "0";
		regWrite = "0";
		jump = "0";

	}

	public static void getControl(String code) throws InvalidOpcodeException {
		// * regDst 1bit,branch 1bit,memRead 1bit,MemtoReg 1bit,MemWrite 1bit, ALUSrc
		// 1bit regWrite 1bit, Jump 1bit
		
		// * op 4 rs 5 rt 5 rd 5 ====> if R Type
		// * op 4 rs 5 rt 5 imm 18 ====> if not R Type
		// * op 4 imm 28 ====> if j

		// R type
		// add-mult-sub-and-SLT
		if (code.equals("0000") || code.equals("0011") || code.equals("0010") || code.equals("0100") || code.equals("1100")) {
			regDst = "1";
			regWrite = "1";
		}
		// BEQ and BGT
		else if (code.equals("1010") || code.equals("1011")) {
			branch = "1";
		}
		// sw
		else if (code.equals("1001")) {
			memWrite = "1";
			isImmediate = "1";
		}
		// lw
		else if (code.equals("1000")) {
			memRead = "1";
			memToReg = "1";
			regWrite = "1";
			isImmediate = "1";
		}
		// Jump
		else if (code.equals("1101")) {
			jump = "1";
		}
		// add i, or i, SLL,SLR
		else if (code.equals("0001") || code.equals("0101") || code.equals("0110") || code.equals("0111")) {
			isImmediate = "1";
			regWrite = "1";
		} else {
			throw new InvalidOpcodeException();
		}
	}
	
	public static String getRegDst() {
		return regDst;
	}

	public static String getBranch() {
		return branch;
	}

	public static String getMemRead() {
		return memRead;
	}

	public static String getMemToReg() {
		return memToReg;
	}

	public static String getMemWrite() {
		return memWrite;
	}

	public static String getIsImmediate() {
		return isImmediate;
	}

	public static String getRegWrite() {
		return regWrite;
	}

	public static String getJump() {
		return jump;
	}
	
}
