package com.project.nexedu.validator.validation.user;

import jakarta.validation.GroupSequence;

@GroupSequence({NicknameValidationGroups.NotNullGroup.class, NicknameValidationGroups.SizeCheckGroup.class,
        NicknameValidationGroups.PatternCheckGroup.class})
public interface NicknameValidationSequence {
}