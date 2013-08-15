package com.googlecode.jsonplugin.rpc;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RPCError {
    private static final Log log = LogFactory.getLog(RPCError.class);

    private int code;
    private String name;
    private String message;
    private String stack;

    public RPCError() {
    }

    public RPCError(String message, int code) {
        this.code = code;
        this.message = message;

        log.error(message);
    }

    public RPCError(String message, RPCErrorCode code) {
        this(message, code.code());
    }

    public RPCError(Throwable t, int code, boolean debug) {
        while (t.getCause() != null) {
            t = t.getCause();
        }

        this.code = code;
        this.message = t.getMessage();
        this.name = t.getClass().getName();

        if (debug) {
            StringWriter s = new StringWriter();
            PrintWriter w = new PrintWriter(s);
            t.printStackTrace(w);
            w.flush();
            this.stack = s.toString();
        }

        log.error(t.getMessage(), t);
    }

    public RPCError(Throwable t, RPCErrorCode code, boolean debug) {
        this(t, code.code(), debug);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}
