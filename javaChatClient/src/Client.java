import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Client extends Thread implements Protocol {
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	private Socket s;
	private SendData data;
	private ClientForm cf;
	private String myId;
	private Login login;
	private User user;
	private int count = 0;
	private BlackListForm blackListForm;
	private OneOnOneChatPanel oneChat;

	public Client(String s) {
		login = new Login(this);
		makeSocket(s);

		new Thread() {
			// 메세지를 기다리는 용도로 만들었음
			@Override
			public void run() {
				System.out.println("메세지 기다리는중 ...");
				try {
					synchronized (this) {
						while ((data = (SendData) ois.readObject()) != null) {
							System.out.println("데이터 받아라");
							System.out.println("OPCODE : " + data.getOpcode());
							// /받은 샌드데이터 다 뜯어서 보여줌
							if (data.getData() != null) {
								for (int i = 0; i < data.getData().length; i++) {
									System.out.println("Data[" + i + "] : "
											+ data.getData()[i]);
								}
							}
							switch (data.getOpcode()) {
							case LOGIN_SUCCESS_PRIVATE:
								sucessLogin(data);
								break;
							/*
							 * 190703 이재영 추가
							 */
							case LOGIN_NOT_FOUND_ID:
								notFoundId();
								break;

							case LOGIN_DUPLICATION:
								duplicateId();
								break;

							case LOGIN_WRONG_PASSWORD:
								loginWrongNumber();
								break;

							case LOGIN_GET_SCATTTER_ORDER:
								loginGetScatterOrder(data);
								break;

							case ANNOUNCE_LOGOUT_INFO:
								announceLogoutInfo(data);
								break;
							case SUCCESS_OVERLAP_CHECK:
								successOverlapCheck();
								break;

							case JOIN_ALREADY_EXIST_ID:
								alreadyExistUser();
								break;

							case JOIN_SUCCESS:
								suceesJoin();
								break;

							case FOUND_ID:
								foundId();
								break;

							case NOT_FOUND_ID:
								notFoundIdInFindId();
								break;

							case FOUND_PW:
								foundPw(data);
								break;

							case NOT_FOUND_INFO_PW:
								notFoundPwInFindPw(data);
								break;

							case SUCCESS_SEND_WATINGROOM_NORMAL_MSG:
								successSendWaitingRoomNormalMsg(data);
								break;

							case SEND_WAITINGROOM_WHISPER_MSG:
								sendWaitingRoomWhisperMsg(data);
								break;

							case NO_JUPSOK_SO_CANT_WHISPER:
								cantWhipsper();
								break;

							case SUCCESS_SHOW_MY_PROFILE:
								successShowMyProfile(data);
								break;

							case SUCCESS_CREATE_CHATROOM:
								sucessCreateChatRoom(data);
								break;

							case CREATE_GET_SCATTER_ORDER:
								createGetScatterOrder(data);
								break;

							case WITHDRAW_OK:
								withdraw();
								break;

							case SUCCESS_SELF_MODIFY_PROFILE:
								successSelfModifyProfile(data);
								break;

							case FAIL_SELF_MODIFY_PROFILE:
								failSelfModiftProfile(data);
								break;

							case SUCCESS_SHOW_OTHER_PROFILE:
								successShowOtherProfile(data);
								break;
							case NO_EXIST_ID_SEARCH_ID :
								noExistId();
								break;
							case SUCCESS_ENTER_CHATROOM:
								// data : ChatRoom
								sucessEnterChatRoom(data);
								break;

							case FAIL_ENTER_CHATROOM_WRONG_PASSWORD:
								failEnterChatRoomWroingPassword();
								break;
							case OVERFLOW_PERSON_LIMIT_IN_CHATROOM :
								overFlowPersonLimitInChatroom();
								break;

							case PASSWORD_INPUT_PLEASE:
								passwordInputPlease(data);
								break;

							case ENTER_GET_SCATTER_ORDER_IN_CHATROOM:
								// data : id
								// 손원도
								// 대기방을 갱신해야지. 음음!!!
								enterGetScatterorderInChatRoom(data);
								break;

							case ENTER_GET_SCATTER_ORDER_IN_WAITINGROOM:
								enterGetScatterOrderInWaitingRoom(data);
								break;

							case OK_SUCESS_CHANGED_HEAD:

								// data[0] = title
								// data[1] = selectedId
								// 채팅방내의 유저들이 받는 정보
								successChangeHead(data);
								break;

							case SUCCESS_EXIT_CHATROOM:
								// 0627 손원도
								// data[0] = WaitingRoom
								// data[1] = title
								successExitChatRoom(data);
								break;

							case CHANGE_CHATROOM_HEAD_BEFORE_EXIT:
								changeChatRoomHeadBeforeExit(data);
								break;

							case SUCCESS_EXIT_CHATROOM_SCATTER_TO_WAITINGROOM:
								// 0627 손원도
								successExitChatRoomScatterToWairingRoom(data);
								break;

							case SUCCESS_EXIT_CHATROOM_SCATTER_TO_CHATROOM:
								successExitChatRoomScatterToChatRoom(data);
								break;

							// 손원도 추가
							case SUCCESS_KICKOUT:
								kickOut();
								break;

							case YOU_ARE_KICKED:
								kickedOut(data);
								break;

							case NOT_EXIST_ID_FAIL_ADD_FRIEND:
								AlreadyExistUser();
								break;

							case OVERLAP_FRIEND:
								overlapFriend();
								break;

							case ADD_FRIEND:
								addFriend(data); // 정상욱 추가 06-24 20:00
								break;

							case RESPOND_NO_REQUSET_ADD_FRIEND:
								respondNoRequserAddFriend();
								break;

							case RESPOND_YES_REQUSET_ADD_FRIEND:
								respondYesRequestAddFriend(data);
								// 정상욱 추가 06-24 20:00
								break;

							case DELETE_FRIEND:
								deleteFriend(data); // 정상욱 추가 06-24 24:00
								break;

							case ADD_BLACKLIST:
								addBlacklist(data);

								break;
							case OVERLAP_BLACKLIST:
								// JOptionPane.showMessageDialog(null,
								// "이미 블랙리스트에 추가되어있습니다.");

								cf.showMassege("이미 블랙리스트에 추가되어있습니다.");
								break;

							case FAIL_ADD_BLACKLIST:
								// JOptionPane.showMessageDialog(null,
								// "이미 탈퇴한 회원입니다.");

								cf.showMassege("이미 탈퇴한 회원입니다.");
								break;

							case SUCCESS_SHOW_BLACKLIST:
								successShowBlacklist();
								// Vector<User> myBlackList = (Vector<User>)
								// data.getData()[0];

								break;

							case DELETE_BLACKLIST:
								deleteBlackList(data);
								break;

							case REQUEST_ONEONONE_MSG: // 정상욱 추가 06-25 20:41
								requestOneononeMsg(data);
								break;

							case FAIL_SEND_ONEONE_MSG:
								failSendOneononeMsg(data);

								break;

							case SUCCESS_SEND_ONEONONE_MSG:
								successSendOneOneMsg(data);

								break;

							case SEND_ONEONONE_MSG:
								sendOneononeMsg(data);

								break;

							case ONEONONECHAT_OUT:
								oneononeChatOut(data);

								break;

							case MYPROFILE_OTHER_REFRESH:
								// System.out.println("정보수정이 상대한테 전달되나");
								myProfileOtherRefresh(data);

								break;

							case MYPROFILE_WAITINGUSER_REFRESH:
								myProfileWatingUserRefresh(data);

								break;
							case MYPROFILE_CHATROOMUSER_REFRESH :
								myProfileChatRoomUserRefresh(data);
								break;

							case TRY_SEND_NOTE: // 쪽지보내기 (임준석 6,24일)
								trySendNote(data);

								break;

							case SUCCESSE_NOTE_SEND:
								sucesseNoteSend(data);

								break;


							case SUCESS_MODIFY_ROOM_TITLE:
								sucessModifyRoomTitle(data);
								break;

							case GET_SCATTER_MODIFY_ROOM_TITLE:
								System.out.println("왔니>>???");
								modifyChatRoomTitle(data);
								break;

							case REQUEST_SEND_CHATROOM_MSG:
								// System.out.println("메세지 요청을 받으십니까?");
								// 해당채팅방의 ta에 메세지 표시
								requestSendChatRoomMsg(data);

								break;

							case SUCESSE_OPEN_NOTEBOX:
								// System.out.println("실례지만 거거거 어디 최씹니꺼?  : " +
								// ((Vector<Note>)
								// data.getData()[0]).toString());
								sucesseopennotebox(data);

								break;
							case OK_SHAKE_IT_BABY_ONE_ON_ONE:
								okShakeItBabyOneOnOne(data);

								break;

							case ANNOUNCE_NOTICE_MSG:
								announceNoticeMsg(data);

								break;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

	}
	
	private synchronized void myProfileChatRoomUserRefresh(SendData data){
		//[0] 이전 user
		//[1] 바뀐 유저
		getCf().getCp().getCr().getChatRoomUserList().
			remove((User)data.getData()[0]);
		getCf().getCp().getCr().getChatRoomUserList().
			add((User)data.getData()[1]);
		getCf().getMpInChat().removeFromList((User)data.getData()[0]);
		getCf().getMpInChat().updateList((User)data.getData()[1]);
				
	}
	
	private synchronized void noExistId(){
		Utils.showMsg(cf, "존재안한다");
	}
	
	private synchronized void overFlowPersonLimitInChatroom(){
		Utils.showMsg(cf, "제한인원을 초과했습니다");
	}

	private synchronized void addBlacklist(SendData data) {
		User tempTargetUser = (User) data.getData()[0];
		user.addBlackList(tempTargetUser);
		cf.showMassege("블랙리스트에 추가 되었습니다.");
	}

	private synchronized void respondNoRequserAddFriend() {
		cf.showMassege("상대방이 친구추가를 거절하였습니다.");
	}

	private synchronized void overlapFriend() {
		cf.showMassege("이미 친구추가 되어있습니다.");
	}

	private synchronized void AlreadyExistUser() {
		cf.showMassege("이미 탈퇴한 회원입니다.");
	}

	private synchronized void kickedOut(SendData data) {
		WaitingRoom tempWr = (WaitingRoom) data.getData()[0];
		cf.showMassege("당신은 우리와 함께 갈 수 없습니다.");
		cf.returnToWaitingRoom(tempWr);
	}

	private synchronized void kickOut() {
		cf.showMassege("병진이 형은 나가있어. 뒤지기 싫으면");
	}

	private synchronized void successExitChatRoomScatterToChatRoom(SendData data) {
		ChatRoom cr2 = (ChatRoom) data.getData()[1];

		cf.getWp().removeCrList((ChatRoom) (data.getData()[1]),
				(User) (data.getData()[0]));
		cf.getMpInChat().removeFromList((User) (data.getData()[0]));

		cf.getCp().changeChatRoom(cr2.getChatRoomUserList().size());
		cf.getWp().setChatRoomPanel();
		cf.getWp().refresh();
	}

	private synchronized void successExitChatRoomScatterToWairingRoom(SendData data) {
		cf.getWp().removeCrList((ChatRoom) (data.getData()[1]),
				(User) (data.getData()[0]));
		cf.getMp().updateList((User) data.getData()[0]);
		cf.getWp().setChatRoomPanel();
		cf.getWp().refresh();
	}

	private synchronized void changeChatRoomHeadBeforeExit(SendData data) {
		ChatRoom exitCr = (ChatRoom) data.getData()[0];
		String exitId = (String) data.getData()[1];

		Utils.showMsg(cf, "나가기전에 방장을 선택하고 가시어요");
		new ChangeHeadFrame(Client.this, exitCr, true);

		// sendData(new SendData(EXIT_CHATROOM, exitCr.getTitle(), exitId));
	}

	private synchronized void successExitChatRoom(SendData data) {
		WaitingRoom tempWr = (WaitingRoom) data.getData()[0];
		String title = (String) data.getData()[1];

		cf.returnToWaitingRoom(tempWr);
		cf.setNowState(true);

		sendData(new SendData(SUCCESS_EXIT_CHATROOM_PLEASE_SCATTER, getMyId(),
				title));
	}

	private synchronized void successChangeHead(SendData data) {
		String selectedId = (String) data.getData()[1];
		cf.getCp().changeRoomHead(selectedId);
		cf.getCp().resetButton();
	}

	private synchronized void enterGetScatterOrderInWaitingRoom(SendData data) {
		cf.getMp().removeFromList((User) data.getData()[0]);
		cf.getWp().updateCrList((ChatRoom) (data.getData()[1]),
				(User) (data.getData()[0]));

		cf.getWp().setChatRoomPanel();
		cf.getWp().refresh();
	}

	private synchronized void enterGetScatterorderInChatRoom(SendData data) {
		ChatRoom cr = (ChatRoom) data.getData()[1];
		cf.getWp().updateCrList((ChatRoom) (data.getData()[1]),
				(User) (data.getData()[0]));
		cf.getMpInChat().updateList((User) data.getData()[0]);

		System.out.println("after -> update Cr List : "
				+ cf.getWp().getCrList().toString());
		cf.getCp().changeChatRoom(cr.getChatRoomUserList().size());
		cf.getWp().setChatRoomPanel();
		cf.getWp().refresh();
	}

	private synchronized void passwordInputPlease(SendData data) {
		String pw = JOptionPane.showInputDialog("비밀번호 입력ㄱ");
		// data[0] : title
		// data[1] : id
		sendData(new SendData(PASSWORD_INPUT_DONE, pw, data.getData()[0],
				data.getData()[1]));
	}

	private synchronized void failEnterChatRoomWroingPassword() {
		Utils.showMsg(cf, "비밀번호 일치X 고로 입장X");
	}

	private synchronized void failSelfModiftProfile(SendData data) {

		cf.showMassege("비밀번호가 일치하지 않습니다.");
	}

	private synchronized void successSelfModifyProfile(SendData data) {
		//data[0] = 이전
		//data[1] = 바꾼거
		User tempUser = (User)data.getData()[0];
		user = (User)data.getData()[1];
		if(cf.isNowState()){
			System.out.println("여기는 이프");
			cf.getWp().getWr().getWaitingRoomUserList().remove(tempUser);
			cf.getWp().getWr().getWaitingRoomUserList().add(user);
			cf.getMp().removeFromList(tempUser);
			cf.getMp().updateList(user);
			cf.changeLbl(user);
		}else{//챗룸에서
			System.out.println("여기는 엘스");
			cf.getCp().getCr().getChatRoomUserList().remove(tempUser);
			cf.getCp().getCr().getChatRoomUserList().add(user);
			cf.getMpInChat().removeFromList(tempUser);
			cf.getMpInChat().updateList(user);
			cf.changeLbl(user);
		}
		
		cf.showMassege("회원 정보가 수정 되었습니다.");
	}

	private synchronized void withdraw() {
		cf.showMassege("회원 탈퇴 성공!");
		System.exit(-11);
	}

	private synchronized void createGetScatterOrder(SendData data) {
		cf.getWp().addChatRoomList((ChatRoom) data.getData()[0]);
		cf.getMp().removeFromList((String) data.getData()[1]);
		cf.getWp().setChatRoomPanel();
	}

	private synchronized void cantWhipsper() {
		Utils.showMsg(cf, "대기실에 없는 사람입니다");
	}

	private synchronized void sendWaitingRoomWhisperMsg(SendData data) {
		String myId = (String) data.getData()[0];
		String targetId = (String) data.getData()[1];
		String whisperMsg = (String) data.getData()[2];
		cf.getWp().showWhisperMsgInTa(myId, targetId, whisperMsg);
	}

	private synchronized void successSendWaitingRoomNormalMsg(SendData data) {
		System.out.println(data.getData()[0].toString());
		String id = (String) data.getData()[0];
		String msg = (String) data.getData()[1];
		System.out.println(id + ":" + msg);
		cf.getWp().showMsgInTa(id, msg);
	}

	private synchronized void notFoundPwInFindPw(SendData data) {
		cf.showMassege("비밀번호 : " + (String) data.getData()[0]);
	}

	private synchronized void foundPw(SendData data) {
		cf.showMassege("비밀번호 : " + (String) data.getData()[0]);
	}

	private synchronized void notFoundIdInFindId() {
		cf.showMassege("일치하는 정보가 없습니다.");
	}

	private synchronized void foundId() {
		Utils.showMsg(login.getJoin(), "아이디 : " + (String) data.getData()[0]);
	}

	private synchronized void suceesJoin() {
		Utils.showMsg(login.getJoin(), "회원가입 성공");
	}

	private synchronized void alreadyExistUser() {
		Utils.showMsg(login.getJoin(), "이미 존재하는 회원입니다.");
	}

	private synchronized void successOverlapCheck() {
		Utils.showMsg(login.getJoin(), "중복되지 않았습니다. 가입하이소");
		login.getJoin().changeState();
	}

	private synchronized void announceLogoutInfo(SendData data) {
		if (data.getData()[0].equals(myId)) {
			System.exit(0);
		} else {
			cf.getMp().removeFromList((String) data.getData()[0]);
		}
	}

	private synchronized void loginGetScatterOrder(SendData data) {
		cf.getMp().updateList((User) data.getData()[0]);
		cf.getWp().updateWaitingRoom();

	}

	private synchronized void loginWrongNumber() {
		Utils.showMsg(login, "비밀번호가 틀렸슈");
	}

	private synchronized void notFoundId() {
		Utils.showMsg(login, "아이디를 찾을 수 없습니다");
	}

	private synchronized void duplicateId() {
		Utils.showMsg(login, "이미 로그인 중인 아이디 입니다.");
	}

	private synchronized void successSendOneOneMsg(SendData data) {
		User user = (User) data.getData()[0];
		User targetUser2 = (User) data.getData()[1];

		oneChat = new OneOnOneChatPanel(Client.this, user, targetUser2);

	}

	private synchronized void successShowBlacklist() {
		blackListForm = new BlackListForm(Client.this, user.getBlackList()); // /
																				// 2019-06-26
																				// 와서
																				// 수정할부분
	}

	private synchronized void requestSendChatRoomMsg(SendData data) {
		cf.getCp().showMessage(data);
	}

	private synchronized void sucesseopennotebox(SendData data) {
		cf.openNoteBox(data.getData()[0]);
	}

	private synchronized void okShakeItBabyOneOnOne(SendData data) {
		String shakeId = (String) data.getData()[0];
		oneChat.shakeItDogBaby(shakeId);
	}

	private synchronized void announceNoticeMsg(SendData data) {
		String msg1 = (String) data.getData()[0];
		JOptionPane.showMessageDialog(null, "운영자로부터 알림" + msg1, "알림",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private synchronized void sucesseNoteSend(SendData data) {
		String msg = (String) data.getData()[0];
		String myId = (String) data.getData()[1];
		String targetId = (String) data.getData()[2];

		// 받을때 받는 나자신의 유저에 메세지 저장
		getUser().addNote(
				new Note(myId, targetId, msg, Utils.currentTimeToString()));
		new Messenger_Receive(this, msg, myId, targetId);
	}

	private synchronized void modifyChatRoomTitle(SendData data) {
		String oriTitle = (String) data.getData()[0];
		String changedTitle = (String) data.getData()[1];
		cf.getWp().editChatRoomList(oriTitle, changedTitle);
	}

	private synchronized void sucessModifyRoomTitle(SendData data) {
		System.out.println("이까지 오는교?");
		String oriTitle = (String) data.getData()[0];
		String changedTitle = (String) data.getData()[1];
		cf.showMassege("방 타이틀이 " + changedTitle + "변경되었습니다");
		sendData(new SendData(SCATTER_MODIFY_ROOM_TITLE_PLEASE, oriTitle,
				changedTitle));
	}

	/*
	 * 190624 이재영이 추가함 ChatRoom객체의 정보로 cf에 cp표시
	 */
	// 190628 손원도가 챗룸 넘어오는값 수정함.
	private synchronized void sucessEnterChatRoom(SendData data) {
		Object[] dataArr = data.getData();
		ChatRoom tempCr = (ChatRoom) dataArr[0];
		System.out.println("이방에 누가누가 숨어있나???? " + tempCr.getChatRoomUserList());
		User user = (User) dataArr[1];

		tempCr.addChatRoomUser(user);
		cf.createChatRoom(tempCr);
		sendData(new SendData(ENTER_CHATROOM_SUCCESS_SCATTER_PLEASE, getMyId(),
				tempCr));
	}

	private synchronized void oneononeChatOut(SendData data) {
		User user = (User) data.getData()[0];
		User targetUser = (User) data.getData()[1];

		oneChat.getChatArea().append(user + "님이 퇴장하였습니다.");
		oneChat.getChatArea().setEditable(false);
		oneChat.getChatField().setEditable(false);
	}

	private synchronized void myProfileWatingUserRefresh(SendData data) {
		User user = (User) data.getData()[0];
		User reFreshUser = (User) data.getData()[1];
		
		cf.getWp().getWr().getWaitingRoomUserList().remove(user);
		cf.getWp().getWr().getWaitingRoomUserList().add(reFreshUser);
		cf.getMp().removeFromList(user);
		cf.getMp().updateList(reFreshUser);
	}

//	private synchronized void successSelfModifyProfile(SendData data) {
//		User User = (User) data.getData()[0];
//		User refreshUser = (User) data.getData()[1];
//		if(WaitingRoom)
//		cf.getMp().removeFromList(user);
//		cf.getMp().updateList(refreshUser);
//		JOptionPane.showMessageDialog(null, "회원정보가 수정되었습니다.");
//
//	}

	private synchronized void myProfileOtherRefresh(SendData data) {
		User user1 = (User) data.getData()[0]; // 기존 유저정보
		User user2 = (User) data.getData()[1];
		
		cf.getFp().removeUI(user1);
		cf.getFp().updateUI(user2);
		
		//for()
	}

	private synchronized void sendOneononeMsg(SendData data) {
		System.out.println("채팅 상대방한태 잘던져지나");
		User user = (User) data.getData()[0];
		User targetUser = (User) data.getData()[1];
		String msg = (String) data.getData()[2];

		oneChat.getChatArea().append(user + " : " + msg + "\n");
	}

	private synchronized void failSendOneononeMsg(SendData data) {
		User targetUser = (User) data.getData()[1];
		JOptionPane.showMessageDialog(null, targetUser + " 님이 1:1대화를 거절하였습니다");
	}

	// 정상욱 추가 06-25 20:41

	private synchronized void requestOneononeMsg(SendData data) {
		User user = (User) data.getData()[0];
		User targetUser = (User) data.getData()[1];
		int result = JOptionPane.showConfirmDialog(null, user
				+ "님께서 1:1대화를 요청합니다 수락하시겠습니까?", "1:1 대화요청",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_NO_OPTION) {
			sendData(new SendData(SUCCESS_SEND_ONEONONE_MSG, user, targetUser));
		} else {
			sendData(new SendData(FAIL_SEND_ONEONE_MSG, user, targetUser));
		}
	}

	// 정상욱 추가 06-24 20:00
	private synchronized void respondYesRequestAddFriend(SendData data) {
		User user = (User) data.getData()[0];

		cf.getFp().updateUI(user);
	}

	private synchronized void deleteBlackList(SendData data) {
		User targetUser = (User) data.getData()[0];

		user.getBlackList().remove(targetUser);
		blackListForm.updateUi(targetUser);
	}

	private synchronized void deleteFriend(SendData data) {
		User targetUser = (User) data.getData()[0];
		cf.getFp().removeUI(targetUser);

	}

	// 정상욱 추가 06-24 20:00
	private synchronized void addFriend(SendData data) {
		String targetId = (String) data.getData()[0];
		String myId = (String) data.getData()[1];
		String msg = (String) data.getData()[2];
		new AddFriendY(Client.this, targetId, myId, msg);
	}

	private synchronized void sucessCreateChatRoom(SendData data) {
		ChatRoom cr = (ChatRoom) data.getData()[0];
		cf.createChatRoom(cr);
		sendData(new SendData(CREATE_CAHTROOM_SUCESS_SCATTER_PLEASE, cr,
				getMyId()));
		cf.getLblRoomInfo().setText(cr.getTitle());
	}

	private synchronized void sucessLogin(SendData data) {
		/*
		 * 190623 이재영이 수정함
		 */
		Object[] dataArr = data.getData();

		myId = (String) dataArr[0];
		WaitingRoom wr = (WaitingRoom) dataArr[1];
		user = (User) dataArr[2];

		cf = new ClientForm(Client.this, wr);

		Vector<User> friendList = (Vector<User>) data.getData()[3]; // 2019-06-25
																	// 정상욱 수정
		// Vector<User> connetedUserList = (Vector<User>)data.getData()[3];
		System.out.println("친구사이즈" + friendList.size());
		int i = 0;
		for (User user : friendList) {
			System.out.println("카운트" + i);
			cf.getFp().updateUI(user);
			i++;
		}

		System.out.println("대기실 정보 내놔라 이녀석아!");
		sendData(new SendData(LOGIN_SUCCESS_SCATTER_PLEASE, myId));

		login.dispose();
	}

	// 쪽지보내기 임준석 6.24일 추가
	private synchronized void trySendNote(SendData data) {
		String msg = (String) data.getData()[0];
		User user = (User) data.getData()[1];
		User targetUser = (User) data.getData()[2];

		new Messenger1(msg, this, user, targetUser);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	public ClientForm getCf() {
		return cf;
	}

	public void setCf(ClientForm cf) {
		this.cf = cf;
	}

	private synchronized void successShowOtherProfile(SendData data) {
		Object[] dataArr = data.getData();
		user = (User) dataArr[0];
		new ProfileForm(user, Client.this);

	}

	// 지워버렷 0624 손원도
	// private void idNick(SendData data){
	// Object[] dataArr = data.getData();
	// Vector<String> idNick = (Vector<String>)dataArr[0];
	// cf.setIdNick(idNick);
	// }

	private synchronized void successShowMyProfile(SendData data) {
		Object[] dataArr = data.getData();
		;
		user = (User) dataArr[0];
		new MyProfileForm(user, Client.this, cf.isNowState());
		System.out.println(user.getId());
		System.out.println(user.getNickname());
		System.out.println(user.getName());
		System.out.println(user.getEmail());
	}

	public synchronized void makeSocket(String ip) {
		try {
			s = new Socket(ip, 8080);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			System.out.println(s.getInetAddress() + ": 연결됨");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 클라이언트가 서버에 쓴다 -> 콘솔에 메세지 출력됨
	public synchronized void sendData(SendData data) {
		try {
			oos.writeObject(data);
			oos.flush();
			// oos.reset();
			// oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 클라이언트가 서버에 정보를 보냄
	public synchronized void sendLoginInfo(SendData data) {
		try {
			oos.writeObject(data);
			oos.flush();
			// oos.reset();
			// oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void logout() {
		sendData(new SendData(LOGOUT, myId));
		new Thread(){
			public void run(){
				try{
					Utils.showMsg(cf, "2초후 종료됩니다 확인을 누르고 나서 제발 좀 기다려주세요");
					Thread.sleep(2000);
					System.exit(-12);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}.start();
	}

	public synchronized static void main(String[] args) {
		String ip = JOptionPane.showInputDialog("아이피 입력하소");
		// String ip = "192.168.200.57";

		new Client(ip);
	}

}
