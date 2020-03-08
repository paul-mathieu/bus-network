import java.io.IOException;
import java.text.ParseException;

public class DijkstraRequest extends Request {

    public DijkstraRequest(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        super(day, departure, arrival, hour);
    }

    public void doRequest(){
//        System.out.println(sibra.getNearestBusStop("CAMPUS", this.typeDay));
        for (NodeBusStop nbs: sibra.getNearestBusStop(this.arrival, this.typeDay)) {
            System.out.println("=========");
            System.out.println(nbs.nameBus);
            System.out.println(nbs.nameLine);
            nbs.busStop.print();
        }
        System.out.println("=========");
    }

    // fonction de recherche du poids minimal

}
