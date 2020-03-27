import java.util.ArrayList;

public class NodeBusStop {

    private String nameBus;
    private String nameLine;
    private BusStop busStop;

    private ArrayList<NodeBusStop> nextNodeBusStops = new ArrayList<>();

    public NodeBusStop(String nameBus, String nameLine, BusStop busStop){
        this.nameBus = nameBus;
        this.nameLine = nameLine;
        this.busStop = busStop;
    }

    public String getNameBus(){
        return this.nameBus;
    }

    public String getNameLine(){
        return this.nameLine;
    }

    public String getNameBusStop(){
        return this.busStop.name;
    }

    public ArrayList<String> getListHourOfPassage(){
        return this.busStop.listHourOfPassage;
    }

    public BusStop getBusStop(){
        return this.busStop;
    }

    public boolean isComplete(){
        return this.getBusStop() != null && this.getNameLine() != null && this.getNameBus() != null;
    }

    public void addNextNodeBusStops(NodeBusStop nodeBusStop){
        this.nextNodeBusStops.add(nodeBusStop);
    }

}
