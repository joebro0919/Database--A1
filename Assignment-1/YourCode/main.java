import java.util.Scanner;
import java.util.HashMap;

public class main {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		String userDirectory = System.getProperty("user.dir");
		System.out.println(userDirectory);
		System.out.println("Please enter the folder path of the CSV files:");
		String CSVFolder = scanner.nextLine();
//		print("First Output:")
		getContent(CSVFolder);
//	    print("Print current status of Log Sub-system\n\n")
//		sample:	updateLog("112345S","98765","99190","1","success"); 
		displayLog(CSVFolder);
		while(true) {
			System.out.println("Please enter the number associated with which transaction you wish to see: \n1) Successful Transaction\n2) Failed Transaction");
			try {
			int userChoice = scanner.nextInt();
			if(userChoice == 1) {
				//first choice code:
				System.out.println("TRANSACTION 1:\n");
				transaction1(CSVFolder);
				break;
			}
			else if(userChoice == 2) {
				//second choice code
				System.out.println("TRANSACTION 2:\n");
				transaction2(CSVFolder);
				break;
			}
			else {
				 System.err.println("Invalid input, please try again");
			}
		} catch(Exception e){
			System.err.println("Invalid input, please try again");
			scanner.next();
		}
	}
	
		
	}
	
	public static void transaction1(String CSVFolder) {
		//get Emma's account details
		//Emma will be moving $100,000 from her checking account (312345C) to her Savings Account (312345S).
		readData readDataObject = new readData(CSVFolder);
		HashMap accBalData = readData.getAccountBalance();
		Integer valueChecking = readDataObject.getAccountBalance("312345c");
		Integer valueSavings = readDataObject.getAccountBalance("312345s");
		Integer newCheckingValue = valueChecking - 100000;
		readDataObject.updateAccountBalance("312345c", newCheckingValue);
		updateLog("312345c","" + valueChecking,""+newCheckingValue,"" + 3,"success"); 
		accBalData.put("312345c", newCheckingValue);
		Integer newSavingsValue = valueSavings + 100000;
		readDataObject.updateAccountBalance("312345s", newSavingsValue);
		updateLog("312345s","" + valueSavings,""+newSavingsValue,"" + 3,"success");
		accBalData.put("312345s", newSavingsValue);
		//Writes updated data to file
		writeData writeDataObject = new writeData(accBalData);
		//PRINT UPDATED VALUE
		displayLog(CSVFolder);
		getContent(CSVFolder);
	}

	public static void transaction2(String CSVFolder) {
		LogSystem logSystem = new LogSystem(CSVFolder);
		//get Emma's account details
		//Emma will be moving $100,000 from her checking account (312345C) to her Savings Account (312345S).
		readData readDataObject = new readData(CSVFolder);
		HashMap accBalData = readData.getAccountBalance();
		Integer valueChecking = readDataObject.getAccountBalance("312345c");
		Integer valueSavings = readDataObject.getAccountBalance("312345s");
		Integer newCheckingValue = valueChecking - 100000;
		readDataObject.updateAccountBalance("312345c", newCheckingValue);
		updateLog("312345c","" + valueChecking,""+newCheckingValue,"" + 3,"fail");
		writeData writeDataObject = new writeData(accBalData);
		System.err.println("Transaction Failed, rolling back log\n");
		System.out.println("Database Contents While in Bad State:");
		getContent(CSVFolder);
		//FAIL, ROLLBACK
		logSystem.rollback(accBalData, "312345c");
		writeData writeDataObjectRollback = new writeData(accBalData);
		//PRINT UPDATED VALUE
		displayLog(CSVFolder);
		System.out.println("Database Contents After Rollback to Good State: ");
		getContent(CSVFolder);
	}
	
	public static void getContent(String CSVFolder) {
		readData readDataObject = new readData(CSVFolder);
		try {
			System.out.println("\nThe Contents of Database are:");
			readDataObject.read();
			
		} catch (Exception e) {
			System.err.println(e);
			try {
				//This is not elegant but it was printing getContent before the exception. Unable to find root cause.
				Thread.sleep(100);
				getContent(CSVFolder);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void displayLog(String CSVFolder) {

		LogSystem logSystem = new LogSystem(CSVFolder);
		try {
			System.out.println("\nCurrent Log System Status:");
			logSystem.displayLogContent();
			
		} catch (Exception e) {
			System.err.println(e);
			try {
				Thread.sleep(100);
				displayLog(CSVFolder);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void updateLog(String accountNum, String oldBalance, String newBalance, String customerId, String status) {
		//Note: status parameter is whether the transaction succeeded or not. Options: "Success" or"Failure"

		try {
			System.out.println("\nUpdating Log System...");
			LogSystem.writeLogData(accountNum, oldBalance, newBalance, customerId, status);
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	

}
