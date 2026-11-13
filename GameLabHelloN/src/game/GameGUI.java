package game;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JTextArea output = new JTextArea();
	private final JTextField commandInput = new JTextField();
	private final JButton submit = new JButton("Enter");

	public GameGUI(Consumer<String> commandHandler) {
		super("Hello Neighbor - Escape Room");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(760, 570);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		getContentPane().setBackground(new Color(25, 30, 40));

		JLabel heading = new JLabel("HELLO NEIGHBOR | ESCAPE ROOM", SwingConstants.CENTER);

		heading.setFont(new Font("SansSerif", Font.BOLD, 23));
		heading.setForeground(new Color(238, 203, 115));
		heading.setBorder(BorderFactory.createEmptyBorder(16, 8, 8, 8));
		add(heading, BorderLayout.NORTH);

		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setFont(new Font("Monospaced", Font.PLAIN, 15));
		output.setBackground(new Color(19, 24, 32));
		output.setForeground(new Color(225, 235, 240));
		output.setMargin(new Insets(14, 14, 14, 14));

		JScrollPane scroll = new JScrollPane(output);
		scroll.setBorder(BorderFactory.createEmptyBorder(4, 14, 4, 14));
		add(scroll, BorderLayout.CENTER);

		JPanel controls = new JPanel(new BorderLayout(8, 8));
		controls.setBorder(BorderFactory.createEmptyBorder(6, 14, 14, 14));
		controls.setBackground(new Color(25, 30, 40));

		commandInput.setFont(new Font("SansSerif", Font.PLAIN, 16));
		controls.add(commandInput, BorderLayout.CENTER);

		submit.setFont(new Font("SansSerif", Font.BOLD, 15));
		controls.add(submit, BorderLayout.EAST);
		add(controls, BorderLayout.SOUTH);

		Runnable send = () -> {
			String command = commandInput.getText().trim();
			commandInput.setText("");

			if (!command.isEmpty()) {
				appendOutput("> " + command);
				commandHandler.accept(command);
			}

			commandInput.requestFocusInWindow();
		};

		commandInput.addActionListener(e -> send.run());
		submit.addActionListener(e -> send.run());
	}

	public void appendOutput(String text) {
		output.append(text + "\n");
		output.setCaretPosition(output.getDocument().getLength());
	}

	public void setCommandEnabled(boolean enabled) {
		commandInput.setEnabled(enabled);
		submit.setEnabled(enabled);
	}
}
