import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Server implements Protocol {
	/*
	 * 서버가 할일 : 접속한 클라이언트의 소켓정보를 받아서 쓰레드에 전달 클라이언트는 이너쓰레드 or 별도쓰레드를 이용해서 서버에서 오는 응답을
	 * 기다림 트랜스포트 쓰레드는 opcode를 판별해서 클라이언트의 요청에 맞는 메소드를 실행하고 응답결과를 code를 던져줌 클라이언트
	 * 매니저는 클라이언트의 요청에 따라
	 */

	private Map<String, User> userList; // 회원가입 되어있는 모든 유저 정보 -> File In/Out : (ObjectIO) -> 프로필 확인 시 사용
	private ClientManager cm;
	private ServerForm sf;

	public Server() {
		init();
		
		sf = new ServerForm(this, cm);

		new Thread() {
			public void run() {
				ServerSocket s;
				synchronized (this) {
					try {
						System.out.println("연결을 기다리는 중..");
						s = new ServerSocket(8080);

						while (true) {
							Socket sock = s.accept();
							sf.appendMsg(sock.getInetAddress() + " 님이 접속. \n");
							TransportThread newThread = new TransportThread(sock, userList, cm, Server.this);
							System.out.println("Transport 스레드 생성");
							newThread.start();
							System.out.println("Transport 스레드 시작");
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private void init() {
		cm = new ClientManager();
		userList = Utils.readFile("data\\data.dat");
	}

	public void broadCast(SendData data) {
		// 접속하고 있는 모든 유저의 쓰레드의 oos에 쓰기
		String msg = (String) data.getData()[0];
		Collection<TransportThread> tempVec = cm.getThreadMap().values();
		for (TransportThread temp : tempVec) {
			temp.sendData(data);
		}
	}

	public void printUsers() {
		for (User temp : userList.values()) {
			temp.toString();
		}
	}

	public User getUserById(String id) {
		return userList.get(id);
	}

	public ClientManager getCm() {
		return cm;
	}
		
	public ServerForm getSf() {
		return sf;
	}

	public void setSf(ServerForm sf) {
		this.sf = sf;
	}

	public void broadCast(String msg) {
		//접속하고 있는 모든 유저의 쓰레드의 oos에 쓰기
		Collection<TransportThread> tempVec = cm.getThreadMap().values();
		for(TransportThread temp : tempVec) {
			temp.sendData(new SendData(ANNOUNCE_NOTICE_MSG, msg));
		}
	}
	
	
		
	public Map<String, User> getUserList() {
		return userList;
	}

	public void setUserList(Map<String, User> userList) {
		this.userList = userList;
	}

	public void setCm(ClientManager cm) {
		this.cm = cm;
	}

	public static void main(String[] args) {
		new Server();
	}
}