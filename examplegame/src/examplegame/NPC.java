
package examplegame;

import java.io.Serializable;
import javax.swing.JOptionPane;

public class NPC implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public NPC(String name) {
		this.name = name.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public void say(String message) {
		Game.print("\n" + name.toUpperCase());
		Game.print(message);
	}

	public void talk() {
		Game.print("You can't talk to " + name + ".");
	}

	public int getResponse(String[] responses) {
		StringBuilder options = new StringBuilder();

		for (int i = 0; i < responses.length; i++) {
			options.append(i + 1).append(") ").append(responses[i]).append("\n");
		}

		String input = JOptionPane.showInputDialog(null, options.toString() + "\nChoose an option:",
				"Talking to " + name, JOptionPane.QUESTION_MESSAGE);

		if (input == null) {
			return 0;
		}

		try {
			return Integer.parseInt(input.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public void give(Item item) {
		say("I don't want the " + item.getName() + ".");
	}
}
