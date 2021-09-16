package org.ece.model;

public class Nurse {

	private String name;

	private String patientID;

	private int time;

	private boolean privacy;

	private boolean sanitizer;

	private boolean gloves;

	private boolean identification;
	
	private int privacytime;
	
	private int sanitizertime;
	
	private int glovestime;

	private int identificationtime;
	
	public Nurse(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	public boolean isSanitizer() {
		return sanitizer;
	}

	public void setSanitizer(boolean sanitizer) {
		this.sanitizer = sanitizer;
	}

	public boolean isGloves() {
		return gloves;
	}

	public void setGloves(boolean gloves) {
		this.gloves = gloves;
	}

	public boolean isIdentification() {
		return identification;
	}

	public void setIdentification(boolean identification) {
		this.identification = identification;
	}

	public int getPrivacytime() {
		return privacytime;
	}
	

	public void setPrivacytime(int privacytime) {
		this.privacytime = privacytime;
	}
	

	public int getSanitizertime() {
		return sanitizertime;
	}
	

	public void setSanitizertime(int sanitizertime) {
		this.sanitizertime = sanitizertime;
	}
	

	public int getGlovestime() {
		return glovestime;
	}
	

	public void setGlovestime(int glovestime) {
		this.glovestime = glovestime;
	}
	

	public int getIdentificationtime() {
		return identificationtime;
	}
	

	public void setIdentificationtime(int identificationtime) {
		this.identificationtime = identificationtime;
	}
	
	
}
