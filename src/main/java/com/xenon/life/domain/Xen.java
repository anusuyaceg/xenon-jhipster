package com.xenon.life.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Xen.
 */
@Entity
@Table(name = "xen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Xen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "is_task")
    private Boolean isTask;

    @Column(name = "is_compleated")
    private Boolean isCompleated;

    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Xen title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Xen description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsTask() {
        return isTask;
    }

    public Xen isTask(Boolean isTask) {
        this.isTask = isTask;
        return this;
    }

    public void setIsTask(Boolean isTask) {
        this.isTask = isTask;
    }

    public Boolean isIsCompleated() {
        return isCompleated;
    }

    public Xen isCompleated(Boolean isCompleated) {
        this.isCompleated = isCompleated;
        return this;
    }

    public void setIsCompleated(Boolean isCompleated) {
        this.isCompleated = isCompleated;
    }

    public Category getCategory() {
        return category;
    }

    public Xen category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Xen xen = (Xen) o;
        if (xen.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, xen.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Xen{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", isTask='" + isTask + "'" +
            ", isCompleated='" + isCompleated + "'" +
            '}';
    }
}
