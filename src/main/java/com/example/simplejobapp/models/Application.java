package com.example.simplejobapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Application extends Model{
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String emailAddress;
    private String address;
    @Column(nullable = false)
    private String birthDate;
    private String cvAttachment;
    @Column(columnDefinition = "longtext null")
    private String coverLetter;

    private String status;

    @ManyToOne
    @JoinColumn(name = "hr_assessed_id")
    private User hrAssessed;
}
