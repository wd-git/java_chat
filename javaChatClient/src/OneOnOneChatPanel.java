import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class OneOnOneChatPanel extends JFrame implements Protocol {
	private JTextArea chatArea;
	private JTextField chatField;
	private Client client;
	private User user;
	private User targetUser;

	/*
	 * 190704 이재영 추가 멤버변수 추가
	 */
	private JLabel lblInfo;
	private JButton btnSend;
	private JButton btnShake;

	public OneOnOneChatPanel(Client client, User user, User targetUser) {
		this.client = client;
		this.user = user;
		this.targetUser = targetUser;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		chatArea = new JTextArea(21, 43);
		chatArea.setEnabled(false);
		chatArea.setLineWrap(true);
		chatField = new JTextField(43);

		lblInfo = new JLabel(targetUser.getId() + " 님과 1:1 대화중");
		btnSend = new JButton("전송");
		btnShake = new JButton("한번 흔들어 볼라예");
	}

	private void setDisplay() {
		JScrollPane scroll = new JScrollPane(chatArea);
		chatArea.setEditable(false);

		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(chatField);
		pnlSouth.add(btnSend);
		pnlSouth.add(btnShake);

		add(lblInfo, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void addListeners() {

		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!chatField.getText().equals(null)) {
					chatArea.append(user + " : " + chatField.getText() + "\n");
					String msg = chatField.getText();
					client.sendData(new SendData(SEND_ONEONONE_MSG, user,
							targetUser, msg));
					chatField.setText("");
					System.out.println("야이 쓱을놈아 액션이벤트는 되는지 두고보겠어");
				}
			}
		};

		chatField.addActionListener(aListener);
		btnSend.addActionListener(aListener);

		WindowListener listener = new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				client.sendData(new SendData(ONEONONECHAT_OUT, user, targetUser));
				System.out.println("윈도우 클로즈시에 되나 안되나보자");
			}
		};
		addWindowListener(listener);

		btnShake.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendData(new SendData(SHAKE_IT_BABY_ONE_ON_ONE_PLEASE,
						targetUser.getId(), user.getId()));
				shakeItDogBaby(user.getId());
			}
		});
	}

	public void shakeItDogBaby(String id) {
		chatArea.append(id + "님이 흔들어 제껴 버렸슈 ~\n");
		btnSend.setEnabled(false);
		chatField.setEnabled(false);
		int count = 0;
		Point tempPoint = getLocation();
		while (count != 500) {
			setLocation((int) tempPoint.getX() + 3, (int) tempPoint.getY() + 3);
			pack();
			setLocation((int) tempPoint.getX(), (int) tempPoint.getY());
			pack();
			count++;
		}
		
		btnSend.setEnabled(true);
		chatField.setEnabled(true);
		revalidate();
		repaint();
		

	}

	public JTextField getChatField() {
		return chatField;
	}
	

	public void setChatField(JTextField chatField) {
		this.chatField = chatField;
	}

	public JTextArea getChatArea() {
		return chatArea;
	}

	public void setChatArea(JTextArea chatArea) {
		this.chatArea = chatArea;
	}

	private void showFrame() {
		setTitle(targetUser + "님과 1:1 대화 중");
		pack();
		setLocation(100, 100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		chatField.requestFocus();

	}

}
