public class Player {
        //Instance Variables:
        private Pokemon player;
        private Pokemon partner;
        private int gold;
        //private ArrayList<Pokemon> startingOptions: Starting pokemon the player can choose. Should be defined inside of the main program.

        //Constructor:

        //Takes in a pokemon, set its variables = to it. Other params are for partner & gold.
        public Player(Pokemon p, Pokemon pt, int g) {
                player = p;
                partner = pt;
                gold = g;
        }

        //Accessor methods:

        public Pokemon getPlayer() {
                return player;
        }

        public Pokemon getPartner() {
                return partner;
        }

        public int getGold() {
                return gold;
        }

        //Mutator methods:
        //This method would only be used at the start of the program.
        public void setPlayer(Pokemon p) {
                player = p;
        }

        public void setPartner(Pokemon p) {
                partner = p;
        }

        public void addGold(int a) {
                gold += a;
        }

        public void decreaseGold(int d) {
                gold -= d;
        }

        public void moveLeft() {
              player.setLocation(player.getX() - 1, player.getY());
        }

        public void moveRight() {
              player.setLocation(player.getX() + 1, player.getY());
        }

        public void moveUp() {
              player.setLocation(player.getX(), player.getY() + 1);
        }

        public void moveDown() {
              player.setLocation(player.getX(), player.getY() - 1);
        }
}
