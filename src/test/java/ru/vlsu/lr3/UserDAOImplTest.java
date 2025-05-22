// package ru.vlsu.lr3;
// import ru.vlsu.lr3.dao.DatabaseConnection;
// import ru.vlsu.lr3.dao.UserDAO;
// import ru.vlsu.lr3.dao.UserDAOImpl;
// import org.junit.jupiter.api.*;
// import ru.vlsu.lr3.beans.User;

// import java.sql.SQLException;
// import java.util.Date;
// import java.util.List;

// import junit.framework.TestCase;


// public class UserDAOImplTest extends TestCase  {

//     private UserDAO userDAO;
//     private User testUser;

//     @BeforeEach
//     public void setUp() {
//         userDAO = new UserDAOImpl(DatabaseConnection.getDataSource());
//         testUser = new User();
//         testUser.setName("Test1 User");
//         testUser.setEmail("Test1@example.com");
//         testUser.setRegistr_date(new Date());
//         testUser.setTaskId(1); // Пример значения, замените на реальное значение
//     }

//     @Test
//     public void testAddUser() throws SQLException{
//         userDAO.addUser(testUser);
//         assertNotNull(userDAO.getUser(testUser.getId()));
//     }

//     @Test
//     public void testGetUser() throws SQLException {
//             userDAO.addUser(testUser);
//             User retrieved = userDAO.getUser(testUser.getId());
//             assertEquals(testUser.getName(), retrieved.getName());                                                                      
//     }

//     @Test
//     public void testGetAllUser() throws SQLException {
//             userDAO.addUser(testUser);
//             List<User> users = userDAO.getAllUser();
//             assertFalse(users.isEmpty());
//     }

//     @Test
//     public void testUpdateUser() throws SQLException {
//         testUser.setName("Updated User");    
//         userDAO.addUser(testUser);
//             userDAO.updateUser(testUser);
//             User updated = userDAO.getUser(testUser.getId());
//             assertEquals("Updated User", updated.getName());
        
//     }

//     @Test
//     public void testDeleteUser() throws SQLException {
        
//             userDAO.addUser(testUser);
//             userDAO.deleteUser(testUser.getId());
//             assertNull("User should be deleted",userDAO.getUser(testUser.getId()));
        
//     }
// }
