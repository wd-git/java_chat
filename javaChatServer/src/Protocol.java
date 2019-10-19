interface Protocol { // 프토로콜들 정의

	public final static int LOGIN_PLEASE = 100; // 로그인 요청 -> String Id, Strign Pw;
	public final static int LOGIN_SUCCESS_PRIVATE = 1101; // 로그인 정보 일치 ->
															// Vector<ChatRoom>
															// ChatRoomList,
															// Vector<String>
	// waitingRoomUserList, User user / this.Client
	/*
	 * 190623 이재영 프로토콜 추가
	 */
	public final static int LOGIN_SUCCESS_SCATTER_PLEASE = 77;
	public final static int LOGIN_GET_SCATTTER_ORDER = 78;
	public final static int ANNOUNCE_ENTER_WAITINGROOM = 1102; // 대기실에 있는 유저들에게
																// 알림 / String
																// enteredId /
																// other all
																// Client
																// in
																// waitingroom
	public final static int LOGIN_NOT_FOUND_ID = 1103; // 아이디를 찾을 수 없음 / FALSE /
														// this.Client
	public final static int LOGIN_WRONG_PASSWORD = 1004; // 비밀번호 틀림 / FALSE /
															// this.Client
	public final static int LOGIN_DUPLICATION = 1005; // 이미 중복된 아이디가 로그인해 있읍니다.
	public final static int UPDATE_MEMBER_LIST = 1006;

	public final static int LOGIN_CONFIRM = 110; // 로그인 성공, 이동 요청

	public final static int LOOKUP_ID = 120; // 아이디 찾기 요청
	public final static int FOUND_ID = 1121; // 아이디 찾음 / String id / this.Client
	public final static int NOT_FOUND_ID = 1122; // 아이디 없음 / FALSE / this.Client
	
	public final static int LOOKUP_PW = 130; // 비밀번호 찾기 요청 / String Id, String mailAdress / Server
	public final static int FOUND_PW = 1131; // 비밀번호 찾음 String password this.Client
	public final static int NOT_FOUND_INFO_PW = 1132; // 일치하는 정보가 없음 FALSE this.Client

	public final static int WITHDRAW = 140; // 탈퇴 요청 String Id, String Pw Server
	public final static int WITHDRAW_OK = 1401; // 탈퇴승인 TRUE this.Client
	public final static int NOTICE_WITHDRAW = 1402; // 회원의 탈퇴결과를 회원들에게 반영 Vector<String> connetedUsersList
													// connected.Client
	public final static int NOT_FOUND_INFO_WITHDARW = 1403; // 일치하는 정보가 없음 FALSE this.Client
	
	public final static int LOGOUT = 150; // 나 나간다. 바이루 // String id;
	public final static int ANNOUNCE_LOGOUT_INFO = 1150; // 떠나는 자의 뒷모습은 아름다워야 한다더라.. // String id
	
	///  이부분 정리 필요 
	
	public final static int ID_NICK = 160;
	

	public final static int JOIN = 200; // 회원가입 요청 User user Server
	public final static int JOIN_SUCCESS = 1201; // 회원가입 성공 TRUE this.Client
	public final static int JOIN_NON_SAME_PASSWORD = 1202; // 비밀번호 일치 하지 않음. FALSE this.Client

	public final static int VERIFY_ID = 210; // 중복확인 요청 String id Server
	public final static int JOIN_ALREADY_EXIST_ID = 1211; // 이미 존재하는 아이디 입니다. FALSE this.Client
	public final static int SUCCESS_OVERLAP_CHECK = 1212; // 중복임 FALSE this.Client

	public final static int SEND_WAITINGROOM_NORMAL_MSG = 300; // 대기실 일반 메시지 전송 요청 String myId, String msg Server
	public final static int SUCCESS_SEND_WATINGROOM_NORMAL_MSG = 1302; // 대기실에게 있는 유저들에게 메시지 전송 String id other all Client in
																// waitingroom
	public final static int FAILED_SEND_NORMAL_MSG = 1303; // 메시지 전송 실패 // FALSE// this.Client

	public final static int SEND_WAITINGROOM_WHISPER_MSG = 310; // 대기실 귓속말 메시지 전송 요청 String myId, String targetId,
																// String msg Server
	public final static int SUCCESS_SEND_WAITINGROOM_WHISPER_MSG = 1311; // 귓속말 메시지 전송 성공 String myId, String targetId, String msg
																// this.Client
	public final static int SEND_WHISPER_MSG = 1312; // 특정 유저에게 귓속말 전달 String id, String msg target.Client
	public final static int FAIL_SEND_WATINGROOM_WHISPER_MSG = 1313; // 존재하지않는 아이디, 메시지전송실패 FALSE this.Client

	public final static int REQUEST_ONEONONE_MSG = 400; // 1:1대화 생성 요청 String myId, String targetid, String msg Server
	public final static int SUCCESS_SEND_ONEONONE_MSG = 1421; // 1:1 대화 생성 성공 String myId, String targetid this.Client
	public final static int SEND_ONEONONE_MSG = 1402; // 1:1 대화 상대에게 메시지 전달 String myId, String targetid, String msg
														// target.Client

	public final static int FAIL_SEND_ONEONE_MSG = 1403; // 1:1: 대화 생성 실패 , 상대 거절
	public final static int ONEONONECHAT_OUT = 1405; // 1:1 대화방 나갔을때 상대 텍스트필트, 텍스트 에어리어 잠금 및 나갔다는 내용 전송
	public final static int ADD_FRIEND = 500; // 친구 추가 요청 String myId, String targetId, String msg Server
	public final static int CAN_YOU_BE_MY_FRIEND = 1501; // 타켓아이디에게 친구 요청을 보냄 Stirng id, String msg (Target)Client
	public final static int NOT_EXIST_ID_FAIL_ADD_FRIEND = 1502; // 존재하지않는 아이디, 친구추가실패 FALSE this.Client

	public final static int OVERLAP_FRIEND = 1507;// 이미 친구추가 되어있음 
	public final static int RESPOND_YES_REQUSET_ADD_FRIEND = 510; // 친구추가 요청에 대한 응답_YES , true, server
	public final static int REQUEST_ADD_FRIEND = 1511; // 친구요청 전달 String myId, String targetId this.Client &&
														// target.Client

	public final static int RESPOND_NO_REQUSET_ADD_FRIEND = 520; // 친구추가 요청에 대한 응답_NO FALSE Server

	public final static int DELETE_FRIEND = 530; // 친구 삭제 String myId, String targetId Server
	public final static int SUCCESS_DELETE_FRIEND = 1531; // 친구 삭제 성공 String myId, String targetId this.Client
	public final static int FAIL_DELETE_FRIEND = 1532; // 친구 삭제 실패 FALSE this.Client

	public final static int SHOW_BLACKLIST = 600; // 블랙리스트 보기 String myId Server
	public final static int SUCCESS_SHOW_BLACKLIST = 1601; // 블랙리스트 보기 성공 TRUE this.Client
	public final static int FAIL_SHOW_BLACKLIST = 1602; // 블랙리스트 보기 실패 FALSE this.Client

	public final static int ADD_BLACKLIST = 610; // 블랙리스트 추가 String myId, String targetId Server
	public final static int SUCCESS_ADD_BLACKLIST = 1611; // 블랙리스트 등록 성공 String targetId this.Client
	public final static int FAIL_ADD_BLACKLIST = 1612; // 블랙리스트 등록 실패_고단새탈퇴 FALSE this.Client

	public final static int OVERLAP_BLACKLIST = 1618; // 블랙리스트 등록 실패// 중복	
	public final static int DELETE_BLACKLIST = 620; // 블랙리스트 삭제 String myId, String targetId Server
	public final static int SUCCESS_DELETE_BLACKLIST = 1621; // SUCCESS_DELETE_BLACKLIST
	public final static int FAIL_DELETE_BLACKLIST = 1622; // 블랙리스트 삭제 실패_고단새탈퇴 FALSE this.Client

	public final static int CREATE_CHATROOM = 700; // 채팅방생성 요청 -> String myId
	public final static int SUCCESS_CREATE_CHATROOM = 1701; // 채팅방 생성 성공 -> String myId, ChatRoom cr
	/*
	 * 190623이재영 추가 프로토콜
	 * */
	public final static int CREATE_CAHTROOM_SUCESS_SCATTER_PLEASE = 1702;
	public final static int CREATE_GET_SCATTER_ORDER = 33;
	public final static int ALREADY_EXIST_NAME_OF_CHATROOM = 1703; // 채팅방 이름이 이미 존재함 / FALSE
	public final static int SEND_INFO_NEW_CAHTROOM_TO_JUPSOK = 1704; // 새로만든 채팅방의 정보를 접속자들에게 뿌림 -> Vector<ChatRoom>
	// ChatRoomList, Vector<String>watingRoomList
	public final static int CREATE_PRIVATE_CHATROOM = 710; // 비밀 채팅방 생성 -> String myId, String password
	public final static int SUCCESS_CREATE_PRIVATE_CHATROOM = 1711; // 비밀채팅방 생성 성공 -> Vector<ChatRoom> ChatRoomList,
																	// Vector<String> watingRoomUserList, String myId,
																	// // ChatRoom cr, String pw
	public final static int SEND_INFO_NEW_PRIVATE_CHATROOM_TO_JUPSOK = 1712; // 새로만든 비밀 채팅방의 정보를 접속자들에게 뿌림 ->
																				// Vector<ChatRoom> ChatRoomList,
																				// Vector<String> watingRoomUserList
	public final static int FAIL_ENTER_NOT_PRIVATE_CHATROOM = 1713; // 비밀 채팅방 비밀번호 불일치, 채팅방 입장실패 / FALSE

	public final static int ENTER_CHATROOM = 720; // 채팅방 입장 -> String myId
	public final static int SUCCESS_ENTER_CHATROOM = 1721; // 채팅방 입장 성공 ->ChatRoom cr
	public final static int UPDATE_INFO_ENTER_CHATROOM = 1722; // 채팅방 입장 했을때 나머지 회원들의 정보 갱신 -> Vector<String>
																// watingRoomUserList, ChatRoom cr
	public final static int ENTER_CHATROOM_SUCCESS_SCATTER_PLEASE = 1723;
	public final static int ENTER_GET_SCATTER_ORDER_IN_CHATROOM = 1724;
	public final static int ENTER_GET_SCATTER_ORDER_IN_WAITINGROOM = 1725;
	public final static int FAIL_ENTER_NOT_CHATROOM = 1726; // 해당채팅방 미존재, 채팅방 입장실패 / FALSE
	public final static int FAIL_ENTER_OVER_NUMBER = 1727; // 채팅방 인원 초과, 채팅방 입장실패 / FALSE

	public final static int EXIT_CHATROOM = 730; // 채팅방 나가기 -> String myId
	// 0626 14:00 손원도 수정
	public final static int SUCCESS_EXIT_CHATROOM = 1731;
	public final static int SUCCESS_EXIT_CHATROOM_PLEASE_SCATTER = 1732;
	public final static int SUCCESS_EXIT_CHATROOM_SCATTER_TO_WAITINGROOM = 1733; // 채팅방 나가기 성공 -> ChatRoom cr, User user;
	public final static int SUCCESS_EXIT_CHATROOM_SCATTER_TO_CHATROOM = 1734; // 채팅방 나가기 성공 -> ChatRoom cr, User user;
	public final static int FAIL_EXIT_CHATROOM = 1735; // 채팅방 나가기 실패 / FALSE

	public final static int INVITE_CHATROOM = 740; // 채팅방 초대 -> String targetId
	public final static int REQUEST_INVITE_CHATROOM = 1741; // 타겟아이디에게 초대 요청을 보냄 -> ChatRoom cr, String id
	public final static int NOT_EXIST_ID_FAIL_INVITE = 1742; // 존재하지 않는 아이디, 초대요청 실패 / FALSE

	public final static int RESPOND_INVITE_CAHTROOM = 750; // 채팅방 초대에 대한 응답 -> boolean true / false
	public final static int INVITE_ACCEPT_CHATROOM = 1751; // 채팅창 초대 수락 -> Vector<String> wating RoomUerList, ChatRoom
															// cr
	public final static int INVITE_REFUSE_CHATROOM = 1752; // 채팅창 초대 거절 / FALSE

	// 0703 원도 수정+추가
	public final static int KICKOUT_CHATROOM = 760; // 채팅방에서 강퇴 -> ChatRoom cr, String targetId
	public final static int SUCCESS_KICKOUT = 1761; // 강퇴 성공 -> Vector<String> watingRoomUserList, ChatRoom cr;
	public final static int FAIL_KICKOUT_NOT_EXIST = 1762; // 채팅방에 해당 아이디 없어서 강퇴 실패 / FALSE
	public final static int YOU_ARE_KICKED = 1763; // 말좀 조심해라 자슥아. 니 강퇴 당했다 이자슥아. // chatRoom
	public final static int ANNOUNCE_KICKOUT_MESSAGE_TO_CHATROOM = 1763; // 채팅방에서 강퇴당했음을 강퇴당한 사람에게 알려줌 -> String id, Vector<String>
																// watingRoomUserList(강퇴당하면 대기실로 감)
	public final static int ANNOUNCE_KICKOUT_MESSAGE_TO_WAITINGROOM = 1763;

	public final static int SEND_CHATROOM_NORMAL_MSG = 770; // 채팅방 일반 메시지 전송 -> String myId, String msg
	public final static int FAILED_SEND_MSG = 1771; // 메시지 전송 실패 / FALSE

	public final static int HAVE_HEAD_RIGHT = 1801; // 방장권한 있음 / TRUE
	public final static int NOT_HAVE_HEAD_RIGHT = 1802; // 방장권한 없음 / FALSE
	public final static int ANNOUNCE_SUCCESS_GRANT_HEAD_RIGHT = 1811; // 방장 권한 이전을 당사자에게 알림 -> String targetId, ChatRoom

	public final static int GRANT_HEAD_RIGHT = 810; // 방장권한 넘기기 -> String targeId// cr
	public final static int FAIL_GRANT_HEAD_RIGHT = 1812; // 방장권한 이전 실패 / FALSE
	public final static int ANNOUNCE_HEAD_RIGHT_CHANGE = 1813; // 방장권한 변경 알림 -> String headId
	public final static int ALRAM_YOU_HAVE_GOT_HEAD_RIGHT = 1814; // 방장 권한 이전 알림 / TRUE
	public final static int SUCCESS_CAHNGE_TITLE = 1821; // 방제 변경 성공 -> String myId, ChatRoom cr
	public final static int UPDATE_CHANGED_TITLE = 1822; // 방제 변경 갱신 / FALSE
	public final static int FAIL_SUCCESS_CHANGE_TITLE = 1823; // 방제 변경 실패 / FALSE
	public final static int ALREADY_EXIST_NAME_OF_TITLE = 1824; // 이미 존재하는 방입니다 / FALSE
	
	public final static int SHOW_MY_PROFILE = 900; // 자기자신 프로필 확인 -> String myId,Vector<User> lis
	public final static int SUCCESS_SHOW_MY_PROFILE = 1901; // 프로필 열람 성공 -> String myId
	public final static int FAIL_SHOW_MY_PROFILE = 1902; // 프로필 열람 실패 / FALSE

	public final static int MODIFY_MY_PROFILE = 910; // 자기자신 프로필 수정 -> User user
	public final static int SUCCESS_SELF_MODIFY_PROFILE = 1911; // 자기자신 프로필 수정 성공 -> String myId
	public final static int FAIL_SELF_MODIFY_PROFILE = 1912; // 자기자신 프로필 수정 실패 / FALSE
	public final static int MYPROFILE_OTHER_REFRESH = 1915; // 자기자신 프로필 수정시 상대방친구창에 갱신
	public final static int MYPROFILE_WAITINGUSER_REFRESH = 1918; // 자기자신 프로필 수정시 접속유저에 갱신
	public final static int MYPROFILE_CHATROOMUSER_REFRESH = 1919;
	public final static int SHOW_OTHER_PROFILE = 920; // 상대방 프로필 확인 -> String id
	public final static int SUCCESS_SHOW_OTHER_PROFILE = 1921; // 상대방 프로필 열람 성공 -> User user
	public final static int FAIL_SHOW_OTHER_PROFILE = 1922; // 상대방 프로필 열람 실패 / FALSE

	public final static int ANNOUNCE_NOTICE_MSG = 1950; // 공지사항 전달 -> String msg
	
	public final static int TRY_SEND_NOTE = 1000; // 쪽지 전송 -> Note note,
	public final static int SEND_NOTE_TO_ME = 9002; // 받을 클라이언트에게 쪽지 전송 /
	
//	 쓸지 몰라서 일단 주석처리
//		public final static int TRY_SEND_NOTE = 900; // 쪽지 전송 -> Note note, String targetId
//		public final static int
//		public final static int

//		public final static int ANNOUNCE_NOTICE_MSG = 9001; // 쪽지 전송 확인  / TRUE
//		public final static int SEND_NOTE_TO_TARGET = 9002; // 받을 클라이언트에게 쪽지 전송 / TRUE
//		public final static int FAIL_SEND_NOTE_NOT_FOUND_ID = 9003; // 쪽지 전송 실패 - 아이디 없음 / FALSE
//		public final static int FAIL_SEND_NOTE_EXCEED_BOX = 9004; // 쪽지 전송 실패 = 쪽지함 초과 / FALSE

	/*
	 * 190627 이재영 추가
	 * */
	
	public final static int REQUEST_MODIFY_ROOM_TITLE = 15854;
	public final static int SUCESS_MODIFY_ROOM_TITLE = 15853;
	public final static int SCATTER_MODIFY_ROOM_TITLE_PLEASE = 15852;
	public final static int GET_SCATTER_MODIFY_ROOM_TITLE = 15851;
	
	/*
	 * 190627 이재영 추가
	 * */
	public final static int SEND_CHATROOM_MSG_PLEASSE = 185625;
	public final static int REQUEST_SEND_CHATROOM_MSG = 1562584556;
	
	/*
	 * 190628 이재영 추가
	 * */
	
	public final static int CHANGE_CHATROOM_HEAD_PLEASE = 1578822;
	public final static int CHANGE_CHATROOM_HEAD_PLEASE_AND_GETOUT = 546823;
	public final static int OK_SUCESS_CHANGED_HEAD = 556854565;
	
/*
	 * 190702 이재영 추가
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