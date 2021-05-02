package org.mql.crowddonating.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String label;

    @ManyToMany(mappedBy = "domains")
    private List<Association> associations;

    public Domain() {
    }

    public void addAssociation(Association association){
        associations.add(association);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public void setAssociations(List<Association> associations) {
        this.associations = associations;
    }
}