import java.util.Comparator;

public class GridNode {

    public int rw, cl;
    public int fval, gval, hval;
    public boolean isNodeBlocked;
    public boolean isNodeVisited;
    public boolean path;
    public GridNode parentNode;

    public GridNode(){

    }

    public GridNode(int rw, int cl, int hval, boolean isNodeBlocked,int rw_max, int cl_max) {

        this.parentNode = null;
        this.rw = rw;
        this.cl = cl;
        this.hval = hval;
        this.gval = 0;
        this.fval = 0;
        this.isNodeBlocked = isNodeBlocked;
        this.isNodeVisited = false;
        this.path = false;
    }
    /**
     *
     * Updates the current g value and respectively updates the f value of the
     *
     * node
     *
     */
    public void updateG(int newG) {
        this.gval = newG;
        this.fval = this.gval + this.hval;
    }
    /**
     *
     * Return the g value of the current node
     *
     */
    public int getG() {
        return this.gval;
    }


    /**
     *
     * Set the new parent node of the current node
     *
     */
    public void setParent(GridNode parent) {
        this.parentNode = parent;
    }

    /**
     *
     * Get the parent node of the current node
     *
     */
    public GridNode getParent() {
        return this.parentNode;
    }

    /**
     *
     * Return the h value of the current node
     *
     */
    public int getH() {
        return this.hval;
    }

    /**
     *
     * Return the f value of the current node
     *
     */
    public int getF() {
        return getG() + getH();
    }

    static final Comparator<GridNode>  BigG = new Comparator<GridNode>() {
        @Override
        public int compare(GridNode o1, GridNode o2) {
            if (o1.fval == o2.fval) {
                return o2.gval - o1.gval; // this sorts by g value largest -> smallest
            }
            return o1.fval - o2.fval;
        }
        //
    };

    static final Comparator<GridNode> SmallG = new Comparator<GridNode>() {
        @Override
        public int compare(GridNode o1, GridNode o2) {
            if (o1.fval == o2.fval) {
                return o1.gval - o2.gval; // this sorts by g value smallest -> largest
            }
            return o1.fval - o2.fval;
        }
    };

}
