package util;

import java.lang.reflect.Method;

/**
 *
 * @author BOUGSID Ayoub
 */
public class M {
    private Method method;
    private Method ownerMethod;

    public M(Method method, Method ownerMethod) {
        this.method = method;
        this.ownerMethod = ownerMethod;
    }

    
    public Method getMethod() {
        return method;
    }

    public Method getOwnerMethod() {
        return ownerMethod;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setOwnerMethod(Method ownerMethod) {
        this.ownerMethod = ownerMethod;
    }
    
    
}
