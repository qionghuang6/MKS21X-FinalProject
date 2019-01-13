//API : http://mabe02.github.io/lanterna/apidocs/2.1/
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.input.CharacterPattern;
import com.googlecode.lanterna.input.InputDecoder;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.KeyMappingProfile;
import java.util.ArrayList;
import java.util.List;

public class PokemonMysteryD{
  static Terminal terminal;
  static TerminalSize terminalSize;

  //displays text at certain location
  public static void putString(int r, int c,Terminal t, String s){
		t.moveCursor(r,c);
		for(int i = 0; i < s.length();i++){
			t.putCharacter(s.charAt(i));
		}
	}
  //Moves terminal cursor to Pokemon location, sets symbol and color
  //Uses Pokemon class setLocation to record location changed to
  public static void putPokemon(int x, int y, Terminal t, Pokemon p){
    int[] rgb = p.getColorArr();
    t.moveCursor(y,x);
    t.applyBackgroundColor(rgb[0],rgb[1],rgb[2]);
    t.applyForegroundColor(Terminal.Color.BLACK);
    t.putCharacter(p.getSymbol().charAt(0));
    p.setLocation(x,y);
  }
  //gets delta x and delta y and moves pokemon to that location using putPokemon
  //gets information from map to clean up after itself
  public static void movePokemon(Tile[][] mapMap, int dx, int dy, Terminal t, Pokemon p){
    int curX = p.getX();
    int curY = p.getY();
    //putString(70, 20,t,"" + curX + " " + curY);
    t.moveCursor(curY,curX);
    setBg(t, mapMap[curY][curX], curX, curY);
    t.moveCursor(curY,curX);
    terminal.putCharacter(' ');
    putPokemon(curX + dx, curY + dy, t, p);
    //p.setLocation(curX + dx,y);
  }
  //sets background of a certain location to an rgb value
  public static void setBg(Terminal t, int x, int y, int r, int g, int b){
    t.moveCursor(y,x);
    t.applyBackgroundColor(r,g,b);
    terminal.putCharacter(' ');
  }
  //takes in Tile from generated Map 2d array and sets that location to certain colors
  public static void setBg(Terminal terminal, Tile t, int x, int y){
    if(t.getColor() == 0){
      setBg(terminal, x,y,131,203,58);
    }
    if(t.getColor() == 2){
      setBg(terminal,x,y,201,134,0);
    }
    if(t.getColor() == 4){
      setBg(terminal,x,y,52,111,18);
  }
}

  //Uses Map array from Map class to display the map in the beginning of a round
  public static void buildMap(Tile[][] mapMap){
    for (int x = 0; x < mapMap.length;x++) {
      for(int y = 0 ; y < mapMap[0].length;y++){
        terminal.putCharacter(' ');
        //terminal.putCharacter(("" + y).charAt(0));
        setBg(terminal,mapMap[x][y],y,x);
        }
      }
  }
  //spawns player pokemons from player class using putPokemon()
  public static void spawnPlayer(Player player, Terminal t, Map m){
    putPokemon(m.getStartX(),m.getStartY(), t, player.getPlayer());
    putPokemon(m.getStartX(),m.getStartY() + 1, t, player.getPartner());
  }

  public static void main(String[] args) {
    Pokemon playerPokemon = PokemonRandomizer.returnPokemon();
    playerPokemon.setSymbol("@");
    Pokemon partnerPokemon = PokemonRandomizer.returnPokemon();
    while(partnerPokemon.getName().equals(playerPokemon.getName())){
      partnerPokemon = PokemonRandomizer.returnPokemon();
    }
    Player player = new Player(playerPokemon, partnerPokemon, 300);

    //defines lanterna terminal
    terminal = TerminalFacade.createUnixTerminal();
		terminal.enterPrivateMode();

		terminalSize = terminal.getTerminalSize();
		terminal.setCursorVisible(false);

    //gets map from Map generator class
    Map testMap = new Map();
    Tile[][] mapMap = testMap.getMap();

    //calls buildMap to display map and spawns player pokemons
    buildMap(mapMap);
    spawnPlayer(player, terminal, testMap);

    //makes sure there isn't a spawn error and runs the map building and spawning process again
    //if pokemons spawn improperly (highly unlikely but theoretically possible)
    while(mapMap[testMap.getStartY()][testMap.getStartX()].getColor() != 0){
      testMap = new Map();
      mapMap = testMap.getMap();
      buildMap(mapMap);
      spawnPlayer(player, terminal, testMap);
    }

    boolean running = true;
    while (running){
      //key type from Lanterna reads key inputs
      Key key = terminal.readInput();
			if (key != null){
        int curX = player.getPlayer().getX();
        int curY = player.getPlayer().getY();
        //Used to check if Esc is pressed to exit the game
        if (key.getKind() == Key.Kind.Escape) {
          running = false;
          terminal.exitPrivateMode();
          System.exit(0);
        }
        //Check what arrow keys are pressed and if those locations are walkable and moves Pokemon
        if (key.getKind() == Key.Kind.ArrowLeft && mapMap[curY -1 ][curX].getWalkable()) {
          movePokemon(mapMap, 0,-1,terminal,player.getPlayer());
          movePokemon(mapMap, 0,-1,terminal,player.getPartner());

				}
            //checks two to the right to account for partner pokemon
				if (key.getKind() == Key.Kind.ArrowRight && mapMap[curY + 2][curX ].getWalkable()) {
          movePokemon(mapMap, 0,1,terminal,player.getPartner());
          movePokemon(mapMap, 0,1,terminal,player.getPlayer());
        }
				if (key.getKind() == Key.Kind.ArrowUp && mapMap[curY][curX - 1].getWalkable()
            //additional checks used to check partner pokemon location
            && mapMap[curY + 1][curX - 1].getWalkable()) {
          movePokemon(mapMap, -1,0,terminal,player.getPlayer());
          movePokemon(mapMap, -1,0,terminal,player.getPartner());
        }
				if (key.getKind() == Key.Kind.ArrowDown && mapMap[curY][curX + 1].getWalkable()
            && mapMap[curY + 1][curX + 1].getWalkable()) {
          movePokemon(mapMap, 1,0,terminal,player.getPlayer());
          movePokemon(mapMap, 1,0,terminal,player.getPartner());
        }
        /*
        // Debug Code: Used to display current player location
        curX = player.getPlayer().getX();
        curY = player.getPlayer().getY();
        putString(10,40,terminal," " + curX + " " + curY);
        */
      }
    }
  }
}
