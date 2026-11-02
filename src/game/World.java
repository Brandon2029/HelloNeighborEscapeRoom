package game;

import java.util.ArrayList;

public class World {

	public static ArrayList<Room> rooms = new ArrayList<>();

	public static Room buildWorld() {
		rooms.clear();

		Room basement = new Room("Basement");
		Room kitchen = new Room("Kitchen");
		Room livingRoom = new Room("Living Room");
		Room garage = new Room("Garage");
		Room bedroom1 = new Room("Bedroom 1");
		Room bedroom2 = new Room("Bedroom 2");
		Room attic = new Room("Attic");

		// Connect the rooms.
		// east = 0, west = 1
		// north = 2, south = 3
		// up = 4, down = 5

		livingRoom.addExit(kitchen, 0);
		kitchen.addExit(livingRoom, 1);

		livingRoom.addExit(garage, 1);
		garage.addExit(livingRoom, 0);

		livingRoom.addExit(bedroom1, 4);
		bedroom1.addExit(livingRoom, 5);

		bedroom1.addExit(bedroom2, 0);
		bedroom2.addExit(bedroom1, 1);

		bedroom2.addExit(attic, 4);
		attic.addExit(bedroom2, 5);

		kitchen.addExit(basement, 5);
		basement.addExit(kitchen, 4);

		// Lab 11: Lock three rooms.
		garage.setLocked(true);
		attic.setLocked(true);
		basement.setLocked(true);

		// Living Room items.
		livingRoom.addItem(new Key());
		livingRoom.addItem(new Item("musicbox"));
		livingRoom.addItem(new Item("couch", true));

		// Kitchen items.
		kitchen.addItem(new Item("flashlight"));
		kitchen.addItem(new Item("knife"));
		kitchen.addItem(new Item("orb"));
		kitchen.addItem(new BasementKey());

		// Bedroom 1 items.
		bedroom1.addItem(new Crowbar());
		bedroom1.addItem(new Item("bed", true));

		// Bedroom 2 items.
		bedroom2.addItem(new Item("note"));

		// Garage item.
		garage.addItem(new Item("toolbox", true));

		// Basement item.
		basement.addItem(new Item("crate", true));

		// Attic item.
		attic.addItem(new Item("chest", true));

		return livingRoom;
	}
}