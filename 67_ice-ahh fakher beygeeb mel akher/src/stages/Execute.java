package stages;

import java.math.BigInteger;

import components.Pipeline;
import components.ProgramCounter;

public class Execute {

	static Boolean zeroFlag;
	static int ALUresult;
	static int branchAddress;
	static int jumpAddress;
	static int clockcycle;
	static boolean canBranch;
	static boolean canJump;
	static boolean oneFlag;

	public Execute(int clockcycle)  {
		zeroFlag = false;
		oneFlag=true;;
		ALUresult = -1;
		branchAddress = 0;
		jumpAddress = -1;
		this.clockcycle = clockcycle;
		canBranch = false;
		canJump = false;

		ExecuteProcess(clockcycle);
	}

	public static int addOp(int op1, int op2) {
		int result = op1 + op2;
		if (result == 0) {
			zeroFlag = true;
		}
		if(result==1) {
			oneFlag = true;
		}
		return result;
	}

	public static int subOp(int op1, int op2) {
		int result = op1 - op2;
		if (result == 0) {
			zeroFlag = true;
		}
		if(result==1) {
			oneFlag = true;
		}
		return result;
	}

	public static int multOp(int op1, int op2) {
		int result = op1 * op2;
		if (result == 0) {
			zeroFlag = true;
		}
		if(result==1) {
			oneFlag = true;
		}
		return result;
	}

	public static int shiftLeftOp(int op1, int op2) {
		int result = op1 << op2;
		if (result == 0) {
			zeroFlag = true;
		}
		if(result==1) {
			oneFlag = true;
		}
		return result;
	}

	public static int shiftRightOp(int op1, int op2) {
		int result = op1 >> op2;
		if (result == 0) {
			zeroFlag = true;
		}
		if(result==1) {
			oneFlag = true;
		}
		return result;
	}

	public static int sltOp(int op1, int op2) {
		int result = 0;

		if (op1 < op2) {
			
				oneFlag = true;
			
			result = 1;
		}
		if (result == 0) {
			zeroFlag = true;
		}
		return result;
	}

	public static int sbtOp(int op1, int op2) {
		int result = 0;

		if (op1 > op2) {
			oneFlag = true;
			result = 1;
		}
		if (result == 0)
			zeroFlag = true;
		return result;
	}

	public static int andOp(int op1, int op2) {
		int result = op1 & op2;
		if (result == 0) {
			zeroFlag = true;
		}
		if(result==1) {
			oneFlag = true;
		}
		return result;
	}

	public static int orOp(int op1, int op2) {
		int result = op1 | op2;
		if (result == 0) {
			zeroFlag = true;
		}
		if(result==1) {
			oneFlag = true;
		}
		return result;

	}

	public void ExecuteProcess(int clockcycle) {
		if (Pipeline.exitFlagDecode)
			Pipeline.exitFlagExecute = true;

		if (Pipeline.ID_EX.isEmpty()) {
			return;
		}
		if (clockcycle == (int) Pipeline.ID_EX.get("executeCLK") || !Pipeline.exitFlagExecute) {
			System.out.println("\nExecute at ... ClockCycle: " + clockcycle);
			Pipeline.EX_MA.clear();
			Pipeline.switchEXtoMA();
			String ALUControl = (String) Pipeline.EX_MA.get("ALUControl");
			String isImmediate = (String) Pipeline.EX_MA.get("isImmediate");
			String branch = (String) Pipeline.EX_MA.get("branch");
			int rsValue = (int) Pipeline.EX_MA.get("rsValue");
			int rtValue = (int) Pipeline.EX_MA.get("rtValue");
			String immediateSignExtend =( String) Pipeline.EX_MA.get("immediate");
			BigInteger b = new BigInteger(immediateSignExtend,2);
			int immediate = b.intValue();
			int PC = (int) Pipeline.EX_MA.get("PC");
			String jump = (String) Pipeline.EX_MA.get("jump");
			// 0000 add
			// 0001 sub
			// 0010 mult
			// 0011 or
			// 0100 and
			// 0101 sll
			// 0110 srl
			// 0111 slt
			// 1000 sbt
			// 1001 jump

			switch (ALUControl) {
			// add
			case "0000":
				if (isImmediate.equals("1")) {
					ALUresult = addOp(rsValue, immediate);
				} else {
					ALUresult = addOp(rsValue, rtValue);
				}
				break;
			// sub
			case "0001":
				if (branch.equals("1")) {
					ALUresult = subOp(rsValue, rtValue);
					if (ALUresult != 0) {
						branchAddress = immediate;
						canBranch = true;
					}
				} else {
					ALUresult = subOp(rsValue, rtValue);
				}
				break;
			// mult
			case "0010":
				ALUresult = multOp(rsValue, rtValue);
				break;
			// or i
			case "0011":
				ALUresult = orOp(rsValue, immediate);
				break;
			// and
			case "0100":
				ALUresult = andOp(rsValue, rtValue);
				break;
			// sll
			case "0101":
				ALUresult = shiftLeftOp(rsValue, immediate);
				break;

			case "0111":
				ALUresult = sltOp(rsValue, rtValue);
				break;

			case "0110":
				ALUresult = shiftRightOp(rsValue, immediate);
				break;

			case "1000":
				ALUresult = sbtOp(rsValue, rtValue);
				if (branch.equals("1") && oneFlag) {
					branchAddress = immediate;
					canBranch = true;
				}
				break;
			case "1001":
				String jTemp = (String) Pipeline.EX_MA.get("JumpAddress");
				BigInteger b1= new BigInteger(jTemp,2);
				jumpAddress = b1.intValue();
				canJump = true;
				break;
			}
		}
		Pipeline.EX_MA.put("ALUresult", ALUresult);
		Pipeline.EX_MA.put("zeroFlag", zeroFlag);
		Pipeline.EX_MA.put("oneFlag", oneFlag);
		Pipeline.EX_MA.put("branchAddress", branchAddress);
		Pipeline.EX_MA.put("jumpAddress", jumpAddress);
		Pipeline.EX_MA.put("canBranch", canBranch);
		Pipeline.EX_MA.put("canJump", canJump);

	}

}
