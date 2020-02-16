import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// use only to extract public holiday

public class PublicHoliday {
    private String stringURL;
    private String rawData;

    public ArrayList<String> listPublicHoliday;

    public PublicHoliday(String stringURL) throws IOException {
        this.stringURL = stringURL;
        this.rawData = extractData();
        this.listPublicHoliday = extractPublicHoliday();
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
        return outputList;
    }

    public void print(){
        System.out.println(this.listPublicHoliday);
    }

}
