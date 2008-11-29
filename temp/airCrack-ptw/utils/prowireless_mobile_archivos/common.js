<!--

var agt=navigator.userAgent.toLowerCase();
var appVer = navigator.appVersion.toLowerCase();
var is_mac = (agt.indexOf("mac")!=-1);
var is_minor = parseFloat(appVer);
var is_major = parseInt(is_minor);
var is_opera = (agt.indexOf("opera") != -1);
var iePos  = appVer.indexOf('msie');
var is_mac_ie = false;
if (iePos !=-1) {
   is_minor = parseFloat(appVer.substring(iePos+5,appVer.indexOf(';',iePos)));
   is_major = parseInt(is_minor);
}
var is_ie = ((iePos!=-1) && (!is_opera));
if (is_mac && is_ie) {
	is_mac_ie = true;
}
var is_ie5up = (is_ie && is_minor >= 5);
var is_ie5_5up =(is_ie && is_minor >= 5.5);   
var is_safari = ((agt.indexOf('safari')!=-1)&&(agt.indexOf('mac')!=-1))?true:false;
var nn4 = false;
if (document.layers) {
	nn4 = true;
}

function sectionOn(section,geoPath) {
	if (geoPath == "" || geoPath==undefined) {
		geoPath = "/sites/sitewide/pix/";
	}
	document.images["nav" + section].src = geoPath.toString() + "nav" + section + "_on.gif";

	/*var href = document.location + "";
	var hrefData = href.split("/");
	var sections = new Array("products","techtrends","solutions","resources","support");
	var changeThis;
	var topfolder = hrefData[3];
	
	if (topfolder == "cd") {
		topfolder = hrefData[4];
	}
	
	for (i = 0; i < sections.length; i++) {
		if (topfolder == sections[i]) {
			changeThis = i;
		}
	}
	
	if (changeThis || changeThis == 0) {
		var image = document.images["nav" + changeThis].src.split(".gif");
		var target = image[0] + "_on.gif";
		document.images["nav" + changeThis].src = target;
	}*/
}

function goURL(s) { var val=s.options[s.selectedIndex].value.toString(); if (val!='') window.location.href=val; }

function avPopup(url,target,opts) { var win=window.open(url,target,opts); if (window.focus)win.focus(); }

var ns6,ns4,ie4;
// Show/Hide functions for non-pointer layer/objects
ns4 = (document.layers)? true:false
ie4 = (document.all)? true:false
ns6 = false;
//if the DOM is not IE and not NS4, it must be NS6
if (ns4 == ie4) {
	ns6 = true;
	ie4 = ns4 = false;
}

var URL,width,height,scroll,menubar,toolbar,resize,xPos,yPos,s_gs;winName = "";
function openWin(URL,width,height,scroll,menubar,toolbar,resize,xPos,yPos,winName){
	var focusFail = false;
	if (width == "") width = "500";
	if (height == "") height = "500";
	if (scroll == "") scroll = "auto";
	if (menubar =="") menubar = "no";
	if (toolbar == "") toolbar = "no";
	if (resize == "") resize = "yes";
	if (xPos == "") xPos = "5";
	if (yPos == "") yPos = "5";
	if (winName == "") winName = "win";
	var features ="width=" +width+ ",height=" +height+ ",scrollbars=" +scroll+ ",menubar=" +menubar+ ",toolbar=" +toolbar+ ",resizable=" +resize+ ",left=" +xPos+ ",top=" +yPos;
	var newWin = window.open(URL,winName,features);
	if (navigator.appVersion.indexOf("NT")!= -1){
		if (navigator.appVersion.indexOf("NT 5")!= -1){
			focusFail = false;
		} else if (ie4 == true)	focusFail = true;
	}
	if (focusFail == false){
		if (window.focus) newWin.focus();
	}
}

function showmore(which) {
	getObj("showmore" + which).style.display = "none";
	getObj("content" + which).style.display = "block";
}

function showless(which) {
	getObj("content" + which).style.display = "none";
	getObj("showmore" + which).style.display = "block";
}

function timelinePop() {
	window.name='home';
	url = "/intel/other/ehs/timeline/index.htm";
	var win=window.open(url,'timelinePop','width=562,height=463,pageX=60,left=60,pageY=0,top=0,menubar=0,toolbar=0,scrollbars=0,resizable=0,status=0,location=0')
	win.focus();
}

function popExternal(url) { var win=window.open('/sites/templates/templates/external_popup.htm?url='+escape(url),'externalPop','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=520,height=230'); if (window.focus)win.focus(); }


//-->
