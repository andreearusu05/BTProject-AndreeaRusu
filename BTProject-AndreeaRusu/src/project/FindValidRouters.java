package project;

public class FindValidRouters {

	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("Invalid number of command-line arguments. You need only one argument: path/filename.csv");
			System.exit(1);
		}
		CSVReader reader = new CSVReader(args[0]);
		reader.printValidRouters();
	}
	
}
