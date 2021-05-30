package components;

public class ProgramCounter {
	private static int pc=0;
	
	public ProgramCounter() {
		pc=0;
	}

	public static int getPc() {
		return pc;
	}

	public static void setPc(int Pc) {
		pc = Pc;
	}
	
	

}
