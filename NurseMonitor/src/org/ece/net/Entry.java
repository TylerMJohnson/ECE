package org.ece.net;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.ece.ui.UI;

public class Entry {
	
	private static UI ui;
	
	public static UI getUI(){
		return ui;
	}
	
	public static String ip = null;

	public static void main(String[] args) {
		
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config");

			// load a properties file
			prop.load(input);
			
			// get the property value and print it out
			ip = prop.getProperty("ip");
			System.out.println(prop.getProperty("ip"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Runnable server = new Runnable() {
			@Override
			public void run() {
		        ServerTest server = new ServerTest(ip, 1337);
		        server.listen();
			}
		};
		
		new Thread(server).start();

/*		Runnable client = new Runnable() {
			@Override
			public void run() {
		        ClientTest client = new ClientTest("localhost", 1337);
		        try {
					client.connect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        client.send("Hello server");
		        String receivedMsg = null;
				try {
					receivedMsg = client.receive();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println(receivedMsg);
		        try {
					client.disconnect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		new Thread(server).start();
		new Thread(client, "client-A").start();
		new Thread(client, "client-B").start();*/

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ui = new UI();
					ui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
