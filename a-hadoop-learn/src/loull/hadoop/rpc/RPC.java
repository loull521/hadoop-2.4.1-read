package loull.hadoop.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RPC（Remote Procedure Call Protocol）——远程过程调用协议，它是一种通过网络从远程计算机程序上请求服务，
 * 而不需要了解底层网络技术的协议。RPC协议假定某些传输协议的存在，如TCP或UDP，为通信程序之间携带信息数据。
 * 在OSI网络通信模型中，RPC跨越了传输层和应用层。RPC使得开发包括网络分布式多程序在内的应用程序更加容易。
 *
 * 工作原理
 * 运行时,一次客户机对服务器的RPC调用,其内部操作大致有如下十步：
 * 1.调用客户端句柄；执行传送参数
 * 2.调用本地系统内核发送网络消息
 * 3.消息传送到远程主机
 * 4.服务器句柄得到消息并取得参数
 * 5.执行远程过程
 * 6.执行的过程将结果返回服务器句柄
 * 7.服务器句柄返回结果，调用远程系统内核
 * 8.消息传回本地主机
 * 9.客户句柄由内核接收消息
 * 10.客户接收句柄返回的数据
 *
 * RPC类，提供两个方法，分别是getServer()和getProxy().用来创建服务端和客户端
 */
public class RPC {
    /**
     * 使用传统的ServerSocket创建一个服务
     *
     * @param nameNode 通信协议接口，此参数需要协议接口的实现类
     * @param port     服务端的监听的端口号
     * @param backlog  ServerSocket构造方法的backlog参数用来显式设置连结请求队列的长度，
     *                 它将覆盖操作系统限定的队列的最大长度。值得注意的是，在以下几种情况，
     *                 仍然会采用操作系统限定的队列的最大长度：
     *                 l backlog参数的值大于操作系统限定的队列的最大长度。
     *                 l backlog参数的值小于或等于0。
     *                 l 在ServerSocket构造方法中没有设置backlog参数
     * @param host     服务端的ip地址
     * @throws Exception
     */
    public static void getServer(NameNode nameNode, int port, int backlog, String host) throws Exception {
        ServerSocket objSer = new ServerSocket(port, backlog, InetAddress.getByName(host));
        while (true) {
            Socket socket = objSer.accept();
            //开辟新的线程，用来处理客户端的请求
            new ProcessThread(nameNode, socket).start();
        }

    }

    /**
     * 使用Java动态代理技术，创建通信协议的虚拟实现类
     *
     * @param clazz 通信协议接口类，当前代码中该类应该是基类
     * @param host  服务端的ip地址
     * @param port  服务端监听的端口号
     * @return 虚拟的接口实现类
     * @throws Exception
     */
    public static NameNode getProxy(Class clazz, String host, int port) throws Exception {
        Class[] interfaces = new Class[1];
        interfaces[0] = clazz;
        /*
        * 代理模式是常用的java设计模式，
        * 他的特征是代理类与委托类有同样的接口，代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后处理消息等。
        * 那么这里的委托类在客户端实际上是不存在的，通过序列化对象之后将程序要执行的信息传递给服务端
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