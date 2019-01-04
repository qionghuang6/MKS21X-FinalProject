public class Pokemon {
        //Instance Variables:
        private String name;
        private String type;
        private int hp;
        private int color;
        private int level;
        private int exp;
        //private List<Move> moveset; //Remember to import List.java! 

        //Constructor:
        public Pokemon(String n, String t, int h, int c, int l) {
               exp = 0;
               name = n;
               type = t;
               hp = h;
               color = c;
               level = l;
        }

        //Accessor Methods:
        public String getName() {
                return name;
        }

        public String getType() {
                return type;
        }

        public int getHp() {
                return hp;
        }

        public int getColor() {
                return color;
        }

        public int getExp() {
                return exp;
        }

        public int getLevel() {
                return level;
        }
}
