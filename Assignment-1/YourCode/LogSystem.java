import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
                    		System.out.println("The log is currently empty. \n");
                    	}
                    }
            }
        } else {
            throw new Exception("Directory doesn't contain any file contents.");
        }
	}

    /*
     * Function: void rollback(HashMap accBalData, String accountNumber)
     * 
     * Purpose:
     * Given the account balance data structure and accountNumber, rollsback the previous
     * operation found in the transaction log. The account balance data structure is updated
     * to the "before" value of the most recent operation found in the Log System
     * 
     */
    public void rollback(HashMap accBalData, String accountNumber){
        try{
            BufferedReader br = new BufferedReader(new FileReader(csvFolderPath + "\\log.csv"));

            Integer beforeFailure;
            String lastLine = "", currLine;
            String[] rollbackLine;

            while ((currLine = br.readLine()) != null) {   
                lastLine = currLine;
            }
            System.out.println(lastLine);
            rollbackLine = lastLine.split(",");

            //need to revert to this value
            beforeFailure = Integer.parseInt(rollbackLine[3]);
            //reverts bad changes to database data struct
            accBalData.put(accountNumber, beforeFailure);

            br.close();
        }catch(IOException e){
            e.printStackTrace();
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
              .append(" Status = ").append(values.get(7)).append("\n\n");
        }
        System.out.println(sb.toString());
    }
    
    //Updates log.csv 
    public static void writeLogData(String accountNum, String oldBalance, String newBalance, String customerId, String status) throws FileNotFoundException { 
    	
    	//grab path to log.csv
    	String currentDir = Paths.get("").toAbsolutePath().toString();
        //System.out.println(currentDir); testing
    	String logPath = currentDir + "\\Data-Assignment-1\\csv\\log.csv"; 
    	File file = new File(logPath);
        //System.out.println(logPath); testing
    	
    	//grab transaction ID
    	String transId = Integer.toString(incrementTransId(logPath));
    	
    	String transactionData = formatTransaction(transId, accountNum, oldBalance, newBalance, customerId, status);
		
		try {
			FileWriter fw = new FileWriter(logPath, true);
			if (file.length() == 0) {
				fw.write(transactionData);
			} else {
				fw.write(System.getProperty("line.separator") + transactionData);
			}
			fw.close();
		} catch (IOException e) {
			System.err.println("An error occurred while writing to the csv file: " + e.getMessage());
		}
		 
	}
    
    private static Integer incrementTransId(String fileName) throws FileNotFoundException {
    	BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String input;
        int count = 0;
        try {
			while((input = bufferedReader.readLine()) != null)
			{
			    count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if (count == 0) {
        	return 1;
        } else {
        	return count + 1;
        }

    }
    
    private static String formatTransaction(String transId, String accountNum, String oldBalance, String newBalance, String customerId, String status) {
    	
    	//table altered column
    	String TABLE_ALTERED = "Account Balance"; 
    	
    	//get today's date and time
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	Date date = new Date();
    	String currentDateTime = dateFormat.format(date);

    	//put it all together
    	StringBuilder sb = new StringBuilder();
    	sb.append(transId + ",")
    	.append(TABLE_ALTERED + ",")
    	.append(accountNum + ",")
    	.append(oldBalance + ",")
    	.append(newBalance + ",")
    	.append(currentDateTime + ",")
    	.append(customerId + ",")
    	.append(status);

    	return sb.toString();
    }

    
 
}
