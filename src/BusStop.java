import java.util.ArrayList;
import java.util.Arrays;

// ex: Campus

public class BusStop {

    private String rawDataBusStop;
    public String name = "";
    public ArrayList<String> listHourOfPassage = new ArrayList<>();

    public Boolean isSeparation = false;         // TODO if it's one of two possibilities
    public Boolean isJunctionSeparation = false; // TODO if there are two possibilities next
    public Boolean isCrossingLines = false;      // TODO if an other bus line stops there
    public Boolean isNotAlready = false;         // if don't already pass
    public Boolean isTerminus = false;           // TODO if is terminus (even if not already)

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
}
