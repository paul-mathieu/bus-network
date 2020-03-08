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

        Line ligne_1 = sibra.listBus.get(0).lineWeekDirection1;
        ligne_1.dataLine.get(0).print();
        ligne_1.dataLine.get(1).print();
//        ligne_1.dataLine.get(2).print();
//
        System.out.println(ligne_1.dataLine.get(0).isReverse(ligne_1.dataLine.get(1)));
        System.out.println(ligne_1.dataLine.get(1).isReverse(ligne_1.dataLine.get(0)));
        System.out.println(ligne_1.dataLine.get(1).isReverse(ligne_1.dataLine.get(2)));

    }

    // fonction de recherche du poids minimal

}
