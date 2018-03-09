package com.stvjuliengmail.smartmeds.model;

/**
 * Created by danm1 on 3/9/2018.
 */

public class ClassNameResult {
    private RxclassDrugInfoList rxclassDrugInfoList;

    public RxclassDrugInfoList getRxclassDrugInfoList() {
        return rxclassDrugInfoList;
    }

    //
    public class RxclassDrugInfoList
    {
        private RxclassDrugInfo[] rxclassDrugInfo;

        public RxclassDrugInfo[] getRxclassDrugInfo ()
        {
            return rxclassDrugInfo;
        }

    }

    //
    public class RxclassDrugInfo
    {
        private String relaSource;

        private String rela;

        private RxclassMinConceptItem rxclassMinConceptItem;

        public String getRelaSource ()
        {
            return relaSource;
        }

        public String getRela ()
        {
            return rela;
        }

        public RxclassMinConceptItem getRxclassMinConceptItem ()
        {
            return rxclassMinConceptItem;
        }

    }

    //
    public class RxclassMinConceptItem
    {
        private String classId;

        private String classType;

        private String className;

        public String getClassId ()
        {
            return classId;
        }

        public String getClassType ()
        {
            return classType;
        }

        public String getClassName ()
        {
            return className;
        }

    }
    //
}
