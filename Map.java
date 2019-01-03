import java.lang.Math;

public class Map{
  int startX;
  int startY;
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

  }

  private void buildStart(){
    startX = 5;
    startY = 5;
    for(int x = startX - 3; x <= startX + 3; x++){
      
    }
  }
}
