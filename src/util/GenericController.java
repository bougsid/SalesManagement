package util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Date;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.check.Check;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Duration;
import metier.IGenericMetier;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * @author BOUGSID Ayoub
 */
public abstract class GenericController<T> implements IGenericController<T> {
    //private Class beanClass;
    //private T object;
    //private Map<Method, Object> setters;
    //private Map<String, TableColumn> columns;
    public static ApplicationContext context;
    protected ValidationSupport validationSupport;
    protected List<String> errors;
    private Class<T> type;
    protected IGenericMetier metier;
    private TableView<T> objectTable;
    protected ObservableList<T> observableObjects;
    private Map<String, Object> controls;
    private Map<String, Button> buttons;
    protected ResourceBundle resources;

    private ArrayList<Column> columns;
    private Map<String, Method> selfProperties;
    private Map<String, M> otherProperties;
    private Map<String, Method> selfGetters;
    private Map<String, M> otherGetters;
    private Map<String, Method> selfSetters;

    //private Map<String, M> otherSetters;
    public GenericController() {
        if (GenericController.context == null)
            GenericController.context = ApplicationContextProvider.getContext();
        this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.metier = (IGenericMetier) context.getBean(getMetierClassId());
        this.controls = new HashMap<>();
        this.buttons = new HashMap<>();
        this.selfProperties = new HashMap<>();
        this.otherProperties = new HashMap<>();
        this.selfGetters = new HashMap<>();
        this.otherGetters = new HashMap<>();
        this.selfSetters = new HashMap<>();

        this.validationSupport = new ValidationSupport();
        this.errors = new ArrayList<>();
    }

    public void genericInitializer(TableView<T> objectTable, ResourceBundle resources) {

//        this.controls = new HashMap<>();
//        this.buttons = new HashMap<>();
//        this.setters = new HashMap<>();
        //this.columns = new HashMap<>();
//        this.otherSetters = new HashMap<>();
        this.resources = resources;
        //this.type = type;
        this.objectTable = objectTable;
        this.columns = new ArrayList<>();

        initializeClass();
        fillControls();
        setCellFactory();

        List objectsList = metier.findAll();
        if (objectsList == null) {
            this.observableObjects = FXCollections.observableArrayList();
        } else {
            this.observableObjects = FXCollections.observableList(objectsList);
        }

        this.objectTable.setItems(observableObjects);
        objectTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            //addButton.setText(resourceBundle.getString("button.text.validate"));
            if (newSelection != null) {
                setFiledsWithObject(newSelection);
            }
        });
    }

    public void genericInitializer(ResourceBundle resources) {
//        this.setters = new HashMap<>();
        //this.columns = new HashMap<>();
//        this.selfProperties = new HashMap<>();
//        this.otherProperties = new HashMap<>();
//        this.selfGetters = new HashMap<>();
//        this.otherGetters = new HashMap<>();
//        this.selfSetters = new HashMap<>();
//        this.otherSetters = new HashMap<>();
        this.resources = resources;
        //this.type = type;
        initializeClass();
        fillControls();
    }

    public void fillControls() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = getColumnName(field.getName());
            try {
                if (field.getType().equals(TableColumn.class) && field.getName().contains("Column")) {
                    Method method = this.selfProperties.get(name);
                    Method ownerMethod = null;
                    if (method == null) {
                        try {
                            method = this.otherProperties.get(name).getMethod();
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        ownerMethod = this.otherProperties.get(name).getOwnerMethod();
                    }
                    this.columns.add(new Column((TableColumn) field.get(this), method, ownerMethod));
                    continue;
                }
                if (field.getType().equals(Button.class)) {
                    buttons.put(field.getName(), (Button) field.get(this));
                    continue;
                }
                if (field.getType().equals(Label.class)) {
                    this.controls.put(field.getName(), field.get(this));
                    continue;
                }
                if (field.getType().equals(TextField.class) || field.getType().equals(ComboBox.class) || field.getType().equals(DatePicker.class)) {
                    this.controls.put(field.getName(), field.get(this));
                }

            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void initializeClass() {
        Method methods[] = type.getDeclaredMethods();
        for (Method method : methods) {
            Class returnType = method.getReturnType();
            if (isCustomClass(returnType)) {
                Method ownerMethod = method;
                Method RTMethods[] = returnType.getDeclaredMethods();//RTMethods = Return Type class methods
                for (Method rtm : RTMethods) {
                    addMethod(rtm, ownerMethod);
                }
            }
            addMethod(method);
        }
    }

    public void addMethod(Method... methods) {
        Method method = methods[0];
        String methodName = method.getName();
        String name;
        if (methods.length == 1) {
            name = isProperty(method);
            if (name != null) {
                this.selfProperties.put(name, method);
                return;
            }
            name = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
            if (isSetter(method)) {
                this.selfSetters.put(name, method);
            }
            if (isGetter(method)) {
                this.selfGetters.put(name, method);
            }
        } else {
            Method ownerMethod = methods[1];
            System.out.println("----owner"+ownerMethod.getName());
            name = isProperty(method);
            if (name != null) {
                this.otherProperties.put(name, new M(method, ownerMethod));
                return;
            }
            name = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
            /*if (isSetter(method)) {
             this.otherSetters.put(name, new M(method, ownerMethod));
             }*/
            if (isGetter(method)) {
                this.otherGetters.put(name, new M(method, ownerMethod));
            }
        }
    }

    public void setCellFactory() {
        for (Column column : this.columns) {
            column.getColumn().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, Object>, ObservableValue<Object>>() {
                @Override
                public ObservableValue<Object> call(TableColumn.CellDataFeatures<T, Object> param) {
                    Method method = column.getMethod();
                    Method ownerMethod = column.getOwnerMethod();
                    Object value = null;
                    Object object = null;
                    if (ownerMethod == null) {
                        object = param.getValue();
                    } else {
                        try {
                            object = ownerMethod.invoke(param.getValue());
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        value = method.invoke(object);
                        //System.out.println("column = "+column.getMethod().getName()+"onwer = "+column.getOwnerMethod());
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return (ObservableValue<Object>) value;
                }
            });
        }
    }

    /*public ArrayList<Method> getPropertiesMethods(Class clazz) {
     Method[] methods = clazz.getDeclaredMethods();
     ArrayList<Method> properityMethod = new ArrayList<>();
     for (Method method : methods) {
     if (isProperty(method) != null) {
     properityMethod.add(method);
     }
     }
     return properityMethod;
     }

     public ArrayList<Method> getGettersMethods(Class clazz) {
     Method[] methods = clazz.getDeclaredMethods();
     ArrayList<Method> gettersMethod = new ArrayList<>();
     for (Method method : methods) {
     if (isGetter(method)) {
     gettersMethod.add(method);
     }
     }
     return gettersMethod;
     }*/
    public void setFiledsWithObject(T object) {
        try {
            //TODO
            for (Entry<String, Object> entry : this.controls.entrySet()) {
                String name = entry.getKey();
                Method method = this.selfGetters.get(name);
                Method ownerMethod = null;
                Object ob = object;
                Object value = null;
                if (method == null) {
                    M m = this.otherGetters.get(name);
                    if (m == null) {
                        continue;
                    }
                    method = m.getMethod();
                    ownerMethod = this.otherGetters.get(name).getOwnerMethod();
                    ob = ownerMethod.invoke(object);
                }
                if (ob != null)
                    value = method.invoke(ob);
                if (entry.getValue() instanceof TextField) {
                    ((TextField) entry.getValue()).setText(String.valueOf(value));
                }
                if (entry.getValue() instanceof Label) {
                    ((Label) entry.getValue()).setText(String.valueOf(value));
                }
                if (entry.getValue() instanceof ComboBox) {
                    ((ComboBox) entry.getValue()).setValue(value);
                }
                if (entry.getValue() instanceof DatePicker) {
                    if (value != null)
                        ((DatePicker) entry.getValue()).setValue(((Date) value).toLocalDate());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setObjectWithFields(T entity) {
        try {
            //TODO
            for (Entry<String, Method> entry : selfSetters.entrySet()) {
                Method method = entry.getValue();
                Object control = this.controls.get(entry.getKey());
                if (control == null) {
                    continue;
                }
                if (control instanceof TextField) {
                    method.invoke(entity, parseObjectFromString(((TextField) control).getText(), method.getParameterTypes()[0]));
                }
                if (control instanceof ComboBox) {
                    if (((ComboBox) control).getValue() != null)
                        method.invoke(entity, ((ComboBox) control).getValue());
                }
                if (control instanceof DatePicker) {
                    if (((DatePicker) control).getValue() != null)
                        method.invoke(entity, Date.valueOf(((DatePicker) control).getValue()));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static <T> T parseObjectFromString(String s, Class<T> clazz) throws Exception {
        if (s.equals("")) {
            s = "0";
        }
        return clazz.getConstructor(new Class[]{String.class}).newInstance(s);
    }

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(method.getReturnType())) {
            return false;
        }
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

    /*public static boolean isProperty(Method method) {
     if (!method.getName().endsWith("Property")) {
     return false;
     }
     if (method.getParameterTypes().length != 0) {
     return false;
     }
     return true;
     }*/
    public static String isProperty(Method method) {
        if (!method.getName().endsWith("Property")) {
            return null;
        }
        if (method.getParameterTypes().length != 0) {
            return null;
        }
        return method.getName().substring(0, method.getName().indexOf("Property"));
    }

    public String getMetierClassId() {
        String className = type.getSimpleName();
        return Character.toLowerCase(className.charAt(0)) + className.substring(1) + "Metier";
    }

    public String getColumnName(String column) {
        if (!column.contains("Column")) {
            return column;
        } else {
            return column.substring(0, column.indexOf("Column"));
        }
    }

    public T getSelectedObject() {
        int selectedIndex = objectTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return observableObjects.get(selectedIndex);
        } else {
            return null;
        }
    }

    public boolean isCustomClass(Class clazz) {
        return clazz.getName().startsWith("app");
    }

    @Override
    public Object add() {
        if (!validate()) return null;
        T object = null;
        try {
            object = this.type.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setObjectWithFields(object);
        try {
            metier.makePersistent(object);
            observableObjects.add(object);
            objectTable.getSelectionModel().select(object);
            //System.out.println(objectTable.getItems().size());
            //refresh();
            //To Change badltha bach takhdam f ajouter client bo7dha
            //objectTable.getSelectionModel().select(observableObjects.size() - 1);
        } catch (Exception ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }

    @Override
    public void delete() {
        T entity = getSelectedObject();
        if (entity == null) return;
        Optional<ButtonType> result = Dialogs.createConfirmationDialog(resources.getString("validation.delete"), resources.getString("validation.deleteMessage"), "");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("entity1 = "+entity);
            this.observableObjects.remove(entity);
            this.metier.makeTransient(entity);
        }
    }

    @Override
    public void update() {
        if (validate()) {
            T entity = getSelectedObject();
            setObjectWithFields(entity);
            metier.makePersistent(entity);
        }
    }

    @Override
    public void reset() {
        try {
            //TODO
            for (Entry<String, Object> entry : controls.entrySet()) {
                if (entry.getValue() instanceof TextField) {
                    ((TextField) entry.getValue()).setText("");
                }
//                if (entry.getValue() instanceof Label) {
//                    ((Label) entry.getValue()).setText("");
//                }
                if (entry.getValue() instanceof ComboBox) {
                    ((ComboBox) entry.getValue()).setValue(null);
                }
                if (entry.getValue() instanceof DatePicker) {
                    ((DatePicker) entry.getValue()).setValue(null);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getClassName(Class clazz) {
        return clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
    }

    public boolean isEmpty() {
        return observableObjects.size() == 0;
    }

    public boolean validate() {
        Field[] fields = this.getClass().getDeclaredFields();
//        List<Field> checkableFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Checkable.class)) {
                Annotation annotation = field.getAnnotation(Checkable.class);
                Checkable checkable = (Checkable) annotation;
                field.setAccessible(true);
                try {
                    if (field.getType().equals(TextField.class)) {
                        TextField textField = ((TextField) field.get(this));
                        if (!textField.getText().matches(checkable.pattern().getValue()))
                            errors.add(resources.getString("validation." + field.getName()));
                    }
                    if (field.getType().equals(ComboBox.class)) {
                        ComboBox comboBox = ((ComboBox) field.get(this));
                        if (comboBox.getValue() == null)
                            errors.add(resources.getString("validation." + field.getName()));
                    }
                } catch (IllegalAccessException ex) {
                }
            }
        }
        boolean valid = errors.isEmpty();
        if (!valid)
            showErrors();
        errors.clear();
        return valid;
    }

    public void showErrors() {
        StringBuffer errorsText = new StringBuffer();
        for (String error : errors) {
            errorsText.append(error).append(System.getProperty("line.separator"));
        }
        Notifications notificationBuilder = Notifications.create()
                //.title(showTitleChkBox.isSelected() ? "Title Text" : "")
                .text(errorsText.toString())
                //.graphic(graphic)
                .hideAfter(Duration.seconds(2))
                .position(Pos.BASELINE_RIGHT);
        notificationBuilder.showError();
    }

    public void refresh() {
//        objectTable.getProperties().put(TableViewSkinBase.RECREATE, Boolean.TRUE);
//        objectTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        objectTable.layout();
        objectTable.setItems(null);
        objectTable.setItems(observableObjects);
    }
}
