package examplegame;

public class Tool extends Item {

	private static final long serialVersionUID = 1L;

	public Tool() {
		super("tool");
	}

	@Override
	public void use() {
		if (!Game.getCurrentRoom().getName().equals("Bedroom")) {
			Game.print("You can't use the tool here.");
			return;
		}

		Room attic = Game.getCurrentRoom().getExit(4);

		if (!attic.isLocked()) {
			Game.print("The attic door is already open.");
			return;
		}

		attic.setLocked(false);
		Game.print("You use the tool to open the attic hatch.");
		Game.print("A ladder drops down from the ceiling.");
	}
}
