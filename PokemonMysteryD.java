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

public class PokemonMysteryD{
  static Terminal terminal;
  static TerminalSize terminalSize;
  public static void putString(int r, int c,Terminal t, String s){
		t.moveCursor(r,c);
		for(int i = 0; i < s.length();i++){
			t.putCharacter(s.charAt(i));
		}
	}

  public static void putString(int r, int c,Terminal t,
        String s, Terminal.Color forg, Terminal.Color back ){
    t.moveCursor(r,c);
    t.applyBackgroundColor(forg);
    t.applyForegroundColor(Terminal.Color.BLACK);

    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }
  public static void setBg(Terminal t, int x, int y, int r, int g, int b){
    t.moveCursor(x,y);
    t.applyBackgroundColor(r,g,b);

  }
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
  public static void main(String[] args) {
    int[] charColor = {3,4,5};
    int[] squirtColor = {33,232,323};
    Pokemon charmander = new Pokemon("charmander", "fire", "@", 30, charColor, 5);
    Pokemon squirtle = new Pokemon("squirtle", "water", "O", 30, squirtColor, 5);
    Player bryan = new Player(charmander, squirtle, 300);

    terminal = TerminalFacade.createUnixTerminal();
		terminal.enterPrivateMode();

		terminalSize = terminal.getTerminalSize();
		terminal.setCursorVisible(false);

    Map testMap = new Map();
    Tile[][] mapMap = testMap.getMap();
    for (int x = 0; x < mapMap.length;x++) {
      for(int y = 0 ; y < mapMap[0].length;y++){
        terminal.putCharacter(' ');
        //terminal.putCharacter(("" + y).charAt(0));
        setBg(terminal,mapMap[x][y],x,y);

        }
      }

    terminal.moveCursor(5,5);
    terminal.applyBackgroundColor(Terminal.Color.WHITE);
    boolean running = true;
    while (running){
      Key key = terminal.readInput();
			if (key != null){
        if (key.getKind() == Key.Kind.Escape) {
          running = false;
          terminal.exitPrivateMode();
          System.exit(0);
        }

      }
    }
  }
}
