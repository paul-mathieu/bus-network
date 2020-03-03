import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class Request {

    protected String day;
    protected String departure;
    protected String arrival;
    protected String hour;

    private final String stringURL;

    public String typeDay;

    public Sibra sibra = new Sibra();
    public ArrayList<String> listPublicHoliday = new ArrayList<>();

    public String stringDay;

    public Request(String day, String departure, String arrival, String hour) throws IOException, ParseException {
        this.day = day;
        this.departure = departure;
        this.arrival = arrival;
        this.hour = hour;

        this.stringURL = "https://jours-feries-france.antoine-augusti.fr/api/" + this.day.substring(0,4);

        initialize();
    }

    private void initialize() throws IOException, ParseException {
        // create Sibra
        this.sibra.loadData();

        // list public holiday
        PublicHoliday publicHoliday = new PublicHoliday(this.stringURL);
        this.listPublicHoliday = publicHoliday.listPublicHoliday;

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        this.stringDay = sdf.format(date);

        setTypeDay();
    }

    private void setTypeDay() {
        // if sunday or public holiday > no data available ("no data available")
        // if saturday or summer > data saturday ("saturday or summer")
        // else > data week ("week")
        Boolean isInSummer = (this.day.substring(5, 7).equals("07")) || (this.day.substring(5, 7).equals("08"));
        Boolean isPublicHoliday = isPublicHoliday();
        Boolean isSunday = (this.stringDay.equals("dimanche")) || (this.stringDay.equals("sunday"));
        Boolean isSaturday = (this.stringDay.equals("samedi")) || (this.stringDay.equals("saturday"));

        if (isSunday || isPublicHoliday){
            this.typeDay = "no data available";
        } else if (isInSummer || isSaturday){
            this.typeDay = "saturday or summer";
        } else {
            this.typeDay = "week";
        }

    }

    private Boolean isPublicHoliday() {
        AtomicReference<Boolean> verify = new AtomicReference<>(false);
        for (String date: this.listPublicHoliday){
            if (date.equals(this.day)) {
                verify.set(true);
                break;
            }
        }
        return verify.get();
    }

}
