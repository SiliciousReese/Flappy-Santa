package flappySanta.userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import flappySanta.FlappySanta;
import flappySanta.northPoleMechanics.ElvishLiaison;

/* TODO Serialize? */
@SuppressWarnings("serial")
public class SantaMenu extends JMenuBar implements ActionListener {
	private static final ElvishLiaison MECHANICS = ElvishLiaison.getInstance();

	private JMenu main;
	private JMenuItem exit;
	private JMenuItem restart;

	/** A typical menu bar for a game. */
	public SantaMenu() {
		main = new JMenu("Main");

		exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		main.add(exit);

		restart = new JMenuItem("Restart");
		restart.setActionCommand("restart");
		restart.addActionListener(this);
		main.add(restart);

		add(main);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* TODO Refactor, this code is the same as the new game dialog. */
		if (e.getActionCommand().equals("exit"))
			FlappySanta.exit(false);
		else if (e.getActionCommand().equals("restart"))
			MECHANICS.noahsFlood();
	}
}
