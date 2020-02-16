import java.io.IOException;
import java.text.ParseException;

public class RequestSimple extends Request {
    public RequestSimple(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        super(day, departure, arrival, hour);
    }


}
