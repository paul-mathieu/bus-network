import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Request {

    protected String day;
    protected String departure;
    protected String arrival;
    protected String hour;

    private final String stringURL;

    public String typeDay;

    public Sibra sibra = new Sibra();
    public ArrayList<String> listPublicHoliday = new ArrayList<>();

    public String stringDay;

    public Request(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        this.day = day;
        this.departure = departure;
        this.arrival = arrival;
        this.hour = hour;

        this.stringURL = "https://jours-feries-france.antoine-augusti.fr/api/" + this.day.substring(0,4);

        initialize();
    }

    private void initialize() throws IOException, ParseException {
        // create Sibra
        this.sibra.loadData();

        // list public holiday
        PublicHoliday publicHoliday = new PublicHoliday(this.stringURL);
        this.listPublicHoliday = publicHoliday.listPublicHoliday;

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        this.stringDay = sdf.format(date);

        setTypeDay();
        this.sibra.setUsedBus(this.typeDay);
    }

    private void setTypeDay() {
        // if sunday or public holiday > no data available ("no data available")
        // if saturday or summer > data saturday ("saturday or summer")
        // else > data week ("week")
        Boolean isInSummer = (this.day.substring(5, 7).equals("07")) || (this.day.substring(5, 7).equals("08"));
        Boolean isPublicHoliday = isPublicHoliday();
        Boolean isSunday = (this.stringDay.equals("dimanche")) || (this.stringDay.equals("sunday"));
        Boolean isSaturday = (this.stringDay.equals("samedi")) || (this.stringDay.equals("saturday"));

        if (isSunday || isPublicHoliday){
            this.typeDay = "no data available";
        } else if (isInSummer || isSaturday){
            this.typeDay = "saturday or summer";
        } else {
            this.typeDay = "week";
        }

    }

    private Boolean isPublicHoliday() {
        AtomicReference<Boolean> verify = new AtomicReference<>(false);
        for (String date: this.listPublicHoliday){
            if (date.equals(this.day)) {
                verify.set(true);
                break;
            }
        }
        return verify.get();
    }

    public boolean compareTwoHours(String hour_1, String hour_2, String choice){
        if (!(hour_1.length() == 5) && !(hour_2.length() == 5)) return false;

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

    public int getNextBusAtThisHour(NodeBusStop departureBusStopNode, String hour) {
        // get the next bus at this hour (the number with the list of bus)
        AtomicInteger busNumber = new AtomicInteger(1);
        for (String h : departureBusStopNode.getListHourOfPassage()) {
            // index of the exact time or the nearest hour for the current stop
            if (!compareTwoHours(hour, h, "before")) {
                // if after or equal
                return busNumber.get();
            }
            busNumber.getAndIncrement();
        }
        return -1;
    }

    public ArrayList<NodeBusStop> busStopToNodeBusStops(String busStopName){
        return sibra.busStopToNodeBusStop(busStopName, this.typeDay);
    }

    public int getTimeBetweenTwoBusStop(String busStop1, String busStop2){
        // return the time in minute between two buses in the same line
        ArrayList<NodeBusStop> nodeBusStops1 = busStopToNodeBusStops(busStop1);
        ArrayList<NodeBusStop> nodeBusStops2 = busStopToNodeBusStops(busStop2);

        ArrayList<String> infoLine = getInfoLine(busStop1, busStop2);
//        System.out.println("infoLine: " + infoLine);

        return sibra.getTimeBetweenTwoBusStop(infoLine, busStop1, busStop2);

    }

    public ArrayList<String> getInfoLine(String busStop1, String busStop2){
        // return the time in minute between two buses in the same line
        ArrayList<NodeBusStop> nodeBusStops1 = busStopToNodeBusStops(busStop1);
        ArrayList<NodeBusStop> nodeBusStops2 = busStopToNodeBusStops(busStop2);
//        System.out.println("nodeBusStops1: " + nodeBusStops1);

        ArrayList<String> infoLine;

        for (NodeBusStop nbs1: nodeBusStops1){
            for (NodeBusStop nbs2: nodeBusStops2){
                boolean check = nbs1.getNameBus().equals(nbs2.getNameBus()) &&
                        nbs1.getNameLine().equals(nbs2.getNameLine());
                infoLine = sibra.busAndLineWithThisSuccession(this.typeDay,
                        nbs1.getNameBusStop(), nbs1.getListHourOfPassage(),
                        nbs2.getNameBusStop(), nbs2.getListHourOfPassage());
//                System.out.println("infoLine: " + infoLine);
//                System.out.println("check: " + check);

                if (check && infoLine != null){
                    return infoLine;
                }
            }
        }
        return null;
    }

}
