import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        // init request
        String day = "2020-02-14";
        String departure = "CAMPUS";
        String arrival = "GARE";
        String hour = "12:00";

        Request request = new Request(day, departure, arrival, hour);

        System.out.println(request.typeDay);

    }
}

