package org.smurve.hsr2014.boot;


import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.startup.Tomcat;
import org.smurve.hsr2014.web.CorsFilter;
import org.smurve.hsr2014.web.MVCConfig;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class TomcatRunner {

  public static void main(String[] args) throws LifecycleException, InterruptedException {

    AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
    mvcContext.register(MVCConfig.class);

    Tomcat tomcat = new Tomcat();

    tomcat.setPort(8080);

    File base = new File("");

    Context rootContext = tomcat.addContext("", base.getAbsolutePath());

    rootContext.addParameter("contextConfigLocation", "org.smurve.hsr2014.web.MVCConfig");

    Tomcat.addServlet(rootContext, "Dispatcher", new DispatcherServlet(mvcContext));

    rootContext.addServletMapping("/rest/*", "Dispatcher");

    FilterDef cors = new FilterDef();
    cors.setFilter(new CorsFilter());
    cors.setFilterName("CorsFilter");

    FilterMap corsMapping = new FilterMap();
    corsMapping.setFilterName("CorsFilter");
    corsMapping.addURLPattern("/*");

    rootContext.addFilterDef(cors);
    rootContext.addFilterMap(corsMapping);


    FilterDef securityFilter = new FilterDef();
    securityFilter.setFilter(new DelegatingFilterProxy());
    securityFilter.setFilterName("securityFilter");

    FilterMap securityFilterMapping = new FilterMap();
    securityFilterMapping.setFilterName("securityFilter");
    securityFilterMapping.addURLPattern("/*");

    rootContext.addFilterDef(securityFilter);
    rootContext.addFilterMap(securityFilterMapping);

    tomcat.start();

    tomcat.getServer().await();

  }
}
