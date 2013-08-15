package com.googlecode.jsonplugin.rpc;

/**
 * Class that will be serialized as a response to an RPC call
 */
public class RPCResponse {
    private String id;
    private Object result;
    private RPCError error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public RPCError getError() {
        return error;
    }

    public void setError(RPCError error) {
        this.error = error;
    }
}
