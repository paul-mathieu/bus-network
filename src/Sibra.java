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


    /*
    =======================================================================
     Functions for graph
    =======================================================================
    */

    public ArrayList<NodeBusStop> getNearestBusStop(String nameBusStop, String typeDay){
        //list of buses only with the lines having the requested stop
        // (the list contains only the nearest bus stops)
        ArrayList<NodeBusStop> arrayNearestBusStop = new ArrayList<>();
        for (Bus b: this.listBus){
            arrayNearestBusStop.addAll(b.getArrayNearestBusStop(nameBusStop, typeDay));
        }
        arrayNearestBusStop.removeIf(Objects::isNull);
        return arrayNearestBusStop;
    }

    public void doBusStopUsed(String nameBusStop, String typeDay){
        for (Bus b: this.listBus){
            b.doBusStopUsed(nameBusStop, typeDay);
        }
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
