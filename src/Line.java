import java.util.ArrayList;

public class Line {

    private ArrayList<String> rawDataLine;
    public ArrayList<BusStop> dataLine;

    // initialisation
    public Line(ArrayList<String> rawDataLine){
        this.rawDataLine = rawDataLine;
        convertRawData();
    }

    // print
    public void print(){
        System.out.println(this.rawDataLine);
    }

    // convert raw data
    public void convertRawData(){
        // écrire le code pour convertir des lignes
    }
}
