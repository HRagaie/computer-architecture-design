package components;

public class DataMemory {
	
	private static int[] dataMemory;
	
	public DataMemory()
	{
		dataMemory = new int[1024];
	}
	
	public static int read(int address)
	{
		return dataMemory[address];
	}
	
	
	public static void write(int address, int value)
	{
		dataMemory[address]=value;
	}

}
