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
	
	String csvFolderPath;
	
	public LogSystem(String Path) {
		csvFolderPath = Path;
	}
	
	public void displayLogContent() throws Exception {
		File dir = new File(csvFolderPath);
        File[] dirFiles = dir.listFiles();

        //Checks if directory of files exists then loops through all files
        if (dirFiles != null) {
            for (File child : dirFiles) {
                    String dataset = child.getName();
                    //Checks which file the system is on
                    if (dataset.equals("sample-log.csv")){
                        HashMap log = readLogData(child);
                        formatLog(log);
                    }
            }
        } else {
            throw new Exception("Directory doesn't contain any file contents.");
        }
	}
	
	//Reads log.csv data
    private static HashMap readLogData(File child) {
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
    
    //Prints the log in table format
    private static void formatLog(HashMap<Integer, List<String>> logData) {
    	
    	StringBuilder sb = new StringBuilder();
        for (int key : logData.keySet()) {
            sb.append("Transaction ").append(key).append(":").append("\n");
            List<String> values = logData.get(key);
            sb.append(" Table Altered = ").append(values.get(0)).append("\n")
              .append(" Account Number = ").append(values.get(1)).append("\n")
              .append(" Balance Before = ").append(values.get(2)).append("\n")
              .append(" Balance After = ").append(values.get(3)).append("\n")
              .append(" Time Stamp = ").append(values.get(4)).append("\n")
              .append(" Customer ID = ").append(values.get(5)).append("\n")
              .append(" Successful = ").append(values.get(6)).append("\n\n");
        }
        System.out.println(sb.toString());
    }
    
    //Updates the log.csv file
    public static void writeLogData(HashMap<Integer, List<String>> logData) { 
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
    
    //clears log.csv contents
    private static void clearLog() { 
		File file = new File("");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}

}
