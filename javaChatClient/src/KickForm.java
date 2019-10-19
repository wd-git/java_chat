import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


public class KickForm extends JFrame implements Protocol{
	/*
	 * 이재영 님의 머리바꾸기프레임을 고대로 가져와서 고대로 양심 없이 썻습니다.
	 * 뭐 어쩌겠어요?
	 * 다들 소프트웨어 공학 배우셔서 잘 아시겠지만..
	 * 이런게 프로그램 재사용이죠? 
	 * 냠냠..
	*/
	private Client client;
	private ChatRoom cr;
	private JButton btnConfirm;
	private String crTitle;
	private JList chatUserJList;
	private Vector<User> chatRoomUserList;
	private Vector<String> chatRoomIdList;
	private String nowHeadId;

	
	public KickForm(Client client,ChatRoom cr){
		this.client = client;
		this.cr = cr;
		this.crTitle = cr.getTitle();
		this.chatRoomUserList = cr.getChatRoomUserList();
		nowHeadId = cr.getHeadId();
		
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	
	/*
	 * 190702이재영 멤버변수랑 메소드 수정
	 * */
	private void init(){
		chatRoomIdList = new Vector<String>();
		for(User temp : chatRoomUserList){
			if(!temp.getId().equals(nowHeadId)){
				chatRoomIdList.add(temp.getId());
			}
		}
		chatUserJList = new JList<String>(chatRoomIdList);
		chatUserJList.setPrototypeCellValue("                                  ");
		chatUserJList.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION
			);
		btnConfirm = new JButton("너 나가");
		
	}
	
	private void setDisplay(){
		JPanel pnlNorth = new JPanel();
		JLabel lblNorth = new JLabel("누굴 보내볼까..?? ^ㅡ^? ",JLabel.CENTER);
		pnlNorth.add(lblNorth, BorderLayout.NORTH);
		
		JPanel pnlCenter = new JPanel();
		JScrollPane scroll = new JScrollPane(chatUserJList);
		pnlCenter.add(scroll);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnConfirm);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}
	
	private void addListener(){
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedId = (String)chatUserJList.getSelectedValue();
				
				// 당신은 우리와 함께 갈 수 없습니다
				
				client.sendData(new SendData(
						KICKOUT_CHATROOM, cr, selectedId ));
				dispose();
			}
		});
		
	}
	
	private void showFrame(){
		setTitle("강퇴");
		pack();
		setLocationRelativeTo(client.getCf());
		setVisible(true);
	}

}
