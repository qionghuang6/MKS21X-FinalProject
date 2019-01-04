public class Player {
        //Instance Variables:
        private Pokemon player;
        private Pokemon partner;
        private int gold;
        //private ArrayList<Pokemon> startingOptions: Starting pokemon the player can choose. Should be defined inside of the main program.

        //Constructor:
        //Only defines the pokemon the player is using.
        public Player(String n, String t, String s, int h, int c, int l) {
                player = new Pokemon(n,t,s,h,c,l);
        }

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
}
