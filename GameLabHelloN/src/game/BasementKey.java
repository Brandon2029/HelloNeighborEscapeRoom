package game;

public class BasementKey extends Item {

	private static final long serialVersionUID = 1L;

	public BasementKey() {
		super("key");
	}

	@Override
	public void use() {
		Room current = Game.getCurrentRoom();

		if (!current.getName().equals("Kitchen")) {
			Game.print("You don't see a basement lock here.");
			return;
		}

		Room basement = current.getExit(5);

		if (!basement.isLocked()) {
			Game.print("The basement is already unlocked.");
		} else {
			basement.setLocked(false);
			Game.print("The basement key turns. " + "The basement door is now unlocked!");
		}
	}
}
