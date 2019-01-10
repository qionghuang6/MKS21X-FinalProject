import java.lang.Math;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Map{
  private int startX, startY;
  private   Tile[][] tileMap;

  //sets up Map
  public Map(int width, int length){
    if(width < 10 || length < 10){
      throw new IllegalArgumentException();
    }
    tileMap = new Tile[width][length];
    Random random = new Random();
    //sets up tiles on map
    for (int r = 0; r < tileMap.length; r++) {
      for(int c = 0; c < tileMap[0].length; c++){
        //if(tileMap[r][c] == null){
          tileMap[r][c] = new Tile (4,false);
        //}
      }
    }
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
      if(Math.random() < 0.5){
        goRight = false;
      }
      int ranSize = (goRight ? -1 : 1) * (2 * ((int)(Math.random() * 11)) + 1);
      double axis = 2.0 * Math.random() - 1.0;
      if(axis > 0){
        currentX += ranSize;
      } else{
        currentY += ranSize;
      }
      if(currentX < 0 || currentX > tileMap[0].length || currentY < 0 || currentY > tileMap.length ||
          !buildArea(currentX,currentY,ranSize -1)){
        currentX = lastX;
        currentY = lastY;
      }
    }
  }

  public Map(){
    this(100,45);
  }

  public int getStartX(){
    return startX;
  }
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
    int width = (2 * ((int) (Math.random() * 4))) + (2 * Math.abs(ranSize)) - 1;
    int length = (2 * ((int) (Math.random() * 4))) + (2 * Math.abs(ranSize)) - 1;
    //System.out.print(" L" + length + " W" + width);
    // System.out.println("" + length + " " + width);
    //System.out.print(" max x" + (xS - ((length - 1) / 2)) + " " + (xS + ((length - 1) / 2)));
    //System.out.println(" max y" + (yS - (width - 1) / 2) + " " + yS + (width - 1) / 2);
    if (xS - ((length - 1) / 2) < 1 || xS + ((length - 1) / 2) >= tileMap[0].length -2||
        yS - (width - 1) / 2 < 1 || yS + (width - 1) / 2 >= tileMap.length - 2){
          return false;
        }
    for(int x = xS - ((length - 1) / 2); x <= xS + ((length - 1) / 2) ; x++){
      if(tileMap[yS - ((width - 1) / 2)][x].getColor() != 0){
        tileMap[yS - ((width - 1) / 2)][x].makeUnwalkable(2);
      }
      if(tileMap[yS + ((width - 1) / 2)][x].getColor() != 0){
        tileMap[yS + ((width - 1) / 2)][x].makeUnwalkable(2);
      }
    }
    for(int y = yS - (width - 1) / 2; y <= yS + (width - 1) / 2 ; y++){
      if(tileMap[y][xS - (length - 1) / 2].getColor()!= 0){
        tileMap[y][xS - (length - 1) / 2].makeUnwalkable(2);
      }
      if(tileMap[y][xS + (length - 1) / 2].getColor() != 0){
        tileMap[y][xS + (length - 1) / 2].makeUnwalkable(2);
      }
    }
    for(int x = xS - (length - 1) / 2 + 1; x < xS + (length - 1) / 2 ; x++){
      for(int y = yS - (width -1) / 2 + 1; y < yS + (width - 1) / 2; y++){
        tileMap[y][x].makeWalkable(0);
      }
    }
    return true;
  }
}
