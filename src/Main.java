import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        // init request
        String day = "2020-02-14";
        String departure = "GARE";
        String arrival = "POISY_COLLÈGE";
        String hour = "12:00";

//        Request request = new Request(day, departure, arrival, hour);
//        SimpleRequest simpleRequest = new SimpleRequest(day, departure, arrival, hour);
//        simpleRequest.doRequest();

//        System.out.println(day.substring(0,4));

        DijkstraRequest dijkstraRequest = new DijkstraRequest(day, departure, arrival, hour);
        System.out.println(dijkstraRequest.typeDay);
        dijkstraRequest.doRequest();

    }

}

