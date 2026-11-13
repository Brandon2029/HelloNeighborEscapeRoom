package game;

public class Neighbor extends NPC {

	private static final long serialVersionUID = 1L;

	public Neighbor() {
		super("neighbor");
	}

	@Override
	public void talk() {

		say("HEY! What are you doing in my garage?!");

		String[] responses = { "I'm just looking for my missing basketball.", "Why do you keep locking all your doors?",
				"Nice mustache! Is it real?" };

		int choice = getResponse(responses);

		switch (choice) {

		case 1:
			say("A basketball? In MY house? " + "You kids come up with the strangest excuses!");
			break;

		case 2:
			say("Those doors are locked for a reason! " + "Especially the basement. " + "Stay away from there!");
			break;

		case 3:
			say("OF COURSE IT'S REAL! " + "I've been growing this masterpiece for years!");
			break;

		case 0:
			say("Hey! I'm talking to you!");
			break;

		default:
			say("I have no idea what you're talking about!");
		}
	}
}
