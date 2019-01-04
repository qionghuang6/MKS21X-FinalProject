public class Move {
        //Instance Variables:
        private String name;
        private String type;
        private int pp;
        private int levelRequirement;
        private int baseDamage;

        //Constructor:
        public Move(String n, String t, int p, int l, int b) {
                name = n;
                type = t;
                pp = p;
                levelRequirement = l;
                baseDamage = b;
        }

}
