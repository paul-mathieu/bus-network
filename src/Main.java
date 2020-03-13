import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        // init request
        String day = "2020-02-14";
        String departure = "GARE";
        String arrival = "POISY_COLLÈGE";
        String hour = "12:00";

        DijkstraRequestShortest dijkstraRequestShortest = new DijkstraRequestShortest(day, departure, arrival, hour);
        dijkstraRequestShortest.doRequest();
//        System.out.println(dijkstraRequestShortest.typeDay);

    }

}

