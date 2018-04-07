package com.stvjuliengmail.smartmeds.api;

/**
 * Created by shaht_000 on 4/5/2018.
 */

public class NameSearchResult
{
    private SuggestionGroup suggestionGroup;

    public SuggestionGroup getSuggestionGroup ()
    {
        return suggestionGroup;
    }

    public void setSuggestionGroup (SuggestionGroup suggestionGroup)
    {
        this.suggestionGroup = suggestionGroup;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [suggestionGroup = "+suggestionGroup+"]";
    }

    public class SuggestionGroup
    {
        private String name;

        private SuggestionList suggestionList;

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public SuggestionList getSuggestionList ()
        {
            return suggestionList;
        }

        public void setSuggestionList (SuggestionList suggestionList)
        {
            this.suggestionList = suggestionList;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [name = "+name+", suggestionList = "+suggestionList+"]";
        }
    }

    public class SuggestionList
    {
        private String[] suggestion;

        public String[] getSuggestion ()
        {
            return suggestion;
        }

        public void setSuggestion (String[] suggestion)
        {
            this.suggestion = suggestion;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [suggestion = "+suggestion+"]";
        }
    }


}
