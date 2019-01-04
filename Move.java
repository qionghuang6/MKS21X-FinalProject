public class Move {
        //Instance Variables:
        private String name;
        private String type;
        private int currentPp;
        private int pp;
        private int levelRequirement;
        private int baseDamage;

        //Constructor:
        public Move(String n, String t, int p, int l, int b) {
                name = n;
                type = t;
                currentPp = p;
                pp = p;
                levelRequirement = l;
                baseDamage = b;
        }

        //Accessor Methods:
        public String getName() {
                return name;
        }

        public String getType() {
                return type;
        }

        public int getPp() {
                return pp;
        }

        public int getCurrentPp() {
                return currentPp;
        }

        public int getLevelRequirement() {
                return levelRequirement;
        }

        public int getBaseDamage() {
                return baseDamage;
        }

        //Mutator Methods:
        private void decreasePP() {
               currentPp--; 
        }

        private void restorePP() {
               currentPp++; 
        }
}
