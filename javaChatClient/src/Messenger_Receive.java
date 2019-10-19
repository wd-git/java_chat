import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Messenger_Receive extends JFrame implements Protocol {
	private JLabel lblMsg;
	private JLabel lblId;
	private JLabel lblTargetId;
	private JLabel lblCurrentTime;
	private JTextArea taMsg;
	private JButton btnOk;
	private JButton btncancel;
	private String msg;
	private User user;
	private User targetUser;
	private Client client;
	private MemberPanel mp;
	
	/*
	 * 190702 이재영 수정
	 * */
	private String myId;
	private String targetId;

	public Messenger_Receive(Client client, String msg, String myId, String targetId){
		this.client = client;
		this.msg = msg;
		this.myId = myId;
		this.targetId = targetId;
		
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	
	
	private void init() {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		lblTargetId = new JLabel("From: "+ myId);
		lblId = new JLabel("To : " + targetId);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy년 MM월 dd일 E요일 HH시 mm분" );
		lblCurrentTime = new JLabel(sdf.format(date));
		lblMsg = new JLabel("메세지가 도착하였습니다.");
		taMsg = new JTextArea(3, 30);
		taMsg.setText(msg);
		btnOk = new JButton("답장하기");
		btncancel = new JButton("취소");
		
		taMsg.setLineWrap(true);
	
	}
	private void setDisplay() {
		JPanel pnllbl = new JPanel(new GridLayout(0,1));
		pnllbl.add(lblTargetId);
		pnllbl.add(lblId);
		pnllbl.add(lblMsg);
		pnllbl.add(lblCurrentTime);
		
		JPanel pnlta = new JPanel();
		JScrollPane scroll = new JScrollPane(taMsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		taMsg.setEnabled(false);
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
		//놀랍게도 답장하기 버튼이다
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Messenger_Send(client, targetId, myId);
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
