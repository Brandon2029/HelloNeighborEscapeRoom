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
		if (gui != null) {
			gui.appendOutput(String.valueOf(value));
		} else {
			System.out.println(value);
		}
	}

	public static void loadDescriptions() {
		roomDescriptions.clear();

		try (Scanner file = new Scanner(new File("roomDescriptions.txt"))) {

			while (file.hasNextLine()) {
				String roomName = file.nextLine().trim();

				if (roomName.isEmpty()) {
					continue;
				}

				StringBuilder description = new StringBuilder();

				while (file.hasNextLine()) {
					String line = file.nextLine();

					if (line.trim().equals("#")) {
						break;
					}

					if (description.length() > 0) {
						description.append("\n");
					}

					description.append(line);
				}

				roomDescriptions.put(roomName, description.toString().trim());
			}

		} catch (FileNotFoundException e) {
			print("Could not find roomDescriptions.txt.");
			print("Put it in the main project folder.");
		}
	}

	public static void startGame() {
		loadDescriptions();
		currentRoom = World.buildWorld();
		inventory.clear();

		print("HELLO NEIGHBOR: ESCAPE ROOM");
		print("Explore the neighbor's house and unlock its hidden rooms.");
		print("Type help to see the commands.");
		print("\n" + currentRoom);
	}

	public static void move(String direction) {
		int exitNumber;

		switch (direction) {
		case "east":
			exitNumber = 0;
			break;
		case "west":
			exitNumber = 1;
			break;
		case "north":
			exitNumber = 2;
			break;
		case "south":
			exitNumber = 3;
			break;
		case "up":
			exitNumber = 4;
			break;
		case "down":
			exitNumber = 5;
			break;
		default:
			print("That is not a valid direction.");
			return;
		}

		Room nextRoom = currentRoom.getExit(exitNumber);

		if (nextRoom == null) {
			print("You can't go that way.");
		} else if (nextRoom.isLocked()) {
			print("That room is locked. Find an item to unlock it!");
		} else {
			currentRoom = nextRoom;
			print("\n" + currentRoom);
		}
	}

	public static void takeItem(String itemName) {
		Item item = currentRoom.getItem(itemName);

		if (item == null) {
			print("There is no " + itemName + " here.");
		} else if (item.isHeavy()) {
			print("You can't lift the " + item.getName() + "!");
		} else {
			inventory.add(item);
			currentRoom.removeItem(itemName);

			print("You take the " + item.getName() + " and carry it with you!");
		}
	}

	// Lab 11: Use items in the inventory.
	public static void useItem(String itemName) {
		for (Item item : inventory) {
			if (item.getName().equalsIgnoreCase(itemName)) {
				item.use();
				return;
			}
		}

		print("You don't have the " + itemName + " in your inventory.");
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

	// Lab 10: Save game.
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

	// Lab 10: Load game.
	@SuppressWarnings("unchecked")
	public static void loadGame() {
		File saveFile = new File(SAVE_FILE);

		if (!saveFile.exists()) {
			print("No saved game was found.");
			return;
		}

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile))) {

			Room savedRoom = (Room) in.readObject();

			ArrayList<Item> savedInventory = (ArrayList<Item>) in.readObject();

			ArrayList<Room> savedRooms = (ArrayList<Room>) in.readObject();

			currentRoom = savedRoom;
			inventory = savedInventory;

			World.rooms.clear();
			World.rooms.addAll(savedRooms);

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

		command = command.toLowerCase().trim();

		command = command.replaceAll("\\b(the|to)\\b", "").trim().replaceAll("\\s+", " ");

		if (command.isEmpty()) {
			return true;
		}

		String[] words = command.split(" ");

		switch (words[0]) {

		case "east":
		case "west":
		case "north":
		case "south":
		case "up":
		case "down":
			move(words[0]);
			break;

		case "go":
			if (words.length < 2) {
				print("Go where?");
			} else {
				move(words[1]);
			}
			break;

		case "take":
			if (words.length < 2) {
				print("Take what?");
			} else {
				takeItem(words[1]);
			}
			break;

		case "use":
			if (words.length < 2) {
				print("Use what?");
			} else {
				useItem(words[1]);
			}
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
			print("Commands: go east/west/north/south/up/down, " + "take <item>, use <item>, i, look, "
					+ "save, load, help, exit");
			break;

		case "exit":
			print("Thanks for playing Hello Neighbor!");
			return false;

		default:
			print("That is not a valid command.");
		}

		return true;
	}

	// Console version.
	public static void runGame() {
		startGame();

		try (Scanner input = new Scanner(System.in)) {
			while (input.hasNextLine()) {
				print("Enter a command:");

				if (!processCommand(input.nextLine())) {
					break;
				}
			}
		}
	}

	// GUI version.
	public static void launchGUI() {
		SwingUtilities.invokeLater(() -> {

			gui = new GameGUI(command -> {
				if (!processCommand(command)) {
					gui.setCommandEnabled(false);
				}
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