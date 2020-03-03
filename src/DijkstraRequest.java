import java.io.IOException;
import java.text.ParseException;

public class DijkstraRequest extends Request {

    private Graph graph;

    public DijkstraRequest(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        super(day, departure, arrival, hour);
    }


}
