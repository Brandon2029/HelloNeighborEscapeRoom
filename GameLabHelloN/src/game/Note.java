package game;

public class Note extends Item {

	private static final long serialVersionUID = 1L;

	public Note() {
		super("note");
	}

	@Override
	public void read() {
		Game.print("The note says: " + "'He keeps his secrets in the attic. " + "The old chest holds a diary.'");
	}
}
