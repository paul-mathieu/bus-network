public class Path {

    NodeBusStop actualBusStop;
    NodeBusStop previousBusStop;
    int timeSinceDeparture;

    public Path(NodeBusStop actualBusStop, NodeBusStop previousBusStop, int timeSinceDeparture){
        this.actualBusStop = actualBusStop;
        this.previousBusStop = previousBusStop;
        this.timeSinceDeparture = timeSinceDeparture;
    }

    public String getNameLine(){
        return this.actualBusStop.getNameLine();
    }

    public String getNameBus(){
        return this.actualBusStop.getNameBus();
    }

}
