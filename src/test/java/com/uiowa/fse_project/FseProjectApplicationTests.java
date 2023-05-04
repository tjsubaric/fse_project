package com.uiowa.fse_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import com.uiowa.fse_project.controller.AdminController;
import com.uiowa.fse_project.controller.HomeController;
import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.model.Employee;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.model.UserDtls;
import com.uiowa.fse_project.service.AdminService;
import com.uiowa.fse_project.service.UserService;

@SpringBootTest
@Transactional
class FseProjectApplicationTests {

    @Autowired
    private AdminController adminController;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    private Admin existingAdmin;

    @BeforeEach
    public void setup() {
        existingAdmin = new Admin();
        existingAdmin.setFirstName("Super");
        existingAdmin.setLastName("Admin");
        existingAdmin.setEmail("superadmin@test.com");
        existingAdmin.setPassword(new BCryptPasswordEncoder().encode("password"));

        adminService.saveAdmin(existingAdmin);
    }

    @Test
    public void testCreateAdmin() throws Exception {
        // create a mock HttpSession
        MockHttpSession session = new MockHttpSession();

        // log in as the existing admin
        session.setAttribute("admin", existingAdmin);

        // create a UserDtls object with test data
        UserDtls user = new UserDtls();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@test.com");
        user.setPassword("password");

        // call the saveAdmin method of the AdminController
        String result = adminController.saveAdmin(user, session);

        // verify that the result of the saveAdmin method is a redirect to the admin home page
        assertEquals("redirect:/admin/", result);

        // retrieve the admin object created by the saveAdmin method
        Admin newAdmin = adminService.getAdminByEmail("johndoe@test.com");

        // verify that the admin object is not null
        assertNotNull(newAdmin);

        // verify that the admin object has the correct properties
        assertEquals("John ", newAdmin.getFirstName());
        assertEquals("Doe", newAdmin.getLastName());
        assertEquals("johndoe@test.com", newAdmin.getEmail());

        // verify that the admin password is encrypted
	    assertFalse(new BCryptPasswordEncoder().matches("password", newAdmin.getPassword()));

        // verify that a UserDtls object with the same email does not already exist in the database
        assertTrue(userService.checkEmail("johndoe@test.com"));
    }

    // test admin creating employee account
    @Test
    public void testCreateEmployee() throws Exception {
        // create a mock HttpSession
        MockHttpSession session = new MockHttpSession();

        // log in as the existing admin
        session.setAttribute("employee", existingAdmin);

        // create a UserDtls object with test data
        UserDtls user = new UserDtls();
        user.setFirstName("Johnny");
        user.setLastName("Sins");
        user.setEmail("johnnysins@test.com");
        user.setPassword("password");

        // call the saveAdmin method of the AdminController
        String result = adminController.saveEmployee(user, session);

        // verify that the result of the saveAdmin method is a redirect to the admin home page
        assertEquals("redirect:/admin/employee_board", result);

        // retrieve the employee object created by the saveAdmin method
        Employee newEmployee = adminService.getEmployeeByEmail("johnnysins@test.com");

        // verify that the admin object is not null
        assertNotNull(newEmployee);

        // verify that the admin object has the correct properties
        assertEquals("Johnny", newEmployee.getFirstName());
        assertEquals("Sins", newEmployee.getLastName());
        assertEquals("johnnysins@test.com", newEmployee.getEmail());

        // verify that the admin password is encrypted
	    assertFalse(new BCryptPasswordEncoder().matches("password", newEmployee.getPassword()));

        // verify that a UserDtls object with the same email does not already exist in the database
        assertTrue(userService.checkEmail("johnnysins@test.com"));
    }

    // test admin creating patient account
    @Test
    public void testCreatePatient() throws Exception {
        // create a mock HttpSession
        MockHttpSession session = new MockHttpSession();

        // log in as the existing admin
        session.setAttribute("patient", existingAdmin);

        // create a UserDtls object with test data
        UserDtls user = new UserDtls();
        user.setFirstName("Theo");
        user.setLastName("Von");
        user.setEmail("theovon@test.com");
        user.setPassword("password");

        // call the saveAdmin method of the AdminController
        String result = adminController.savePatient(user, session);

        // verify that the result of the saveAdmin method is a redirect to the admin home page
        assertEquals("redirect:/admin/patient_board", result);

        // retrieve the employee object created by the saveAdmin method
        Patient newPatient = adminService.getPatientByEmail("theovon@test.com");

        // verify that the admin object is not null
        assertNotNull(newPatient);

        // verify that the admin object has the correct properties
        assertEquals("Theo", newPatient.getFirstName());
        assertEquals("Von", newPatient.getLastName());
        assertEquals("theovon@test.com", newPatient.getEmail());

        // verify that the admin password is encrypted
	    assertFalse(new BCryptPasswordEncoder().matches("password", newPatient.getPassword()));

        // verify that a UserDtls object with the same email does not already exist in the database
        assertTrue(userService.checkEmail("theovon@test.com"));
    }

    @Test
    public void testDeleteAdmin() throws Exception {
        // create a mock HttpSession
        MockHttpSession session = new MockHttpSession();

        // log in as the existing admin
        session.setAttribute("admin", existingAdmin);

        // create a UserDtls object with test data
        UserDtls user = new UserDtls();
        user.setId(113);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@test.com");
        user.setPassword("password");

        // call the saveAdmin method of the AdminController
        adminController.saveAdmin(user, session);

        // delete the admin account
        adminController.deleteAdmin(user.getId());

        // verify that the admin object has been deleted from the database
        assertNull(adminService.getAdminByEmail("johndoe@test.com"));

        // verify that the associated UserDtls object has been deleted from the database
        //assertFalse(userService.checkEmail("johndoe@test.com"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        // create a mock HttpSession
        MockHttpSession session = new MockHttpSession();

        // log in as the existing admin
        session.setAttribute("admin", existingAdmin);

        // create a UserDtls object with test data
        UserDtls user = new UserDtls();
        user.setId(41);
        user.setFirstName("doc");
        user.setLastName("stoopid");
        user.setEmail("stoopid@gmail.com");
        user.setPassword("password");

        // call the saveAdmin method of the AdminController
        adminController.saveEmployee(user, session);

        // delete the admin account
        adminController.deleteEmployee(user.getId());

        // verify that the admin object has been deleted from the database
        assertNull(adminService.getEmployeeByEmail("stoopid@gmail.com"));

        // verify that the associated UserDtls object has been deleted from the database
        //assertFalse(userService.checkEmail("johndoe@test.com"));
    }

    @Test
    public void testDeletePatient() throws Exception {
        // create a mock HttpSession
        MockHttpSession session = new MockHttpSession();

        // log in as the existing admin
        session.setAttribute("admin", existingAdmin);

        // create a UserDtls object with test data
        UserDtls user = new UserDtls();
        user.setId(44);
        user.setFirstName("Michael");
        user.setLastName("Jordan");
        user.setEmail("mj@gmail.com");
        user.setPassword("password");

        // call the saveAdmin method of the AdminController
        adminController.savePatient(user, session);

        // delete the admin account
        adminController.deletePatient(user.getId());

        // verify that the admin object has been deleted from the database
        assertNull(adminService.getPatientByEmail("mj@gmail.com"));

        // verify that the associated UserDtls object has been deleted from the database
        //assertFalse(userService.checkEmail("johndoe@test.com"));
    }
}
