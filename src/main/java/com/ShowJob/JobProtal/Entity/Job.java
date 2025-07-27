package com.ShowJob.JobProtal.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String location;
    private String description;
    private String type;
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate = new Date();

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;

    public Job() {}

    public Job(Long id, String title, String company, String location, String description, String type, String category,
               Date postedDate, User postedBy) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        this.type = type;
        this.category = category;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
    }

    // Getters and Setters
    // ...
}
