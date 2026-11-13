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

		// Directions:
		// east=0, west=1, north=2
		// south=3, up=4, down=5

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

		// Lab 11: Locked rooms.
		garage.setLocked(true);
		attic.setLocked(true);
		basement.setLocked(true);

		// Living Room.
		livingRoom.addItem(new Key());
		livingRoom.addItem(new MusicBox());
		livingRoom.addItem(new Item("couch", true));

		// Kitchen.
		kitchen.addItem(new Item("flashlight"));
		kitchen.addItem(new Item("knife"));
		kitchen.addItem(new Orb());
		kitchen.addItem(new BasementKey());

		// Bedroom 1.
		bedroom1.addItem(new Crowbar());
		bedroom1.addItem(new Item("bed", true));

		// Bedroom 2.
		bedroom2.addItem(new Note());

		// Garage.
		garage.addItem(new Item("toolbox", true));

		// Basement.
		basement.addItem(new Item("crate", true));

		// Attic.
		attic.addItem(new Chest());

		// Lab 13: Place the neighbor.
		garage.addNPC(new Neighbor());

		return livingRoom;
	}
}
