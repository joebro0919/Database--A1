import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
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
                    if (dataset.equals("log.csv")){
                    	if(child.length() > 0) {
                    		HashMap log = readLogData(child);
                            formatLog(log);
                    	} else {
                    		System.out.println("The log is currently empty.");
                    	}
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
                for(int i = 0; i < dataRow.length; i++){
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
            sb.append("Transaction (ID = ").append(key).append("):").append("\n");
            List<String> values = logData.get(key);
            sb.append(" Table Altered = ").append(values.get(1)).append("\n")
              .append(" Account Number = ").append(values.get(2)).append("\n")
              .append(" Balance Before = ").append(values.get(3)).append("\n")
              .append(" Balance After = ").append(values.get(4)).append("\n")
              .append(" Time Stamp = ").append(values.get(5)).append("\n")
              .append(" Customer ID = ").append(values.get(6)).append("\n")
              .append(" Successful = ").append(values.get(7)).append("\n\n");
        }
        System.out.println(sb.toString());
    }
    
    //Updates log.csv 
    public static void writeLogData(String transactionData) { 
    	//Assumption: transactionData is in the format: "transactionId,tableAltered,accountNum,balanceBefore,balanceAfter,timeStamp,accountId,isSuccessful"
    	//Example: "1,account balance,112345C,3425,3000,2023-01-23 14:12,1,yes"
    	//NOTE: transactionID will be randomly generated eventually
    	
    	String currentDir = Paths.get("").toAbsolutePath().toString();
    	String logPath = currentDir + "\\Assignment-1\\Data-Assignment-1\\csv\\log.csv"; //hardcoded for now
    	File file = new File(logPath);

    	try {
            FileWriter fw = new FileWriter(logPath, true);
            if(file.length() == 0) {
            	fw.write(transactionData);
            } else {
            	fw.write(System.getProperty("line.separator") + transactionData);
            }
            fw.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the csv file: " + e.getMessage());
        }
	}
 
}
