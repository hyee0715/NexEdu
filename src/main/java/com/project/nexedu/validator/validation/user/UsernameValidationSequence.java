package com.project.nexedu.validator.validation.user;

import jakarta.validation.GroupSequence;

@GroupSequence({UsernameValidationGroups.NotNullGroup.class, UsernameValidationGroups.SizeCheckGroup.class,
        UsernameValidationGroups.PatternCheckGroup.class})
public interface UsernameValidationSequence {
}