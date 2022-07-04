package com.example.springbootdatajpa.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
// import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
/**
 * Si se necesita definir el nombre de la tabla porque no es el mismo de la base
 * de datos se ocupa la anotacion @Table
 * 
 * @Table(name = "customers")
 */
// @Table(name = "customers")
public class Customer implements Serializable {

    @Id // Indica que este atributo es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Si se necesita definir el nombre de la columna porque no es el mismo de la
     * base de datos se ocupa la anotacion @Table
     * 
     * @Column(name = "nombredistinto")
     */

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    // Timestamps
    @Column(name = "created_at")
    // Define el formato en como se guardará en la base
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private static final long serialVersionUID = 1L;

    @PrePersist
    // este metodo se ejecuta antes de que se guarde en la bd
    public void prePersist() {
        createdAt = new Date();
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Date return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}