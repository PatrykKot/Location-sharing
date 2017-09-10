package com.kotlarz.system.exception;

public class SystemInitializingException extends RuntimeException {
    public SystemInitializingException(Throwable ex) {
        super(ex);
    }
}
