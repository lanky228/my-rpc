package service;

import impl.IHello;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket 处理请求
 * 实际上 可以预先定义 bean 名称，自动匹配实现类
 */
public class RpcProxyServer {
    private final IHello hello = new HelloService();

    public void publisherServer(int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = ss.accept()) {
                    try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                        String method = ois.readUTF();
                        Object[] objects = (Object[]) ois.readObject();
                        Class<?>[] types = new Class[objects.length];
                        for (int i = 0; i < types.length; i++) {
                            types[i] = objects[i].getClass();
                        }
                        Method m = HelloService.class.getMethod(method, types);
                        Object obj = m.invoke(hello, objects);

                        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                            oos.writeObject(obj);
                            oos.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
