package examplegame;

import java.io.Serializable;
import java.util.HashMap;

public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Room[] exits;
	private HashMap<String, Item> items;
	private HashMap<String, NPC> npcs;
	private boolean locked;

	public Room(String name) {
		this.name = name;
		exits = new Room[6];
		items = new HashMap<>();
		npcs = new HashMap<>();
		locked = false;

		World.rooms.put(name, this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return Game.roomDescriptions.getOrDefault(name, "No description available.");
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

	public boolean isLocked() {
		return locked;
	}

	public boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
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

	public void addNPC(NPC npc) {
		npcs.put(npc.getName().toLowerCase(), npc);
	}

	public NPC getNPC(String name) {
		return npcs.get(name.toLowerCase());
	}

	@Override
	public String toString() {
		String result = name + "\n" + getDescription();

		if (!items.isEmpty()) {
			result += "\n\nItems here:";
			for (Item item : items.values()) {
				result += "\n- " + item.getName();
			}
		}

		if (!npcs.isEmpty()) {
			result += "\n\nPeople here:";
			for (NPC npc : npcs.values()) {
				result += "\n- " + npc.getName();
			}
		}

		return result;
	}
}
