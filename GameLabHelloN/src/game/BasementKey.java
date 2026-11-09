package game;

public class BasementKey extends Item {

	private static final long serialVersionUID = 1L;

	public BasementKey() {
		super("basementkey");
	}

	@Override
	public void use() {
		Room room = Game.getCurrentRoom();

		if (!room.getName().equals("Kitchen")) {
			Game.print("You don't see a basement lock here.");
			return;
		}

		Room basement = room.getExit(5);

		if (!basement.isLocked()) {
			Game.print("The basement is already unlocked.");
		} else {
			basement.setLocked(false);
			Game.print("The basement key turns. " + "The basement door is now unlocked!");
		}
	}
}
