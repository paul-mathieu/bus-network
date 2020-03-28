import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        // init request
        String day = "2020-02-14";
        String departure = "Ponchy";
        String arrival = "POISY_COLLÈGE";
        String hour = "12:00";

        DijkstraRequestShortest dijkstraRequestShortest = new DijkstraRequestShortest(day, departure, arrival, hour);
//        System.out.println(dijkstraRequestShortest.getTimeBetweenTwoBusStop("VIGNIÈRES", "CAMPUS"));
//        System.out.println(dijkstraRequestShortest.getInfoLine("VIGNIÈRES", "CAMPUS"));
//        System.out.println(dijkstraRequestShortest.getInfoLine("GARE", "Courier"));
//        System.out.println(dijkstraRequestShortest.getInfoLine("GARE", "Bonlieu"));

        dijkstraRequestShortest.doRequest();
//        System.out.println(dijkstraRequestShortest.typeDay);

    }

}

