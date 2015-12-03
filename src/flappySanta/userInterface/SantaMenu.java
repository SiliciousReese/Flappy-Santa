package flappySanta.userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import flappySanta.FlappySanta;

@SuppressWarnings("serial")
public class SantaMenu extends JMenuBar implements ActionListener {
	private JMenu main;

	private JMenuItem exit;

	public SantaMenu() {
		main = new JMenu("Main");

		exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		main.add(exit);

		add(main);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("exit"))
			FlappySanta.exit(false);
	}
}
