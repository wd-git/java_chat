import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ChatRoomListPanel extends JPanel implements Protocol {
	/*
	 * 190624 재영추가 6개씩 미리 만들어놓고 채팅방이 추가될때 글자로 표시한다
	 */

	private Vector<JLabel> lblRoomList;
	private Client client;
	private Vector<ChatRoom> tempCrList;

	public ChatRoomListPanel(Client client, Vector<ChatRoom> tempCrList) {
		this.client = client;
		this.tempCrList = tempCrList;
		setOpaque(false);
		init();
		setDisplay();
		setLblRoom();
	}

	private void init() {
		lblRoomList = new Vector<JLabel>();

	}

	private void setDisplay() {
		MouseListener mListener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				/*
				 * 이재영 수정 190623 결국 입장하겠다는 소리임 서버에 요청해서 해당 ChatRoom객체를 받아오고 CF에서
				 * 해당객체를 이용해서 CP표시한다. 그리고 다시 서버에 대기리스트 업데이트 요청
				 */
				Object src = me.getSource();
				JLabel tempLbl = (JLabel)src;
				
				if(!tempLbl.getText().equals("")){
					String tempTitle = tempLbl.getText();
					int LIndex = tempTitle.lastIndexOf("(");
					String title = tempTitle.substring(8, LIndex);
					client.sendData(new SendData(ENTER_CHATROOM, client.getMyId(),
							title));
				}

			}
		};

		JPanel pnlRoom = new JPanel(new GridLayout(3, 2, 20, 20));
		pnlRoom.setOpaque(false);
		pnlRoom.setBorder(new EmptyBorder(10, 10, 10, 10));
		for (int i = 0; i < 6; i++) {
			JLabel tempLbl = new JLabel("", JLabel.CENTER);
			Utils.setLabelToDefaultFont(tempLbl);
			tempLbl.setForeground(Color.WHITE);
			tempLbl.setBackground(new Color(58,119,176));
			tempLbl.setBorder(new LineBorder(Color.GRAY));
			tempLbl.setPreferredSize(new Dimension(250, 60));
			tempLbl.addMouseListener(mListener);
			tempLbl.setOpaque(true);
			lblRoomList.add(tempLbl);
			pnlRoom.add(lblRoomList.get(i));

		}
		TitledBorder tBorder = new TitledBorder("채팅방 목록");
		tBorder.setTitleFont(new Font("맑은 고딕", Font.BOLD, 15));
		tBorder.setTitleColor(Color.white);
		setBorder(tBorder);
		add(pnlRoom);
		setOpaque(false);
	}

	// 190701 손원도 수정
	// 방제 중복으로 그리는 현상 여기서 막아버리자.
	public void setLblRoom() {
		if (tempCrList != null) {
			int size = tempCrList.size();
			//빈칸 그리기 방지 하려면 tempCrList랑 lblRoomList의 인덱스를 따로 써야겠다.
			//완전히 C스타일로 간드아
			int i = 0;
			int j = 0;
			while (i != size) {
				JLabel tempLbl = lblRoomList.get(j);// 순서대로 가져와서
				
				int chatSize = tempCrList.get(i).getChatRoomUserList().size();
				
				if (tempLbl.getText().equals("") && chatSize != 0) {// 내용이 비어있으면
					tempLbl.setText("Title : " + tempCrList.get(i).getTitle()
							+ "("
							+ chatSize
							+ "/" + tempCrList.get(i).getPersonLimit() + ")"
							);
					j++;
				}
				i++;
			}
		}
	}

	public Vector<JLabel> getLblRoomList() {
		return lblRoomList;
	}

	public void setLblRoomList(Vector<JLabel> lblRoomList) {
		this.lblRoomList = lblRoomList;
	}
}
