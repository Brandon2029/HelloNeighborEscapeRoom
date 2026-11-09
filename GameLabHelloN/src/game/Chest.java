package game;

public class Chest extends Item {

	private static final long serialVersionUID = 1L;

	private boolean opened = false;
	private boolean diaryFound = false;

	public Chest() {
		super("chest", true);
	}

	@Override
	public void open() {
		if (opened) {
			Game.print("The chest is already open.");
			return;
		}

		opened = true;
		Game.print("You lift the dusty chest lid.");

		if (!diaryFound) {
			Game.getCurrentRoom().addItem(new Diary());
			diaryFound = true;

			Game.print("Inside is an old diary. " + "You can take diary.");
		} else {
			Game.print("The chest is empty now.");
		}
	}

	@Override
	public void close() {
		if (!opened) {
			Game.print("The chest is already closed.");
		} else {
			opened = false;
			Game.print("You close the chest.");
		}
	}
}
