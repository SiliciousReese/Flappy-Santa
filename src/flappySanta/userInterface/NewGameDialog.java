package flappySanta.userInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import flappySanta.FlappySanta;
import flappySanta.northPolePhysics.PhysicsManager;

@SuppressWarnings("serial")
public class NewGameDialog extends JDialog implements ActionListener {
	private JTextArea text;

	private JButton exit;
	private JButton newGame;

	public NewGameDialog(JFrame owner) {
		super(owner, "New Game");
		JPanel buttonPanel = new JPanel();

		/* Set text to display. */
		text = new JTextArea("New Game?");
		text.setEditable(false);
		buttonPanel.add(text);

		/* Create buttons. */
		exit = new JButton("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		buttonPanel.add(exit);

		newGame = new JButton("New Game");
		newGame.setActionCommand("new");
		newGame.addActionListener(this);
		buttonPanel.add(newGame);

		setMinimumSize(new Dimension(100, 100));

		getContentPane().add(buttonPanel);
		pack();
		setVisible(true);
	}

	@Override
	/** Buttons pressed. */
	public void actionPerformed(ActionEvent e) {
		/* Exit if exit, restart if restart. */
		if (e.getActionCommand().equals("exit"))
			FlappySanta.exit(false);
		else if (e.getActionCommand().equals("new"))
			PhysicsManager.getInstance().noahsFlood();
	}
}
