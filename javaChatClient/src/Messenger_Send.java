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


public class Messenger_Send extends JFrame implements Protocol {
	private JLabel lblMsg;
	private JLabel lblId;
	private JLabel lblTarget;
	private JTextArea taMsg;
	private JButton btnOk;
	private JButton btncancel;
	private Client client;
	private MemberPanel mp;
	private String msg;
	
	/*
	 * 190702 ���翵 ������� �߰�
	 * */
	private String myId;
	private String targetId;
	
	public Messenger_Send(Client client, String myId, String targetId){
		this.client = client;
		this.myId = myId;
		this.targetId = targetId;
		
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	
	private void init() {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		lblTarget = new JLabel("�޴� ��� : " + targetId);
		lblId = new JLabel("������ ��� : " + client.getMyId());
		lblMsg = new JLabel("���� �޽����� �Է��� �ּ���");
		taMsg = new JTextArea(3, 30);
		btnOk = new JButton("Ȯ��");
		btncancel = new JButton("���");
		taMsg.setText(msg);
		taMsg.setLineWrap(true);
	}
	
	private void setDisplay() {
		JPanel pnllbl = new JPanel(new GridLayout(3,1));
		pnllbl.add(lblTarget);
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

	private void addListeners() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(taMsg.getText().length() < 200){
					String msg = taMsg.getText();
					client.sendData(new SendData(NOTE_SEND_PLEASE, 
							msg,myId,targetId));
					//������ ������ �������� send �� Ŭ���̾�Ʈ ���̵� �־ ����
					client.getUser().addNote(new Note(myId,targetId,msg,Utils.currentTimeToString()));
					dispose();
				}else{
					Utils.showMsg(Messenger_Send.this, "200�� ���ϸ� ���� �����մϴ�.");
				}
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
		setTitle("����������");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
