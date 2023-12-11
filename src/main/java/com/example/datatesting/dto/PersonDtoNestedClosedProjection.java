package com.example.datatesting.dto;

public interface PersonDtoNestedClosedProjection {
    String getName();
    OrganizationDtoNested getOrganization();
    interface OrganizationDtoNested {
        String getName();
        String getMotto();
    }
}
