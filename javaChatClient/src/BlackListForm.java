import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.ws.handler.MessageContext.Scope;


public class BlackListForm extends JFrame implements Protocol {
	private JList<User> blackJList;
	private JButton btnDel;
	private Client client;
	private DefaultListModel<User> blackList;
	private JScrollPane scroll;
	
	public BlackListForm(Client client,Vector<User> blackList1) {
		this.client = client;
				
		blackList = new DefaultListModel<User>();
		for(User temp : blackList1){
			blackList.addElement(temp);
		}
		
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init() {
		
		blackJList = new JList<User>(blackList);
		blackJList.setPreferredSize(new Dimension(300, 400));
		btnDel = new JButton("삭제");
		btnDel.setFont(new Font("바탕", Font.BOLD,15));
	}
	
	public void updateUi(User user){
		blackList.removeElement(user);
		blackJList.updateUI();
		Utils.showMsg(this,user.getId()+ "이 블랙리스트에서 삭제되었습니다");
		dispose();
		
	}
	
	private void setDisplay() {
		JPanel pnlCenter = new JPanel();
		scroll = new JScrollPane(blackJList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnlCenter.add(scroll);
		
		add(pnlCenter, BorderLayout.CENTER);
		add(btnDel, BorderLayout.SOUTH);
	}
	
	private void addListeners() {
		btnDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				User targetUser = blackJList.getSelectedValue();
				String myId = client.getMyId();
				client.sendData(new SendData(DELETE_BLACKLIST, myId,targetUser));
				
			}
		});
	}
	
	private void showFrame() {
		setTitle("블랙리스트 목록");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
