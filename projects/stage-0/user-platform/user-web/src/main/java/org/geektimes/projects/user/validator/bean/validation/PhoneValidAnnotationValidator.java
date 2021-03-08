package org.geektimes.projects.user.validator.bean.validation;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidAnnotationValidator implements ConstraintValidator<PhoneValid, String> {

    private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");

    private String phone;

    public void initialize(PhoneValid annotation) {
        this.phone = annotation.phone();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 获取模板信息
        //context.getDefaultConstraintMessageTemplate();
        return (StringUtils.isNotEmpty(value) && CHINA_PATTERN.matcher(value).matches());
    }
}
