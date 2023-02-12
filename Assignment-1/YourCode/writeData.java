import java.io.*;
import java.util.HashMap;

public class writeData {

    public writeData(HashMap accBalData) {
        String filePath = new File("").getAbsolutePath();
        writeToFile(filePath, accBalData);
    }

    // Loops through HashMap and re-writes new data into csv file
    public void writeToFile(String path, HashMap map) {

        String updateFile = path + "/Assignment-1/Data-Assignment-1/csv/account-balance.csv";
        File file = new File(updateFile);

        String eol = System.getProperty("line.separator");

        try (Writer writer = new FileWriter(file)) {

            HashMap<String, Integer> newMap = map;
            for (HashMap.Entry<String, Integer> entry : newMap.entrySet()) {
                writer.append(entry.getKey())
                        .append(',')
                        .append(entry.getValue().toString())
                        .append(eol);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

    }

}