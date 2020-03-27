import java.io.IOException;
import java.text.ParseException;

public class DijkstraRequestShortest extends Request {

    /*
    =======================================================================
     Initialisation
    =======================================================================
    */

    public DijkstraRequestShortest(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        super(day, departure, arrival, hour);
    }


    public void doRequest() {
        System.out.println(sibra.getNearestBusStop("CAMPUS", this.typeDay));
        for (NodeBusStop nbs : sibra.getNearestBusStop(this.arrival, this.typeDay)) {
            System.out.println("=========");
            System.out.println(nbs.getNameBus());
            System.out.println(nbs.getNameLine());
            nbs.getBusStop().print();
        }
        System.out.println("=========");

        System.out.println(getTimeBetweenTwoBusStop("Mandallaz", "Chorus"));

    }

    public void initialise(){

    }


    /*
    =======================================================================
     Methods
    =======================================================================
    */

    private NodeBusStopGraph doDijkstraGraph(){

        return null;
    }

    public NodeBusStop getNearestBusStop(NodeBusStop departureBusStopNode, String hour){

        int busNumbre = getNextBusAtThisHour(departureBusStopNode, hour);

        // for this bus if it's possible
        for (NodeBusStop nbs: this.sibra.getNearestBusStop(departureBusStopNode.getNameBusStop(),this.typeDay)){
            if (departureBusStopNode.getNameBus().equals(nbs.getNameBus()) &&
                departureBusStopNode.getNameLine().equals(nbs.getNameLine())) {
                // if same bus same line (= the next bus stop)
                return nbs;
            }
        }

        // else for another bus
        for (NodeBusStop nbs: this.sibra.getNearestBusStop(departureBusStopNode.getNameBusStop(),this.typeDay)){
            // prendre en compte le temps pour aller à l'arrêt pour pouvoir ensuite savoir à partir de quand attendre un autre bus
            //    -> fonction dans Sibra pour savoir le temps entre deux arrêts (rajouter une minute de marge apres)
            //    -> continuer sette boucle for en regardant l procain bus qui passe
        }

        return null;
    }


    /*
    =======================================================================
     Print and debugs
    =======================================================================
    */

}
