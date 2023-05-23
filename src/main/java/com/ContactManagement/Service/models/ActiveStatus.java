package com.ContactManagement.Service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "active_status")
public class ActiveStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @OneToMany(mappedBy = "activeStatus")
    private List<Contact> contact = new ArrayList<>();
    private enum Type{
        ACTIVE(1, "active"),
        INACTIVE(2, "inactive");

        private int id;
        private String value;

        Type(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public int getId(){
            return this.id;
        }

        public String getValue(){
            return this.value;
        }
    }

    public static ActiveStatus getInstanceOf(Type type){
        if (Objects.nonNull(type))
            return ActiveStatus.builder().id(type.getId()).value(type.getValue()).build();

        return null;
    }

    public static ActiveStatus active(){
        return getInstanceOf(Type.ACTIVE);
    }

    public static ActiveStatus inactive(){
        return getInstanceOf(Type.INACTIVE);
    }

}
