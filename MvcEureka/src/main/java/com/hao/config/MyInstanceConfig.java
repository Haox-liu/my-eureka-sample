package com.hao.config;

import com.google.inject.Singleton;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.MyDataCenterInstanceConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hao on 17-12-13.
 */

/*
Service Consumer获取的Service Provider的地址信息中只有域名，而Service Consumer本地并没有配置这个域名的解析，所以会报异常了。
我们并不想增加过多的域名解析环节，所以最好的方案是让Service Provider使用IP地址而不是域名来注册。
研究Eureka源码后，我们发现Eureka本身获取并告知Eureka Server的域名是服务器本地配置的hostname，
而Spring Cloud注册的服务对这点做了改进，提供了eureka.instance.preferIpAddress这个参数来允许IP地址注册。
但Eureka本身并没有提供这个功能，所以我们要进行一些简单的改进：重写getHostName（）方法
 */
@Singleton
public class MyInstanceConfig extends MyDataCenterInstanceConfig implements EurekaInstanceConfig {
    @Override
    public String getHostName(boolean refresh) {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return super.getHostName(refresh);
        }
    }
//    @Override
//    public String getAppname() {
//        return "HOST";
//    }

    @Override
    public Map<String, String> getMetadataMap() {
        Map<String, String> metadata = new HashMap<String, String>();

        metadata.put("helloApi", "/hello");

        return metadata;
    }
}
