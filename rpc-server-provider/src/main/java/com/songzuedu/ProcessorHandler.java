package com.songzuedu;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
public class ProcessorHandler implements Runnable {

    private Socket socket;

    private Map<String, Object> handlerMap;

    public ProcessorHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    public void run() {
        System.out.println("客户端连接");
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            InputStream in = socket.getInputStream();
            objectInputStream = new ObjectInputStream(in);
            //输入流中应该有:请求哪个类，方法名称、参数
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(rpcRequest); //反射调用本地服务

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //1.反射调用
        Object[] args = request.getParameters();//拿到客户端的请求参数
        String serviceName = request.getClassName();//类名
        String version = request.getVersion();//版本
        //2.增加版本号的判断
        if (!StringUtils.isEmpty(version)) {
            serviceName += "-" + version;
        }
        //3.拿到类实例对象
        Object service = handlerMap.get(serviceName);
        if (service == null) {
            //throw new RuntimeException("service not found:" + serviceName);
            System.out.println("service not found:" + serviceName);
            return "service not found:" + serviceName;
        }
        //4.拿到类方法
        Method method = null;
        if (args != null) {
            Class<?>[] types = new Class[args.length];//获得每个参数的类型
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
            Class<?> clazz = Class.forName(request.getClassName());//根据请求的类进行加载
            method = clazz.getMethod(request.getMethodName(), types);//找到类中的方法
        } else {
            Class clazz = Class.forName(request.getClassName());
            method = clazz.getMethod(request.getMethodName());
        }
        //5.调用方法
        Object result = method.invoke(service, args);//反射调用

        return result;
    }

}
