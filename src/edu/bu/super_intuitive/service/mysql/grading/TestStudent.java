/**
 * @Author Zhenghang Yin
 * @Description // Class for testing the Student class
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.service.mysql.grading;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestStudent {
    @Test
    public void testStudent() {
        try {
            Student student = new Student("U82871437");
            System.out.println(student.getName());
            System.out.println(student.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNewUser() {
        try {
            var student = new Student("U00000002", "a", "abc@bu.edu");
            System.out.println(student.getName());
            System.out.println(student.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testModifyUserProperty() {
        try {
            var student = new Student("U00000002");
            student.setName("Test user name");
            student.setEmail("test@bu.edu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInstantTeacherAsStudent() {
        // Exceptions must be thrown here, otherwise the test will not pass
        assertThrows(InstantiationException.class, () -> {
            var student = new Student("U00000000");
            System.out.println(student.getName());
            System.out.println(student.getEmail());
        });

        assertThrows(InstantiationException.class, () -> {
            var inst = new Instructor("U82871437");
            System.out.println(inst.getName());
            System.out.println(inst.getEmail());
        });
    }

    @Test
    public void testTeacherCreate() {
        try {
            var inst = new Instructor("U00000000");
            inst.openCourse("Algorithm Practice", "CS112", "Spring 2024");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
