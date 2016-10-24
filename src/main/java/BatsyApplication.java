import org.batsy.core.exception.BatsyException;
import org.batsy.core.service.ServiceLoader;

/**
 * Created by ufuk on 21.10.2016.
 */
public class BatsyApplication {
    public static void run(Class clazz) throws BatsyException {
        ServiceLoader.runAll();
    }
}
