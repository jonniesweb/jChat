package client;

import java.awt.Color;

import javax.swing.JPanel;

import message.ID;
import message.User;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class ActiveUserList extends JPanel {
	private static JTable table;
	private JScrollPane scrollPane;
	private static DefaultTableModel model;

	/**
	 * Create the panel.
	 */
	public ActiveUserList() {
		setLayout(new MigLayout("", "[100px:100px,grow]", "[100px:100px,grow]"));

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		add(scrollPane, "cell 0 0,grow");
		setBackground(new Color(77, 77, 77));
		setForeground(new Color(180, 180, 180));

		table = new JTable(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"Userlist"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		table.setForeground(new Color(180, 180, 180));
		table.setBackground(new Color(77, 77, 77));
		
		// implement sorting of the list
		table.setAutoCreateRowSorter(true);
		
		
		model = (DefaultTableModel) table.getModel();
		
		scrollPane.setViewportView(table);
		table.setFillsViewportHeight(true);
		
	}

	public static void addUser(User user) {
		model.insertRow(model.getRowCount(), new Object[] {user});
	}

	public static void removeUser(ID id) {
		try {
			User user = UserMap.getMap().get(id);
			model.removeRow(model.findColumn(user.toString()));
		} catch (Exception e) {
		}

	}

}
