package com.example.datatesting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
// It is important to be serializable
public class UserGroupId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    // Equals and hash code must be based on both ids
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
        UserGroupId other = (UserGroupId) obj;
        return Objects.equals(userId, other.userId) && Objects.equals(groupId, other.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId);
    }
}
