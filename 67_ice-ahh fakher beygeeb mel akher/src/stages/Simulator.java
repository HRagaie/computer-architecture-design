package stages;

import java.util.Hashtable;

import components.*;

public class Simulator {
	static int clockcycle=0;

	public Simulator() throws InvalidOpcodeException{
		// data memory
		DataMemory dataMem = new DataMemory();
		for (int i = 0; i < 20; i++) {
			dataMem.write(i, i + 1);
		}
		// instruction memory
		InstructionMemory instMem = new InstructionMemory();
		
		Cache cache = new Cache();

		
		String bne = "10100000000001000000000000000011"; // bne reg0 reg1 imm=3		(goes to J)						 
		String sw =  "10010001000111000000000000000100"; // sw reg2 reg7 imm=4		(M[reg7]= reg2+4					
		String add = "00000010000101001100000000000000"; // add reg4 reg5 reg6 		(reg6=reg4+reg5)	
		String j =	 "11010000000000000000000000000101"; // jump imm = 5  			(goes to LW)
		String ori = "01010011101000000000000000000011"; // ori reg7 reg8 imm=3 		(reg8=reg7 or 3)					
		String lw =  "10000100101010000000000000000011"; // lw reg9 reg10 imm=3		(reg10=M[reg9+3])						
		String addi ="00010101101100111111111111111110"; // addi reg11 reg12 imm= -2	(reg12= reg11 - 2)				
		String sub = "00100111001101011110000000000000"; // sub reg14 reg13 reg15	 	(reg15=reg14-reg13)						
		String mult= "00111000010001100100000000000000"; // mult reg16 reg 17 reg18	(reg18=reg16*reg17)					
		String and = "01001001110100101010000000000000"; // and reg19 reg20 reg21 	(reg21=reg19 and reg20 (result 20))					
		String sll = "01101011010111000000000000000010"; // sll reg22 reg23 imm=2 	(reg23 = reg22 << 2) result = 92 
		String srl = "01111100011001000000000000000010"; // srl reg24 reg25 imm=2 	(reg25 = reg24 >> 2) result = 6
		String slt = "11001101011011111000000000000000"; // slt reg26 reg27 reg28  	(reg28 = boolean reg26 < reg27) , true		
		String slt2= "11001111011101111110000000000000"; // slt reg30 reg29 reg31 	(reg31 = boolean reg30 < reg29) , false	
		String sbt = "10111000001111000000000000001101"; // sbt reg16 reg15 imm=13 	(skips sll, srl)  
		String sw2 = "10010001001111000000000000000111"; // sw reg2 reg15 imm=7		(M[reg15]= reg2+7)						

//
		instMem.setInstruction(0, bne);
		instMem.setInstruction(1, add);
		instMem.setInstruction(2, j);
		instMem.setInstruction(3, ori);
		instMem.setInstruction(4, addi);
		instMem.setInstruction(5, sub);
		instMem.setInstruction(6, lw);
		instMem.setInstruction(7, mult);
		instMem.setInstruction(8, and);
		instMem.setInstruction(9, sw);
		instMem.setInstruction(10, sll);
		instMem.setInstruction(11, srl);
		instMem.setInstruction(12, sw2);
	//	instMem.setInstruction(13, sbt);
		instMem.setInstruction(13, slt2);
		instMem.setInstruction(14, slt);



		// registerfile
		RegisterFile regFile = new RegisterFile();
		ProgramCounter pc = new ProgramCounter();		
		
		while(!Pipeline.IF_ID.isEmpty() || !Pipeline.ID_EX.isEmpty() || !Pipeline.EX_MA.isEmpty() || !Pipeline.MA_WB.isEmpty() || clockcycle == 0) {
			System.out.println("ClockCycle: " + clockcycle);
			
			WriteBack wb = new WriteBack(clockcycle);
			System.out.println("MA_WB"+Pipeline.MA_WB);

			MemoryAccess memAccess = new MemoryAccess(clockcycle);
			System.out.println("MA_WB"+Pipeline.MA_WB);

			Execute exe = new Execute(clockcycle);
			System.out.println("EX_MA"+Pipeline.EX_MA);

			InstructionDecode ID = new InstructionDecode(clockcycle);
			System.out.println("ID_EX"+ Pipeline.ID_EX);

			InstructionFetch IF = new InstructionFetch(instMem, clockcycle);
			System.out.println("IF_ID" +Pipeline.IF_ID);
			clockcycle++;
			System.out.println("___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");	
			if(Pipeline.exitFlagFetch && Pipeline.exitFlagDecode && Pipeline.exitFlagExecute && Pipeline.exitFlagMemory && Pipeline.exitFlagWriteback) {
				break;
			}
		}
	}
		
	public static void main(String[] args) throws InvalidOpcodeException{
		Simulator sim = new Simulator();
		for(int i =0 ; i<20; i++) {
		System.out.println("Data memory " +  i+ " : " +DataMemory.read(i));
		}
		for (int i=0; i<32;i++) {
			System.out.println("Register File " + i + " : " + RegisterFile.readRegister(i));
		}
//		
		for(int i =0; i<8; i++) {
			System.out.println ("Cache " + i + " :" +Cache.cache[i] );
		}
	}

}
