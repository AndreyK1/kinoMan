package ru.chuhan.demo.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonFilter("depth_5")
public class TreeItem implements Serializable {

//https://stackoverflow.com/questions/1656113/hibernate-recursive-many-to-many-association-with-the-same-entity
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private int id;

    @JsonProperty("firstName")
    private String firstName;


    @ManyToOne
    @JoinColumn(name="theme_id")
    @JsonProperty("theme")
    private Theme theme;

//    @JsonProperty("parent_id")
//    private TreeItem parent;
//
//    @JsonProperty("child_id")
//    private TreeItem child;


    public void addParents(TreeItem parent) {
        this.parents.add(parent);
    }

    //    @JsonProperty("parent_id")
//    @JoinColumn(name = "parent")
    @ManyToMany
    @JoinTable(name="tbl_links",
                    joinColumns=@JoinColumn(name="parentId"),
                    inverseJoinColumns=@JoinColumn(name="childId"))
    @JsonProperty("parents")
    private List<TreeItem> parents = new ArrayList<>();

    public void addChilds(TreeItem child) {
        this.childs.add(child);
    }

    //    @ManyToMany(mappedBy = "child", cascade = CascadeType.ALL)
//    @JsonProperty("children")
//    @Lasy
    @ManyToMany
    @JoinTable(name="tbl_links",
            joinColumns=@JoinColumn(name="childId"),
            inverseJoinColumns=@JoinColumn(name="parentId"))

    @JsonProperty("childs")
    private List<TreeItem> childs = new ArrayList<>();


    @JsonProperty("user_id")
    @Column(columnDefinition="bigint default 0")
    private long user_id;

    @JsonProperty("member")
    @Column(columnDefinition="int default 1")
    private int member;


//    public void addChildren(TreeItem children) {
//        this.children.add(children);
//    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

//    public void setParent(TreeItem parent) {
//        this.parent = parent;
//    }
}
