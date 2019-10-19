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
	 * �ȳ��ϼ��� ���� ������ 1. ä�ù濡�� ������ �����Ҷ�
	 * 
	 * �����е��� ���̵� ���� ���� ���� ���ؼ�
	 * 
	 * 2. ä�ù� �������Ʈ�� JList���·� ����
	 * 
	 * 3. ���õ� ������ �������� "������ �ǻ�ʹ� �������" �ٲ�����̴ϴ社��
	 * 
	 * 4. �̶� Ȯ�� ��ư�� ������ �����Ҳ�����~
	 * 
	 * �׷� �� ��Ź�����
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
	 * 190702���翵 ��������� �޼ҵ� ����
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
		btnConfirm = new JButton("�ʹ�����");
	}
	
	private void setDisplay(){
		JPanel pnlNorth = new JPanel();
		JLabel lblNorth = new JLabel("���� �� ����� �ѹ� ��󺸼���",JLabel.CENTER);
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
		 * ���õ� ����� �����ͼ� ��û�� ����������~
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
					Utils.showMsg(ChangeHeadFrame.this, "���õ� ����� ���µ���");
				}
			}
		});
		
	}
	
	
	private void showFrame(){
		setTitle("���庯���ϱ�");
		pack();
		setLocation(100, 100);
		setVisible(true);
	}

}
