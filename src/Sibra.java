import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    // method
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
