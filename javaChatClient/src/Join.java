import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

public class Join extends JFrame implements ActionListener,Protocol {
	public static final int PROFILEIMAGE = 0;
	public static final int ID = 1;
	public static final int PW = 2;
	public static final int RE = 3;
	public static final int NAME = 4;
	public static final int NICK = 5;
	public static final int EMAIL = 6;
	private Dimension btnSize = new Dimension();

	public String[] names = { "ID", "Password", "Retry", "Name", "NickName",
			"E-MAIL" };

	private JButton btnJoin;
	private JButton btnCancel;
	private JTextComponent[] inputs;
	private JButton btnOpen;
	private JButton btnOverlap;
	private Client client;
	private JLabel lblProfile;
	private boolean overlapCheck;

	public Join(Client client) {
		this.client = client;
		overlapCheck = false;
		init();
		setDisplay();
		addListener();
		showDlg();
	}

	private void init() {
		lblProfile = new JLabel("", JLabel.CENTER);
		btnJoin = LoginUtils.getButton("가입");
		btnJoin.setEnabled(true);

		btnCancel = LoginUtils.getButton("취소");
		btnOpen = LoginUtils.getButton("열기");

		btnOverlap = LoginUtils.getButton("중복검사");
		inputs = new JTextComponent[] {
				LoginUtils.getTextComponent(LoginUtils.TEXT),
				LoginUtils.getTextComponent(LoginUtils.PASSWORD),
				LoginUtils.getTextComponent(LoginUtils.PASSWORD),
				LoginUtils.getTextComponent(LoginUtils.TEXT),
				LoginUtils.getTextComponent(LoginUtils.TEXT),
				LoginUtils.getTextComponent(LoginUtils.TEXT) };

	}

	private void setDisplay() {
		JPanel pnlProfile = new JPanel(new BorderLayout());
		lblProfile.setPreferredSize(new Dimension(150, 150));
		lblProfile.setBorder(new LineBorder(Color.BLACK));
		pnlProfile.add(lblProfile, BorderLayout.CENTER);

		JPanel pnlOpen = new JPanel();
		pnlOpen.add(btnOpen);
		pnlProfile.add(pnlOpen, BorderLayout.SOUTH);

		add(pnlProfile, BorderLayout.NORTH);

		JPanel pnlMain = new JPanel(new BorderLayout());

		JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
		pnlNorth.setBorder(new EmptyBorder(0, 10, 0, 0));
		JPanel pnlSet[] = new JPanel[names.length];

		for (int i = 0; i < inputs.length; i++) {
			pnlSet[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnlSet[i].add(LoginUtils.getLabel(names[i]));
			pnlSet[i].add(inputs[i]);
			pnlNorth.add(pnlSet[i]);
		}

		pnlSet[0].add(btnOverlap);

		JPanel pnlBtns = new JPanel();
		pnlBtns.add(btnJoin);
		pnlBtns.add(btnCancel);

		pnlMain.add(pnlNorth, BorderLayout.CENTER);
		pnlMain.add(pnlBtns, BorderLayout.SOUTH);

		add(pnlMain, BorderLayout.CENTER);
	}

	private void addListener() {
		btnJoin.addActionListener(this);
		btnOverlap.addActionListener(this);
		btnCancel.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});

		btnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image = null;
				JFileChooser openDialog = new JFileChooser(".");
				int option = openDialog.showOpenDialog(Join.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					try {
						image = ImageIO.read(openDialog.getSelectedFile());
						image = Utils.resize(image);
						lblProfile.setIcon(new ImageIcon(image));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
	}

	public void toEnableButton(boolean flag) {
		btnJoin.setEnabled(flag);
	}

	private void showDlg() {
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private void join() {
		// 유저객체 만들어서 서버로 전달
		String id = inputs[0].getText();
		String pw = inputs[1].getText();
		String nickname = inputs[3].getText();
		String name = inputs[4].getText();
		String email = inputs[5].getText();

		User[] data = { new User(id, pw, nickname, name, email,
				lblProfile.getIcon()) };
		client.sendData(new SendData(200, data));
	}

	private void checkOverlapId(String id) {
		// 클라이언트가 서버로 sendData 보내서 중복요청 서버가 확인해서 보내줌
		System.out.println("눼에에에에ㅔ~~");
		SendData joinData = new SendData(VERIFY_ID, id);
		client.sendData(joinData);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		if (src == btnJoin) {

			if (checkTextField()) {
				if (!checkPw()) {
					JOptionPane.showMessageDialog(this, "패스워드 입력값이 다름");
				} else {
					if(overlapCheck){
						join();
						dispose();
					}else{
						Utils.showMsg(this, "아이디 중복체크를 하세요");
					}
					
				}
			} else {
				JOptionPane.showMessageDialog(this, "입력하세요");
			}

		} else if (src == btnOverlap) {
			checkOverlapId(inputs[0].getText());
		} else if (src == btnCancel) {
			dispose();
		}

	}

	public void changeState(){
		overlapCheck = true;
	}
	
	private boolean checkTextField() {
		for (int i = 0; i < 6; i++) {
			if (inputs[i].getText().length() == 0) {
				return false;
			}
		}
		return true;
	}

	private boolean checkPw() {
		if (!(inputs[1].getText().equals((inputs[2]).getText()))) {
			return false;
		}
		return true;
	}

	public void showFrame() {
		setTitle("회원가입");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}
}
