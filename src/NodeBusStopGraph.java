import java.util.ArrayList;

public class NodeBusStopGraph extends NodeBusStop {

    /*
    =======================================================================
     Initialization
    =======================================================================
    */

    private int timeFromDeparture = -1;
    private ArrayList<NodeBusStopGraph> nodeBusStopChildren = new ArrayList<>();

    public NodeBusStopGraph(String nameBus, String nameLine, BusStop busStop) {
        super(nameBus, nameLine, busStop);
    }


    /*
    =======================================================================
     Setters
    =======================================================================
    */

    public void setTimeFromDeparture(int timeFromDeparture) {
        this.timeFromDeparture = timeFromDeparture;
    }


    /*
    =======================================================================
     Getters
    =======================================================================
    */

    public int getTimeFromDeparture() {
        return this.timeFromDeparture;
    }

    public ArrayList<NodeBusStopGraph> getNodeBusStopChildren() {
        return nodeBusStopChildren;
    }

    public ArrayList<NodeBusStopGraph> getLeaves(){
        ArrayList<NodeBusStopGraph> leaves = new ArrayList<>();
        for (NodeBusStopGraph child: this.nodeBusStopChildren){
            if (child.nodeBusStopChildren.size() == 0){
                leaves.add(child);
            } else {
                leaves.addAll(child.getLeaves());
            }
        }
        return leaves;
    }

    public int getShortestTime(){
        int shortestTime = -1;
        for (NodeBusStopGraph leaf: getLeaves()){
            if (shortestTime > leaf.getTimeFromDeparture()){
                shortestTime = leaf.getTimeFromDeparture();
            }
        }
        return shortestTime;
    }

    /*
    =======================================================================
     Methods
    =======================================================================
    */

    public void addNodeBusStopChild(NodeBusStopGraph nodeBusStopChild){
        this.nodeBusStopChildren.add(nodeBusStopChild);
    }

}
