public class Search {

    public static void main(String[] args) {

            MazeGrid grid;
            boolean pathFound;
            int pathCount=0;
        System.out.println("50 Grid worlds");
            while (pathCount<2){

                grid = new MazeGrid(6,6,0,0,5,5);

                grid.showMazeGrid();
                System.out.println();
                System.out.print(pathCount + "\n");
                System.out.println();
                // forward
                long startTime = System.currentTimeMillis();
                pathFound = grid.AStar(0, 1);

                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                pathCount++;
                if (pathFound) {

                   grid.showMazeGrid();
                    System.out.print(/*"number of expansions"+grid.getExpansions() +*/ "running time"+elapsedTime +"ms"+"," + "optimal path cost"+ grid.getOptimalPathCost() /*+ "," +"path traversal"*/);
                    //grid.getOptimalPathCost();
                    System.out.println();

                    System.out.println();
                    grid.resetPath();
                    grid.optimalPathCostListSize=0;
                    grid.expansions=0;
                    // backward
                    long startTime1 = System.currentTimeMillis();
                    //Stopwatch timer = new Stopwatch().start();
                    grid.AStar(1, 2);
                    long stopTime1 = System.currentTimeMillis();
                    long elapsedTime1 = stopTime1 - startTime1;
                    System.out.print(/*"number of expansions"+grid.getExpansions()*/"running time"+ elapsedTime1 +"ms" + ","  + "optimal path cost"+grid.getOptimalPathCost() + "\n");
                   grid.showMazeGrid();
                    System.out.println();
                }

            }
    }




}
