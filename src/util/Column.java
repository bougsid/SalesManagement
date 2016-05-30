package util;

import java.lang.reflect.Method;
import javafx.scene.control.TableColumn;

/**
 *
 * @author BOUGSID Ayoub
 */
public class Column {
    private TableColumn column;
    private Method method;
    private Method ownerMethod;

    public Column(TableColumn column, Method method, Method ownerMethod) {
        this.column = column;
        this.method = method;
        this.ownerMethod = ownerMethod;
    }

    
    public TableColumn getColumn() {
        return column;
    }

    public Method getMethod() {
        return method;
    }

    public Method getOwnerMethod() {
        return ownerMethod;
    }

    public void setColumn(TableColumn column) {
        this.column = column;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setOwnerMethod(Method ownerMethod) {
        this.ownerMethod = ownerMethod;
    }
    
    
}
