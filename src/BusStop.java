import java.util.ArrayList;

// ex: Campus

public class BusStop {

    private String rawDataBusStop;
    public String name = "";
    public ArrayList<String> listHourOfPassage = new ArrayList<>();


    public BusStop(String rawDataBusStop){
        this.rawDataBusStop = rawDataBusStop;
        convertRawData();
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
//            System.out.println(element);
            if (!this.name.equals("")) this.listHourOfPassage.add(element);
            else if (!element.equals("")) this.name = element;
        }

    }

    public String getName(){
        return this.name;
    }

}
