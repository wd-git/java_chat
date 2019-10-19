import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// 메인 프레임

public class ClientForm extends JFrame implements Protocol {
	/*
	 * 190624 이재영 수정
	 */
	private JLabel lblId;
	private JLabel lblNick;
	private JLabel lblRoomInfo;
	private ChatPanel cp;
	private FriendPanel fp;
	private MemberPanel mp;
	private MemberPanel mpInChat;
	private WatingRoomPanel wp;
	private JButton btnProfile;
	private JButton btnNoteBox;
	private JButton btnExit;
	private JButton btnUserSearch;

	private Client client;
	private User myUser;

	private JPanel pnlCenter;
	private JPanel pnlEast;
	private JPanel pnlEastMain;
	
	private ImageIcon iconLeft;
	private JPanel backgroundLeft;
	private ImageIcon iconRight;
	private JPanel backgroundRight;

	/*
	 * 
	 * 멤버 변수 추가 nowState / waitingRoomList true - WaitingRoom false - ChatRoom
	 */

	private boolean nowState;
	private WaitingRoom wr;

	public ClientForm(Client client, WaitingRoom wr) {

		this.client = client;
		this.wr = wr;
		this.myUser = client.getUser();

		init();
		setDisplay();
		addListener();
		showframe();
	}

	private void init() {
		/*
		 * 190624 이재영 수정
		 */
		nowState = true;
		lblId = new JLabel(myUser.getId());
		lblId.setOpaque(true);
		lblNick = new JLabel("(" + myUser.getNickname() + ") 님 위치 : ");
		lblNick.setOpaque(true);
		lblRoomInfo = new JLabel("대기실");
		lblRoomInfo.setOpaque(true);
		fp = new FriendPanel(client);
		fp.setOpaque(false);
		mp = new MemberPanel(client, wr);
		mp.setOpaque(false);
		wp = new WatingRoomPanel(client, wr);
		wp.setOpaque(false);

		btnProfile = new JButton(new ImageIcon("files/내정보보기.png"));
		Utils.btnSetting(btnProfile);
		btnNoteBox = new JButton(new ImageIcon("files/쪽지함.png"));
		Utils.btnSetting(btnNoteBox);
		btnExit = new JButton(new ImageIcon("files/나가기.png"));
		Utils.btnSetting(btnExit);
		btnUserSearch = new JButton(new ImageIcon("files/유저검색.png"));
		Utils.btnSetting(btnUserSearch);
	}

	public MemberPanel getMpInChat() {
		return mpInChat;
	}

	public void setMpInChat(MemberPanel mpInChat) {
		this.mpInChat = mpInChat;
	}

	public JPanel getPnlEastMain() {
		return pnlEastMain;
	}

	public void setPnlEastMain(JPanel pnlEastMain) {
		this.pnlEastMain = pnlEastMain;
	} 

	// 원도 25일 오후 4시 33분;
	public void createChatRoom(ChatRoom cr) {
		
		nowState = false;
		pnlCenter.removeAll();

		
		cp = new ChatPanel(cr, client);
		cp.setOpaque(false);
		pnlCenter.add(cp, BorderLayout.CENTER);
		pnlCenter.setOpaque(false);
		
		
		pnlEastMain.removeAll();
		mpInChat = new MemberPanel(client, cr);
		pnlEastMain.add(mpInChat);
		
		pnlEast.add(pnlEastMain, BorderLayout.NORTH);
		backgroundRight.add(pnlEast, BorderLayout.EAST);
		
		pnlEastMain.revalidate();
		pnlEastMain.repaint();
		pnlEast.revalidate();
		pnlEastMain.repaint();
		pnlCenter.revalidate();
		pnlCenter.repaint();
		
		btnUserSearch.setVisible(false);
		
		revalidate();
		repaint();
	}

	/*
	 * 190624 재영추가 채팅룸에 새로 만들어 졌을때 나머지 웨이팅 룸에 있s는 사람들의 UI업데이트 그리고 대기자리스트도
	 * 업데이트(채팅방 들어간놈 OUT!)
	 */
	// public void updateByCreateChatRoom(){
	// wp.
	// }
	
	// 원도 25일 오후 4시 33분;
	// 원도 26일 오후 12시 
	public void returnToWaitingRoom(WaitingRoom wr) {
		nowState = true;
//		pnlMain.removeAll();
//		pnlMain.add(wp, BorderLayout.CENTER);
//		wp.updateWaitingRoom();
//		
//		repaint();
//		revalidate();

		pnlCenter.removeAll();
		wp.setWr(wr);
		wp = new WatingRoomPanel(client, wr);
		wp.setOpaque(true);
		pnlCenter.add(wp, BorderLayout.CENTER);

		pnlEastMain.removeAll();
		pnlEastMain.setOpaque(false);
		mp = new MemberPanel(client, wr);
		pnlEastMain.add(mp);
		pnlEastMain.revalidate();
		pnlEastMain.repaint();
		
		
		pnlEast.add(pnlEastMain, BorderLayout.NORTH);
		backgroundRight.removeAll();
		backgroundRight.add(pnlEast);
		
		pnlEast.setOpaque(false);
		
		if(nowState){
			btnUserSearch.setVisible(true);
		}
		
		repaint();
		revalidate();
		
		lblRoomInfo.setText("대기실");
	}

	private void setDisplay() {
		pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setOpaque(false);
		
		iconLeft = new ImageIcon("files/왼쪽.jpg");
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        backgroundLeft = new JPanel() {
            public void paintComponent(Graphics g) {
                // Approach 1: Dispaly image at at full size
                g.drawImage(iconLeft.getImage(), 0, 0, null);
                // Approach 2: Scale image to size of component
                // Dimension d = getSize();
                // g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                // Approach 3: Fix the image position in the scroll pane
                // Point p = scrollPane.getViewport().getViewPosition();
                // g.drawImage(icon.getImage(), p.x, p.y, null);
                setOpaque(false); //그림을 표시하게 설정,s투명하게 조절
                super.paintComponent(g);
                setLayout(null);
            }
        };
        
        iconRight = new ImageIcon("files/오른쪽.jpg");
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        backgroundRight = new JPanel() {
            public void paintComponent(Graphics g) {
                // Approach 1: Dispaly image at at full size
                g.drawImage(iconRight.getImage(), 0, 0, null);
                // Approach 2: Scale image to size of component
                // Dimension d = getSize();
                // g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                // Approach 3: Fix the image position in the scroll pane
                // Point p = scrollPane.getViewport().getViewPosition();
                // g.drawImage(icon.getImage(), p.x, p.y, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
                //setLayout());
            }
        };
        
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlNorth.add(lblId);
		Utils.setLabelToDefaultFont(lblId);
		pnlNorth.add(lblNick);
		Utils.setLabelToDefaultFont(lblNick);
		pnlNorth.add(lblRoomInfo);
		Utils.setLabelToDefaultFont(lblRoomInfo);
		pnlNorth.setOpaque(false);

		backgroundLeft.add(pnlMain);
		backgroundLeft.setOpaque(false);
		pnlCenter.add(wp, BorderLayout.CENTER);
		pnlCenter.setOpaque(false);
		pnlEast = new JPanel(new BorderLayout());
		pnlEastMain = new JPanel();
		pnlEast.setOpaque(false);
		pnlEastMain.add(mp);
		pnlEastMain.setOpaque(false);
		
		pnlEast.add(pnlEastMain, BorderLayout.NORTH);
		pnlEast.add(fp, BorderLayout.CENTER);
		
		JPanel pnlSouth = new JPanel(new GridLayout(0, 1));
		if(nowState){
			pnlSouth.add(btnUserSearch);
		}
		pnlSouth.add(btnProfile);
		pnlSouth.add(btnNoteBox);
		pnlSouth.add(btnExit);
		pnlSouth.setOpaque(false);
		pnlSouth.setBorder(new EmptyBorder(100,0,0,0));
		
		pnlEast.add(pnlSouth, BorderLayout.SOUTH);
		pnlEast.setOpaque(false);

		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.setOpaque(false);
		pnlMain.setBackground(new Color(58,119,176));
		
		backgroundRight.add(pnlEast);
		backgroundRight.setOpaque(false);
		
		add(backgroundLeft, BorderLayout.CENTER);
		add(backgroundRight, BorderLayout.EAST);
		
	}
	
	public void changeLbl(User user){
		myUser = user;
		lblId.setText(myUser.getId());
		lblNick.setText("(" + myUser.getNickname() + ") 님 위치 : ");
		repaint();
		revalidate();
	}

	private void addListener() {
		btnProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = client.getMyId();
				showMyProfile(id);
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nowState) {// true면 WaitingRoom
					logout();
					nowState = false;
				}else {// false면 ChatRoom
					showMassege("진정으로 나가십니까? 대기실로 갑네다");
					client.sendData(new SendData(EXIT_CHATROOM, cp.getRoomTitle(),client.getMyId()));
				}
			}
		});
		
		/*
		 * 190702 이재영 추가
		 * */
		btnNoteBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendData(new SendData(OPEN_NOTEBOX_PLEASE
							,myUser.getId()));
			}
		});
		btnUserSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String targetId = JOptionPane.showInputDialog(
						null,
						"검색할 유저의 아이디를 입력하세요",
						""
						
				);
				if(!targetId.equals("")){
					client.sendData(new SendData(SHOW_OTHER_PROFILE, targetId));
				}else{
					Utils.showMsg(ClientForm.this, "입력하세요");
				}
				
				
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (nowState) {// true면 WaitingRoom
					logout();
					nowState = false;
				} else {// false면 ChatRoom
					showMassege("진정으로 나가십니까? 대기실로 갑네다");
					client.sendData(new SendData(EXIT_CHATROOM, cp.getRoomTitle(),client.getMyId()));
				}
			}
		});

	}

	private void showframe() {
		setTitle("채팅방");
		pack();
		setLocation(100, 100);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
				
	}
	
	

	public boolean isNowState() {
		return nowState;
	}

	public void setNowState(boolean nowState) {
		this.nowState = nowState;
	}

	public ChatPanel getCp() {
		return cp;
	}

	public void setCp(ChatPanel cp) {
		this.cp = cp;
	}

	public FriendPanel getFp() {
		return fp;
	}

	public void setFp(FriendPanel fp) {
		this.fp = fp;
	}

	public MemberPanel getMp() {
		return mp;
	}

	public void setMp(MemberPanel mp) {
		this.mp = mp;
	}

	public WatingRoomPanel getWp() {
		return wp;
	}

	public void setWp(WatingRoomPanel wp) {
		this.wp = wp;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public JLabel getLblRoomInfo() {
		return lblRoomInfo;
	}

	public void setLblRoomInfo(JLabel lblRoomInfo) {
		this.lblRoomInfo = lblRoomInfo;
	}
	
	public void openNoteBox(Object noteBox){
		new NoteBoxFrame(client, noteBox);
	}

	
	
	/*
	 * 손사원 추가 190621
	 */
	public void logout() {
		showMassege("진정으로 나가십니까? 로그아웃합네다");
		client.logout();
	}

	public void showMyProfile(String id) {
		// 클라이언트의 id를 서버에 전달하여 유저정보를 받는다.
		client.sendData(new SendData(SHOW_MY_PROFILE, id));
	}
	
	public void showMassege(String str) {
		Utils.showMsg(this, str);
	}

}
