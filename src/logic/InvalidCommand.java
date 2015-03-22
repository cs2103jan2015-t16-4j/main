package logic;

public class InvalidCommand extends Command{
	private String userCommand;
	public InvalidCommand(String userCommand){
		this.userCommand = userCommand;
	}
	
	public void execute(){
		System.out.println("Command invalid: "+userCommand);
	}
}
