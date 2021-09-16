package org.ece.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import org.ece.io.JsonExporter;
import org.ece.model.Nurse;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;

public class UI extends JFrame {
	
	private Timer timer;

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblcurtime;
	
	private String time;
	
	DefaultListModel model = new DefaultListModel();
	private List<Nurse> nurses = new ArrayList<Nurse>();
	
	BufferedImage good;
	BufferedImage bad;
	
	JLabel goodpic;
	JLabel badpic;
	
	JPanel privacy = new JPanel();
	JPanel sanitizer = new JPanel();
	JPanel gloves = new JPanel();
	JPanel identification = new JPanel();
	
	public static boolean active = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public UI() throws IOException {
		
		good = ImageIO.read(new File("./img/good.jpeg"));
		bad = ImageIO.read(new File("./img/bad.jpeg"));
		
		goodpic = new JLabel(new ImageIcon(good));
		badpic = new JLabel(new ImageIcon(bad));
		
		setTitle("ECE103");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		privacy.setBorder(new LineBorder(Color.RED));
		privacy.setBounds(10, 30, 150, 250);
		contentPane.add(privacy);
	
		sanitizer.setBorder(new LineBorder(Color.RED));
		sanitizer.setBounds(170, 30, 150, 250);
		contentPane.add(sanitizer);
		
		gloves.setBorder(new LineBorder(Color.RED));
		gloves.setBounds(330, 30, 150, 250);
		contentPane.add(gloves);
		
		identification.setBorder(new LineBorder(Color.RED));
		identification.setBounds(490, 30, 150, 250);
		contentPane.add(identification);

		JList list = new JList(model);
		list.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Nurse) {
                    ((JLabel) renderer).setText(((Nurse) value).getName() + " : " + ((Nurse) value).getTime());
                }
                return renderer;
            }
		});
		list.setBounds(650, 30, 124, 250);
		contentPane.add(list);
		
		JLabel lblEntries = new JLabel("Name : Time (secs)");
		lblEntries.setBounds(653, 11, 131, 14);
		contentPane.add(lblEntries);
		
		JLabel lblIdentification = new JLabel("Identification");
		lblIdentification.setBounds(526, 11, 106, 14);
		contentPane.add(lblIdentification);
		
		JLabel lblGloves = new JLabel("Gloves");
		lblGloves.setBounds(380, 11, 81, 14);
		contentPane.add(lblGloves);
		
		JLabel lblSantitizer = new JLabel("Sanitizer");
		lblSantitizer.setBounds(220, 11, 81, 14);
		contentPane.add(lblSantitizer);
		
		JLabel lblPrivacy = new JLabel("Privacy");
		lblPrivacy.setBounds(60, 11, 46, 14);
		contentPane.add(lblPrivacy);
		
		JLabel lblName = new JLabel("Name (First Last)");
		lblName.setBounds(10, 302, 150, 14);
		contentPane.add(lblName);
		
		textField = new JTextField();
		textField.setBounds(159, 299, 113, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		timer = new Timer(this);
		
		JButton startButton = new JButton("Start Timer");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().equalsIgnoreCase("")){
					if(active){
						JOptionPane.showMessageDialog(new JFrame(), "You can't start the timer twice...");
						return;
					}
					n = new Nurse(textField.getText());
					timer.startTimer();
					active = true;
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "You must enter a name before starting the timer.");
				}
			}
		});
		startButton.setBounds(10, 327, 150, 23);
		contentPane.add(startButton);
		
		JButton btnStopTimer = new JButton("Stop Timer");
		btnStopTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAndReset();
			}
		});
		btnStopTimer.setBounds(170, 327, 139, 23);
		contentPane.add(btnStopTimer);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(342, 302, 46, 14);
		contentPane.add(lblTime);
		
		lblcurtime = new JLabel("");
		lblcurtime.setBounds(380, 302, 46, 14);
		contentPane.add(lblcurtime);
	}
	
	Nurse n;

	protected void addAndReset() {
		timer.stopTimer();
		n.setTime(Integer.valueOf(time));
		//nurses.add(n);
		model.addElement(n);
		JsonExporter.getInstance();
		try {
			JsonExporter.saveEntry(n);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		textField.setText("");
		lblcurtime.setText("");
		resetAllPanels();
		active = false;
		n = null;
	}

	public void update(long dT){
		time = String.valueOf((dT/1000)%1000);
		lblcurtime.setText(time);
	}

	public void togglePrivacy(boolean good){
		privacy.removeAll();
		if(good){
			privacy.add(goodpic);
			privacy.setBorder(new LineBorder(Color.GREEN));
			n.setPrivacytime(Integer.valueOf(time));
		} else {
			privacy.add(badpic);
			privacy.setBorder(new LineBorder(Color.RED));
		}
        revalidate();
        repaint();
	}
	
	public void toggleSanitizer(boolean good){
		sanitizer.removeAll();
		if(good){
			sanitizer.add(goodpic);
			sanitizer.setBorder(new LineBorder(Color.GREEN));
			n.setSanitizertime(Integer.valueOf(time));
		} else {
			sanitizer.add(badpic);
			sanitizer.setBorder(new LineBorder(Color.RED));
		}
        revalidate();
        repaint();
	}
	
	public void toggleGloves(boolean good){
		gloves.removeAll();
		if(good){
			gloves.setBorder(new LineBorder(Color.GREEN));
			gloves.add(goodpic);
			n.setGlovestime(Integer.valueOf(time));
		} else {
			gloves.setBorder(new LineBorder(Color.RED));
			gloves.add(badpic);
		}
        revalidate();
        repaint();
	}
	
	public void toggleIdentification(boolean good){
		identification.removeAll();
		if(good){
			identification.add(goodpic);
			identification.setBorder(new LineBorder(Color.GREEN));
			n.setIdentificationtime(Integer.valueOf(time));
		} else {
			identification.add(badpic);
			identification.setBorder(new LineBorder(Color.RED));
		}
        revalidate();
        repaint();
        try {
        	timer.stopTimer();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        addAndReset();
	}
	
	private void resetAllPanels(){
		privacy.setBorder(new LineBorder(Color.RED));
		privacy.removeAll();
		sanitizer.setBorder(new LineBorder(Color.RED));
		sanitizer.removeAll();
		gloves.setBorder(new LineBorder(Color.RED));
		gloves.removeAll();
		identification.setBorder(new LineBorder(Color.RED));
		identification.removeAll();
        revalidate();
        repaint();
	}
	
}
