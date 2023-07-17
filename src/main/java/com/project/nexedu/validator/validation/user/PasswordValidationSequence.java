package com.project.nexedu.validator.validation.user;

import jakarta.validation.GroupSequence;

@GroupSequence({PasswordValidationGroups.NotNullGroup.class, PasswordValidationGroups.SizeCheckGroup.class,
        PasswordValidationGroups.PatternCheckGroup.class})
public interface PasswordValidationSequence {
}