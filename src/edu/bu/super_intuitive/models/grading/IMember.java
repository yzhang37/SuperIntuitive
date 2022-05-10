package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface IMember {
    public String getBUId();
    public String getName();
    public void setName(String name) throws OperationFailed;
    public String getEmail();
    public void setEmail(String email) throws OperationFailed;
}
