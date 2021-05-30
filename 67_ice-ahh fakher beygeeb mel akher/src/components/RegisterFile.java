package components;

public class RegisterFile {
	
	private static Register[] registerFile;
	private String[] names = {"reg0","reg1","reg2","reg3","reg4","reg5","reg6","reg7","reg8","reg9","reg10","reg11","reg12","reg13","reg14","reg15","reg16","reg17","reg18","reg19","reg20","reg21","reg22","reg23","reg24","reg25","reg26","reg27","reg28","reg29","reg30","reg31"};
	private String[] descriptions = {"General Purpose"};

	public RegisterFile()
	{
		registerFile = new Register[32];
		for(int i=0; i<32;i++) {
			registerFile[i]= new Register(names[i],descriptions[0]);
			registerFile[i].setValue(i+1);
			
		}
		
	}
	
	
	public static Register readRegister(int index)
	{
		return registerFile[index];
	}
	
	
	public static void writeRegister(int index, int value)
	{
		registerFile[index].setValue(value);
	}
	public String toString()
	{
		String r = "";
		for(int i = 0; i < 32; i++)
			r += registerFile[i].toString() + "\n";
		return r;
	}
}
