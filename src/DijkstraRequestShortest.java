import org.w3c.dom.Node;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

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
//        System.out.println(sibra.getNearestBusStop("CAMPUS", this.typeDay));
//        for (NodeBusStop nbs : sibra.getNearestBusStop(this.arrival, this.typeDay)) {
//            System.out.println("=========");
//            System.out.println(nbs.getNameBus());
//            System.out.println(nbs.getNameLine());
//            nbs.getBusStop().print();
//        }
//        System.out.println("=========");
//
//        System.out.println(getTimeBetweenTwoBusStop("Mandallaz", "Chorus"));
        printDjikstraPath();


    }

    public void initialise(){

    }

    private ArrayList<Path> doDijkstra() {
        // Initialisation

        // liste des noeuds pas passés
        ArrayList<NodeBusStop> listOfRemainingBusStops = this.sibra.getAllNodeBusStops(this.typeDay);
        // liste des chemins
        ArrayList<Path> listOfPaths = new ArrayList<>();
        ArrayList<Path> newListOfPaths = new ArrayList<>();
        // liste des chemins choisis
        ArrayList<Path> listOfSelectedPaths = new ArrayList<>();

        // Noeud actuel
        NodeBusStop actualNode = this.sibra.busStopToNodeBusStop(this.departure, this.typeDay).get(0);
        Path actualPath = new Path(actualNode, null, 0);
        listOfSelectedPaths.add(actualPath);
        listOfRemainingBusStops = removeNodeBusStopFromList(listOfRemainingBusStops, actualNode);


        int t;
        Path shortestPath;
        Path newPath;

        // Loop
        while (listOfRemainingBusStops.size() > 0){

            shortestPath = new Path(null, null, 1000);

            // ********************
            // update de la liste des chemins -> on ajoute tous les chemins possibles
            // ********************
            // pour toutes les destinations possibles depuis le chemin actuel
            for (NodeBusStop nbs: sibra.getNearestBusStop(actualNode.getNameBusStop(), actualNode.getNameBus(), this.typeDay)){
                // si l'arret n'a pas encore été emprunté -> si il est encore dans la liste des destinations possibles
                if (isBusStopInList(nbs.getNameBusStop(), listOfRemainingBusStops)){
//                    System.out.println("_______");
//                    System.out.println("actual: " + actualNode.getNameBusStop());
//                    System.out.println("before: " + nbs.getNameBusStop());
//                    System.out.println(getTimeBetweenTwoBusStop(actualNode.getNameBusStop(), nbs.getNameBusStop()));
                    t = getTimeBetweenTwoBusStop(actualNode.getNameBusStop(), nbs.getNameBusStop()) + actualPath.timeSinceDeparture;
//                    System.out.println("t: " + t);
//                    System.out.println("_______");
                    newPath = new Path(nbs, actualNode, t);
                    newListOfPaths.add(newPath);
                }
            }
            listOfPaths = newListOfPaths;
            newListOfPaths = new ArrayList<>();

            // ********************
            // update de la liste des chemins les plus courts
            // ********************
            // recherche du chemin le plus court
//            System.out.println("-- " + listOfPaths);
            for (Path p: listOfPaths){
                if (p.timeSinceDeparture < shortestPath.timeSinceDeparture){
                    shortestPath = p;
                }
            }

            // nouveau chemin actuel
            actualNode = shortestPath.actualBusStop;
            actualPath = shortestPath;
            listOfSelectedPaths.add(actualPath);
//            System.out.println("*********");
//            System.out.println(actualNode.getNameBus());
//            System.out.println(actualNode.getNameLine());
//            System.out.println(actualNode.getNameBusStop());

            // ********************
            // update de la liste de la liste des noeuds
            // ********************
            if (actualNode == null){break;}
            listOfRemainingBusStops = removeNodeBusStopFromList(listOfRemainingBusStops, actualNode);
//            System.out.println(listOfRemainingBusStops.size());

        }

        return listOfSelectedPaths;

    }

    private void displayPath(){

    }

    private ArrayList<NodeBusStop> removeNodeBusStopFromList(ArrayList<NodeBusStop> nodeBusStops, NodeBusStop nodeBusStop){
        ArrayList<NodeBusStop> outputNodeBusStops = new ArrayList<>();
        boolean isSameBus;
//        boolean isSameLine;
        boolean isSameBusStop;
        String nodeBusStopNameBus = nodeBusStop.getNameBus();
//        String nodeBusStopNameLine = nodeBusStop.getNameLine();
        String nodeBusStopName = nodeBusStop.getNameBusStop();
        for (NodeBusStop nbs: nodeBusStops){
            isSameBus = nbs.getNameBus().equals(nodeBusStopNameBus);
//            isSameLine = nbs.getNameLine().equals(nodeBusStopNameLine);
            isSameBusStop = nbs.getBusStop().getName().equals(nodeBusStopName);
            if (!isSameBus || !isSameBusStop) {
                outputNodeBusStops.add(nbs);
            } else {
//                System.out.println(nbs.getNameBusStop());
//                System.out.println(nbs.getNameBus());
            }
        }
//        for (NodeBusStop nbs: outputNodeBusStops){
//            System.out.print(nbs.getNameBusStop() + "(" + nbs.getNameBus() + ") - ");
//        }
//        System.out.println(" ");
//        System.out.println("_______________");
        return outputNodeBusStops;
    }

    /*
    =======================================================================
     Methods
    =======================================================================
    */


    public NodeBusStop getNearestBusStop(NodeBusStop departureBusStopNode, String hour){

        int busNumbre = getNextBusAtThisHour(departureBusStopNode, hour);

        // for this bus if it's possible
        for (NodeBusStop nbs: this.sibra.getNearestBusStop(departureBusStopNode.getNameBusStop(), null,this.typeDay)){
            if (departureBusStopNode.getNameBus().equals(nbs.getNameBus()) &&
                departureBusStopNode.getNameLine().equals(nbs.getNameLine())) {
                // if same bus same line (= the next bus stop)
                return nbs;
            }
        }

        // else for another bus
        for (NodeBusStop nbs: this.sibra.getNearestBusStop(departureBusStopNode.getNameBusStop(), null,this.typeDay)){
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

    public void printDjikPath(){

        System.out.println("begin dijkstra path: ");

        for (Path p: doDijkstra()) {
            System.out.println("==========");
            if (p.actualBusStop != null) {
                System.out.println("actual bus stop: " + p.actualBusStop.getNameBusStop());
                System.out.println("actual bus: " + p.actualBusStop.getNameBus());
//                System.out.println("hour: " + p.actualBusStop.getHourAfter(hour));
                if (p.previousBusStop != null) {
                    System.out.println("previous bus stop: " + p.previousBusStop.getNameBusStop());
                }
                System.out.println("time since departure: " + p.timeSinceDeparture);
            }
        }
        System.out.println("==========");
    }

    public void printDjikstraPath() {
        ArrayList<Path> djikstraPath = doDijkstra();
        ArrayList<Path> upsideDownPath = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        String hour = this.hour;
        String hourArrival = this.hour;

        Path previousPath;
        Path lastPath;
        boolean isSameBusStop;
        boolean isBefore;
        int counter = 0;
        int intMinutes;
        int t;

        Path path = new Path(null, null, 1000);
        // first path
        for (Path p: djikstraPath){
            isSameBusStop = path.timeSinceDeparture > p.timeSinceDeparture;
            if (p.actualBusStop != null) {
                isBefore = p.actualBusStop.getNameBusStop().equals(this.arrival);
            } else {
                isBefore = false;
            }
            if (isBefore && isSameBusStop){
                path = p;
            }
        }
        upsideDownPath.add(path);

        while (!path.actualBusStop.getNameBusStop().equals(this.departure) && counter <= 1000){
            previousPath = path;
            for (Path p: djikstraPath){
                if (p.actualBusStop != null && previousPath.actualBusStop != null) {
                    isSameBusStop = p.actualBusStop.getNameBusStop().equals(previousPath.previousBusStop.getNameBusStop());
                } else {
                    isSameBusStop = false;
                }
                isBefore = p.timeSinceDeparture < previousPath.timeSinceDeparture;
                if (isSameBusStop && isBefore){
                    path = p;
                }
            }
            upsideDownPath.add(path);
            counter ++;
//            if (path.actualBusStop == null){break;}
        }

        Collections.reverse(upsideDownPath);

//        System.out.println(hour + "");
        for (Path p: upsideDownPath){
            System.out.println("______________");
            System.out.println("bus stop: " + p.actualBusStop.getNameBusStop());
            System.out.println("bus: " + p.actualBusStop.getNameBus());
//            if (p.actualBusStop == null ||  p.previousBusStop == null) {
//                t = 0;
//            } else {
//                t = getTimeBetweenTwoBusStop(p.actualBusStop.getNameBusStop(), p.previousBusStop.getNameBusStop());
//            }
//            intMinutes = Integer.parseInt(hour.substring(0,2)) * 60 + Integer.parseInt(hour.substring(3,5));
//            System.out.println(intMinutes);
//            hour =  p.actualBusStop.getHourAfter(intMinutesToHour(intMinutes));
//            System.out.println("hour: " + hour);
            System.out.println("time: " + p.timeSinceDeparture);
        }
        lastPath = upsideDownPath.get(upsideDownPath.size() - 1);
        intMinutes = Integer.parseInt(hour.substring(0,2)) * 60 + Integer.parseInt(hour.substring(3,5)) + lastPath.timeSinceDeparture;
        hourArrival =  lastPath.actualBusStop.getHourAfter(intMinutesToHour(intMinutes));
        System.out.println("estimated time of arrival: " + hourArrival);

    }

}
