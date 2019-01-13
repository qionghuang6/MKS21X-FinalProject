import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;

public class PokemonRandomizer{

  //Normal Moves
  static Move growl = new Move("Growl", "Normal",10,1,25);
  static Move tailWhip = new Move("Tail Whip", "Normal",15,1,35);
  static Move tackle = new Move("Growl", "Normal",20,1,40);
  static Move defenseCurl = new Move("Growl", "Normal",5,1,10);
  //Fire moves
  static Move ember = new Move("Ember","Fire",20,1,50);
  static Move flameWheel = new Move("Flame Wheel","Fire",25,5,100);
  //Water Moves
  static Move waterGun = new Move("Water Gun", "Water",30,10,100);
  static Move bubble = new Move("Bubble", "Water",15,5,50);
  static Move whirlpool = new Move("Whirlpool", "Water",10,1,25);

  static List fireMoves<Move> = Lists.newArrayList(ember,flameWheel,growl,tackle);
  static List waterMoves<Move> = Lists.newArrayList(waterGun,bubble,whirlpool,tailWhip);
  static List normalMoves<Move> = Lists.newArrayList(growl,tackle,tailWhip,defenseCurl);

  static Pokemon charmander = new Pokemon("Charmander", "Fire", "C", 30, {240,10,23}, 1, fireMoves);
  static Pokemon growlithe = new Pokemon("Growlithe", "Fire", "G", 40, {244,197,66}, 1, fireMoves);
  static Pokemon squirtle = new Pokemon("Squirtle", "Water", "S", 40, {244,197,66}, 1, waterMoves);
  static Pokemon vaporeon = new Pokemon("Growlithe", "Water", "V", 40, {244,197,66}, 1, waterMoves);
  static Pokemon eevee = new Pokemon("Eevee", "Normal", "E", 40, {244,197,66}, 1, normalMoves);
  static Pokemon meowth = new Pokemon("Meowth", "Normal", "M", 40, {244,197,66}, 1, normalMoves);

  List pokemonList<Pokemon> = new ArrayList<Pokemon>({charmander, growlithe,squirtle,vaporeon,eevee,meowth});

  public static Pokemon returnPokemon(){
    return pokemonList.get((int) (Math.random() * pokemonList.size())).clone();
  }
}
