package loull.hadoop.rpc;

/**
 * ���������
 */
public class TestRpcServer {

    public static void main(String[] args) throws Exception{
        RPC.getServer(new NameNodeImpl(), 8888,200, "127.0.0.1");
    }

}
