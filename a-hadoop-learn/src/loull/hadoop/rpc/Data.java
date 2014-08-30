package loull.hadoop.rpc;

import java.io.Serializable;

/**
 * ��װ�ͻ��˵�������Ϣ
 * ����������֮�󣬷���ͬһ�����󣬽����н����װ�ڴ˶����С�
 * ����˵��Java�����л�������ͨ��������ʱ�ж����serialVersionUID����֤�汾һ���Եġ�
 * �ڽ��з����л�ʱ��JVM��Ѵ������ֽ����е� serialVersionUID�뱾����Ӧʵ�壨�ࣩ��serialVersionUID���бȽϣ�
 * �����ͬ����Ϊ��һ�µģ����Խ��з����л�������ͻ�������л��汾��һ�µ��쳣��
 * ��ʵ��java.io.Serializable�ӿڵ�ʵ�壨�ࣩû����ʽ�ض���һ����ΪserialVersionUID������Ϊlong�ı���ʱ��
 * Java���л����ƻ���ݱ����class�Զ�����һ��serialVersionUID�����л��汾�Ƚ��ã�
 * ��������£�ֻ��ͬһ�α������ɵ�class�Ż�������ͬ��serialVersionUID ��
 * ������ǲ�ϣ��ͨ��������ǿ�ƻ�������汾����ʵ�����л��ӿڵ�ʵ���ܹ�������ǰ�汾��δ�����ĵ��࣬
 * ����Ҫ��ʽ�ض���һ����ΪserialVersionUID������Ϊlong�ı��������޸��������ֵ�����л�ʵ�嶼�����໥���д��л��ͷ����л���
 */
public class Data implements Serializable {
    //����ķ�������
    private  String method;
    //����Ĳ����������м��跽����ֻ��һ������
    private String param;
    //�������к󷵻ؽ���������з��������ַ���
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