import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// use only to extract public holiday

public class PublicHoliday {
    private String stringURL;
    private String rawData;

public ArrayList<String> listPublicHoliday;

    public PublicHoliday(String stringURL) throws IOException {
        this.stringURL = stringURL;
        setData();
    }

    private void setData() throws IOException {
        if (netIsAvailable()) {
            this.rawData = extractData();
        } else {
            this.rawData = null;
        }

        if (this.rawData != null) {
            this.listPublicHoliday = extractPublicHoliday();
        } else {
            this.listPublicHoliday = new ArrayList<>();
            int curYear = Calendar.getInstance().get(Calendar.YEAR);
            String[] days = new String[]{"01-01", "04-13", "05-01", "05-08", "05-21", "06-01", "07-14", "08-15", "11-01", "11-11", "12-25"};
            for (String d : days) {
//                System.out.println(curYear + "-" + d);
                this.listPublicHoliday.add(curYear + "-" + d);
            }
        }
//        System.out.println(this.listPublicHoliday);
    }


    private String extractData() throws IOException {

        URL url = new URL(this.stringURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = in.lines().collect(Collectors.joining());
        in.close();

        // print result
        return response;

    }

    private ArrayList<String> extractPublicHoliday(){
        ArrayList<String> outputList = new ArrayList<>();
        String temp_rawData = this.rawData;
        String key = "\"date\"";
        AtomicInteger index = new AtomicInteger();

        while (temp_rawData.contains(key)){
            index.set(temp_rawData.indexOf(key));
            outputList.add(temp_rawData.substring(index.get() + key.length() + 3, index.get() + key.length() + 13));
            temp_rawData = temp_rawData.substring(index.get() + 13);
        }
//        System.out.println(outputList);
        return outputList;
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            ((URLConnection) conn).getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    public void print(){
        System.out.println(this.listPublicHoliday);
    }

}
