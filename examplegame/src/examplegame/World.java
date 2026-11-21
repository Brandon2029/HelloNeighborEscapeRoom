package examplegame;

import java.util.HashMap;

public class World {

	public static HashMap<String, Room> rooms = new HashMap<>();

	public static Room buildWorld() {
		rooms.clear();

		Room livingRoom = new Room("Living Room");
		Room office = new Room("Office");
		Room kitchen = new Room("Kitchen");
		Room bedroom = new Room("Bedroom");
		Room attic = new Room("Attic");
		Room garage = new Room("Garage");

		livingRoom.addExit(kitchen, 0);
		livingRoom.addExit(office, 1);
		livingRoom.addExit(bedroom, 2);

		office.addExit(livingRoom, 0);
		office.addExit(garage, 3);

		kitchen.addExit(livingRoom, 1);
		kitchen.addExit(garage, 3);

		bedroom.addExit(livingRoom, 3);
		bedroom.addExit(attic, 4);

		attic.addExit(bedroom, 5);

		garage.addExit(kitchen, 2);
		garage.addExit(office, 4);

		office.setLocked(true);
		attic.setLocked(true);
		garage.setLocked(true);

		livingRoom.addItem(new Tool());
		livingRoom.addItem(new Item("musicbox"));
		livingRoom.addItem(new Item("couch", true));

		kitchen.addItem(new Item("flashlight"));
		kitchen.addItem(new Item("knife"));
		kitchen.addItem(new Item("orb"));

		bedroom.addItem(new OfficeKey());
		bedroom.addItem(new Item("bed", true));
		bedroom.addItem(new Item("money"));

		attic.addItem(new Safe());

		office.addItem(new Item("computer", true));

		kitchen.addNPC(new Fred());

		return livingRoom;
	}
}
