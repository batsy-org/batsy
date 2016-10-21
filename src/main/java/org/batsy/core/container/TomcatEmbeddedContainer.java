package org.batsy.core.container;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.property.ApplicationProperties;
import org.batsy.core.service.IBatsyService;

import java.io.File;

/**
 * Created by ufuk on 21.10.2016.
 */
public class TomcatEmbeddedContainer implements IBatsyService {

    public void start() throws BatsyException {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(Integer.parseInt(ApplicationProperties.getProperty("batsy.server.port", "8080")));
            File base = new File(System.getProperty("java.io.tmpdir"));
            Context context = tomcat.addContext(ApplicationProperties.getProperty("batsy.server.contextPath", "/"), base.getAbsolutePath());
            //Tomcat.addServlet(context, "dateServlet", new DatePrintServlet());
            //context.addServletMapping("/date", "dateServlet");
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException ex) {
            throw new BatsyException(ex.getMessage(), ex);
        }

    }
}
