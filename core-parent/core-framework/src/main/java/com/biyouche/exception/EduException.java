package com.biyouche.exception;

public class EduException extends Exception {

    private static final long serialVersionUID = 1L;

    public EduException() {
        super(String.valueOf(100002));
    }

    public EduException(int code) {
        super(String.valueOf(code));
    }

    public EduException(String msg) {
        super(msg);
    }
}
