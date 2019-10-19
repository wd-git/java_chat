import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class MyProfileForm extends JFrame implements Protocol {
	private JLabel lblPhoto;
	private JLabel lblId;
	private JLabel lblPw;
	private JLabel lblNick;
	private JLabel lblName;
	private JLabel lblMail;
	private JTextField tfId;
	private JPasswordField tfPw;
	private JTextField tfNick;
	private JTextField tfName;
	private JTextField tfMail;

	private JButton btnOpen; // 사진열기
	private JButton btnBlackList; // 차단목록보기
	private JButton btnChange; // 수정
	private JButton btnWithdraw; // 탈퇴
	private JButton btnCancel; // 취소
	private User user;
	private Client client;

	public MyProfileForm(User user, Client client, boolean nowState) {
		this.user = user;
		this.client = client;
		init();
		setDisplay();
		addListeners();
		showFrame();
		
		if(nowState==false){
			btnWithdraw.setEnabled(false);
		}
		

	}

	private void init() {
		lblPhoto = new JLabel();
		lblId = new JLabel("ID");
		lblPw = new JLabel("비밀번호");
		lblNick = new JLabel("NickName");
		lblName = new JLabel("Name");
		lblMail = new JLabel("E-Mail");
		tfId = new JTextField(10);
		tfPw = new JPasswordField(10);
		tfId.setEditable(false);
		tfNick = new JTextField(10);
		tfName = new JTextField(10);
		tfMail = new JTextField(10);
		tfId.setText(user.getId());
		tfPw.setText(user.getPw());
		tfNick.setText(user.getNickname());
		tfName.setText(user.getName());
		tfMail.setText(user.getEmail());

		lblPhoto.setBorder(new LineBorder(Color.blue));
		lblPhoto.setPreferredSize(new Dimension(150, 100));
		lblPhoto.setIcon(user.getProfileIcon());

		btnOpen = new JButton("열기");
		btnOpen.setFont(new Font("고딕", Font.BOLD, 8));
		btnBlackList = new JButton("내가 차단한 목록 보기");
		btnBlackList.setBackground(Color.red);
		btnChange = new JButton("수정");
		btnWithdraw = new JButton("탈퇴");
		btnCancel = new JButton("취소");
	}
	
	private void setDisplay() {
		JPanel pnlPhoto = new JPanel();
		pnlPhoto.add(lblPhoto);

		JPanel pnlbtnOpen = new JPanel();
		pnlbtnOpen.add(btnOpen);
		pnlbtnOpen.setPreferredSize(new Dimension(35, 25));

		JPanel pnllblId = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblId.add(lblId);

		JPanel pnllblPw = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblPw.add(lblPw);

		JPanel pnllblNick = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblNick.add(lblNick);

		JPanel pnllblName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblName.add(lblName);

		JPanel pnllblMail = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblMail.add(lblMail);

		JPanel pnltfId = new JPanel();
		pnltfId.add(tfId);

		JPanel pnltfPw = new JPanel();
		pnltfPw.add(tfPw);

		JPanel pnltfNick = new JPanel();
		pnltfNick.add(tfNick);

		JPanel pnltfName = new JPanel();
		pnltfName.add(tfName);

		JPanel pnltfMail = new JPanel();
		pnltfMail.add(tfMail);

		JPanel pnlBlackList = new JPanel();
		pnlBlackList.add(btnBlackList);

		JPanel pnlBtn = new JPanel();
		pnlBtn.add(btnChange);
		pnlBtn.add(btnWithdraw);
		pnlBtn.add(btnCancel);

		JPanel pnlNorth = new JPanel();
		pnlNorth.add(pnlPhoto);
		pnlNorth.add(pnlbtnOpen);

		JPanel pnlWest = new JPanel(new GridLayout(0, 1));
		pnlWest.add(pnllblId);
		pnlWest.add(pnllblPw);
		pnlWest.add(pnllblNick);
		pnlWest.add(pnllblName);
		pnlWest.add(pnllblMail);

		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		pnlCenter.add(pnltfId);
		pnlCenter.add(pnltfPw);
		pnlCenter.add(pnltfNick);
		pnlCenter.add(pnltfName);
		pnlCenter.add(pnltfMail);

		JPanel pnlSouth = new JPanel(new GridLayout(0, 1));
		pnlSouth.add(pnlBlackList);
		pnlSouth.add(pnlBtn);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlWest, BorderLayout.WEST);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void addListeners() {

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});

		btnWithdraw.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,
						"탈퇴한다는 소문이 참말입니까?", "회원탈퇴", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					String password = JOptionPane.showInputDialog(null,
							"아쉽네예 비밀번호라도 입력해보소", "비밀번호 입력",
							JOptionPane.INFORMATION_MESSAGE);
					String id = tfId.getText();
					withdraw(id, password);
				}
			}
		});

		btnChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane
						.showConfirmDialog(null, "회원정보를 수정하시겠습니까?", "회원정보 수정",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					String password = JOptionPane.showInputDialog(null,
							"기존 비밀번호를 입력해주세요", "비밀번호 입력",
							JOptionPane.INFORMATION_MESSAGE);

					modifyMyProfile(password);

				}

			}
		});
		btnBlackList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendData(new SendData(SHOW_BLACKLIST, client.getMyId()));
				
				
			}
		});
	}

	public void modifyMyProfile(String password) {
		String id = tfId.getText();
		String pw = tfPw.getText();
		String nickname = tfNick.getText();
		String name = tfName.getText();
		String email = tfMail.getText();

		User[] data = {
				new User(id, password),new User(id, pw, name, nickname, email, lblPhoto.getIcon())
				 };
		
		
		client.sendData(new SendData(MODIFY_MY_PROFILE, data));
		System.out.println("수정데이터 전송됨");

	}

	public void withdraw(String id, String pw) {
		// 텍스트필드에 입력되있는 아이디와 inputDialog에서 비밀번호를 받아서 서버에 전달한다.
		String[] data = { id, pw };
		client.sendData(new SendData(WITHDRAW, data));

	}

	private void showFrame() {
		setTitle("나의 프로필 보기");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
