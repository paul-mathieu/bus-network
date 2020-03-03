import java.io.IOException;
import java.text.ParseException;

// for journeys on the same bus line
public class SimpleRequest extends Request {

    // super
    public SimpleRequest(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        super(day, departure, arrival, hour);
    }

    //
    public void doRequest(){
        // list of buses in departure of bus stop
        System.out.println(this.sibra.listBusStop(this.departure));
        // first hour possible

        busDepartureAndArrival();
//        if (lineInThisDirection)
    }

    public void busDepartureAndArrival(){
        sibra.displayBusDepartureAndArrival(this.departure, this.arrival);
    }


}
