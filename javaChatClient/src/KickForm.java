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
	 * ���翵 ���� �Ӹ��ٲٱ��������� ���� �����ͼ� ���� ��� ���� �����ϴ�.
	 * �� ��¼�ھ��?
	 * �ٵ� ����Ʈ���� ���� ���ż� �� �ƽð�����..
	 * �̷��� ���α׷� ��������? 
	 * �ȳ�..
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
		btnConfirm = new JButton("�� ����");
		
	}
	
	private void setDisplay(){
		JPanel pnlNorth = new JPanel();
		JLabel lblNorth = new JLabel("���� ��������..?? ^��^? ",JLabel.CENTER);
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
				
				// ����� �츮�� �Բ� �� �� �����ϴ�
				
				client.sendData(new SendData(
						KICKOUT_CHATROOM, cr, selectedId ));
				dispose();
			}
		});
		
	}
	
	private void showFrame(){
		setTitle("����");
		pack();
		setLocationRelativeTo(client.getCf());
		setVisible(true);
	}

}
