import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        // init request
        String day = "2020-02-14";
        String departure = "Ponchy";
//        String arrival = "POISY_COLLÈGE";
        String arrival = "CAMPUS";
        String hour = "12:00";

//        DijkstraRequestShortest dijkstraRequestShortest = new DijkstraRequestShortest(day, departure, arrival, hour);
//        System.out.println(dijkstraRequestShortest.getTimeBetweenTwoBusStop("VIGNIÈRES", "CAMPUS"));
//        System.out.println(dijkstraRequestShortest.typeDay);
//        dijkstraRequestShortest.doRequest();

//        DijkstraRequestForemost dijkstraRequestForemost = new DijkstraRequestForemost(day, departure, arrival, hour);
//        dijkstraRequestForemost.doRequest();

        DijkstraRequestFastest dijkstraRequestFastest = new DijkstraRequestFastest(day, departure, arrival, hour);
        dijkstraRequestFastest.doRequest();

    }

}

