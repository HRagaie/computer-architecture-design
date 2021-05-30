package components;

public class CacheObject {
	boolean valid;
	int tag;
	int data;
	
	public CacheObject(boolean valid, int tag, int data) {
		this.valid = valid;
		this.tag=tag;
		this.data = data;
	}
	
	public String toString() {
		return "Valid: " + valid + ", Tag: " + tag + ", Data: " + data;
	}
}
