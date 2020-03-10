import java.io.IOException;
import java.text.ParseException;

public class DijkstraRequestForemost extends Request {
    public DijkstraRequestForemost(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        super(day, departure, arrival, hour);
    }
}
