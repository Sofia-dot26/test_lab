// package ru.vlsu.lr3;
// import ru.vlsu.lr3.dao.DatabaseConnection;
// import ru.vlsu.lr3.dao.TaskDAO;
// import ru.vlsu.lr3.dao.TaskDAOImpl;
// import ru.vlsu.lr3.beans.Task;
// import org.junit.jupiter.api.*;

// import junit.framework.TestCase;

// import java.util.Date;
// import java.util.List;

// public class TaskDAOImplTest extends TestCase {

//     private TaskDAO taskDAO;
//     private Task testTask;

//     @BeforeEach
//     public void setUp() {
//         taskDAO = new TaskDAOImpl(DatabaseConnection.getDataSource());
//         testTask = new Task();
//         testTask.setName("Test Task");
//         testTask.setDesc("Test Description");
//         testTask.setDue_date(new Date());
//         testTask.setStatus("In Progress");
//     }

//     @Test
//     public void testAddTask() {
//         taskDAO.addTask(testTask);
//         assertNotNull(testTask.getId());
//     }

//     @Test
//     public void testGetTask() {
//         taskDAO.addTask(testTask);
//         Task retrieved = taskDAO.getTask(testTask.getId());
//         assertEquals(testTask.getName(), retrieved.getName());
//     }

//     @Test
//     public void testGetAllTasks() {
//         taskDAO.addTask(testTask);
//         List<Task> tasks = taskDAO.getAllTasks();
//         assertFalse("Task list should not be empty after insertion", tasks.isEmpty());
//     }

//     @Test
//     public void testUpdateTask() {
//         taskDAO.addTask(testTask);
//         testTask.setName("Updated Task");
//         taskDAO.updateTask(testTask);
//         Task updated = taskDAO.getTask(testTask.getId());
//         assertEquals("Updated Task", updated.getName());
//     }

//     @Test
//     public void testDeleteTask() {
//         taskDAO.addTask(testTask);
//         taskDAO.deleteTask(testTask.getId());
//         assertNull("Task should be deleted",taskDAO.getTask(testTask.getId()));
//     }
// }
