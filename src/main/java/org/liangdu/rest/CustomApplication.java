package org.liangdu.rest;


import io.github.biezhi.wechat.handle.CollectMyGroupToBuffMessageHandler;
import io.github.biezhi.wechat.model.Environment;
import io.github.biezhi.wechat.ui.StartUI;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tandewei on 2016/6/25.
 */
public class CustomApplication extends Application {

    public CustomApplication() {
        // 执行微信服务
        new Thread(new Runnable() {
            @Override
            public void run() {
                Environment environment = Environment.of("classpath:config.properties");
                StartUI startUI     = new StartUI(environment);
                startUI.setMsgHandle(new CollectMyGroupToBuffMessageHandler(environment));
                // startUI.setMsgHandle(new TulingRobot(environment));
                startUI.start();
                try {
                    Thread.sleep(2000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Add Service APIs
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = //super.getClasses();
                new HashSet<Class<?>>();

        //register REST modules
        //resources.add(MultiPartFeature.class);
        //Manually adding MOXyJSONFeature
        //resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);

        //Configure Moxy behavior
        //resources.add(JsonMoxyConfigurationContextResolver.class);




        return resources;
    }
}