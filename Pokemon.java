import java.util.ArrayList;

public class Pokemon {
        //Instance Variables:
        private String name;
        private String type;
        private String symbol;
        private int hp;
        private int[] color;
        private int level;
        private int exp;
        private int x;
        private int y;
        private ArrayList<Move> moveset;

        //Constructor:
        public Pokemon(String n, String t, String s, int h, int[] c, int l) {
               exp = 0;
               name = n;
               type = t;
               hp = h;
               level = l;
               symbol = s;
               moveset = new ArrayList<Move>();
               x = 0;
               y = 0;
               //The following below statement doesn't account for values < 0 and > 255.
               //But it can't be set anyways.
               if(c.length == 3) {
                       color = c;
               }
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

        public int getX() {
                return x;
        }

        public int getY() {
                return y;
        }

        public int getColor(int index) {
                for(int i = 0; i < color.length; i++) {
                        if(i == index) {
                                return color[i];
                        }
                }
                return 0;
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

        public void setColor(int r, int g, int b) {
                color[0] = r;
                color[1] = g;
                color[2] = b;
        }

        //toString()
        public String toString() {
                return getName() + " LVL:" + getLevel() + " HP:" + getHp() + " EXP:" + getExp();
        }
}