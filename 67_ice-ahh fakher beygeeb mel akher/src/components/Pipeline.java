package components;

import java.util.Hashtable;

public class Pipeline {
	public static Boolean exitFlagFetch = false;
	public static Boolean exitFlagDecode = false;
	public static Boolean exitFlagExecute = false;
	public static Boolean exitFlagMemory = false;
	public static Boolean exitFlagWriteback = false;

	public static Hashtable<String,Object> IF_ID =new Hashtable<String, Object>() ;
	public static Hashtable<String,Object> ID_EX =new Hashtable<String, Object>() ;
	public static Hashtable<String,Object> EX_MA =new Hashtable<String, Object>() ;
	public static Hashtable<String,Object> MA_WB =new Hashtable<String, Object>() ;

	public static void switchIDtoEX() {
		ID_EX.putAll(IF_ID);
		
	}
	
	public static void switchEXtoMA() {
		EX_MA.putAll(ID_EX);
	}
	
	public static void switchMAtoWB() {
		MA_WB.putAll(EX_MA);
	}

	public static void BJClear() {
		IF_ID.clear();
		ID_EX.clear();
		EX_MA.clear();
	}
}
