// wa_process.js Version 3.2
// Last modified by:
// Susan Hullum 3/30/2007
// History */
// Added Header to track version and history
// Added wa_custom05 to variable > 80 check and org1 between 2 and 4 chars check
// -------------------------------------
// Version 2.1 - 16 Feb 2006 - Added custom links function and tagged links function
// -------------------------------------
// Version 2.2 - 21 Aug 2006 - First party cookie code and addition of 'txt' file extension to 
// download types, hard-coded version number to s_prop13
// -------------------------------------
// Version 3.0 - 18 Sep 2006 - added Flash Tracking functions(waTrackAsLink and waTrackAsPage),
// added new Custom Links function (waCustomLink), and main link processing function (waProcessLink) 
// -------------------------------------
// Version 3.0 - 23 Jan 2007 - fixed report suite bug in custom link and flash page functions. Code
// appending the wa_reportSuites value to the wa_intelCorpRS value and it should not do that in the pre-prod
// environment.
// -------------------------------------
// Version 3.1 - 30 Mar 2007 - Added wa_custom1-15 and wa_ecustom21-35 vars to the waCustomLinks function
// Added 'proc' geo for future usage (possibly)
// -------------------------------------
// Version 3.2 - 27 July 2007 - Added traffic vars: wa_custom36-50; Added Commerce vars: wa_eCustom36-50; Added 
// custom events: se_cust06-se_cust30.
// Added wa_custom36-44 and wa_ecustom36-50 vars to the waCustomLinks function
// -------------------------------------


// to deploy uncomment either the test or production section and remove the others.

// Dev
//  var	wa_intelCorpRS	= "devintelcorptest",
//	wa_errorRS	= "devintelcorperror";

//s_account = wa_intelCorpRS;

//Test
//var	wa_intelCorpRS	= "intelcorptest",
//	wa_errorRS	= "intelcorperrortest";

// s_account = wa_intelCorpRS;

//

//Production

var	wa_intelCorpRS	= "intelcorp",
	wa_errorRS	= "intelcorperror";

s_account = (wa_reportSuites == "") ? wa_intelCorpRS : wa_reportSuites + "," + wa_intelCorpRS;

/***** SC CONFIG SECTION *****/

if (wa_trackDownloads.toLowerCase() == 'n')
{
	var s_trackDownloadLinks = false;
}
else 
{
	var s_trackDownloadLinks = true;
}


var	s_disableLegacyVars=true,
	s_currencyCode="USD",
	s_eVarCFG="",
//	s_trackDownloadLinks=true,
	s_trackExternalLinks=true,
	s_trackInlineStats = true,
	s_linkDownloadFileTypes="exe,dll,com,zip,pdf,arc,bin,sit,tar,gz,z,arj,rpm,rar,doc,mpeg,wav,mp3,mov,mpg,avi,xls,txt",
	s_linkInternalFilters="javascript:,intel",
	s_linkLeaveQueryString=false,
	s_linkTrackVars="None",
	s_linkTrackEvents="None";

/***** Begin WA Code *****/

// Initialize Site Catalyst vars to blank
waInitSCVars();

// Capture IID sent on querystring
var wa_iid = (typeof wa_queryObj.iid == "undefined") ? "" : wa_queryObj.iid;

// Capture campaign tracking code sent on querystring - is either cid or ppc_cid	
cv1=(typeof wa_queryObj.cid == "undefined") ? "" : wa_queryObj.cid		
cv2=(typeof wa_queryObj.ppc_cid == "undefined") ? "" : wa_queryObj.ppc_cid;


if (cv1)
{
	is_org_prepended=cv1.indexOf(':');
	
	if (is_org_prepended == -1){
		wa_campaign = wa_org1 + ":" + cv1;
		}
	else {
		wa_campaign = cv1;
	}		
}
else if (cv2)
{
	is_org_prepended=cv2.indexOf(':');
	if (is_org_prepended == -1) {
		wa_campaign = wa_org1 + ":" + cv2;
	}	
	else {	
		wa_campaign = cv2;
	}
}
else
{
	wa_campaign = "";
}			
// Call Main Processing Function
waProcess();

// Main validation/processing function
// Is called for any page event (on a page load and a Flash page event)
function waProcess()
{

	// set error Message variable to blank - this is a global var

	errorMsg = "";
		
	//populate wa_url
	wa_urlQueryString = wa_urlQueryString.toLowerCase();
	
	switch(wa_urlQueryString)
	{
			
	   case "all":
	      wa_url = unescape(location.href);
		  break;
	   case "":
	   case "none":
	      wa_url = location.protocol + "//" + location.host + unescape(location.pathname);
	      break;
       default :
	      wa_urlQueryString = wa_urlQueryString.split(",");
			var wa_parseUrl = ""
			for (i=0;i<wa_urlQueryString.length;i++)
			{
				if (typeof eval("wa_queryObj." + wa_urlQueryString[i]) != "undefined")
				{
					if (wa_urlQueryString.length > 0)
					{
						wa_parseUrl += '&';
					}
					wa_parseUrl += wa_urlQueryString[i] + '=' + eval("wa_queryObj." + wa_urlQueryString[i])
				}
			}
			wa_url = location.protocol + "//" + location.host + unescape(location.pathname);
			if (wa_parseUrl.length > 0)
			{
			   wa_url += "?" + wa_parseUrl;
			}
	}	

	//Lowercase all page related wa_ variables and validate that they are less then 96 characters
	// Most vars can be 100 chars but need to leave room for the wa_org1 to be prepended 
	var wa_vars = new Array(
		"wa_pageName","wa_org1","wa_org2","wa_org3","wa_org4","wa_orgX",
		"wa_geo","wa_language","wa_iid","wa_reportSuites","wa_url","wa_ngipDocId",
		"wa_ngipUniqueId","wa_campaign","wa_events","wa_urlQueryString",
		"wa_custom01","wa_custom02","wa_custom03","wa_custom04","wa_custom05",
		"wa_custom06","wa_custom07","wa_custom08","wa_custom09","wa_custom10",
		"wa_custom11","wa_custom12","wa_custom13","wa_custom14","wa_custom15",		
		"wa_custom36","wa_custom37","wa_custom38","wa_custom39","wa_custom40",
		"wa_custom41","wa_custom42","wa_custom43","wa_custom44",
		"wa_custom46","wa_custom47","wa_custom48","wa_custom49","wa_custom50",
		"wa_eCustom21","wa_eCustom22","wa_eCustom23","wa_eCustom24","wa_eCustom25",
		"wa_eCustom26","wa_eCustom27","wa_eCustom28","wa_eCustom29","wa_eCustom30",
		"wa_eCustom31","wa_eCustom32","wa_eCustom33","wa_eCustom34","wa_eCustom35",
		"wa_eCustom36","wa_eCustom37","wa_eCustom38","wa_eCustom39","wa_eCustom40",
		"wa_eCustom41","wa_eCustom42","wa_eCustom43","wa_eCustom44","wa_eCustom45",
		"wa_eCustom46","wa_eCustom47","wa_eCustom48","wa_eCustom49","wa_eCustom50",
		"wa_profileID"
		);
	
	// Validate/lowercase all wa_ vars in wa_vars array
	// wa_reportSuites, wa_url, wa_orgX, wa_urlQueryString not validated for length
	
	for (i=0;i<wa_vars.length;i++)
	{
						
	eval(wa_vars[i] + " = " + wa_vars[i] + ".toString().toLowerCase()");
	
				
		switch (wa_vars[i])
		{
						
			case "wa_reportSuites" :
			case "wa_url" :
			case "wa_orgX" :
			case "wa_urlQueryString" :
			case "wa_events" :
				break;
			default :
			
			
				if (isValidLength(eval(wa_vars[i]),96) == false)
				{
					errorMsg = errorMsg + wa_vars[i] + "; ";
					
				}				
		}
	}	
	
	//Validate wa_org1	
	if (isValidLengthRange(wa_org1,2,4) == false)
		errorMsg = errorMsg + "wa_org1; ";
		
		//Validate wa_geo
	if (isValidGeo(wa_geo) == false){
		errorMsg = errorMsg + "wa_geo; ";
		}
		
	//validate wa_referrer and populate s_referrer if not null	
	if (isValidLength(wa_referrer,100) == false){	
		errorMsg = errorMsg + "wa_referrer; ";
		}
	
	//call wa_events set/validate function - the 2nd parameter indicates whether to set event5 also
	waProcessEvents(wa_events,'Y');
		
	// Prepend wa_org1 to pagename
	wa_pageName = (wa_pageName == "") ? wa_org1 + ":" + location.pathname.toLowerCase() : wa_org1 + ":" + wa_pageName;
	
	// set SC vars from wa_ vars	
	
	s_pageName 	= 	wa_pageName;
	s_channel  	=	wa_org1;
	s_prop1 	= 	wa_org2;
	s_prop2 	= 	wa_org3;
	s_prop3 	= 	wa_org4;
	s_prop4 	= 	wa_geo;
	s_prop5 	= 	wa_language;
	s_prop6 	= 	wa_iid;
	s_prop7		= 	wa_reportSuites;
	s_prop8		= 	wa_url;
	s_prop9		= 	wa_visitId;
	s_prop10	= 	wa_ngipDocId;
	s_prop11	= 	wa_ngipUniqueId;
	s_prop12	= 	wa_profileID;
	s_prop13	=	"Version 3.2";
	s_prop21	= 	wa_custom01;
	s_prop22	= 	wa_custom02;
	s_prop23	= 	wa_custom03;
	s_prop24	= 	wa_custom04;
	s_prop25	= 	wa_custom05;
	s_prop26	= 	wa_custom06;
	s_prop27	= 	wa_custom07;
	s_prop28	= 	wa_custom08;
	s_prop29	= 	wa_custom09;
	s_prop30	= 	wa_custom10;
	s_prop31	= 	wa_custom11;
	s_prop32	= 	wa_custom12;
	s_prop33	= 	wa_custom13;
	s_prop34	= 	wa_custom14;
	s_prop35	= 	wa_custom15;
	s_prop36	= 	wa_custom36;
	s_prop37	= 	wa_custom37;
	s_prop38	= 	wa_custom38;
	s_prop39	= 	wa_custom39;
	s_prop40	= 	wa_custom40;
	s_prop41	= 	wa_custom41;
	s_prop42	= 	wa_custom42;
	s_prop43	= 	wa_custom43;
	s_prop44	= 	wa_custom44;
	s_prop46	= 	wa_custom46;
	s_prop47	= 	wa_custom47;
	s_prop48	= 	wa_custom48;
	s_prop49	= 	wa_custom49;
	s_prop50	= 	wa_custom50;	
	s_eVar21	= 	wa_eCustom21;
	s_eVar22	= 	wa_eCustom22;
	s_eVar23	= 	wa_eCustom23;
	s_eVar24	= 	wa_eCustom24;
	s_eVar25	= 	wa_eCustom25;
	s_eVar26	= 	wa_eCustom26;
	s_eVar27	= 	wa_eCustom27;
	s_eVar28	= 	wa_eCustom28;
	s_eVar29	= 	wa_eCustom29;
	s_eVar30	= 	wa_eCustom30;
	s_eVar31	= 	wa_eCustom31;
	s_eVar32	= 	wa_eCustom32;
	s_eVar33	= 	wa_eCustom33;
	s_eVar34	= 	wa_eCustom34;
	s_eVar35	= 	wa_eCustom35;
	s_eVar36	= 	wa_eCustom36;
	s_eVar37	= 	wa_eCustom37;
	s_eVar38	= 	wa_eCustom38;
	s_eVar39	= 	wa_eCustom39;
	s_eVar40	= 	wa_eCustom40;
	s_eVar41	= 	wa_eCustom41;
	s_eVar42	= 	wa_eCustom42;
	s_eVar43	= 	wa_eCustom43;
	s_eVar44	= 	wa_eCustom44;
	s_eVar45	= 	wa_eCustom45;
	s_eVar46	= 	wa_eCustom46;
	s_eVar47	= 	wa_eCustom47;
	s_eVar48	= 	wa_eCustom48;
	s_eVar49	= 	wa_eCustom49;
	s_eVar50	= 	wa_eCustom50;			
	s_campaign 	= 	wa_campaign;
	s_referrer 	= 	wa_referrer;
	s_events	=	wa_events;
	

	// Create hierarchy var s_hier1 by concatenating org1, org2, org3, org4 and orgX
	// Validate s_hier1 that length is < 250 chars and that there are < 15 levels
	
	var wa_orgVars = new Array('wa_org1','wa_org2','wa_org3','wa_org4','wa_orgX');
	
	for (i=0;i<wa_orgVars.length;i++)
	{
		if ((eval(wa_orgVars[i] + ".indexOf(\"|\")") >= 0) && (wa_orgVars[i] != "wa_orgX"))
		{
			errorMsg = errorMsg + wa_orgVars[i] + "; ";
			break;
		}
		
		if (eval(wa_orgVars[i]) == "")
		{
			break;
		}
		
			
		if (s_hier1.length > 0)
		{
			s_hier1 += "|";
		}
		s_hier1 += eval(wa_orgVars[i]);
		
	}
	var wa_h1levels = s_hier1.split("|");
	
	if ((s_hier1.length > 255) || (wa_h1levels.length > 15))
	{
		"Org hierarchy size limit exceeded. Length: " + s_hier1.length + "/255, Levels: " + wa_h1levels.length + "/15; ";
	}

	// Check if any error messages have been generated and call function if true
	
	if (errorMsg != "")
	{
		errorMsg = "Validation failed: " + errorMsg;
		waSendToErrorRS(errorMsg)
	}

}

// called when errors found - sets the error reporting suite and also
// populates s_prop45 with the error message	
function waSendToErrorRS(message)
{
    s_account = wa_errorRS;
    s_prop45  = message;
}

// Check that the number of characters in a string is between a max and a min
function isValidLengthRange(string, min, max) {
				
   if (string.length < min || string.length > max)return false;
   else return true;
}

// Check that variable does not contain more than 'max' chars
function isValidLength(string, max) {

	if (string.length > max)return false;
  	else return true;
}

// check wa_geo var for valid geo and sets unassigned if not
function isValidGeo(geo) {
	
   geo = geo.toLowerCase();	
     
   switch (geo)
	{
	   case "apac" :
	   case "asmo-lar" :
	   case "asmo-na" :
	   case "emea" :
	   case "ijkk" :
	   case "proc" :
	   case "unassigned" :return true;  
	   default : return false;
	}
}

// sets all Site Catalyst vars to blank
function waInitSCVars() {

	s_pageName="",s_server="",s_channel="",s_pageType="",s_referrer="",
	s_prop1="",s_prop2="",s_prop3="",s_prop4="",s_prop5="",s_prop6="",s_prop7="",s_prop8="",s_prop9="",s_prop10="",
	s_prop11="",s_prop12="",s_prop13="",s_prop14="",s_prop15="",s_prop16="",s_prop17="",s_prop18="",s_prop19="",s_prop20="",
	s_prop21="",s_prop22="",s_prop23="",s_prop24="",s_prop25="",s_prop26="",s_prop27="",s_prop28="",s_prop29="",s_prop30="",
	s_prop31="",s_prop32="",s_prop33="",s_prop34="",s_prop35="",s_prop36="",s_prop37="",s_prop38="",s_prop39="",s_prop40="",
	s_prop41="",s_prop42="",s_prop43="",s_prop44="",s_prop45="",s_prop46="",s_prop47="",s_prop48="",s_prop49="",s_prop50="",
	s_campaign="",s_state="",s_zip="",s_events="",s_products="",s_purchaseID="",s_objectID="",
	s_eVar1="",s_eVar2="",s_eVar3="",s_eVar4="",s_eVar5="",s_eVar6="",s_eVar7="",s_eVar8="",s_eVar9="",s_eVar10="",
	s_eVar11="",s_eVar12="",s_eVar13="",s_eVar14="",s_eVar15="",s_eVar16="",s_eVar17="",s_eVar18="",s_eVar19="",s_eVar20="",
	s_eVar21="",s_eVar22="",s_eVar23="",s_eVar24="",s_eVar25="",s_eVar26="",s_eVar27="",s_eVar28="",s_eVar29="",s_eVar30="",
	s_eVar31="",s_eVar32="",s_eVar33="",s_eVar34="",s_eVar35="",s_eVar36="",s_eVar37="",s_eVar38="",s_eVar39="",s_eVar40="",
	s_eVar41="",s_eVar42="",s_eVar43="",s_eVar44="",s_eVar45="",s_eVar46="",s_eVar47="",s_eVar48="",s_eVar49="",s_eVar50="",
	s_hier1="",s_hier2="",s_hier3="",s_hier4="",s_hier5="";
}

// repopulates wa_event var with the appropriate event number
function waProcessEvents(eventStr,setEvent5) {
	
	var newEventStr = "";
	
	// set Event5 on Page Views only	
	if (setEvent5 == "Y")
	{
		newEventStr = "event5,";		
	}
	
	if (eventStr != "")
	{
		
		var linkVars = eventStr.split(",");
	
		for (var i=0;i<linkVars.length;i++){
	
			linkVars[i] = linkVars[i].toLowerCase();
				
			switch(linkVars[i]) {
			    
    			case "se_register" : newEventStr = newEventStr + "event1,"; break;
    			case "se_third_party" : newEventStr = newEventStr + "event2,";	break;
    			case "se_rich_media" : newEventStr = newEventStr + "event3,";	break;	
				case "se_buy" : newEventStr = newEventStr + "event4,"; break;
    			case "se_vid_pct1" : newEventStr = newEventStr + "event7,"; break;
    			case "se_vid_pct2" : newEventStr = newEventStr + "event8,"; break;
    			case "se_cust01" : newEventStr = newEventStr + "event16,"; break;
    			case "se_cust02" : newEventStr = newEventStr + "event17,"; break;
    			case "se_cust03" : newEventStr = newEventStr + "event18,"; break;
    			case "se_cust04" : newEventStr = newEventStr + "event19,"; break;
    			case "se_cust05" : newEventStr = newEventStr + "event20,"; break;
    			case "se_cust06" : newEventStr = newEventStr + "event21,"; break;
    			case "se_cust07" : newEventStr = newEventStr + "event22,"; break;
    			case "se_cust08" : newEventStr = newEventStr + "event23,"; break;
    			case "se_cust09" : newEventStr = newEventStr + "event24,"; break;
    			case "se_cust10" : newEventStr = newEventStr + "event25,"; break;    			
    			case "se_cust11" : newEventStr = newEventStr + "event26,"; break;
    			case "se_cust12" : newEventStr = newEventStr + "event27,"; break;
    			case "se_cust13" : newEventStr = newEventStr + "event28,"; break;
    			case "se_cust14" : newEventStr = newEventStr + "event29,"; break;
    			case "se_cust15" : newEventStr = newEventStr + "event30,"; break;
    			case "se_cust16" : newEventStr = newEventStr + "event31,"; break;
    			case "se_cust17" : newEventStr = newEventStr + "event32,"; break;
    			case "se_cust18" : newEventStr = newEventStr + "event33,"; break;
    			case "se_cust19" : newEventStr = newEventStr + "event34,"; break;
    			case "se_cust20" : newEventStr = newEventStr + "event35,"; break;    			
    			case "se_cust21" : newEventStr = newEventStr + "event36,"; break;
    			case "se_cust22" : newEventStr = newEventStr + "event37,"; break;
    			case "se_cust23" : newEventStr = newEventStr + "event38,"; break;
    			case "se_cust24" : newEventStr = newEventStr + "event39,"; break;
    			case "se_cust25" : newEventStr = newEventStr + "event40,"; break;
    			case "se_cust26" : newEventStr = newEventStr + "event41,"; break;
    			case "se_cust27" : newEventStr = newEventStr + "event42,"; break;
    			case "se_cust28" : newEventStr = newEventStr + "event43,"; break;
    			case "se_cust29" : newEventStr = newEventStr + "event44,"; break;
    			case "se_cust30" : newEventStr = newEventStr + "event45,"; break;
				// default : errorMsg = errorMsg + "Invalid event " + linkVars[i] + "; ";
				 	
			}
		}

	}
	
	if (newEventStr == "")
	{
		wa_events = "";
	}
	else
	{
		strEventLen = String(newEventStr).length;
		strEvents = String(newEventStr).substring(0,(strEventLen-1));	
		// set wa_events - waProcess and custom link functions will assign to s_events
		wa_events = strEvents;
	}	
}

// Tagged Links - used for naming links for Clickmap
function tagLinks(tagName) {
s_objectID = tagName;
}
//  ----------------------

// Main link processing function - called by waCustomLink and waSendLinkEvent functions
function waProcessLink (waURL,waLinkName,waLinkType,waSendVals,waCalledBy)
{

// This function is called by both the Flash link function and the Custom link function
// Different processing occurs based on which one called the function - hence the 'calledBy' parameter
// 'sendVals' is in a named pair format and specifies addition vars to send to SC:
// "wa_custom46=flash 01 value&wa_custom50=flash 05 value";
// Valid additional wa_custom1-15,wa_custom36-50, wa_eCustom21-50, campaign, wa_reportSuites, wa_iid and all events
    
// reset global error message variable
	errorMsg = "";
	
// set wa_events var to blank -- if it was set on page, then it will cause invalid data to be sent	
	wa_events = "";
		       
    // Set SC Vars to blank so no additional values are sent to SC
    waInitSCVars();
    
    // set values that can be sent in linkTrackVars 
   
    strLinkVars = "campaign,prop6,prop7,events,"
    strLinkVars = strLinkVars + "prop21,prop22,prop23,prop24,prop25,prop26,prop27,prop28,prop29,prop30,";
    strLinkVars = strLinkVars + "prop31,prop32,prop33,prop34,prop35,prop36,prop37,prop38,prop39,prop40,";
    strLinkVars = strLinkVars + "prop41,prop42,prop43,prop44,prop45,prop46,prop47,prop48,prop49,prop50,";   
    strLinkVars = strLinkVars + "eVar21,eVar22,eVar23,eVar24,eVar25,eVar26,eVar27,eVar28,eVar29,eVar30,";
    strLinkVars = strLinkVars + "eVar31,eVar32,eVar33,eVar34,eVar35,eVar36,eVar37,eVar38,eVar39,eVar40,";
    strLinkVars = strLinkVars + "eVar41,eVar42,eVar43,eVar44,eVar45,eVar46,eVar47,eVar48,eVar49,eVar50"
    s_linkTrackVars= strLinkVars;
    
     // set linkTrackEvents to 'None' - will be modified if any events are passed
    s_linkTrackEvents = 'None';
  	
 	// Validate link type; set link type to custom link type if not passed    
    waLinkType = waLinkType.toLowerCase();
    
    switch(waLinkType) {
    
    	case "d":
    	case "e":
    	case "o": 
    	break;
    	default: waLinkType = "o"    
    }
    
 	// assign and validate additional variables if any are passed
 	// proper format is: name=value&name=value
		
	  if (waSendVals != "None") {
	  
	  	var linkVars = waSendVals.split("&");
		
		for (var i=0;i<linkVars.length;i++){
		
			// Check for equal sign - if not present, then the format is invalid and will not process
			is_valid = linkVars[i].indexOf("=");
						
			if (is_valid == -1)
			{
				errorMsg = errorMsg + "Invalid format passed to custom link function; "; 				
			}
			else
			{
				// Validate length less than 96 chars
				
				var holdVals = linkVars[i].split("=");
			
				if (isValidLength(holdVals[1],96) == false)
    			{
    				errorMsg = errorMsg + holdVals[0] + "; ";
    			}					
		
				// assign passed wa_var values to SC vars
			
				holdVals[0] = holdVals[0].toLowerCase();				
		
				switch(holdVals[0]) {    
    						
    			
    			case "wa_campaign" : s_campaign = holdVals[1]; break;   			
    			case "wa_custom01" : s_prop21 = holdVals[1]; break;
				case "wa_custom02" : s_prop22 = holdVals[1]; break;
				case "wa_custom03" : s_prop23 = holdVals[1]; break;
				case "wa_custom04" : s_prop24 = holdVals[1]; break;
				case "wa_custom05" : s_prop25 = holdVals[1]; break;
				case "wa_custom06" : s_prop26 = holdVals[1]; break;
				case "wa_custom07" : s_prop27 = holdVals[1]; break;
				case "wa_custom08" : s_prop28 = holdVals[1]; break;
				case "wa_custom09" : s_prop29 = holdVals[1]; break;
				case "wa_custom10" : s_prop30 = holdVals[1]; break;
				case "wa_custom11" : s_prop31 = holdVals[1]; break;
				case "wa_custom12" : s_prop32 = holdVals[1]; break;
				case "wa_custom13" : s_prop33 = holdVals[1]; break;
				case "wa_custom14" : s_prop34 = holdVals[1]; break;
				case "wa_custom15" : s_prop35 = holdVals[1]; break;				    			
    			case "wa_custom36" : s_prop36 = holdVals[1]; break;
				case "wa_custom37" : s_prop37 = holdVals[1]; break;
				case "wa_custom38" : s_prop38 = holdVals[1]; break;
				case "wa_custom39" : s_prop39 = holdVals[1]; break;
				case "wa_custom40" : s_prop40 = holdVals[1]; break;
				case "wa_custom41" : s_prop41 = holdVals[1]; break;
				case "wa_custom42" : s_prop42 = holdVals[1]; break;
				case "wa_custom43" : s_prop43 = holdVals[1]; break;
				case "wa_custom44" : s_prop44 = holdVals[1]; break;
				case "wa_custom46" : s_prop46 = holdVals[1]; break;
    			case "wa_custom47" : s_prop47 = holdVals[1]; break;
				case "wa_custom48" : s_prop48 = holdVals[1]; break;
				case "wa_custom49" : s_prop49 = holdVals[1]; break;
    			case "wa_custom50" : s_prop50 = holdVals[1]; break;    			
				case "wa_ecustom21" : s_eVar21 = holdVals[1]; break;
				case "wa_ecustom22" : s_eVar22 = holdVals[1]; break;
				case "wa_ecustom23" : s_eVar23 = holdVals[1]; break;
				case "wa_ecustom24" : s_eVar24 = holdVals[1]; break;
				case "wa_ecustom25" : s_eVar25 = holdVals[1]; break;
				case "wa_ecustom26" : s_eVar26 = holdVals[1]; break;
				case "wa_ecustom27" : s_eVar27 = holdVals[1]; break;
				case "wa_ecustom28" : s_eVar28 = holdVals[1]; break;
				case "wa_ecustom29" : s_eVar29 = holdVals[1]; break;
				case "wa_ecustom30" : s_eVar30 = holdVals[1]; break;
				case "wa_ecustom31" : s_eVar31 = holdVals[1]; break;
				case "wa_ecustom32" : s_eVar32 = holdVals[1]; break;
				case "wa_ecustom33" : s_eVar33 = holdVals[1]; break;
				case "wa_ecustom34" : s_eVar34 = holdVals[1]; break;
				case "wa_ecustom35" : s_eVar35 = holdVals[1]; break;				
				case "wa_ecustom36" : s_eVar36 = holdVals[1]; break;
				case "wa_ecustom37" : s_eVar37 = holdVals[1]; break;
				case "wa_ecustom38" : s_eVar38 = holdVals[1]; break;
				case "wa_ecustom39" : s_eVar39 = holdVals[1]; break;
				case "wa_ecustom40" : s_eVar40 = holdVals[1]; break;
				case "wa_ecustom41" : s_eVar41 = holdVals[1]; break;
				case "wa_ecustom42" : s_eVar42 = holdVals[1]; break;
				case "wa_ecustom43" : s_eVar43 = holdVals[1]; break;
				case "wa_ecustom44" : s_eVar44 = holdVals[1]; break;
				case "wa_ecustom45" : s_eVar45 = holdVals[1]; break; 
				case "wa_ecustom46" : s_eVar46 = holdVals[1]; break;
    			case "wa_ecustom47" : s_eVar47 = holdVals[1]; break;
    			case "wa_ecustom48" : s_eVar48 = holdVals[1]; break;	
    			case "wa_ecustom49" : s_eVar49 = holdVals[1]; break;
    			case "wa_ecustom50" : s_eVar50 = holdVals[1]; break;   			
    			case "wa_iid" : s_prop6 = holdVals[1]; break;
    			case "wa_reportsuites" : wa_reportSuites = holdVals[1]; break;
    			case "wa_events" : 	waProcessEvents(holdVals[1],'N'); s_events = wa_events; break;
    			default : errorMsg = errorMsg + "Invalid var: " + holdVals[0] + "; ";  	
				}    			
		}	
      }
    }
    
    else
   	{
    	s_linkTrackVars='None';	    		
	}
	
	// set error if Flash link name not defined in flash
	if ((waCalledBy == "FlashLink") && (waLinkName == ""))
	{
		waLinkName = wa_org1 + ":Flash Unassigned Link Name";
		errorMsg = errorMsg + "Flash Unassigned Link Name; ";
	}	  
	
	// set report suite
	if (errorMsg != "")
	{		
		s_prop45 = ("Validation failed: " + errorMsg);		
		accnt = wa_errorRS;	
	}
	else
	{	
		// Comment out next line for production version
		// accnt = wa_intelCorpRS;
		// Uncomment next line for production version
		accnt = (wa_reportSuites == "") ? wa_intelCorpRS : wa_reportSuites + "," + wa_intelCorpRS;
	}
	
	if (s_events != "")
	{
		s_linkTrackEvents = s_events;
	}
		 
    s_linkType=waLinkType; 
    s_linkName=wa_org1 + ":" + waLinkName;
    
    // Set s_lnk var based on whether it is called by the custom link or a flash link functions  
   if (waCalledBy == 'CustomLink')
   {
   		s_lnk=s_co(waURL); 
   	}
   	else
   	{
   		s_lnk = true;
   	}    
      
    s_gs(accnt);  
    
    var date = new Date();
	var curDate = null;
	
	do { curDate = new Date(); } 
	while(curDate-date < 2000);
    
}

// Function is called using an onClick event on an anchor tag on a page
function waCustomLink(cUrl,cLinkName,cLinkType,cSendVals) {  
   
   	if	(cLinkName == "")
   	{
			cLinkName = cUrl;		
   	}
   		
    if (cSendVals == "")
   	{
   		cSendVals = "None"
   	}
   		
   	waProcessLink(cUrl,cLinkName,cLinkType,cSendVals,'CustomLink');
}   

// Function is used for Rich Internet Applications and is called from an actionscript  
function waTrackAsLink(rLinkName,rLinkType,rSendVals,limitExceeded) {     

   	// set url to blank to pass to waProcessLink function - it is not valid for the Flash usage
   	url = ""; 
   	
   	// Blank out error traffic variable prop45
   	s_prop45 = "";
   	
  	if (rSendVals == "")
   	{
   		rSendVals = "None"   		
   	}
   	
	// if more than 508 characters were sent or the link name is blank - send to error report suite
	
	if (rLinkName == "") {
	
		rLinkName = "Flash Unassigned Link Name";
		s_prop45 = "Error: Flash Unassigned Link Name; "
	}
	
	if (limitExceeded == "Y") {
		s_prop45 = "Error: Flash Name/Value string Exceeded 508 characters";
	}
		
	if (s_prop45 != "") 
	{	
	    		
		switch(rLinkType) {    
    	case "d":
    	case "e":
    	case "o": 
    	break;
    	default: rLinkType = "o"    
   		}
	
		accnt = wa_errorRS;
		s_linkTrackVars='prop45'
		s_linkTrackEvents='None';
    	s_linkType = rLinkType;     	
   		s_linkName = wa_org1 + ":" + rLinkName;
    	s_lnk=true;
    	s_gs(accnt);

	}
	else 
	{	
    	waProcessLink(url,rLinkName,rLinkType,rSendVals,'FlashLink');
    }
}   

// Function is used for Rich Internet Applications and is called from an actionscript
// Sends as a page type event

function waTrackAsPage(pgName,sendVals,limitExceeded) {

// Values passed to rSendVals parameter are defined in the flash file in a name/value pair format:
// "&wa_custom06=flash 06&wa_eCustom22=ecustom 22 value";

// limitExceeded parameter tracks whether the limit of 508 characters was exceeded in the flash getURL call
// If it was, data is sent to the error suite

// set report suite to what is set on page

// Comment out next line for production version
// accnt = wa_intelCorpRS;

// Uncomment next line for production version
accnt = (wa_reportSuites == "") ? wa_intelCorpRS : wa_reportSuites + "," + wa_intelCorpRS;

// Initialize error message var
errorFound = "";

// Set SC Variables to blank
waInitSCVars();

// blank out wa vars that will not be pulled from values that were set on the html page
// that contains the flash file

wa_pageName = "";
wa_iid = "";
wa_visitId = "";
wa_ngipDocId = "";
wa_ngipUniqueId = "";
wa_events="";
wa_profileID = "";
wa_custom01 = "";
wa_custom02 = "";
wa_custom03 = "";
wa_custom04 = "";
wa_custom05 = "";
wa_custom06 = "";
wa_custom07 = "";
wa_custom08 = "";
wa_custom09 = "";
wa_custom10 = "";
wa_custom11 = "";
wa_custom12 = "";
wa_custom13 = "";
wa_custom14 = "";
wa_custom15 = "";
wa_custom36 = "";
wa_custom37 = "";
wa_custom38 = "";
wa_custom39 = "";
wa_custom40 = "";
wa_custom41 = "";
wa_custom42 = "";
wa_custom43 = "";
wa_custom44 = "";
wa_custom46 = "";
wa_custom47 = "";
wa_custom48 = "";
wa_custom49 = "";
wa_custom50 = "";
wa_eCustom21 = "";
wa_eCustom22 = "";
wa_eCustom23 = "";
wa_eCustom24 = "";
wa_eCustom25 = "";
wa_eCustom26 = "";
wa_eCustom27 = "";
wa_eCustom28 = "";
wa_eCustom29 = "";
wa_eCustom30 = "";
wa_eCustom31 = "";
wa_eCustom32 = "";
wa_eCustom33 = "";
wa_eCustom34 = "";
wa_eCustom35 = "";
wa_eCustom36 = "";
wa_eCustom37 = "";
wa_eCustom38 = "";
wa_eCustom39 = "";
wa_eCustom40 = "";
wa_eCustom41 = "";
wa_eCustom42 = "";
wa_eCustom43 = "";
wa_eCustom44 = "";
wa_eCustom45 = "";	
wa_eCustom46 = "";
wa_eCustom47 = "";
wa_eCustom48 = "";
wa_eCustom49 = "";
wa_eCustom50 = "";

// Check for page name - this is a required field
if (pgName == "")
	{
		wa_pageName = "Flash Unassigned Page Name"
		errorFound = "No Flash Page Name Defined; ";
	}	
else
	{
		wa_pageName = pgName;	
	}

// Check is length of sendvals was exceeded - this is an error
 if (limitExceeded == "Y") 
{
	errorFound = errorFound + "Flash Name/Value string Exceeded 508 characters";		
}  

if (errorFound != "") 

{
	accnt = wa_errorRS;
	s_pageName = wa_org1 + ":" + wa_pageName;
	s_prop45 = errorFound;
}

else
{

// process additional variables set in Flash
// format is "name=value&name=value"	
if (sendVals != "") {

	// splits string at the '&' which results in array of name=value
	var pageVals = sendVals.split("&");

 	for (var i=0;i<pageVals.length;i++){
	
		// check for valid format (name=value) - if not valid, throw error and don't process
		is_valid = pageVals[i].indexOf("=");			
		
		if (is_valid == -1)
		{
			errorFound = errorFound + "Invalid format passed to waTrackAsPage function; "; 
		}
		else
		{		
			// split name=value pair and validate
			var holdVals = pageVals[i].split("=");		
			holdVals[0] = holdVals[0].toLowerCase();
			
			switch(holdVals[0]) {
		
			case "wa_events" : wa_events = holdVals[1]; break;			
			case "wa_custom01" : wa_custom01 = holdVals[1]; break;
			case "wa_custom02" : wa_custom02 = holdVals[1]; break;
			case "wa_custom03" : wa_custom03 = holdVals[1]; break;
			case "wa_custom04" : wa_custom04 = holdVals[1]; break;
			case "wa_custom05" : wa_custom05 = holdVals[1]; break;
			case "wa_custom06" : wa_custom06 = holdVals[1]; break;
			case "wa_custom07" : wa_custom07 = holdVals[1]; break;
			case "wa_custom08" : wa_custom08 = holdVals[1]; break;
			case "wa_custom09" : wa_custom09 = holdVals[1]; break;
			case "wa_custom10" : wa_custom10 = holdVals[1]; break;
			case "wa_custom11" : wa_custom11 = holdVals[1]; break;
			case "wa_custom12" : wa_custom12 = holdVals[1]; break;
			case "wa_custom13" : wa_custom13 = holdVals[1]; break;
			case "wa_custom14" : wa_custom14 = holdVals[1]; break;
			case "wa_custom15" : wa_custom15 = holdVals[1]; break;			
			case "wa_custom36" : wa_custom36 = holdVals[1]; break;
			case "wa_custom37" : wa_custom37 = holdVals[1]; break;
			case "wa_custom38" : wa_custom38 = holdVals[1]; break;
			case "wa_custom39" : wa_custom39 = holdVals[1]; break;
			case "wa_custom40" : wa_custom40 = holdVals[1]; break;
			case "wa_custom41" : wa_custom41 = holdVals[1]; break;
			case "wa_custom42" : wa_custom42 = holdVals[1]; break;
			case "wa_custom43" : wa_custom43 = holdVals[1]; break;
			case "wa_custom44" : wa_custom44 = holdVals[1]; break;			
			case "wa_custom46" : wa_custom46 = holdVals[1]; break;
			case "wa_custom47" : wa_custom47 = holdVals[1]; break;
			case "wa_custom48" : wa_custom48 = holdVals[1]; break;
			case "wa_custom49" : wa_custom49 = holdVals[1]; break;
			case "wa_custom50" : wa_custom50 = holdVals[1]; break;			
			case "wa_ecustom21" : wa_eCustom21 = holdVals[1]; break;
			case "wa_ecustom22" : wa_eCustom22 = holdVals[1]; break;
			case "wa_ecustom23" : wa_eCustom23 = holdVals[1]; break;
			case "wa_ecustom24" : wa_eCustom24 = holdVals[1]; break;
			case "wa_ecustom25" : wa_eCustom25 = holdVals[1]; break;
			case "wa_ecustom26" : wa_eCustom26 = holdVals[1]; break;
			case "wa_ecustom27" : wa_eCustom27 = holdVals[1]; break;
			case "wa_ecustom28" : wa_eCustom28 = holdVals[1]; break;
			case "wa_ecustom29" : wa_eCustom29 = holdVals[1]; break;
			case "wa_ecustom30" : wa_eCustom30 = holdVals[1]; break;
			case "wa_ecustom31" : wa_eCustom31 = holdVals[1]; break;
			case "wa_ecustom32" : wa_eCustom32 = holdVals[1]; break;
			case "wa_ecustom33" : wa_eCustom33 = holdVals[1]; break;
			case "wa_ecustom34" : wa_eCustom34 = holdVals[1]; break;
			case "wa_ecustom35" : wa_eCustom35 = holdVals[1]; break;			
			case "wa_ecustom36" : wa_ecustom36 = holdVals[1]; break;
			case "wa_ecustom37" : wa_ecustom37 = holdVals[1]; break;
			case "wa_ecustom38" : wa_ecustom38 = holdVals[1]; break;
			case "wa_ecustom39" : wa_ecustom39 = holdVals[1]; break;
			case "wa_ecustom40" : wa_ecustom40 = holdVals[1]; break;
			case "wa_ecustom41" : wa_ecustom41 = holdVals[1]; break;
			case "wa_ecustom42" : wa_ecustom42 = holdVals[1]; break;
			case "wa_ecustom43" : wa_ecustom43 = holdVals[1]; break;
			case "wa_ecustom44" : wa_ecustom44 = holdVals[1]; break;
			case "wa_ecustom45" : wa_ecustom45 = holdVals[1]; break;
			case "wa_ecustom46" : wa_ecustom46 = holdVals[1]; break;
			case "wa_ecustom47" : wa_ecustom47 = holdVals[1]; break;
			case "wa_ecustom48" : wa_ecustom48 = holdVals[1]; break;
			case "wa_ecustom49" : wa_ecustom49 = holdVals[1]; break;
			case "wa_ecustom50" : wa_ecustom50 = holdVals[1]; break;
			default : errorFound = errorFound + "Invalid var: " + holdVals[0] + "; ";		
		}
	  }
	 }
	}
	
	// call main processing function	
	waProcess();		
	
	// If no errors found in waProcess function call, but errors found in this function, then 
	// initialize s_prop45 and add errors var:
	if ((s_prop45 == "") && (errorFound != ""))
		{
			s_prop45 = "Validation failed: " + errorFound;
			accnt = wa_errorRS;
		}
	// if errors were found by waProcess function call and also in this function, then 
	// only append to existing errors	
	else if ((s_prop45 != "") && (errorFound != ""))
		{
			s_prop45 = s_prop45 + "; " + errorFound	
			accnt = wa_errorRS;		
		}

	
	}
	
	
	s_gs(accnt);
}


/***** End WA Code *****/


/* Plugin Config */
var s_usePlugins=true
function s_doPlugins() {

    /* Add calls to plugins here */
 
	// Perform setValOnce function for s_campaign so only track once in a session

	s_vp_setValOnce('s_campaign');
	
    // capture DARTmail tracking code

    s_vp_getCGI("s_sssdmh","sssdmh");

    if (s_vp_getValue("s_sssdmh")) {

        s_vpr("s_campaign",s_vp_getValue("s_sssdmh"))     
    

    }
}



/************************** PLUGINS SECTION *************************/
/* You may insert any plugins you wish to use here.                 */
/*
 * Plugin: Get Plugin Modified Value
 */
function s_vp_getValue(vs)
	{var k=vs.substring(0,2)=='s_'?vs.substring(2):vs;return s_wd[
	's_vpm_'+k]?s_wd['s_vpv_'+k]:s_gg(k)}
/*
 * Plugin: Get Query String CGI Variable Value
 */
function s_vp_getCGI(vs,k)
	{var v='';if(k&&s_wd.location.search){var q=s_wd.location.search,
	qq=q.indexOf('?');q=qq<0?q:q.substring(qq+1);v=s_pt(q,'&',s_cgif,
	k.toLowerCase())}s_vpr(vs,v)}function s_cgif(t,k){if(t){var te=
	t.indexOf('='),sk=te<0?t:t.substring(0,te),sv=te<0?'True':
	t.substring(te+1);if(sk.toLowerCase()==k)return s_epa(sv)}
	return ''}
/*
 * Plugin: Set variable to value only once per session
 */
function s_vp_setValOnce(vs)
	{var cn='s_p1_'+vs,vsk=s_c_r(cn),vsv=s_vp_getValue(vs)
	if(vsv){vsv==vsk?s_vpr(vs,''):s_c_w(cn,vsv,0)}}

/*
 * Plugin: Get Value From Cookie
 */
function s_vp_getCookie(vs,k)
	{s_vpr(vs,s_c_r(k))}
/*
 * Plugin Utilities v2.0 (Required For All Plugins)
 */
function s_vpr(vs,v){if(s_wd[vs])s_wd[vs]=s_wd[vs];else s_wd[vs]=''
if(vs.substring(0,2) == 's_')vs=vs.substring(2);s_wd['s_vpv_'+vs]=v
s_wd['s_vpm_'+vs]=1}function s_dt(tz,t){var d=new Date;if(t)d.setTime(
t);d=new Date(d.getTime()+(d.getTimezoneOffset()*60*1000))
return new Date(Math.floor(d.getTime()+(tz*60*60*1000)))}
function s_vh_gt(k,v){var vh='|'+s_c_r('s_vh_'+k),vi=vh.indexOf('|'+v
+'='),ti=vi<0?vi:vi+2+v.length,pi=vh.indexOf('|',ti),t=ti<0?'':
vh.substring(ti,pi<0?vh.length:pi);return t}function s_vh_gl(k){var
vh=s_c_r('s_vh_'+k),e=vh?vh.indexOf('='):0;return vh?(vh.substring(0,
e?e:vh.length)):''}function s_vh_s(k,v){if(k&&v){var e=new Date,st=
e.getTime(),y=e.getYear(),c='s_vh_'+k,vh='|'+s_c_r(c)+'|',t=s_vh_gt(k,
v);e.setYear((y<1900?y+1900:y)+5);if(t)vh=s_rep(vh,'|'+v+'='+t+'|','|'
);if(vh.substring(0,1)=='|')vh=vh.substring(1);if(vh.substring(
vh.length-1,vh.length)=='|')vh=vh.substring(0,vh.length-1);vh=v
+'=[PCC]'+(vh?'|'+vh:'');s_c_w(c,vh,e);if(s_vh_gt(k,v)!='[PCC]')
return 0;vh=s_rep(vh,'[PCC]',st);s_c_w(c,vh,e)}return 1}

s_vmk="44FF1759"

/************* DO NOT ALTER ANYTHING BELOW THIS LINE ! **************/
var s_linkType,s_linkName,s_objectID,s_un,s_ios=0,s_q='',s_code='',
code='',s_bcr=0,s_lnk='',s_eo='',s_vb,s_pl,s_tfs=0,s_etfs=0,s_wd=
window,s_d=s_wd.document,s_ssl=(s_wd.location.protocol.toLowerCase(
).indexOf('https')>=0),s_n=navigator,s_u=s_n.userAgent,s_apn=
s_n.appName,s_v=s_n.appVersion,s_apv,s_i,s_ie=s_v.indexOf('MSIE '),
s_ns6=s_u.indexOf('Netscape6/'),s_em=0;if(s_v.indexOf('Opera')>=0||
s_u.indexOf('Opera')>=0)s_apn='Opera';var s_isie=(s_apn==
'Microsoft Internet Explorer'),s_isns=(s_apn=='Netscape'),s_isopera=(
s_apn=='Opera'),s_ismac=(s_u.indexOf('Mac')>=0);if(s_ie>0){s_apv=
parseInt(s_i=s_v.substring(s_ie+5));if(s_apv>3)s_apv=parseFloat(s_i)}
else if(s_ns6>0)s_apv=parseFloat(s_u.substring(s_ns6+10));else s_apv=
parseFloat(s_v);if(String.fromCharCode){s_i=escape(
String.fromCharCode(256)).toUpperCase();s_em=(s_i=='%C4%80'?2:(s_i==
'%U0100'?1:0))}function s_fl(s,l){return s?(s+'').substring(0,l):s}
function s_co(o){if(!o)return o;var n=new Object,x;for(x in o)if(
x.indexOf("select")<0&&x.indexOf("filter")<0)n[x]=o[x];return n}
function s_num(x){var s=x.toString(),g='0123456789',p,d
for(p=0;p<s.length;p++){d=s.substring(p,p+1);if(g.indexOf(d)<0)
return 0}return 1}function s_rep(s,o,n){var i=s.indexOf(o),l=n.length>
0?n.length:1;while(s&&i>=0){s=s.substring(0,i)+n+s.substring(i
+o.length);i=s.indexOf(o,i+l)}return s}function s_ape(x){var i;x=x?
s_rep(escape(''+x),'+','%2B'):x;if(x&&s_gg('charSet')&&s_em==1&&
x.indexOf('%u')<0&&x.indexOf('%U')<0){i=x.indexOf('%');while(i>=0){i++
if(('89ABCDEFabcdef').indexOf(x.substring(i,i+1))>=0)
return x.substring(0,i)+'u00'+x.substring(i);i=x.indexOf('%',i)}}
return x}function s_epa(s){return s?unescape(s_rep(''+s,'+',' ')):s}
function s_pt(s,d,f,a){var t=s,x=0,y,r;while(t){y=t.indexOf(d);y=y<0?
t.length:y;t=t.substring(0,y);r=f(t,a);if(r)return r;x+=y+d.length;t=
s.substring(x,s.length);t=x<s.length?t:''}return ''}function s_isf(t,a
){var c=a.indexOf(':');if(c>=0)a=a.substring(0,c);if(t.substring(0,2
)=='s_')t=t.substring(2);return (t!=''&&t==a)}function s_fsf(t,a){if(
s_pt(a,',',s_isf,t))s_fsg+=(s_fsg!=''?',':'')+t;return 0}var s_fsg
function s_fs(s,f){s_fsg='';s_pt(s,',',s_fsf,f);return s_fsg}var
s_c_d='';function s_c_gdf(t,a){if(!s_num(t))return 1;return 0}
function s_c_gd(){var d=s_wd.location.hostname,n=s_gg(
'cookieDomainPeriods'),p;if(d&&!s_c_d){n=n?parseInt(n):2;n=n>2?n:2;p=
d.lastIndexOf('.');while(p>=0&&n>1){p=d.lastIndexOf('.',p-1);n--}
s_c_d=p>0&&s_pt(d,'.',s_c_gdf,0)?d.substring(p):''}return s_c_d}
function s_c_r(k){k=s_ape(k);var c=' '+s_d.cookie,s=c.indexOf(' '+k
+'='),e=s<0?s:c.indexOf(';',s),v=s<0?'':s_epa(c.substring(s+2
+k.length,e<0?c.length:e));return v!='[[B]]'?v:''}function s_c_w(k,v,e
){var d=s_c_gd(),l=s_gg('cookieLifetime'),s;v=''+v;l=l?(''+l
).toUpperCase():'';if(e&&l!='SESSION'&&l!='NONE'){s=(v!=''?parseInt(l?
l:0):-60);if(s){e=new Date;e.setTime(e.getTime()+(s*1000))}}if(k&&l!=
'NONE'){s_d.cookie=k+'='+s_ape(v!=''?v:'[[B]]')+'; path=/;'+(e&&l!=
'SESSION'?' expires='+e.toGMTString()+';':'')+(d?' domain='+d+';':'')
return s_c_r(k)==v}return 0}function s_cet(f,a,et,oe,fb){var r,d=0
/*@cc_on@if(@_jscript_version>=5){try{return f(a)}catch(e){return et(e)}d=1}@end@*/
if(
!d){if(s_ismac&&s_u.indexOf('MSIE 4')>=0)return fb(a);else{s_wd.s_oe=
s_wd.onerror;s_wd.onerror=oe;r=f(a);s_wd.onerror=s_wd.s_oe;return r}}}
function s_gtfset(e){return s_tfs}function s_gtfsoe(e){s_wd.onerror=
s_wd.s_oe;s_etfs=1;var code=s_gs(s_un);if(code)s_d.write(code);s_etfs=
0;return true}function s_gtfsfb(a){return s_wd}function s_gtfsf(w){var
p=w.parent,l=w.location;s_tfs=w;if(p&&p.location!=l&&p.location.host==
l.host){s_tfs=p;return s_gtfsf(s_tfs)}return s_tfs}function s_gtfs(){
if(!s_tfs){s_tfs=s_wd;if(!s_etfs)s_tfs=s_cet(s_gtfsf,s_tfs,s_gtfset,
s_gtfsoe,s_gtfsfb)}return s_tfs}function s_ca(un){un=un.toLowerCase()
var ci=un.indexOf(','),fun=ci<0?un:un.substring(0,ci),imn='s_i_'+fun
if(s_d.images&&s_apv>=3&&!s_isopera&&(s_ns6<0||s_apv>=6.1)){s_ios=1
if(!s_d.images[imn]&&(!s_isns||(s_apv<4||s_apv>=5))){s_d.write('<im'
+'g name="'+imn+'" height=1 width=1 border=0 alt="">');if(!s_d.images[
imn])s_ios=0}}}function s_it(un){s_ca(un)}function s_mr(un,sess,q,ta){
un=un.toLowerCase();var ci=un.indexOf(','),fun=ci<0?un:un.substring(0,
ci),unc=s_rep(fun,'_','-'),imn='s_i_'+fun,ns=s_gg('visitorNamespace'),
im,b,e,rs='http'+(s_ssl?'s':'')+'://'+(s_ssl?'www90':'www91')+'.intel.com/b/ss/'+un+'/1/G.9p2/'
+sess+'?[AQB]&ndh=1'+(q?q:'')+(s_q?s_q:'')+'&[AQE]';if(s_ios){im=s_wd[
imn]?s_wd[imn]:s_d.images[imn];if(!im)im=s_wd[imn]=new Image;im.src=rs
if(rs.indexOf('&pe=')>=0&&(!ta||ta=='_self'||ta=='_top'||(s_wd.name&&
ta==s_wd.name))){b=e=new Date;while(e.getTime()-b.getTime()<500)e=
new Date}return ''}return '<im'+'g sr'+'c="'+rs
+'" width=1 height=1 border=0 alt="">'}function s_gg(v){var g='s_'+v
return s_wd[g]||s_wd.s_disableLegacyVars?s_wd[g]:s_wd[v]}
function s_gv(v){return s_wd['s_vpm_'+v]?s_wd['s_vpv_'+v]:s_gg(v)}var
s_qav='';function s_havf(t,a){var b=t.substring(0,4),s=t.substring(4),
n=parseInt(s),k='s_g_'+t,m='s_vpm_'+t,q=t,v=s_gg('linkTrackVars'),e=
s_gg('linkTrackEvents');if(!s_wd['s_'+t])s_wd['s_'+t]='';s_wd[k]=s_gv(
t);if(s_lnk||s_eo){v=v?v+',pageName,pageURL,referrer,vmk,charSet,visi'
+'torNamespace,cookieDomainPeriods,cookieLifetime,currencyCode,purcha'
+'seID':'';if(v&&!s_pt(v,',',s_isf,t))s_wd[k]='';if(t=='events'&&e)
s_wd[k]=s_fs(s_wd[k],e)}s_wd[m]=0;if(t=='pageURL')q='g';else if(t==
'referrer')q='r';else if(t=='vmk')q='vmt';else if(t=='charSet'){q='ce'
if(s_wd[k]&&s_em==2)s_wd[k]='UTF-8'}else if(t=='visitorNamespace')q=
'ns';else if(t=='cookieDomainPeriods')q='cdp';else if(t==
'cookieLifetime')q='cl';else if(t=='currencyCode')q='cc';else if(t==
'channel')q='ch';else if(t=='campaign')q='v0';else if(s_num(s)){if(b==
'prop')q='c'+n;else if(b=='eVar')q='v'+n;else if(b=='hier'){q='h'+n
s_wd[k]=s_fl(s_wd[k],255)}}if(s_wd[k]&&t!='linkName'&&t!='linkType')
s_qav+='&'+q+'='+s_ape(s_wd[k]);return ''}function s_hav(){var n,av=
'vmk,charSet,visitorNamespace,cookieDomainPeriods,cookieLifetime,page'
+'Name,pageURL,referrer,channel,server,pageType,campaign,state,zip,ev'
+'ents,products,currencyCode,purchaseID,linkName,linkType'
for(n=1;n<51;n++)av+=',prop'+n+',eVar'+n+',hier'+n;s_qav='';s_pt(av,
',',s_havf,0);return s_qav}function s_lnf(t,h){t=t?
t.toLowerCase():'';h=h?h.toLowerCase():'';var te=t.indexOf('=');if(t&&
te>0&&h.indexOf(t.substring(te+1))>=0)return t.substring(0,te)
return ''}function s_ln(h){if(s_gg('linkNames'))return s_pt(s_gg(
'linkNames'),',',s_lnf,h);return ''}function s_ltdf(t,h){t=t?
t.toLowerCase():'';h=h?h.toLowerCase():'';var qi=h.indexOf('?');h=qi>=
0?h.substring(0,qi):h;if(t&&h.substring(h.length-(t.length+1))=='.'+t)
return 1;return 0}function s_ltef(t,h){t=t?t.toLowerCase():'';h=h?
h.toLowerCase():'';if(t&&h.indexOf(t)>=0)return 1;return 0}
function s_lt(h){var lft=s_gg('linkDownloadFileTypes'),lef=s_gg(
'linkExternalFilters'),lif=s_gg('linkInternalFilters')?s_gg(
'linkInternalFilters'):s_wd.location.hostname;h=h.toLowerCase();if(
s_gg('trackDownloadLinks')&&lft&&s_pt(lft,',',s_ltdf,h))return 'd';if(
s_gg('trackExternalLinks')&&(lef||lif)&&(!lef||s_pt(lef,',',s_ltef,h)
)&&(!lif||!s_pt(lif,',',s_ltef,h)))return 'e';return ''}function s_lc(
e){s_lnk=s_co(this);s_gs('');s_lnk='';if(this.s_oc)return this.s_oc(e)
return true}function s_ls(){var l,ln,oc
for(ln=0;ln<s_d.links.length;ln++){l=s_d.links[ln];oc=l.onclick?
l.onclick.toString():'';if(oc.indexOf("s_gs(")<0&&oc.indexOf("s_lc(")<
0){l.s_oc=l.onclick;l.onclick=s_lc}}}function s_bc(e){s_eo=
e.srcElement?e.srcElement:e.target;s_gs('');s_eo=''}function s_ot(o){
var a=o.type,b=o.tagName;return (a&&a.toUpperCase?a:b&&b.toUpperCase?b
:o.href?'A':'').toUpperCase()}function s_oid(o){var t=s_ot(o),p=
o.protocol,c=o.onclick,n='',x=0;if(!o.s_oid){if(o.href&&(t=='A'||t==
'AREA')&&(!c||!p||p.toLowerCase().indexOf('javascript')<0))n=o.href
else if(c){n=s_rep(s_rep(s_rep(s_rep(c.toString(),"\r",''),"\n",''),
"\t",''),' ','');x=2}else if(o.value&&(t=='INPUT'||t=='SUBMIT')){n=
o.value;x=3}else if(o.src&&t=='IMAGE')n=o.src;if(n){o.s_oid=s_fl(n,100
);o.s_oidt=x}}return o.s_oid}function s_rqf(t,un){var e=t.indexOf('='
),u=e>=0?','+t.substring(0,e)+',':'';return u&&u.indexOf(','+un+',')>=
0?s_epa(t.substring(e+1)):''}function s_rq(un){var c=un.indexOf(','),
v=s_c_r('s_sq'),q='';if(c<0)return s_pt(v,'&',s_rqf,un);return s_pt(
un,',',s_rq,0)}var s_sqq,s_squ;function s_sqp(t,a){var e=t.indexOf('='
),q=e<0?'':s_epa(t.substring(e+1));s_sqq[q]='';if(e>=0)s_pt(
t.substring(0,e),',',s_sqs,q);return 0}function s_sqs(un,q){s_squ[un]=
q;return 0}function s_sq(un,q){s_sqq=new Object;s_squ=new Object
s_sqq[q]='';var k='s_sq',v=s_c_r(k),x,c=0;s_pt(v,'&',s_sqp,0);s_pt(un,
',',s_sqs,q);v='';for(x in s_squ)s_sqq[s_squ[x]]+=(s_sqq[s_squ[x]]?','
:'')+x;for(x in s_sqq)if(x&&s_sqq[x]&&(x==q||c<2)){v+=(v?'&':'')
+s_sqq[x]+'='+s_ape(x);c++}return s_c_w(k,v,0)}function s_wdl(e){
s_wd.s_wd_l=1;var r=true;if(s_wd.s_ol)r=s_wd.s_ol(e);if(s_wd.s_ls)
s_wd.s_ls();return r}function s_wds(un){un=un.toLowerCase()
s_wd.s_wd_l=1;if(s_apv>3&&(!s_isie||!s_ismac||s_apv>=5)){s_wd.s_wd_l=0
if(!s_wd.s_unl)s_wd.s_unl=new Array;s_wd.s_unl[s_wd.s_unl.length]=un
if(s_d.body&&s_d.body.attachEvent){if(!s_wd.s_bcr&&
s_d.body.attachEvent('onclick',s_bc))s_wd.s_bcr=1}else if(s_d.body&&
s_d.body.addEventListener){if(!s_wd.s_bcr&&s_d.body.addEventListener(
'click',s_bc,false))s_wd.s_bcr=1}else{if(!s_wd.s_olr){s_wd.s_ol=
s_wd.onload;s_wd.onload=s_wdl}s_wd.s_olr=1}}}function s_iepf(i,a){if(
i.substring(0,1)!='{')i='{'+i+'}';if(s_d.body.isComponentInstalled(i,
'ComponentID')){var n=s_pl.length;s_pl[n]=new Object;s_pl[n].name=i
+':'+s_d.body.getComponentVersion(i,'ComponentID')}return 0}
function s_vs(un,x){var s=s_gg('visitorSampling'),g=s_gg(
'visitorSamplingGroup'),k='s_vsn_'+un+(g?'_'+g:''),n=s_c_r(k),e=
new Date,y=e.getYear();e.setYear(y+10+(y<1900?1900:0));if(s){s*=100
if(!n){if(!s_c_w(k,x,e))return 0;n=x}if(n%10000>s)return 0}return 1}
function s_dyasmf(t,m){if(t&&m&&m.indexOf(t)>=0)return 1;return 0}
function s_dyasf(t,m){var i=t?t.indexOf('='):-1,un,s;if(i>=0&&m){var
un=t.substring(0,i),s=t.substring(i+1);if(s_pt(s,',',s_dyasmf,m))
return un}return 0}function s_dyas(un,l,m){if(!m)m=s_wd.location.host
if(!m.toLowerCase)m=m.toString();l=l.toLowerCase();m=m.toLowerCase()
var nun=s_pt(l,';',s_dyasf,m);if(nun)return nun;return un}
function s_gs(un){un=un.toLowerCase();var dyas=s_gg(
'dynamicAccountSelection'),dyal=s_gg('dynamicAccountList'),dyam=s_gg(
'dynamicAccountMatch');if(dyas&&dyal)un=s_dyas(un,dyal,dyam);s_un=un
var trk=1,tm=new Date,sed=Math&&Math.random?Math.floor(Math.random()
*10000000000000):tm.getTime(),sess='s'+Math.floor(tm.getTime()/
10800000)%10+sed,yr=tm.getYear(),vt=tm.getDate()+'/'+tm.getMonth()+'/'
+(yr<1900?yr+1900:yr)+' '+tm.getHours()+':'+tm.getMinutes()+':'
+tm.getSeconds()+' '+tm.getDay()+' '+tm.getTimezoneOffset(),tfs=
s_gtfs(),vt,ta='',q='',qs='';if(!s_q){var tl=tfs.location,s='',c='',v=
'',p='',bw='',bh='',j='1.0',k=s_c_w('s_cc','true',0)?'Y':'N',hp='',ct=
'',iepl=s_gg('iePlugins'),pn=0,ps;if(s_apv>=4)s=screen.width+'x'
+screen.height;if(s_isns||s_isopera){if(s_apv>=3){j='1.1';v=
s_n.javaEnabled()?'Y':'N';if(s_apv>=4){j='1.2';c=screen.pixelDepth;bw=
s_wd.innerWidth;bh=s_wd.innerHeight;if(s_apv>=4.06)j='1.3'}}s_pl=
s_n.plugins}else if(s_isie){if(s_apv>=4){v=s_n.javaEnabled()?'Y':'N'
j='1.2';c=screen.colorDepth;if(s_apv>=5){bw=
s_d.documentElement.offsetWidth;bh=s_d.documentElement.offsetHeight;j=
'1.3';if(!s_ismac&&s_d.body){s_d.body.addBehavior("#default#homePage")
hp=s_d.body.isHomePage(tl)?"Y":"N";s_d.body.addBehavior(
"#default#clientCaps");ct=s_d.body.connectionType;if(iepl){s_pl=
new Array;s_pt(iepl,',',s_iepf,'')}}}}else r='';if(!s_pl&&iepl)s_pl=
s_n.plugins}if(s_pl)while(pn<s_pl.length&&pn<30){ps=s_fl(s_pl[pn
].name,100)+';';if(p.indexOf(ps)<0)p+=ps;pn++}s_q=(s?'&s='+s_ape(s):''
)+(c?'&c='+s_ape(c):'')+(j?'&j='+j:'')+(v?'&v='+v:'')+(k?'&k='+k:'')+(
bw?'&bw='+bw:'')+(bh?'&bh='+bh:'')+(ct?'&ct='+s_ape(ct):'')+(hp?'&hp='
+hp:'')+(s_vb?'&vb='+s_vb:'')+(p?'&p='+s_ape(p):'')}if(s_gg(
'usePlugins'))s_wd.s_doPlugins();var l=s_wd.location,r=
tfs.document.referrer;if(!s_gg("pageURL"))s_wd.s_pageURL=s_fl(l?l:'',
255);if(!s_gg("referrer"))s_wd.s_referrer=s_fl(r?r:'',255);if(s_lnk||
s_eo){var o=s_eo?s_eo:s_lnk;if(!o)return '';var p=s_gv('pageName'),w=
1,t=s_ot(o),n=s_oid(o),x=o.s_oidt,h,l,i,oc;if(s_eo&&o==s_eo){while(o&&
!n&&t!='BODY'){o=o.parentElement?o.parentElement:o.parentNode;if(!o)
return '';t=s_ot(o);n=s_oid(o);x=o.s_oidt}oc=o.onclick?
o.onclick.toString():'';if(oc.indexOf("s_gs(")>=0)return ''}ta=
o.target;h=o.href?o.href:'';i=h.indexOf('?');h=s_gg(
'linkLeaveQueryString')||i<0?h:h.substring(0,i);l=s_gg('linkName')?
s_gg('linkName'):s_ln(h);t=s_gg('linkType')?s_gg('linkType'
).toLowerCase():s_lt(h);if(t&&(h||l))q+='&pe=lnk_'+(t=='d'||t=='e'?
s_ape(t):'o')+(h?'&pev1='+s_ape(h):'')+(l?'&pev2='+s_ape(l):'');else
trk=0;if(s_gg('trackInlineStats')){if(!p){p=s_gv('pageURL');w=0}t=
s_ot(o);i=o.sourceIndex;if(s_gg('objectID')){n=s_gg('objectID');x=1;i=
1}if(p&&n&&t)qs='&pid='+s_ape(s_fl(p,255))+(w?'&pidt='+w:'')+'&oid='
+s_ape(s_fl(n,100))+(x?'&oidt='+x:'')+'&ot='+s_ape(t)+(i?'&oi='+i:'')}
}if(!trk&&!qs)return '';if(trk)q=(vt?'&t='+s_ape(vt):'')+s_hav()+q
s_wd.s_linkName=s_wd.s_linkType=s_wd.s_objectID=s_lnk=s_eo='';if(
!s_wd.s_disableLegacyVars)s_wd.linkName=s_wd.linkType=s_wd.objectID=''
var code='';if(un){if(trk&&s_vs(un,sed))code+=s_mr(un,sess,q+(qs?qs:
s_rq(un)),ta);s_sq(un,trk?'':qs)}else if(s_wd.s_unl)
for(var unn=0;unn<s_wd.s_unl.length;unn++){un=s_wd.s_unl[unn];if(trk&&
s_vs(un,sed))code+=s_mr(un,sess,q+(qs?qs:s_rq(un)),ta);s_sq(un,trk?'':
qs)}return code}function s_dc(un){un=un.toLowerCase();var dyas=s_gg(
'dynamicAccountSelection'),dyal=s_gg('dynamicAccountList'),dyam=s_gg(
'dynamicAccountMatch');if(dyas&&dyal)un=s_dyas(un,dyal,dyam);s_wds(un)
s_ca(un);return s_gs(un)}
s_code=s_dc(s_account);if(s_code)s_d.write(s_code)