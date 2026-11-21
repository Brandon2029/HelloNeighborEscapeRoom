package examplegame;

import java.io.*;
import java.util.*;
import javax.swing.SwingUtilities;

public class Game {

	public static HashMap<String, String> roomDescriptions = new HashMap<>();

	private static Room currentRoom = World.buildWorld();
	private static ArrayList<Item> inventory = new ArrayList<>();
	private static GameGUI gui;

	public static Room getCurrentRoom() {
		return currentRoom;
	}

	public static void print(Object message) {
		String text = String.valueOf(message);

		if (gui != null) {
			gui.appendText(text);
		} else {
			System.out.println(text);
		}
	}

	public static void loadRoomDescriptions() {
		roomDescriptions.clear();

		try (Scanner scan = new Scanner(new File("roomDescriptions.txt"))) {

			while (scan.hasNextLine()) {
				String roomName = scan.nextLine().trim();

				if (roomName.isEmpty()) {
					continue;
				}

				StringBuilder description = new StringBuilder();

				while (scan.hasNextLine()) {
					String line = scan.nextLine();

					if (line.trim().equals("#")) {
						break;
					}

					description.append(line).append("\n");
				}

				roomDescriptions.put(roomName, description.toString().trim());
			}

		} catch (FileNotFoundException e) {
			print("Could not find roomDescriptions.txt.");
			print("Project folder: " + System.getProperty("user.dir"));
		}
	}

	public static void addInventoryItem(Item item) {
		if (!hasInventoryItem(item.getName())) {
			inventory.add(item);
		}
	}

	public static Item getInventoryItem(String name) {
		for (Item item : inventory) {
			if (item.getName().equalsIgnoreCase(name)) {
				return item;
			}
		}
		return null;
	}

	public static boolean hasInventoryItem(String name) {
		return getInventoryItem(name) != null;
	}

	public static Item removeInventoryItem(String name) {
		Item item = getInventoryItem(name);

		if (item != null) {
			inventory.remove(item);
		}

		return item;
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
			print("Invalid direction.");
			return;
		}

		Room next = currentRoom.getExit(exit);

		if (next == null) {
			print("You can't go that way.");
		} else if (next.isLocked()) {
			print("That room is locked.");
		} else {
			currentRoom = next;
			print(currentRoom);
		}
	}

	public static void takeItem(String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			print("There is no " + name + " here.");
		} else if (item.isHeavy()) {
			print("You can't lift the " + name + "!");
		} else {
			currentRoom.removeItem(name);
			addInventoryItem(item);
			print("You take the " + name + ".");
		}
	}

	public static void printInventory() {
		if (inventory.isEmpty()) {
			print("You are carrying nothing.");
			return;
		}

		print("You are carrying:");

		for (Item item : inventory) {
			print("- " + item.getName());
		}
	}

	public static void useItem(String name) {
		Item item = getInventoryItem(name);

		if (item == null) {
			print("You don't have the " + name + ".");
			return;
		}

		item.use();
	}

	public static void interact(String action, String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			item = getInventoryItem(name);
		}

		if (item == null) {
			print("You can't find the " + name + ".");
			return;
		}

		switch (action) {
		case "open":
			item.open();
			break;
		case "close":
			item.close();
			break;
		case "read":
			item.read();
			break;
		}
	}

	public static void talkToNPC(String name) {
		NPC npc = currentRoom.getNPC(name);

		if (npc == null) {
			print(name + " is not here.");
			return;
		}

		npc.talk();
	}

	public static void giveItem(String itemName, String npcName) {
		NPC npc = currentRoom.getNPC(npcName);

		if (npc == null) {
			print(npcName + " is not here.");
			return;
		}

		Item item = getInventoryItem(itemName);

		if (item == null) {
			print("You don't have the " + itemName + ".");
			return;
		}

		npc.give(item);
	}

	public static void saveGame() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save0.sav"))) {

			out.writeObject(World.rooms);
			out.writeObject(currentRoom.getName());
			out.writeObject(inventory);

			print("Game saved successfully!");

		} catch (IOException e) {
			print("Save failed: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadGame() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("save0.sav"))) {

			HashMap<String, Room> savedRooms = (HashMap<String, Room>) in.readObject();

			String savedRoomName = (String) in.readObject();

			ArrayList<Item> savedInventory = (ArrayList<Item>) in.readObject();

			Room savedCurrentRoom = savedRooms.get(savedRoomName);

			if (savedCurrentRoom == null) {
				throw new IOException("Saved room not found.");
			}

			World.rooms = savedRooms;
			currentRoom = savedCurrentRoom;
			inventory = savedInventory;

			print("Game loaded successfully!");
			print(currentRoom);

		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			print("Load failed: " + e.getMessage());
		}
	}

	public static boolean processCommand(String command) {
		command = command.toLowerCase().trim();
		command = command.replaceAll("\\b(the|to)\\b", "");
		command = command.replaceAll("\\s+", " ").trim();

		if (command.isEmpty()) {
			return true;
		}

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
			if (words.length > 1) {
				move(words[1]);
			} else {
				print("Go where?");
			}
			break;

		case "take":
			if (words.length > 1) {
				takeItem(words[1]);
			} else {
				print("Take what?");
			}
			break;

		case "use":
			if (words.length > 1) {
				useItem(words[1]);
			} else {
				print("Use what?");
			}
			break;

		case "open":
		case "close":
		case "read":
			if (words.length > 1) {
				interact(action, words[1]);
			} else {
				print(action + " what?");
			}
			break;

		case "talk":
			if (words.length > 1) {
				talkToNPC(words[1]);
			} else {
				print("Talk to whom?");
			}
			break;

		case "give":
			if (words.length >= 3) {
				giveItem(words[1], words[2]);
			} else {
				print("Use: give [item] to [NPC]");
			}
			break;

		case "i":
		case "inventory":
			printInventory();
			break;

		case "look":
			print(currentRoom);
			break;

		case "save":
			saveGame();
			break;

		case "load":
			loadGame();
			break;

		case "help":
			print("Commands:");
			print("go north/south/east/west/up/down");
			print("take [item]");
			print("use [item]");
			print("open [item]");
			print("close [item]");
			print("read [item]");
			print("talk to [NPC]");
			print("give [item] to [NPC]");
			print("i, look, save, load, exit");
			break;

		case "exit":
			print("Thanks for playing!");
			return false;

		default:
			print("Invalid command. Type help.");
		}

		return true;
	}

	public static void runGame() {
		loadRoomDescriptions();
		Scanner input = new Scanner(System.in);

		print(currentRoom);

		while (true) {
			print("Enter a command:");

			if (!input.hasNextLine()) {
				break;
			}

			if (!processCommand(input.nextLine())) {
				break;
			}
		}
	}

	public static void runGameGUI(String command) {
		processCommand(command);
	}

	public static void startGUI() {
		loadRoomDescriptions();
		gui = new GameGUI();
		gui.setVisible(true);
		print(currentRoom);
		print("Type help to see all commands.");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> startGUI());
	}
}
