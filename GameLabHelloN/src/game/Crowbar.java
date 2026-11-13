package game;

public class Crowbar extends Item {

	private static final long serialVersionUID = 1L;

	public Crowbar() {
		super("crowbar");
	}

	@Override
	public void use() {
		Room room = Game.getCurrentRoom();

		if (!room.getName().equals("Bedroom 2")) {
			Game.print("There is no attic hatch here.");
			return;
		}

		Room attic = room.getExit(4);

		if (!attic.isLocked()) {
			Game.print("The attic is already unlocked.");
		} else {
			attic.setLocked(false);
			Game.print("You pry open the attic hatch!");
		}
	}
}
