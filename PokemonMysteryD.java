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


        //gets delta x and delta y and moves pokemon to that location using putPokemon gets information from map to clean up after itself
        public static void movePokemon(Tile[][] mapMap, int dx, int dy, Terminal t, Pokemon p){
          int curX = p.getX();
          int curY = p.getY();
                if(mapMap[curY + dy][curX + dx].getWalkable()){
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
        }

        //Spawns the hostilePokemons.
        public static List<Pokemon> spawnHostilePokemons(Tile[][] m, Terminal t){
                int spawned = 0;
                List<Pokemon> p = new ArrayList<Pokemon>();
                while(spawned < 6){
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

        public static void setUpGameOverScreen(Screen gameOver, int xSize) {
                gameOver.startScreen();
                for(int i = 0; i < gameOver.getTerminalSize().getRows();i++) {
                        for(int x = 0; x < gameOver.getTerminalSize().getColumns(); x++) {
                                gameOver.putString(x,i," ",Terminal.Color.BLACK,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                        }
                }
                gameOver.putString(xSize * 3/8,15, "                 GAME OVER!               ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                gameOver.putString(xSize * 3/8,20, "              Press R to try again?            ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Blinking);
                gameOver.putString(xSize * 3/8 - 3, 30, " If you wish to look at the instructions and options, press Backspace!    ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
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
                options.putString(xSize/2 - 10,8,"Instructions & Options:",Terminal.Color.GREEN,Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,15, "Instructions:", Terminal.Color.GREEN,Terminal.Color.BLACK, ScreenCharacterStyle.Underline);
                options.putString(xSize/4 - 3,17, "Objective: Reach the stairs of each level! Looks like --> ", Terminal.Color.GREEN,Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 + 56,17, " ", Terminal.Color.BLACK, Terminal.Color.YELLOW, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,20, "Key Controls:", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Underline);
                options.putString(xSize/4 - 3,22, "1. Enable/Disable Option --> Space Bar", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,24, "2. Move Up --> Up Arrow Key", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,26, "3. Move Down --> Down Arrow Key", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,28, "4. Move Left --> Left Arrow Key", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,30, "5. Move Right --> Right Arrow Key", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,32, "6. To use attacks in the moveset --> You must face a specific pokemon first.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);

                options.putString(xSize/4 - 3,33, "     6a. To face left --> Press A.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,34, "     6b. To face right --> Press D", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,35, "     6c. To face up --> Press W.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,36, "     6d. To face down --> Press S.", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                options.putString(xSize/4 - 3,37, "     6e. Then, Look at the blue screen AND: Click on either 1, 2, 3 or 4 to use the move inside of the array!", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
                //This code details the right portion of the screen. (Options)
                options.putString(xSize * 3/4 - 10,15, "Options:", Terminal.Color.GREEN,Terminal.Color.BLACK, ScreenCharacterStyle.Underline);
                options.putString(xSize * 3/4 - 10,17, "Enable Music Track?", Terminal.Color.GREEN, Terminal.Color.BLACK,ScreenCharacterStyle.Bold);
                //Implement space bar to set true/false for options later..
                options.putString(xSize * 3/4 + 12,17, "Status: ", Terminal.Color.GREEN, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
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
                info.putString(xSize/2 + 8, 30, "POKEMON INFORMATION/STATUS:  ", Terminal.Color.WHITE,Terminal.Color.CYAN,ScreenCharacterStyle.Bold);
        }

        //Adds message to the screen.
        public static void addMessageToInfo(Screen sideScreen, String message, int x, int y ) {
                sideScreen.putString(x,y,message, Terminal.Color.WHITE, Terminal.Color.CYAN, ScreenCharacterStyle.Bold);
        }

        //Adds all player information to info screen.
        public static void addPlayerInfo(Screen sideScreen, Player p, List<Pokemon> all, int xSize) {

                addMessageToInfo(sideScreen, "___________________________________________________________________________________", xSize/2 + 8, 31);
                addMessageToInfo(sideScreen, "Player: " + p.getPlayer().toString() + "                   Gold: " + p.getGold(), xSize/2 + 8, 32);
                addMessageToInfo(sideScreen, "Partner: " + p.getPartner().toString(), xSize/2 + 8, 33);
                addMessageToInfo(sideScreen, "___________________________________________________________________________________", xSize/2 + 8, 34);
                addMessageToInfo(sideScreen, "POKEMON MOVESETS: ", xSize/2 + 8, 35);
                addMessageToInfo(sideScreen, "___________________________________________________________________________________", xSize/2 + 8, 36);
                addMessageToInfo(sideScreen, "Player Movesets:             Partner Movesets:" , xSize/2 + 8, 37);
                addMessageToInfo(sideScreen, "1. " + p.getPlayer().getMoveset().get(0), xSize/2 + 8, 38);
                addMessageToInfo(sideScreen, "2. " + p.getPlayer().getMoveset().get(1), xSize/2 + 8, 39);
                addMessageToInfo(sideScreen, "3. " + p.getPlayer().getMoveset().get(2), xSize/2 + 8, 40);
                addMessageToInfo(sideScreen, "4. " + p.getPlayer().getMoveset().get(3), xSize/2 + 8, 41);
                addMessageToInfo(sideScreen, "Partner Movesets: " , xSize/2 + 8, 43);
                addMessageToInfo(sideScreen, "5. " + p.getPartner().getMoveset().get(0), xSize/2 + 37, 38);
                addMessageToInfo(sideScreen, "6. " + p.getPartner().getMoveset().get(1), xSize/2 + 37, 39);
                addMessageToInfo(sideScreen, "7. " + p.getPartner().getMoveset().get(2), xSize/2 + 37, 40);
                addMessageToInfo(sideScreen, "8. " + p.getPartner().getMoveset().get(3), xSize/2 + 37, 41);
                addMessageToInfo(sideScreen, "___________________________________________________________________________________", xSize/2 + 8, 42);
                addMessageToInfo(sideScreen, "ENEMY POKEMON IN THE MAP: ", xSize/2 + 8, 43);
                int current = 44;
                for(int i = 2; i < all.size(); i++) {
                        addMessageToInfo(sideScreen, all.get(i).toString(), xSize/2 + 8, current);
                        current++;
                }
        }


        //spawns player pokemons from player class using putPokemon()
        public static void spawnPlayer(Player player, Terminal t, Map m){
                putPokemon(m.getStartX(),m.getStartY(), t, player.getPlayer());
                putPokemon(m.getStartX(),m.getStartY() + 1, t, player.getPartner());
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
                List<Pokemon> allPokemons = new ArrayList<Pokemon>();
                Pokemon playerPokemon = PokemonRandomizer.returnPokemon();
                playerPokemon.setSymbol("\u237b");
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
                                        if(!optionsOn) {
                                                start.stopScreen();
                                                sideScreen.stopScreen();
                                                setUpOptionsScreen(options,xSize);
                                                optionsOn = true;
                                        }
                                        else if(gameMode == 2) {
                                                options.stopScreen();
                                                options.completeRefresh();
                                                terminal.enterPrivateMode();
                                                setUpGameOverScreen(gameOver, xSize);
                                                optionsOn = false;
                                                generated = true;
                                        }
                                        //This block of code is called when you're in the options page.
                                        else if(gameMode == 1) {
                                                options.stopScreen();
                                                options.completeRefresh();
                                                terminal.enterPrivateMode();
                                                //Generates the map again.
                                                setUpCombatScreen(sideScreen, xSize, ySize);
                                                setUpInfoScreen(sideScreen, xSize, ySize);
                                                addPlayerInfo(sideScreen, player, allPokemons, xSize);
                                                buildMap(mapMap);
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
                                if (gameMode == 2) {
                                        if (key.getCharacter() == 'r') {
                                        gameOver.stopScreen();
                                        gameMode = 1;
                                        testMap = new Map();
                                        mapMap = testMap.getMap();
                                        player.getPlayer().resetHp();
                                        terminal.enterPrivateMode();
                                        setUpCombatScreen(sideScreen, xSize, ySize);
                                        //Setting up bottom portions for info:
                                        setUpInfoScreen(sideScreen, xSize, ySize);
                                        buildMap(mapMap);
                                        terminal.setCursorVisible(false);
                                        allPokemons.clear();
                                        spawnPlayer(player, terminal, testMap);
                                        allPokemons.addAll(spawnHostilePokemons(mapMap, terminal));
                                        generated = true;
                                         }
                                }
                                //GAMEPLAY CONTROLS, ONLY FOR GAMEMODE 1
                                if(gameMode == 1) {

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
                                                                addMessageToCombat(sideScreen, player.getPlayer().useMove(player.getPlayer().getMoveset().get(0),allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                //moves message coordinate down.
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted!", xSize/2 + 8, yMessage, "bold");
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
                                        }

                                        //Uses 2nd move in moveset.
                                        if(key.getCharacter() == '2') {
                                                //Starts at 2 to avoid partner and player.
                                                for(int i = 2; i < allPokemons.size(); i++) {
                                                        if(allPokemons.get(i).getX() == player.getPlayer().getX() + facingX && allPokemons.get(i).getY() == player.getPlayer().getY() + facingY) {
                                                                //Action for dealing damage.
                                                                addMessageToCombat(sideScreen, player.getPlayer().useMove(player.getPlayer().getMoveset().get(1),allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                //moves message coordinate down.
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted!", xSize/2 + 8, yMessage, "bold");
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
                                        }

                                        //Uses 3rd Move in Moveset.
                                        if(key.getCharacter() == '3') {
                                                //Starts at 2 to avoid partner and player.
                                                for(int i = 2; i < allPokemons.size(); i++) {
                                                        if(allPokemons.get(i).getX() == player.getPlayer().getX() + facingX && allPokemons.get(i).getY() == player.getPlayer().getY() + facingY) {
                                                                //Action for dealing damage.
                                                                addMessageToCombat(sideScreen, player.getPlayer().useMove(player.getPlayer().getMoveset().get(2),allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted!", xSize/2 + 8, yMessage, "bold");
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
                                        }

                                        //Uses 4th move in Moveset.
                                        if(key.getCharacter() == '4') {
                                                //Starts at 2 to avoid partner and player.
                                                for(int i = 2; i < allPokemons.size(); i++) {
                                                        if(allPokemons.get(i).getX() == player.getPlayer().getX() + facingX && allPokemons.get(i).getY() == player.getPlayer().getY() + facingY) {
                                                                //Action for dealing damage.
                                                                addMessageToCombat(sideScreen, player.getPlayer().useMove(player.getPlayer().getMoveset().get(3),allPokemons.get(i)), xSize/2 + 8,  yMessage, "bold");
                                                                //moves message coordinate down.
                                                                yMessage++;
                                                                if(allPokemons.get(i).getHp() <= 0) {
                                                                        addMessageToCombat(sideScreen, allPokemons.get(i).getName() + " has fainted!", xSize/2 + 8, yMessage, "bold");
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
                                        }
                                        //Check what arrow keys are pressed and if those locations are walkable and moves Pokemon
                                        if (key.getKind() == Key.Kind.ArrowLeft && mapMap[curY -1 ][curX].getWalkable() && !optionsOn) {
                                                movePokemon(mapMap, 0,-1,terminal,player.getPlayer());
                                                movePokemon(mapMap, 0,-1,terminal,player.getPartner());
                                                facingX = 0;
                                                facingY = -1;
                                                addMessageToCombat(sideScreen, "Moved left!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
                                        }
                                        //checks two to the right to account for partner pokemon
                                        if (key.getKind() == Key.Kind.ArrowRight && mapMap[curY + 2][curX ].getWalkable() && !optionsOn) {
                                                movePokemon(mapMap, 0,1,terminal,player.getPartner());
                                                movePokemon(mapMap, 0,1,terminal,player.getPlayer());
                                                facingX = 0;
                                                facingY = 1;
                                                addMessageToCombat(sideScreen, "Moved Right!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
                                        }
                                        if (key.getKind() == Key.Kind.ArrowUp && mapMap[curY][curX - 1].getWalkable()
                                                        //additional checks used to check partner pokemon location
                                                        && mapMap[curY + 1][curX - 1].getWalkable()
                                                        && !optionsOn) {
                                                movePokemon(mapMap, -1,0,terminal,player.getPlayer());
                                                movePokemon(mapMap, -1,0,terminal,player.getPartner());
                                                facingX = -1;
                                                facingY = 0;
                                                addMessageToCombat(sideScreen, "Moved Up!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
                                                        }
                                        if (key.getKind() == Key.Kind.ArrowDown && mapMap[curY][curX + 1].getWalkable()
                                                        && mapMap[curY + 1][curX + 1].getWalkable() 
                                                        && !optionsOn) {
                                                movePokemon(mapMap, 1,0,terminal,player.getPlayer());
                                                movePokemon(mapMap, 1,0,terminal,player.getPartner());
                                                facingX = 1;
                                                facingY = 0;
                                                addMessageToCombat(sideScreen, "Moved Down!", xSize/2 + 8, yMessage, "bold");
                                                yMessage++;
                                                playerTurn = false;
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
                                                addPlayerInfo(sideScreen, player, allPokemons, xSize);
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
                                buildMap(mapMap);
                                spawnPlayer(player, terminal, testMap);
                                terminal.setCursorVisible(false);
                                allPokemons.addAll(spawnHostilePokemons(mapMap, terminal));
                                generated = true;
                        }
                        if(!playerTurn) {
                                for(int i = 2; i < allPokemons.size(); i++) {
                                        Pokemon enemy = allPokemons.get(i);
                                        int[] move = allPokemons.get(i).moveTowards(player.getPlayer());
                                        //If enemy is directly in front of them, attack player, else: just move.
                                if(isFacing(allPokemons.get(i),player.getPlayer())) {
                                        //Action for dealing damage.
                                        //Uses 1st move for now. Make 1 a variable later for the below.
                                        addMessageToCombat(sideScreen, manageMove(enemy,player.getPlayer(),1),xSize/2+8,yMessage,"bold");
                                        yMessage++;
                                }
                                else {
                                        movePokemon(mapMap, move[0], move[1], terminal, allPokemons.get(i));
                                }
                        }
                                playerTurn = true;
                        }

                        if(player.getPlayer().getHp() <= 0) {
                                gameMode = 2;
                                sideScreen.stopScreen();
                                setUpGameOverScreen(gameOver, xSize);
                        }

                        //Ending updates:
                        addPlayerInfo(sideScreen, player, allPokemons, xSize);
                        sideScreen.refresh();
                }
        }
}
