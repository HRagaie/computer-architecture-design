package stages;

import components.Pipeline;
import components.RegisterFile;

public class WriteBack {
	static int writeBackData = 0;
	static int clockcycle;

	public WriteBack(int clockcycle) {
		this.clockcycle = clockcycle;
		WriteBack(clockcycle);
	}

	public static void WriteBack(int clockcycle) {

		if (Pipeline.exitFlagMemory)
			Pipeline.exitFlagWriteback = true;
		
		if (Pipeline.MA_WB.isEmpty()) {
			return;
		}
		
		if (clockcycle == (int) Pipeline.MA_WB.get("writebackCLK") || !Pipeline.exitFlagWriteback) {
			System.out.println("\nWriteBack at ... ClockCycle: " + clockcycle);

			String regDst = (String) Pipeline.MA_WB.get("regDst");
			String memToReg = (String) Pipeline.MA_WB.get("memToReg");
			int ALUresult = (int) Pipeline.MA_WB.get("ALUresult");
			int memoryOutput = (int) Pipeline.MA_WB.get("memoryOutput");
			int rdIndex = (int) Pipeline.MA_WB.get("rdIndex");
			int rtIndex = (int) Pipeline.MA_WB.get("rtIndex");
			String regWrite = (String) Pipeline.MA_WB.get("regWrite");

			if (regDst.equals("1")) {
				writeBackData = ALUresult;
				RegisterFile.writeRegister(rdIndex, writeBackData);
			} else if (memToReg.equals("1")) {
				writeBackData = memoryOutput;
				RegisterFile.writeRegister(rtIndex, writeBackData);
			} else if(regWrite.equals("1")) {
				writeBackData = ALUresult;
				RegisterFile.writeRegister(rtIndex, writeBackData);
			}
			else {
				System.out.println("There was no data to write back");
			}
		}

	}

}
