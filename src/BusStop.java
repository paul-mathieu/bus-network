import java.util.ArrayList;

public class BusStop {

    private String rawDataBusStop;
    public String name = null;
    public ArrayList<String> listHourOfPassage;


    public BusStop(String rawDataBusStop){
        this.rawDataBusStop = rawDataBusStop;
        convertRawData();
    }

    public void print(){
        System.out.println(this.rawDataBusStop);
    }

    private void convertRawData(){
        String[] rawDataSplit = this.rawDataBusStop.split("\\s");

        for (String element : rawDataSplit) {
            if (this.name == null) {
                this.name = element;
            } else {
                this.listHourOfPassage.add(element);
            }
        }

    }

}
