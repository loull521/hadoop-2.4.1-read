package loull.hadoop.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * 序列化和序列化
 * 当两个进程在进行远程通信时，彼此可以发送各种类型的数据。无论是何种类型的数据，都会以二进制序列的形式在网络上传送。发送方需要把这个Java对象转换为字节序列，才能在网络上传送；接收方则需要把字节序列再恢复为Java对象。
 *　　把Java对象转换为字节序列的过程称为对象的序列化。
 *　　把字节序列恢复为Java对象的过程称为对象的反序列化。
 *　　对象的序列化主要有两种用途：
 *　　1） 把对象的字节序列永久地保存到硬盘上，通常存放在一个文件中；
 *　　2） 在网络上传送对象的字节序列。.
 */
public class RequestHandler {


    public static Data request(Data data,String host,int port) {
        try {
            Socket socket = new Socket(host,port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            data = (Data) objectInputStream.readObject();
            System.out.println("服务器反馈消息：" + data.getResult());
            objectOutputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("请求失败....");
        }
        return data;
    }
}
