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
        ArrayList<Path> lastListOfPaths = new ArrayList<>();
        ArrayList<Path> newNewListOfPaths = new ArrayList<>();
        ArrayList<NodeBusStop> nearestBusStops;
        int appearsCounter;
        boolean isAlready;
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
            nearestBusStops = sibra.getNearestBusStop(actualNode.getNameBusStop(), actualNode.getNameLine(), actualNode.getNameBus(), this.typeDay);
//            System.out.println("++++" + nearestBusStops.size() + "++++");
            for (NodeBusStop nbs: nearestBusStops){
                // si l'arret n'a pas encore été emprunté -> si il est encore dans la liste des destinations possibles
                if (isBusStopInList(nbs.getNameBusStop(), nbs.getNameLine(), nbs.getNameBus(), listOfRemainingBusStops)){
//                    System.out.println(nbs.getNameBusStop() + " - " + nbs.getNameBus() + " - " + nbs.getNameLine());
                    t = getTimeBetweenTwoBusStop(actualNode.getNameBusStop(), nbs.getNameBusStop()) + actualPath.timeSinceDeparture;
                    newPath = new Path(nbs, actualNode, t);
                    newListOfPaths.add(newPath);
                }
            }
            // si deux arrest sont identiques, on garde le plus court
            for (Path p1: newListOfPaths){
                appearsCounter = 0;
                for (Path p2: newListOfPaths){
                    if (p1.actualBusStop == p2.actualBusStop){
                        appearsCounter ++;
                    }
                }
                if (appearsCounter == 1){
                    // si une fois, on le garde
                    newNewListOfPaths.add(p1);
                } else {
                    // sinon on garde le plus court
                    newPath = p1;
                    for (Path p2: newListOfPaths){
                        if (p1.actualBusStop == p2.actualBusStop && p2.timeSinceDeparture < newPath.timeSinceDeparture){
                            newPath = p2;
                        }
                    }
                    // ajout que si il n'y st pas deja
                    isAlready = false;
                    for (Path p: newNewListOfPaths){
                        if (p == newPath){
                            isAlready = true;
                        }
                    }
                    if (!isAlready) newNewListOfPaths.add(newPath);
                }
            }

            lastListOfPaths = listOfPaths;
            listOfPaths = new ArrayList<>();
            listOfPaths.addAll(lastListOfPaths);
            listOfPaths.addAll(newListOfPaths);

//            listOfPaths = newNewListOfPaths;

//            listOfPaths = newListOfPaths;
            newListOfPaths = new ArrayList<>();
//            newNewListOfPaths = new ArrayList<>();

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
//            for (Path p: listOfSelectedPaths) System.out.println(p.actualBusStop.getNameBusStop());
//            System.out.println("++++" + actualNode.getNameBusStop() + " - " + actualNode.getNameBus() + " - " + actualNode.getNameLine());

            // ********************
            // update de la liste de la liste des noeuds
            // ********************
            if (actualNode == null){break;}
//            for (NodeBusStop nbs: listOfRemainingBusStops){
//                System.out.println();
//            }
            listOfPaths = removePathFromList(listOfPaths, actualPath);
            listOfRemainingBusStops = removeNodeBusStopFromList(listOfRemainingBusStops, actualNode);

//            System.out.println(listOfRemainingBusStops.size());

        }

        return listOfSelectedPaths;

    }

    private ArrayList<NodeBusStop> removeNodeBusStopFromList(ArrayList<NodeBusStop> nodeBusStops, NodeBusStop nodeBusStop){
        ArrayList<NodeBusStop> outputNodeBusStops = new ArrayList<>();
        boolean isSameBus;
        boolean isSameLine;
        boolean isSameBusStop;
        String nodeBusStopNameBus = nodeBusStop.getNameBus();
        String nodeBusStopNameLine = nodeBusStop.getNameLine();
//        System.out.println("n: " + nodeBusStopNameLine);
        String nodeBusStopName = nodeBusStop.getNameBusStop();
        for (NodeBusStop nbs: nodeBusStops){
            isSameBus = nbs.getNameBus().equals(nodeBusStopNameBus);
            isSameLine = nbs.getNameLine().equals(nodeBusStopNameLine);
            isSameBusStop = nbs.getBusStop().getName().equals(nodeBusStopName);
//            System.out.println(isSameLine);
            if (isSameBus && isSameLine && isSameBusStop) {
//            if (isSameBus && isSameBusStop) {
//                System.out.println(nbs.getNameBusStop());
//                System.out.println(nbs.getNameBus());
            } else {
                outputNodeBusStops.add(nbs);
            }
        }
//        for (NodeBusStop nbs: outputNodeBusStops){
//            System.out.print(nbs.getNameBusStop() + "(" + nbs.getNameBus() + ") - ");
//        }
//        System.out.println(" ");
//        System.out.println("_______________");
        return outputNodeBusStops;
    }

    private ArrayList<Path> removePathFromList(ArrayList<Path> paths, Path path){
        ArrayList<Path> outputPaths = new ArrayList<>();
        for (Path p: paths){
            if (p != path) {
                outputPaths.add(p);
            }
        }
        return outputPaths;
    }

    /*
    =======================================================================
     Methods
    =======================================================================
    */


    public NodeBusStop getNearestBusStop(NodeBusStop departureBusStopNode, String hour){

        int busNumbre = getNextBusAtThisHour(departureBusStopNode, hour);

        // for this bus if it's possible
        for (NodeBusStop nbs: this.sibra.getNearestBusStop(departureBusStopNode.getNameBusStop(), null, null,this.typeDay)){
            if (departureBusStopNode.getNameBus().equals(nbs.getNameBus()) &&
                departureBusStopNode.getNameLine().equals(nbs.getNameLine())) {
                // if same bus same line (= the next bus stop)
                return nbs;
            }
        }

        // else for another bus
        for (NodeBusStop nbs: this.sibra.getNearestBusStop(departureBusStopNode.getNameBusStop(), null, null,this.typeDay)){
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
        System.out.println(djikstraPath.size());
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
        boolean isFirst = true;

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

        System.out.println(path.actualBusStop);
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
            System.out.println("direction: " + sibra.getDirection(p.getNameBus(), p.getNameLine()));
            if (isFirst) {
                isFirst = false;
                System.out.println("estimated time of departure: " + p.actualBusStop.getHourAfter(this.hour));
            }
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
