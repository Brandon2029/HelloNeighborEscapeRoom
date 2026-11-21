package examplegame;

public class Notebook extends Item {

	private static final long serialVersionUID = 1L;

	private boolean readBefore = false;

	public Notebook() {
		super("notebook");
	}

	@Override
	public void read() {
		if (!Game.hasInventoryItem("notebook")) {
			Game.print("You need to take the notebook first.");
			return;
		}

		Game.print("You read the notebook.");
		Game.print("It contains your father's computer password.");

		if (!readBefore) {
			Game.addInventoryItem(new Item("password"));
			readBefore = true;
			Game.print("Password added to inventory!");
		}
	}
}
