import java.util.Scanner;

public class main {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
//		print("First Output:")
		getContent();
//	    print("Print current status of Log Sub-system\n\n")
		
	}
	
	public static void getContent() {
		System.out.println("Please enter the folder path of the CSV files:");
		String CSVFolder = scanner.nextLine();
//	    print("Print Original Contents of Databases")
		readData readDataObject = new readData(CSVFolder);
		try {
			System.out.println("Original Contents of Database are:");
			readDataObject.read();
		} catch (Exception e) {
			System.err.println(e);
			try {
				//This is not elegant but it was printing getContent before the exception. Unable to find root cause.
				Thread.sleep(100);
				getContent();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

}
