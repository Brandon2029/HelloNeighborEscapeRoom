package game;

import java.io.*;
import java.util.*;
import javax.swing.SwingUtilities;

public class Game {

	public static HashMap<String, String> roomDescriptions = new HashMap<>();

	private static Room currentRoom;
	private static ArrayList<Item> inventory = new ArrayList<>();
	private static GameGUI gui;
	private static final String SAVE_FILE = "save0.sav";

	public static Room getCurrentRoom() {
		return currentRoom;
	}

	public static void print(Object value) {
		if (gui != null)
			gui.appendOutput(String.valueOf(value));
		else
			System.out.println(value);
	}

	public static void loadDescriptions() {
		roomDescriptions.clear();

		try (Scanner file = new Scanner(new File("roomDescriptions.txt"))) {

			while (file.hasNextLine()) {
				String name = file.nextLine().trim();

				if (name.isEmpty())
					continue;

				StringBuilder description = new StringBuilder();

				while (file.hasNextLine()) {
					String line = file.nextLine();

					if (line.trim().equals("#"))
						break;

					if (description.length() > 0)
						description.append("\n");

					description.append(line);
				}

				roomDescriptions.put(name, description.toString().trim());
			}

		} catch (FileNotFoundException e) {
			print("Could not find roomDescriptions.txt. " + "Put it in the main project folder.");
		}
	}

	public static void startGame() {
		loadDescriptions();
		currentRoom = World.buildWorld();
		inventory.clear();

		print("HELLO NEIGHBOR: ESCAPE ROOM");
		print("Explore the neighbor's house and unlock its hidden rooms.");
		print("Type help to see commands.");
		print("\n" + currentRoom);
	}

	public static void move(String direction) {
		int exit;

		switch (direction) {
		case "east":
			exit = 0;
			break;
		case "west":
			exit = 1;
			break;
		case "north":
			exit = 2;
			break;
		case "south":
			exit = 3;
			break;
		case "up":
			exit = 4;
			break;
		case "down":
			exit = 5;
			break;
		default:
			print("That is not a valid direction.");
			return;
		}

		Room next = currentRoom.getExit(exit);

		if (next == null) {
			print("You can't go that way.");
		} else if (next.isLocked()) {
			print("That room is locked. Find an item to unlock it!");
		} else {
			currentRoom = next;
			print("\n" + currentRoom);
		}
	}

	public static void takeItem(String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			print("There is no " + name + " here.");
		} else if (item.isHeavy()) {
			print("You can't lift the " + name + "!");
		} else {
			inventory.add(item);
			currentRoom.removeItem(name);
			print("You take the " + name + " and carry it with you!");
		}
	}

	public static void printInventory() {
		if (inventory.isEmpty()) {
			print("You are carrying nothing.");
		} else {
			print("You are carrying:");

			for (Item item : inventory) {
				print("- " + item);
			}
		}
	}

	public static void useItem(String name) {
		Item item = findInventoryItem(name);

		if (item == null)
			print("You don't have the " + name + " in your inventory.");
		else
			item.use();
	}

	private static Item findInventoryItem(String name) {
		for (Item item : inventory) {
			if (item.getName().equalsIgnoreCase(name))
				return item;
		}

		return null;
	}

	// Lab 12: Open an item in the current room.
	public static void openItem(String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			print("There is no " + name + " here to open.");
			return;
		}

		item.open();
	}

	// Lab 12: Close an item in the current room.
	public static void closeItem(String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			print("There is no " + name + " here to close.");
			return;
		}

		item.close();
	}

	// Lab 12: Read an item in the inventory.
	public static void readItem(String name) {
		Item item = findInventoryItem(name);

		if (item == null) {
			print("You must carry the " + name + " before you can read it.");
			return;
		}

		item.read();
	}

	// Lab 10: Save game progress.
	public static void saveGame() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {

			out.writeObject(currentRoom);
			out.writeObject(inventory);
			out.writeObject(World.rooms);

			print("Game saved successfully!");

		} catch (IOException e) {
			print("Error saving game: " + e.getMessage());
		}
	}

	// Lab 10: Load game progress.
	@SuppressWarnings("unchecked")
	public static void loadGame() {
		File save = new File(SAVE_FILE);

		if (!save.exists()) {
			print("No saved game was found.");
			return;
		}

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(save))) {

			Room loadedRoom = (Room) in.readObject();

			ArrayList<Item> loadedInventory = (ArrayList<Item>) in.readObject();

			ArrayList<Room> loadedRooms = (ArrayList<Room>) in.readObject();

			currentRoom = loadedRoom;
			inventory = loadedInventory;

			World.rooms.clear();
			World.rooms.addAll(loadedRooms);

			print("Game loaded successfully!");
			print("\n" + currentRoom);
			printInventory();

		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			print("Error loading game: " + e.getMessage());
		}
	}

	// Lab 9: GUI command handler.
	public static void runGameGUI(String command) {
		processCommand(command);
	}

	public static boolean processCommand(String command) {
		if (currentRoom == null) {
			print("Start the game first.");
			return true;
		}

		command = command.toLowerCase().trim().replaceAll("\\b(the|to)\\b", "").trim().replaceAll("\\s+", " ");

		if (command.isEmpty())
			return true;

		String[] words = command.split(" ");
		String action = words[0];

		switch (action) {

		case "east":
		case "west":
		case "north":
		case "south":
		case "up":
		case "down":
			move(action);
			break;

		case "go":
			if (words.length < 2)
				print("Go where?");
			else
				move(words[1]);
			break;

		case "take":
			if (words.length < 2)
				print("Take what?");
			else
				takeItem(words[1]);
			break;

		case "use":
			if (words.length < 2)
				print("Use what?");
			else
				useItem(words[1]);
			break;

		case "open":
			if (words.length < 2)
				print("Open what?");
			else
				openItem(words[1]);
			break;

		case "close":
			if (words.length < 2)
				print("Close what?");
			else
				closeItem(words[1]);
			break;

		case "read":
			if (words.length < 2)
				print("Read what?");
			else
				readItem(words[1]);
			break;

		case "i":
		case "inventory":
			printInventory();
			break;

		case "look":
			print("\n" + currentRoom);
			break;

		case "save":
			saveGame();
			break;

		case "load":
			loadGame();
			break;

		case "help":
			print("Commands: go <direction>, " + "take <item>, use <item>, " + "open <item>, close <item>, "
					+ "read <item>, i, look, " + "save, load, exit");
			break;

		case "exit":
			print("Thanks for playing Hello Neighbor!");
			return false;

		default:
			print("That is not a valid command.");
		}

		return true;
	}

	// Original console game.
	public static void runGame() {
		startGame();

		try (Scanner input = new Scanner(System.in)) {
			while (input.hasNextLine()) {
				print("Enter a command:");

				if (!processCommand(input.nextLine()))
					break;
			}
		}
	}

	// Launch the GUI.
	public static void launchGUI() {
		SwingUtilities.invokeLater(() -> {
			gui = new GameGUI(command -> {
				if (!processCommand(command))
					gui.setCommandEnabled(false);
			});

			gui.setVisible(true);
			startGame();
		});
	}

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("console")) {
			runGame();
		} else {
			launchGUI();
		}
	}
}
