package entities;

import java.util.Set;
import java.util.TreeSet;

public class Issue extends TableBase{

    private static int idCount = 1;
    private static Set<Issue> issueList;

    public Issue() {
    }

    private Project project;
    private User user;
    private String description;

    public Issue(Project project, User user, String description) {
        super(idCount);
        idCount++;

        this.project = project;
        this.user = user;
        this.description = description + id;

        addIssue(this);
    }

    public static void addIssue(Issue issue) {
        if (issueList == null)
            issueList = new TreeSet<>();
        issueList.add(issue);
    }

    @Override
    public Set<Issue> getList() {
        return issueList;
    }

    @Override
    public void setList(Set entities) {
        issueList = entities;
        idCount = issueList.size() + 1;
    }

    @Override
    public String toString() {
        return super.toString() + project.getName() + "  :  " + user.getName() + "  :  " + description;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

}