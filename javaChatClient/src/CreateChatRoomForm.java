import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CreateChatRoomForm extends JFrame implements Protocol {
	private JLabel lblTitle;
	private JLabel lblPw;
	private JLabel lblJoinPeople;
	private JTextField tfTitle;
	private JSpinner spJoinNum;
	private JPasswordField pfPw;
	private JButton btnCreate;
	private JButton btnCancel;
	private JCheckBox cbIsPrivate;

	private Client client;

	public CreateChatRoomForm(Client client) {
		this.client = client;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		lblTitle = new JLabel("�� ����", JLabel.CENTER);
		lblPw = new JLabel("��й�");
		lblJoinPeople = new JLabel("�����ο�", JLabel.CENTER);
		tfTitle = new JTextField(10);
		spJoinNum = new JSpinner();
		pfPw = new JPasswordField(10);
		cbIsPrivate = new JCheckBox();
		btnCreate = new JButton("����");
		btnCancel = new JButton("���");

	}

	private void setDisplay() {
		JPanel pnllblTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblTitle.add(lblTitle);

		JPanel pnllblPw = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblPw.add(cbIsPrivate);
		pnllblPw.add(lblPw);

		JPanel pnllblJoinNum = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblJoinNum.add(lblJoinPeople);

		JPanel pnltfTitle = new JPanel();
		pnltfTitle.add(tfTitle);

		JPanel pnlpfPw = new JPanel();
		pnlpfPw.add(pfPw);

		JPanel pnltfJoinNum = new JPanel();
		lblJoinPeople.setPreferredSize(new Dimension(50, 30));
		spJoinNum.setPreferredSize(new Dimension(50, 30));
		SpinnerNumberModel numberModel = new SpinnerNumberModel(10, +0, +30, 1); // 0
																					// ~
																					// 30����.
																					// 1��
																					// ����,
																					// �ʱⰪ
																					// 10;
		spJoinNum.setModel(numberModel);
		pnltfJoinNum.add(spJoinNum);

		JPanel pnlbtnCreate = new JPanel();
		pnlbtnCreate.add(btnCreate);

		JPanel pnlbtnCancel = new JPanel();
		pnlbtnCancel.add(btnCancel);

		JPanel pnlWest = new JPanel(new GridLayout(0, 1));
		pnlWest.add(pnllblTitle);
		pnlWest.add(pnllblJoinNum);
		JPanel pnlPws = new JPanel();

		pnlPws.add(pnllblPw);
		pnlPws.add(cbIsPrivate);

		pnlWest.add(pnlPws);

		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		pnlCenter.add(pnltfTitle);
		pnlCenter.add(pnltfJoinNum);
		pnlCenter.add(pnlpfPw);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(pnlbtnCreate);
		pnlSouth.add(pnlbtnCancel);

		add(pnlWest, BorderLayout.WEST);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void addListeners() {
		cbIsPrivate.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JCheckBox temp = (JCheckBox) e.getSource();
				if (temp.isSelected()) {
					pfPw.setEditable(true);
				} else {
					pfPw.setEditable(false);
				}

			}
		});

		spJoinNum.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int value = new Integer(((Integer) spJoinNum.getValue())
						.intValue() - e.getWheelRotation());
				if (value >= 0 && value <= 30) {
					spJoinNum.setValue(value);
				}
			}
		});

		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ������ �뿡 ���ο� ê�� ����� �޼ҵ��߰�
				
				String id = client.getMyId();
				int personLimit = (int) spJoinNum.getValue();
				String title = tfTitle.getText();
				String pw = null;
				if (pfPw.getText() != null) {
					pw = pfPw.getText();
				}
				
				if(!title.equals("")){
					if(personLimit == 0){
						Utils.showMsg(client.getCf(), "�����ο��� 0���̱�����");
					}else{
						Object[] createChatRoomData = { id, personLimit, title, pw };
						client.sendData(new SendData(CREATE_CHATROOM,
								createChatRoomData));
						dispose();
					}
				}else{
					Utils.showMsg(client.getCf(), "���̸��� ��ĭ�̱�����");
				}

			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	private void showFrame() {
		setTitle("�� �����");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

}
