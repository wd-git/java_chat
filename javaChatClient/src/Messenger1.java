import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Messenger1 extends JFrame implements Protocol {
	private JLabel lblMsg;
	private JLabel lblId;
	private JLabel lblTargetId;
	private JTextArea taMsg;
	private JButton btnOk;
	private JButton btncancel;
	private String msg;
	private User user;
	private User tagetUser;
	private Client client;
	private MemberPanel mp;

	public Messenger1(String msg,Client client,User user,User tagetUser) {
		this.client = client;
		this.user = user;
		this.mp = mp;
		this.msg = msg;
		this.tagetUser = tagetUser;
		
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	
	private void init() {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		lblTargetId = new JLabel("보낼 사람 : "+ user);
		lblId = new JLabel("받은 사람 : " + tagetUser);
		lblMsg = new JLabel("메세지가 도착하였습니다.");
		taMsg = new JTextArea(3, 30);
		taMsg.setText(msg);
		btnOk = new JButton("답장하기");
		btncancel = new JButton("취소");
		
		taMsg.setLineWrap(true);
	
	}
	private void setDisplay() {
		JPanel pnllbl = new JPanel(new GridLayout(3,1));
		pnllbl.add(lblTargetId);
		pnllbl.add(lblId);
		pnllbl.add(lblMsg);
		
		JPanel pnlta = new JPanel();
		JScrollPane scroll = new JScrollPane(taMsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		pnlta.add(scroll);
		
		JPanel pnlbtn = new JPanel();
		pnlbtn.add(btnOk);
		pnlbtn.add(btncancel);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(pnllbl, BorderLayout.NORTH);
		pnlCenter.add(pnlta, BorderLayout.CENTER);
		pnlCenter.add(pnlbtn, BorderLayout.SOUTH);
		
		add(pnlCenter, BorderLayout.CENTER);
	}
	private void addListener() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = taMsg.getText();
				System.out.println(msg);
				client.sendData(new SendData(SEND_NOTE_TO_ME, msg,user,tagetUser));
				System.out.println("쪽지 전송 잘되나");
				dispose();
			}
		});
		btncancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
	}
	
	private void showFrame() {
		setTitle("쪽지도착");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
