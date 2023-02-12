import java.util.Scanner;

public class main {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Please enter the folder path of the CSV files:");
		String CSVFolder = scanner.nextLine();
		
//		print("First Output:")
		getContent(CSVFolder);
//	    print("Print current status of Log Sub-system\n\n")
//		sample:	updateLog("112345S","98765","99190","1","success"); 
		updateLog("112345S","98765","99190","1","success"); 
		displayLog(CSVFolder);
		while(true) {
			System.out.println("Please enter the number associated with which transaction you wish to see: \n1) Successfuly Transaction\n2) Failed Transaction");
			try {
			int userChoice = scanner.nextInt();
			if(userChoice == 1) {
				//first choice code:
				System.out.println("TRANSACTION 1:\n");
				break;
			}
			else if(userChoice == 2) {
				//second choice code
				System.out.println("TRANSACTION 2:\n");
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
