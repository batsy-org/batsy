package org.batsy.core.exception;

/**
 * Created by ufuk on 21.10.2016.
 */
public class BatsyException extends Throwable {

    public BatsyException(String message, Throwable t) {
        super(message, t);
    }

}
