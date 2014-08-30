package loull.hadoop.rpc;

import java.lang.reflect.Method;

/**
 * 使用反射技术，运行客户端请求的方法
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
            System.out.print("反射时出错");
        }
        return data;
    }


}
