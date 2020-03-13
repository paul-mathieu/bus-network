import java.util.ArrayList;

public class NodeBusStopGraph extends NodeBusStop {

    private int timeToNextStep = -1;
    private ArrayList<NodeBusStopGraph> nodeBusStopChildren = new ArrayList<>();

    public NodeBusStopGraph(String nameBus, String nameLine, BusStop busStop) {
        super(nameBus, nameLine, busStop);
    }

    public int getTimeToNextStep() {
        return this.timeToNextStep;
    }

    public ArrayList<NodeBusStopGraph> getNodeBusStopChildren() {
        return nodeBusStopChildren;
    }

    public void setTimeToNextStep(int timeToNextStep) {
        this.timeToNextStep = timeToNextStep;
    }

    public void addNodeBusStopChild(NodeBusStopGraph nodeBusStopChild){
        this.nodeBusStopChildren.add(nodeBusStopChild);
    }

}
