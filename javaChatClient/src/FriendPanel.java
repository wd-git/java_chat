import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class FriendPanel extends JPanel implements Protocol {
	private JLabel lblTitle;

	private JPopupMenu pMenu;
	private JMenuItem miFriendout;
	private JMenuItem miProfile;
	private JMenuItem mispeak;
	private JTextArea ta;
	private JList<User> listFriend;
	// private Vector<User> FriendList = new Vector<User>(); // 정상욱 추가 06-24
	// 20:00
	private DefaultListModel<User> listModel;
	private Client client;

	public FriendPanel(Client client) {
		this.client = client;
		init();
		setDisplay();
		// addListners();

	}

	private void init() {
		listModel = new DefaultListModel<User>();
		lblTitle = new JLabel("친구 목록", JLabel.CENTER);
		Utils.setLabelToDefaultFont(lblTitle);
		lblTitle.setBackground(Color.white);
		listFriend = new JList<User>(listModel);
		listFriend.setPrototypeCellValue(new User("xxxxxxxxxxxxxxxx"));
		// listFriend = new JList<User>(FriendList); // 정상욱 추가 06-24 20:00
		mispeak = new JMenuItem("1:1대화");
		miFriendout = new JMenuItem("친구삭제");
		miProfile = new JMenuItem("프로필보기");

		pMenu = new JPopupMenu();

		pMenu.add(mispeak);
		pMenu.add(miFriendout);
		pMenu.add(miProfile);
	}

	public void removeUI(User user) {
		listModel.removeElement(user);

		// FriendList.remove(user);
		// listFriend.updateUI();
	}

	public void updateUI(User user) {
		listModel.addElement(user);
		// FriendList.add(user);
		// listFriend.updateUI(); // 정상욱 추가 06-24 20:00
	}

	private void setDisplay() {
		JPanel pnlAll = new JPanel(new BorderLayout());
		JPanel pnlMain = new JPanel();
		pnlMain.add(lblTitle);
		pnlMain.setOpaque(false);

		lblTitle.setOpaque(false);
		lblTitle.setPreferredSize(new Dimension(200, 20));
		lblTitle.setBackground(Color.GRAY);

		JPanel pnlList = new JPanel();
		pnlList.setOpaque(false);

		JScrollPane scroll = new JScrollPane(listFriend);
		pnlList.add(scroll);

		LineBorder line1 = new LineBorder(Color.BLACK, 1, true);

		pnlAll.add(pnlMain, BorderLayout.NORTH);
		pnlAll.add(scroll, BorderLayout.CENTER);
		pnlAll.setBackground(new Color(58, 119, 176));
		add(pnlAll, BorderLayout.CENTER);

		mispeak.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, listFriend
						.getSelectedValue().getId() + "님에게 1:1대화를 요청하시겠습니까",
						"1:1 대화요청", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, listFriend
							.getSelectedValue().getId() + "님에게 대화요청을 하였습니다.");
					String myId = client.getMyId();
					String targetId = listFriend.getSelectedValue().getId();
					client.sendData(new SendData(REQUEST_ONEONONE_MSG, myId,
							targetId));
				}

			}
		});

		listFriend.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (listFriend.getSelectedValue() != null) {
					if (me.isPopupTrigger()) {
						pMenu.show(listFriend, me.getX(), me.getY());
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (listFriend.getSelectedValue() != null) {
					if (me.isPopupTrigger()) {
						pMenu.show(listFriend, me.getX(), me.getY());
					}
				}
			}
		});

		miProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				String targetId = listFriend.getSelectedValue().getId();
				client.sendData(new SendData(SHOW_OTHER_PROFILE, targetId));
			}
		});
		// 정상욱 추가 06-24 24:00
		miFriendout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String targetId = listFriend.getSelectedValue().getId();
				client.sendData(new SendData(DELETE_FRIEND, client.getMyId(),
						targetId));
			}
		});
	}

	// ActionListener listener = new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent ae) {
	//
	// Object src = ae.getSource();
	// if(src == miFriend ) {
	//
	// } else if(src == miProfile) {
	//
	// } else {
	//
	// }
	// }
	// };
	//
	// miFriend.addActionListener(listener);
	// miProfile.addActionListener(listener);
	// miBlacklist.addActionListener(listener);
	// }
}
