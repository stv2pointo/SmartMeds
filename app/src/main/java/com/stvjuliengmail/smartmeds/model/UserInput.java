package com.stvjuliengmail.smartmeds.model;

/**
 * Created by lisatokunaga on 2/24/18.
 */

public class UserInput
{
    private String classId;

    public String getClassId ()
    {
        return classId;
    }

    public void setClassId (String classId)
    {
        this.classId = classId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [classId = "+classId+"]";
    }
}