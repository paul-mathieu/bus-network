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

    public String name;

    // initialisation
    public Bus(String pathFile){
        ReadFile readFile = new ReadFile(pathFile);
        this.rawData = readFile.readRow();
        this.name = pathFile.substring(pathFile.indexOf("data/") + 5, pathFile.indexOf(".txt"));
    }


    // print
    public void print(){
//        this.lineWeekDirection1.print();
//        this.lineWeekDirection2.print();
//        this.lineSaturdayDirection1.print();
//        this.lineSaturdayDirection2.print();
        System.out.println(this.name);
    }

    //extract data from file
    public void extractData() {

        // aller juste apres les deux \n qui se suivent
        // etape 1 : tant que element de liste non vide, stocker les donnees (semaine_aller)
        // etape 2 : apres element de liste non vide, tant que element de liste non vide, sotcker les donnees (semaine_retour)
        // etape 3 : apres deux elements de liste vide, tant que element de liste non vide, sotcker les donnees (samedi_retour)
        // etape 4 : apres element de liste non vide, tant que element de liste non vide, sotcker les donnees (samedi_retour)

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
            else if (countEmpty.get() == 3) step.set(0);
            else if (countEmpty.get() == 4) step.set(3);
            else if (countEmpty.get() == 5) step.set(4);

            // step 1 (countEmpty = 1)
            if (step.get() == 1 && counterValidity.get() >= 1) raw_weekDirection1.add(row);
            else if (step.get() == 2 && counterValidity.get() >= 1) raw_weekDirection2.add(row);
            else if (step.get() == 3 && counterValidity.get() >= 3) raw_saturdayDirection1.add(row);
            else if (step.get() == 4 && counterValidity.get() >= 1) raw_saturdayDirection2.add(row);

            counterValidity.getAndIncrement();


        }

//        System.out.println(raw_weekDirection1);
//        System.out.println(raw_weekDirection2);
//        System.out.println(raw_saturdayDirection1);
//        System.out.println(raw_saturdayDirection2);

//        for (String s: raw_weekDirection1) System.out.println(s);

        this.lineWeekDirection1 = new Line(raw_weekDirection1);
        this.lineWeekDirection2 = new Line(raw_weekDirection2);
        this.lineSaturdayDirection1 = new Line(raw_saturdayDirection1);
        this.lineSaturdayDirection2 = new Line(raw_saturdayDirection2);

    }

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

    public ArrayList<Line> listLineOfTheDay(String typeDay){
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

}
