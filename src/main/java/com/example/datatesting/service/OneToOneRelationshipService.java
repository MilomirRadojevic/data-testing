package com.example.datatesting.service;

import com.example.datatesting.entity.Developer;
import com.example.datatesting.entity.LinkedinProfile;
import com.example.datatesting.repository.DeveloperRepository;
import com.example.datatesting.repository.LinkedinProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OneToOneRelationshipService {
    // use maps id annotation for best performance
    // use unidirectional or bidirectional as long previous point is respected (here we use unidirectional)
    // no cascading
    // keep both sides in sync if bidirectional
    // equals, hash code, to string overriding
    // specify join column

    private final DeveloperRepository developerRepository;
    private final LinkedinProfileRepository linkedinProfileRepository;

    private static final Long DEVELOPER_ID = 1L;

    @Transactional
    public void addDeveloperAndLinkedinProfile() {
        Developer developer = new Developer();
        developer.setName("d name");

        LinkedinProfile linkedinProfile = new LinkedinProfile();
        linkedinProfile.setName("lp name");
        linkedinProfile.setDeveloper(developer);

        developerRepository.save(developer);
        linkedinProfileRepository.save(linkedinProfile);
    }

    public void findLinkedinProfileByDeveloper() {
        LinkedinProfile linkedinProfile = linkedinProfileRepository.findById(DEVELOPER_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
    }
}
