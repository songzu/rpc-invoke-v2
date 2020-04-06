package com.songzuedu;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
public class RpcRequest implements Serializable {

    private String className;

    private String methodName;

    private Object[] parameters;

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RpcRequest() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
