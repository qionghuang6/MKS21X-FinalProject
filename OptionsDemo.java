//API : http://mabe02.github.io/lanterna/apidocs/2.1/
import com.googlecode.lanterna.terminal.Terminal.SGR;
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



public class OptionsDemo {

	private static int currentx;
	//y value of cursor
	private static int currenty;

  public static void putString(int r, int c,Terminal t, String s){
    t.moveCursor(r,c);
    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
  }

  public static void putString(int r, int c,Terminal t,
        String s, Terminal.Color text, Terminal.Color forg, Terminal.Color back ){
    t.moveCursor(r,c);
    t.applyBackgroundColor(forg);
    t.applyForegroundColor(text);

    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }
  public static void main(String[] args) {

    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(true);

    boolean running = true;
    boolean hasLoaded = false;
    currentx = 16;
    currenty = 5;
    int mode = 0;

    while(running){
      Key key = terminal.readInput();
      if (key != null)
      {

              if (key.getKind() == Key.Kind.Escape) {
                      terminal.exitPrivateMode();
                      running = false;
              }
              if (key.getKind() == Key.Kind.ArrowUp){
                      if (currenty > 0) currenty--;
                      terminal.clearScreen();
                      hasLoaded = false;
              }
              if (key.getKind() == Key.Kind.ArrowDown){
                      if (currenty < 18) currenty++;
                      terminal.clearScreen();
                      hasLoaded = false;
              }
              if (key.getKind() == Key.Kind.ArrowRight){
                      if (currentx < 80) currentx++;
                      terminal.clearScreen();
                      hasLoaded = false;
              }
              if (key.getKind() == Key.Kind.ArrowLeft){
                      if (currentx > 0)currentx--;
                      terminal.clearScreen();
                      hasLoaded = false;
              }
              //added below:
              if(mode == 0){
                      if(key.getKind() == Key.Kind.Backspace) {
                              mode = 1;
                              hasLoaded = false;
                                terminal.clearScreen();
                      }
              }
              if(mode == 1) {
                      if(key.getKind() == Key.Kind.Backspace) {
                              mode = 0;
                              hasLoaded = false;
                                terminal.clearScreen();
                      }

              }
      }



      if(mode==0){
              if(!hasLoaded) {
				putString(0,0,terminal, "AAAAAAAAAAAAAAAAAAAAAAAAAAWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",Terminal.Color.RED,Terminal.Color.RED,Terminal.Color.RED);

				putString(1,5,terminal, "C",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,6,terminal, "B",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,7,terminal, "A#",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,8,terminal, "A",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,9,terminal, "G#",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,10,terminal, "G",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,11,terminal, "F#",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,12,terminal, "F",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,13,terminal, "E",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,14,terminal, "D#",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,15,terminal, "D",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,16,terminal, "C#",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
				putString(1,17,terminal, "C",Terminal.Color.BLUE,Terminal.Color.WHITE,Terminal.Color.RED);
                                putString(1,3,terminal, "currentx: "+ currentx,Terminal.Color.BLUE, Terminal.Color.WHITE,Terminal.Color.RED);
                                putString(15,3,terminal, "currenty: "+ currenty,Terminal.Color.BLUE, Terminal.Color.WHITE,Terminal.Color.RED);
              }
              putString(currentx,currenty,terminal,"^",Terminal.Color.BLUE, Terminal.Color.WHITE, Terminal.Color.GREEN);

        hasLoaded = true;
              }
      } 
      if(mode == 1) {
              if(!hasLoaded) {
        putString(1,3,terminal, "Not game, just a pause!",Terminal.Color.BLUE,Terminal.Color.RED,Terminal.Color.WHITE);
                hasLoaded = true;
              }
      }

  }

}
