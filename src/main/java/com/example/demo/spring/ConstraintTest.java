package com.example.demo.spring;

import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

public class ConstraintTest {

    @Documented
    @Inherited
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = IsValidName.EmptyChecker.class)
    public @interface IsValidName {

        String message() default "Wrong";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default{};

        class EmptyChecker implements ConstraintValidator<IsValidName, String> {

            @Override
            public void initialize(IsValidName arg0) {
            }

            @Override
            public boolean isValid(String value, ConstraintValidatorContext context) {
                System.out.println(value);
                if (StringUtils.isEmpty(value)) {
                    return false;
                }
                return true;
            }

        }
    }

    /**
     * 注解模型类
     * @author sunpy
     *
     */
    public static class User {

        @IsValidName(message="asdasd")
        private String userName;

        public User(String userName) {
            this.userName = userName;
        }


        public String getUserName() {
            return userName;
        }

    }

    public static void main(String[] args) {

        User user = new User("");
        user.getUserName();

    }

}
