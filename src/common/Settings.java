package common;

import java.io.File;

public class Settings {
	private String filename;
	private String directory;
	private static final String FILE_TYPE = ".txt";
	private static final String DEFAULT_FILENAME = "anytasklist.txt";

	public Settings() {	
		this.setDefault();
	}
	
	public void setDefault() {
		this.filename=DEFAULT_FILENAME;
		this.directory=System.getProperty("user.dir");
		
	}
	
	public String getFilepath() {
		File filepath = new File(directory,filename);
		return(filepath.getAbsolutePath());
	}
	
	public void setFilepath(String path) {
		if (path.endsWith(FILE_TYPE)) {
            if (path.contains("\\")||path.contains("/")) {
            	File filepath = new File(path);
            	this.filename = filepath.getName();
            	this.directory = "/"+filepath.getParent();
            } else {
            	File filepath = new File(path);
            	this.filename = filepath.getName();
            	this.directory = "/";
            }
        } else{
			this.filename=DEFAULT_FILENAME; 
			if (path.startsWith(File.separator)) {
				this.directory=path;
			} else {
				this.directory = "/"+path;
			}
			this.filename=DEFAULT_FILENAME;
        }
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String userText) {
		filename = userText;
	}
	
	public String getDirectory() {
		return directory;
	}	
	
	public void setDirectory(String userText) {
		directory = userText;
	}
	
	
	
	
}
