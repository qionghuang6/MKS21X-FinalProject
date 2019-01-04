import java.lang.Math;

public class Map{
  private int startX, startY;
  private   Tile[][] tileMap;

  //sets up Map
  public Map(int width, int length){
    if(width < 10 || length < 10){
      throw new IllegalArgumentException();
    }
    tileMap = new Tile[width][length];
    //sets up tiles on map
    for (int r = 0; r < tileMap.length; r++) {
      for(int c = 0; c < tileMap[0].length; c++){
        //if(tileMap[r][c] == null){
          tileMap[r][c] = new Tile (4,false);
        //}
      }
    }
    startX = 5;
    startY = 5;
    int currentX = startX;
    int currentY = startY;
    buildArea(startX,startY); //makes first area of movable land around start points
    //moves around building Areas
    for (int a = 0; a < 5; a++ ) {

    }
  }

  public Map(){
    this(50,30);
  }

  //getter for tile array
  public Tile[][] getMap(){
    return tileMap;
  }

  //Creates a movable area centered at coordinates xS and yS
  //width and length are randomly generated from 3 to 9
  //surrounds moveable area in walls
  private boolean buildArea(int xS, int yS){
    int width = (2 * ((int) (Math.random() * 4)) + 3);
    int length = (2 * ((int) (Math.random() * 4)) + 3);
    System.out.println("" + length + " " + width);
    if (xS - (length - 1) / 2 < 0 || xS + (length - 1) / 2 > tileMap[0].length ||
        yS - (width - 1) / 2 < 0 || yS + (width - 1) / 2 > tileMap.length){
          return false;
        }
    for(int x = xS - ((length - 1) / 2); x <= xS + ((length - 1) / 2) ; x++){
      tileMap[yS - ((width - 1) / 2)][x].setColor(2);
      tileMap[yS + ((width - 1) / 2)][x].setColor(2);
    }
    for(int y = yS - (width - 1) / 2; y <= yS + (width - 1) / 2 ; y++){
      tileMap[y][xS - (length - 1) / 2].setColor(2);
      tileMap[y][xS + (length - 1) / 2].setColor(2);
    }
    for(int x = xS - (length - 1) / 2 + 1; x < xS + (length - 1) / 2 ; x++){
      for(int y = yS - (width -1) / 2 + 1; y < yS + (width - 1) / 2; y++){
        tileMap[y][x].makeWalkable(0);
      }
    }
    return true;
  }
}
