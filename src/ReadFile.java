import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    // input
    private final String pathFile;

    // output
    ArrayList<String> outputList = new ArrayList<>();


    public ReadFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public void print() {
        try {
            InputStream flux = new FileInputStream(this.pathFile);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            while ((ligne = buff.readLine()) != null) {
                System.out.println(ligne);
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public ArrayList<String> readRow(){
        try {
            InputStream flux = new FileInputStream(this.pathFile);
            InputStreamReader read = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(read);
            String rowFile;
            while ((rowFile = buff.readLine()) != null) {
                outputList.add(rowFile);
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return outputList;
    }
}
