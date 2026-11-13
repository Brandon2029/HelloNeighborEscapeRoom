package game;

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
		StringBuilder choices = new StringBuilder();

		for (int i = 0; i < responses.length; i++) {
			choices.append(i + 1).append(") ").append(responses[i]).append("\n");
		}

		while (true) {
			String input = JOptionPane.showInputDialog(null, choices.toString() + "\nChoose an option:",
					"Talking to " + name.toUpperCase(), JOptionPane.QUESTION_MESSAGE);

			if (input == null) {
				return 0;
			}

			try {
				int choice = Integer.parseInt(input.trim());

				if (choice >= 1 && choice <= responses.length) {
					return choice;
				}

			} catch (NumberFormatException e) {
				// Ask again if the player enters text.
			}

			JOptionPane.showMessageDialog(null, "Please enter a number from 1 to " + responses.length + ".");
		}
	}
}
