package game;

public class Key extends Item {

    private static final long serialVersionUID = 1L;

    public Key() {
        super("key");
    }

    @Override
    public void use() {
        Room current = Game.getCurrentRoom();

        if (!current.getName().equals("Living Room")) {
            Game.print("You don't see a garage lock "
                    + "you can reach from here.");
            return;
        }

        Room garage = current.getExit(1);

        if (!garage.isLocked()) {
            Game.print("The garage is already unlocked.");
        } else {
            garage.setLocked(false);
            Game.print("You turn the key in the garage "
                    + "door. It clicks open!");
        }
    }
}
