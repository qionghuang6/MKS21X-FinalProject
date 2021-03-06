import java.util.ArrayList; 
import java.util.List; 
import java.lang.Math;
public class Pokemon implements Cloneable{
        //Instance Variables:
        private String name;
        private String type;
        private String symbol;
        private double hp;
        private double maxHp;
        private int[] color;
        private int level;
        private int exp;
        private int x;
        private int y;
        private ArrayList<Move> moveset;

        //Constructor:
        public Pokemon(String n, String t, String s, int h, int[] c, int l, List<Move> m) {
               exp = 0;
               name = n;
               type = t;
               hp = h;
               maxHp = hp;
               level = l;
               symbol = s;
               moveset = new ArrayList<Move>(m);
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

        public double getHp() {
                return hp;
        }

        public double getMaxHp() {
                return maxHp;
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

        public int[] getColorArr() {
                return color;
        }

        public void setLocation(int x, int y) {
                this.x = x;
                this.y = y;
        }

        public void resetExp() {
                exp = 0;
        }

        public void gainExp() {
               exp += (int)(Math.random() * 100);
        }

        public void levelUp() {
                level += 1;
        }

        public void resetLevel() {
                level = 1;
        }

        public void loseHp(double num) {
                hp -= num;
        }

        public void healHp(double num) {
                hp += num;
        }

        public void resetHp() {
                hp = maxHp;
        }

        public void setSymbol(String s) {
                symbol = s;
        }

        public void setColor(int r, int g, int b) {
                color[0] = r;
                color[1] = g;
                color[2] = b;
        }

        public String useMove(Move m, Pokemon target) {
                String moveType = m.getType();
                String targetType = target.getType();
                //To calculate Damage: 
                //((((2 * Pokemon Level)/ 5) + 2) * moveDamage) / 50) + 2
                //Super effective moves: 2x Damage.
                if(moveType == "Water" && targetType == "Fire") {
                        target.loseHp((((((2 * getLevel())/ 5) + 2) * m.getBaseDamage()) / 20 + 2) * 2);
                        return getName() + " uses " + m.getName() + ", It was super effective! " + target.getName() + " has " + target.getHp() + " HP remaining!";
                }
                if(moveType == "Grass" && targetType == "Water") {
                        target.loseHp((((((2 * getLevel())/ 5) + 2) * m.getBaseDamage()) / 20 + 2) * 2);
                        return getName() + " uses " + m.getName() + ", It was super effective! " + target.getName() + " has " + target.getHp() + " HP remaining!";
                }
                if(moveType == "Fire" && targetType == "Grass") {
                        target.loseHp((((((2 * getLevel())/ 5) + 2) * m.getBaseDamage()) / 20 + 2) * 2);
                        return getName() + " uses " + m.getName() + ", It was super effective! " + target.getName() + " has " + target.getHp() + " HP remaining!";
                }

                //Non effective type moves: 0.5x Damage.
                if(moveType.equals(targetType)) {
                        target.loseHp((((((2 * getLevel())/ 5) + 2) * m.getBaseDamage()) / 20 + 2) * 0.5);
                        return getName() + " uses " + m.getName() + ", It was not effective... " + target.getName() + " has " + target.getHp() + " HP remaining!" ;
                }
                if(moveType == "Fire" && targetType == "Water") {
                        target.loseHp((((((2 * getLevel())/ 5) + 2) * m.getBaseDamage()) / 20 + 2) * 0.5);
                        return getName() + " uses " + m.getName() + ", It was not effective... " + target.getName() + " has " + target.getHp() + " HP remaining!" ;
                }
                if(moveType == "Water" && targetType == "Grass") {
                        target.loseHp((((((2 * getLevel())/ 5) + 2) * m.getBaseDamage()) / 20 + 2) * 0.5);
                        return getName() + " uses " + m.getName() + ", It was not effective..." + target.getName() + " has " + target.getHp() + " HP remaining!";
                }
                if(moveType == "Grass" && targetType == "Fire") {
                        target.loseHp((((((2 * getLevel())/ 5) + 2) * m.getBaseDamage()) / 20 + 2) * 0.5);
                        return getName() + " uses " + m.getName() + ", It was not effective... " + target.getName() + " has " + target.getHp() + " HP remaining!";
                }

                //Returns regular message if none of the above, and normal base damage.
                target.loseHp((((((2 * getLevel())/ 5) + 2) * m.getBaseDamage()) / 20 + 2));
                return getName() + " uses " + m.getName() + ", " + target.getName() + " has " + target.getHp() + " HP remaining!";
        }

        //toString()
        public String toString() {
                return getName() + " LVL:" + getLevel() + " HP:" + getHp() + " EXP:" + getExp();
        }
        public Pokemon clone() throws CloneNotSupportedException{
          return (Pokemon) super.clone();
        }
        public int[] moveTowards(Pokemon p){
          int dx = 0;
          int dy = 0;
          if(p.getX() > this.getX()){
            dx = 1;
          }
          if(p.getX() < this.getX()){
            dx = -1;
          }
          if(p.getY() < this.getY()){
            dy = -1;
          }
          if(p.getY() > this.getY()){
            dy = 1;
          }
          return new int[]{dx,dy};
        }
}
