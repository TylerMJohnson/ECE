package org.ece.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.ece.model.Nurse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

public class JsonExporter {

	private static JsonExporter instance;

	private JsonExporter(){

	}

	static{
		try{
			instance = new JsonExporter();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static JsonExporter getInstance(){
		return instance;
	}
	
	public static void saveEntry(Nurse nurse) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        try(Writer writer = new FileWriter("entries/" + nurse.getName())){
            Gson gson = new GsonBuilder().create();
            gson.toJson(nurse, writer);
        }
	}

}
