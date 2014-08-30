package loull.hadoop.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RPC��Remote Procedure Call Protocol������Զ�̹��̵���Э�飬����һ��ͨ�������Զ�̼�����������������
 * ������Ҫ�˽�ײ����缼����Э�顣RPCЭ��ٶ�ĳЩ����Э��Ĵ��ڣ���TCP��UDP��Ϊͨ�ų���֮��Я����Ϣ���ݡ�
 * ��OSI����ͨ��ģ���У�RPC��Խ�˴�����Ӧ�ò㡣RPCʹ�ÿ�����������ֲ�ʽ��������ڵ�Ӧ�ó���������ס�
 *
 * ����ԭ��
 * ����ʱ,һ�οͻ����Է�������RPC����,���ڲ���������������ʮ����
 * 1.���ÿͻ��˾����ִ�д��Ͳ���
 * 2.���ñ���ϵͳ�ں˷���������Ϣ
 * 3.��Ϣ���͵�Զ������
 * 4.����������õ���Ϣ��ȡ�ò���
 * 5.ִ��Զ�̹���
 * 6.ִ�еĹ��̽�������ط��������
 * 7.������������ؽ��������Զ��ϵͳ�ں�
 * 8.��Ϣ���ر�������
 * 9.�ͻ�������ں˽�����Ϣ
 * 10.�ͻ����վ�����ص�����
 *
 * RPC�࣬�ṩ�����������ֱ���getServer()��getProxy().������������˺Ϳͻ���
 */
public class RPC {
    /**
     * ʹ�ô�ͳ��ServerSocket����һ������
     *
     * @param nameNode ͨ��Э��ӿڣ��˲�����ҪЭ��ӿڵ�ʵ����
     * @param port     ����˵ļ����Ķ˿ں�
     * @param backlog  ServerSocket���췽����backlog����������ʽ��������������еĳ��ȣ�
     *                 �������ǲ���ϵͳ�޶��Ķ��е���󳤶ȡ�ֵ��ע����ǣ������¼��������
     *                 ��Ȼ����ò���ϵͳ�޶��Ķ��е���󳤶ȣ�
     *                 l backlog������ֵ���ڲ���ϵͳ�޶��Ķ��е���󳤶ȡ�
     *                 l backlog������ֵС�ڻ����0��
     *                 l ��ServerSocket���췽����û������backlog����
     * @param host     ����˵�ip��ַ
     * @throws Exception
     */
    public static void getServer(NameNode nameNode, int port, int backlog, String host) throws Exception {
        ServerSocket objSer = new ServerSocket(port, backlog, InetAddress.getByName(host));
        while (true) {
            Socket socket = objSer.accept();
            //�����µ��̣߳���������ͻ��˵�����
            new ProcessThread(nameNode, socket).start();
        }

    }

    /**
     * ʹ��Java��̬������������ͨ��Э�������ʵ����
     *
     * @param clazz ͨ��Э��ӿ��࣬��ǰ�����и���Ӧ���ǻ���
     * @param host  ����˵�ip��ַ
     * @param port  ����˼����Ķ˿ں�
     * @return ����Ľӿ�ʵ����
     * @throws Exception
     */
    public static NameNode getProxy(Class clazz, String host, int port) throws Exception {
        Class[] interfaces = new Class[1];
        interfaces[0] = clazz;
        /*
        * ����ģʽ�ǳ��õ�java���ģʽ��
        * ���������Ǵ�������ί������ͬ���Ľӿڣ���������Ҫ����Ϊί����Ԥ������Ϣ��������Ϣ������Ϣת����ί���࣬�Լ��º�����Ϣ�ȡ�
        * ��ô�����ί�����ڿͻ���ʵ�����ǲ����ڵģ�ͨ�����л�����֮�󽫳���Ҫִ�е���Ϣ���ݸ������
        * */
        return (NameNode) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Data data = new Data();
                data.setMethod((String) method.getName());
                data.setParam((String) args[0]);
                return RequestHandler.request(data, "127.0.0.1", 8888).getResult();
            }
        });
    }

}