package client;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class test extends JApplet implements KeyListener {
	public test() {
		getContentPane().setLayout(new MigLayout("", "[]", "[]"));
		
		JLabel lblTest = new JLabel("test");
		getContentPane().add(lblTest, "cell 0 0");
	}
	
	@Override
	public void init() {
		new test();
		
	}
	
	public void main(String[] arg0) {
		
		new test();
		
	}
	
	@Override
	public void paint(Graphics g) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
