import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

// ex: line 1 or line 2

public class Bus {
    private ArrayList<String> rawData;

    public Line lineWeekDirection1;
    public Line lineWeekDirection2;
    public Line lineSaturdayDirection1;
    public Line lineSaturdayDirection2;

    private boolean isUsedLineWeek;
    private boolean isUsedLineSaturday;

    public String name;

    /*
    =======================================================================
     Class initialization
    =======================================================================
    */

    public Bus(String pathFile){
        ReadFile readFile = new ReadFile(pathFile);
        this.rawData = readFile.readRow();
        this.name = pathFile.substring(pathFile.indexOf("data/") + 5, pathFile.indexOf(".txt"));
        setCleanData();
    }

    //extract data from file
    public void setCleanData() {

        /*
        convert raw data to clean data

        just go after the two \n after each other
        step 1: as a non-empty list element, store the data (week_stop)
        step 2: after non-empty list element, as long as non-empty list element, save the data (week_return)
        step 3: after two empty list elements, as a non-empty list element, sotcker the data (saturday_return)
        step 4: after non-empty list element, as long as non-empty list element, save the data (Saturday_return)

        aller juste apres les deux \n qui se suivent
        etape 1 : tant que element de liste non vide, stocker les donnees (semaine_aller)
        etape 2 : apres element de liste non vide, tant que element de liste non vide, sotcker les donnees (semaine_retour)
        etape 3 : apres deux elements de liste vide, tant que element de liste non vide, sotcker les donnees (samedi_retour)
        etape 4 : apres element de liste non vide, tant que element de liste non vide, sotcker les donnees (samedi_retour)
        */

        ArrayList<String> raw_weekDirection1 = new ArrayList<>();
        ArrayList<String> raw_weekDirection2 = new ArrayList<>();
        ArrayList<String> raw_saturdayDirection1 = new ArrayList<>();
        ArrayList<String> raw_saturdayDirection2 = new ArrayList<>();

        ArrayList<ArrayList<String>> outputList = new ArrayList<>();

        AtomicInteger countEmpty = new AtomicInteger();
        AtomicInteger counterValidity = new AtomicInteger();
        AtomicInteger step = new AtomicInteger();

        for (String row : this.rawData) {
            // for each step
            if (row.equals("")) {
                countEmpty.getAndIncrement();
                counterValidity.set(0);
            }

            if (countEmpty.get() == 1) step.set(1);
            else if (countEmpty.get() == 2) step.set(2);
            else if (countEmpty.get() == 3) step.set(0); // do nothing
            else if (countEmpty.get() == 4) step.set(3);
            else if (countEmpty.get() == 5) step.set(4);

            if (step.get() == 1 && counterValidity.get() >= 1) raw_weekDirection1.add(row);          // if step 1 (countEmpty = 1)
            else if (step.get() == 2 && counterValidity.get() >= 1) raw_weekDirection2.add(row);     // if step 3
            else if (step.get() == 3 && counterValidity.get() >= 3) raw_saturdayDirection1.add(row); // if step 4
            else if (step.get() == 4 && counterValidity.get() >= 1) raw_saturdayDirection2.add(row); // if step 5

            counterValidity.getAndIncrement();

        }

        // for (String s: raw_weekDirection1) System.out.println(s);

        this.lineWeekDirection1 = new Line(raw_weekDirection1);
        this.lineWeekDirection2 = new Line(raw_weekDirection2);
        this.lineSaturdayDirection1 = new Line(raw_saturdayDirection1);
        this.lineSaturdayDirection2 = new Line(raw_saturdayDirection2);

    }


    /*
    =======================================================================
     Setters
    =======================================================================
    */

    public void setIsUsedLineWeek(boolean b){
        this.isUsedLineWeek = b;
    }

    public void setIsUsedLineSaturday(boolean b){
        this.isUsedLineSaturday = b;
    }


    /*
    =======================================================================
     Getters
    =======================================================================
    */

    public String getName(){
        return this.name;
    }

    public ArrayList<NodeBusStop> getAllNodeBusStops(@NotNull String typeDay, String busName){
        ArrayList<NodeBusStop> allNodeBusStops = new ArrayList<>();
        switch (typeDay) {
            case "no data available":
                return allNodeBusStops;
            case "saturday or summer":
                allNodeBusStops.addAll(this.lineSaturdayDirection1.getAllNodeBusStops(busName, "lineSaturdayDirection1"));
                allNodeBusStops.addAll(this.lineSaturdayDirection2.getAllNodeBusStops(busName, "lineSaturdayDirection2"));
                return allNodeBusStops;
            case "week":
                allNodeBusStops.addAll(this.lineWeekDirection1.getAllNodeBusStops(busName, "lineWeekDirection1"));
                allNodeBusStops.addAll(this.lineWeekDirection2.getAllNodeBusStops(busName, "lineWeekDirection1"));
                return allNodeBusStops;
            default:
                throw new IllegalStateException("Unexpected value: " + typeDay);
        }
    }


    /*
    =======================================================================
     Methods
    =======================================================================
    */

    public boolean hasBusStop(String nameBusStop) {
        // variables
        AtomicBoolean verify = new AtomicBoolean(false);
        Line[] listLine = {lineWeekDirection1, lineWeekDirection2, lineSaturdayDirection1, lineSaturdayDirection2};

        // for each line possibility
        for (Line line: listLine){

            // for each name of bus stop
            for (String name: line.listBusStopName){

                // if same bus stop than request
                if (nameBusStop.equals(name)) {
                    verify.set(true);
                    break;
                }
            }
        }

        return verify.get();
    }

    public ArrayList<Line> listLineOfTheDay(@NotNull String typeDay){
        ArrayList<Line> output_listLineOfTheDay = new ArrayList<>();
        switch (typeDay) {
            case "no data available":
                return output_listLineOfTheDay;
            case "saturday or summer":
                output_listLineOfTheDay.add(this.lineSaturdayDirection1);
                output_listLineOfTheDay.add(this.lineSaturdayDirection2);
                return output_listLineOfTheDay;
            case "week":
                output_listLineOfTheDay.add(this.lineWeekDirection1);
                output_listLineOfTheDay.add(this.lineWeekDirection2);
                return output_listLineOfTheDay;
            default:
                throw new IllegalStateException("Unexpected value: " + typeDay);
        }
    }

    public Line lineInThisDirection(String firstBusStopName, String secondBusStopName, String typeDay){
        for (Line l: listLineOfTheDay(typeDay)){
            if (l.isAfter(firstBusStopName, secondBusStopName)){
                return l;
            }
        }
        return null;
    }

    public void makeAllLineToFalse(){
        this.lineWeekDirection1.makeAllBusStopToFalse();
        this.lineWeekDirection2.makeAllBusStopToFalse();
        this.lineSaturdayDirection1.makeAllBusStopToFalse();
        this.lineSaturdayDirection2.makeAllBusStopToFalse();
    }

    public int getTimeBetweenTwoBusStop(@NotNull String nameLine, String nameBusStop1, String nameBusStop2){
        switch (nameLine) {
            case "lineSaturdayDirection1":
                return lineSaturdayDirection1.getTimeBetweenTwoBusStop(nameBusStop1, nameBusStop2);
            case "lineSaturdayDirection2":
                return lineSaturdayDirection2.getTimeBetweenTwoBusStop(nameBusStop1, nameBusStop2);
            case "lineWeekDirection1":
                return lineWeekDirection1.getTimeBetweenTwoBusStop(nameBusStop1, nameBusStop2);
            case "lineWeekDirection2":
                return lineWeekDirection2.getTimeBetweenTwoBusStop(nameBusStop1, nameBusStop2);
            default:
                return -1;
        }
    }

    /*
    =======================================================================
     Functions for graph
    =======================================================================
    */

    public ArrayList<NodeBusStop> getArrayNearestBusStop(String busStopName, @NotNull String typeDay){
        ArrayList<NodeBusStop> arrayNearestBusStop = new ArrayList<>();
        switch (typeDay) {
            case "no data available":
                return null;
            case "saturday or summer":
                arrayNearestBusStop.addAll(this.lineSaturdayDirection1.getArrayNearestBusStop(busStopName, this.name, "lineSaturdayDirection1"));
                arrayNearestBusStop.addAll(this.lineSaturdayDirection2.getArrayNearestBusStop(busStopName, this.name, "lineSaturdayDirection2"));
            case "week":
                arrayNearestBusStop.addAll(this.lineWeekDirection1.getArrayNearestBusStop(busStopName, this.name, "lineWeekDirection1"));
                arrayNearestBusStop.addAll(this.lineWeekDirection2.getArrayNearestBusStop(busStopName, this.name, "lineWeekDirection2"));
        }
        return arrayNearestBusStop;
    }


    public void doBusStopUsed (String busStopName, @NotNull String typeDay){
        switch (typeDay) {
            case "saturday or summer":
                this.lineSaturdayDirection1.doBusStopUsed(busStopName);
                this.lineSaturdayDirection2.doBusStopUsed(busStopName);
            case "week":
                this.lineWeekDirection1.doBusStopUsed(busStopName);
                this.lineWeekDirection2.doBusStopUsed(busStopName);
        }
    }

    public ArrayList<NodeBusStop> busStopToNodeBusStop(String busStopName, @NotNull String typeDay) {
        ArrayList<NodeBusStop> nodeBusStopArrayList = new ArrayList<>();
        ArrayList<NodeBusStop> nodeBusStopArrayListClean = new ArrayList<>();
        switch (typeDay) {
            case "saturday or summer":
//                System.out.println("=======");
                nodeBusStopArrayList.add(this.lineSaturdayDirection1.getNodeBusStop(busStopName, this.name, "lineSaturdayDirection1"));
                nodeBusStopArrayList.add(this.lineSaturdayDirection2.getNodeBusStop(busStopName, this.name, "lineSaturdayDirection2"));
            case "week":
//                System.out.println("+++++++");
//                System.out.println(busStopName);
                nodeBusStopArrayList.add(this.lineWeekDirection1.getNodeBusStop(busStopName, this.name, "lineWeekDirection1"));
                nodeBusStopArrayList.add(this.lineWeekDirection2.getNodeBusStop(busStopName, this.name, "lineWeekDirection2"));
        }
//        System.out.println(this.name);
        for (NodeBusStop nbs: nodeBusStopArrayList){
//            System.out.println(nbs);
            if (nbs != null) {
                if (nbs.getBusStop() != null) {
                    nodeBusStopArrayListClean.add(nbs);
                }
            }
        }
        return nodeBusStopArrayListClean;
    }


    public String nameLineWithBusStops(String busStopName1, ArrayList<String> listHourOfPassage1,
                                       String busStopName2, ArrayList<String> listHourOfPassage2,
                                       @NotNull String typeDay) {
        switch (typeDay) {
            case "saturday or summer":
                if (isAfter(this.lineSaturdayDirection1, busStopName1, listHourOfPassage1, busStopName2, listHourOfPassage2)) {
                    return "lineSaturdayDirection1";
                } else if (isAfter(this.lineSaturdayDirection2, busStopName1, listHourOfPassage1, busStopName2, listHourOfPassage2)) {
//                } else {
                    return "lineSaturdayDirection2";
                }
            case "week":
                if (isAfter(this.lineWeekDirection1, busStopName1, listHourOfPassage1, busStopName2, listHourOfPassage2)) {
                    return "lineWeekDirection1";
                } else if (isAfter(this.lineWeekDirection2, busStopName1, listHourOfPassage1, busStopName2, listHourOfPassage2)) {
//                } else {
                    return "lineWeekDirection2";
                }
        }
//        System.out.println(this.getName());
        return null;
    }

    public boolean isAfter(@NotNull Line line,
                           String busStopName1, ArrayList<String> listHourOfPassage1,
                           String busStopName2, ArrayList<String> listHourOfPassage2){
        return line.isBusStopHere(busStopName1, listHourOfPassage1) &&
                line.isBusStopHere(busStopName2, listHourOfPassage2) &&
                line.isAfter(busStopName2, busStopName1);
//        return line.isBusStopHere(busStopName1, listHourOfPassage1) &&
//                line.isBusStopHere(busStopName2, listHourOfPassage2) &&
//                line.isAfter(busStopName2, busStopName1);
    }

    public boolean isAfterWithInfo(String nameBusStop1, String nameBusStop2, String lineName){

        switch (lineName){
            case("lineSaturdayDirection1"):
                return this.lineSaturdayDirection1.isAfter(nameBusStop2, nameBusStop1);
            case("lineSaturdayDirection2"):
                return this.lineSaturdayDirection2.isAfter(nameBusStop2, nameBusStop1);
            case("lineWeekDirection1"):
                return this.lineWeekDirection1.isAfter(nameBusStop2, nameBusStop1);
            case("lineWeekDirection2"):
                return this.lineWeekDirection2.isAfter(nameBusStop2, nameBusStop1);
        }
        return false;

    }

    public ArrayList<NodeBusStop> getNodeBusStop(String nameBusStop, String typeDay){
        ArrayList<NodeBusStop> outputList = new ArrayList<>();
        switch (typeDay) {
            case "saturday or summer":
                outputList.add(this.lineSaturdayDirection1.getNodeBusStop(nameBusStop, this.name, "lineSaturdayDirection1"));
                outputList.add(this.lineSaturdayDirection1.getNodeBusStop(nameBusStop, this.name, "lineSaturdayDirection2"));
            case "week":
                outputList.add(this.lineWeekDirection1.getNodeBusStop(nameBusStop, this.name, "lineWeekDirection1"));
                outputList.add(this.lineWeekDirection2.getNodeBusStop(nameBusStop, this.name, "lineWeekDirection2"));
        }
        return outputList;

    }


    /*
    =======================================================================
     Print, tests and debug
    =======================================================================
    */

    // print
    public void print(){
//        this.lineWeekDirection1.print();
//        this.lineWeekDirection2.print();
//        this.lineSaturdayDirection1.print();
//        this.lineSaturdayDirection2.print();
        System.out.println(this.name);
    }


}
