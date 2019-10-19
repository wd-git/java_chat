import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class AddFriendM extends JFrame implements Protocol {
	private JLabel lblMsg;
	private JTextArea taMsg;
	private JButton btnOk;
	private Client client;
	private JButton btnCancel;
	private String targetId;

	/*
	 * ����� ����
	 */

	public AddFriendM(Client client, String targetId) {
		this.targetId = targetId;
		this.client = client;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	/*
	 * ����� ����
	 */

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		lblMsg = new JLabel(targetId + "�Բ� ���� ��û�޽����� �Է��� �ּ���");
		taMsg = new JTextArea(3, 30);
		btnOk = new JButton("����");
		btnCancel = new JButton("���");

		taMsg.setLineWrap(true);
	}

	private void setDisplay() {
		JPanel pnllbl = new JPanel();
		pnllbl.add(lblMsg);

		JPanel pnlta = new JPanel();
		JScrollPane scroll = new JScrollPane(taMsg,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnlta.add(scroll);

		JPanel pnlbtn = new JPanel();
		pnlbtn.add(btnOk);
		pnlbtn.add(btnCancel);

		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(pnllbl, BorderLayout.NORTH);
		pnlCenter.add(pnlta, BorderLayout.CENTER);
		pnlCenter.add(pnlbtn, BorderLayout.SOUTH);

		add(pnlCenter, BorderLayout.CENTER);
	}

	/*
	 * ����� ����
	 */

	private void addListeners() {

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

	
				String myId = client.getMyId();
				String msg = taMsg.getText();
				Object[] data = { targetId, myId, msg };
				client.sendData(new SendData(ADD_FRIEND, data));
				dispose();

			}
		});

	}

	private void showFrame() {
		setTitle("ģ�� ��� ��û");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
