package examplegame;

import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextArea output;
	private JTextField input;

	public GameGUI() {
		super("CSCI 257 - Example Adventure Game");

		setSize(750, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		output = new JTextArea();
		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setFont(new Font("Monospaced", Font.PLAIN, 14));
		output.setBackground(new Color(25, 30, 40));
		output.setForeground(Color.WHITE);
		output.setMargin(new Insets(12, 12, 12, 12));

		JScrollPane scroll = new JScrollPane(output);

		input = new JTextField();
		input.setFont(new Font("SansSerif", Font.PLAIN, 15));

		JButton enter = new JButton("Enter");

		JPanel bottom = new JPanel(new BorderLayout(8, 0));
		bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		bottom.add(input, BorderLayout.CENTER);
		bottom.add(enter, BorderLayout.EAST);

		add(scroll, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		input.addActionListener(e -> submitCommand());
		enter.addActionListener(e -> submitCommand());
	}

	private void submitCommand() {
		String command = input.getText().trim();

		if (command.isEmpty()) {
			return;
		}

		input.setText("");
		appendText("> " + command);

		Game.runGameGUI(command);
		input.requestFocusInWindow();
	}

	public void appendText(String text) {
		output.append(text + "\n");
		output.setCaretPosition(output.getDocument().getLength());
	}
}
