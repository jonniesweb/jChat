package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class About extends JFrame {

	private JPanel contentPane;
	private JLabel lblAbout;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JButton btnNewButton;
	private StyledDocument document = new DefaultStyledDocument();
	private static StringBuilder builder = new StringBuilder();
	private SimpleAttributeSet title = new SimpleAttributeSet();
	private SimpleAttributeSet name = new SimpleAttributeSet();
	private String blank = "\n\n\n\n\n\n";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public About() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		lblAbout = new JLabel("About");
		lblAbout.setFont(new Font("Dialog", Font.BOLD, 24));
		lblAbout.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAbout, BorderLayout.NORTH);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textPane = new JTextPane(document);
		scrollPane.setViewportView(textPane);


		playCredits();
	}

	private void playCredits() {

		StyleConstants.setForeground(title, Color.black);
		StyleConstants.setUnderline(title, true);  
		StyleConstants.setFontFamily(title, "lucida bright italic");  
		StyleConstants.setFontSize(title, 24); 
		StyleConstants.setAlignment(title, StyleConstants.ALIGN_CENTER);  

		StyleConstants.setAlignment(name, StyleConstants.ALIGN_CENTER);  
		StyleConstants.setForeground(name, Color.black);
		StyleConstants.setFontFamily(name, "lucida typewriter bold");  
		StyleConstants.setFontSize(name, 18);  

		document = textPane.getStyledDocument();  
		document.setCharacterAttributes(105, document.getLength()-105, title, false);  
		document.setParagraphAttributes(0, 104, name, false);  
		
		pt("Programmed By");
		pt("Designed By");
		pt("Chief Director");
		pt("Organizer of objects");
		pt("Official Juggler");
		pt("Catering");
		pt("Late-Night Specialist");
		pt("Slacking Operations");
		pt("Funding");
		pt("Fasting Consultant");
		pt("Couch Potato");
		pt("Hipster-In-Large");
		pt("Makeup Artist");
		pt("Database Procrastinator");
		pt("Late-Night Pretzel Server");
		pt("Head of Bass");
		pt("Right-Hand Man");
		pt("Specialists of Specialists");
		pt("Zombie Apocalypse Preacher");
		pt("Forever-Going-To-Miss-Byers");
		
		
		
		


	}
	
	private void printName() {
		try {
			document.insertString(document.getLength(), "Jonnie Simpson" + blank, name);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void pt(String str) {
		try {
			document.insertString(document.getLength(), str + "\n", title);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printName();
	}
}
