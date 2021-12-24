package com.decode.nextjob.helpers

object Constants  {
    public val MAIN_ACTIVIY_ID= 123
    public val ALL_JONS_ACTIVIY_ID= 1234

    //Todo required
    public val INDEED_BASE_URL ="https://awesome-indeed.p.rapidapi.com/"
    public val X_RapidAPI_Host="awesome-indeed.p.rapidapi.com"
    public val X_RapidAPI_Key="317bf47d98mshc75cb4a3700edd3p185034jsn5dda3335d9f1"
    public val SEARCH_QUERY=""


    // TODO OPTIONAL //The page number to retrieve, default value is 1
    public val page_number = 1

    // TODO OPTIONAL
    //if not provided, the country will be US by default.
    //
    //Examples of country codes:
    //ca - Canada
    //uk - United Kingdom
    //it - Italy
    //ae - United Arab Emirates
    //sa - Saudi Arabia
    //de - Germany
    //jp - Japan
    //tr - Turkey
    //eg - Egypt
    //id - Indonesia
    //cn - China
    //….. There is many more, you can search for the code of the country you want jobs for
    public val COUNTRY =""

    // TODO OPTIONAL The location of jobs to be retrieved. you can add any location.
    public val LOCATION= ""

    // TODO OPTIONAL sort retrieved jobs, supported values are 'date' and 'relevance', default value is relevance.
    public val SORT_BY=""

    // TODO OPTIONAL The type of the job. supported values are 'fulltime', 'parttime', 'contract', 'temporary' and 'internship'.
    public val JOB_TYPE=""

    // TODO OPTIONAL The level of proficiency for the jobs to be retrieved, supported values are 'entry', 'mid' and 'senior'
    public val EXPERIENCE_LEVEL=""

    // TODO acept 1...14
    public val FROM_DAYS_AGO=""


}