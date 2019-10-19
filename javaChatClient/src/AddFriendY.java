import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class AddFriendY extends JFrame implements Protocol {
	private JLabel lblMsg;
	private JTextArea taMsg;
	private JButton btnYes;
	private JButton btnNo;
	private Client client;
	private String targetId;
	private String myId;
	private String msg;
	private User user;

	/*
	 * 정상욱 수정
	 */

	public AddFriendY(Client client, String targetId, String myId, String msg) {
		this.client = client;
		this.targetId = targetId;
		this.myId = myId;
		this.msg = msg;

		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/*
	 * 정상욱 수정
	 */

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		lblMsg = new JLabel(myId + "님으로 부터 친구요청이 도착했습니다");
		taMsg = new JTextArea(3, 30);
		taMsg.setText(msg);
		btnYes = new JButton("승인");
		btnNo = new JButton("거절");

		taMsg.setLineWrap(true);
	}

	private void setDisplay() {
		JPanel pnllbl = new JPanel();
		pnllbl.add(lblMsg);

		JPanel pnlta = new JPanel();
		JScrollPane scroll = new JScrollPane(taMsg,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnlta.add(scroll);

		JPanel pnlYes = new JPanel();
		pnlYes.add(btnYes);

		JPanel pnlNo = new JPanel();
		pnlNo.add(btnNo);

		JPanel pnlbtn = new JPanel();
		pnlbtn.add(pnlYes);
		pnlbtn.add(pnlNo);

		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(pnllbl, BorderLayout.NORTH);
		pnlCenter.add(pnlta, BorderLayout.CENTER);
		pnlCenter.add(pnlbtn, BorderLayout.SOUTH);

		add(pnlCenter, BorderLayout.CENTER);
	}

	/*
	 * 정상욱 수정 상대로 부터 초대 받고 수락,거절 리스너
	 */

	private void addListeners() {
		btnNo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] data = { myId, targetId };
				client.sendData(new SendData(RESPOND_NO_REQUSET_ADD_FRIEND,
						data));
				dispose();

			}
		});
		btnYes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] data = { myId, targetId };
				client.sendData(new SendData(RESPOND_YES_REQUSET_ADD_FRIEND,
						data));
				dispose();

			}
		});

	}

	private void showFrame() {
		setTitle("친구 등록 요청");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
