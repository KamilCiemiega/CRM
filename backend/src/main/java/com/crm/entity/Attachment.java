package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name="file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name="message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name="reporting_id")
    private Reporting reporting;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

    public enum Type {MESSAGE, REPORTING, TASK}
}
