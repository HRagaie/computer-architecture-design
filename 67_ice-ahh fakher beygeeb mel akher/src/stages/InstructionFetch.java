package stages;

import java.util.Hashtable;
import components.*;
import components.InstructionMemory;
import components.Pipeline;
import components.ProgramCounter;

public class InstructionFetch {
	private static String instruction;
	private static InstructionMemory instmem;
	private static ProgramCounter programCounter;
	private static int fetchCLK;
	private static int decodeCLK;
	private static int executeCLK;
	private static int memCLK;
	private static int writebackCLK;

	public InstructionFetch(InstructionMemory instructionMem, int fetchCLK) {
		instruction = "";
		instmem = instructionMem;
		this.fetchCLK = fetchCLK;

		InstFetch(fetchCLK);
	}

	public static void InstFetch(int fetchCLK) {
		System.out.println("\nInstruction Fetching ... at Clock Cycle: " + fetchCLK);
		if (programCounter.getPc() == InstructionMemory.noOfInstructions) {
			Pipeline.exitFlagFetch = true;
			ProgCount();
			return;
		}
		if (!Pipeline.exitFlagFetch) {

			instruction = instmem.getInstruction(programCounter.getPc());
			ProgCount();

			Pipeline.IF_ID.clear();
			Pipeline.IF_ID.put("Instruction", instruction);
			Pipeline.IF_ID.put("PC", programCounter.getPc());
			Pipeline.IF_ID.put("fetchCLK", fetchCLK);
			decodeCLK = fetchCLK + 1;
			executeCLK = fetchCLK + 2;
			memCLK = fetchCLK + 3;
			writebackCLK = fetchCLK + 4;

			Pipeline.IF_ID.put("decodeCLK", decodeCLK);
			Pipeline.IF_ID.put("executeCLK", executeCLK);
			Pipeline.IF_ID.put("memCLK", memCLK);
			Pipeline.IF_ID.put("writebackCLK", writebackCLK);

		}
	}

	public static void ProgCount() {
		int tempPc = programCounter.getPc() + 1;
		programCounter.setPc(tempPc);
	}

}
