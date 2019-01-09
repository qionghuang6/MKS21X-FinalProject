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
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;

public class PokemonMysteryD{
  static Terminal terminal;
  static Screen options;
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
  public static void main(String[] args) {
        //Deals with terminal.
          terminal = TerminalFacade.createUnixTerminal();
          terminal.enterPrivateMode();

          terminalSize = terminal.getTerminalSize();
          terminal.setCursorVisible(false);

        //Screen Options:
        options = new Screen(terminal,terminalSize);


    //Randomnly generated map for dungeons:
    Map testMap = new Map();
    Tile[][] mapMap = testMap.getMap();
    for (int x = 0; x < mapMap.length;x++) {
      for(int y = 0 ; y < mapMap[0].length;y++){
        terminal.putCharacter(' ');
        //terminal.putCharacter(("" + y).charAt(0));
        if(mapMap[x][y].getColor() == 0){
          setBg(terminal, x,y,131,203,58);
        }
        if(mapMap[x][y].getColor() == 2){
          setBg(terminal,x,y,201,134,0);
        }
        if(mapMap[x][y].getColor() == 4){
          setBg(terminal,x,y,52,111,18);
        }
      }
    }

    terminal.moveCursor(5,5);
    terminal.applyBackgroundColor(Terminal.Color.WHITE);
    boolean running = true;
      boolean optionsOn = false;
    while (running){
      Key key = terminal.readInput();
			if (key != null){
        if (key.getKind() == Key.Kind.Escape) {
          running = false;
          terminal.exitPrivateMode();
          System.exit(0);
        }
        if (key.getKind() == Key.Kind.Backspace) {
                if(!optionsOn) {
                        options.startScreen();
                        options.putString(40,10,"Settings and Options",Terminal.Color.BLUE,Terminal.Color.RED,ScreenCharacterStyle.Blinking);
                        options.refresh();
                        optionsOn = true;
                }
                else {
                        terminal.clearScreen();
                        options.stopScreen();
                        options.refresh();
                        optionsOn = false;
                        terminal.enterPrivateMode();
                        //Generates the map again.
                        for (int x = 0; x < mapMap.length;x++) {
                                for(int y = 0 ; y < mapMap[0].length;y++){
                                        terminal.putCharacter(' ');
                                        //terminal.putCharacter(("" + y).charAt(0));
                                        if(mapMap[x][y].getColor() == 0){
                                                setBg(terminal, x,y,131,203,58);
                                        }
                                        if(mapMap[x][y].getColor() == 2){
                                                setBg(terminal,x,y,201,134,0);
                                        }
                                        if(mapMap[x][y].getColor() == 4){
                                                setBg(terminal,x,y,52,111,18);
                                        }
                                }
                        }
                }
        }
                        }
    }
  }
}
