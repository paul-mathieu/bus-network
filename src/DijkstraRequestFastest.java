import java.io.IOException;
import java.text.ParseException;

public class DijkstraRequestFastest extends Request {
    public DijkstraRequestFastest(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        super(day, departure, arrival, hour);
    }
}
