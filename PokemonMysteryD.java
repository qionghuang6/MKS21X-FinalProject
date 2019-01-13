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
import com.googlecode.lanterna.terminal.TerminalSize;
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
                mapMap[curY][curX].makeWalkable();
                t.moveCursor(curY,curX);
                terminal.putCharacter(' ');
                putPokemon(curX + dx, curY + dy, t, p);
                mapMap[curY + dy ][curX + dx].makeUnwalkable();
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
                if(t.getColor() == 10){
                        setBg(terminal,x,y,255,255,0);
                }
        }

        public static List<Pokemon> spawnHostilePokemons(Tile[][] m, Terminal t){
                int spawned = 0;
                List<Pokemon> p= new ArrayList<Pokemon>();
                while(spawned < 15){
                        int r = (int) (Math.random() * m.length);
                        int c = (int) (Math.random() * m[0].length);
                        if(m[r][c].getWalkable()){
                                Pokemon newPoke = PokemonRandomizer.returnPokemon();
                                putPokemon(c,r,t,newPoke);
                                spawned++;
                                m[r][c].makeUnwalkable();
                                p.add(newPoke);
                        }
                }
                return p;
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
        //Sets up start screen.
        public static void setUpStartScreen(Screen start, int xSize) {
                start.startScreen();
                start.updateScreenSize();
                for(int i = 0; i < start.getTerminalSize().getRows();i++) {
                        for(int x = 0; x < start.getTerminalSize().getColumns(); x++) {
                                start.putString(x,i,"|",Terminal.Color.BLACK,null,ScreenCharacterStyle.Bold);
                        }
                }
                start.putString(xSize/2,10, "                 Press S to Start!               ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Blinking);
                start.refresh();
        }
        //Sets up option Screen.
        public static void setUpOptionsScreen(Screen options, int xSize) {
                options.startScreen();
                options.updateScreenSize();
                for(int i = 0; i < options.getTerminalSize().getRows(); i++) {
                        for(int x = 0; x < options.getTerminalSize().getColumns(); x++) {
                                options.putString(x,i,"/",Terminal.Color.BLACK,null,ScreenCharacterStyle.Bold);
                        }
                }
                //This code details the left portion of the screen. (Instructions)
                options.putString(xSize/2 - 10,8,"Instructions & Options:",Terminal.Color.GREEN,null,ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,15, "Instructions:", Terminal.Color.GREEN, null, ScreenCharacterStyle.Underline);
                options.putString(xSize/4 - 3,17, "Objective: Reach the stairs of each level!", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,20, "Key Controls:", Terminal.Color.GREEN, null, ScreenCharacterStyle.Underline);
                options.putString(xSize/4 - 3,22, "1. Enable/Disable Option --> Space Bar", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,24, "2. Move Up --> Up Arrow Key", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,26, "3. Move Down --> Down Arrow Key", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,28, "4. Move Left --> Left Arrow Key", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,30, "5. Move Right --> Right Arrow Key", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,32, "6. Use attacks in moveset --> Press corresponding #s.", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                //This code details the right portion of the screen. (Options)
                options.putString(xSize * 3/4 - 10,15, "Options:", Terminal.Color.GREEN, null, ScreenCharacterStyle.Underline);
                options.putString(xSize * 3/4 - 10,17, "Enable Music Track?", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                //Implement space bar to set true/false for options later..
                options.putString(xSize * 3/4 + 12,17, "Status: ", Terminal.Color.GREEN, null, ScreenCharacterStyle.Bold);
                options.refresh();
        }
        //spawns player pokemons from player class using putPokemon()
        public static void spawnPlayer(Player player, Terminal t, Map m){
                putPokemon(m.getStartX(),m.getStartY(), t, player.getPlayer());
                putPokemon(m.getStartX(),m.getStartY() + 1, t, player.getPartner());
        }

        public static void main(String[] args) {
                List<Pokemon> allPokemons = new ArrayList<Pokemon>();
                Pokemon playerPokemon = PokemonRandomizer.returnPokemon();
                playerPokemon.setSymbol("@");
                Pokemon partnerPokemon = PokemonRandomizer.returnPokemon();
                while(partnerPokemon.getName().equals(playerPokemon.getName())){
                        partnerPokemon = PokemonRandomizer.returnPokemon();
                }
                Player player = new Player(playerPokemon, partnerPokemon, 300);
                allPokemons.add(playerPokemon);
                allPokemons.add(partnerPokemon);

                //defines lanterna terminal
                terminal = TerminalFacade.createUnixTerminal();
                terminal.enterPrivateMode();
                terminalSize = terminal.getTerminalSize();
                terminal.setCursorVisible(false);
                //Screen Options:
                Screen options = new Screen(terminal,terminalSize);
                Screen start = new Screen(terminal, terminalSize);

                //Variables:
                boolean running = true;
                boolean optionsOn = false;
                boolean generated = false;

                //Game mode 0 --> Represents start screen.
                //Game mode 1 --> Represents actual gameplay.
                int gameMode = 0;
                int ySize = options.getTerminalSize().getRows();
                int xSize = options.getTerminalSize().getColumns();

                //gets map from Map generator class
                Map testMap = new Map();
                Tile[][] mapMap = testMap.getMap();

                //calls buildMap to display map and spawns player pokemons

                //makes sure there isn't a spawn error and runs the map building and spawning process again
                //if pokemons spawn improperly (highly unlikely but theoretically possible)
                while(mapMap[testMap.getStartY()][testMap.getStartX()].getColor() != 0){
                        testMap = new Map();
                        mapMap = testMap.getMap();
                        buildMap(mapMap);
                        spawnPlayer(player, terminal, testMap);
                }

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
                                //When you click on Backspace in the game...
                                if (key.getKind() == Key.Kind.Backspace) {
                                        //This block of code is called when you're not in the options page.
                                        if(!optionsOn) {
                                                start.stopScreen();
                                                setUpOptionsScreen(options,xSize);
                                                optionsOn = true;
                                        }
                                        //This block of code is called when you're in the options page.
                                        else if(gameMode == 1) {
                                                options.stopScreen();
                                                options.completeRefresh();
                                                terminal.enterPrivateMode();
                                                //Generates the map again.
                                                buildMap(mapMap);
                                                for(Pokemon p: allPokemons){
                                                  putPokemon(p.getX(),p.getY(),terminal,p);
                                                }
                                                terminal.flush();
                                                terminal.setCursorVisible(false);
                                                optionsOn = false;
                                                generated = true;
                                        }
                                        else if(gameMode == 0) {
                                                options.stopScreen();
                                                options.completeRefresh();
                                                //Generates the start screen again:
                                                terminal.enterPrivateMode();
                                                setUpStartScreen(start, xSize);
                                                optionsOn = false;
                                                generated = true;
                                        }
                                }

                                if (key.getCharacter() == 's' && gameMode == 0) {
                                        gameMode = 1;
                                        start.stopScreen();
                                        generated = false;
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
                                if(mapMap[player.getPlayer().getY()][player.getPlayer().getX()].getColor() == 10){
                                        testMap = new Map();
                                        mapMap = testMap.getMap();
                                        buildMap(mapMap);
                                        spawnPlayer(player, terminal, testMap);
                                        allPokemons.clear();
                                        allPokemons.add(player.getPlayer());
                                        allPokemons.add(player.getPartner());
                                        allPokemons.addAll(spawnHostilePokemons(mapMap, terminal));
                                }
                                /*
                                // Debug Code: Used to display current player location
                                curX = player.getPlayer().getX();
                                curY = player.getPlayer().getY();
                                putString(10,40,terminal," " + curX + " " + curY);
                                */
                        }
                        //below represents closing of running.
                        //Start Screen Controls:
                        if(gameMode == 0 && !generated) {
                                terminal.enterPrivateMode();
                                setUpStartScreen(start, xSize);
                                generated = true;
                        }
                        if(gameMode == 1 && !generated) {
                                terminal.enterPrivateMode();
                                terminal.setCursorVisible(false);
                                buildMap(mapMap);
                                spawnPlayer(player, terminal, testMap);
                                allPokemons.addAll(spawnHostilePokemons(mapMap, terminal));
                                generated = true;
                        }
                }
        }
}
