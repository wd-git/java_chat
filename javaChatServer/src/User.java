import java.awt.Image;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.Icon;

public class User implements Serializable {
	private static final long serialVersionUID = 3L;
	private String id;
	private String pw;
	private String nickname;
	private String name;
	private String email;
	private boolean isHead;
	private Icon profileIcon;
	private String location;
	private Vector<User> friendList = new Vector<User>();
	private Vector<User> blackList = new Vector<User>();

	// -> 이너 스레드 하나로 - 친구목록, 블랙리스트 등 정보를 갱신
/*
	 * 190702 이재영이 추가
	 */
	
	private Vector<Note> noteBox;
	public User(String id){
		this.id = id;
	}
	
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	public User(String id, String pw, String name, String nickname,
			String email, Icon profileIcon) {
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.profileIcon = profileIcon;
		noteBox = new Vector<Note>();
	}

	public void addNote(Note note){
		if(!(noteBox.contains(note))){
				noteBox.add(note);
		}
	}
	public Vector<User> getFriendList() {
		return friendList;
	}

	public void setFriendList(Vector<User> friendList) {
		this.friendList = friendList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHead() {
		return isHead;
	}

	public void setHead(boolean isHead) {
		this.isHead = isHead;
	}

	public Icon getProfileIcon() {
		return profileIcon;
	}

	public void setProfileIcon(Icon profileIcon) {
		this.profileIcon = profileIcon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Vector<User> getFriendsIdList() {
		return friendList;
	}

	public void setFriendsIdList(Vector<User> friendsIdList) {
		friendList = friendsIdList;
	}

	public Vector<User> getBlackList() {
		return blackList;
	}

	public Vector<Note> getNoteBox() {
		return noteBox;
	}

	public void setNoteBox(Vector<Note> noteBox) {
		this.noteBox = noteBox;
	}
	public void setBlackList(Vector<User> blackList) {
		this.blackList = blackList;
	}

	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void addFriendList(User user) {
		if(!(friendList.contains(user))) {
			friendList.add(user);
		}
	}
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void removeFriendList(User user) {
		if((friendList.contains(user))) {
			friendList.remove(user);
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void addBlackList(User user) {
		if(!(blackList.contains(user))) {
			blackList.add(user);
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void removeBlackList(User user) {
		if((blackList.contains(user))) {
			blackList.remove(user);
		}
	}
	
	@Override
	
	public String toString() {
		return getNickname() + "(" + getId()+ ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User) || obj == null) {
			return false;
		}
		User temp = (User) obj;
		return id.equals(temp.id);
	}
}
