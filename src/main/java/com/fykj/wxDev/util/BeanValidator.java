package com.fykj.wxDev.util;

import static com.google.common.collect.Iterables.getFirst;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

/**
 * 实体对象的验证
 * created by wujian on 2018/11/23 16:19
 */
public class BeanValidator {
       /**
     * 验证参数工具类
     * @param params
     * @param <T>
     */
    public static <T>void validate(T params){
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(params);
        //如果有验证信息，则将第一个取出来包装成异常返回
        ConstraintViolation<T> constraintViolation = getFirst(constraintViolations, null);
        if (constraintViolation != null) {
            throw new ValidationException(constraintViolation.getMessage());
        }
    }
}
