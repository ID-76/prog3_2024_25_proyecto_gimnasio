package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class VentanaHilo extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar pb;
	private JLabel label;
	private JFrame vActual;
	public VentanaHilo() {
		super();
		setBounds(300, 200, 300, 100);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		vActual = this;
		pb = new JProgressBar(0, 100);
		label = new JLabel("Cargando...");
		add(pb, BorderLayout.CENTER);
		add(label, BorderLayout.SOUTH);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<=100;i++) {
					pb.setValue(i);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				vActual.dispose();
			}
		}).start();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VentanaHilo();
	}

}

