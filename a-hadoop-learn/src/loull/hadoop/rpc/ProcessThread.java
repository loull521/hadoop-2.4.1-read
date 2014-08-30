package loull.hadoop.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * �����������ڽ���Զ��ͨ��ʱ���˴˿��Է��͸������͵����ݡ������Ǻ������͵����ݣ������Զ��������е���ʽ�������ϴ��͡����ͷ���Ҫ�����Java����ת��Ϊ�ֽ����У������������ϴ��ͣ����շ�����Ҫ���ֽ������ٻָ�ΪJava����
 *������Java����ת��Ϊ�ֽ����еĹ��̳�Ϊ��������л���
 *�������ֽ����лָ�ΪJava����Ĺ��̳�Ϊ����ķ����л���
 *������������л���Ҫ��������;��
 *����1�� �Ѷ�����ֽ��������õر��浽Ӳ���ϣ�ͨ�������һ���ļ��У�
 *����2�� �������ϴ��Ͷ�����ֽ����С�.
 */
public class ProcessThread extends Thread {

    private NameNode nameNode;
    private Socket socket;


    public ProcessThread(){}

    public ProcessThread(NameNode nameNode,Socket socket) {
        this.nameNode=nameNode;
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Data data = (Data) objectInputStream.readObject();
            NameNodeHandler.doMethod(nameNode, data);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.print("��������ʧ��....");
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.print("socket �ر�ʧ��");
            }
        }
    }
}
