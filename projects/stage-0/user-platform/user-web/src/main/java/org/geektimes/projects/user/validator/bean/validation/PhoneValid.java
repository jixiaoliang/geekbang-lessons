package org.geektimes.projects.user.validator.bean.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidAnnotationValidator.class)
public @interface PhoneValid {

    String message() default "{javax.validation.constraints.PhoneValid.message}";

    String phone() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
