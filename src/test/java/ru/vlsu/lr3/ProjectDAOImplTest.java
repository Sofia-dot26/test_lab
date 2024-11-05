package ru.vlsu.lr3;
import ru.vlsu.lr3.dao.DatabaseConnection;
import ru.vlsu.lr3.dao.ProjectDAO;
import ru.vlsu.lr3.dao.ProjectDAOImpl;
import org.junit.jupiter.api.*;
import ru.vlsu.lr3.beans.Project;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

public class ProjectDAOImplTest extends TestCase {

    private ProjectDAO projectDAO;
    private Project testProject;

    @BeforeEach
    public void setUp() {
        projectDAO = new ProjectDAOImpl(DatabaseConnection.getDataSource());
        testProject = new Project();
        testProject.setName("Test Project");
        testProject.setDescription("Test Description");
        testProject.setStart_date(new Date());
        testProject.setEnd_date(new Date());
    }

    @Test
    public void testAddProject() {
        projectDAO.addProject(testProject);
        assertNotNull(testProject.getId());
    }

    @Test
    public void testGetProject() {
        projectDAO.addProject(testProject);
        Project retrieved = projectDAO.getProject(testProject.getId());
        assertEquals(testProject.getName(), retrieved.getName());
    }

    @Test
    public void testGetAllProject() {
        projectDAO.addProject(testProject);
        List<Project> projects = projectDAO.getAllProject();
        assertFalse("Project list should not be empty after insertion", projects.isEmpty());
    }

    @Test
    public void testUpdateProject() {
        projectDAO.addProject(testProject);
        testProject.setName("Updated Project");
        projectDAO.updateProject(testProject);
        Project updated = projectDAO.getProject(testProject.getId());
        assertEquals("Updated Project", updated.getName());
    }

    @Test
    public  void testDeleteProject() {
        projectDAO.addProject(testProject);
        projectDAO.deleteProject(testProject.getId());
        assertNull( "Project should be deleted", projectDAO.getProject(testProject.getId()));
    }
}
