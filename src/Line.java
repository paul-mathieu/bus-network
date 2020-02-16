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
}
