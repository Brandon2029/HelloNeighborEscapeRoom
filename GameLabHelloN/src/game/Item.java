package game;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private boolean heavy;

	public Item(String name) {
		this(name, false);
	}

	public Item(String name, boolean heavy) {
		this.name = name;
		this.heavy = heavy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHeavy() {
		return heavy;
	}

	public void setHeavy(boolean heavy) {
		this.heavy = heavy;
	}

	public void use() {
		Game.print("You can't use the " + name + " here.");
	}

	public void open() {
		Game.print("You can't open the " + name + ".");
	}

	public void close() {
		Game.print("You can't close the " + name + ".");
	}

	public void read() {
		Game.print("There is nothing to read on the " + name + ".");
	}

	@Override
	public String toString() {
		return name;
	}
}
