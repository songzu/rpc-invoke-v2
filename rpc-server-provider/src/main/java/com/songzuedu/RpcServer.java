package com.songzuedu;

import com.songzuedu.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private int port;

    private Map<String, Object> handlerMap = new HashMap();

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public RpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端监听端口：" + port);
            while (true) {//不断接受请求
                Socket socket = serverSocket.accept();//BIO
                //每一个socket 交给一个processorHandler来处理
                executorService.execute(new ProcessorHandler(socket, handlerMap));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (!serviceBeanMap.isEmpty()) {
            for (Object serviceBean : serviceBeanMap.values()) {
                //拿到注解
                RpcService annotation = serviceBean.getClass().getAnnotation(RpcService.class);
                //拿到接口类定义
                String serviceName = annotation.value().getName();
                //拿到版本号
                String version = annotation.version();
                if (!StringUtils.isEmpty(version)) {
                    serviceName += "-" + version;
                }
                //key:类名, value:实例对象
                handlerMap.put(serviceName, serviceBean);
            }
        }


    }
}
