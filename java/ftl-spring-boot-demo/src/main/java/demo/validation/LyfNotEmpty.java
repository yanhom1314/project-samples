package demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {NotEmptyValidator.class})
public @interface LyfNotEmpty {
    String message() default "{javax.validation.lyf.not.empty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
