import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogSystem {
	
	//Get sample-log.csv data
    static HashMap readLogData(File child) {
        String splitDataBy = ",";
        String line = "";
        BufferedReader br = null;

        HashMap<Integer, List<String>> log = new HashMap<Integer, List<String>>();
        
        //Try-catch-finally block to read data into a HashMap
        //KEY = ID
        try{
            br = new BufferedReader(new FileReader(child));
            while((line = br.readLine()) != null){
                List<String> data = new ArrayList<String>();

                String[] dataRow = line.split(splitDataBy);
                //Skip first value (Key)
                for(int i = 1; i < dataRow.length; i++){
                    data.add(dataRow[i]);
                }
                log.put(Integer.parseInt(dataRow[0]), data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return log;
    }
    
    public static void writeLogData(HashMap<Integer, List<String>> logData) { //writes the transaction entry to the log csv file
		try (FileWriter writer = new FileWriter("")) {
			clearLog();
            for (Map.Entry<Integer, List<String>> entry : logData.entrySet()) {
                Integer key = entry.getKey();
                List<String> values = entry.getValue();
                String line = key + "," + String.join(",", values);
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    public static void clearLog() { //helper to clear csv file contents
		File file = new File("");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}

}
