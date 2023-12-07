package com.spring.statistic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="details",  nullable = false)
    private String details;

    @Column(name="date_time_last_change",  nullable = false)
    private LocalDateTime dateTimeLastChange;

    @Column(name="date_time_created",  nullable = false)
    private LocalDateTime dateTimeCreated;

    @Column(name="deadline",  nullable = false)
    private LocalDateTime deadline;

    @Column(name="is_completed", nullable = true)
    private boolean isCompleted;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "todolist_id", referencedColumnName = "id")
    private Todolist todolist;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;
}
