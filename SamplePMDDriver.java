public class SamplePMDDriver {
        public static void main(String[] args) {
                int[] charColor = {3,4,5};
                int[] squirtColor = {33,232,323};
                Pokemon charmander = new Pokemon("charmander", "fire", "@", 30, charColor, 5);
                Pokemon squirtle = new Pokemon("squirtle", "water", "O", 30, squirtColor, 5);
                Player bryan = new Player(charmander, squirtle, 300);
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
                System.out.println(charmander.getColor(0));
                System.out.println(charmander.getColor(1));
                System.out.println(charmander.getColor(2));
        }
}
