import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;

public class MemberPanel extends JPanel implements Protocol {
	private JLabel lblTitle;

	private JPopupMenu pMenu;
	private JMenuItem miFriendIn;
	private JMenuItem miProfile;
	private JMenuItem miBlacklist;
	private JMenuItem miMessage;
	private JList<User> listMembers;
	private ChatRoom chatRoom;

	private DefaultListModel<User> listModel;
	private JPanel pnlAll;
	private JPanel pnlList;
	private JScrollPane scroll;
	private Client client;
	private String targetId;

	public MemberPanel(Client client, WaitingRoom wr) {

		this.client = client;
		init();

		for (User temp : wr.getWaitingRoomUserList()) {
			updateList(temp);
		}

		setDisplay();
		addListeners();

		lblTitle.setText("대기실 목록");
		Utils.setLabelToDefaultFont(lblTitle);
	}

	public MemberPanel(Client client, ChatRoom cr) {
		this.chatRoom = cr;
		this.client = client;

		init();

		for (User temp : cr.getChatRoomUserList()) {
			updateList(temp);
		}

		setDisplay();
		addListeners();
		setOpaque(false);

		lblTitle.setText(cr.getTitle() + " 목록");
		Utils.setLabelToDefaultFont(lblTitle);
	}

	// 0624 03:00 원도 수정 필요 없는거 몇개 날림.
	private void init() {
		listModel = new DefaultListModel<User>();

		lblTitle = new JLabel("접속자 목록", JLabel.CENTER);
		listMembers = new JList<User>(listModel);

		miFriendIn = new JMenuItem("친구추가");
		miProfile = new JMenuItem("프로필보기");
		miBlacklist = new JMenuItem("블랙리스트추가");
		miMessage = new JMenuItem("쪽지보내기");

		pMenu = new JPopupMenu();

		pMenu.add(miFriendIn);
		pMenu.add(miProfile);
		pMenu.add(miBlacklist);
		pMenu.add(miMessage);

	}

	// private Vector<String> makeIdNickList(){
	// Vector<String> idNickList = new Vector<String>();
	// for(int i=0; i<waitingRoomUserList.size(); i++){
	// User tempUser = waitingRoomUserList.get(i);
	// String str = tempUser.getNickname() + "(" + tempUser.getId() + ")";
	// idNickList.add(str);
	// }
	// return idNickList;
	// }

	/*
	 * JAEYOUNG 수정
	 */
	// public void updateChatRoomMemberPanelToMemberPanel(Vector<String>
	// waitingRoomList){
	// //인원 변경없이 그냥 리스트에만 추가했음
	// pnlAll.removeAll();
	// pnlList.removeAll();
	// lblTitle.setText("대기방 접속자 목록");
	// pnlAll.add(lblTitle, BorderLayout.NORTH);
	// scroll = new JScrollPane(listJupsok);
	// pnlList.add(scroll);
	// pnlAll.add(pnlList, BorderLayout.CENTER);
	// add(pnlAll, BorderLayout.CENTER);
	// repaint();
	// revalidate();
	// }

	/*
	 * JAEYOUNG 수정
	 */
	// public void updateMemberPanelToChatRoomMemberPanel(Vector<String>
	// chatRoomList){
	// //인원 변경없이 그냥 리스트에만 추가했음
	// pnlAll.removeAll();
	// lblTitle.setText("채팅방 접속자 목록");
	// pnlAll.add(lblTitle, BorderLayout.NORTH);
	// listChatroom = new JList<String>(chatRoomList);
	// listChatroom.setPrototypeCellValue("                                      ");
	// listChatroom.setVisibleRowCount(14);
	//
	// System.out.println("음음" + chatRoomList.size());
	// scroll = new JScrollPane(listChatroom);
	// pnlList.add(scroll);
	// pnlAll.add(pnlList, BorderLayout.CENTER);
	// add(pnlAll, BorderLayout.CENTER);
	// repaint();
	// revalidate();
	// }
	//
	// /*
	// * JAEYOUNG 수정
	// * */

	// 0624 03:00 원도 수정
	public void updateList(User enteredUser) {
		if (!(listModel.contains(enteredUser))) {
			listModel.addElement(enteredUser);
		}
	}

	// 190621 15:23 손원도
	// 190624 11:41 숸도 재수정
	// J리스트에서 Ji워버려

	public void removeFromList(String id) {
		listModel.removeElement((new User(id)));
	}

	public void removeFromList(User user) {
		listModel.removeElement(user);
	}

	private void setDisplay() {
		pnlAll = new JPanel(new BorderLayout());
		JPanel pnlMain = new JPanel();
		pnlMain.add(lblTitle);
		pnlMain.setOpaque(false);

		lblTitle.setPreferredSize(new Dimension(200, 20));
		lblTitle.setBackground(Color.GRAY);

		pnlList = new JPanel();
		scroll = new JScrollPane(listMembers);

		pnlList.add(scroll);

		pnlAll.add(pnlMain, BorderLayout.NORTH);
		pnlAll.add(scroll, BorderLayout.CENTER);

		add(pnlAll, BorderLayout.CENTER);
		pnlAll.setBackground(new Color(58, 119, 176));

	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	private void addListeners() {

		listMembers.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				if (listMembers.getSelectedValue() != null) {
					if (me.isPopupTrigger()) {
						pMenu.show(listMembers, me.getX(), me.getY());

					}
				}
				// getSelectedindex
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (listMembers.getSelectedValue() != null) {
					if (me.isPopupTrigger()) {
						pMenu.show(listMembers, me.getX(), me.getY());
					}
				}
			}
		});

		miProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (listMembers.getSelectedValue() != null) {
					String id = listMembers.getSelectedValue().getId();
					if (!id.equals(client.getMyId())) {
						client.sendData(new SendData(SHOW_OTHER_PROFILE, id));
					} else {
						client.getCf().showMyProfile(id);
					}
				}
			}
		});

		miFriendIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == miFriendIn) {
					System.out.println("여기는 되는건가 ");
					targetId = listMembers.getSelectedValue().getId();
					System.out.println("어디서 문제가 생기나");
					String myId = client.getMyId();
					System.out.println("여기까지는 오는가" + myId);
					if (client.getMyId().equals(targetId)) {
						JOptionPane
								.showMessageDialog(null, "자신은 친구추가가 불가능합니다.");
					} else {
						new AddFriendM(client, targetId);

					}
				}
			}
		});

		miBlacklist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String targetId = listMembers.getSelectedValue().getId();
				if (!client.getMyId().equals(targetId)) {
					client.sendData(new SendData(ADD_BLACKLIST, client
							.getMyId(), targetId));
				} else {
					Utils.showMsg(null, "자기자신은 추가될 수 없습니다");

				}
			}

		});

		miMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// String idNick = listMembers.getSelectedValue().getId();
				// int intId = idNick.indexOf("(", 0);
				// targetId = idNick.substring(intId + 1, idNick.length());
				// new Messenger(client, MemberPanel.this);

				String targetId = listMembers.getSelectedValue().getId();
				if (!targetId.equals(client.getMyId())) {
					new Messenger_Send(client, client.getMyId(), targetId);
				} else {
					Utils.showMsg(client.getCf(), "자신에게 쪽지보내는 것은 조금 곤란합니다");
				}
			}
		});
	}

}
