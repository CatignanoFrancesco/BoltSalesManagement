/**
 * @author Francolino Flavio Domenico
 * 
 * questa classe implementa il JFrame principale delle gui
 * e ha il metodo partente del software
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import databaseSQL.exception.DatabaseSQLException;

public class MainWindow extends JFrame {

	static private final int MIN_WIDTH_WINDOW = 1000;
	static private final int MIN_HEIGHT_WINDOW = 700;

	static private Dimension minDimensione = new Dimension(MIN_WIDTH_WINDOW, MIN_HEIGHT_WINDOW);
	static private GridBagConstraints gbc = new GridBagConstraints();

	static private ScreenManager screen;

	/**
	 * costruttore che inizilizza valori quali layout e dimensione delle finestra
	 * corrente
	 * @throws SQLException 
	 * @throws DatabaseSQLException 
	 */
	private MainWindow() throws DatabaseSQLException, SQLException {

		setTitle("bulloni");

		this.setMinimumSize(minDimensione);
		this.setPreferredSize(minDimensione);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridBagLayout());

		screen = new ScreenManager(this);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.getContentPane().add(screen, gbc);

		this.revalidate();
		this.repaint();

		this.setVisible(true);

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MainWindow window = null;

		try {
			 window = new MainWindow();
			 
		} catch (DatabaseSQLException | SQLException e) {
			
			JOptionPane.showMessageDialog(window, e.getMessage(), "exception", JOptionPane.ERROR_MESSAGE);
		}

	}

}
