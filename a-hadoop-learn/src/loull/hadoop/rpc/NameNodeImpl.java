package loull.hadoop.rpc;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-7-23
 * Time: ����8:41
 * To change this template use File | Settings | File Templates.
 */
public class NameNodeImpl implements NameNode {
    @Override
    public String say(String message) {
        System.out.println("�ͻ���������Ϣ��" + message);
       return "hello master...".equals(message)?"hello salve!":"hello!";
    }
}
