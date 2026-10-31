package game;

public class Crowbar extends Item {

	private static final long serialVersionUID = 1L;

	public Crowbar() {
		super("crowbar");
	}

	@Override
	public void use() {
		Room current = Game.getCurrentRoom();

		if (!current.getName().equals("Bedroom 2")) {
			Game.print("There is no attic hatch " + "to open here.");
			return;
		}

		Room attic = current.getExit(4);

		if (!attic.isLocked()) {
			Game.print("The attic hatch is already open.");
		} else {
			attic.setLocked(false);
			Game.print("You pry open the attic hatch " + "with the crowbar. " + "The attic is unlocked!");
		}
	}
}
