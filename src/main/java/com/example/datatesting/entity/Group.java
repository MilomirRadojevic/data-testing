package com.example.datatesting.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity(name = "Group_")
@Getter
@Setter
@ToString
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Efficient bidirectional many to many mapping with additional columns realized via two bidirectional one to many mappings (parent side)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group", orphanRemoval = true)
    @Exclude
    private List<UserGroup> users = new ArrayList<>();

    public void addUser(User user) {
        UserGroup userGroup = new UserGroup(user, this);
        users.add(userGroup);
        user.getGroups().add(userGroup);
    }

    public void removeUser(User user) {
        Iterator<UserGroup> iterator = users.iterator();
        while (iterator.hasNext()) {
            UserGroup userGroup = iterator.next();
            if (userGroup.getUser().equals(user) && userGroup.getGroup().equals(this)) {
                iterator.remove();
                userGroup.getUser().getGroups().remove(userGroup);
                userGroup.setUser(null);
                userGroup.setGroup(null);
            }
        }
    }

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
        return id != null && id.equals(((Group) obj).id);
    }

    @Override
    public int hashCode() {
        return 8;
    }
}
