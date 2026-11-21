package examplegame;

public class Fred extends NPC {

	private static final long serialVersionUID = 1L;

	private int convoNumber;
	private boolean receivedMoney;

	public Fred() {
		super("fred");
		convoNumber = 0;
		receivedMoney = false;
	}

	@Override
	public void talk() {
		if (receivedMoney) {
			say("Stop bothering me. I already gave you the combination!");
			return;
		}

		switch (convoNumber) {
		case 0:
			firstConversation();
			break;

		case 1:
			say("What are you still doing here?");
			convoNumber = 0;
			break;

		case 2:
			secondConversation();
			break;

		case 3:
			say("Where's my money? I want my money!");
			break;

		default:
			say("Leave me alone.");
		}
	}

	private void firstConversation() {
		say("What do you want, loser?");

		String[] responses = { "Nothing! Sorry to bother you!", "Why are you always in the kitchen?",
				"Hey, got the safe combination?" };

		int option = getResponse(responses);

		switch (option) {
		case 1:
			say("Then get out of here!");
			convoNumber = 1;
			break;

		case 2:
			say("Because I'm hungry. Why else?");
			convoNumber = 2;
			break;

		case 3:
			say("Yeah. If you want it, give me $20.");
			convoNumber = 3;
			break;

		default:
			say("What are you talking about?");
		}
	}

	private void secondConversation() {
		say("You're back? Do you have something useful to say?");

		String[] responses = { "I'm just looking around.", "Can you help me get into the garage?",
				"Do you know the safe combination?" };

		int option = getResponse(responses);

		switch (option) {
		case 1:
			say("Then go look somewhere else.");
			convoNumber = 1;
			break;

		case 2:
			say("Maybe. First, you need what's in the safe.");
			convoNumber = 2;
			break;

		case 3:
			say("Of course I do. Bring me $20.");
			convoNumber = 3;
			break;

		default:
			say("That doesn't make sense.");
		}
	}

	@Override
	public void give(Item item) {
		if (!item.getName().equalsIgnoreCase("money")) {
			say("I don't want the " + item.getName() + ".");
			return;
		}

		if (receivedMoney) {
			say("You already paid me!");
			return;
		}

		Game.removeInventoryItem("money");

		receivedMoney = true;
		convoNumber = 4;

		Game.print("You hand Fred $20.");

		say("Thanks, poser. Here's the combination.");

		Game.addInventoryItem(new Item("combination"));

		Game.print("Fred hands you the safe combination.");
		Game.print("Combination added to inventory!");
	}
}
