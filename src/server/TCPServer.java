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
                           System.out.println("开始监听。。。");
                           Socket socket = server.accept();
                           System.out.println("有链接");
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
        	(new File(path)).mkdirs();//如果文件夹不存在，则建立新文件夹

//            while(true){
            	din = new DataInputStream(socket.getInputStream());
            	File file = new File(path+File.separator+din.readUTF());
            	if (!file.exists()) {   //文件不存在则创建文件，先创建目录  
	                file.createNewFile();  
	            } 
            	fout = new FileOutputStream(file);
                inputByte = new byte[1024];
                System.out.println("开始接收数据...");
                while (true) {
                    if (din != null) {
                        length = din.read(inputByte, 0, inputByte.length);
                    }
                    if (length == -1) {
                        break;
                    }
                    System.out.println("文件长度为："+length);
                    fout.write(inputByte, 0, length);
                    fout.flush();
                }
                count++;
                System.out.println("第"+count+"个文件读取结束");
                
//            }
        } catch (Exception ex) {
	           ex.printStackTrace();
        } finally {
            if (socket != null)
                socket.close();
        }
    }

}
