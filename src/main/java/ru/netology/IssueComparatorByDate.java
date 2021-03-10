package ru.netology;

import ru.netology.domain.Issue;

import java.util.Comparator;

public class IssueComparatorByDate implements Comparator<Issue> {
    public int compare(Issue o1, Issue o2){
        return o2.getDate() - o1.getDate();
    }
}
