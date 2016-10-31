package org.batsy.core.service;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.servlet.BatsyServlet;

import javax.servlet.ServletException;
import java.io.File;
import java.util.logging.Logger;

/**
 * Created by ufuk on 24.10.2016.
 */
public class WebServerService implements IBatsyService{
    private static final String WEB_APP_DOC_BASE = "src/main/webapp/";
    private static final Logger logger = Logger.getLogger(WebServerService.class.getSimpleName());

    @Override
    public void start() throws BatsyException {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(Integer.parseInt(PropertyService.getProperty("batsy.server.port", "8080")));
            tomcat.setBaseDir(".");
            //tomcat.addWebapp("/", new File(WEB_APP_DOC_BASE).getAbsolutePath());
            Context context = tomcat.addContext(PropertyService.getProperty("batsy.server.contextPath", "/"), new File(".").getAbsolutePath());

            Tomcat.addServlet(context, "batsyServlet", new BatsyServlet());
            context.addServletMapping("/*", "batsyServlet");

            tomcat.getHost().setDeployOnStartup(true);
            tomcat.getHost().setAutoDeploy(true);
            tomcat.start();
            logger.info("Tomcat Started at " + tomcat.getServer().getAddress() + ":" + tomcat.getConnector().getPort());
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            throw new BatsyException(e.getMessage(), e);
        }
    }


}
