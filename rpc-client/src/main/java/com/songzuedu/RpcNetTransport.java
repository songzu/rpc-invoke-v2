package com.songzuedu;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
public class RpcNetTransport {

    private String host;

    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest rpcRequest) {
        Socket socket = null;
        Object result = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            //1.建立连接
            socket = new Socket(host, port);

            //2.发送请求
            outputStream = new ObjectOutputStream(socket.getOutputStream());//网络socket
            outputStream.writeObject(rpcRequest);//序列化
            outputStream.flush();

            //3.接收响应
            InputStream in = socket.getInputStream();
            inputStream = new ObjectInputStream(in);
            result = inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

}
