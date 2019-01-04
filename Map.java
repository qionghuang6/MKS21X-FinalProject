import java.lang.Math;

public class Map{
  int startX, startY;
  Tile[][] tileMap;

  public Map(int width, int length){
    if(width < 10 || length < 10){
      throw new IllegalArgumentException();
    }
    tileMap = new Tile[width][length];
  }

  public Map(){
    this(50,30);
  }

  public Tile[][] getMap(){
    return tileMap;
  }

  private void buildMap(){
    int currentX = 5;
    int currentY = 5;
  }

  private void buildStart(){
    startX = 5;
    startY = 5;
    buildArea(5,5);
    for (Tile[] tileRow: tileMap) {
      for(Tile t: tileRow){
        if(t == null){
          t = new Tile (2,false);
        }
      }
    }
  }
  private boolean buildArea(int xS, int yS){
    int width = (2 * (int) Math.random() * 2) + 3;
    int length = (2 * (int) Math.random() * 2) + 3;
    if (xS - (length - 1) / 2 < 0 || xS + (length - 1) / 2 > tileMap[0].length ||
        yS - (width - 1) / 2 < 0 || yS + (width - 1) / 2 > tileMap.length){
          return false;
        }
    for(int x = xS - (length - 1) / 2; x <= xS + (length - 1) / 2 ; x++){
      tileMap[yS - (width - 1) / 2][x] = new Tile(2,false);
      tileMap[yS + (width - 1) / 2][x] = new Tile(2,false);
    }
    for(int y = yS - (width - 1) / 2; y <= yS + (length - 1) / 2 ; y++){
      tileMap[y][xS - (length - 1)] = new Tile(2,false);
      tileMap[y][xS - (length - 1)] = new Tile(2,false);
    }
    for(int x = xS - (length - 1) / 2 + 1; x < xS + (length - 1) / 2 ; x++){
      for(int y = yS - (length -1) / 2 + 1; y < yS + (length - 1) / 2; y++){
        tileMap[y][x] = new Tile (0, true);
      }
    }
    return true;
  }
}
