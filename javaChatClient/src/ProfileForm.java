import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ProfileForm extends JFrame implements Protocol {
	private JLabel lblPhoto;
	private JLabel lblId;
	private JLabel lblNick;
	private JLabel lblName;
	private JLabel lblMail;
	private JTextField tfId;
	private JTextField tfNick;
	private JTextField tfName;
	private JTextField tfMail;
	private User user;
	private JButton btnFriendIn;
	private JButton btnAddBlackList;
	private Client client;

	public ProfileForm(User user,Client client) {
		this.user = user;
		this.client = client;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		lblPhoto = new JLabel();
		lblId = new JLabel("ID");
		lblNick = new JLabel("NickName");
		lblName = new JLabel("Name");
		lblMail = new JLabel("E-Mail");
		tfId = new JTextField(10);
		tfId.setEditable(false);
		tfId.setText(user.getId());
		tfNick = new JTextField(10);
		tfNick.setEditable(false);
		tfNick.setText(user.getNickname());
		tfName = new JTextField(10);
		tfName.setEditable(false);
		tfName.setText(user.getName());
		tfMail = new JTextField(10);
		tfMail.setEditable(false);
		tfMail.setText(user.getEmail());
		btnFriendIn = new JButton("친구 추가");
		btnAddBlackList = new JButton("블랙리스트 추가");


		lblPhoto.setBorder(new LineBorder(Color.blue));
		lblPhoto.setPreferredSize(new Dimension(150, 100));
		lblPhoto.setIcon(user.getProfileIcon());
	}

	private void setDisplay() {
		JPanel pnlPhoto = new JPanel();
		pnlPhoto.add(lblPhoto);

		JPanel pnllblId = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblId.add(lblId);

		JPanel pnllblNick = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblNick.add(lblNick);

		JPanel pnllblName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblName.add(lblName);

		JPanel pnllblMail = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllblMail.add(lblMail);

		JPanel pnltfId = new JPanel();
		pnltfId.add(tfId);

		JPanel pnltfNick = new JPanel();
		pnltfNick.add(tfNick);

		JPanel pnltfName = new JPanel();
		pnltfName.add(tfName);

		JPanel pnltfMail = new JPanel();
		pnltfMail.add(tfMail);

		JPanel pnlNorth = new JPanel();
		pnlNorth.add(pnlPhoto);

		JPanel pnlWest = new JPanel(new GridLayout(0, 1));
		pnlWest.add(pnllblId);
		pnlWest.add(pnllblNick);
		pnlWest.add(pnllblName);
		pnlWest.add(pnllblMail);

		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		pnlCenter.add(pnltfId);
		pnlCenter.add(pnltfNick);
		pnlCenter.add(pnltfName);
		pnlCenter.add(pnltfMail);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnFriendIn);
		pnlSouth.add(btnAddBlackList);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlWest, BorderLayout.WEST);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}
	private void addListeners(){
		  btnFriendIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					new AddFriendM(client, tfId.getText());
				
			}
		});
		  btnAddBlackList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!client.getMyId().equals(tfId.getText())){
				client.sendData(new SendData(ADD_BLACKLIST, client.getMyId(),
						tfId.getText()));
			} else {
				JOptionPane.showMessageDialog(
						null,
						"자기자신은 블랙리스트에 추가할수없습니다."
				);
			}
			}
		});
		}

	private void showFrame() {
		setTitle("프로필 보기");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
