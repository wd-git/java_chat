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
	 * 190624 �翵�߰� 6���� �̸� �������� ä�ù��� �߰��ɶ� ���ڷ� ǥ���Ѵ�
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
				 * ���翵 ���� 190623 �ᱹ �����ϰڴٴ� �Ҹ��� ������ ��û�ؼ� �ش� ChatRoom��ü�� �޾ƿ��� CF����
				 * �ش簴ü�� �̿��ؼ� CPǥ���Ѵ�. �׸��� �ٽ� ������ ��⸮��Ʈ ������Ʈ ��û
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
		TitledBorder tBorder = new TitledBorder("ä�ù� ���");
		tBorder.setTitleFont(new Font("���� ���", Font.BOLD, 15));
		tBorder.setTitleColor(Color.white);
		setBorder(tBorder);
		add(pnlRoom);
		setOpaque(false);
	}

	// 190701 �տ��� ����
	// ���� �ߺ����� �׸��� ���� ���⼭ ���ƹ�����.
	public void setLblRoom() {
		if (tempCrList != null) {
			int size = tempCrList.size();
			//��ĭ �׸��� ���� �Ϸ��� tempCrList�� lblRoomList�� �ε����� ���� ��߰ڴ�.
			//������ C��Ÿ�Ϸ� �����
			int i = 0;
			int j = 0;
			while (i != size) {
				JLabel tempLbl = lblRoomList.get(j);// ������� �����ͼ�
				
				int chatSize = tempCrList.get(i).getChatRoomUserList().size();
				
				if (tempLbl.getText().equals("") && chatSize != 0) {// ������ ���������
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
