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
  public static void putString(int r, int c,Terminal t, String s){
		t.moveCursor(r,c);
		for(int i = 0; i < s.length();i++){
			t.putCharacter(s.charAt(i));
		}
	}
  public static void main(String[] args) {

    Terminal terminal = TerminalFacade.createUnixTerminal();
		terminal.enterPrivateMode();

		TerminalSize terminalSize = terminal.getTerminalSize();
		terminal.setCursorVisible(false);

    Map testMap = new Map();
    Tile[][] mapMap = testMap.getMap();
    for (int x = 0; x < mapMap.length;x++) {
      for(int y = 0 ; y < mapMap[0].length;y++){
        terminal.moveCursor(x,y);
        terminal.putCharacter(("" + x).charAt(0));
        if(mapMap[x][y].getColor() == 0){
          terminal.applyBackgroundColor(Terminal.Color.YELLOW);
        }
        if(mapMap[x][y].getColor() == 2){
          terminal.applyBackgroundColor(Terminal.Color.RED);
        }
        if(mapMap[x][y].getColor() == 4){
          terminal.applyBackgroundColor(Terminal.Color.GREEN);
        }
      }
    }
    boolean running = true;
    while (running){
      Key key = terminal.readInput();
			if (key != null){
        if (key.getKind() == Key.Kind.Escape) {
          running = false;
          terminal.exitPrivateMode();
          System.exit(0);
        }
        putString(1,35,terminal,key+"        ");
      }
    }
  }
}
