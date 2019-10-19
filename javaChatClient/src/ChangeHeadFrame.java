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


public class ChangeHeadFrame extends JFrame implements Protocol{
	/*
	 * 안녕하세요 저의 역할은 1. 채팅방에서 방장을 변경할때
	 * 
	 * 여러분들의 아이디를 적는 수고를 덜기 위해서
	 * 
	 * 2. 채팅방 멤버리스트를 JList형태로 띄우고
	 * 
	 * 3. 선택된 유저를 방장으로 "유저의 의사와는 상관없이" 바꿔버릴겁니당ㅎㅎ
	 * 
	 * 4. 이때 확인 버튼을 눌러서 실행할꺼예요~
	 * 
	 * 그럼 잘 부탁드려요
	 * 
	 * */
	private Client client;
	private JButton btnConfirm;
	private String crTitle;
	private JList chatUserJList;
	private Vector<User> chatRoomUserList;
	private Vector<String> chatRoomIdList;
	private String nowHeadId;
	private ChatRoom cr;
	private boolean isExit;

	
	public ChangeHeadFrame(Client client,ChatRoom cr){
		this.client = client;
		this.crTitle = cr.getTitle();
		this.chatRoomUserList = cr.getChatRoomUserList();
		nowHeadId = cr.getHeadId();
		
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	
	public ChangeHeadFrame(Client client,ChatRoom cr, boolean isExit){
		this.cr = cr;
		this.client = client;
		this.crTitle = cr.getTitle();
		this.chatRoomUserList = cr.getChatRoomUserList();
		nowHeadId = cr.getHeadId();
		this.isExit = isExit;
		
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
		btnConfirm = new JButton("너반장해");
	}
	
	private void setDisplay(){
		JPanel pnlNorth = new JPanel();
		JLabel lblNorth = new JLabel("방장 할 사람을 한번 골라보세용",JLabel.CENTER);
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
		/*
		 * 선택된 사람을 가져와서 요청을 보낼꺼예용~
		 * */
		btnConfirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedId = (String)chatUserJList.getSelectedValue();
				
				if(selectedId != null){
					if(isExit){
						client.sendData(new SendData(CHANGE_CHATROOM_HEAD_PLEASE_AND_GETOUT,
								crTitle, selectedId,client.getMyId()));
						
						client.sendData(new SendData(
								EXIT_CHATROOM, cr.getTitle(), client.getMyId() ));
						dispose();
					}else{
						client.sendData(new SendData(
								CHANGE_CHATROOM_HEAD_PLEASE, crTitle,selectedId));
						dispose();
					}
				}else{
					Utils.showMsg(ChangeHeadFrame.this, "선택된 사람이 없는디유");
				}
			}
		});
		
	}
	
	
	private void showFrame(){
		setTitle("방장변경하기");
		pack();
		setLocation(100, 100);
		setVisible(true);
	}

}
