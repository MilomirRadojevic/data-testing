package com.example.datatesting.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserGroup {
    @EmbeddedId
    private UserGroupId id = new UserGroupId();

    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @Exclude
    private Group group;

    public UserGroup(User user, Group group) {
        this.user = user;
        this.group = group;
        this.id = new UserGroupId(user.getId(), group.getId());
    }

    // Equals and hash code must be based on both relationship members
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserGroup other = (UserGroup) obj;
        return Objects.equals(user, other.user) && Objects.equals(group, other.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, group);
    }
}
