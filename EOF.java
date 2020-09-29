public class EOF {
    public static void main(String[] args) {

        // Test larger vs smaller tie breaking
        MazeGrid grid;
        Boolean pathfound;
        int pathcount = 0;

        while (pathcount < 50) {
            grid = new MazeGrid(101, 101, 0, 0, 100, 100);

            // smaller
            pathfound = grid.AStar(0, 1);
            if (pathfound) {
                pathcount++;
                System.out.print("expansions"+grid.getExpansions() + "," +"cost"+grid.getOptimalPathCost() + ",");
                grid.resetPath();

                // larger
                grid.AStar(0, 2);
                System.out.print("expansions"+grid.getExpansions() + "," +"cost"+grid.getOptimalPathCost() + "\n");
            }

        }
    }
}
