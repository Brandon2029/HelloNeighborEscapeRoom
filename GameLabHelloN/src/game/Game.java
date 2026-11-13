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
				String name = file.nextLine().trim();

				if (name.isEmpty()) {
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

				roomDescriptions.put(name, description.toString().trim());
			}

		} catch (FileNotFoundException e) {
			print("Could not find roomDescriptions.txt.");
		}
	}

	public static void startGame() {
		loadDescriptions();
		currentRoom = World.buildWorld();
		inventory.clear();

		print("HELLO NEIGHBOR: ESCAPE ROOM");
		print("Explore the neighbor's mysterious house.");
		print("Type help to see the commands.");
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
			print("You take the " + name + ".");
		}
	}

	public static void addInventoryItem(Item item) {
		inventory.add(item);
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

	private static Item findInventoryItem(String name) {
		for (Item item : inventory) {
			if (item.getName().equalsIgnoreCase(name)) {
				return item;
			}
		}

		return null;
	}

	public static void useItem(String name) {
		Item item = findInventoryItem(name);

		if (item == null) {
			print("You don't have the " + name + ".");
		} else {
			item.use();
		}
	}

	public static void openItem(String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			print("There is no " + name + " here to open.");
		} else {
			item.open();
		}
	}

	public static void closeItem(String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			print("There is no " + name + " here to close.");
		} else {
			item.close();
		}
	}

	public static void readItem(String name) {
		Item item = findInventoryItem(name);

		if (item == null) {
			print("You must carry the " + name + " before you can read it.");
		} else {
			item.read();
		}
	}

	// Lab 13: Talk to an NPC.
	public static void talkToNPC(String name) {
		NPC npc = currentRoom.getNPC(name);

		if (npc == null) {
			print("There is no " + name + " here to talk to.");
		} else {
			npc.talk();
		}
	}

	// Lab 10: Save progress.
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

	// Lab 10: Load progress.
	@SuppressWarnings("unchecked")
	public static void loadGame() {
		File file = new File(SAVE_FILE);

		if (!file.exists()) {
			print("No saved game was found.");
			return;
		}

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {

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

	public static void runGameGUI(String command) {
		processCommand(command);
	}

	public static boolean processCommand(String command) {
		if (currentRoom == null) {
			print("Start the game first.");
			return true;
		}

		command = command.toLowerCase().trim().replaceAll("\\b(the|to)\\b", "").trim().replaceAll("\\s+", " ");

		if (command.isEmpty()) {
			return true;
		}

		String[] words = command.split(" ");
		String action = words[0];
		String target = words.length > 1 ? String.join(" ", Arrays.copyOfRange(words, 1, words.length)) : "";

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
			if (target.isEmpty()) {
				print("Go where?");
			} else {
				move(target);
			}
			break;

		case "take":
			if (target.isEmpty()) {
				print("Take what?");
			} else {
				takeItem(target);
			}
			break;

		case "use":
			if (target.isEmpty()) {
				print("Use what?");
			} else {
				useItem(target);
			}
			break;

		case "open":
			if (target.isEmpty()) {
				print("Open what?");
			} else {
				openItem(target);
			}
			break;

		case "close":
			if (target.isEmpty()) {
				print("Close what?");
			} else {
				closeItem(target);
			}
			break;

		case "read":
			if (target.isEmpty()) {
				print("Read what?");
			} else {
				readItem(target);
			}
			break;

		case "talk":
			if (target.isEmpty()) {
				print("Talk to who?");
			} else {
				talkToNPC(target);
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
			print("Commands:");
			print("go <direction>");
			print("take <item>");
			print("use <item>");
			print("open <item>");
			print("close <item>");
			print("read <item>");
			print("talk <character>");
			print("i, look, save, load, help, exit");
			break;

		case "exit":
			print("Thanks for playing Hello Neighbor!");
			return false;

		default:
			print("That is not a valid command.");
		}

		return true;
	}

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
