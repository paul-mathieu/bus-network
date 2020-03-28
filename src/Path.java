public class Path {

    NodeBusStop actualBusStop;
    NodeBusStop previousBusStop;
    int timeSinceDeparture;

    public Path(NodeBusStop actualBusStop, NodeBusStop previousBusStop, int timeSinceDeparture){
        this.actualBusStop = actualBusStop;
        this.previousBusStop = previousBusStop;
        this.timeSinceDeparture = timeSinceDeparture;
    }

}
