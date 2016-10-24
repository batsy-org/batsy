package org.batsy.core.annotation;

import org.batsy.core.http.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ufuk on 21.10.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only
public @interface RequestMapping {
    String path();
    RequestMethod method() default RequestMethod.GET;
}
