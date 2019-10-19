import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindIdForm extends JFrame implements Protocol {

	private JButton btnOk;
	private JButton btnCancel;
	private JTextField tfInput;
	private Client client;

	public FindIdForm(Client client) {
		this.client = client;

		add(new JLabel("E-MAIL을 입력하세요.", JLabel.CENTER), BorderLayout.NORTH);

		tfInput = new JTextField(15);

		btnOk = new JButton("OK");
		btnCancel = new JButton("CANCEL");

		JPanel pnlInput = new JPanel();
		pnlInput.add(tfInput);

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
				client.sendData(new SendData(LOOKUP_ID, tfInput.getText()));
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
