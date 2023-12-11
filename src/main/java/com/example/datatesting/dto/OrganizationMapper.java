package com.example.datatesting.dto;

import com.example.datatesting.dto.PersonDtoSimpleOpenProjection.OrganizationDtoClassNested;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public OrganizationDtoClassNested buildOrganizationDto(String name, String motto) {
        return new OrganizationDtoClassNested(name, motto);
    }
}
