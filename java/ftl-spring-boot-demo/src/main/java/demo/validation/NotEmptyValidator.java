package demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidator implements ConstraintValidator<LyfNotEmpty, Object> {
    @Override
    public void initialize(LyfNotEmpty constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null && value.toString().trim().length() > 0;
    }
}
