package com.project.nexedu.validator.validation.user;

import jakarta.validation.GroupSequence;

@GroupSequence({RealNameValidationGroups.NotNullGroup.class, RealNameValidationGroups.SizeCheckGroup.class,
        RealNameValidationGroups.PatternCheckGroup.class})
public interface RealNameValidationSequence {
}