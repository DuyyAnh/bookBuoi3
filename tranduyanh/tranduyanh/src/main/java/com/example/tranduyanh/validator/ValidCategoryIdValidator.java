package com.example.tranduyanh.validator;

import com.example.tranduyanh.entity.Category;
import com.example.tranduyanh.validator.annotation.ValidCategoryId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Category> {
    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context ){
        return category != null && category.getId() != null;
    }
}
