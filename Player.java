public class Player extends Pokemon {
        //Instance Variables:
        private Pokemon player;
        private Pokemon partner;
        private int gold;
        //private ArrayList<Pokemon> startingOptions: Starting pokemon the player can choose. Should be defined inside of the main program.

        //Constructor:
        public Player(String n, String t, int h, int c, int l) {
                super(n,t,h,c,l);
        }

        //Accessor methods:
        public Pokemon getPlayer() {
                return player;
        }

        public Pokemon getPartner() {
                return partner;
        }

        public Pokemon getGold() {
                return gold;
        }

        //Mutator methods:
        //This method would only be used at the start of the program.
        public setPlayer(Pokemon p) {
                player = p;
        }

        public setPartner(Pokemon p) {
                partner = p;
        }

        public addGold(int a) {
                gold += a;
        }

        public decreaseGold(int d) {
                gold -= d;
        }
}
