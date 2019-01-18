public class Tile{
  private int color;
  //colors are to be translated into Lanterna colors
  //0: Normal Grass 1: Special walkable 2: unwalkable 3: special unwalkable 4: none
  //10: stairs 8 health potion
  private boolean walkable;
  //whether or not a pokemon can move onto it
  private boolean revealed;
  //whether or not it is displayed
  //not currently used yet
  private boolean stair;
  //whether or not tile will give simple health potion
  private int healthPotion;

  private Tile tpTo;

  //constructs Tiles based on givens, starts off as not being revealed
  public Tile(int color, boolean walkable){
    this.color = color;
    this.walkable = walkable;
    revealed = false;
  }

  //default tile
  public Tile(){
    this(0,false);
  }
  //makes a tile walkable and sets color at same time
  public void makeWalkable(int color){
    setColor(color);
    walkable = true;
  }
  public void makeWalkable(){
    walkable = true;
  }
  //changes tile to make it unwalkable and sets color
  public void makeUnwalkable(int color){
    setColor(color);
    walkable = false;
  }
  public void makeUnwalkable(){
    walkable = false;
  }
  //checks if a tile is walkable
  public boolean getWalkable(){
    return walkable;
  }
  //setter for tile color
  public void setColor(int color){
    this.color = color;
  }
  //changes status of revealed;
  public void reveal(){
    revealed = true;
  }
  //gets color
  public int getColor(){
    return color;
  }
  //checks if revealed
  public boolean getRevealed(){
    return revealed;
  }
  //setter for stair
  public void setStair(){
    stair = true;
  }
  //getter for if its a stair
  public boolean isStair(){
    return stair;
  }
  //setter for health potion
  public void setHealthPotion(int h){
    healthPotion = h;
  }
  //getter for health potion
  public int getHealthPotion(){
    return healthPotion;
  }
  public void setTp(Tile t){
    tpTo = t;
  }
  public Tile getTp(){
    return tpTo;
  }
}
