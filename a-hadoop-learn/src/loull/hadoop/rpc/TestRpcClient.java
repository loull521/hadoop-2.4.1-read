package loull.hadoop.rpc;

/**
 * �����ͻ���
 */
public class TestRpcClient {

    public static void main(String[] args) throws Exception {

        for (int i = 5; i > 0; i--) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NameNode nameNode = RPC.getProxy(NameNode.class, "127.0.0.1", 8888);
                        nameNode.say("������������!");
                    } catch (Exception e) {

                    }
                }
            }).start();

        }

    }

}
