import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sibra {

    ArrayList<String> listPathFile = new ArrayList<>();

    ArrayList<Bus> listBus = new ArrayList<Bus>();

    // by default
    public Sibra(){
        this.listPathFile.add(System.getProperty("user.dir") + "/data/1_Poisy-ParcDesGlaisins.txt");
        this.listPathFile.add(System.getProperty("user.dir") + "/data/2_Piscine-Patinoire_Campus.txt");
    }

    // with non optionnal parameters
    public Sibra(ArrayList<String> listPathFile){
        this.listPathFile = listPathFile;
    }


    public void loadData(){
        // initialisation
        ArrayList<List<String>> data = new ArrayList<>();

        // data in list
        for (String pathBus: this.listPathFile) {
            Bus bus = new Bus(pathBus);
            listBus.add(bus);
            bus.extractData();
//            bus.print();
        }



    }

    public ArrayList<String> listDataToInfo(List<String> listData){
        return null;
    }

    public void test(){

        ReadFile data_l1 = new ReadFile(this.listPathFile.get(0));
        data_l1.print();

        ReadFile data_l2 = new ReadFile(this.listPathFile.get(1));
        data_l2.print();
        System.out.println(data_l2.readRow());
    }

}
