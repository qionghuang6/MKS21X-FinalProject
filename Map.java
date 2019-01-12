import java.lang.Math;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Map{
  private int startX, startY; //start of Map, where pokemons spawn
  private Tile[][] tileMap; //2d array to store Tile data

  //sets up Map
  public Map(int width, int length){
    if(width < 10 || length < 10){ //makes sure dimensions arent too small
      throw new IllegalArgumentException();
    }
    tileMap = new Tile[width][length];
    Random random = new Random();
    //sets up tiles on map
    for (int r = 0; r < tileMap.length; r++) {
      for(int c = 0; c < tileMap[0].length; c++){
          tileMap[r][c] = new Tile (4,false);
      }
    }
    //redomized start location that is at least 10 blocks from edge of map
    startY =  ((int) (Math.random() * 50) + 10);
    startX = ((int) (Math.random() * 10) + 10);
    int currentX = startX;
    int currentY = startY;
    buildArea(startX,startY,3); //makes first area of movable land around start points
    //moves around building Areas
    for (int a = 0; a < 100; a++ ) {
      int lastX = currentX;
      int lastY = currentY;
      //System.out.print("X" + currentX + " Y" + currentY);
      int lastRanSize = 0;
      boolean goRight = true;
      //randomly chooses to move a direction and how far
      if(Math.random() < 0.5){
        goRight = false;
      }
      int ranSize = (goRight ? -1 : 1) * (2 * ((int)(Math.random() * 11)) + 1);
      double axis = 2.0 * Math.random() - 1.0;
      //updates location of pointer based on above random values
      if(axis > 0){
        currentX += ranSize;
      } else{
        currentY += ranSize;
      }
      //creates swath of walkable land there
      if(currentX < 0 || currentX > tileMap[0].length || currentY < 0 || currentY > tileMap.length ||
          !buildArea(currentX,currentY,ranSize -1)){
        currentX = lastX;
        currentY = lastY;
      }
    }
  }

  //default map size is 100 wide and 45 tall
  public Map(){
    this(100,45);
  }
  //get startX coord
  public int getStartX(){
    return startX;
  }
  //get startY coord
  public int getStartY(){
    return startY;
  }
  //getter for tile array
  public Tile[][] getMap(){
    return tileMap;
  }

  //Creates a movable area centered at coordinates xS and yS
  //width and length are randomly generated from 3 to 9
  //surrounds moveable area in walls
  private boolean buildArea(int xS, int yS, int ranSize){
    //randomly created size of swath of walkable land to generate
    int width = (2 * ((int) (Math.random() * 4))) + (2 * Math.abs(ranSize)) - 1;
    int length = (2 * ((int) (Math.random() * 4))) + (2 * Math.abs(ranSize)) - 1;

    //checks to see if we can build the randomly generated swath
    //makes sure its not off the map, if it is tries again
    if (xS - ((length - 1) / 2) < 1 || xS + ((length - 1) / 2) >= tileMap[0].length -2||
        yS - (width - 1) / 2 < 1 || yS + (width - 1) / 2 >= tileMap.length - 2){
          return false;
        }
    //sets border of walkable area along y axis
    //makes sure border doesnt cover walkable land
    for(int x = xS - ((length - 1) / 2); x <= xS + ((length - 1) / 2) ; x++){
      if(tileMap[yS - ((width - 1) / 2)][x].getColor() != 0){
        tileMap[yS - ((width - 1) / 2)][x].makeUnwalkable(2);
      }
      if(tileMap[yS + ((width - 1) / 2)][x].getColor() != 0){
        tileMap[yS + ((width - 1) / 2)][x].makeUnwalkable(2);
      }
    }
    //sets border of walkable area along x axis
    for(int y = yS - (width - 1) / 2; y <= yS + (width - 1) / 2 ; y++){
      if(tileMap[y][xS - (length - 1) / 2].getColor()!= 0){
        tileMap[y][xS - (length - 1) / 2].makeUnwalkable(2);
      }
      if(tileMap[y][xS + (length - 1) / 2].getColor() != 0){
        tileMap[y][xS + (length - 1) / 2].makeUnwalkable(2);
      }
    }
    //makes insides walkable land
    for(int x = xS - (length - 1) / 2 + 1; x < xS + (length - 1) / 2 ; x++){
      for(int y = yS - (width -1) / 2 + 1; y < yS + (width - 1) / 2; y++){
        tileMap[y][x].makeWalkable(0);
      }
    }
    return true;
  }
}
