import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

// ex: each senses

public class Line {

    private ArrayList<String> rawDataLine;

    public ArrayList<BusStop> dataLine = new ArrayList<>();
    public ArrayList<String> listBusStopName = new ArrayList<>();


    /*
    =======================================================================
     Initialisation
    =======================================================================
    */

    // initialisation
    public Line(ArrayList<String> rawDataLine){
        this.rawDataLine = rawDataLine;
        convertRawData();
        setListBusStopName();
        // Special BusStop
        setIsTerminus();
        setIsNotAlready();
        setIsSeparation();
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

    public void setIsTerminus(){
        for (BusStop bs: this.dataLine){
            if (bs.name.equals(bs.name.toUpperCase())){
                bs.isTerminus = true;
            }
        }
    }

    public void setIsNotAlready(){
        for (BusStop bs: this.dataLine){
            if (bs.listHourOfPassage.stream().anyMatch(s->s.equals("-"))) bs.isNotAlready = true;
        }
    }

    public void setIsSeparation(){
        for (BusStop bs: this.dataLine){
            if (bs.isNotAlready){
                for (BusStop bs_chack: this.dataLine) {
                    if (bs.isReverse(bs_chack)){
                        bs.isSeparation = true;
                        break;
                    }
                }
            }
        }
    }

//    public void setIsJunctionSeparation(){


    /*
    =======================================================================
     Methods
    =======================================================================
    */

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

    public BusStop getNextBusStop(BusStop busStop){
        AtomicReference<BusStop> previousBusStop = null;
        for (BusStop bs: this.dataLine) {
            if (busStop == previousBusStop.get()){
                return bs;
            } // prendre en compte les fourchettes
            previousBusStop.set(bs);
        }
        return null;
    }

    public BusStop getPreviousBusStop(BusStop busStop){
        AtomicReference<BusStop> previousBusStop = null;
        for (BusStop bs: this.dataLine) {
            if (busStop == previousBusStop.get()){
                return previousBusStop.get();
            } // prendre en compte les fourchettes
            previousBusStop.set(bs);
        }
        return null;
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

    /*
    =======================================================================
     Functions for graph
    =======================================================================
    */

    public NodeBusStop getArrayNearestBusStop(String busStopName, String busName, String lineName){
        // get the next bus stop in the list if exists
        BusStop nextBusStop;
        for (BusStop bs: this.dataLine){
            if (bs.name.equals(busStopName)){
                nextBusStop = getNextBusStop(bs);
                if (nextBusStop != null) {
                    return new NodeBusStop(busName, lineName, nextBusStop);
                }
            }
        }
        return null;
    }

    public void doBusStopUsed(String busStopName){
        for (BusStop bs: this.dataLine){
            if (bs.name.equals(busStopName)){
                bs.setIsUsed(true);
            }
        }
    }


    /*
    =======================================================================
     Print and debugs
    =======================================================================
    */

    public void print(){
        System.out.println("========================");
//        for (BusStop busStop: this.dataLine) busStop.print();
        System.out.println(this.listBusStopName);
        System.out.println("========================");
    }


}
