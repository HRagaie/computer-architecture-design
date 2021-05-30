package components;

public class Register {
	private String name;
	private String description;
	private int value;

	public Register(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String toString() {
		return "NAME: " + name + ", DESCRIPTION: " + description + ", VALUE: " + value;
	}

}
