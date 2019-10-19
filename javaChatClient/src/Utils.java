import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javazoom.jl.player.Player;

public class Utils {
	public static final String LOGIN_BGM = "Login.mp3";
	public static final String CLICK_SOUND = "클릭.mp3";
	public static final String MOUSEIN_SOUND = "버튼.mp3";
	

	public static BufferedImage resize(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();

		BufferedImage dimg = new BufferedImage(150, 150, img.getType());
		Graphics2D g = dimg.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // 걍 그런가보다 해

		g.drawImage(img, 0, 0, 150, 150, 0, 0, w, h, null);
		g.dispose();
		
		return dimg;
	}

	public static void writeFile(Map<String, User> userList, String fileName) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		File file = new File(fileName);
		File fileDirectry = new File(file.getParent());

		if (!fileDirectry.exists()) {
			fileDirectry.mkdirs();
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			fos = new FileOutputStream(fileName, false);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(userList);
			oos.flush();
			oos.reset();

		} catch (NotSerializableException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeAll(oos, fos);
		}

	}

	public static Map<String, User> readFile(String fileName) {
		Map<String, User> userList = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		File file = new File(fileName);
		if (file.exists()) {
			try {
				fis = new FileInputStream(fileName);
				ois = new ObjectInputStream(fis);
				userList = (Map<String, User>) ois.readObject();

			} catch (Exception e) {

			} finally {
				closeAll(ois, fis);
			}
			System.out.println("불러 왔다요");
			return userList;
		}

		System.out.println("404 : NOT FOUND FILE");
		return null;
	}

	public static void closeAll(Closeable... c) {
		for (Closeable temp : c) {
			try {
				temp.close();
			} catch (Exception e) {
			}
		}
	}

	public static void showMsg(JFrame jfm, String msg) {
		JOptionPane.showMessageDialog(jfm, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	public static String currentTimeToString() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat();

		return sdf.format(date);
	}

	public static void btnSetting(JButton btn) {
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setOpaque(false);
	}

	public static void setLabelToDefaultFont(JLabel lbl) {
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lbl.setForeground(Color.WHITE);		
		lbl.setOpaque(false);
	}
	public static void play(String filename) {
		Player player;
		
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
			player = new Player(buffer);
			player.play();
			player.close();
		} catch (Exception e) {

			System.out.println(e);
		}

	}
}
