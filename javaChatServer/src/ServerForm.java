import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ServerForm extends JFrame implements Protocol {
	private JLabel lblAllUser;
	private JLabel lblWaitingUser;
	private JLabel lblChatUser;
	private JLabel lblAllChatRoom;
	private JLabel lblOpenChatRoom;
	private JLabel lblPrivateChatRoom;
	private JLabel lblAllMember;
	private JTextField tfAllUser;
	private JTextField tfWaitingUser;
	private JTextField tfChatUser;
	private JTextField tfAllChatRoom;
	private JTextField tfOpenChatRoom;
	private JTextField tfPrivateChatRoom;
	private JTextField tfAllMember;

	private JTable AllUserInfoTable;
	private Vector<User> connectedUserList;

	private JTextArea taNotice;
	private JTextField tfNotice;
	private JButton btnNotice;
	private Server server;
	private ClientManager cm;

	private DefaultTableModel model;

	public ServerForm(Server server, ClientManager cm) {
		this.server = server;
		this.cm = cm;
		this.connectedUserList = server.getCm().getConnectedUserList();

		init();
		setDisplay();
		addListeners();
		showFrame();
		startRefreshThread();
	}

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		// NORTH
		lblAllUser = new JLabel("�� �����ο�");
		lblWaitingUser = new JLabel("���� �ο�");
		lblChatUser = new JLabel("ä�ù� �ο�");
		lblAllChatRoom = new JLabel("�� ä�ù�");
		lblOpenChatRoom = new JLabel("���� ä�ù�");
		lblPrivateChatRoom = new JLabel("��� ä�ù�");
		lblAllMember = new JLabel("�� ȸ��");
		tfAllUser = new JTextField(5);
		tfWaitingUser = new JTextField(5);
		tfChatUser = new JTextField(5);
		tfAllChatRoom = new JTextField(5);
		tfOpenChatRoom = new JTextField(5);
		tfPrivateChatRoom = new JTextField(5);
		tfAllMember = new JTextField(5);

		tfOpenChatRoom.setText("0");
		tfPrivateChatRoom.setText("0");

		tfAllUser.setEditable(false);
		tfWaitingUser.setEditable(false);
		tfChatUser.setEditable(false);
		tfAllChatRoom.setEditable(false);
		tfOpenChatRoom.setEditable(false);
		tfPrivateChatRoom.setEditable(false);
		tfAllMember.setEditable(false);

		tfAllUser.setBackground(Color.white);
		tfWaitingUser.setBackground(Color.white);
		tfChatUser.setBackground(Color.white);
		tfAllChatRoom.setBackground(Color.white);
		tfOpenChatRoom.setBackground(Color.white);
		tfPrivateChatRoom.setBackground(Color.white);
		tfAllMember.setBackground(Color.white);

		// CENTER
		// lblInfo = new JLabel(
		// "                                    ���� �� / �� ���� / ����or���");
		// AllUserInfoList = new JList<User>(connectedUserList);
		// AllUserInfoList.setPrototypeCellValue("                                ");
		// AllUserInfoList.setVisibleRowCount(10);
		// AllUserInfoList.setSelectionMode(
		// ListSelectionModel.SINGLE_SELECTION
		// );

		String[] Names = new String[] { "������", "������" };
		model = new DefaultTableModel(Names, 0);
		AllUserInfoTable = new JTable(model);
		AllUserInfoTable.getTableHeader().setReorderingAllowed(false);
		AllUserInfoTable.getTableHeader().setResizingAllowed(false);

		// SOUTH
		taNotice = new JTextArea(20, 60);
		tfNotice = new JTextField(30);
		btnNotice = new JButton("�˸� ������");

		taNotice.setEditable(false);
	}

	private void setDisplay() {
		JPanel pnllblAllUser = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblAllUser.add(lblAllUser);
		JPanel pnllblWatingUser = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblWatingUser.add(lblWaitingUser);
		JPanel pnllblChatUser = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblChatUser.add(lblChatUser);
		JPanel pnllblAllChatRoom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblAllChatRoom.add(lblAllChatRoom);
		JPanel pnllblOpenChatRoom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblOpenChatRoom.add(lblOpenChatRoom);
		JPanel pnllblPrivateChatRoom = new JPanel(new FlowLayout(
				FlowLayout.LEFT));
		pnllblPrivateChatRoom.add(lblPrivateChatRoom);
		JPanel pnllblAllMember = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblAllMember.add(lblAllMember);
		JPanel pnltfAllUser = new JPanel();
		pnltfAllUser.add(tfAllUser);
		JPanel pnltfWatingUser = new JPanel();
		pnltfWatingUser.add(tfWaitingUser);
		JPanel pnltfChatUser = new JPanel();
		pnltfChatUser.add(tfChatUser);
		JPanel pnltfAllChatRoom = new JPanel();
		pnltfAllChatRoom.add(tfAllChatRoom);
		JPanel pnltfOpenChatRoom = new JPanel();
		pnltfOpenChatRoom.add(tfOpenChatRoom);
		JPanel pnltfPrivateChatRoom = new JPanel();
		pnltfPrivateChatRoom.add(tfPrivateChatRoom);
		JPanel pnltfAllMember = new JPanel();
		pnltfAllMember.add(tfAllMember);

		// NORTH
		JPanel pnlNoWelbl = new JPanel(new GridLayout(0, 1));
		pnlNoWelbl.add(pnllblAllMember);

		JPanel pnlNoWetf = new JPanel(new GridLayout(0, 1));
		pnlNoWetf.add(pnltfAllMember);

		JPanel pnlNoWest = new JPanel(new BorderLayout());
		pnlNoWest.setBorder(new TitledBorder(new LineBorder(Color.gray), "ȸ��"));
		pnlNoWest.add(pnlNoWelbl, BorderLayout.WEST);
		pnlNoWest.add(pnlNoWetf, BorderLayout.CENTER);

		JPanel pnlNoCelbl = new JPanel(new GridLayout(0, 1));
		pnlNoCelbl.add(pnllblAllUser);
		pnlNoCelbl.add(pnllblWatingUser);
		pnlNoCelbl.add(pnllblChatUser);

		JPanel pnlNoCetf = new JPanel(new GridLayout(0, 1));
		pnlNoCetf.add(pnltfAllUser);
		pnlNoCetf.add(pnltfWatingUser);
		pnlNoCetf.add(pnltfChatUser);

		JPanel pnlNoCenter = new JPanel(new BorderLayout());
		pnlNoCenter
				.setBorder(new TitledBorder(new LineBorder(Color.gray), "�ο�"));
		pnlNoCenter.add(pnlNoCelbl, BorderLayout.WEST);
		pnlNoCenter.add(pnlNoCetf, BorderLayout.CENTER);

		JPanel pnlNoEalbl = new JPanel(new GridLayout(0, 1));
		pnlNoEalbl.add(pnllblAllChatRoom);
		pnlNoEalbl.add(pnllblOpenChatRoom);
		pnlNoEalbl.add(pnllblPrivateChatRoom);

		JPanel pnlNoEatf = new JPanel(new GridLayout(0, 1));
		pnlNoEatf.add(pnltfAllChatRoom);
		pnlNoEatf.add(pnltfOpenChatRoom);
		pnlNoEatf.add(pnltfPrivateChatRoom);

		JPanel pnlNoEast = new JPanel(new BorderLayout());
		pnlNoEast
				.setBorder(new TitledBorder(new LineBorder(Color.gray), "ä�ù�"));
		pnlNoEast.add(pnlNoEalbl, BorderLayout.WEST);
		pnlNoEast.add(pnlNoEatf, BorderLayout.CENTER);

		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(pnlNoWest, BorderLayout.WEST);
		pnlNorth.add(pnlNoCenter, BorderLayout.CENTER);
		pnlNorth.add(pnlNoEast, BorderLayout.EAST);

		// CENTER
		JPanel pnlCenter = new JPanel();
		JScrollPane scroll1 = new JScrollPane(AllUserInfoTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// pnlCenter.add(lblInfo, BorderLayout.CENTER);
		scroll1.setPreferredSize(new Dimension(445, 200));
		pnlCenter.add(scroll1, BorderLayout.SOUTH);

		// SOUTH
		JPanel pnlSouthTa = new JPanel();
		JScrollPane scroll = new JScrollPane(taNotice,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnlSouthTa.add(scroll);

		JPanel pnlSouthChat = new JPanel();
		pnlSouthChat.add(tfNotice);
		pnlSouthChat.add(btnNotice);

		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.setBorder(new LineBorder(Color.gray));
		pnlSouth.add(pnlSouthTa, BorderLayout.CENTER);
		pnlSouth.add(pnlSouthChat, BorderLayout.SOUTH);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void addListeners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();

				if (src == btnNotice || src == tfNotice) {
					String msg = tfNotice.getText();
					server.broadCast(msg);
					tfNotice.setText("");
					showMsgInTaNotice(msg);
				}
			}
		};
		tfNotice.addActionListener(listener);
		btnNotice.addActionListener(listener);
	}

	private void showFrame() {
		setTitle("Server");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void broadCast(String msg) {
		// �����ϰ� �ִ� ��� ������ �������� oos�� ����
		Collection<TransportThread> tempVec = cm.getThreadMap().values();
		for (TransportThread temp : tempVec) {
			temp.sendData(new SendData(ANNOUNCE_NOTICE_MSG, msg));
		}
	}

	public void showMsgInTaNotice(String msg) {
		taNotice.append("<�˸�> " + msg + "\n");
	}

	public void addUsers(String id, String title) {
		Object[] rowData = new Object[2];
		rowData[0] = id;
		rowData[1] = title;
		// rowData[2] = what;

		model.addRow(rowData);
	}

	public void refreshUsers(String id, String title) {
		removeUsers(id);
		
		Object[] rowData = new Object[2];
		rowData[0] = id;
		rowData[1] = title;
		// rowData[2] = what;

		model.addRow(rowData);

	}

	public void removeUsers(String id) {
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(id)) {
				model.removeRow(i);
			}
		}
	}

	public void appendMsg(String s) {
		taNotice.append(s);
	}

	private void startRefreshThread() {
		new Thread() {
			public void run() {
				while (true) {

					//
					// ��� ������ ���������� �������� �غ�����
					// 1. �� ȸ����
					tfAllMember.setText(server.getUserList().size() + "");

					// 2. �� ���� ������
					tfAllUser.setText(server.getCm().getConnectedUserList()
							.size()
							+ "");

					// 3. ���� �ο�
					tfWaitingUser.setText(server.getCm().getWr()
							.getWaitingRoomUserList().size()
							+ "");

					// 4. ä�ù� �ο�
					// tfChatUser.setText(server.getCm().getCr().getChatRoomUserList().size()
					// + "");
					tfChatUser.setText(server.getCm().getConnectedUserList()
							.size()
							- server.getCm().getWr().getWaitingRoomUserList()
									.size() + "");

					// 5. ä�ù� ����
					tfAllChatRoom.setText(server.getCm().getChatRoomList()
							.size()
							+ "");

					// 6. ����,��� ä�ù� ����
					int open = 0;
					int unOpen = 0;
					for (ChatRoom temp : server.getCm().getChatRoomList()) {

						if (temp.getIsPrivte() == false) {
							open++;
							tfOpenChatRoom.setText(open + "");
						} else {
							unOpen++;
							tfPrivateChatRoom.setText(unOpen + "");
						}
					}

					// 7. ������ġ����

				}
			}
		}.start();
	}
}