import java.util.Scanner;

public class main {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Please enter the folder path of the CSV files:");
		String CSVFolder = scanner.nextLine();
		
//		print("First Output:")
		getContent(CSVFolder);
//	    print("Print current status of Log Sub-system\n\n")
//		updateLog("3,account balance,111111S,11111,2323,2023-01-23 14:13,1,no"); sample
		displayLog(CSVFolder);
	}
	
	public static void getContent(String CSVFolder) {
		readData readDataObject = new readData(CSVFolder);
		try {
			System.out.println("\nOriginal Contents of Database are:");
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
	
	public static void updateLog(String transactionData) {
		//assumption: transactionData is in the format: "transactionId,tableAltered,accountNum,balanceBefore,balanceAfter,timeStamp,accountId,isSuccessful"
		//example: "1,account balance,112345C,3425,3000,2023-01-23 14:12,1,yes"
		
		try {
			System.out.println("\nUpdating Log System...");
			LogSystem.writeLogData(transactionData);
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
