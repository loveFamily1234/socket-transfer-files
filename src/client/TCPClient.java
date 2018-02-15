package client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {

	public static void main(String[] args) {
		int length = 0;
		byte[] sendByte = null;
		Socket socket = null;
		DataOutputStream dout = null;
		FileInputStream fin = null;
		try {
			try {
				String path = "E:\\ip";
				File files = new File(path);
				String[] files_Name = files.list();
				File temp = null;
				for (int i = 0; i < files_Name.length; i++) {
					if (path.endsWith(File.separator)) {
						temp = new File(path + files_Name[i]);
					} else {
						temp = new File(path + File.separator + files_Name[i]);
					}
					if (temp.isFile()) {
						fin = new FileInputStream(temp);
						socket = new Socket();
						socket.connect(new InetSocketAddress("127.0.0.1", 33456), 10 * 1000);
						dout = new DataOutputStream(socket.getOutputStream());
						sendByte = new byte[1024];
						dout.writeUTF(temp.getName());
						while ((length = fin.read(sendByte, 0, sendByte.length)) != -1) {
							dout.write(sendByte, 0, length);
							dout.flush();
						}
						socket.close();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
