package ru.netology.manager;

import ru.netology.IssueComparatorByDate;
import ru.netology.NotFoundException;
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

    /* filters */
    public List<Issue> filterByPredicate(Predicate<Issue> filter) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.findAll()) {
            if (filter.test(issue)) {
                result.add(issue);
            }
        }
        return result;
    }

    public List<Issue> filterByAuthor(String author) {
        return filterByPredicate(issue -> issue.getAuthor().equalsIgnoreCase(author));
    }

    public List<Issue> filterByLabel(String label) {
        return filterByPredicate(issue -> issue.getLabel().contains(label));
    }

    public List<Issue> filterByAssignee(String assignee) {
        return filterByPredicate(issue -> issue.getAssignedTo().equalsIgnoreCase(assignee));
    }

    /* sorters */
    public List<Issue> sortNewest() {
        List<Issue> result = this.getAll();
        result.sort(new IssueComparatorByDate());
        return result;
    }

    public List<Issue> sortOldest() {
        List<Issue> result = this.getAll();
        result.sort(new IssueComparatorByDate().reversed());
        return result;
    }

    /* open and close issue */
    public void openIssue(int id) {
        Issue issue = repository.getById(id);
        if (issue == null) {
            throw new NotFoundException("Issue with id: " + id + " not found");
        }
        else if (!issue.isOpen()) {
            issue.setOpen(true);
        }
    }

    public void closeIssue(int id) {
        Issue issue = repository.getById(id);
        if (issue == null) {
            throw new NotFoundException("Issue with id: " + id + " not found");
        }
        else if (issue.isOpen()) {
            issue.setOpen(false);
        }
    }
}
