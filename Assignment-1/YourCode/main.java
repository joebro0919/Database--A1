import java.util.Scanner;

public class main {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Please enter the folder path of the CSV files:");
		String CSVFolder = scanner.nextLine();
//		print("First Output:")
		getContent(CSVFolder);
		displayLog(CSVFolder);
//	    print("Print current status of Log Sub-system\n\n")
	}
	
	public static void getContent(String CSVFolder) {
		readData readDataObject = new readData(CSVFolder);
		try {
			System.out.println("\nOriginal Contents of Database are:\n");
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
			System.out.println("\nCurrent Log System Status:\n");
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

}
