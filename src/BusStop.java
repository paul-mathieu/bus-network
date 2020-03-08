import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

// ex: Campus

public class BusStop {

    private String rawDataBusStop;
    public String name = "";
    public ArrayList<String> listHourOfPassage = new ArrayList<>();

    public Boolean isSeparation = false;         // if it's one of two possibilities (after junction separation)
    public Boolean isJunctionSeparation = false; // TODO if there are two possibilities next
    public Boolean isCrossingLines = false;      // TODO if an other bus line stops there
    public Boolean isNotAlready = false;         // if don't already pass
    public Boolean isTerminus = false;           // if is terminus (even if not already)

    public boolean isUsed = false;

    public BusStop(String rawDataBusStop){
        this.rawDataBusStop = rawDataBusStop;
        convertRawData();
        setIsNotAlready();
        setIsTerminus();
    }

    public void print(){
        System.out.print("Bus Stop Name: ");
        System.out.println(this.name);
        System.out.print("Hour: ");
        System.out.println(this.listHourOfPassage);
    }

    private void convertRawData(){
        String[] rawDataSplit = this.rawDataBusStop.split("\\s");
        for (String element : rawDataSplit) {
            if (!this.name.equals("")) this.listHourOfPassage.add(element);
            else if (!element.equals("")) this.name = element;
        }

    }

    public String getName(){
        return this.name;
    }

    /*
    ==================================================
     Setters
    ==================================================
     */

    private void setIsNotAlready(){
        if (this.rawDataBusStop.contains("-")){
            this.isNotAlready = true;
        }
    }

    public void setIsUsed(boolean b){
        this.isUsed = b;
    }

    private void setIsTerminus(){
        if (true){
            this.isTerminus = true;
        }
    }

    public boolean isReverse(BusStop busStop){
        AtomicBoolean check = new AtomicBoolean(true);
        for (int i =  0 ; i <  this.listHourOfPassage.size() ; i++){
            // b_temp = "-" and "12:00" || "12:00" and "-"
            boolean b_temp =
                    (this.listHourOfPassage.get(i).contains("-") && !busStop.listHourOfPassage.get(i).contains("-")) ||
                    (busStop.listHourOfPassage.get(i).contains("-") && !this.listHourOfPassage.get(i).contains("-"));
            if (!b_temp) {
                check.set(false);
                break;
            }
        }
//        System.out.println(check.get());
        return check.get();
    }

    /*
    ==================================================
     Functions for graph
    ==================================================
     */


}
