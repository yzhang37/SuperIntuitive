/**
 * @Author Zhenghang Yin
 * @Description // IInstructor is the interface for the instructor.
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface IInstructor extends IMember {
    ICourse[] getOwnedCourses();
    ICourse openCourse(String courseName,
                       String courseAlias,
                       String semester) throws InstantiationException;
    void removeCourse(ICourse course) throws OperationFailed;
    boolean hasOwnedCourse(ICourse course);
}
