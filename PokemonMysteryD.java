//API : http://mabe02.github.io/lanterna/apidocs/2.1/
import com.googlecode.lanterna.TerminalFacade;
import java.util.concurrent.ThreadLocalRandom;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color; import com.googlecode.lanterna.terminal.TerminalSize;
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
import java.util.Random;

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


        //gets delta x and delta y and moves pokemon to that location using emon
        //gets information from map to clean up after itself
        public static void movePokemon(Tile[][] mapMap, int dx, int dy, Terminal t, Pokemon p, double level){
          int curX = p.getX();
          int curY = p.getY();
                if(mapMap[curY + dy][curX + dx].getWalkable()){
                  //putString(70, 20,t,"" + curX + " " + curY);
                  t.moveCursor(curY,curX);
                  setBg(t, mapMap[curY][curX], curX, curY,level);
                  mapMap[curY][curX].makeWalkable();
                  t.moveCursor(curY,curX);
                  terminal.putCharacter(' ');
                  putPokemon(curX + dx, curY + dy, t, p);
                  mapMap[curY + dy ][curX + dx].makeUnwalkable();
                  //p.setLocation(curX + dx,y);
                }
        }


        //sets background of a certain location to an rgb value
        public static void setBg(Terminal t, int x, int y, int r, int g, int b){
                t.moveCursor(y,x);
                t.applyBackgroundColor(r,g,b);
                terminal.putCharacter(' ');
        }


        //takes in Tile from generated Map 2d array and sets that location to certain colors
        public static void setBg(Terminal terminal, Tile t, int x, int y, double level){
          //every 5 levels the map changes
          //different colors for each of 3 kinds of colors
          if(((int)(level / 5.0)) % 3 == 0){
                if(t.getColor() == 0){
                        setBg(terminal, x,y,131,203,58);
                }
                if(t.getColor() == 12){
                        setBg(terminal, x,y,0,255,255);
                }
                //light orange border
                if(t.getColor() == 2){
                        setBg(terminal,x,y,201,134,0);
                }
                if(t.getColor() == 4){
                        setBg(terminal,x,y,52,111,18);
                }
                if(t.getColor() == 10){
                        setBg(terminal,x,y,255,255,0);
                }
                if(t.getColor() == 8){
                        setBg(terminal,x,y,235, 66, 244);
                }
              }
          if(((int)(level / 5.0)) % 3 == 1){
            if(t.getColor() == 0){
                    setBg(terminal, x,y,9, 71, 16);
            }
            if(t.getColor() == 12){
                    setBg(terminal, x,y,0,255,255);
            }
            //dark brown border
            if(t.getColor() == 2){
                    setBg(terminal,x,y,53, 25, 25);
            }
            //swampy water
            if(t.getColor() == 4){
                    setBg(terminal,x,y,2, 46, 122);
            }
            if(t.getColor() == 10){
                    setBg(terminal,x,y,255,255,0);
            }
            if(t.getColor() == 8){
                    setBg(terminal,x,y,235, 66, 244);
            }
          }

          if(((int)(level / 5.0)) % 3 == 2){
            //snowy boy
            if(t.getColor() == 0){
                    setBg(terminal, x,y,234, 241, 242);
            }
            if(t.getColor() == 12){
                    setBg(terminal, x,y,0,255,255);
            }
            //icy
            if(t.getColor() == 2){
                    setBg(terminal,x,y,155, 238, 255);
            }
            //outside ice
            if(t.getColor() == 4){
                    setBg(terminal,x,y,96, 218, 242);
            }
            if(t.getColor() == 10){
                    setBg(terminal,x,y,255,255,0);
            }
            if(t.getColor() == 8){
                    setBg(terminal,x,y,235, 66, 244);
            }
          }
        }

        //Spawns the hostilePokemons.
        public static List<Pokemon> spawnHostilePokemons(Tile[][] m, Terminal t){
                int spawned = 0;
                List<Pokemon> p = new ArrayList<Pokemon>();
                while(spawned < 10){
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
        public static void buildMap(Tile[][] mapMap, double level){
                for (int x = 0; x < mapMap.length;x++) {
                        for(int y = 0 ; y < mapMap[0].length;y++){
                                terminal.putCharacter(' ');
                                //terminal.putCharacter(("" + y).charAt(0));
                                setBg(terminal,mapMap[x][y],y,x,level);
                        }
                }
        }


        //Sets up start screen.
        public static void setUpStartScreen(Screen start, int xSize) {
                start.startScreen();
                start.updateScreenSize();
                //Black area
                for(int i = 0; i < start.getTerminalSize().getRows();i++) {
                        for(int x = 0; x < start.getTerminalSize().getColumns(); x++) {
                                start.putString(x,i," ",Terminal.Color.BLACK,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                        }
                }
                start.putString(xSize * 3/8,15, "                 Pokemon Mystery Dungeon               ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                start.putString(xSize * 3/8,20, "                    Press S to Start!               ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Blinking);
                start.putString(xSize * 3/8 - 3, 30, " If you wish to look at the instructions and options, press Backspace!    ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                start.refresh();
        }

        public static void setUpGameOverScreen(Screen gameOver, int xSize, double floor) {
                gameOver.startScreen();
                for(int i = 0; i < gameOver.getTerminalSize().getRows();i++) {
                        for(int x = 0; x < gameOver.getTerminalSize().getColumns(); x++) {
                                gameOver.putString(x,i," ",Terminal.Color.BLACK,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                        }
                }
                gameOver.putString(xSize * 3/8,15, "                 GAME OVER! You fainted =(              ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                gameOver.putString(xSize * 3/8,18, "                 You reached Floor Level: " + floor + "!            ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                gameOver.putString(xSize * 3/8,21, "                 Continue? Press R to Restart.            ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Blinking);
                gameOver.refresh();
        }

        //Sets up option Screen.
        public static void setUpOptionsScreen(Screen options, int xSize) {
                options.startScreen();
                options.updateScreenSize();
                //Black area.
                for(int i = 0; i < options.getTerminalSize().getRows(); i++) {
                        for(int x = 0; x < options.getTerminalSize().getColumns(); x++) {
                                options.putString(x,i," ",Terminal.Color.BLACK,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                        }
                }

                //This code details the left portion of the screen. (Instructions)
                options.putString(xSize/2 - 20,4,"Instructions & Options:",Terminal.Color.GREEN,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,6,"Q & A:",Terminal.Color.GREEN,Terminal.Color.BLACK,ScreenCharacterStyle.Underline);
                options.putString(xSize/4 - 23,8,"Question: Where am I?                                 Answer: Don't fret! You're represented as a @.",Terminal.Color.GREEN,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,9,"Question: Why is there another box following me?      Answer: That's your partner, it's another pokemon to help you on your journey! Represented as a ^.",Terminal.Color.GREEN,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,10,"Question: How do I win?                               Answer: Continue exploring and going up levels! Don't lose all your HP from enemy pokemon, or else it's GAME OVER.",Terminal.Color.GREEN,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,13, "Map Information:", Terminal.Color.GREEN,Terminal.Color.BLACK, ScreenCharacterStyle.Underline);
                options.putString(xSize/4 - 23,15, "Objective: Reach the stairs of each level! Looks like --> ", Terminal.Color.GREEN,Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 + 36,15, " ", Terminal.Color.BLACK, Terminal.Color.YELLOW, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,16, "To see what floor level you are on and other info, check the cyan player box in the game!", Terminal.Color.GREEN,Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,17, "There are also portals that teleport you to another, these look like --> ", Terminal.Color.GREEN,Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 + 50,17, " ", Terminal.Color.BLACK, Terminal.Color.CYAN, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,18, "Some tiles heal your pokemon to their max HP, these look like --> ", Terminal.Color.GREEN,Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 + 43,18, " ", Terminal.Color.BLACK, Terminal.Color.MAGENTA, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,20, "Controls:", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Underline);
                options.putString(xSize/4 - 23,22, "1. Wait a turn --> Press Q", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,24, "2. Move Up --> Up Arrow Key (Faces up)", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,26, "3. Move Down --> Down Arrow Key (Faces down)", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,28, "4. Move Left --> Left Arrow Key (Faces left)", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,30, "5. Move Right --> Right Arrow Key (Faces Right)", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,32, "6. To use attacks in the moveset --> You must face a pokemon first.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,33, "     6a. To face left --> Press A.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,34, "     6b. To face right --> Press D", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,35, "     6c. To face up --> Press W.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,36, "     6d. To face down --> Press S.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,37, "     6e. Then, Look at the blue screen AND: Click on either 1, 2, 3 or 4 to use that specific move!", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,39, "Partner (AI) Controls:", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Underline);
                options.putString(xSize/4 - 23,41, "You cannot facilitate movement of your partner, but you can set the move that you want it to use.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,42, "Press either 5, 6, 7, or 8 to set a move for your partner to use. Once it is set, press 'Q' to wait another turn so your partner can attack.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 23,43, "Or... you can attack yourself, thus triggering your partner's turn!", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.refresh();
        }


        //Sets up Combat Screen.
        public static void setUpCombatScreen(Screen combat, int xSize, int ySize) {
                combat.startScreen();
                for(int y = 0; y < combat.getTerminalSize().getRows()/2; y++) {
                        for(int x = xSize * 1/2; x < combat.getTerminalSize().getColumns(); x++) {
                                combat.putString(x,y," ",Terminal.Color.BLACK,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                        }
                }
                addMessageToCombat(combat,"COMBAT MESSAGES AND ACTIONS: ", xSize/2 + 8, 3, "bold");
                addMessageToCombat(combat,"Any messages that arrive here will reset all messages above!", xSize/2 + 8, 25, "blinking");
        }

        //updateCombatScreen

        //Method to add message to combatScreen:
        public static void addMessageToCombat(Screen combat, String message, int x, int y, String style) {
                if(style == "bold") {
                        combat.putString(x,y,message, Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                }
                if(style == "blinking") {
                        combat.putString(x,y,message, Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Blinking);
                }
        }

        //Sets up the info screen.
        public static void setUpInfoScreen(Screen info, int xSize, int ySize) {
                info.startScreen();
                for(int y = ySize/2; y < info.getTerminalSize().getRows(); y++) {
                        for(int x = xSize * 1/2; x < info.getTerminalSize().getColumns(); x++) {
                                info.putString(x,y," ",Terminal.Color.BLACK,Terminal.Color.CYAN,ScreenCharacterStyle.Bold);
                        }
                }
                info.putString(xSize/2 + 8, 28, "POKEMON INFORMATION/STATUS:  ", Terminal.Color.WHITE,Terminal.Color.CYAN,ScreenCharacterStyle.Bold);
        }

        //Adds message to the screen.
        public static void addMessageToInfo(Screen sideScreen, String message, int x, int y ) {
                sideScreen.putString(x,y,message, Terminal.Color.WHITE, Terminal.Color.CYAN, ScreenCharacterStyle.Bold);
        }

        //Adds all player information to info screen.
        public static void addPlayerInfo(Screen sideScreen, Player p, List<Pokemon> all, int xSize, double level) {
                addMessageToInfo(sideScreen, "___________________________________________________________________________________", xSize/2 + 8, 29);
                addMessageToInfo(sideScreen, "Player: " + p.getPlayer().toString() + "            Partner: " + p.getPartner().toString(), xSize/2 + 8, 30);
                addMessageToInfo(sideScreen, "Gold: " + p.getGold() + "                                    Floor Level: " + level, xSize/2 + 8, 31);
                addMessageToInfo(sideScreen, "___________________________________________________________________________________", xSize/2 + 8, 32);
                addMessageToInfo(sideScreen, "POKEMON MOVESETS: ", xSize/2 + 8, 33);
                addMessageToInfo(sideScreen, "___________________________________________________________________________________", xSize/2 + 8, 34);
                addMessageToInfo(sideScreen, "Player Movesets:             Partner Movesets:" , xSize/2 + 8, 35);
                addMessageToInfo(sideScreen, "1. " + p.getPlayer().getMoveset().get(0), xSize/2 + 8, 36);
                addMessageToInfo(sideScreen, "2. " + p.getPlayer().getMoveset().get(1), xSize/2 + 8, 37);
                addMessageToInfo(sideScreen, "3. " + p.getPlayer().getMoveset().get(2), xSize/2 + 8, 38);
                addMessageToInfo(sideScreen, "4. " + p.getPlayer().getMoveset().get(3), xSize/2 + 8, 39);
                addMessageToInfo(sideScreen, "5. " + p.getPartner().getMoveset().get(0), xSize/2 + 37, 36);
                addMessageToInfo(sideScreen, "6. " + p.getPartner().getMoveset().get(1), xSize/2 + 37, 37);
                addMessageToInfo(sideScreen, "7. " + p.getPartner().getMoveset().get(2), xSize/2 + 37, 38);
                addMessageToInfo(sideScreen, "8. " + p.getPartner().getMoveset().get(3), xSize/2 + 37, 39);
                addMessageToInfo(sideScreen, "___________________________________________________________________________________", xSize/2 + 8, 40);
                addMessageToInfo(sideScreen, "ENEMY POKEMON IN THE MAP: ", xSize/2 + 8, 41);
                int current = 43;
                for(int i = 2; i < all.size(); i++) {
                        addMessageToInfo(sideScreen, all.get(i).toString(), xSize/2 + 8, current);
                        current++;
                }
        }


        //spawns player pokemons from player class using putPokemon()
        public static void spawnPlayer(Player player, Terminal t, Map m, boolean partnerFainted){
                putPokemon(m.getStartX(),m.getStartY(), t, player.getPlayer());
                if(!partnerFainted) {
                putPokemon(m.getStartX(),m.getStartY() + 1, t, player.getPartner());
                }
        }

        //Checks to see if a pokemon is facing you before attacking.
        //Only used for opposing pokemon to face you.
        public static boolean isFacing(Pokemon a, Pokemon b) {
                return a.getX() == b.getX() + 1 && a.getY() == b.getY() ||
                        a.getX() == b.getX() - 1 && a.getY() == b.getY() ||
                        a.getX() == b.getX() && a.getY() == b.getY() + 1 ||
                        a.getX() == b.getX() && a.getY() == b.getY() - 1;
        }

        //Uses moves.
        public static String manageMove(Pokemon a, Pokemon target, int moveIndex) {
                return a.useMove(a.getMoveset().get(moveIndex - 1),target);
        }







        //MAIN PROGRAM

        public static void main(String[] args) {
                double level = 1;
                List<Pokemon> allPokemons = new ArrayList<Pokemon>();
                Pokemon playerPokemon = PokemonRandomizer.returnPokemon();
                playerPokemon.setSymbol("@");
                Pokemon partnerPokemon = PokemonRandomizer.returnPokemon();
                while(partnerPokemon.getName().equals(playerPokemon.getName())){
                        partnerPokemon = PokemonRandomizer.returnPokemon();
                }
                partnerPokemon.setSymbol("^");
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
                int ySize = options.getTerminalSize().getRows();
                int xSize = options.getTerminalSize().getColumns();
                int facingX = 0;
                int facingY = 0;
                int yMessage = 5;
                Screen start = new Screen(terminal, terminalSize);
                Screen sideScreen = new Screen(terminal, terminalSize);
                Screen character = new Screen(terminal, terminalSize);
                Screen gameOver = new Screen(terminal, terminalSize);

                //Variables:
                boolean running = true;
                boolean optionsOn = false;
                boolean generated = false;
                boolean playerTurn = true;
                boolean partnerTurn = false;
                boolean partnerFainted = false;
                //Sets partner move to be the first in the beginning.
                Move partnerMove = player.getPartner().getMoveset().get(0);
                Random walkGenerator;
                Random moveGenerator;

                //Game mode 0 --> Represents start screen.
                //Game mode 1 --> Represents actual gameplay.
                int gameMode = 0;

                //gets map from Map generator class
                Map testMap = new Map();
                Tile[][] mapMap = testMap.getMap();

                //calls buildMap to display map and spawns player pokemons


                //makes sure there isn't a spawn error and runs the map building and spawning process again
                //if pokemons spawn improperly (highly unlikely but theoretically possible)
                //while(mapMap[testMap.getStartY()][testMap.getStartX()].getColor() != 0){
                //        testMap = new Map();
                //        mapMap = testMap.getMap();
                //        buildMap(mapMap);
                //        spawnPlayer(player, terminal, testMap);
                //}



                //@@@@@@@@@@RUNNING SECTION OF THE MAIN PROGRAM@@@@@@@
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
                                        if(!optionsOn && gameMode != 2) {
                                                start.stopScreen();
                                                sideScreen.stopScreen();
                                                setUpOptionsScreen(options,xSize);
                                                optionsOn = true;
                                        }
                                        //This block of code is called when you're in the options page.
                                        else if(gameMode == 1) {
                                                options.stopScreen();
                                                options.completeRefresh();
                                                terminal.enterPrivateMode();
                                                //Generates the map again.
                                                setUpCombatScreen(sideScreen, xSize, ySize);
                                                setUpInfoScreen(sideScreen, xSize, ySize);
                                                addPlayerInfo(sideScreen, player, allPokemons, xSize, level);
                                                buildMap(mapMap,level);
                                                for(Pokemon p : allPokemons) {
                                                        putPokemon(p.getX(),p.getY(),terminal,p);
                                                }
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
                                //Starting the game:
                                if (key.getCharacter() == 's' && gameMode == 0) {
                                        start.stopScreen();
                                        gameMode = 1;
                                        generated = false;
                                }
                                //GAME OVER SCREEN CONTROLS
                                //Press r to restart the game!
                                if (key.getCharacter() == 'r' && gameMode == 2) {
                                        gameOver.stopScreen();
                                        gameMode = 1;
                                        level = 1;
                                        testMap = new Map();
                                        mapMap = testMap.getMap();
                                        player.getPlayer().resetHp();
                                        player.getPartner().resetHp();
                                        player.getPlayer().resetLevel();
                                        player.getPartner().resetLevel();
                                        player.getPlayer().resetExp();
                                        player.getPartner().resetExp();
                                        partnerFainted = false;
                                        terminal.enterPrivateMode();
                                        setUpCombatScreen(sideScreen, xSize, ySize);
                                        //Setting up bottom portions for info:
                                        setUpInfoScreen(sideScreen, xSize, ySize);
                                        buildMap(mapMap, level);
                                        terminal.setCursorVisible(false);
                                        allPokemons.clear();
                                        spawnPlayer(player, terminal, testMap,partnerFainted);
                                        allPokemons.add(player.getPlayer());
                                        allPokemons.add(player.getPartner());
                                        allPokemons.addAll(spawnHostilePokemons(mapMap, terminal));
                                        generated = true;
                                }
                                //GAMEPLAY CONTROLS, ONLY FOR GAMEMODE 1
                                if(gameMode == 1) {

                                        //Pressing Q will make you wait a turn.
                                        if(key.getCharacter() == 'q') {
                                                addMessageToCombat(sideScreen, "Waited one turn.", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
                                                if(!partnerFainted) partnerTurn = true;
                                        }
                                        //This code details facing the enemy before using the skill.
                                        if(key.getCharacter() == 'a') {
                                                facingX = 0;
                                                facingY = -1;
                                                addMessageToCombat(sideScreen, "Faced Left!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                        }
                                        if(key.getCharacter() == 'd') {
                                                facingX = 0;
                                                facingY = 1;
                                                addMessageToCombat(sideScreen, "Faced Right!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                        }
                                        if(key.getCharacter() == 'w') {
                                                facingX = -1;
                                                facingY = 0;
                                                addMessageToCombat(sideScreen, "Faced Up!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                        }
                                        if(key.getCharacter() == 's') {
                                                facingX = 1;
                                                facingY = 0;
                                                addMessageToCombat(sideScreen, "Faced Down!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                        }

                                        //The reason why we cannot have a separate method for each of the following is BECAUSE OF ymessage++;
                                        //Uses first move in moveset.
                                        if(key.getCharacter() == '1') {
                                                //Starts at 2 to avoid partner and player.
                                                for(int i = 2; i < allPokemons.size(); i++) {
                                                        if(allPokemons.get(i).getX() == player.getPlayer().getX() + facingX && allPokemons.get(i).getY() == player.getPlayer().getY() + facingY) {
                                                                //Action for dealing damage.
                                                                //Adds combat message!
                                                                addMessageToCombat(sideScreen, "You: " + player.getPlayer().useMove(player.getPlayer().getMoveset().get(0),allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                //moves message coordinate down.
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        player.getPlayer().gainExp();
                                                                        player.getPartner().gainExp();
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted! You gained exp!", xSize/2 + 8, yMessage, "bold");
                                                                        mapMap[allPokemons.get(i).getY()][allPokemons.get(i).getX()].makeWalkable();
                                                                        setBg(terminal,allPokemons.get(i).getX(),allPokemons.get(i).getY(),131,203,58);
                                                                        allPokemons.remove(i);
                                                                        for(int y = ySize/2 + 10; y < sideScreen.getTerminalSize().getRows(); y++) {
                                                                                for(int x = xSize * 1/2; x < sideScreen.getTerminalSize().getColumns(); x++) {
                                                                                        sideScreen.putString(x,y," ",Terminal.Color.BLACK,Terminal.Color.CYAN,ScreenCharacterStyle.Bold);
                                                                                }
                                                                        }
                                                                        yMessage++;
                                                                }

                                                        }
                                                }
                                                playerTurn = false;
                                                if(!partnerFainted) partnerTurn = true;
                                        }

                                        //Uses 2nd move in moveset.
                                        if(key.getCharacter() == '2') {
                                                //Starts at 2 to avoid partner and player.
                                                for(int i = 2; i < allPokemons.size(); i++) {
                                                        if(allPokemons.get(i).getX() == player.getPlayer().getX() + facingX && allPokemons.get(i).getY() == player.getPlayer().getY() + facingY) {
                                                                //Action for dealing damage.
                                                                addMessageToCombat(sideScreen, "You: " + player.getPlayer().useMove(player.getPlayer().getMoveset().get(1),allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                //moves message coordinate down.
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        player.getPlayer().gainExp();
                                                                        player.getPartner().gainExp();
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted! You gained exp!", xSize/2 + 8, yMessage, "bold");
                                                                        mapMap[allPokemons.get(i).getY()][allPokemons.get(i).getX()].makeWalkable();
                                                                        setBg(terminal,allPokemons.get(i).getX(),allPokemons.get(i).getY(),131,203,58);
                                                                        allPokemons.remove(i);
                                                                        for(int y = ySize/2 + 10; y < sideScreen.getTerminalSize().getRows(); y++) {
                                                                                for(int x = xSize * 1/2; x < sideScreen.getTerminalSize().getColumns(); x++) {
                                                                                        sideScreen.putString(x,y," ",Terminal.Color.BLACK,Terminal.Color.CYAN,ScreenCharacterStyle.Bold);
                                                                                }
                                                                        }
                                                                yMessage++;
                                                                }
                                                        }
                                                }
                                                playerTurn = false;
                                                if(!partnerFainted) partnerTurn = true;
                                        }

                                        //Uses 3rd Move in Moveset.
                                        if(key.getCharacter() == '3') {
                                                //Starts at 2 to avoid partner and player.
                                                for(int i = 2; i < allPokemons.size(); i++) {
                                                        if(allPokemons.get(i).getX() == player.getPlayer().getX() + facingX && allPokemons.get(i).getY() == player.getPlayer().getY() + facingY) {
                                                                //Action for dealing damage.
                                                                addMessageToCombat(sideScreen, "You: " + player.getPlayer().useMove(player.getPlayer().getMoveset().get(2),allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        player.getPlayer().gainExp();
                                                                        player.getPartner().gainExp();
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted! You gained exp!", xSize/2 + 8, yMessage, "bold");
                                                                        mapMap[allPokemons.get(i).getY()][allPokemons.get(i).getX()].makeWalkable();
                                                                        setBg(terminal,allPokemons.get(i).getX(),allPokemons.get(i).getY(),131,203,58);
                                                                        allPokemons.remove(i);
                                                                        for(int y = ySize/2 + 10; y < sideScreen.getTerminalSize().getRows(); y++) {
                                                                                for(int x = xSize * 1/2; x < sideScreen.getTerminalSize().getColumns(); x++) {
                                                                                        sideScreen.putString(x,y," ",Terminal.Color.BLACK,Terminal.Color.CYAN,ScreenCharacterStyle.Bold);
                                                                                }
                                                                        }
                                                                yMessage++;
                                                                }
                                                        }
                                                }
                                                playerTurn = false;
                                                if(!partnerFainted) partnerTurn = true;
                                        }

                                        //Uses 4th move in Moveset.
                                        if(key.getCharacter() == '4') {
                                                //Starts at 2 to avoid partner and player.
                                                for(int i = 2; i < allPokemons.size(); i++) {
                                                        if(allPokemons.get(i).getX() == player.getPlayer().getX() + facingX && allPokemons.get(i).getY() == player.getPlayer().getY() + facingY) {
                                                                //Action for dealing damage.
                                                                addMessageToCombat(sideScreen, "You: " + player.getPlayer().useMove(player.getPlayer().getMoveset().get(3),allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                //moves message coordinate down.
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        player.getPlayer().gainExp();
                                                                        player.getPartner().gainExp();
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted! You gained exp!", xSize/2 + 8, yMessage, "bold");
                                                                        mapMap[allPokemons.get(i).getY()][allPokemons.get(i).getX()].makeWalkable();
                                                                        setBg(terminal,allPokemons.get(i).getX(),allPokemons.get(i).getY(),131,203,58);
                                                                        allPokemons.remove(i);
                                                                        for(int y = ySize/2 + 10; y < sideScreen.getTerminalSize().getRows(); y++) {
                                                                                for(int x = xSize * 1/2; x < sideScreen.getTerminalSize().getColumns(); x++) {
                                                                                        sideScreen.putString(x,y," ",Terminal.Color.BLACK,Terminal.Color.CYAN,ScreenCharacterStyle.Bold);
                                                                                }
                                                                        }
                                                                yMessage++;
                                                                }
                                                        }
                                                }
                                                playerTurn = false;
                                                if(!partnerFainted) partnerTurn = true;
                                        }
                                        //Keys 5, 6, 7 and 8 determine the move that the partner will be using since it's only a SIMPLE AI POKEMON.
                                        if(key.getCharacter() == '5' && !partnerFainted) {
                                                partnerMove = player.getPartner().getMoveset().get(0);
                                                addMessageToCombat(sideScreen, "Your partner's move is set to: " + partnerMove, xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                        }
                                        if(key.getCharacter() == '6' && !partnerFainted) {
                                                partnerMove = player.getPartner().getMoveset().get(1);
                                                addMessageToCombat(sideScreen, "Your partner's move is set to: " + partnerMove, xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                        }
                                        if(key.getCharacter() == '7' && !partnerFainted) {
                                                partnerMove = player.getPartner().getMoveset().get(2);
                                                addMessageToCombat(sideScreen, "Your partner's move is set to: " + partnerMove, xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                        }
                                        if(key.getCharacter() == '8' && !partnerFainted) {
                                                partnerMove = player.getPartner().getMoveset().get(3);
                                                addMessageToCombat(sideScreen, "Your partner's move is set to: " + partnerMove, xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                        }
                                        if(partnerTurn && !partnerFainted) {
                                                for(int i = 2; i < allPokemons.size(); i++) {
                                                        if(isFacing(player.getPartner(), allPokemons.get(i))) {
                                                                addMessageToCombat(sideScreen, "Partner: " + player.getPartner().useMove(partnerMove, allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        player.getPlayer().gainExp();
                                                                        player.getPartner().gainExp();
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted! You gained exp!", xSize/2 + 8, yMessage, "bold");
                                                                        mapMap[allPokemons.get(i).getY()][allPokemons.get(i).getX()].makeWalkable();
                                                                        setBg(terminal,allPokemons.get(i).getX(),allPokemons.get(i).getY(),131,203,58);
                                                                        allPokemons.remove(i);
                                                                        for(int y = ySize/2 + 10; y < sideScreen.getTerminalSize().getRows(); y++) {
                                                                                for(int x = xSize * 1/2; x < sideScreen.getTerminalSize().getColumns(); x++) {
                                                                                        sideScreen.putString(x,y," ",Terminal.Color.BLACK,Terminal.Color.CYAN,ScreenCharacterStyle.Bold);
                                                                                }
                                                                        }
                                                                yMessage++;
                                                        }
                                                }
                                              }
                                                partnerTurn = false;
                                        }
                                        else if(partnerFainted) {
                                                partnerTurn = false;
                                        }
                                        //Check what arrow keys are pressed and if those locations are walkable and moves Pokemon

                                        if (key.getKind() == Key.Kind.ArrowLeft && mapMap[curY -1 ][curX].getWalkable() && !optionsOn) {
                                                movePokemon(mapMap, 0,-1,terminal,player.getPlayer(),level);
                                                if(!partnerFainted) {
                                                    movePokemon(mapMap, 0,-1,terminal,player.getPartner(),level);
                                                }
                                                facingX = 0;
                                                facingY = -1;
                                                addMessageToCombat(sideScreen, "Moved left!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
                                                partnerTurn = false;
                                        }
                                        //checks two to the right to account for partner pokemon
                                        if (key.getKind() == Key.Kind.ArrowRight && mapMap[curY + 2][curX ].getWalkable() && !optionsOn) {
                                                if(!partnerFainted) {
                                                        movePokemon(mapMap, 0,1,terminal,player.getPartner(),level);
                                                }
                                                movePokemon(mapMap, 0,1,terminal,player.getPlayer(),level);
                                                facingX = 0;
                                                facingY = 1;
                                                addMessageToCombat(sideScreen, "Moved Right!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
                                                partnerTurn = false;
                                                mapMap[player.getPartner().getY()][player.getPartner().getX()].getTp();

                                        }
                                        if (key.getKind() == Key.Kind.ArrowUp && mapMap[curY][curX - 1].getWalkable()
                                                        //additional checks used to check partner pokemon location
                                                        && mapMap[curY + 1][curX - 1].getWalkable()
                                                        && !optionsOn) {
                                                movePokemon(mapMap, -1,0,terminal,player.getPlayer(),level);
                                                if(!partnerFainted) {
                                                     movePokemon(mapMap,-1,0,terminal,player.getPartner(),level);
                                                }
                                                facingX = -1;
                                                facingY = 0;
                                                addMessageToCombat(sideScreen, "Moved Up!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
                                                partnerTurn = false;
                                                        }
                                        if (key.getKind() == Key.Kind.ArrowDown && mapMap[curY][curX + 1].getWalkable()
                                                        && mapMap[curY + 1][curX + 1].getWalkable()
                                                        && !optionsOn) {
                                                movePokemon(mapMap, 1,0,terminal,player.getPlayer(),level);
                                                if(!partnerFainted) {
                                                   movePokemon(mapMap, 1,0,terminal,player.getPartner(),level);
                                                }
                                                facingX = 1;
                                                facingY = 0;
                                                addMessageToCombat(sideScreen, "Moved Down!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
                                                partnerTurn = false;
                                                        }
                                          //checks if player is on a stair to start a new level
                                        if(mapMap[player.getPlayer().getY()][player.getPlayer().getX()].getColor() == 10 ||
                                            mapMap[player.getPartner().getY()][player.getPartner().getX()].getColor() == 10){
                                                level++;
                                                testMap = new Map();
                                                mapMap = testMap.getMap();
                                                buildMap(mapMap,level);
                                                allPokemons.clear();
                                                spawnPlayer(player, terminal, testMap,partnerFainted);
                                                allPokemons.add(player.getPlayer());
                                                allPokemons.add(player.getPartner());
                                                allPokemons.addAll(spawnHostilePokemons(mapMap, terminal));
                                                addPlayerInfo(sideScreen, player, allPokemons, xSize, level);

                                        }
                                        //used to move player if they are on a portal
                                        //gets if tiles the player pokemons are on lead to another tile
                                        Tile playerTile = mapMap[player.getPlayer().getY()][player.getPlayer().getX()].getTp();
                                        Tile partnerTile =mapMap[player.getPartner().getY()][player.getPartner().getX()].getTp();
                                        if(playerTile != null|| partnerTile != null){
                                              //puts coords of player and partner into variables
                                               curX = player.getPlayer().getX();
                                               curY = player.getPlayer().getY();
                                               int pX = player.getPartner().getX();
                                               int pY = player.getPartner().getY();


                                              // if its the player pokemon that is on a portal
                                              if(playerTile != null){
                                                //resets portal so it cant  be used again
                                                addMessageToCombat(sideScreen,"You and your partner teleported!", xSize/2 + 8, yMessage, "bold"); 
                                                yMessage++;
                                                mapMap[player.getPlayer().getY()][player.getPlayer().getX()].getTp().setTp(null);
                                                mapMap[player.getPlayer().getY()][player.getPlayer().getX()].setTp(null);
                                                //moves pokemons
                                                putPokemon(playerTile.getX(),playerTile.getY(),terminal,player.getPlayer());
                                                putPokemon(playerTile.getX(),playerTile.getY() + 1,terminal,player.getPartner());
                                                //updates pokemon object coordinates
                                                player.getPlayer().setLocation(playerTile.getX(),playerTile.getY());
                                                  player.getPartner().setLocation(playerTile.getX(),playerTile.getY() + 1);
                                                  //changes tile color values to remove portal color
                                                  mapMap[curY][curX].setColor(0);
                                                  playerTile.setColor(0);
                                              }
                                              if(partnerTile != null){
                                                //resets portal so it cant  be used again
                                                addMessageToCombat(sideScreen,"You and your partner teleported!", xSize/2 + 8, yMessage, "bold"); 
                                                yMessage++;
                                                mapMap[player.getPartner().getY()][player.getPartner().getX()].getTp().setTp(null);
                                                mapMap[player.getPartner().getY()][player.getPartner().getX()].setTp(null);
                                                //physically places new pokemons on map
                                                putPokemon(partnerTile.getX(),partnerTile.getY() - 1, terminal,player.getPlayer());
                                                putPokemon(partnerTile.getX(),partnerTile.getY(),terminal,player.getPartner());
                                                player.getPlayer().setLocation(partnerTile.getX(),partnerTile.getY() - 1);
                                                //updates pokemon object coordinates
                                                  player.getPartner().setLocation(partnerTile.getX(),partnerTile.getY());
                                                  //removes tile color vlaues
                                                  mapMap[pY][pX].setColor(0);
                                                  partnerTile.setColor(0);
                                              }
                                              //update to remove portal colors
                                              setBg(terminal, mapMap[curY][curX], curX, curY,level);
                                              mapMap[curY][curX].makeWalkable();
                                              setBg(terminal, mapMap[pY][pX], pX, pY,level);
                                              mapMap[pY][pX].makeWalkable();
                                            }
                                        //checks if player is on a potion;
                                        int healHp = mapMap[player.getPlayer().getY()][player.getPlayer().getX()].getHealthPotion();
                                        int partHealHp = mapMap[player.getPartner().getY()][player.getPartner().getX()].getHealthPotion();
                                        if(healHp != 0 || partHealHp != 0){
                                          //healing values of tile that the pokemon is on
                                          player.getPlayer().healHp(healHp);
                                          player.getPartner().healHp(healHp);
                                          player.getPlayer().healHp(partHealHp);
                                          player.getPartner().healHp(partHealHp);
                                          //if the player pokemon is on a heal tile, heal both
                                          if(healHp != 0){
                                            mapMap[player.getPlayer().getY()][player.getPlayer().getX()].setHealthPotion(0);
                                            mapMap[player.getPlayer().getY()][player.getPlayer().getX()].setColor(0);
                                          }
                                          //if the partner pokemon is on a heal tile, heal both
                                          if(partHealHp != 0){
                                            mapMap[player.getPartner().getY()][player.getPartner().getX()].setHealthPotion(0);
                                            mapMap[player.getPartner().getY()][player.getPartner().getX()].setColor(0);
                                          }
                                          //addPlayerInfo(sideScreen, player, allPokemons, xSize);
                                          addMessageToCombat(sideScreen, "Healed " + (healHp + partHealHp) + "HP!", xSize/2 + 8, yMessage, "bold");
                                          yMessage++;
                                        }
                                }

                                /*
                                // Debug Code: Used to display current player location
                                curX = player.getPlayer().getX();
                                curY = player.getPlayer().getY();
                                putString(10,40,terminal," " + curX + " " + curY);
                                */
                        }
                        //Checks to see if the # of combat messages has overflowed:
                        if(yMessage >= 25) {
                                setUpCombatScreen(sideScreen, xSize, ySize);
                                yMessage = 5;
                        }
                        //below represents closing of running.
                        //Start Screen Controls:
                        if(gameMode == 0 && !generated) {
                                terminal.enterPrivateMode();
                                setUpStartScreen(start, xSize);
                                generated = true;
                        }
                        //Gameplay Screen Actions:
                        if(gameMode == 1 && !generated) {
                                terminal.enterPrivateMode();
                                setUpCombatScreen(sideScreen, xSize, ySize);
                                //Setting up bottom portions for info:
                                setUpInfoScreen(sideScreen, xSize, ySize);
                                buildMap(mapMap,level);
                                spawnPlayer(player, terminal, testMap,partnerFainted);
                                terminal.setCursorVisible(false);
                                allPokemons.addAll(spawnHostilePokemons(mapMap, terminal));
                                generated = true;
                        }
                        if(!playerTurn && !partnerTurn) {
                                //Goes through all the enemy pokemon for them to make an action.
                                for(int i = 2; i < allPokemons.size(); i++) {
                                        //random move index for the enemy to use.
                                        int rmi = ThreadLocalRandom.current().nextInt(1,5);
                                        Pokemon enemy = allPokemons.get(i);
                                        int[] move = allPokemons.get(i).moveTowards(player.getPlayer());
                                        //If enemy is directly in front of them, attack player, else: just move.
                                if(isFacing(enemy,player.getPlayer())) {
                                        //Action for dealing damage.
                                        //Uses 1st move for now. Make 1 a variable later for the below.
                                        addMessageToCombat(sideScreen, manageMove(enemy,player.getPlayer(),rmi),xSize/2+8,yMessage,"bold");
                                        yMessage++;
                                }
                                else if(!partnerFainted && isFacing(enemy,player.getPartner())) {
                                        addMessageToCombat(sideScreen, manageMove(enemy,player.getPartner(),rmi),xSize/2+8,yMessage,"bold");
                                        yMessage++;
                                }
                                else if(Math.random()>0.8){
                                      movePokemon(mapMap, move[0], move[1], terminal, allPokemons.get(i),level);
                                }
                                }
                                playerTurn = true;
                        }

                        if((player.getPlayer().getHp() <= 0)) {
                                sideScreen.stopScreen();
                                gameMode = 2;
                                setUpGameOverScreen(gameOver, xSize, level);
                                generated = true;
                        }

                        //Checks to see if either the player or partner has an exp greater than 200. If they do, they level up!
                        if(player.getPlayer().getExp() > 200) {
                                player.getPlayer().levelUp();
                                addMessageToCombat(sideScreen, "Congratulations! Your leveled up!", xSize/2+8,yMessage,"bold");
                                yMessage++;
                                player.getPlayer().resetExp();
                        }

                        if(player.getPartner().getExp() > 200) {
                                player.getPartner().levelUp();
                                addMessageToCombat(sideScreen, "Congratulations! Your partner leveled up!", xSize/2+8,yMessage,"bold");
                                yMessage++;
                                player.getPartner().resetExp();
                                setUpInfoScreen(sideScreen,xSize,ySize);
                        }


                        if(player.getPartner().getHp() <= 0 && !partnerFainted) {
                                addMessageToCombat(sideScreen, "Oh no! Your partner has fainted!", xSize/2+8, yMessage, "bold");
                                yMessage++;
                                mapMap[player.getPartner().getY()][player.getPartner().getX()].makeWalkable();
                                setBg(terminal,player.getPartner().getX(), player.getPartner().getY(), 131,203,58);
                                partnerFainted = true;
                                setUpInfoScreen(sideScreen,xSize,ySize);
                        }

                        //Ending updates:
                        addPlayerInfo(sideScreen, player, allPokemons, xSize, level);
                        sideScreen.refresh();
                }
        }
}
