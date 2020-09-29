import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import  java.lang.Math;

public class MazeGrid {

    GridNode [][] gridworld;
    int rw_max,cl_max;
    boolean foundPath;
    ArrayList<GridNode> openList;
    ArrayList<GridNode> currentList = new ArrayList<GridNode>();
    Stack<GridNode> closeList;
    ArrayList<GridNode> blockedNodes = new ArrayList<GridNode>();
    Stack<GridNode> shortestPath;
    Stack<GridNode> shortestPathFinal;
    int optimalPathCostListSize=0;
    int[][] countNodes;
    ArrayList<GridNode> currentShortestPath  = new ArrayList<GridNode>();;
    GridNode current ;
    GridNode start;
    GridNode target;
    GridNode agent = new GridNode();
    int expansions;
    int count=0;
    boolean result;
    boolean isAdaptive=false;
    boolean second=false;
    public MazeGrid(int rw, int cl, int start_rw, int start_cl, int target_rw, int target_cl) {

        if(rw < 1){
            rw_max=101;
        }
        if(cl < 1){
            cl_max=101;
        }
        rw_max=rw-1;
        cl_max=cl-1;
        this.gridworld=new GridNode[rw][cl];
        this.countNodes = new int[rw][cl];
        setMazeGrid();


        // check for start node
        if(start_rw > rw || start_cl > cl || start_rw < 0 || start_cl < 0){
            start = this.gridworld[0][0];
        }else{
            start = this.gridworld[start_rw][start_cl];
        }

        // check for target node
        if(target_rw > rw || target_cl > cl || target_rw < 0 || target_cl < 0){
            target = this.gridworld[rw_max][cl_max];
        }else{
            target = this.gridworld[target_rw][target_cl];
        }

        calculateHvalue();

        openList = new ArrayList<GridNode>();
        closeList = new Stack<GridNode>();
        shortestPath = new Stack<GridNode>();
        shortestPathFinal = new Stack<GridNode>();


        start.isNodeBlocked = false;
        target.isNodeBlocked = false;
        this.current= start;
        expansions=0;
        foundPath=false;

    }

    public void calculateHvalue() {
        for(int i=0;i<=rw_max;i++){
            for(int j=0;j<=cl_max;j++){
                this.gridworld[i][j].hval = Math.abs(target.rw - i) + Math.abs(target.cl - j);

            }

    }}

    public void calculateGvalue(GridNode curr) {
        agent=curr;
        for(int i=0;i<=rw_max;i++){
            for(int j=0;j<=cl_max;j++){
                this.gridworld[i][j].gval = Math.abs(agent.rw - i) + Math.abs(agent.cl - j);

            }

        }}

    public void setMazeGrid()
    {

    for(int i=0;i<=rw_max;i++){
            for(int j=0;j<=cl_max;j++){
                this.gridworld[i][j] = new GridNode(i,j,0,blockedValue(),rw_max,cl_max);
            }
        }

    }

    public boolean blockedValue(){
        return Math.random() * 10 < 3 ? true : false;
    }

    public void showMazeGrid() {

        for(int i=0;i<=rw_max;i++){
            System.out.print(i +"\t");
            for(int j=0; j<= cl_max;j++) {
                if (this.gridworld[i][j].isNodeBlocked) {
                    System.out.print("X");
                } else if (this.gridworld[i][j].path) {
                    System.out.print("O");
                } else {
                    System.out.print("_");
                }

            }
            System.out.print("\n");
        }
    }


    /**
     * Helper function to perform A* Search forward or backwards
     *
     * @param direction
     *            0 for forward, 1 for backward
     * @return True if path if found, false if not
     */
    public Boolean AStar(int direction, int priority) {
        Boolean result = false;

        expansions = 0;

        if (direction == 0) { // forward
            current = start;
            calculateHvalue();
            result = AStarSearch(0, priority,direction);



            openList.clear();
            currentShortestPath.clear();
            shortestPathFinal.clear();

            shortestPath.clear();
            count--;

            while (!closeList.isEmpty()) {
                closeList.pop();
            }

            return result;
        } else if (direction == 1) { // backward

            // swap initial and goal
            GridNode temp = target;
            target = start;
            start = temp;
            calculateHvalue();
            current = start;

            result = AStarSearch(0, priority,direction);
            if(result == true ){
                createShortestPath(shortestPath, countNodes,priority,direction);
            }


            openList.clear();
            currentShortestPath.clear();
            shortestPathFinal.clear();

            shortestPath.clear();
            count--;
            openList.clear();

            while (!closeList.isEmpty()) {
                closeList.pop();
            }

            // swap initial and goal back
            temp = target;
            target = start;
            start = temp;

            return result;
        } else { // error input
            return false;
        }
    }
   /* *
     * Perform the AStar Search and alter the Grid to reflect the optimal path found
     */
   /* private Boolean AStarSearch(int step, int priority) {
        expansions = step;
        this.gridworld[current.rw][current.cl].hval = current.hval;
        this.gridworld[current.rw][current.cl].gval = current.gval;
        this.gridworld[current.rw][current.cl].fval = current.fval;
        closeList.push(current);

        // base case
        if (target.equals(current)) {
            foundPath = true;
            return foundPath;
        }

        // add neighbors to opened list
       // GridNode temp;

         leftNode(current);
         upNode(current);
         rightNode(current);
         downNode(current);

        if (openList.size() < 1) {
            return foundPath;
        }

        // tie breaking and sorting
        if (priority == 1) {
            Collections.sort(openList, GridNode.BigG); // priority for smaller g values
        } else {
            Collections.sort(openList, GridNode.SmallG); // priority for larger g values
        }

        current = openList.get(0);
        openList.remove(0);

         AStarSearch(step + 1, priority);

        // all done with the planning phase
        // trail up from the currNode should be the optimal path
            optimalPath(current);


        return foundPath; // end
    }*/
    private Boolean AStarSearch(int step, int priority,int direction) {

       //calculateGvalue(current);

        while (this.current != target || openList.size() < 1) {
            if(currentList != null){

                   // currentList.clear();
                currentList.add(current);}
        expansions = step;
        this.gridworld[current.rw][current.cl].hval = current.hval;
        this.gridworld[current.rw][current.cl].gval = current.gval;
       this.gridworld[current.rw][current.cl].fval = current.fval;
           // this.gridworld[current.rw][current.cl].fval = current.gval + current.hval;
            if(!closeList.contains(current)){
        closeList.push(current);}

            if(target.equals(current)){
                break;
            }


        // add neighbors to opened list
        // GridNode temp;

        leftNode(current);
        upNode(current);
        rightNode(current);
        downNode(current);
            if (openList.size() < 1){

                break;
            }

        // tie breaking and sorting
        if (priority == 1) {
            Collections.sort(openList, GridNode.BigG); // priority for smaller g values
        } else {
            Collections.sort(openList, GridNode.SmallG); // priority for larger g values
        }
        if(current!=null){

            current = openList.get(0);
            openList.remove(0);
            /*GridNode temp,prevItem;
            prevItem=current;
            int size = openList.size();
            int i=0;
            while( i < size){
        temp = openList.get(i);

       if( temp.cl == current.cl+1 || temp.cl == current.cl+ 1 ||  temp.rw == current.rw + 1 || temp.rw == current.rw - 1 ) {
           current= temp;
           openList.remove(0);
           break;
       }
            i++;
            }
            //current not updated
            if(prevItem == current){
                //current=current.getParent();
                break;

            }*/
          //  if(currentList != null)
           // currentList.add(current);
        }
        //openList.clear();

        step = step + 1;
    }

        // all done with the planning phase
        // trail up from the currNode should be the optimal path


        if (target.equals(current)) {
            while (current != null) {
                // this.gridworld[current.rw][current.cl].path = true;
                currentShortestPath.add(current);
                current = current.getParent();
            }
            currentList.add(current);
            optimalPath(currentShortestPath ,priority, direction);
           if(openList.size()<1){foundPath= false;}else
           { foundPath= true;}
            return foundPath;
        }
        if (openList.size() < 1) {
            return foundPath;
        }


        return foundPath; // end
    }


    public Boolean AdaptiveStarSearch(int start_rw, int start_cl) {
        Boolean ret = false;

        expansions = 0;

        // always forward
        current = gridworld[start_rw][start_cl];
        current.isNodeBlocked=false;
        if (!isAdaptive) {
            calculateHvalue();
            isAdaptive = true;
        } else {
            second = true;
        }
        ret = AdaptiveSearch(0);
     /*   if(ret == true ){
                    createShortestPath(shortestPath, countNodes);
        }*/
        openList.clear();
        currentShortestPath.clear();
        shortestPathFinal.clear();
        shortestPath.clear();
        // Adaptive Search:
        if (ret == true) {
            GridNode temp;
            while (!closeList.isEmpty()) {
                temp = closeList.pop();
                int tempH = this.gridworld[temp.rw][temp.cl].hval;
                this.gridworld[temp.rw][temp.cl].hval = this.target.getG() - temp.getG();
                if ((tempH != this.gridworld[temp.rw][temp.cl].hval)) {
                    /*
                     * System.out.print("Node: " + temp + " changed h from " + tempH);
                     * System.out.print(" to " + this.grid[temp.row][temp.column].h + "\n");
                     */
                }
            }
        }
        return ret;
    }
    /**
     * Perform the Adaptive Search and alter the Grid to reflect the optimal
     * path found
     */
    private Boolean AdaptiveSearch(int step) {

        while (this.current != target || openList.size() < 1) {
            expansions = step;
            if(currentList != null){

                // currentList.clear();
                currentList.add(current);}
        this.gridworld[current.rw][current.cl].hval = current.hval;
        this.gridworld[current.rw][current.cl].gval = current.gval;
        this.gridworld[current.rw][current.cl].fval = current.fval;
        closeList.push(current);

        // base case
        if (target.equals(current)) {
            break;
        }

        // add neighbors to opened list
        // add neighbors to opened list
        // GridNode temp;

        leftNode(current);
        upNode(current);
        rightNode(current);
        downNode(current);

        if (openList.size() < 1) {
            break;
        }

        Collections.sort(openList, GridNode.BigG); // priority for larger g values

            if(current!=null){
                current = openList.get(0);
                openList.remove(0);
                //  if(currentList != null)
              //  currentList.add(current);
            }
      /*  current = openList.get(0);
        openList.remove(0);
            if(currentList != null)
                currentList.add(current);*/

        //AdaptiveSearch(step + 1);
           // openList.clear();
            step = step+1;

    }
        // all done with the planning phase
        // trail up from the currNode should be the optimal path

       /* if (current != null) {
            this.gridworld[current.rw][current.cl].path = true;

            shortestPath.push(current);
            current = current.getParent();
        }*/

        // base case
        if (target.equals(current)) {
            while (current != null) {
                // this.gridworld[current.rw][current.cl].path = true;
                currentShortestPath.add(current);
                current = current.getParent();
            }
            currentList.add(current);
            optimalPath(currentShortestPath , 1,0);
            if(openList.size()<1){foundPath= false;}else
            { foundPath= true;}
            return foundPath;
        }
       /* if (target.equals(current)) {
            foundPath = true;
            optimalPath(currentList, 1);
            return foundPath;
        }*/
        if (openList.size() < 1) {
            return foundPath;
        }
        return foundPath; // end


    }









  /*  private void optimalPath(GridNode current) {
        if (current != null) {
            this.gridworld[current.rw][current.cl].path = true;
            shortestPath.push(current);
            current = current.getParent();
        }
    }*/

        int size;
        int step = expansions;
        int i;
        if (currentShortestPath != null) {
           System.out.println("cvount");
            size= currentShortestPath.size();
            for( i=0 ;i< size ; i++){
                System.out.print("("+currentShortestPath.get(i).rw+","+currentShortestPath.get(i).cl+")");}
            System.out.println();
            size= currentShortestPath.size();

            if(size>0){

            for( i=size -1 ;i>=0 ; i--){
                if( !this.gridworld[currentShortestPath.get(i).rw][currentShortestPath.get(i).cl].isNodeBlocked ){
                    step = step +1;
            //this.gridworld[currentShortestPath.get(i).rw][currentShortestPath.get(i).cl].path = true;
            countNodes[currentShortestPath.get(i).rw][currentShortestPath.get(i).cl]++;
            shortestPath.push(currentShortestPath.get(i));
            /*GridNode n = currentList.get(i) ;
            n = n.getParent();*/}
                if(target== currentShortestPath.get(i)){
                    this.current=target;
                }
                if( this.gridworld[currentShortestPath.get(i).rw][currentShortestPath.get(i).cl].isNodeBlocked ){
                    break;
                }

            }
                if(i >=0){
            if (target!= currentShortestPath.get(i)){

                        GridNode temp = currentShortestPath.get(i);
                        if(!blockedNodes.contains(temp)){
                        blockedNodes.add(temp);}
                        int s =  shortestPath.size();
                        shortestPath.remove(s-1);
                        if(!currentShortestPath.isEmpty()){
                countNodes[currentShortestPath.get(i+1).rw][currentShortestPath.get(i+1).cl]--;}
                       // int sizeOfShortestPath = shortestPath.size();
                    closeList.clear();
                       // for(int j=0 ;j<sizeOfShortestPath; j++){
                    //closeList.push(shortestPath.get(j));}
                        openList.clear();
                       // for(int j=0 ;j<sizeOfShortestPath ; j++){
                           //calNeighbours(shortestPath.get(j));}
                        //shortestPath.remove(s-1);
                        this.current= this.gridworld[currentShortestPath.get(i+1).rw][currentShortestPath.get(i+1).cl];
                        this.current.setParent(null);
                        currentList.clear();

                currentShortestPath.clear();

                   }}





        }
            if(target != current){
                AStarSearch(step,priority,direction);
                AdaptiveSearch(0);
            }

            if(target == current ){
                while(!shortestPath.isEmpty()){
                createShortestPath(shortestPath, countNodes,priority, direction);}
            }
            }

        }

    private void createShortestPath(Stack<GridNode> shortestPath1, int[][] countNodes1,int priority,int direction) {
        this.shortestPath = shortestPath1;
        this.countNodes= countNodes1;
        boolean flag = false;
        GridNode temp = new GridNode();
        int cost=0;
        while(!shortestPath.isEmpty()) {
            GridNode node = shortestPath.pop();

            if (flag == true) {
                if(!shortestPath.isEmpty()){
                //shortestPath.pop();
                countNodes[node.rw][node.cl]--;
                if (node.equals(temp)) {
                    flag = false;
                }}
            } else if (countNodes[node.rw][node.cl] > 1) {
                    flag = true;
                    temp=node;
                    shortestPathFinal.push(node);
                    countNodes[node.rw][node.cl]--;

            } else{

                shortestPathFinal.push(node);
                countNodes[node.rw][node.cl]--;
            }
        }
if(direction == 0) {
    while (!shortestPathFinal.isEmpty()) {
        //cost++;
        GridNode n = shortestPathFinal.pop();
        this.gridworld[n.rw][n.cl].path = true;
    }
}else {
    while(!shortestPathFinal.isEmpty()){
        //cost++;
        GridNode n = shortestPathFinal.peek();
        shortestPathFinal.remove(n);
        this.gridworld[n.rw][n.cl].path = true;
    }
}
    }


    private void calNeighbours(GridNode gridNode) {

        //openList.clear();
        current=gridNode;
        leftNode(current);
        upNode(current);
        rightNode(current);
        downNode(current);
    }


    private void leftNode(GridNode current) {
        GridNode temp;
        if (this.current.cl > 0 /*&& !this.gridworld[this.current.rw][this.current.cl - 1].isNodeBlocked ? true :false*/) {
            temp = this.gridworld[this.current.rw][this.current.cl - 1];
            if (!closeList.contains(temp) && !blockedNodes.contains(temp)) { // don't update g value if in closed list
               temp.updateG(this.current.getG() + 1); // update node's g and f values
                addToOpenedList(temp);
            }
        }
    }

    private void upNode(GridNode current) {
        GridNode temp;
        if (this.current.rw > 0 /*&& !this.gridworld[this.current.rw - 1][this.current.cl].isNodeBlocked ? true :false*/){
            temp = this.gridworld[this.current.rw - 1][this.current.cl]; // get the up node
            if (!closeList.contains(temp)&& !blockedNodes.contains(temp)) { // don't update g value if in closed list
                temp.updateG(this.current.getG() + 1); // update node's g and f values
                addToOpenedList(temp);
            }
        }
    }

    private void rightNode(GridNode current) {
        GridNode temp;
        if (this.current.cl < cl_max /*&& !this.gridworld[this.current.rw][this.current.cl + 1].isNodeBlocked? true :false*/){
            temp = this.gridworld[this.current.rw][this.current.cl + 1];; // get the right node
            if (!closeList.contains(temp)&& !blockedNodes.contains(temp)) { // don't update g value if in closed list
               temp.updateG(this.current.getG() + 1); // update node's g and f values
                addToOpenedList(temp);
            }
        }
    }

    private void downNode(GridNode current) {
        GridNode temp;
        if (this.current.rw < rw_max/* && !this.gridworld[this.current.rw + 1][this.current.cl].isNodeBlocked? true :false*/) {
            temp =  this.gridworld[this.current.rw + 1][this.current.cl]; // get the down node
            if (!closeList.contains(temp)&& !blockedNodes.contains(temp)) { // don't update g value if in closed list
                temp.updateG(this.current.getG() + 1); // update node's g and f values
                addToOpenedList(temp);
            }
        }
    }


    /**
     * Add a node to Opened List only if new node has smaller f value
     *
     * @param n
     */
    public void addToOpenedList(GridNode n) {
        if (openList.contains(n)) {
            if (openList.get(openList.indexOf(n)).getF() > n.getF()) { // add smaller version of node
                openList.remove(openList.indexOf(n));
                n.setParent(current);
                this.gridworld[n.rw][n.cl].setParent(this.gridworld[current.rw][current.cl]);
                openList.add(n);
            }
        } else {
            n.setParent(current);
            this.gridworld[n.rw][n.cl].setParent(this.gridworld[current.rw][current.cl]);
            openList.add(n);
        }

    }

    /**
     * Print the cost of the optimal path found
     */
    public int getOptimalPathCost() {
int cost=0;
        for(int i=0;i<=rw_max;i++){

            for(int j=0; j<= cl_max;j++) {

                if(this.gridworld[i][j].path==true){
                    cost++;
                }
            }
        }
        /*int count = 0;
        GridNode curr = new GridNode();
        while (!this.optimalPathCostList.isEmpty()) {
            count++;
           curr = this.optimalPathCostList.pop();
            System.out.print("("+curr.rw +","+curr.cl+")"+ ",");
        }*/
        return cost;
    }
    /**
     * Print the cost of the optimal path found
     */
    public void getPathTraversal() {

        GridNode curr = new GridNode();
        while (!shortestPath.isEmpty()) {

            curr = shortestPath.pop();
            if(curr!=null){
            System.out.print("("+curr.rw +curr.cl+")"+ ",");}
        }

    }

    public int getExpansions() {
        return expansions;
    }

    /**
     * reset path variable of every node in grid
     */
    public void resetPath() {
        for (int i = 0; i <= rw_max; i++) {
            for (int j = 0; j <= cl_max; j++) {
                this.gridworld[i][j].path = false;
                this.gridworld[i][j].parentNode = null;
            }
        }
        expansions = 0;
    }
}
