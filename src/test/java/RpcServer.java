import service.RpcProxyServer;

class RpcServer {
    //发布服务
    public static void main(String[] args) {
        RpcProxyServer server = new RpcProxyServer();
        server.publisherServer(8080);
    }
}
