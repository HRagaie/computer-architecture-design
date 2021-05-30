package stages;

import components.*;
import java.util.Hashtable;

import components.Pipeline;
import components.RegisterFile;

public class InstructionDecode {
	public static int clockcycle; // gaya mn el simulator

	public InstructionDecode(int clockcycle) throws InvalidOpcodeException {
		this.clockcycle = clockcycle;
		InstDecode(clockcycle);
	}
	/**
	 * OPCODES a) Arithmetic Instructions: 1. Add. 0000 r1 + r2 --> r3 R 2. Add
	 * immediate. 0001 I 3. Sub. 0010 R 4. Multiply. 0011 R
	 * 
	 * b) Logical Instructions: 1. And. 0100 R 2. Or immediate.0101 I 3. Shift left
	 * logical. 0110 I =====> op 4 rs rt imm rt=shift(rs,by imm) 4. Shift right
	 * logical. 0111 I
	 * 
	 * c) Data Transfer Instructions: 1. Load word. 1000 I 2. Store word.1001 I
	 * 
	 * d) Conditional Branch Instructions: 1. Branch on not equal. 1010 2. Branch on
	 * greater than. 1011
	 * 
	 * e) Comparison Instructions: 1. Set on less than. 1100
	 * 
	 * f) Unconditional Jump Instructions: 1. Jump. 1101
	 * 
	 * regdst 1bit,branch 1bit,memRead 1bit,Memtoreg 1bit,Mem write 1bit, Alusrc
	 * 1bit regwrite 1bit, Jump 1bit
	 * 
	 * op 4 rs 5 rt 5 rd 5 ====> if R Type op 4 rs 5 rt 5 imm 18 ====> if not R Type
	 * op 4 imm 28 ====> if j
	 * 
	 * what we need so far: add - sub - mult - and - or - ( shift left/right or
	 * mult/division ) - slt - 000 add 001 sub 010 mult 011 and 100 or 101 division
	 * 110 slt
	 */

	public static String getJumpAddress(String Instruction) {
		
		return SignExtend(Instruction.substring(4));
	}

	public static String getImmediateValue(String Instruction) {
		return SignExtend(Instruction.substring(14));
	}

	public static int getrsValue(String Instruction) {
		int rsIndex = Integer.parseInt(Instruction.substring(4, 9), 2);
		return RegisterFile.readRegister(rsIndex).getValue();
	}

	public static int getrtValue(String Instruction) {
		int rtIndex = Integer.parseInt(Instruction.substring(9, 14), 2);
		return RegisterFile.readRegister(rtIndex).getValue();
	}

	public static int getrdValue(String Instruction) {
		int rdIndex = Integer.parseInt(Instruction.substring(14, 19), 2);
		return RegisterFile.readRegister(rdIndex).getValue();
	}

	public static int getrsIndex(String Instruction) {
		int rsIndex = Integer.parseInt(Instruction.substring(4, 9), 2);
		return rsIndex;
	}

	public static int getrdIndex(String Instruction) {
		int rdIndex = Integer.parseInt(Instruction.substring(14, 19), 2);
		return rdIndex;
	}

	public static int getrtIndex(String Instruction) {
		int rtIndex = Integer.parseInt(Instruction.substring(9, 14), 2);
		return rtIndex;
	}

	 public static String SignExtend(String imm) {
	  int value = Integer.parseInt(imm, 2);
	  if ((32768 & value) == 0) {
	  return String.format("%32s", imm).replace(' ', '0');
	  }
	  return String.format("%32s", imm).replace(' ', '1');
	 }

	public static String getOPCode(String Instruction) {

		return Instruction.substring(0, 4);
	}

	public static void InstDecode(int clockcycle) throws InvalidOpcodeException {
		if (Pipeline.exitFlagFetch)
			Pipeline.exitFlagDecode = true;

		if (Pipeline.IF_ID.isEmpty()) {
			return;
		}

		if (clockcycle == (int) Pipeline.IF_ID.get("decodeCLK") || !Pipeline.exitFlagDecode) {

			System.out.println("\nInstruction Decode ... at ClockCycle: " + clockcycle);
			Pipeline.ID_EX.clear();
			Pipeline.switchIDtoEX();
			String Instruction = (String) Pipeline.ID_EX.get("Instruction");
			String opCode = getOPCode(Instruction);
			Pipeline.ID_EX.put("opCode", opCode);
			Pipeline.ID_EX.put("JumpAddress", getJumpAddress(Instruction));
			Pipeline.ID_EX.put("immediate", getImmediateValue(Instruction));
			Pipeline.ID_EX.put("rsValue", getrsValue(Instruction));
			Pipeline.ID_EX.put("rtValue", getrtValue(Instruction));
			Pipeline.ID_EX.put("rdIndex", getrdIndex(Instruction));
			Pipeline.ID_EX.put("rtIndex", getrtIndex(Instruction));

			ControlUnit controlUnit = new ControlUnit();
			controlUnit.getControl(opCode);

			String ALUControl = ALU.ALUControl(opCode);

			Pipeline.ID_EX.put("regDst", controlUnit.getRegDst());

			Pipeline.ID_EX.put("branch", controlUnit.getBranch());

			Pipeline.ID_EX.put("jump", controlUnit.getJump());

			Pipeline.ID_EX.put("memRead", controlUnit.getMemRead());

			Pipeline.ID_EX.put("memWrite", controlUnit.getMemWrite());

			Pipeline.ID_EX.put("memToReg", controlUnit.getMemToReg());

			Pipeline.ID_EX.put("regWrite", controlUnit.getRegWrite());

			Pipeline.ID_EX.put("isImmediate", controlUnit.getIsImmediate());

			Pipeline.ID_EX.put("ALUControl", ALUControl);

		}

	}

}
