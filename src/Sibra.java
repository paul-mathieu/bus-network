import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// all buses

public class Sibra {

    ArrayList<String> listPathFile = new ArrayList<>();

    ArrayList<Bus> listBus = new ArrayList<Bus>();

    /*
    =======================================================================
     Class initialization
    =======================================================================
    */

    // by default
    public Sibra(){
        this.listPathFile.add(System.getProperty("user.dir") + "/data/1_Poisy-ParcDesGlaisins.txt");
        this.listPathFile.add(System.getProperty("user.dir") + "/data/2_Piscine-Patinoire_Campus.txt");
    }

    // with non optionnal parameters
    public Sibra(ArrayList<String> listPathFile){
        this.listPathFile = listPathFile;
    }


    /*
    =======================================================================
     Load data
    =======================================================================
    */


    public void loadData(){
        // initialisation
        ArrayList<List<String>> data = new ArrayList<>();

        // data in list
        for (String pathBus: this.listPathFile) {
            Bus bus = new Bus(pathBus);
            listBus.add(bus);
//            bus.print();
        }

    }

    public ArrayList<String> listDataToInfo(List<String> listData){
        return null;
    }


    /*
    =======================================================================
     Setters
    =======================================================================
    */

    public void setUsedBus(String typeDay){
        for (Bus b: this.listBus){
            if (typeDay.equals("saturday or summer")){
                b.setIsUsedLineWeek(false);
                b.setIsUsedLineSaturday(true);
            } else if (typeDay.equals("week")){
                b.setIsUsedLineWeek(true);
                b.setIsUsedLineSaturday(false);
            }
        }
    }



    /*
    =======================================================================
     Use bus methods
    =======================================================================
    */

    public void displayBusDepartureAndArrival(String departure, String arrival){
        System.out.println("Possibility of departure bus line:");
        for (Bus b: listBusStop(departure)) {
            //
            System.out.println(b.name);
        }
        System.out.println("Possibility of arrival bus line:");
        for (Bus b: listBusStop(arrival)) {
            System.out.println(b.name);
        }

    }

    public ArrayList<Bus> listBusStop(String nameBusStop){
        ArrayList<Bus> listBusStop = new ArrayList<>();
        for (Bus b: this.listBus){
            if (b.hasBusStop(nameBusStop)){
                listBusStop.add(b);
//                b.print();
            }
        }

        return listBusStop;
    }

    public ArrayList<Bus> getListBus() {
        return listBus;
    }

    public void makeAllBusUsedToFalse() {
        for (Bus b : this.listBus) {
            b.makeAllLineToFalse();
        }
    }


//    public ArrayList<String> firstHourDepartance(String nameBusStop, String hourDepartance){
//        ArrayList<String> listHourDepartance = new ArrayList<>();
//        for (Bus b: this.listBus){
//            if (b.hasBusStop(nameBusStop)){
//                listBusStop.add(b);
//                b.print();
//            }
//        }
//
//        return listBusStop;
//    }

//    public boolean isBusStop1

    public ArrayList<String> busAndLineWithThisSuccession(String typeDay,
                                                          String busStopName1, ArrayList<String> listHourOfPassage1,
                                                          String busStopName2, ArrayList<String> listHourOfPassage2){
        ArrayList<String> busAndLine = new ArrayList<>();
        String nameLineWithBusStops;
        for (Bus b: this.listBus){
            nameLineWithBusStops = b.nameLineWithBusStops(busStopName1, listHourOfPassage1, busStopName2, listHourOfPassage2, typeDay);
//            System.out.println("(" + busStopName1 + " - " + b.name + ") listHourOfPassage1: " + listHourOfPassage1);
//            System.out.println("(" + busStopName2 + " - " + b.name + ") listHourOfPassage2: " + listHourOfPassage2);
//            System.out.println("nameLineWithBusStops: " + nameLineWithBusStops);
            if (nameLineWithBusStops != null) {
                busAndLine.add(b.name);
                busAndLine.add(nameLineWithBusStops);
//                System.out.println("#");
                break;
            }
        }
        return busAndLine;
    }

    public int getTimeBetweenTwoBusStop(ArrayList<String> infoLine, String nameBusStop1, String nameBusStop2){
        for (Bus b: this.listBus){
            // if same bus
            if (infoLine != null) {
                if (b.getName().equals(infoLine.get(0))) {
//                    System.out.println("=======");
                    return b.getTimeBetweenTwoBusStop(infoLine.get(1), nameBusStop1, nameBusStop2);
                }
            }
        }
        return -1;
    }


    /*
    =======================================================================
     Functions for graph
    =======================================================================
    */

    public ArrayList<NodeBusStop> getNearestBusStop(String nameBusStop, String nameBus, String typeDay){
        //list of buses only with the lines having the requested stop
        // (the list contains only the nearest bus stops)
        ArrayList<NodeBusStop> arrayNearestBusStop = new ArrayList<>();
        ArrayList<NodeBusStop> sameNodeBusStopOtherLine;
        for (Bus b: this.listBus){
            arrayNearestBusStop.addAll(b.getArrayNearestBusStop(nameBusStop, typeDay));
            // si c'est un autre bus, ajout du même arret si existe
            if (b.getName() != nameBus){
                sameNodeBusStopOtherLine = b.getNodeBusStop(nameBusStop, typeDay);
                if (sameNodeBusStopOtherLine != null){
                    arrayNearestBusStop.addAll(sameNodeBusStopOtherLine);
                }
            }
        }
        arrayNearestBusStop.removeIf(Objects::isNull);
        return arrayNearestBusStop;
    }

    public void doBusStopUsed(String nameBusStop, String typeDay){
        for (Bus b: this.listBus){
            b.doBusStopUsed(nameBusStop, typeDay);
        }
    }

    public ArrayList<NodeBusStop> busStopToNodeBusStop(String busStopName, String typeDay){
        ArrayList<NodeBusStop> nodeBusStopArrayList = new ArrayList<>();
        for (Bus b: this.listBus){
            nodeBusStopArrayList.addAll(b.busStopToNodeBusStop(busStopName, typeDay));
        }
        return nodeBusStopArrayList;
    }

    public ArrayList<NodeBusStop> getAllNodeBusStops(String typeDay){
        ArrayList<NodeBusStop> allNodeBusStops = new ArrayList<>();
        for (Bus b: this.listBus){
            allNodeBusStops.addAll(b.getAllNodeBusStops(typeDay, b.name));
        }
        return allNodeBusStops;
    }

    public boolean isAfterWithInfo(String nameBusStop1, String nameBusStop2, String busName, String lineName){
        for (Bus b: this.listBus){
            if (b.getName() == busName){
                return b.isAfterWithInfo(nameBusStop1, nameBusStop2, lineName);
            }
        }
        return false;
    }


    /*
    =======================================================================
     Print, tests and debug
    =======================================================================
    */
    public void test(){

        ReadFile data_l1 = new ReadFile(this.listPathFile.get(0));
        data_l1.print();

        ReadFile data_l2 = new ReadFile(this.listPathFile.get(1));
        data_l2.print();
        System.out.println(data_l2.readRow());
    }

    public void print(){
        for (Bus bus: listBus){
            bus.print();
        }
    }


}
