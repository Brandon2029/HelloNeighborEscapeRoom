package examplegame;

public class OfficeKey extends Item {

	private static final long serialVersionUID = 1L;

	public OfficeKey() {
		super("officekey");
	}

	@Override
	public void use() {
		if (!Game.getCurrentRoom().getName().equals("Living Room")) {
			Game.print("You can't use the office key here.");
			return;
		}

		Room office = Game.getCurrentRoom().getExit(1);

		if (!office.isLocked()) {
			Game.print("The office is already unlocked.");
			return;
		}

		office.setLocked(false);
		Game.print("You unlock the office door.");
	}
}
