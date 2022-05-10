package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface IMember {
    String getBUId();
    String getName();
    void setName(String name) throws OperationFailed;
    String getEmail();
    void setEmail(String email) throws OperationFailed;
}
