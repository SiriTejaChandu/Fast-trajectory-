public class AdaptiveSearch {

    public static void main(String[] args) {
        MazeGrid grid;
        Boolean pathfound;
        int pathcount = 0;

        while (pathcount < 1) {
            grid = new MazeGrid(6,6,0,0,5,5);

            // First Search in Adaptive
            pathfound = grid.AdaptiveStarSearch(0,0);

            if (pathfound) {
                pathcount++;

                grid.showMazeGrid();
                System.out.print("Expansions: " +grid.getExpansions() + ", Optimal Path Cost: "+grid.getOptimalPathCost() + "\n");

                grid.resetPath();


                // Second Search, Different Initial State: pass initial state as argument (row, column)
              boolean path=  grid.AdaptiveStarSearch(4, 3);
                grid.showMazeGrid();
                System.out.print("Expansions: " + grid.getExpansions() + "Optimal Path Cost: "+ grid.getOptimalPathCost() + "\n");
            }

        }

    }

}
