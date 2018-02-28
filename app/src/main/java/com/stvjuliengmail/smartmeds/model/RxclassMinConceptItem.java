package com.stvjuliengmail.smartmeds.model;

/**
 * Created by lisatokunaga on 2/24/18.
 */

public class RxclassMinConceptItem
{
    private String classId;

    private String classType;

    private String className;

    public String getClassId ()
    {
        return classId;
    }

    public void setClassId (String classId)
    {
        this.classId = classId;
    }

    public String getClassType ()
    {
        return classType;
    }

    public void setClassType (String classType)
    {
        this.classType = classType;
    }

    public String getClassName ()
    {
        return className;
    }

    public void setClassName (String className)
    {
        this.className = className;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [classId = "+classId+", classType = "+classType+", className = "+className+"]";
    }
}
