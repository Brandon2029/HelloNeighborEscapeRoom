package game;

public class Neighbor extends NPC {

	private static final long serialVersionUID = 1L;

	// Lab 14: Track the conversation.
	private int convoNumber = 0;

	public Neighbor() {
		super("neighbor");
	}

	@Override
	public void talk() {

		switch (convoNumber) {

		case 0:
			firstConversation();
			break;

		case 1:
			basketballConversation();
			break;

		case 2:
			lockedDoorsConversation();
			break;

		case 3:
			mustacheConversation();
			break;

		case 4:
			basketballEnding();
			break;

		case 5:
			basementEnding();
			break;

		case 6:
			atticEnding();
			break;

		case 7:
			funnyEnding();
			break;

		case 8:
			angryEnding();
			break;

		default:
			say("I have nothing else to tell you!");
			break;
		}
	}

	// Conversation 0: First meeting.
	public void firstConversation() {

		say("HEY! What are you doing in my garage?!");

		String[] responses = { "I'm looking for my missing basketball.", "Why do you keep locking all your doors?",
				"Nice mustache! Is it real?" };

		int choice = getResponse(responses);

		switch (choice) {

		case 1:
			say("A basketball? In MY house? " + "You kids always have strange excuses!");
			convoNumber = 1;
			break;

		case 2:
			say("Those doors are locked for a reason! " + "Especially the basement!");
			convoNumber = 2;
			break;

		case 3:
			say("OF COURSE IT'S REAL! " + "I've been growing it for years!");
			convoNumber = 3;
			break;

		default:
			say("Hey! Don't ignore me!");
			break;
		}
	}

	// Conversation 1: Basketball story.
	public void basketballConversation() {

		say("You're still looking for that basketball? " + "I might know where it is.");

		String[] responses = { "Can you tell me where it is?", "Did you steal my basketball?",
				"Never mind. I don't need it." };

		int choice = getResponse(responses);

		switch (choice) {

		case 1:
			say("I saw something round in the kitchen. " + "Maybe that's what you're looking for!");
			convoNumber = 4;
			break;

		case 2:
			say("STEAL IT?! How dare you accuse me! " + "Get out of my garage!");
			convoNumber = 8;
			break;

		case 3:
			say("Good! Now stop bothering me!");
			convoNumber = 4;
			break;

		default:
			say("Are you going to answer me?");
			break;
		}
	}

	// Conversation 2: Locked doors.
	public void lockedDoorsConversation() {

		say("You really want to know why " + "I keep those doors locked?");

		String[] responses = { "What's hidden in the basement?", "What's inside the attic?",
				"I think you're hiding something!" };

		int choice = getResponse(responses);

		switch (choice) {

		case 1:
			say("The basement holds things " + "that should never be disturbed. "
					+ "But if you insist, look for a strange orb.");
			convoNumber = 5;
			break;

		case 2:
			say("The attic contains an old chest. " + "I haven't opened it in years!");
			convoNumber = 6;
			break;

		case 3:
			say("HIDING SOMETHING?! " + "You have no proof! Leave me alone!");
			convoNumber = 8;
			break;

		default:
			say("You don't seem very interested.");
			break;
		}
	}

	// Conversation 3: Mustache.
	public void mustacheConversation() {

		say("You seem very interested in my mustache!");

		String[] responses = { "How long did it take to grow?", "Can I have a mustache like yours?",
				"Actually, I think it looks fake." };

		int choice = getResponse(responses);

		switch (choice) {

		case 1:
			say("YEARS! A great mustache " + "takes patience and dedication!");
			convoNumber = 7;
			break;

		case 2:
			say("Of course! But you'll need " + "a lot of patience!");
			convoNumber = 7;
			break;

		case 3:
			say("FAKE?! This conversation is OVER!");
			convoNumber = 8;
			break;

		default:
			say("My mustache deserves respect!");
			break;
		}
	}

	// Conversation 4: Basketball ending.
	public void basketballEnding() {
		say("I already told you everything " + "I know about your basketball!");
	}

	// Conversation 5: Basement ending.
	public void basementEnding() {
		say("Remember what I said about the basement. " + "Some secrets are better left alone!");
	}

	// Conversation 6: Attic ending.
	public void atticEnding() {
		say("If you're going into the attic, " + "be careful with that old chest!");
	}

	// Conversation 7: Funny ending.
	public void funnyEnding() {
		say("Remember, kid. A great mustache " + "is a work of art!");
	}

	// Conversation 8: Angry ending.
	public void angryEnding() {
		say("I'm not talking to you anymore! " + "GET OUT OF MY GARAGE!");
	}
}
