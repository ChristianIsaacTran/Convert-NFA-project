import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

//
class automata{ //Automata object with attributes
	static ArrayList<String> symbols = new ArrayList<String>(); //Arraylist of symbols
	
	static ArrayList<String> states = new ArrayList<String>(); //Arraylist of given states
	
	static String startState; //Start State
	
	static String finalState; //End State
	
	static ArrayList<String> transitionRelations = new ArrayList<String>(); //ArrayList of transition cases between states
	
	//Sets symbols and adds them to the list while removing the extra unnecessary elements (parentheses, commas, etc.)
	public static void setSymbols(String givenString) {
		String[] tempStringArr;
		tempStringArr = givenString.split(",");
		
		for(int i = 0; i < tempStringArr.length; i++) {
			tempStringArr[i] = tempStringArr[i].replace("(", "");
			tempStringArr[i] = tempStringArr[i].replace(")", "");
			symbols.add(tempStringArr[i]);
		}
		
		
		
 	}
	
	//Sets states and adds them to the list while removing the extra unnecessary elements (parentheses, commas, etc.)
	public static void setStates(String givenString) {
		String[] tempStringArr;
		tempStringArr = givenString.split(",");
		
		for(int i = 0; i < tempStringArr.length; i++) {
			tempStringArr[i] = tempStringArr[i].replace("(", "");
			tempStringArr[i] = tempStringArr[i].replace(")", "");
			states.add(tempStringArr[i]);
		}
	
	
	}
	
	//Sets start state but removes any other unnecessary elements (parentheses, commas, etc.)
	public static void setStart(String givenString) {
		for(int i = 0; i < givenString.length(); i++) {
			givenString = givenString.replace("(", "");
			givenString = givenString.replace(")", "");
			givenString = givenString.replace(",", "");
		}
		
		startState = givenString; //Assigns string after removing elements
	}
	
	//Sets final state but removes any other unnecessary elements (parentheses, commas, etc.)
	public static void setfinalState(String givenString) {
		for(int i = 0; i < givenString.length(); i++) {
			givenString = givenString.replace("(", "");
			givenString = givenString.replace(")", "");
			givenString = givenString.replace(",", "");
		}
		
		finalState = givenString; //Assigns string after removing elements
	}
	
	//Used to set the transitionRelation Arraylist with the given tuple state transitions, but it also removes the parentheses on the outside as well. Keeps the commas to keep them separated
	public static void setTransition(String givenString) {
		String[] tempStringArr; //Temporary string array to fit split string into 
		tempStringArr = givenString.split("\\),"); //Splits the given string into parts separated by ")," closing parentheses and a comma
		for(int i = 0; i < tempStringArr.length; i++) { //Goes through each string in the tempStringArr (array) and removes unneeded parentheses
			if(tempStringArr[i].indexOf("(") != -1 || tempStringArr[i].indexOf(")") == -1) {
				tempStringArr[i] = tempStringArr[i].replace("(","");
				tempStringArr[i] = tempStringArr[i].replace(")","");
			}
			
			transitionRelations.add(tempStringArr[i]); //After removing all parentheses, adds the state transition string back into the transitionRelation arrayList
		}
	}
	
	//Used to display all elements within the given array **USED MAINLY FOR DISPLAYING AND FOR TESTING PURPOSES ONLY, NOT INTEGRAL TO ACTUAL CODE**
	public static void displayArray(String[] givenArray) {
		System.out.println("--Printing Array--");
		for(int i = 0; i<givenArray.length; i++) {
			System.out.println(givenArray[i]);
		}
	}
	
}





public class runNFA {

	public static void main(String[] args) {
		//Try and catch block to detect if the given input file created even EXISTS in the first place, returns an error message if there isn't a file detected
		try {
			Scanner userInputFileSelection = new Scanner(System.in);//User input scanner
			System.out.println("Please input the file name: ");//Asks the user to input the file name (I made it so that the user doesn't have to put .txt at the end)
			
			File input_file_1 = new File(userInputFileSelection.nextLine()+".txt"); //Creates a file object for input text file (.txt file)
			Scanner fileReader1 = new Scanner(input_file_1); //Scanner used to read each individual line in the .txt file
			ArrayList<String> list1 = new ArrayList<String>(); //Array List used to store the given tuple NFA and read it
			automata NFA = new automata(); //automata object created with attributes
			System.out.println("File found successfully"); //Simple file found message
			
			while(fileReader1.hasNextLine()) { //While file still has elements
				String readLine = fileReader1.nextLine(); //Individual String variable for each line in the input file
				readLine = readLine.replace(" ", ""); //Replace used to get rid of whitespace in the input file 
				list1.add(readLine); //Adds the tuple to list1 line by line
			}
			killParen(list1); //Removes the initial parentheses from read tuple
			displayArrayList(list1); //Displays arraylist
			
			//Setting attribute automata object variables
			NFA.setTransition(list1.get(4)); //gets and sets the automata object's transition relation list attribute
			NFA.setSymbols(list1.get(0)); //sets the symbol arraylist with given symbols from the tuple
			NFA.setStates(list1.get(1)); //sets the states arraylist with given states from the tuple
			NFA.setStart(list1.get(2)); //sets the start state
			NFA.setfinalState(list1.get(3)); //sets the final state
			
			System.out.println("--Symbols--");
			displayArrayList(NFA.symbols); //displays the automata's symbols
			System.out.println("--States--");
			displayArrayList(NFA.states); //displays the automata's states
			System.out.println("--State Transitions/Relations--");
			displayArrayList(NFA.transitionRelations); //displays the automata's transition relations
			System.out.println("--Start State--");
			System.out.println("Start State: "+NFA.startState); //displays the automata's start state
			System.out.println("--Final State--");
			System.out.println("Final State: "+NFA.finalState); //displays the automata's final state
			
			runNFA(list1.get(5), NFA); //Runs the NFA with the given input at the end of the tuple
			
			
		}catch(FileNotFoundException e) { //If file is not found or doesn't exist, print error. Otherwise, do try statement
			System.out.println("Error: File not found");
		}
	}
	
	
	
	//Used to display all elements in the given ArrayList with a simple for loop 
	public static void displayArrayList(ArrayList<String> givenArrayList) {
		for(int i = 0; i < givenArrayList.size(); i++) {
			System.out.println(i+" "+givenArrayList.get(i));
		}
		
		
	}
	

	//Removes the unnecessary parentheses and spaces in the ArrayList, and condenses the list down to it's needed components
	public static void killParen(ArrayList<String> givenArrayList) {
		//parentheses cases
		String case1 = "("; 
		String case2 = ")";
		String case3 = "),";
		
		//If the array list has any parentheses cases in them, then replace them with an empty string
		for(int i = 0; i < givenArrayList.size(); i++) {
			if(givenArrayList.get(i).equals(case1) || givenArrayList.get(i).equals(case2) || givenArrayList.get(i).equals(case3) ) {
				givenArrayList.set(i, "");
			}
		}
		
		givenArrayList.remove(0); //Removes the first element since the first element is always going to be a opening parentheses
		
		//If the array list has any empty elements in the list, remove them and re-order the list
		for(int j = 0; j < givenArrayList.size(); j++) {
			if(givenArrayList.get(j).equals("") || givenArrayList.get(j).equals(" ")) {
				givenArrayList.remove(j);
			}
		}
	}
	
	//Used to run the NFA based off of the given tuple and if there is any input.
	/*2 cases: 
	 *  - if the last element in the tuple is empty, ask the user for input strings to pass through the automata.
	 *  - if the last element in the tuple contains elements, proceed
	 * 
	 *After obtaining the input strings, tell whether each input string is accepted or rejected by the automata.
	 */
	public static void runNFA(String givenString, automata autoObject){
		//Replace is used to get rid of unnecessary parentheses
		givenString = givenString.replace("(", ""); 
		givenString = givenString.replace(")", "");
		
		if(givenString.isEmpty()) { //if there is NOTHING in the input tuple, ask user for string inputs for NFA and tell whether inputs are accepted or rejected by NFA
			System.out.println("Type end to exit program. Please input a string: ");
			while(true) {
				Scanner userInput = new Scanner(System.in);
				String temp = userInput.nextLine();
				if(!(temp.toLowerCase().equals("end"))) {
					System.out.println(acceptOrReject(temp,autoObject));
				}
				else {
					System.exit(0);
				}
				
				System.out.println("Please input a string: ");
				
				
				
				
			}
			
		}
		else { //if there IS input strings in the input tuple, proceed and tell whether the inputs are accepted or rejected by NFA
			String[] tempStrArr;
			
			tempStrArr = givenString.split(",");
			
			
			for(int i = 0; i < tempStrArr.length; i++) {
				System.out.println("Input given: "+tempStrArr[i]);
				System.out.println(acceptOrReject(tempStrArr[i], autoObject));
				
			}
			
			}
			//System.out.println(acceptOrReject(,autoObject));
		}
		
	

	
	//Tells whether the NFA accepts or rejects the given input
	public static String acceptOrReject(String givenString, automata autoObject) {
		String first = automata.transitionRelations.get(automata.transitionRelations.size()-2);
		String second = automata.transitionRelations.get(automata.transitionRelations.size()-1);
		String pattern = first.substring(3, 4) + second.substring(3,4);
		
		if(givenString.substring(givenString.length()-2).equals(pattern)) {
			return "Accepted";
		}
		else {
			return "Rejected";
		}
		
	}
	
	
	public static void displayArray(String[] givenArray) {
		System.out.println("--Printing Array--");
		for(int i = 0; i<givenArray.length; i++) {
			System.out.println(givenArray[i]);
		}
	}
	
	
}




