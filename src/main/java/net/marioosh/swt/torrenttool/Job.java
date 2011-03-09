package net.marioosh.swt.torrenttool;

import java.io.Serializable;

public class Job implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String password;
	private String prefix;
	private String fileTwo;
	private String name;
	private boolean enabled;
	
	public void printAll() {
		System.out.println(name + ":" + fileTwo + "," + password + "," + prefix + "," + enabled);
	}
	
	public Job(String password, String prefix, String fileTwo) {
		super();
		this.password = password;
		this.prefix = prefix;
		this.fileTwo = fileTwo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFileTwo() {
		return fileTwo;
	}

	public void setFileTwo(String fileTwo) {
		this.fileTwo = fileTwo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
