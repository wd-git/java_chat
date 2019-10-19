import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindPwForm extends JFrame implements Protocol {

	private JButton btnOk;
	private JButton btnCancel;
	private JTextField tfInputId;
	private JTextField tfInputEmail;
	private Client client;

	public FindPwForm(Client client) {
		this.client = client;

		add(new JLabel("ID/E-MAIL을 입력하세요.", JLabel.CENTER), BorderLayout.NORTH);

		tfInputId = new JTextField(15);
		tfInputEmail = new JTextField(15);

		btnOk = new JButton("OK");
		btnCancel = new JButton("CANCEL");

		JPanel pnlInputId = new JPanel();
		pnlInputId.add(tfInputId);
		JPanel pnlInputEmail = new JPanel();
		pnlInputEmail.add(tfInputEmail);

		JPanel pnlInput = new JPanel();
		pnlInput.add(pnlInputId);
		pnlInput.add(pnlInputEmail);
		add(pnlInput, BorderLayout.CENTER);

		JPanel pnlBtn = new JPanel();

		pnlBtn.add(btnOk);
		pnlBtn.add(btnCancel);

		add(pnlBtn, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

		addActionListener();
	}

	public void addActionListener() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendData(new SendData(LOOKUP_PW, tfInputId.getText(),
						tfInputEmail.getText()));
				dispose();
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
