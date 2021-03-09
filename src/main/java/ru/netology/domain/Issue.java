package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class Issue {
    private int id;
    private String title;
    private Set<String> label;
    private String author;
    private int date;
    private boolean isOpen;
    private String pullRequestLink;
    private String assignedTo;
    private int comments;
    private String milestones;
}
