public class Tile{
  int color; //0: Normal Grass 1: Special walkable 2: unwalkable 3: special unwalkable 4: none
  boolean walkable;
  boolean revealed;

  public Tile(int color, boolean walkable){
    this.color = color;
    this.walkable = walkable;
    revealed = false;
  }

  public Tile(){
    this(0,false);
  }

  public void setColor(int color){
    this.color = color;
  }

  public void reveal(){
    revealed = true;
  }

  public int getColor(){
    return color;
  }

  public boolean getRevealed(){
    return revealed;
  }
}
