package com.buildupchao.zns.common.exception;

/**
 * @author buildupchao
 *         Date: 2019/2/1 02:27
 * @since JDK 1.8
 */
public class ZnsException extends RuntimeException {

    public ZnsException() {
    }

    public ZnsException(String message) {
        super(message);
    }

    public ZnsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZnsException(Throwable cause) {
        super(cause);
    }
}
