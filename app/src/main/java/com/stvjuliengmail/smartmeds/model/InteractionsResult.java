package com.stvjuliengmail.smartmeds.model;

/**
 * Created by Steven on 2/23/2018.
 */

public class InteractionsResult
{
    private InteractionTypeGroup[] interactionTypeGroup;

    private String nlmDisclaimer;

    public InteractionTypeGroup[] getInteractionTypeGroup ()
    {
        return interactionTypeGroup;
    }

    public String getNlmDisclaimer ()
    {
        return nlmDisclaimer;
    }

    public class InteractionTypeGroup
    {
        private InteractionType[] interactionType;

        public InteractionType[] getInteractionType ()
        {
            return interactionType;
        }
    }
    public class InteractionType
    {
        private InteractionPair[] interactionPair;

        public InteractionPair[] getInteractionPair ()
        {
            return interactionPair;
        }
    }

    public class InteractionPair
    {
        private InteractionConcept[] interactionConcept;

        private String description;

        private String severity;

        public InteractionConcept[] getInteractionConcept ()
        {
            return interactionConcept;
        }

        public String getDescription ()
        {
            return description;
        }

        public String getSeverity ()
        {
            return severity;
        }
    }

    public class InteractionConcept
    {
        public SourceConceptItem sourceConceptItem;

        public SourceConceptItem getSourceConceptItem ()
        {
            return sourceConceptItem;
        }
    }

    public class SourceConceptItem
    {
        public String id;

        public String name;

        public String url;

        public String getId ()
        {
            return id;
        }

        public String getName ()
        {
            return name;
        }

        public String getUrl ()
        {
            return url;
        }

    }

}
