package com.hao.eureka;


import com.hao.config.MyInstanceConfig;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by hao on 17-12-13.
 */
@Component
public class EurekaRegister {
    private final static Logger LOG = Logger.getLogger(EurekaRegister.class);
//    private static final DynamicPropertyFactory configInstance = DynamicPropertyFactory
//            .getInstance();

    private ApplicationInfoManager applicationInfoManager;
    private EurekaClient eurekaClient;

    //@PostConstruct注解，bean创建时执行
    @PostConstruct
    public void init()
    {
        //初始化应用信息管理器，设置其状态为STAERTING
        applicationInfoManager = initializeApplicationInfoManager(new MyInstanceConfig());
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);
        LOG.info("Registering service to eureka with STARTING status");

        //读取配置文件，初始化eurekaClient，并设置应用信息管理器的状态为UP
        //configInstance = DynamicPropertyFactory.getInstance();
        eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        LOG.info("Initialization finished, now changing eureka client status to UP");
    }

    //@PreDestroy注解，bean销毁时执行
    @PreDestroy
    public void stop()
    {
        if (eurekaClient != null)
        {
            LOG.info("Shutting down eureka service.");
            eurekaClient.shutdown();
        }
    }

    //初始化应用信息管理器
    private synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig)
    {
        if (applicationInfoManager == null)
        {
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        }
        return applicationInfoManager;
    }

    //初始化EurekaClient
    private synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig)
    {
        if (eurekaClient == null)
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        return eurekaClient;
    }
}
