import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class WatingRoomPanel extends JPanel implements Protocol {
	private JTextArea taChat;
	private JComboBox cb;
	private JButton btnSend;
	private JTextField tfChat;
	private String[] type = { "ALL USER", "WHISPER" };

	/*
	 * 190623 �翵���� �迭�� ���ͷ� �ٲ�
	 */
	private Vector<ChatRoomListPanel> crlPnlList;

	/*
	 * 190625 �翵 ����
	 * 190626 ���� ���� -> CR����Ʈ �Ű��ּ��� ��Ź�帳�ϴ�!!
	 */
	private Vector<ChatRoom> crList;
	
	private int index = 0;
	private JLabel lblWhisper;
	private JButton btnMakeRoom;
	private JButton btnBefore;
	private JButton btnAfter;
	
	/*
	 * JAEYOUNG ���� �ϴ� �������
	 */
	private JPanel pnlMain;

	private JPanel pnlNorth;
	private Client client;
	private WaitingRoom wr;
	private String whisperId;
	private JScrollPane jsp; 

	public WatingRoomPanel(Client client, WaitingRoom wr) {
		this.client = client;
		this.wr = wr;
		this.setOpaque(false);
		init();
		createWaitingRoomDisplay();
		addListeners();
		
	
	}

	public Vector<ChatRoom> getCrList() {
		return crList;
	}

	public void setCrList(Vector<ChatRoom> crList) {
		this.crList = crList;
	}

	public WaitingRoom getWr() {
		return wr;
	}

	public void setWr(WaitingRoom wr) {
		this.wr = wr;
	}

	public void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}

		/*
		 * 190625 �翵 ����
		 */
		crList = wr.getCrList();
		taChat = new JTextArea(7, 30);
		taChat.setBorder(new LineBorder(Color.GRAY, 1));
		taChat.setEnabled(false);
		tfChat = new JTextField(40);
		cb = new JComboBox(type);
		btnSend = new JButton(new ImageIcon("files/����.png"));
		Utils.btnSetting(btnSend);

		/*
		 * 
		 * 190624 �翵�߰� �ϴ� ó���� ���鶧�� �ϳ��� �г��� ���� �־����
		 */

		lblWhisper = new JLabel("���� ��ü");
		Utils.setLabelToDefaultFont(lblWhisper);
		
		btnMakeRoom = new JButton(new ImageIcon("files/�游���.png"));
		Utils.btnSetting(btnMakeRoom);
		btnBefore = new JButton("��");
		btnAfter = new JButton("��");

/*
		 * 
		 * 190624 �翵�߰� �ϴ� ó���� ���鶧�� �ϳ��� �г��� ���� �־����
		 */
		crlPnlList = new Vector<ChatRoomListPanel>();
		
		if(crList.size() == 0){
			ChatRoomListPanel crlPnl = new ChatRoomListPanel(client, null);
			crlPnlList.add(crlPnl);
			
		}else{
			setChatRoomPanel();
		}
		//lblNumber = new JLabel(String.valueOf(index + 1));
	}
	private void createWaitingRoomDisplay() {
		/*
		 * 190626 ���翵 ����
		 * */
		index = 0;

		pnlMain = new JPanel(new BorderLayout());
		pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.setOpaque(false);

		/*
		 * 190624 �翵���� ������ �г��� lbl������ 6�̸� ���� �г��� �������� ó�� �����Ҷ� �ϳ������ �����ϴϱ� ������
		 * index 0���ͽ����� ó���������� ������ 0���� ������
		 */
		pnlNorth.add(crlPnlList.get(0));


		JPanel pnlFunction = new JPanel(new BorderLayout());
		JPanel pnlFunction1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlFunction1.add(btnBefore);
		pnlFunction1.add(btnAfter);
		pnlFunction.setOpaque(false);
		pnlFunction1.setOpaque(false);

		JPanel pnlFunction2 = new JPanel(new GridLayout(1, 0));
		JPanel pnlFunction2sub = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlFunction2.add(lblWhisper);
		pnlFunction2sub.add(btnMakeRoom);
		pnlFunction2.add(pnlFunction2sub);
		pnlFunction2.setOpaque(false);
		pnlFunction2sub.setOpaque(false);

		pnlFunction.add(pnlFunction1, BorderLayout.CENTER);
		pnlFunction.add(pnlFunction2, BorderLayout.SOUTH);

		JPanel pnlCenter = new JPanel(new BorderLayout());

		jsp = new JScrollPane(taChat);
		jsp.setOpaque(false);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(cb);
		pnlSouth.add(tfChat);
		pnlSouth.add(btnSend);
		pnlSouth.setOpaque(false);

		pnlCenter.add(pnlFunction, BorderLayout.NORTH);
		pnlCenter.add(jsp, BorderLayout.CENTER);
		pnlCenter.add(pnlSouth, BorderLayout.SOUTH);
		pnlCenter.setOpaque(false);

		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);

		pnlMain.setOpaque(false);
		add(pnlMain);
		setOpaque(false);
		

	}

	/*
	 * JAEYOUNG ����
	 */

	public void updateWaitingRoom() {
		removeAll();
		createWaitingRoomDisplay();
		repaint();
		revalidate();
	}

	public void addChatRoomList(ChatRoom cr) {
		crList.add(cr);

		setChatRoomPanel();
		removeAll();
		createWaitingRoomDisplay();
		repaint();
		revalidate();
	}
	
	public void editChatRoomList(String oriTitle, String changeTitle){
		for(ChatRoom temp : crList){
			if(temp.getTitle().equals(oriTitle)){
				System.out.println("�̱��� ����?");
				temp.setTitle(changeTitle);
			}
		}
		refresh();
	}

	public void refresh() {
		setChatRoomPanel();
		removeAll();
		createWaitingRoomDisplay();
		setOpaque(false);
		repaint();
		revalidate();
	}
	public void updateCrList(ChatRoom cr, User user) {
		System.out.println(crList.size());
		
		for(int i = 0 ; i < crList.size(); i++) {
			if(crList.get(i).equals(cr)){
				crList.get(i).addChatRoomUser(user);
				break;
			}
		}
	}
	
	public void removeCrList(ChatRoom cr, User user) {
		System.out.println(crList.size());
		
		for(int i = 0 ; i < crList.size(); i++) {
			if(crList.get(i).equals(cr)){
				crList.get(i).removeChatRoomUser(user);
				break;
			}
		}
	}
	
	public void setChatRoomPanel(){
		int size = crList.size();
		System.out.println("����Ǿ��ִ� ê���� ������" + size);
		for (int i = 0; i < size; i++) {
			System.out.println("i : " + i);
			int j = i;
			Vector<ChatRoom> splitCrList = new Vector<ChatRoom>();
			for (j = i; j < i + 6 && j < size; j++) {
				System.out.println("j : " + j);
				splitCrList.add(crList.get(j));
				System.out.println("���ø� ����Ʈ ������" + splitCrList.size());
			}

			ChatRoomListPanel tempPnl = new ChatRoomListPanel(client, splitCrList);

			if(i == 0){
				System.out.println("���� ����");
				crlPnlList.removeAllElements();
			}
			tempPnl.setOpaque(false);
			crlPnlList.add(tempPnl);
			
			i = j-1;
			System.out.println("�߰��ϰ� �г� ������" + crlPnlList.size());
			
		}
		

	}

	public void showWhisperMsgInTa(String myId, String targetId, String msg) {
		taChat.append("[�ӼӸ�]" + myId + "��" + targetId + " : " + msg + "\n");
		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
	}

	public void showMsgInTa(String id, String msg) {
		taChat.append(id + " : " + msg + "\n");
		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
	}

	public void addListeners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				String selectedString = (String) cb.getSelectedItem();

				if (src == btnSend || src == tfChat) {
					if(tfChat.getText().length() <= 70){
						String msg = tfChat.getText();
						if (selectedString.equals("ALL USER")) {
							// ��ư������
							if(!tfChat.getText().equals("")){
								client.sendData(new SendData(300, client.getMyId(), msg));
								tfChat.setText("");
							}
						} else {
							// �ӼӸ� ������ : �����̵� / ���� ���̵� / �޼���
							if(!tfChat.getText().equals("")){
								client.sendData(new SendData(
										SEND_WAITINGROOM_WHISPER_MSG, client.getMyId(),
										whisperId, msg));
								tfChat.setText("");
							}
						}
					}else{
						Utils.showMsg(client.getCf(), "�ʹ� ���� �Է��߽�");
					}
					

				} else if (src == btnMakeRoom) {
					new CreateChatRoomForm(client);
				}

			}
		};
		btnSend.addActionListener(listener);
		tfChat.addActionListener(listener);
		btnMakeRoom.addActionListener(listener);

		cb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox tempCb = (JComboBox) e.getSource();
				int index = tempCb.getSelectedIndex();
				try {

					if (index == 1) {
						// �ӼӸ��� �����̸�
						boolean flag = true;
						while (flag) {

							whisperId = JOptionPane
									.showInputDialog("�ӼӸ��� ���� ����� ID �Է�");

							if (!whisperId.equals("")) {
								if (whisperId.equals(client.getMyId())) {
									Utils.showMsg(client.getCf(),
											"It is YOUR ID");
									flag = true;
								} else {
									System.out.println("���Ϲ� Ż��!!!!!!");
									flag = false;
								}
							} else {
								Utils.showMsg(client.getCf(), "��ĭ�̴� �̳��");
								flag = true;
							}
						}
						// �޺����� �ӼӸ��� �ش�ID�� ����
						lblWhisper.setText(whisperId + "�� ���� �ӼӸ� ��");
					} else if (index == 0) {
						lblWhisper.setText("���� ��ü");
					}
				} catch (Exception ex) {
					lblWhisper.setText("���� ��ü");
					cb.setSelectedIndex(0);
				}
			}
		});

		/*
		 * 190626 ���翵 ����
		 * */
		
		ActionListener bListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src == btnBefore) {

					index = index - 1;
					System.out.println("1) ������ �̵��������ǵ� ���� �ε��� : " + index);
					if (index < 0) {
						Utils.showMsg(client.getCf(), "ù��° ������ �Դϴ�");
						//������� ��������
						index = index + 1;
						System.out.println("���� ��� �������� �ε��� : " + index);
					} else {
						System.out.println("0�̶� ���ų� ũ�ٰ� �ǴܵǾ����� �ε��� : " + index);
						pnlNorth.removeAll();
						pnlNorth.add(crlPnlList.get(index));
						//lblNumber.setText(String.valueOf(index+1));
						repaint();
						revalidate();
					}

				} else if (src == btnAfter) {

					index = index + 1;
					System.out.println("2) ������ �̵��������ǵ� ������ �ε���" + index);
					System.out.println("����Ʈ�� ������" + crlPnlList.size());
					if (index > crlPnlList.size() - 1) {
						Utils.showMsg(client.getCf(), "������ ������ �Դϴ�");
						index = index - 1;
						System.out.println("������� �������� �ε��� : " + index);
					} else {
						System.out.println("������� �۰ų� ���ٰ� �ǴܵǾ����� �ε��� : " + index);
						pnlNorth.removeAll(); 
						pnlNorth.add(crlPnlList.get(index));
						//lblNumber.setText(String.valueOf(index+1));
						repaint();
						revalidate();
					}
				}
			}
		};
		btnAfter.addActionListener(bListener);
		btnBefore.addActionListener(bListener);

		System.out.println("�游��� : " + btnMakeRoom.getSize());
		System.out.println("���� : " + btnSend.getSize());
		
		
	}
}
