import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class NoteBoxFrame extends JFrame{
	
	public class NotePanel extends JPanel{
		private int index;
		private JTextField tfNum;
		private JTextField tfFrom;
		private JTextField tfTo;
		private JTextField tfDate;
		private JTextField tfMsg;
		private JPanel pnlMain;
		
		private Note note;
		
		public NotePanel(int index, Note note){
			this.index = index;
			this.note = note;
			init();
			setTextTextField();
		}
		
		private void init(){
			tfNum = new JTextField(5);
			tfNum.setEditable(false);
			tfFrom = new JTextField(10);
			tfFrom.setEditable(false);
			tfTo = new JTextField(10);
			tfTo.setEditable(false);
			tfDate = new JTextField(20);
			tfDate.setEditable(false);
			tfMsg = new JTextField(50);
			tfMsg.setEditable(false);
			pnlMain = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnlMain.add(tfFrom);
			pnlMain.add(tfTo);
			pnlMain.add(tfDate);
			pnlMain.add(tfMsg);
			add(pnlMain);
		}
		
		private void setTextTextField(){
			tfNum.setText(String.valueOf(index));
			tfFrom.setText(note.getSendId());
			tfTo.setText(note.getReceiveId());
			tfMsg.setText(note.getMsg());
			tfDate.setText(note.getDate());
		}
		
	}
	
	private JButton btnAllMsg;
	private JButton btnSendMsg;
	private JButton btnReceiveMsg;
	//노트하나당 노트패널 하나씩
	private Vector<Note> noteArr;
	private Vector<NotePanel> ntPnlVec; 
	
	
	private Client client;
	private JPanel pnlCenter;
	
	
	public NoteBoxFrame(Client client, Object  noteBox){
		this.noteArr = (Vector<Note>)noteBox;
		this.client = client;
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	
	private void init(){
		btnAllMsg = new JButton("전체 쪽지함");
		btnSendMsg = new JButton("보낸 쪽지함");
		btnReceiveMsg = new JButton("받은 쪽지함");
		ntPnlVec = new Vector<NotePanel>();
		
		for(int i = 0; i < noteArr.size();  i++) {
			ntPnlVec.add(new NotePanel(i,noteArr.get(i)));
		}
	}
	
	private void setDisplay(){
		JPanel pnlNorth = new JPanel(new GridLayout(1,0));
		pnlNorth.add(btnAllMsg);
		pnlNorth.add(btnReceiveMsg);
		pnlNorth.add(btnSendMsg);
		add(pnlNorth, BorderLayout.NORTH);
		//from to date msg
		pnlCenter = new JPanel(new GridLayout(0,1));
		
		JPanel pnlInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlInfo.add(new NotePanel(0,new Note("From","To","Message","Date")));
		pnlCenter.add(pnlInfo);
		for(int i=0; i<ntPnlVec.size(); i++){
			pnlCenter.add(ntPnlVec.get(i));
		}
		add(pnlCenter, BorderLayout.CENTER);
		
	}
	
	private void resetNoteBox(){
		pnlCenter.removeAll();
		JPanel pnlInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlInfo.add(new NotePanel(0,new Note("From","To","Message","Date")));
		pnlCenter.add(pnlInfo);
		for(int i=0; i<ntPnlVec.size(); i++){
			pnlCenter.add(ntPnlVec.get(i));
		}
		add(pnlCenter, BorderLayout.CENTER);
		repaint();
		revalidate();
		pack();
	}
	
	private void addListener(){
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if(src == btnReceiveMsg){
					System.out.println("머리가 빠지지 않는다");
					ntPnlVec.removeAllElements();
					for(int i = 0; i < noteArr.size();  i++) {
						if(noteArr.get(i).getReceiveId().equals(client.getMyId())){
							ntPnlVec.add(new NotePanel(i,noteArr.get(i)));
						}
					}
					resetNoteBox();
				}else if(src == btnSendMsg){
					System.out.println("머리가 빠진다");
					ntPnlVec.removeAllElements();
					for(int i = 0; i < noteArr.size();  i++) {
						if(noteArr.get(i).getSendId().equals(client.getMyId())){
							ntPnlVec.add(new NotePanel(i,noteArr.get(i)));
						}
					}
					resetNoteBox();
				}else{
					System.out.println("과연 다시 날까?");
					ntPnlVec.removeAllElements();
					for(int i = 0; i < noteArr.size();  i++) {
						ntPnlVec.add(new NotePanel(i,noteArr.get(i)));
					}
					resetNoteBox();
				}
			}
		};
		
		btnReceiveMsg.addActionListener(listener);
		btnSendMsg.addActionListener(listener);
		btnAllMsg.addActionListener(listener);
	}
	
	private void showFrame(){
		setTitle("쪽지함");
		setVisible(true);
		setLocation(100,100);
		pack();
	}
	

}






















