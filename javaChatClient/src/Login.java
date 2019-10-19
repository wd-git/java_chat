import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Login extends JFrame {
	private String id;
	private String pw;
	private String ip;
	private Socket s;
	private JLabel lblId;
	private static JLabel lblPw;
	private JTextField tfId;
	private JPasswordField pfPw;
	private JButton btnLogin;
	private JButton btnJoin;
	private static JButton btnIdFind;
	private static JButton btnPwFind;
	private Dimension lblSize = new Dimension(20, 20);
	private Dimension btnSize = new Dimension(65, 30);
	private Client client;
	private Join join;
	private JScrollPane scrollPane;
	private ImageIcon icon;
	private JPanel background;
	private JPanel mainPanel;

	public Login(Client client) {
		
		icon = new ImageIcon("files/login.jpg");
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        background = new JPanel() {
            public void paintComponent(Graphics g) {
                // Approach 1: Dispaly image at at full size
                g.drawImage(icon.getImage(), 0, 0, null);
                // Approach 2: Scale image to size of component
                // Dimension d = getSize();
                // g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                // Approach 3: Fix the image position in the scroll pane
                // Point p = scrollPane.getViewport().getViewPosition();
                // g.drawImage(icon.getImage(), p.x, p.y, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
                setLayout(null);
            }
        };

		this.client = client;
		
		Thread music = new Thread() {
			@Override
			public void run() {
				Utils.play(Utils.LOGIN_BGM);	
			}
			
		};
		
		music.setDaemon(true);
		music.start();
		
		init();
		setDisplay();
		addListeners();
		showFrame();

	}

	public void login() {

	}

	public void lookupId() {

	}

	public void lookupPw() {

	}

	public void withdraw() {

	}

	public void join() {

	}

	public void verifyId() {

	}

	public void init() {
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		lblId = new JLabel(new ImageIcon("files/ID.png"));
		lblId.setPreferredSize(lblSize);
		lblPw = new JLabel(new ImageIcon("files/PW.png"));
		lblPw.setPreferredSize(lblSize);
		tfId = new JTextField(9);
		pfPw = new JPasswordField(9);
		btnLogin = new JButton(new ImageIcon("files/로그인.png"));
		btnLogin.setBorderPainted(false);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setFocusPainted(false);
		btnLogin.setOpaque(false);
		btnJoin = new JButton(new ImageIcon("files/회원가입.png"));
		Utils.btnSetting(btnJoin);
		btnJoin.setPreferredSize(btnSize);
		btnIdFind = new JButton(new ImageIcon("files/ID찾기.png"));
		Utils.btnSetting(btnIdFind);
		btnIdFind.setPreferredSize(btnSize);
		btnPwFind = new JButton(new ImageIcon("files/PW찾기.png"));
		btnPwFind.setPreferredSize(btnSize);
		Utils.btnSetting(btnPwFind);

	}

	public void setDisplay() {
		mainPanel = new JPanel(new BorderLayout());
		JPanel pnlCenterOfCenter = new JPanel(new BorderLayout());
		pnlCenterOfCenter.setOpaque(false);
		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		pnlCenter.setOpaque(false);

		JPanel pnlId = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlId.add(lblId);
		pnlId.add(tfId);
		pnlId.setOpaque(false);

		JPanel pnlPw = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPw.add(lblPw);
		pnlPw.add(pfPw);
		pnlPw.setOpaque(false);
			
		pnlCenter.add(pnlId);
		pnlCenter.add(pnlPw);

		pnlCenterOfCenter.add(pnlCenter, BorderLayout.CENTER);
		btnLogin.setPreferredSize(new Dimension(70, 70));
		pnlCenterOfCenter.add(btnLogin, BorderLayout.EAST);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnJoin);
		pnlSouth.add(btnIdFind);
		pnlSouth.add(btnPwFind);
		pnlSouth.setOpaque(false);

		mainPanel.add(pnlCenterOfCenter, BorderLayout.CENTER);
		mainPanel.add(pnlSouth, BorderLayout.SOUTH);

	}

	public void addListeners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == btnJoin) {
					join = new Join(client);
				} else if (src == btnIdFind) {
					new FindIdForm(client);
				} else if (src == btnPwFind) {
					new FindPwForm(client);
				}
			}
		};

		btnJoin.addActionListener(listener);
		btnIdFind.addActionListener(listener);
		btnPwFind.addActionListener(listener);

		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// 로그인 버튼을 클릭하면 (공백이 아닐경우)서버에 전송
				String id = tfId.getText();
				String pw = pfPw.getText();
				sendLoginInfoToServer(id, pw);
			}
		});
		
		
		MouseAdapter buttonMouseEffect = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				new Thread() {
					public void run() {
						Utils.play(Utils.MOUSEIN_SOUND);	
					}
				}.start();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new Thread() {
					public void run() {
						Utils.play(Utils.CLICK_SOUND);	
					}
				}.start();
			}
		};
		
		btnLogin.addMouseListener(buttonMouseEffect);
		btnJoin.addMouseListener(buttonMouseEffect);
		btnIdFind.addMouseListener(buttonMouseEffect);
		btnPwFind.addMouseListener(buttonMouseEffect);
		

	}

	public Join getJoin() {
		return join;
	}

	public void setJoin(Join join) {
		this.join = join;
	}

	public void sendLoginInfoToServer(String id, String pw) {
		SendData loginData = new SendData(100, id, pw);
		client.sendLoginInfo(loginData);
	}

	public void showFrame() {
		background.add(mainPanel);
		scrollPane = new JScrollPane(background);
	    setContentPane(scrollPane);
	    
	    mainPanel.setOpaque(false);
	    
	
		setTitle("CRAZY 채팅");
		setSize(500, 440);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		mainPanel.setBounds(150,280,210,100);
		
	}

}