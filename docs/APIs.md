# API Usage

- [back to README](https://github.com/stv2pointo/SmartMeds/tree/master/README.md)

## JSON Tools
http://jsonviewer.stack.hu/

https://www.getpostman.com/apps


## RxImage
<a href="https://rximage.nlm.nih.gov/docs/doku.ph" target="blank">RxImage</a>

<a href="https://rximage.nlm.nih.gov/index/visualizer/" target="blank">RxImage Request Builder</a>

Example: get list of pills that are red, round and have the letters dp in the imprint https://rximage.nlm.nih.gov/api/rximage/1/rxnav?&resolution=600&color=RED&imprint=dp&shape=ROUND

Example: get a list of colors with which to populate a spinner for collecting user selection: https://rximage.nlm.nih.gov/api/rximage/1/enum/color

## RxNorm
<a href="https://rxnav.nlm.nih.gov/RxNormAPIs.html#" target="blank">RxNorm</a>
Contains many different useful resources when making queries. For example: get a list of candidate pills when the user enters "zocor 10 mg" https://rxnav.nlm.nih.gov/REST/approximateTerm.json?term=zocor%2010%20mg&maxEntries=4  This is probably the best resource for getting a list of possible matches, as it replaces mispelled names with the closes ones. The following is the first 10 results if the prior query only consisted of "zoco": 
{"approximateGroup":{"inputTerm":"zoco","maxEntries":"10","comment":"Replaced zoco with zocor; ","candidate":[{"rxcui":"196503","rxaui":"1090485","score":"100","rank":"1"},{"rxcui":"196503","rxaui":"1090486","score":"100","rank":"1"},{"rxcui":"196503","rxaui":"7883539","score":"100","rank":"1"},{"rxcui":"1187974","rxaui":"3850416","score":"50","rank":"4"},{"rxcui":"1187973","rxaui":"3850415","score":"33","rank":"5"}]}}

## RxTerms
<a href="https://rxnav.nlm.nih.gov/RxTermsAPIREST.html" target="blank">RxTerms</a>
Simple return object with some basic info such as Id, String, full name, dosage form.

## NDF-RT
<a href="https://rxnav.nlm.nih.gov/NdfrtAPIREST.html" target="blank">NDF-RT</a>
I have no idea what this is good for.

## RxClass
<a href="https://rxnav.nlm.nih.gov/RxClassAPIs.html#" target="blank">RxClass</a>
General information about the kind of drug a given pill is. For example: https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=7052&relaSource=NDFRT&relas=may_treat returns an object with a list of diseases that Morphine (rxcui 7052) may treat.

## Note: 
Many examples are given in the XML format. SmartMeds primarily makes use of JSON. Simply insert ".json" into the get request prior to the query string (the question mark). Example:  https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui?rxcui=7052&relaSource=NDFRT&relas=may_treat is the xml version of the above query.

- [back to README](https://github.com/stv2pointo/SmartMeds/tree/master/README.md)

