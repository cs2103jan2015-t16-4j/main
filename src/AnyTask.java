import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class AnyTask {

	public static final String MESSAGE_WELCOME="Welcome to AnyTask. %s is ready for use.\n";
	public static final String MESSAGE_ADD="Added to %s :\" %s \"\n";
	public static final String MESSAGE_DELETELINE="Deleted from %s :\" %s \"\n";
	public static final String MESSAGE_DISPLAY_SUCCESS="%d. %s \n";
	public static final String MESSAGE_DISPLAY_EMPTY="%s is empty \n";
	public static final String MESSAGE_DELETEALL="All content deleted from %s \n";
	public static final String MESSAGE_PROMPT="Command: ";
	public static final String MESSAGE_ERROR="ERROR: \n";
	public static final String MESSAGE_INSTRUCTIONS="Please type in the text file name after the java file name ";

	private static String fileName;
	private static boolean isCorrectFormat= false;
	
	private static Scanner sc= new Scanner(System.in);	

	public static void setFileName(String name){
    	fileName = name;
		isCorrectFormat=true;
    }
	
	public static void addNewLine(String userText){
		try{
		    BufferedWriter bWrite = new BufferedWriter(new FileWriter(fileName,true));
		    bWrite.write(userText+"\n");
		    bWrite.close();
		    displayMsgAdd(userText);
		}
		catch(IOException e){
		    System.out.printf(MESSAGE_ERROR, e.getMessage());
		}
	}
	
	public static void deleteLine(int userInt){
		String line="";
		String tmpFile;
		if(fileName!="temp.txt"){
			tmpFile="tmp.txt";
		}
		else{
			tmpFile="tmp1.txt";
		}
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile,true));
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int i=1;
			while((line=br.readLine())!=null){
				if(i!=userInt){
					bw.write(line+"\n");
				}
				else{
					displayMsgDeleteLine(line);
				}
				i++;
			}
			br.close();
			bw.close();
		}
		catch(IOException e){
		    System.out.printf(MESSAGE_ERROR, e.getMessage());
		}
		
		//remove original file and rename temp file to original name
		File oldFile = new File(fileName);
		oldFile.delete();
		File newFile = new File(tmpFile);
		newFile.renameTo(oldFile);

	}
	
	public static void displayAll(){
		String line="";
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int i=1;
			line=br.readLine();
			if (line== null) {
			    displayMsgEmpty();
			}
			else{
				displayMsgDisplay(i,line);
				i++;
				while((line=br.readLine())!=null){
					displayMsgDisplay(i,line);
					i++;
				}
			}
			br.close();
		}
		catch(IOException e){
		    System.out.printf(MESSAGE_ERROR, e.getMessage());
		}

	}
	
	public static void deleteAll(){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,false));
			bw.close();
			displayMsgDeleteAll();
		}
		catch(IOException e){
		    System.out.printf(MESSAGE_ERROR, e.getMessage());
		}
	}
	
	public static void executeUserCommand(String userCommand){
		if (userCommand.equalsIgnoreCase("add")){
			String userText=sc.nextLine();
			addNewLine(userText); 
		}
		else if (userCommand.equalsIgnoreCase("display")){
			displayAll(); 
		}
		else if (userCommand.equalsIgnoreCase("delete")){
			int userInt=sc.nextInt();
			deleteLine(userInt); 
		}
		else if (userCommand.equalsIgnoreCase("clear")){
			deleteAll(); 
		}
		else if (userCommand.equalsIgnoreCase("exit")){
			System.exit(0);
		}
		else{
			//error
			System.out.printf(MESSAGE_ERROR, userCommand);
		}
		
	}
	
	private static void displayMsgWelcome() {
		System.out.printf(MESSAGE_WELCOME, fileName);
	}

    private static void displayMsgAdd(String userText){
		System.out.printf(MESSAGE_ADD, fileName,userText);
    }
    
    private static void displayMsgDeleteLine(String line){
		System.out.printf(MESSAGE_DELETELINE, fileName, line);
    }
    
    private static void displayMsgDisplay(int i,String line){
		System.out.printf(MESSAGE_DISPLAY_SUCCESS, i, line);
    }
    
    private static void displayMsgEmpty(){
		System.out.printf(MESSAGE_DISPLAY_EMPTY, fileName);
    }
    
    private static void displayMsgDeleteAll(){
		System.out.printf(MESSAGE_DELETEALL, fileName);
    }
    
    private static void displayMsgPrompt(){
		System.out.printf(MESSAGE_PROMPT);
    }
    
    private static void displayMsgInstructions(){
		System.out.printf(MESSAGE_INSTRUCTIONS);
    }

    
	public static void main(String[] args) {
		if(args.length!=0) {
			fileName = args[0];
			isCorrectFormat=true;
			displayMsgWelcome();
		} 
		else {
			displayMsgInstructions(); 
			System.exit(0);
		}
		
		while (isCorrectFormat) {
			displayMsgPrompt();
			String command = sc.next();
			executeUserCommand(command.trim());
		}
	}
}
