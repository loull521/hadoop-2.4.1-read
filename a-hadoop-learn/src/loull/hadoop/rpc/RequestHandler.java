package loull.hadoop.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * ���л������л�
 * �����������ڽ���Զ��ͨ��ʱ���˴˿��Է��͸������͵����ݡ������Ǻ������͵����ݣ������Զ��������е���ʽ�������ϴ��͡����ͷ���Ҫ�����Java����ת��Ϊ�ֽ����У������������ϴ��ͣ����շ�����Ҫ���ֽ������ٻָ�ΪJava����
 *������Java����ת��Ϊ�ֽ����еĹ��̳�Ϊ��������л���
 *�������ֽ����лָ�ΪJava����Ĺ��̳�Ϊ����ķ����л���
 *������������л���Ҫ��������;��
 *����1�� �Ѷ�����ֽ��������õر��浽Ӳ���ϣ�ͨ�������һ���ļ��У�
 *����2�� �������ϴ��Ͷ�����ֽ����С�.
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
            System.out.println("������������Ϣ��" + data.getResult());
            objectOutputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("����ʧ��....");
        }
        return data;
    }
}
