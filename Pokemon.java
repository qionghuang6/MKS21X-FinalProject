import java.util.ArrayList;

public class Pokemon {
        //Instance Variables:
        private String name;
        private String type;
        private String symbol;
        private int hp;
        private int color;
        private int level;
        private int exp;
        private int x;
        private int y;
        private ArrayList<Move> moveset;

        //Constructor:
        public Pokemon(String n, String t, String s, int h, int c, int l) {
               exp = 0;
               name = n;
               type = t;
               hp = h;
               color = c;
               level = l;
               symbol = s;
               moveset = new ArrayList<Move>();
               x = 0;
               y = 0;
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

        public String getSymbol() {
                return symbol;
        }

        public ArrayList<Move> getMoveset() {
                return moveset;
        }

        //Mutator methods:
        public void addMove(Move n) {
                moveset.add(n);
        }

        public void addMove(String n, String t, int p, int l, int b) {
                moveset.add(new Move(n, t, p, l, b));
        }

        public void setLocation(int x, int y) {
                this.x = x;
                this.y = y;
        }

        public void loseHp(int num) {
                hp -= num;
        }

        public void healHp(int num) {
                hp += num;
        }

        public void setSymbol(String s) {
                symbol = s;
        }

        //toString()
        public String toString() {
                return getName() + " LVL:" + getLevel() + " HP:" + getHp() + " EXP:" + getExp();
        }
}
