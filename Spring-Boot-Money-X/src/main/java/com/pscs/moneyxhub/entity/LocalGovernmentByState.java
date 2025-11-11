package com.pscs.moneyxhub.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "local_government_by_state")
public class LocalGovernmentByState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sno")
    private Integer sno;

    @Column(name = "local_government_area")
    private String localGovernmentArea;

    @Column(name = "state")
    private String state;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getLocalGovernmentArea() {
        return localGovernmentArea;
    }

    public void setLocalGovernmentArea(String localGovernmentArea) {
        this.localGovernmentArea = localGovernmentArea;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
