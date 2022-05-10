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
    // get the BUID
    String getBUId();
    // get the name
    String getName();
    // set the name
    void setName(String name) throws OperationFailed;
    // get the email
    String getEmail();
    // set the email
    void setEmail(String email) throws OperationFailed;
}
