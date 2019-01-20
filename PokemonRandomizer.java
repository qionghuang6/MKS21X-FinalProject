import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class PokemonRandomizer{

  //Normal Moves
  private static Move growl = new Move("Growl", "Normal",10,1,5);
  private static Move tailWhip = new Move("Tail Whip", "Normal",15,1,5);
  private static Move tackle = new Move("Growl", "Normal",20,1,10);
  private static Move defenseCurl = new Move("Growl", "Normal",5,1,5);
  //Fire moves
  private static Move ember = new Move("Ember","Fire",20,1,50);
  private static Move flameWheel = new Move("Flame Wheel","Fire",25,5,20);
  //Water Moves
  private static Move waterGun = new Move("Water Gun", "Water",30,10,10);
  private static Move bubble = new Move("Bubble", "Water",15,5,8);
  private static Move whirlpool = new Move("Whirlpool", "Water",10,1,15);
  //grass Moves
  private static Move razorLeaf = new Move("Razor Leaf", "Grass",15,5,8);
  private static Move vineWhip = new Move("Whirlpool", "Grass",10,1,15);

  //movesets to be used in pokemons
  private static List<Move> fireMoves = new ArrayList(Arrays.asList(ember,flameWheel,growl,tackle));
  private static List<Move> waterMoves = new ArrayList(Arrays.asList(waterGun,bubble,whirlpool,tailWhip));
  private static List<Move> normalMoves = new ArrayList(Arrays.asList(growl,tackle,tailWhip,defenseCurl));
  private static List<Move> grassMoves = new ArrayList(Arrays.asList(razorLeaf,vineWhip,tailWhip,growl));

  //list of pokemons, employ movesets
  private static Pokemon charmander = new Pokemon("Charmander", "Fire", "C", 30, new int[ ]{240,10,23}, 1, fireMoves);
  private static Pokemon growlithe = new Pokemon("Growlithe", "Fire", "G", 40, new int[ ]{244, 152, 14}, 1, fireMoves);
  private static Pokemon squirtle = new Pokemon("Squirtle", "Water", "S", 40, new int[ ]{50, 115, 219}, 1, waterMoves);
  private static Pokemon vaporeon = new Pokemon("Vaporeon", "Water", "V", 40, new int[ ]{94, 144, 224}, 1, waterMoves);
  private static Pokemon eevee = new Pokemon("Eevee", "Normal", "E", 40, new int[ ]{198, 171, 31}, 1, normalMoves);
  private static Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass", "B", 40, new int[]{66, 244, 75}, 1 ,grassMoves);
  private static Pokemon meowth = new Pokemon("Meowth", "Normal", "M", 40, new int[ ]{225, 226, 170}, 1, normalMoves);

  //llist of all the pokemons
  private static List<Pokemon> pokemonList = new ArrayList(Arrays.asList(charmander, growlithe,squirtle,vaporeon,eevee,meowth));

  //returns a random pokemon from the pokemonlist
  public static Pokemon returnPokemon(){
    try {
      return pokemonList.get((int) (Math.random() * pokemonList.size())).clone();
    } catch(Exception e) {
      return null;
    }
  }
  //returns one of 3 starter pokemons
  public static Pokemon[] getStarters(){
    return new Pokemon[]{charmander,squirtle,bulbasaur};
  }
}
