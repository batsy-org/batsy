import org.batsy.core.container.TomcatEmbeddedContainer;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.property.ApplicationProperties;
import org.batsy.core.service.ServiceLoader;

/**
 * Created by ufuk on 21.10.2016.
 */
public class BatsyApplication {
    public static void run() throws BatsyException {
        runAllServices();
    }

    private static void runAllServices() throws BatsyException {
        ServiceLoader.addServiceRunner(new ApplicationProperties());
        ServiceLoader.addServiceRunner(new TomcatEmbeddedContainer());
        ServiceLoader.runAll();
    }
}
