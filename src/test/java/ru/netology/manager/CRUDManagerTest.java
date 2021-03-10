package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.IssueComparatorByDate;
import ru.netology.NotFoundException;
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
    private Issue fourth = new Issue(4, "question", Set.of("bug"), "Petr", 2,
            true, "link 2", "Ivan", 2, "milestone 2");

    @Nested
    public class Empty {

        @Test
        void shouldAddAndGetOpenedIssues() {
            manager.add(first);
            List<Issue> actual = manager.getOpenedIssues();
            List<Issue> expected = List.of(first);
            assertEquals(expected, actual);
        }

        @Test
        void shouldGetClosedIssues() {
            List<Issue> actual = manager.getClosedIssues();
            List<Issue> expected = new ArrayList<>();
            assertEquals(expected, actual);
        }

        @Test
        void shouldFilterByLabel() {
            List<Issue> actual = manager.filterByLabel("bug");
            List<Issue> expected = new ArrayList<>();
            assertEquals(expected, actual);
        }

        @Test
        void shouldSortNewest() {
            List<Issue> actual = manager.sortNewest();
            List<Issue> expected = new ArrayList<>();
            assertEquals(expected, actual);
        }

        @Test
        void shouldOpenIssue() {
            assertThrows(NotFoundException.class, () -> manager.openIssue(2));
        }

        @Test
        void shouldCloseIssue() {
            assertThrows(NotFoundException.class, () -> manager.closeIssue(2));
        }
    }

    @Nested
    public class SingleItem {

        @BeforeEach
        public void setUp() {
            manager.add(first);
        }

        @Test
        void shouldGetOpenedIssues() {
            List<Issue> actual = manager.getOpenedIssues();
            List<Issue> expected = List.of(first);
            assertEquals(expected, actual);
        }

        @Test
        void shouldGetClosedIssues() {
            List<Issue> actual = manager.getClosedIssues();
            List<Issue> expected = new ArrayList<>();
            assertEquals(expected, actual);
        }


        @Test
        void shouldFilterByAuthor() {
            List<Issue> actual = manager.filterByAuthor("Vasiliy");
            List<Issue> expected = List.of(first);
            assertEquals(expected, actual);
        }

        @Test
        void shouldFilterByAssignee() {
            List<Issue> actual = manager.filterByAssignee("Ivan");
            List<Issue> expected = new ArrayList<>();
            assertEquals(expected, actual);
        }

        @Test
        void shouldSortNewest() {
            List<Issue> actual = manager.sortNewest();
            List<Issue> expected = List.of(first);
            assertEquals(expected, actual);
        }

        @Test
        void shouldCloseIssue() {
            manager.closeIssue(1);
            boolean actual = repository.getById(1).isOpen();
            assertEquals(false, actual);
        }

        @Test
        void shouldOpenIssueNotExist() {
            assertThrows(NotFoundException.class, () -> manager.closeIssue(2));
        }
    }

    @Nested
    public class MultipleItems {

        @BeforeEach
        public void setUp() {
            manager.add(first);
            manager.add(second);
            manager.add(third);
            manager.add(fourth);
        }

        @Test
        void shouldGetOpenedIssues() {
            List<Issue> actual = manager.getOpenedIssues();
            List<Issue> expected = List.of(first, third, fourth);
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

        @Test
        void shouldFilterByLabel() {
            List<Issue> actual = manager.filterByLabel("in progress");
            List<Issue> expected = List.of(first, third);
            assertEquals(expected, actual);
        }

        @Test
        void shouldFilterByAssignee() {
            List<Issue> actual = manager.filterByAssignee("IVAN");
            List<Issue> expected = List.of(third, fourth);
            assertEquals(expected, actual);
        }

        @Test
        void shouldFilterByAssigneeNotExist() {
            List<Issue> actual = manager.filterByAssignee("nobody");
            List<Issue> expected = new ArrayList<>();
            assertEquals(expected, actual);
        }

        @Test
        void shouldSortNewest() {
            List<Issue> actual = manager.sortNewest();
            List<Issue> expected = List.of(third, second, fourth, first);
            assertEquals(expected, actual);
        }

        @Test
        void shouldSortOldest() {
            List<Issue> actual = manager.sortOldest();
            List<Issue> expected = List.of(first, fourth, second, third);
            assertEquals(expected, actual);
        }

        @Test
        void shouldOpenClosedIssue() {
            manager.openIssue(2);
            boolean actual = repository.getById(2).isOpen();
            assertEquals(true, actual);
        }

        @Test
        void shouldOpenOpenedIssue() {
            manager.openIssue(1);
            boolean actual = repository.getById(1).isOpen();
            assertEquals(true, actual);
        }

        @Test
        void shouldCloseOpenedIssue() {
            manager.closeIssue(1);
            boolean actual = repository.getById(1).isOpen();
            assertEquals(false, actual);
        }

        @Test
        void shouldCloseClosedIssue() {
            manager.closeIssue(2);
            boolean actual = repository.getById(2).isOpen();
            assertEquals(false, actual);
        }
    }
}
