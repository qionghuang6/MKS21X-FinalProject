public class SamplePMDDriver {
        public static void main(String[] args) {
                Pokemon charmander = new Pokemon("charmander", "fire", 30, 0, 5);
                Move ember = new Move("ember", "fire", 25, 3, 4);
                Move tackle = new Move("tackle", "normal", 35, 1, 1);
                System.out.println(charmander);
                System.out.println(ember);
                System.out.println(tackle);
                ember.decreasePp();
                tackle.decreasePp();
                System.out.println(ember);
                System.out.println(tackle);
                charmander.addMove(ember);
                System.out.println(charmander.getMoveset());
        }
}
