<!--
// menu style vars
var width = 180;
var height = 25;
var border = "#C6C6C6";
var border2 = "#555555";
var menubgOn = "#E6E6E6";
var menubgOff = "#ffffff";

var menuXoffset = 0;
var menuYoffset = 0;

// functionality variables
var delay = 500;
var delayStart = 150;
var useMask = false;
var useDHTML = false;
var hideSels = false;

var start;
var hideFlag;
var hideTimer;
var showTimer;
var curMenu;
var curItem;
var curNav;
var activeMenu;
var theMenu;
var theImg;
var theDiv;
var theCont;
var theMask;
var onMenu = false;
var menusWritten = false;

// menu building variables;
var menucount = 0;

var menustyle = "width:" + width + "px; text-align:left; border-left:" + border + " 1px solid; border-right:" + border2 + " 1px solid; background-color:" + menubgOff + "; cursor: pointer;";

var separator = "<div class=\"sepstyle\"> </div>";

function menuobject (label, url) {
	this.label = label;
	this.url = url;
	return this;
}

function renderdata () {
	menuData[catCount++] = tmpData;
	tmpData = new Array();
	count = 0;
}

// Turn iFrame mask on for IE 5.5+ / PC only
if (is_ie && !is_mac_ie) {
	if (is_ie5_5up) {
		useMask = true;
	}
	else {
		hideSels = true;
	}
}

// Turn on DHTML for 5+ browsers only
if (is_ie && !is_ie5up) {
	useDHTML = false;
} else if(!nn4) { useDHTML = true; }

function writeMenus() {
	if(useDHTML) {
		for (i = 0; i < menuData.length; i++) {
			document.writeln("<div id=\"menu" + i + "\" style=\"position:absolute; z-index:100; top:0px; left:0px; visibility:hidden;\">");
			if (useMask) {
				document.writeln("<div id=\"cont" + i + "\">");
			}
			for (j = 0; j < menuData[i].length; j++) {
				if (j == 0) {
					document.write("<div id=\"menuitem" + menucount + "\" onMouseOver=\"startShow('" + i + "'); changebg('menuitem" + menucount + "'); changeStatus('" + menuData[i][j].url + "');\" onMouseOut=\"startHide(); changeStatus('');\" onClick=\"goTo('" + menuData[i][j].url + "');\" style=\"" + menustyle + "border-top:" + border + " 1px solid;\"><div class=\"dhtmlmenuitem\"><a href=\"" + menuData[i][j].url + "\" class=\"dhtmlmenulink\">" + menuData[i][j].label + "</a></div>" + separator + "</div>");
				}
				else if (j < menuData[i].length - 1) {
					document.write("<div id=\"menuitem" + menucount + "\" onMouseOver=\"startShow('" + i + "'); changebg('menuitem" + menucount + "'); changeStatus('" + menuData[i][j].url + "');\" onMouseOut=\"startHide(); changeStatus('');\" onClick=\"goTo('" + menuData[i][j].url + "');\" style=\"" + menustyle + "\"><div class=\"dhtmlmenuitem\"><a href=\"" + menuData[i][j].url + "\" class=\"dhtmlmenulink\">" + menuData[i][j].label + "</a></div>" + separator + "</div>");
				}
				else {
					document.write("<div id=\"menuitem" + menucount + "\" onMouseOver=\"startShow('" + i + "'); changebg('menuitem" + menucount + "'); changeStatus('" + menuData[i][j].url + "');\" onMouseOut=\"startHide(); changeStatus('');\" onClick=\"goTo('" + menuData[i][j].url + "');\" style=\"" + menustyle + "border-bottom:" + border2 + " 1px solid;\"><div class=\"dhtmlmenuitem\"><a href=\"" + menuData[i][j].url + "\" class=\"dhtmlmenulink\">" + menuData[i][j].label + "</a></div></div>");
				}
				menucount++;
			}
			if (useMask) {
				document.writeln("</div>");
			}
			document.writeln("</div>");
			moveMenu(i);
		}
	}
	menusWritten = true;
}

function moveMenu(num) {
	tmpMenu = "menu" + num;
	//thePos = getObj("pos" + num);
	theImg = "nav" + num;
	xPos = offsetLeft(getObj(theImg)) + menuXoffset;
	//alert(offsetLeft(getObj(theImg)));
	yPos = offsetTop(getObj(theImg)) + height + menuYoffset;
	/*if (num == 0) {
		xPos += 4;
	}*/
	if (num == (menuData.length - 1) && !is_mac_ie) {
		xPos -= width - document.images[theImg].width - 1;
		if (!is_ie) {
			xPos -= 3;
		}
		//xPos -= 0;
	}
	if (num == (menuData.length - 2)) {
		//xPos -= 30;
	}
	getObj(tmpMenu).style.left = xPos + 'px';
	getObj(tmpMenu).style.top = yPos + 'px';
}

function showMenus() {
	unfocus();
	if (hideSels) {
		hideSelect();
	}
	onMenu = true;
	theMenu = "menu" + curMenu;
	activeMenu = theMenu;
	moveMenu(curMenu);
    if (useMask) {
		theMask = getObj("iMask");
		theCont = "cont" + curMenu;
		theMask.style.left = xPos;
	    theMask.style.top = yPos;
		theMask.style.width = getObj(theCont).offsetWidth;
	    theMask.style.height = getObj(theCont).offsetHeight;
	}
    getObj(theMenu).style.visibility = "visible";
}

function startShow(menu) {
	if (curNav) {
		//navOff(curNav);
	}
	//navOver(menu);
	curNav = menu;
	if (menusWritten && !nn4) {
		curMenu = menu;
		if (activeMenu) {
			getObj(activeMenu).style.visibility = "hidden";
		}
		clearTimer();
		if (!onMenu) {
			if (curItem) {
				curItem.style.backgroundColor = menubgOff;
			}
			showTimer = setTimeout("showMenus()", delayStart);
		}
		else {
			showMenus();
		}
	}
	else {
		return false;
	}
}

function startHide() {
	if (!onMenu) {
		if (curNav) {
			//navOff(curNav);
			curNav = null;
		}
	}
	if (menusWritten && !nn4) {
		if (showTimer) clearTimeout(showTimer);
		start = new Date();
		hideFlag = true;
		hideTimer = setTimeout("hideMenus()", delay);
	}
	else {
		return false;
	}
}

function clearTimer() {
	if (hideTimer) clearTimeout(hideTimer);
	hideTimer = null;
	hideFlag = false;
}

function hideMenus() {
	if (!hideFlag) return;
	var elapsed = new Date() - start;
	if (elapsed < delay) {
		hideTimer = setTimeout("hideMenus()", delay - elapsed);
		return;
	}
	hideFlag = false;
	hideAllMenus();
	if (hideSels) {
		showSelect();
	}
}

function hideAllMenus() {
	changeStatus('');
	if (curNav) {
		//navOff(curNav);
		curNav = null;
	}
	for (i = 0; i<menuData.length; i++) {
		theMenu = "menu" + i;
		getObj(theMenu).style.visibility = "hidden";
	}
    if (useMask) {
		if (theMask) {
			theMask.style.left = -300;
	    	theMask.style.top = -300;
		}
	}
	if (curItem) {
		curItem.style.backgroundColor = menubgOff;
	}
	onMenu = false;
}

function goTo(url) {
	document.location.href = url;
}

//return [object]
function getObj(theId) {
	if (document.getElementById) {
		theObj = document.getElementById(theId); 
	} else if (document.all) { 
		theObj = document.all[theId];
	}
	return theObj;
}

//return [img] object NS4 fix
function getImg(imgName, divName) {
	if (nn4) {
		imgObj = eval("document." + divName + ".document.images." + imgName);
	}
	else {
		imgObj = eval("document.images." + imgName);
	}
	return imgObj;
}

//Mac IE offset fix
function offsetLeft(o){
	var i = 0;
	while (o.offsetParent!=null) {
		i += o.offsetLeft;
		o = o.offsetParent;
	}
	return i + o.offsetLeft;
}
function offsetTop(o){
	var i = 0;
	while (o.offsetParent!=null) {
		i += o.offsetTop;
		o = o.offsetParent;
	}
	return i + o.offsetTop;
}

function changebg(id) {
	if (useDHTML) {
		var tmp = getObj(id);
		if (curItem) {
			if (tmp != curItem) {
				curItem.style.backgroundColor = menubgOff;
				tmp.style.backgroundColor = menubgOn;
			}
		} else { tmp.style.backgroundColor = menubgOn; }
		curItem = tmp;
	}
}

function unfocus() {
	for (x = 0; x < document.forms.length; x++) {
		for (y = 0; y < document.forms[x].elements.length; y++) {
			if (document.forms[x].elements[y].type == "text") {
				document.forms[x].elements[y].blur();
			}
		}
	}
}

function hideSelect() {
	var f = document.forms;
	for(var i=0;i<f.length;i++) {
		for (var j=0; j<f[i].elements.length; j++) {
			if (f[i].elements[j].type == "select-one") {
				f[i].elements[j].style.visibility = "hidden";
			}
		}
	}
}

function showSelect() {
	var f = document.forms;
	for(var i=0;i<f.length;i++) {
		for (var j=0; j<f[i].elements.length; j++) {
			if (f[i].elements[j].type == "select-one") {
				f[i].elements[j].style.visibility = "visible";
			}
		}
	}
}

function changeStatus(text) {
	window.status = text;
}

function clearbg() {
	if (curItem) {
		curItem.style.backgroundColor = menubgOff;
	}
	curItem = null;
}

if (document.addEventListener) {
	document.addEventListener("mouseup", mouseUp, false);
}

document.onmouseup = mouseUp;

function mouseUp() {
	hideAllMenus();
	return true;
}

//-->