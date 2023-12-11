package com.example.datatesting.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OrganizationTransformer {
    public List<OrganizationDtoCustomTransformation> transform(List<Object[]> rs) {
        final Map<Long, OrganizationDtoCustomTransformation> organizationsDtoMap = new HashMap<>();
        for (Object[] o : rs) {
            Long organizationId = ((Number) o[0]).longValue();
            OrganizationDtoCustomTransformation organizationDto = organizationsDtoMap.get(organizationId);

            if (organizationDto == null) {
                organizationDto = new OrganizationDtoCustomTransformation(((Number) o[0]).longValue(), (String) o[1], new ArrayList<>());
            }

            if (o[2] != null) {
                PersonDtoCustomTransformation personDto = new PersonDtoCustomTransformation(((Number) o[2]).longValue(), (String) o[3], (String) o[4]);
                organizationDto.people().add(personDto);
            }

            organizationsDtoMap.putIfAbsent(organizationDto.organizationId(), organizationDto);
        }
        return new ArrayList<>(organizationsDtoMap.values());
    }
}
