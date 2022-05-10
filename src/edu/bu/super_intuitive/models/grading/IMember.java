/**
 * @Author Zhenghang Yin
 * @Description // IMember is the interface for the member class.
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface IMember {
    String getBUId();
    String getName();
    void setName(String name) throws OperationFailed;
    String getEmail();
    void setEmail(String email) throws OperationFailed;
}
