package org.geektimes.projects.user.validator.bean.validation;

import org.geektimes.projects.user.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserValidAnnotationValidator implements ConstraintValidator<UserValid, User> {

    private int idRange;


    public void initialize(UserValid annotation) {
        this.idRange = annotation.idRange();
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        // 获取模板信息
        //context.getDefaultConstraintMessageTemplate();
        //return (Objects.nonNull(value.getId()) && value.getId() > idRange);
        //老师在这里好像留了个坑,注册用户怎么可以自己制定ID呢？ 而且指定了ID, JPA Persistent 会报异常
        // org.hibernate.PersistentObjectException: detached entity passed to persist: org.geektimes.projects.user.domain.User
        return Objects.isNull(value.getId());
    }
}
