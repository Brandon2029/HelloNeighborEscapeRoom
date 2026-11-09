package game;

public class MusicBox extends Item {

	private static final long serialVersionUID = 1L;

	public MusicBox() {
		super("musicbox");
	}

	@Override
	public void use() {
		Game.print("The music box plays a slow tune. " + "Scratched underneath: " + "'The truth is hidden upstairs.'");
	}
}
