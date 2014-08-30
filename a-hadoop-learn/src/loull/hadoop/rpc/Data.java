package loull.hadoop.rpc;

import java.io.Serializable;

/**
 * 封装客户端的请求信息
 * 服务器计算之后，返回同一个对象，将运行结果封装在此对象中。
 * 简单来说，Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。
 * 在进行反序列化时，JVM会把传来的字节流中的 serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，
 * 如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。
 * 当实现java.io.Serializable接口的实体（类）没有显式地定义一个名为serialVersionUID，类型为long的变量时，
 * Java序列化机制会根据编译的class自动生成一个serialVersionUID作序列化版本比较用，
 * 这种情况下，只有同一次编译生成的class才会生成相同的serialVersionUID 。
 * 如果我们不希望通过编译来强制划分软件版本，即实现序列化接口的实体能够兼容先前版本，未作更改的类，
 * 就需要显式地定义一个名为serialVersionUID，类型为long的变量，不修改这个变量值的序列化实体都可以相互进行串行化和反串行化。
 */
public class Data implements Serializable {
    //请求的方法名称
    private  String method;
    //请求的参数，本例中假设方法中只有一个参数
    private String param;
    //方法运行后返回结果，本列中方法返回字符串
    private String result;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}