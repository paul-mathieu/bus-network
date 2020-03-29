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

    public String getHourAfter(String hour) {
        for (String h: this.getListHourOfPassage()){
//            System.out.println(h + " - " + hour);
            if (!h.contains("-")){
                if (!compareTwoHours(h, hour, "before")){
                    return h;
                }
            }
        }
        return "";
    }

    public boolean compareTwoHours(String hour_1, String hour_2, String choice){
        if (!(hour_1.length() == 5) && !(hour_2.length() == 5)) return false;

        if (hour_1.length() == 4) hour_1 = "0" + hour_1;
        if (hour_2.length() == 4) hour_2 = "0" + hour_2;

        int hour1Value = Integer.parseInt(hour_1.substring(0,2));
        int minute1Value = Integer.parseInt(hour_1.substring(3,5));
        int hour2Value = Integer.parseInt(hour_2.substring(0,2));
        int minute2Value = Integer.parseInt(hour_2.substring(3,5));

        switch (choice) {
            case "after":
                return hour1Value > hour2Value || (hour1Value == hour2Value && minute1Value > minute2Value);
            case "before":
                return hour1Value < hour2Value || (hour1Value == hour2Value && minute1Value < minute2Value);
            case "equal":
                return hour1Value == hour2Value && minute1Value == minute2Value;
            default:
                return false;
        }
    }

}
