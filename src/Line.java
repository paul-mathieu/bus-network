import java.util.ArrayList;

// ex: each senses

public class Line {

    private ArrayList<String> rawDataLine;

    public ArrayList<BusStop> dataLine = new ArrayList<>();
    public ArrayList<String> listBusStopName = new ArrayList<>();

    // initialisation
    public Line(ArrayList<String> rawDataLine){
        this.rawDataLine = rawDataLine;
        convertRawData();
        setListBusStopName();
    }

    // print
    public void print(){
        System.out.println("========================");
//        for (BusStop busStop: this.dataLine) busStop.print();
        System.out.println(this.listBusStopName);
        System.out.println("========================");
    }

    // convert raw data
    public void convertRawData(){
        for (String busStop: this.rawDataLine){
//            System.out.println(busStop);
            if (!busStop.equals("")) {
                this.dataLine.add(new BusStop(busStop));
            }
        }
    }

    public void setListBusStopName(){
        for (BusStop bs: this.dataLine){
            this.listBusStopName.add(bs.getName());
        }
    }

    // method
    public int indexBusStopName(String busStopName){
        // -1 if not in list else index
        if (!this.listBusStopName.contains(busStopName)) return -1;
        else return this.listBusStopName.indexOf(busStopName);
    }

    public boolean isAfter(String firstBusStopName, String secondBusStopName){
        // true if the second bus stop name is after
        return indexBusStopName(firstBusStopName) < indexBusStopName(secondBusStopName);
    }

    public boolean isBefore(String firstBusStopName, String secondBusStopName){
        // true if the second bus stop name is before
        return indexBusStopName(firstBusStopName) > indexBusStopName(secondBusStopName);
    }

    public String getFirstAndLast(){
        // get first bus stop and last bus stop
        return this.listBusStopName.get(0) + "_to_" + this.listBusStopName.get(this.listBusStopName.size() - 1);
    }

    public void makeAllBusStopToFalse(){
        for (BusStop bs: this.dataLine){
            bs.setIsUsed(false);
        }
    }


}
