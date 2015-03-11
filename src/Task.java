public class Task {
	
	// For floating Task only the name is required
	public Task(String taskName){
		setName(taskName);
	}
	
	private String name="";
	
	public String getName(){
		return name;
	}
	public void setName(String newName){
		name=newName;
	}
	
	
}
