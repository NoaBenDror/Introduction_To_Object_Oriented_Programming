import oop.ex2.*;

/**
 * This class represents a space ship factory. It is responsible for creating all spaceships.
 */

public class SpaceShipFactory {

    /**
     * Constructor
     */

    public SpaceShipFactory(){}

    /**
     * Creates the spaceships in the game.
     *
     * @param args the command line arguments.
     * @return the array of spaceships.
     */

    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] spaceShips = new SpaceShip[args.length]; // create array of spaceships
        for(int i=0; i<args.length; i++){
            switch (args[i]) {
                case "h":
                    spaceShips[i] = new Human();
                    break;
                case "r":
                    spaceShips[i] = new Runner();
                    break;
                case "b":
                    spaceShips[i] = new Basher();
                    break;
                case "a":
                    spaceShips[i] = new Aggressive();
                    break;
                case "d":
                    spaceShips[i] = new Drunkard();
                    break;
                case "s":
                    spaceShips[i] = new Special();
                    break;
                default: // invalid input
                    spaceShips[i] = null;
            }
        }
        return spaceShips;
    }
}