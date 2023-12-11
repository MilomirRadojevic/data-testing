package com.example.datatesting.dto;

import org.springframework.beans.factory.annotation.Value;

public interface PersonDtoSimpleOpenProjection {
    String getPersonName();

    @Value("#{@organizationMapper.buildOrganizationDto(target.organizationName, target.motto)}")
    OrganizationDtoClassNested getOrganization();

    record OrganizationDtoClassNested(String name, String motto) {
    }
}
