public class AdapticeSearchVsSearch {

    public static void main(String[] args) {

        // Forward vs. Adaptive
        MazeGrid grid;
        Boolean pathfound;
        int pathcount = 0;

        while (pathcount < 50) {
            grid = new MazeGrid(101,101,0,0,100,100);

            // First Search in Adaptive would be a normal forward anyway!
            pathfound = grid.AdaptiveStarSearch(0, 0);

            if (pathfound) {
                pathcount++;

                //grid.printMazeGrid();
                System.out.print("expansions"+grid.getExpansions() + ", "+"cost"+grid.getOptimalPathCost() + ", ");

                grid.resetPath();

                // Second Search, Different Initial State: pass initial state as argument (row, column)
                grid.AdaptiveStarSearch(0, 0);
                System.out.print("expansions"+grid.getExpansions() + ", " +"cost"+grid.getOptimalPathCost() + "\n");
            }

        }

    }

}
