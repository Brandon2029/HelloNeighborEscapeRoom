package game;

import java.io.Serializable;
import java.util.*;

public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Room[] exits = new Room[6];
	private HashMap<String, Item> items = new HashMap<>();
	private HashMap<String, NPC> npcs = new HashMap<>();
	private boolean locked = false;

	public Room(String name) {
		this.name = name;
		World.rooms.add(this);
	}

	public Room(String name, String description) {
		this(name);
		setDescription(description);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getDescription() {
		return Game.roomDescriptions.getOrDefault(name, "No description available for this room.");
	}

	public void setDescription(String description) {
		Game.roomDescriptions.put(name, description);
	}

	public void addExit(Room room, int direction) {
		exits[direction] = room;
	}

	public Room getExit(int direction) {
		return exits[direction];
	}

	public void addItem(Item item) {
		items.put(item.getName().toLowerCase(), item);
	}

	public Item getItem(String name) {
		return items.get(name.toLowerCase());
	}

	public Item removeItem(String name) {
		return items.remove(name.toLowerCase());
	}

	// Lab 13: Add NPC to room.
	public void addNPC(NPC npc) {
		npcs.put(npc.getName().toLowerCase(), npc);
	}

	// Lab 13: Find NPC in room.
	public NPC getNPC(String name) {
		return npcs.get(name.toLowerCase());
	}

	public String getItemsDescription() {
		if (items.isEmpty()) {
			return "There are no items here.";
		}

		ArrayList<String> names = new ArrayList<>(items.keySet());

		Collections.sort(names);

		return "You can see: " + String.join(", ", names);
	}

	public String getNPCDescription() {
		if (npcs.isEmpty()) {
			return "";
		}

		ArrayList<String> names = new ArrayList<>(npcs.keySet());

		Collections.sort(names);

		return "\nCharacters here: " + String.join(", ", names);
	}

	@Override
	public String toString() {
		return name + "\n" + getDescription() + "\n" + getItemsDescription() + getNPCDescription();
	}
}
