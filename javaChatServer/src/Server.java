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
	 * ������ ���� : ������ Ŭ���̾�Ʈ�� ���������� �޾Ƽ� �����忡 ���� Ŭ���̾�Ʈ�� �̳ʾ����� or ���������带 �̿��ؼ� �������� ���� ������
	 * ��ٸ� Ʈ������Ʈ ������� opcode�� �Ǻ��ؼ� Ŭ���̾�Ʈ�� ��û�� �´� �޼ҵ带 �����ϰ� �������� code�� ������ Ŭ���̾�Ʈ
	 * �Ŵ����� Ŭ���̾�Ʈ�� ��û�� ����
	 */

	private Map<String, User> userList; // ȸ������ �Ǿ��ִ� ��� ���� ���� -> File In/Out : (ObjectIO) -> ������ Ȯ�� �� ���
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
						System.out.println("������ ��ٸ��� ��..");
						s = new ServerSocket(8080);

						while (true) {
							Socket sock = s.accept();
							sf.appendMsg(sock.getInetAddress() + " ���� ����. \n");
							TransportThread newThread = new TransportThread(sock, userList, cm, Server.this);
							System.out.println("Transport ������ ����");
							newThread.start();
							System.out.println("Transport ������ ����");
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
		// �����ϰ� �ִ� ��� ������ �������� oos�� ����
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
		//�����ϰ� �ִ� ��� ������ �������� oos�� ����
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