package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CRUDManagerTest {
    private IssueRepository repository = new IssueRepository();
    private IssueManager manager = new IssueManager(repository);
    private Issue first = new Issue(1, "first bug", Set.of("bug", "in progress"), "Vasiliy", 1,
            true, "link 1", "Petr", 3, "milestone 1");
    private Issue second = new Issue(2, "second bug", Set.of("bug", "blocked"), "Ivan", 3,
            false, "link 2", "Petr", 5, "milestone 2");
    private Issue third = new Issue(3, "question", Set.of("in progress"), "Vasiliy", 5,
            true, "link 2", "Ivan", 2, "milestone 2");
    private Issue fourth = new Issue(4, "question", Set.of("bug"), "Vasiliy", 5,
            true, "link 2", "Ivan", 2, "milestone 2");


    @Nested
    public class MultipleItems {

        @BeforeEach
        public void setUp() {
            manager.add(first);
            manager.add(second);
            manager.add(third);
        }

        @Test
        void shouldGetOpenedIssues() {
            List<Issue> actual = manager.getOpenedIssues();
            List<Issue> expected = List.of(first, third);
            assertEquals(expected, actual);
        }

        @Test
        void shouldGetClosedIssues() {
            List<Issue> actual = manager.getClosedIssues();
            List<Issue> expected = List.of(second);
            assertEquals(expected, actual);
        }

        @Test
        void shouldFilterByAuthor() {
            List<Issue> actual = manager.filterByAuthor("vasiliy");
            List<Issue> expected = List.of(first, third);
            assertEquals(expected, actual);
        }

//        @Test
//        void shouldFilterByLabel() {
//            manager.add(fourth);
//            List<Issue> actual = manager.filterByLabel("bug");
//            List<Issue> expected = List.of(first, third);
//            assertEquals(expected, actual);
//        }

    }
}