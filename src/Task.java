public class Task {

	// For floating Task only the name is required
	public Task(String taskName) {
		setName(taskName);
	}

	private String name = "";
	private boolean isDone = false;
	private String description = "";

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean doneState) {
		isDone = doneState;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String newDescription) {
		description = newDescription;
	}

}
