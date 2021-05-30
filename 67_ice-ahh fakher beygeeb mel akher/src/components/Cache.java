package components;

public class Cache {
	public static CacheObject[] cache;

	// static String dataRead;
	public Cache() {
		cache = new CacheObject[8];
		for(int i=0; i<8;i++) {
			cache[i]= new CacheObject(false,-1,-1);
		}
		
	}

	public static int read(int Address) {
		int dataRead;
		int index = Address % 8;
		int tag = Address / 8;
		if (cache[index].valid) {
			if (cache[index].tag == tag) {
				// System.out.println("------------It Is A Hit-------------" + ""+ ++hits);
				// System.out.println(dataOfCache[index]);
				dataRead = cache[index].data;
				return dataRead;
			}
			cache[index].tag = tag;
			dataRead = DataMemory.read(Address);
			cache[index].data = dataRead;

			// System.out.println("------------It Is A Miss-------------" + ""+ ++misses);
			return dataRead;
		}
		cache[index].tag = tag;
		cache[index] .valid= true;
		dataRead = DataMemory.read(Address);
		cache[index].data = dataRead;

		// System.out.println("------------It Is A Miss-------------" + ""+ ++misses);
		return dataRead;
	}

	public static void Write(int Address, int data) {
		int index = Address % 8;
		int tag = Address / 8;
		if (cache[index].valid) {
			if (cache[index].tag== tag) {
				cache[index].data= data;
				DataMemory.write(Address, data);
				return;
			}
		}

		DataMemory.write(Address, data);
		cache[index].valid= true;
		cache[index].tag = tag;
		cache[index].data = data;
System.err.println("data: " + data );
	}

}
