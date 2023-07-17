package com.project.nexedu.validator.validation.user;

import jakarta.validation.GroupSequence;

@GroupSequence({EmailValidationGroups.NotNullGroup.class, EmailValidationGroups.EmailCheckGroup.class})
public interface EmailValidationSequence {
}