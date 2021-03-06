package com.kotlarz.web.annotation;

import com.kotlarz.web.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestMapping {
	RequestMethod method() default RequestMethod.GET;

	String value();
}
