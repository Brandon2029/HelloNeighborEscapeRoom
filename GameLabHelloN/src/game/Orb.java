package game;

public class Orb extends Item {

	private static final long serialVersionUID = 1L;

	public Orb() {
		super("orb");
	}

	@Override
	public void use() {
		if (Game.getCurrentRoom().getName().equals("Basement")) {
			Game.print("The orb glows blue. " + "Strange symbols appear on the basement wall.");
		} else {
			Game.print("The orb feels warm, but nothing happens. " + "Maybe it belongs somewhere darker.");
		}
	}
}
