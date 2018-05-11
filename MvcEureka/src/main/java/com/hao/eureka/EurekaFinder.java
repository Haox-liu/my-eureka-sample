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
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

import java.util.List;

/**
 * Created by hao on 17-12-13.
 */
public class EurekaFinder {
    private static ApplicationInfoManager applicationInfoManager;
    private static EurekaClient eurekaClient;

    private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        if (applicationInfoManager == null) {
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        }

        return applicationInfoManager;
    }

    private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        if (eurekaClient == null) {
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        }

        return eurekaClient;
    }


    public static void main(String[] args) {

        // create the client
        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyInstanceConfig());
        EurekaClient client = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

        List<InstanceInfo> instanceInfos = client.getInstancesByVipAddress("galaxy", false);

        System.out.println(instanceInfos.size());

//        Applications applications = client.getApplications("http://10.158.224.15:32735/eureka/");
//        List<Application> list = applications.getRegisteredApplications();
//        System.out.println(list.size());


//        for (Application application : list) {
//            List<InstanceInfo> instanceInfos = application.getInstances();
//            System.out.println(instanceInfos.size());
//            for (InstanceInfo instanceInfo : instanceInfos) {
//                System.out.println(instanceInfo.getAppName() + " ----- " + instanceInfo.getIPAddr());
//            }
//        }

        // use the client
//        String vipAddress = "mvcAddress";
//        InstanceInfo nextServerInfo = client.getNextServerFromEureka(vipAddress, false);

//        URI url = URI.create(String.format("http://%s:%s" + helloApi, nextServerInfo.getIPAddr(), nextServerInfo.getPort()));
//        // 根据地址获取请求,这里发送get请求
//        HttpGet request = new HttpGet(url);
//        // 获取当前客户端对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        // 通过请求对象获取响应对象
//        try {
//            HttpResponse response = httpClient.execute(request);
//            System.out.println(EntityUtils.toString(response.getEntity()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }
}
