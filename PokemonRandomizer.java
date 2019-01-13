import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class PokemonRandomizer{

  //Normal Moves
  static Move growl = new Move("Growl", "Normal",10,1,5);
  static Move tailWhip = new Move("Tail Whip", "Normal",15,1,5);
  static Move tackle = new Move("Growl", "Normal",20,1,10);
  static Move defenseCurl = new Move("Growl", "Normal",5,1,5);
  //Fire moves
  static Move ember = new Move("Ember","Fire",20,1,50);
  static Move flameWheel = new Move("Flame Wheel","Fire",25,5,20);
  //Water Moves
  static Move waterGun = new Move("Water Gun", "Water",30,10,10);
  static Move bubble = new Move("Bubble", "Water",15,5,8);
  static Move whirlpool = new Move("Whirlpool", "Water",10,1,15);

  static List<Move> fireMoves = new ArrayList(Arrays.asList(ember,flameWheel,growl,tackle));
  static List<Move> waterMoves = new ArrayList(Arrays.asList(waterGun,bubble,whirlpool,tailWhip));
  static List<Move> normalMoves = new ArrayList(Arrays.asList(growl,tackle,tailWhip,defenseCurl));

  static Pokemon charmander = new Pokemon("Charmander", "Fire", "C", 30, new int[ ]{240,10,23}, 1, fireMoves);
  static Pokemon growlithe = new Pokemon("Growlithe", "Fire", "G", 40, new int[ ]{244, 152, 14}, 1, fireMoves);
  static Pokemon squirtle = new Pokemon("Squirtle", "Water", "S", 40, new int[ ]{50, 115, 219}, 1, waterMoves);
  static Pokemon vaporeon = new Pokemon("Vaporeon", "Water", "V", 40, new int[ ]{94, 144, 224}, 1, waterMoves);
  static Pokemon eevee = new Pokemon("Eevee", "Normal", "E", 40, new int[ ]{198, 171, 31}, 1, normalMoves);
  static Pokemon meowth = new Pokemon("Meowth", "Normal", "M", 40, new int[ ]{225, 226, 170}, 1, normalMoves);

  static List<Pokemon> pokemonList = new ArrayList(Arrays.asList(charmander, growlithe,squirtle,vaporeon,eevee,meowth));

  public static Pokemon returnPokemon(){
    try {
      return pokemonList.get((int) (Math.random() * pokemonList.size())).clone();
    } catch(Exception e) {
      return null;
    }
  }
}
