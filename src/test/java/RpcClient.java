import client.RpcProxyClient;
import impl.IHello;


class RpcClient {
    // 调用服务
    public static void main(String[] args) {
        RpcProxyClient rpcClient = new RpcProxyClient();

        IHello hello = rpcClient.proxyClient(IHello.class, "localhost", 8080);
        String result = hello.sayHello("test data");
        System.out.println(result);
    }
}
