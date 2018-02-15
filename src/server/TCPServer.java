package server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Runnable {

	static int count = 0;
    @Override
    public void run() {

    }

    public static void main(String[] args) {
    	
        try {
            final ServerSocket server = new ServerSocket(33456);
            Thread th = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                           System.out.println("��ʼ����������");
                           Socket socket = server.accept();
                           System.out.println("������");
                           receiveFile(socket);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            });
            th.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void receiveFile(Socket socket) throws IOException {
        byte[] inputByte = null;
        int length = 0;
        DataInputStream din = null;
        FileOutputStream fout = null;
        try {
        	String path = "F:\\ip";
        	(new File(path)).mkdirs();//����ļ��в����ڣ��������ļ���

//            while(true){
            	din = new DataInputStream(socket.getInputStream());
            	File file = new File(path+File.separator+din.readUTF());
            	if (!file.exists()) {   //�ļ��������򴴽��ļ����ȴ���Ŀ¼  
	                file.createNewFile();  
	            } 
            	fout = new FileOutputStream(file);
                inputByte = new byte[1024];
                System.out.println("��ʼ��������...");
                while (true) {
                    if (din != null) {
                        length = din.read(inputByte, 0, inputByte.length);
                    }
                    if (length == -1) {
                        break;
                    }
                    System.out.println("�ļ�����Ϊ��"+length);
                    fout.write(inputByte, 0, length);
                    fout.flush();
                }
                count++;
                System.out.println("��"+count+"���ļ���ȡ����");
                
//            }
        } catch (Exception ex) {
	           ex.printStackTrace();
        } finally {
            if (socket != null)
                socket.close();
        }
    }

}
