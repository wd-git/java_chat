
import java.awt.*;

import javax.swing.*;

public class RoomUtils {
	public static final Dimension LABEL_SIZE = new Dimension(50, 50);

	public static JLabel getLabel(String str) {
		JLabel lbl = new JLabel(str, JLabel.LEFT);
		lbl.setPreferredSize(LABEL_SIZE);

		return lbl;
	}

}
