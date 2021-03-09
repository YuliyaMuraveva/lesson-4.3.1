package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        this.repository = repository;
    }

    public void add(Issue issue) {
        repository.save(issue);
    }

    public List<Issue> getAll() {
        return repository.findAll();
    }

    /* Get opened issues */
    public List<Issue> getOpenedIssues() {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.findAll()) {
            if (issue.isOpen()) {
                result.add(issue);
            }
        }
        return result;
    }

    /* Get closed issues */
    public List<Issue> getClosedIssues() {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.findAll()) {
            if (!issue.isOpen()) {
                result.add(issue);
            }
        }
        return result;
    }

    /* Filter by author */
//    public List<Issue> filterByAuthor(String author) {
//        List<Issue> result = new ArrayList<>();
//        for (Issue issue : repository.findAll()) {
//            if (issue.getAuthor() == author) {
//                result.add(issue);
//            }
//        }
//        return result;
//    }

    public List<Issue> filterByAuthor(String author) {
        List<Issue> result = new ArrayList<>();
        Predicate<String> isAuthor = t -> t.equalsIgnoreCase(author);
        for (Issue issue : repository.findAll()) {
            if (isAuthor.test(issue.getAuthor())) {
                result.add(issue);
            }
        }
        return result;
    }

    public List<Issue> filterByLabel(String label) {
        List<Issue> result = new ArrayList<>();
        Predicate<Issue> isLabel = t -> t.getLabel().equals(label);
        for (Issue issue : repository.findAll()) {
            if (isLabel.test(issue)) {
                result.add(issue);
            }
        }
        return result;
    }


}
