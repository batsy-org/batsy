package org.batsy.core.container;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.service.PropertyService;
import org.batsy.core.service.IBatsyService;
import org.batsy.core.servlet.BatsyServlet;

import javax.servlet.ServletException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ufuk on 21.10.2016.
 */
@Deprecated
public class TomcatEmbeddedContainer implements IBatsyService {

    private static List<String> MAPPING_LIST = new ArrayList<>();
    private static final String WEB_APP_DOC_BASE = "src/main/webapp/";

    public void start() throws BatsyException {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(Integer.parseInt(PropertyService.getProperty("batsy.server.port", "8080")));
            File base = new File(System.getProperty("java.io.tmpdir"));
            Context context = tomcat.addContext(PropertyService.getProperty("batsy.server.contextPath", "/"), base.getAbsolutePath());
            /*Tomcat.addServlet(context, "batsyServlet", new BatsyServlet() {
            });*/
            //addServletMapping(tomcat, context);
            /*StandardServer server = (StandardServer)tomcat.getServer();
            AprLifecycleListener listener = new AprLifecycleListener();
            server.addLifecycleListener(listener);

            context.addFilterDef(createFilterDef("batsy", BatsyFilter.class.getName()));
            context.addFilterMap(createFilterMap("batsy", "/*"));*/

            Tomcat.addServlet(context, "batsyServlet", new BatsyServlet());
            context.addServletMapping("/*", "batsyServlet");

            //tomcat.addWebapp(context.getPath()+"/test", new File(WEB_APP_DOC_BASE).getAbsolutePath());

            tomcat.getHost().setDeployOnStartup(true);
            tomcat.getHost().setAutoDeploy(true);
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException ex) {
            throw new BatsyException(ex.getMessage(), ex);
        } /*catch (ServletException ex) {
            throw new BatsyException(ex.getMessage(), ex);
        }*/

    }

    private void addServletMapping(Tomcat tomcat, Context context) throws ServletException {
        for (String mapping : MAPPING_LIST) {
            //context.addServletMapping(mapping, "");
            tomcat.addWebapp(context.getPath() + mapping, new File(WEB_APP_DOC_BASE).getAbsolutePath());
        }
    }

    public static void addMapping(String mapping) {
        MAPPING_LIST.add(mapping);
    }

    private FilterDef createFilterDef(String filterName, String filterClass) {
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName(filterName);
        filterDef.setFilterClass(filterClass);
        return filterDef;
    }

    private FilterMap createFilterMap(String filterName, String urlPattern) {
        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName(filterName);
        filterMap.addURLPattern(urlPattern);
        return filterMap;
    }
}
