package stages;

import components.Cache;
import components.DataMemory;
import components.Pipeline;
import components.ProgramCounter;

public class MemoryAccess {
	static int memoryOutput;
	static int clockcycle;

	public MemoryAccess(int clockcycle) {
		memoryOutput = -1;
		this.clockcycle = clockcycle;
		MemAccess(clockcycle);
	}

	public void MemAccess(int clockcycle) {
		if (Pipeline.exitFlagExecute)
			Pipeline.exitFlagMemory = true;

		if (Pipeline.EX_MA.isEmpty()) {
			return;
		}
		if (clockcycle == (int) Pipeline.EX_MA.get("memCLK") || !Pipeline.exitFlagMemory) {
			System.out.println("\nMemory Access at ... ClockCycle: " + clockcycle);
			Pipeline.MA_WB.clear();
			Pipeline.switchMAtoWB();
			String MemWrite = (String) Pipeline.MA_WB.get("memWrite");
			String MemRead = (String) Pipeline.MA_WB.get("memRead");
			String branch = (String) Pipeline.MA_WB.get("branch");
			Boolean ZeroFlag = (Boolean) Pipeline.MA_WB.get("zeroFlag");
			int BranchAddressResult = (int) Pipeline.MA_WB.get("branchAddress");
			int ALUresult = (int) Pipeline.MA_WB.get("ALUresult");
			int rtValue = (int) Pipeline.MA_WB.get("rtValue");
			String jump = (String) Pipeline.MA_WB.get("jump");
			int jumpAddress = (int) Pipeline.MA_WB.get("jumpAddress");
			boolean canJump = (boolean) Pipeline.MA_WB.get("canJump");
			boolean canBranch = (boolean) Pipeline.MA_WB.get("canBranch");

			if (MemWrite.equals("1")) {
				Cache.Write(rtValue,ALUresult);
			} else if (MemRead.equals("1")) {
				rtValue = Cache.read(ALUresult);
				memoryOutput = rtValue;
			} else if (memoryOutput == -1) {
				System.out.println("No data to output from memory; e.g sw");
			}
			if (canBranch) {
				Pipeline.BJClear();
				ProgramCounter.setPc(BranchAddressResult);
			}
			if (canJump) {
				Pipeline.BJClear();
				ProgramCounter.setPc(jumpAddress);
			}
			Pipeline.MA_WB.put("memoryOutput", memoryOutput);

		}

	}
}
