package com.stvjuliengmail.smartmeds.model;

/**
 * Created by lisatokunaga on 2/24/18.
 */

public class RxclassTree
{
    private RxclassMinConceptItem rxclassMinConceptItem;

    public RxclassMinConceptItem getRxclassMinConceptItem ()
    {
        return rxclassMinConceptItem;
    }

    public void setRxclassMinConceptItem (RxclassMinConceptItem rxclassMinConceptItem)
    {
        this.rxclassMinConceptItem = rxclassMinConceptItem;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rxclassMinConceptItem = "+rxclassMinConceptItem+"]";
    }
}