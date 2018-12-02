package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class RpcProxyClient {

    /**
     * 获取代理类
     * 向 socket 发送数据，并读取返回值
     * @param clazz 接口类
     * @param host ip 地址
     * @param port 端口
     * @param <T> 接口类
     * @return 代理对象
     */
    public <T> T proxyClient(Class<T> clazz, String host, int port) {
        return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, (proxy, method, args) -> {
            try (Socket socket = new Socket(host, port)) {
                try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                    oos.writeUTF(method.getName());
                    oos.writeObject(args);
                    oos.flush();

                    try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                        return ois.readObject();
                    }
                }
            }
        }));
    }
}