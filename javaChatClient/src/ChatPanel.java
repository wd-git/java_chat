import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ChatPanel extends JPanel implements Protocol{
	private JLabel lblNum;
	private JTextField tfRoomName;
	private String roomTitle;
	private JTextField tfChat;
	private JTextArea taChat;
	private JScrollPane jsp;
	private JPanel lblTemp1;
	
	private JButton btnChange;
	private JButton btnSend;
	private ChatRoom cr;
	private Client client;
	
	/*
	 * 190626 ���翵
	 * �����̺���
	 * */
	private JLabel lblHead;
	private JButton btnChangeHead;
	private JButton btnKick;
	private JLabel lblPersonLimit;

	public ChatPanel(ChatRoom cr, Client client) {
		this.cr = cr;
		this.client = client;
		init();
		setDisplay();
		addListeners();
	}
	

	public ChatRoom getCr() {
		return cr;
	}


	public void setCr(ChatRoom cr) {
		this.cr = cr;
	}


	public String getRoomTitle() {
		return roomTitle;
	}

	public void setTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
	
	/*
	 * 190702 
	 * ���翵�� �߰��� ���� �ٲٱ�
	 * */
	public void changeRoomHead(String selectedId){
		if(lblHead.getText().equals(selectedId)){
			Utils.showMsg(client.getCf(), "�̹�" +selectedId +"���� �����̽ʴϴ�. �ƹ��ϵ� �Ͼ�� �ʽ��ϴ�");
		}else{
			cr.setHeadId(selectedId);
			Utils.showMsg(client.getCf(), "������ " + selectedId + "�� ���� �Ǿ����ϴ�");
			lblHead.setText(selectedId);
		}
	}
	
	
	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		lblNum = new JLabel(String.valueOf(cr.getRoomNum()), JLabel.CENTER);
		Utils.setLabelToDefaultFont(lblNum);
		lblNum.setForeground(Color.white);
		tfRoomName = new JTextField(35);
		roomTitle = cr.getTitle();
		tfRoomName.setText(roomTitle);
		tfRoomName.setFont(new Font("���� ���", Font.BOLD, 15));
		tfRoomName.setForeground(Color.white);
		tfRoomName.setOpaque(false);
		tfRoomName.setEditable(false);

		/*
		 * 190626 ���翵
		 * �����̺���
		 * */
		
		lblHead = new JLabel(cr.getHeadId());
		btnChangeHead = new JButton("���庯��");
		btnKick = new JButton("�����ϱ�");
		lblPersonLimit = new JLabel(String.valueOf(cr.getChatRoomUserList().size())+ "/"+ String.valueOf(cr.getPersonLimit()));
		
		jsp = new JScrollPane();
		tfChat = new JTextField(37);
		taChat = new JTextArea(20, 65);
		taChat.setEnabled(false);
		btnChange = new JButton(new ImageIcon("files/����.png"));
		Utils.btnSetting(btnChange);
		btnSend = new JButton(new ImageIcon("files/����_1.png"));
		Utils.btnSetting(btnSend);
		lblNum.setBorder(new LineBorder(Color.GRAY));
		lblNum.setPreferredSize(new Dimension(20, 20));
		// btnInvite.setPreferredSize(new Dimension(80, 30));
	}

	private void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setOpaque(false);
		/*
		 * 190626 ���翵
		 * �����̺���
		 * */
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.setOpaque(false);
		JPanel pnlInfoNorth = new JPanel();
		pnlInfoNorth.setOpaque(false);
		pnlInfoNorth.add(lblNum);
		pnlInfoNorth.add(tfRoomName);
		pnlInfoNorth.add(btnChange);
		
		JPanel pnlInfoCenter = new JPanel(new GridLayout(0,1));
		pnlInfoCenter.setOpaque(false);
		lblTemp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblHeadTemp = new JLabel("���� : ");
		Utils.setLabelToDefaultFont(lblHeadTemp);
		Utils.setLabelToDefaultFont(lblHead);
		lblHeadTemp.setOpaque(false);
		lblTemp1.add(lblHeadTemp);
		lblTemp1.add(lblHead);
		lblTemp1.setOpaque(false);
		if(client.getMyId().equals(cr.getHeadId())){
			lblTemp1.add(btnChangeHead);
			lblTemp1.add(btnKick);
			
		}
		
		JPanel lblTemp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lblTemp2.setOpaque(false);
		JLabel lblPersonLimitTemp = new JLabel("ä�ù� ���� �ο�");
		Utils.setLabelToDefaultFont(lblPersonLimitTemp);
		Utils.setLabelToDefaultFont(lblPersonLimit);
		lblTemp2.add(lblPersonLimitTemp);
		lblTemp2.add(lblPersonLimit);
		
		pnlInfoCenter.add(lblTemp1);
		pnlInfoCenter.add(lblTemp2);
		TitledBorder tb = new TitledBorder("ä�ù� ����");
		tb.setTitleFont(new Font("���� ���", Font.BOLD, 15));
		tb.setTitleColor(Color.white);
		pnlInfoCenter.setBorder(tb);

		pnlNorth.add(pnlInfoNorth, BorderLayout.NORTH);
		pnlNorth.add(pnlInfoCenter, BorderLayout.CENTER);
		
		JPanel pnlCenterTa = new JPanel();
		pnlCenterTa.setOpaque(false);
		jsp = new JScrollPane(taChat,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnlCenterTa.add(jsp);

		JPanel pnlCenterChat = new JPanel();
		pnlCenterChat.add(tfChat);
		pnlCenterChat.add(btnSend);
		pnlCenterChat.setOpaque(false);

		JPanel pnlCenterTaChat = new JPanel(new BorderLayout());
		pnlCenterTaChat.add(pnlCenterTa, BorderLayout.CENTER);
		pnlCenterTaChat.add(pnlCenterChat, BorderLayout.SOUTH);
		pnlCenterTaChat.setOpaque(false);

		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenterTaChat, BorderLayout.CENTER);
		pnlMain.setOpaque(false);

		add(pnlMain, BorderLayout.CENTER);
		setOpaque(false);
	}
	
	/*
	 * 190627 ���翵�� ����
	 * ä���ϱ� �޼ҵ�
	 * */
	public void showMessage(SendData data){
		String id = (String)data.getData()[0];
		String msg = (String)data.getData()[1];
		
		taChat.append("[" + id + "]" + msg + "\n");
		
		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
	}
	
	public void changeChatRoom(int size) {
		lblPersonLimit.setText(String.valueOf(size)+ "/"+ String.valueOf(cr.getPersonLimit()));
	}
	 
	public void resetButton(){
		System.out.println("�Ƶ��~~");
		lblTemp1.removeAll();
		JLabel lblHeadTemp = new JLabel("���� : ");
		Utils.setLabelToDefaultFont(lblHeadTemp);
		lblTemp1.add(lblHeadTemp);
		lblTemp1.add(lblHead);
		
		System.out.println("�׷��� �ٲ� ������ " + cr.getHeadId());
		if(client.getMyId().equals(cr.getHeadId())){
			System.out.println("��Ŭ���� ���°� ���̴�");
			lblTemp1.add(btnChangeHead);
			lblTemp1.add(btnKick);
		}
		repaint();
		revalidate();
	}
	

	private void addListeners() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				/*
				 * 190627 ���翵�� �߰���
				 * ��������
				 * */
				if (src == btnChange) {
					if (btnChange.getText().equals("����")) {
						tfRoomName.setEditable(true);
						btnChange.setText("Ȯ��");
					} else if (btnChange.getText().equals("Ȯ��")) {
						tfRoomName.setEditable(false);
						String changedRoomTitle = tfRoomName.getText();
						
						client.sendData(new SendData(
								REQUEST_MODIFY_ROOM_TITLE
								,roomTitle
								,changedRoomTitle ));
						cr.setTitle(changedRoomTitle);
						
						roomTitle = cr.getTitle();
						btnChange.setText("����");
						System.out.println("��û ������ ���� �г��� Ÿ��Ʋ : " + roomTitle);
					}
				}else if(src == btnSend){
					/*
					 * 190627 ���翵�� �߰��� ä�ù泻�� ä��
					 * */
					if(!tfChat.getText().equals("") ){
						if(tfChat.getText().length() <= 70){
							String msg = tfChat.getText();
							client.sendData(new SendData(SEND_CHATROOM_MSG_PLEASSE
									,client.getMyId(), msg, cr.getTitle()));
							tfChat.setText("");
						}else{
							Utils.showMsg(client.getCf(), "�ʹ� ���� �Է��߽�");
						}
						
					}
				}else if(src == tfChat){
					/*
					 * 190627 ���翵�� �߰��� ä�ù泻�� ä��
					 * */
					if(!tfChat.getText().equals("")){
						if(tfChat.getText().length() <= 30){
							String msg = tfChat.getText();
							client.sendData(new SendData(SEND_CHATROOM_MSG_PLEASSE
									,client.getMyId(), msg, cr.getTitle()));
							tfChat.setText("");
						}else{
							Utils.showMsg(client.getCf(), "�ʹ� ���� �Է��߽�");
						}
					}
				}else if(src == btnChangeHead){
					/*
					 * 190628 ���翵�� �߰��� ���� �ٲٱ� �ɷ�
					 * */
					new ChangeHeadFrame(client,cr);
				}else if(src == btnKick){
				// 0703 �տ��� : �� ���� �̳༮��!
					new KickForm(client,cr);
				}
			}
		};
		btnChange.addActionListener(listener);
		btnKick.addActionListener(listener);
		btnSend.addActionListener(listener);
		btnChangeHead.addActionListener(listener);
		tfChat.addActionListener(listener);
	}
}
