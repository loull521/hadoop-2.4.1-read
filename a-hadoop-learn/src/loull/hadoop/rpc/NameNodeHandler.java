package loull.hadoop.rpc;

import java.lang.reflect.Method;

/**
 * ʹ�÷��似�������пͻ�������ķ���
 */
public class NameNodeHandler {

    public static Data doMethod(NameNode nameNode, Data data) {
        try {
            String methodName = data.getMethod();
            Class clazz = nameNode.getClass();
            Method m = clazz.getMethod(methodName, String.class);
            String result = (String) m.invoke(nameNode, data.getParam());
            data.setResult(result);

        } catch (Exception e) {
            System.out.print("����ʱ����");
        }
        return data;
    }


}
