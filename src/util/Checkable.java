package util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by AYOUB on 20-05-2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Checkable {
    enum Pattern {
        FLOAT("^[-+]?[0-9]*\\.?[0-9]+$"),
        INTEGER("[0-9]+"),
        STRING("[a-zA-Z]+");
        private String pattern;

        Pattern(String pattern) {
            this.pattern = pattern;
        }
        public String getValue(){
            return pattern;
        }
    }
    Pattern pattern() default Pattern.STRING;
}
