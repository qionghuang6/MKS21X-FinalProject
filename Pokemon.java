import java.util.ArrayList;

public class Pokemon {
        //Instance Variables:
        private String name;
        private String type;
        private int hp;
        private int color;
        private int level;
        private int exp;
        private ArrayList<Move> moveset;

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

        //Mutator methods:
        public void addMove(Move n) {
                moveset.add(n);
        }

        public void addMove(String n, String t, int p, int l, int b) {
                moveset.add(new Move(n, t, p, l, b));
        }
}
