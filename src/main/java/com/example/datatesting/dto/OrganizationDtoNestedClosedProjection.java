package com.example.datatesting.dto;

public interface OrganizationDtoNestedClosedProjection {
    String getName();
    PersonDtoNested getPerson();
    interface PersonDtoNested {
        String getName();
        String getNickname();
    }
}
