import java.io.Serializable;

public class SendData implements Serializable, Protocol {
	private static final long serialVersionUID = 2L;

	private int opcode;
	private Object[] data;

	public SendData(int opcode, Object... data) {
		this.opcode = opcode;
		this.data = data;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	// public static void main(String[] args){
	// SendData sd = (SendData)obj;
	// String opcode = sd.getOpcode();
	//
	// if(opcode.equals(100)){
	// //login (id pw)
	//
	// data[0] = id;
	// data[1] = pw;
	// }
	// }
} // class

