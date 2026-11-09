package game;

public class Diary extends Item {

	private static final long serialVersionUID = 1L;

	public Diary() {
		super("diary");
	}

	@Override
	public void read() {
		Game.print(
				"The diary reads: " + "'I hid the evidence behind the basement wall. " + "Nobody must discover it.'");
	}
}
