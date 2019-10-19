interface Protocol { // ������ݵ� ����

	public final static int LOGIN_PLEASE = 100; // �α��� ��û -> String Id, Strign Pw;
	public final static int LOGIN_SUCCESS_PRIVATE = 1101; // �α��� ���� ��ġ ->
															// Vector<ChatRoom>
															// ChatRoomList,
															// Vector<String>
	// waitingRoomUserList, User user / this.Client
	/*
	 * 190623 ���翵 �������� �߰�
	 */
	public final static int LOGIN_SUCCESS_SCATTER_PLEASE = 77;
	public final static int LOGIN_GET_SCATTTER_ORDER = 78;
	public final static int ANNOUNCE_ENTER_WAITINGROOM = 1102; // ���ǿ� �ִ� �����鿡��
																// �˸� / String
																// enteredId /
																// other all
																// Client
																// in
																// waitingroom
	public final static int LOGIN_NOT_FOUND_ID = 1103; // ���̵� ã�� �� ���� / FALSE /
														// this.Client
	public final static int LOGIN_WRONG_PASSWORD = 1004; // ��й�ȣ Ʋ�� / FALSE /
															// this.Client
	public final static int LOGIN_DUPLICATION = 1005; // �̹� �ߺ��� ���̵� �α����� �����ϴ�.
	public final static int UPDATE_MEMBER_LIST = 1006;

	public final static int LOGIN_CONFIRM = 110; // �α��� ����, �̵� ��û

	public final static int LOOKUP_ID = 120; // ���̵� ã�� ��û
	public final static int FOUND_ID = 1121; // ���̵� ã�� / String id / this.Client
	public final static int NOT_FOUND_ID = 1122; // ���̵� ���� / FALSE / this.Client
	
	public final static int LOOKUP_PW = 130; // ��й�ȣ ã�� ��û / String Id, String mailAdress / Server
	public final static int FOUND_PW = 1131; // ��й�ȣ ã�� String password this.Client
	public final static int NOT_FOUND_INFO_PW = 1132; // ��ġ�ϴ� ������ ���� FALSE this.Client

	public final static int WITHDRAW = 140; // Ż�� ��û String Id, String Pw Server
	public final static int WITHDRAW_OK = 1401; // Ż����� TRUE this.Client
	public final static int NOTICE_WITHDRAW = 1402; // ȸ���� Ż������ ȸ���鿡�� �ݿ� Vector<String> connetedUsersList
													// connected.Client
	public final static int NOT_FOUND_INFO_WITHDARW = 1403; // ��ġ�ϴ� ������ ���� FALSE this.Client
	
	public final static int LOGOUT = 150; // �� ������. ���̷� // String id;
	public final static int ANNOUNCE_LOGOUT_INFO = 1150; // ������ ���� �޸���� �Ƹ��ٿ��� �Ѵٴ���.. // String id
	
	///  �̺κ� ���� �ʿ� 
	
	public final static int ID_NICK = 160;
	

	public final static int JOIN = 200; // ȸ������ ��û User user Server
	public final static int JOIN_SUCCESS = 1201; // ȸ������ ���� TRUE this.Client
	public final static int JOIN_NON_SAME_PASSWORD = 1202; // ��й�ȣ ��ġ ���� ����. FALSE this.Client

	public final static int VERIFY_ID = 210; // �ߺ�Ȯ�� ��û String id Server
	public final static int JOIN_ALREADY_EXIST_ID = 1211; // �̹� �����ϴ� ���̵� �Դϴ�. FALSE this.Client
	public final static int SUCCESS_OVERLAP_CHECK = 1212; // �ߺ��� FALSE this.Client

	public final static int SEND_WAITINGROOM_NORMAL_MSG = 300; // ���� �Ϲ� �޽��� ���� ��û String myId, String msg Server
	public final static int SUCCESS_SEND_WATINGROOM_NORMAL_MSG = 1302; // ���ǿ��� �ִ� �����鿡�� �޽��� ���� String id other all Client in
																// waitingroom
	public final static int FAILED_SEND_NORMAL_MSG = 1303; // �޽��� ���� ���� // FALSE// this.Client

	public final static int SEND_WAITINGROOM_WHISPER_MSG = 310; // ���� �ӼӸ� �޽��� ���� ��û String myId, String targetId,
																// String msg Server
	public final static int SUCCESS_SEND_WAITINGROOM_WHISPER_MSG = 1311; // �ӼӸ� �޽��� ���� ���� String myId, String targetId, String msg
																// this.Client
	public final static int SEND_WHISPER_MSG = 1312; // Ư�� �������� �ӼӸ� ���� String id, String msg target.Client
	public final static int FAIL_SEND_WATINGROOM_WHISPER_MSG = 1313; // ���������ʴ� ���̵�, �޽������۽��� FALSE this.Client

	public final static int REQUEST_ONEONONE_MSG = 400; // 1:1��ȭ ���� ��û String myId, String targetid, String msg Server
	public final static int SUCCESS_SEND_ONEONONE_MSG = 1421; // 1:1 ��ȭ ���� ���� String myId, String targetid this.Client
	public final static int SEND_ONEONONE_MSG = 1402; // 1:1 ��ȭ ��뿡�� �޽��� ���� String myId, String targetid, String msg
														// target.Client

	public final static int FAIL_SEND_ONEONE_MSG = 1403; // 1:1: ��ȭ ���� ���� , ��� ����
	public final static int ONEONONECHAT_OUT = 1405; // 1:1 ��ȭ�� �������� ��� �ؽ�Ʈ��Ʈ, �ؽ�Ʈ ����� ��� �� �����ٴ� ���� ����
	public final static int ADD_FRIEND = 500; // ģ�� �߰� ��û String myId, String targetId, String msg Server
	public final static int CAN_YOU_BE_MY_FRIEND = 1501; // Ÿ�Ͼ��̵𿡰� ģ�� ��û�� ���� Stirng id, String msg (Target)Client
	public final static int NOT_EXIST_ID_FAIL_ADD_FRIEND = 1502; // ���������ʴ� ���̵�, ģ���߰����� FALSE this.Client

	public final static int OVERLAP_FRIEND = 1507;// �̹� ģ���߰� �Ǿ����� 
	public final static int RESPOND_YES_REQUSET_ADD_FRIEND = 510; // ģ���߰� ��û�� ���� ����_YES , true, server
	public final static int REQUEST_ADD_FRIEND = 1511; // ģ����û ���� String myId, String targetId this.Client &&
														// target.Client

	public final static int RESPOND_NO_REQUSET_ADD_FRIEND = 520; // ģ���߰� ��û�� ���� ����_NO FALSE Server

	public final static int DELETE_FRIEND = 530; // ģ�� ���� String myId, String targetId Server
	public final static int SUCCESS_DELETE_FRIEND = 1531; // ģ�� ���� ���� String myId, String targetId this.Client
	public final static int FAIL_DELETE_FRIEND = 1532; // ģ�� ���� ���� FALSE this.Client

	public final static int SHOW_BLACKLIST = 600; // ������Ʈ ���� String myId Server
	public final static int SUCCESS_SHOW_BLACKLIST = 1601; // ������Ʈ ���� ���� TRUE this.Client
	public final static int FAIL_SHOW_BLACKLIST = 1602; // ������Ʈ ���� ���� FALSE this.Client

	public final static int ADD_BLACKLIST = 610; // ������Ʈ �߰� String myId, String targetId Server
	public final static int SUCCESS_ADD_BLACKLIST = 1611; // ������Ʈ ��� ���� String targetId this.Client
	public final static int FAIL_ADD_BLACKLIST = 1612; // ������Ʈ ��� ����_��ܻ�Ż�� FALSE this.Client

	public final static int OVERLAP_BLACKLIST = 1618; // ������Ʈ ��� ����// �ߺ�	
	public final static int DELETE_BLACKLIST = 620; // ������Ʈ ���� String myId, String targetId Server
	public final static int SUCCESS_DELETE_BLACKLIST = 1621; // SUCCESS_DELETE_BLACKLIST
	public final static int FAIL_DELETE_BLACKLIST = 1622; // ������Ʈ ���� ����_��ܻ�Ż�� FALSE this.Client

	public final static int CREATE_CHATROOM = 700; // ä�ù���� ��û -> String myId
	public final static int SUCCESS_CREATE_CHATROOM = 1701; // ä�ù� ���� ���� -> String myId, ChatRoom cr
	/*
	 * 190623���翵 �߰� ��������
	 * */
	public final static int CREATE_CAHTROOM_SUCESS_SCATTER_PLEASE = 1702;
	public final static int CREATE_GET_SCATTER_ORDER = 33;
	public final static int ALREADY_EXIST_NAME_OF_CHATROOM = 1703; // ä�ù� �̸��� �̹� ������ / FALSE
	public final static int SEND_INFO_NEW_CAHTROOM_TO_JUPSOK = 1704; // ���θ��� ä�ù��� ������ �����ڵ鿡�� �Ѹ� -> Vector<ChatRoom>
	// ChatRoomList, Vector<String>watingRoomList
	public final static int CREATE_PRIVATE_CHATROOM = 710; // ��� ä�ù� ���� -> String myId, String password
	public final static int SUCCESS_CREATE_PRIVATE_CHATROOM = 1711; // ���ä�ù� ���� ���� -> Vector<ChatRoom> ChatRoomList,
																	// Vector<String> watingRoomUserList, String myId,
																	// // ChatRoom cr, String pw
	public final static int SEND_INFO_NEW_PRIVATE_CHATROOM_TO_JUPSOK = 1712; // ���θ��� ��� ä�ù��� ������ �����ڵ鿡�� �Ѹ� ->
																				// Vector<ChatRoom> ChatRoomList,
																				// Vector<String> watingRoomUserList
	public final static int FAIL_ENTER_NOT_PRIVATE_CHATROOM = 1713; // ��� ä�ù� ��й�ȣ ����ġ, ä�ù� ������� / FALSE

	public final static int ENTER_CHATROOM = 720; // ä�ù� ���� -> String myId
	public final static int SUCCESS_ENTER_CHATROOM = 1721; // ä�ù� ���� ���� ->ChatRoom cr
	public final static int UPDATE_INFO_ENTER_CHATROOM = 1722; // ä�ù� ���� ������ ������ ȸ������ ���� ���� -> Vector<String>
																// watingRoomUserList, ChatRoom cr
	public final static int ENTER_CHATROOM_SUCCESS_SCATTER_PLEASE = 1723;
	public final static int ENTER_GET_SCATTER_ORDER_IN_CHATROOM = 1724;
	public final static int ENTER_GET_SCATTER_ORDER_IN_WAITINGROOM = 1725;
	public final static int FAIL_ENTER_NOT_CHATROOM = 1726; // �ش�ä�ù� ������, ä�ù� ������� / FALSE
	public final static int FAIL_ENTER_OVER_NUMBER = 1727; // ä�ù� �ο� �ʰ�, ä�ù� ������� / FALSE

	public final static int EXIT_CHATROOM = 730; // ä�ù� ������ -> String myId
	// 0626 14:00 �տ��� ����
	public final static int SUCCESS_EXIT_CHATROOM = 1731;
	public final static int SUCCESS_EXIT_CHATROOM_PLEASE_SCATTER = 1732;
	public final static int SUCCESS_EXIT_CHATROOM_SCATTER_TO_WAITINGROOM = 1733; // ä�ù� ������ ���� -> ChatRoom cr, User user;
	public final static int SUCCESS_EXIT_CHATROOM_SCATTER_TO_CHATROOM = 1734; // ä�ù� ������ ���� -> ChatRoom cr, User user;
	public final static int FAIL_EXIT_CHATROOM = 1735; // ä�ù� ������ ���� / FALSE

	public final static int INVITE_CHATROOM = 740; // ä�ù� �ʴ� -> String targetId
	public final static int REQUEST_INVITE_CHATROOM = 1741; // Ÿ�پ��̵𿡰� �ʴ� ��û�� ���� -> ChatRoom cr, String id
	public final static int NOT_EXIST_ID_FAIL_INVITE = 1742; // �������� �ʴ� ���̵�, �ʴ��û ���� / FALSE

	public final static int RESPOND_INVITE_CAHTROOM = 750; // ä�ù� �ʴ뿡 ���� ���� -> boolean true / false
	public final static int INVITE_ACCEPT_CHATROOM = 1751; // ä��â �ʴ� ���� -> Vector<String> wating RoomUerList, ChatRoom
															// cr
	public final static int INVITE_REFUSE_CHATROOM = 1752; // ä��â �ʴ� ���� / FALSE

	// 0703 ���� ����+�߰�
	public final static int KICKOUT_CHATROOM = 760; // ä�ù濡�� ���� -> ChatRoom cr, String targetId
	public final static int SUCCESS_KICKOUT = 1761; // ���� ���� -> Vector<String> watingRoomUserList, ChatRoom cr;
	public final static int FAIL_KICKOUT_NOT_EXIST = 1762; // ä�ù濡 �ش� ���̵� ��� ���� ���� / FALSE
	public final static int YOU_ARE_KICKED = 1763; // ���� �����ض� �ڽ���. �� ���� ���ߴ� ���ڽ���. // chatRoom
	public final static int ANNOUNCE_KICKOUT_MESSAGE_TO_CHATROOM = 1763; // ä�ù濡�� ����������� ������� ������� �˷��� -> String id, Vector<String>
																// watingRoomUserList(������ϸ� ���Ƿ� ��)
	public final static int ANNOUNCE_KICKOUT_MESSAGE_TO_WAITINGROOM = 1763;

	public final static int SEND_CHATROOM_NORMAL_MSG = 770; // ä�ù� �Ϲ� �޽��� ���� -> String myId, String msg
	public final static int FAILED_SEND_MSG = 1771; // �޽��� ���� ���� / FALSE

	public final static int HAVE_HEAD_RIGHT = 1801; // ������� ���� / TRUE
	public final static int NOT_HAVE_HEAD_RIGHT = 1802; // ������� ���� / FALSE
	public final static int ANNOUNCE_SUCCESS_GRANT_HEAD_RIGHT = 1811; // ���� ���� ������ ����ڿ��� �˸� -> String targetId, ChatRoom

	public final static int GRANT_HEAD_RIGHT = 810; // ������� �ѱ�� -> String targeId// cr
	public final static int FAIL_GRANT_HEAD_RIGHT = 1812; // ������� ���� ���� / FALSE
	public final static int ANNOUNCE_HEAD_RIGHT_CHANGE = 1813; // ������� ���� �˸� -> String headId
	public final static int ALRAM_YOU_HAVE_GOT_HEAD_RIGHT = 1814; // ���� ���� ���� �˸� / TRUE
	public final static int SUCCESS_CAHNGE_TITLE = 1821; // ���� ���� ���� -> String myId, ChatRoom cr
	public final static int UPDATE_CHANGED_TITLE = 1822; // ���� ���� ���� / FALSE
	public final static int FAIL_SUCCESS_CHANGE_TITLE = 1823; // ���� ���� ���� / FALSE
	public final static int ALREADY_EXIST_NAME_OF_TITLE = 1824; // �̹� �����ϴ� ���Դϴ� / FALSE
	
	public final static int SHOW_MY_PROFILE = 900; // �ڱ��ڽ� ������ Ȯ�� -> String myId,Vector<User> lis
	public final static int SUCCESS_SHOW_MY_PROFILE = 1901; // ������ ���� ���� -> String myId
	public final static int FAIL_SHOW_MY_PROFILE = 1902; // ������ ���� ���� / FALSE

	public final static int MODIFY_MY_PROFILE = 910; // �ڱ��ڽ� ������ ���� -> User user
	public final static int SUCCESS_SELF_MODIFY_PROFILE = 1911; // �ڱ��ڽ� ������ ���� ���� -> String myId
	public final static int FAIL_SELF_MODIFY_PROFILE = 1912; // �ڱ��ڽ� ������ ���� ���� / FALSE
	public final static int MYPROFILE_OTHER_REFRESH = 1915; // �ڱ��ڽ� ������ ������ ����ģ��â�� ����
	public final static int MYPROFILE_WAITINGUSER_REFRESH = 1918; // �ڱ��ڽ� ������ ������ ���������� ����
	public final static int MYPROFILE_CHATROOMUSER_REFRESH = 1919;
	public final static int SHOW_OTHER_PROFILE = 920; // ���� ������ Ȯ�� -> String id
	public final static int SUCCESS_SHOW_OTHER_PROFILE = 1921; // ���� ������ ���� ���� -> User user
	public final static int FAIL_SHOW_OTHER_PROFILE = 1922; // ���� ������ ���� ���� / FALSE

	public final static int ANNOUNCE_NOTICE_MSG = 1950; // �������� ���� -> String msg
	
	public final static int TRY_SEND_NOTE = 1000; // ���� ���� -> Note note,
	public final static int SEND_NOTE_TO_ME = 9002; // ���� Ŭ���̾�Ʈ���� ���� ���� /
	
//	 ���� ���� �ϴ� �ּ�ó��
//		public final static int TRY_SEND_NOTE = 900; // ���� ���� -> Note note, String targetId
//		public final static int
//		public final static int

//		public final static int ANNOUNCE_NOTICE_MSG = 9001; // ���� ���� Ȯ��  / TRUE
//		public final static int SEND_NOTE_TO_TARGET = 9002; // ���� Ŭ���̾�Ʈ���� ���� ���� / TRUE
//		public final static int FAIL_SEND_NOTE_NOT_FOUND_ID = 9003; // ���� ���� ���� - ���̵� ���� / FALSE
//		public final static int FAIL_SEND_NOTE_EXCEED_BOX = 9004; // ���� ���� ���� = ������ �ʰ� / FALSE

	/*
	 * 190627 ���翵 �߰�
	 * */
	
	public final static int REQUEST_MODIFY_ROOM_TITLE = 15854;
	public final static int SUCESS_MODIFY_ROOM_TITLE = 15853;
	public final static int SCATTER_MODIFY_ROOM_TITLE_PLEASE = 15852;
	public final static int GET_SCATTER_MODIFY_ROOM_TITLE = 15851;
	
	/*
	 * 190627 ���翵 �߰�
	 * */
	public final static int SEND_CHATROOM_MSG_PLEASSE = 185625;
	public final static int REQUEST_SEND_CHATROOM_MSG = 1562584556;
	
	/*
	 * 190628 ���翵 �߰�
	 * */
	
	public final static int CHANGE_CHATROOM_HEAD_PLEASE = 1578822;
	public final static int CHANGE_CHATROOM_HEAD_PLEASE_AND_GETOUT = 546823;
	public final static int OK_SUCESS_CHANGED_HEAD = 556854565;
	
/*
	 * 190702 ���翵 �߰�
	 * 
	 * */
	public final static int SUCCESSE_NOTE_SEND = 5425758;
	public final static int NOTE_SEND_PLEASE = 7825;
	
	
	public final static int OPEN_NOTEBOX_PLEASE = 75835;
	public final static int SUCESSE_OPEN_NOTEBOX = 85545;
	
	
	public final static int SHAKE_IT_BABY_ONE_ON_ONE_PLEASE = 5634154;
	public final static int OK_SHAKE_IT_BABY_ONE_ON_ONE = 563474;
	
	public final static int FAIL_ENTER_CHATROOM_WRONG_PASSWORD = 5684;
	
	public final static int PASSWORD_INPUT_PLEASE = 568341;
	public final static int  PASSWORD_INPUT_DONE = 25483;
	
	
	public final static int CHANGE_CHATROOM_HEAD_BEFORE_EXIT = 5845588;
	public final static int NO_JUPSOK_SO_CANT_WHISPER = 75684;
	
	public final static int OVERFLOW_PERSON_LIMIT_IN_CHATROOM = 545754;
	public final static int NO_EXIST_ID_SEARCH_ID = 56884;
}