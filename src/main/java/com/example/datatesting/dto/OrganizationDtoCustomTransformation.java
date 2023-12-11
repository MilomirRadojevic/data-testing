package com.example.datatesting.dto;

import java.util.List;

public record OrganizationDtoCustomTransformation(Long organizationId, String organizationName, List<PersonDtoCustomTransformation> people) {
}
