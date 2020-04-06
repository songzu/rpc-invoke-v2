package com.songzuedu;

import com.songzuedu.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
public class ServerApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.start();
    }

}
