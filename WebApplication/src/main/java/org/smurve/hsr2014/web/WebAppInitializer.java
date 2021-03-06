package org.smurve.hsr2014.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class WebAppInitializer /*implements WebApplicationInitializer */ {

    private static Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

    public void onStartup(ServletContext servletContext) {
        WebApplicationContext rootContext = createRootContext(servletContext);

        configureSpringMvc(servletContext, rootContext);
    }

    private WebApplicationContext createRootContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(CoreConfig.class);
        rootContext.refresh();
        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.setInitParameter("defaultHtmlEscape", "true");
      servletContext.setInitParameter("contextConfigLocation", "org.smurve.hsr2014.web.MVCConfig");

        return rootContext;
    }

    private void configureSpringMvc(ServletContext servletContext, WebApplicationContext rootContext) {
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(MVCConfig.class);

      mvcContext.refresh();
      for (String name : mvcContext.getBeanDefinitionNames()) {
        System.out.println(name);
      }
      mvcContext.getBean("springSecurityFilterChain");

        mvcContext.setParent(rootContext);
      ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet(
                "webservice", new DispatcherServlet(mvcContext));
      dispatcherServlet.setLoadOnStartup(1);
      Set<String> mappingConflicts = dispatcherServlet.addMapping("/rest/*");

      FilterRegistration.Dynamic filter = servletContext.addFilter("corsFilter", CorsFilter.class);
      filter.addMappingForUrlPatterns(null, false, "/*");

      FilterRegistration.Dynamic secfilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
      filter.addMappingForUrlPatterns(null, false, "/*");




        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                LOG.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException(
                    "'webservice' cannot be mapped to '/'");
        }
    }
}

