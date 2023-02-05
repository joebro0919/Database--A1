import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

//Reads data from csv files into Hashtable
public class readData {
    public static void main(String[] args) throws Exception {

        //Enter path to csv folder
        String csvFolderPath = "";
        
        File dir = new File(csvFolderPath);
        File[] dirFiles = dir.listFiles();

        //Checks if directory of files exists then loops through all files
        if (dirFiles != null) {
            for (File child : dirFiles) {
                    String dataset = child.getName();
                    //Checks which file the system is on
                    if (dataset.equals("customer.csv")){
                        HashMap customer = customerData(child);
                        System.out.println(customer);
                    }
                    else if (dataset.equals("account.csv")){
                        HashMap account = accountData(child);
                        System.out.println(account);
                    }
                    else if (dataset.equals("account-balance.csv")){
                        HashMap accountBal = accountBalData(child);
                        System.out.println(accountBal);
                    }
            }
        } else {
            System.err.println("Directory doesn't contain any file contents.");
        }
    }

    //Get customer.csv data
    static HashMap customerData(File child){
        String splitDataBy = ",";
        String line = "";
        BufferedReader br = null;

        HashMap<Integer, List<String>> customer = new HashMap<Integer, List<String>>();
        
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
                customer.put(Integer.parseInt(dataRow[0]), data);
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
        return customer;
    }

    //Get account.csv data
    static HashMap accountData(File child){
        String splitDataBy = ",";
        String line = "";
        BufferedReader br = null;

        HashMap<Integer, List<String>> account = new HashMap<Integer, List<String>>();
        
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
                account.put(Integer.parseInt(dataRow[0]), data);
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

        return account;

    }
    
    //Get account-balance.csv data
    static HashMap accountBalData(File child){
        String splitDataBy = ",";
        String line = "";
        BufferedReader br = null;

        HashMap<String, Integer> accountBal = new HashMap<String, Integer>();
        
        //Try-catch-finally block to read data into a HashMap
        //KEY = AccountNum
        try{
            br = new BufferedReader(new FileReader(child));
            while((line = br.readLine()) != null){
        
                String[] dataRow = line.split(splitDataBy);
                
                accountBal.put(dataRow[0], Integer.parseInt(dataRow[1]));
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

        return accountBal;
    }
}
