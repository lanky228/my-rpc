package service;

import impl.IHello;

/**
 * 服务端实现类
 */
public class HelloService implements IHello {
    public String sayHello(String info) {
        return "hello : " + info;
    }
}
