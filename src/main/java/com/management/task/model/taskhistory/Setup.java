package com.management.task.model.taskhistory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "setup", schema = "public")
@Getter
@Setter
public class Setup {

    @Id
    @Column(name = "s_key", length = 30)
    private String id;

    @Column(name = "s_status", nullable = false)
    private boolean status;

}
