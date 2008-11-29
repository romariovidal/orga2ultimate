/****** ******* *******
VERSION 1.2 IEFix777 
****** ******* *******/
    
    
/**
 * SWFObject v1.4: Flash Player detection and embed - http://blog.deconcept.com/swfobject/
 *
 * SWFObject is (c) 2006 Geoff Stearns and is released under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * **SWFObject is the SWF embed script formarly known as FlashObject. The name was changed for
 *   legal reasons.
 */
if(typeof deconcept=="undefined"){var deconcept=new Object();}
if(typeof deconcept.util=="undefined"){deconcept.util=new Object();}
if(typeof deconcept.SWFObjectUtil=="undefined"){deconcept.SWFObjectUtil=new Object();}
deconcept.SWFObject=function(_1,id,w,h,_5,c,_7,_8,_9,_a,_b){
if(!document.createElement||!document.getElementById){return;}
this.DETECT_KEY=_b?_b:"detectflash";
this.skipDetect=deconcept.util.getRequestParameter(this.DETECT_KEY);
this.params=new Object();
this.variables=new Object();
this.attributes=new Array();
if(_1){this.setAttribute("swf",_1);}
if(id){this.setAttribute("id",id);}
if(w){this.setAttribute("width",w);}
if(h){this.setAttribute("height",h);}
if(_5){this.setAttribute("version",new deconcept.PlayerVersion(_5.toString().split(".")));}
this.installedVer=deconcept.SWFObjectUtil.getPlayerVersion(this.getAttribute("version"),_7);
if(c){this.addParam("bgcolor",c);}
var q=_8?_8:"high";
this.addParam("quality",q);
this.setAttribute("useExpressInstall",_7);
this.setAttribute("doExpressInstall",false);
var _d=(_9)?_9:window.location;
this.setAttribute("xiRedirectUrl",_d);
this.setAttribute("redirectUrl","");
if(_a){this.setAttribute("redirectUrl",_a);}};
deconcept.SWFObject.prototype={setAttribute:function(_e,_f){
this.attributes[_e]=_f;
},getAttribute:function(_10){
return this.attributes[_10];
},addParam:function(_11,_12){
this.params[_11]=_12;
},getParams:function(){
return this.params;
},addVariable:function(_13,_14){
this.variables[_13]=_14;
},getVariable:function(_15){
return this.variables[_15];
},getVariables:function(){
return this.variables;
},getVariablePairs:function(){
var _16=new Array();
var key;
var _18=this.getVariables();
for(key in _18){
_16.push(key+"="+_18[key]);}
return _16;
},getSWFHTML:function(){
var _19="";
if(navigator.plugins&&navigator.mimeTypes&&navigator.mimeTypes.length){
if(this.getAttribute("doExpressInstall")){this.addVariable("MMplayerType","PlugIn");}
_19="<embed type=\"application/x-shockwave-flash\" src=\""+this.getAttribute("swf")+"\" width=\""+this.getAttribute("width")+"\" height=\""+this.getAttribute("height")+"\"";
_19+=" id=\""+this.getAttribute("id")+"\" name=\""+this.getAttribute("id")+"\" ";
var _1a=this.getParams();
for(var key in _1a){_19+=[key]+"=\""+_1a[key]+"\" ";}
var _1c=this.getVariablePairs().join("&");
if(_1c.length>0){_19+="flashvars=\""+_1c+"\"";}
_19+="/>";
}else{
if(this.getAttribute("doExpressInstall")){this.addVariable("MMplayerType","ActiveX");}
_19="<object id=\""+this.getAttribute("id")+"\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\""+this.getAttribute("width")+"\" height=\""+this.getAttribute("height")+"\">";
_19+="<param name=\"movie\" value=\""+this.getAttribute("swf")+"\" />";
var _1d=this.getParams();
for(var key in _1d){_19+="<param name=\""+key+"\" value=\""+_1d[key]+"\" />";}
var _1f=this.getVariablePairs().join("&");
if(_1f.length>0){_19+="<param name=\"flashvars\" value=\""+_1f+"\" />";}
_19+="</object>";}
return _19;
},write:function(_20){
if(this.getAttribute("useExpressInstall")){
var _21=new deconcept.PlayerVersion([6,0,65]);
if(this.installedVer.versionIsValid(_21)&&!this.installedVer.versionIsValid(this.getAttribute("version"))){
this.setAttribute("doExpressInstall",true);
this.addVariable("MMredirectURL",escape(this.getAttribute("xiRedirectUrl")));
document.title=document.title.slice(0,47)+" - Flash Player Installation";
this.addVariable("MMdoctitle",document.title);}}
if(this.skipDetect||this.getAttribute("doExpressInstall")||this.installedVer.versionIsValid(this.getAttribute("version"))){
var n=(typeof _20=="string")?document.getElementById(_20):_20;
n.innerHTML=this.getSWFHTML();
return true;
}else{
if(this.getAttribute("redirectUrl")!=""){document.location.replace(this.getAttribute("redirectUrl"));}}
return false;}};
deconcept.SWFObjectUtil.getPlayerVersion=function(_23,_24){
var _25=new deconcept.PlayerVersion([0,0,0]);
if(navigator.plugins&&navigator.mimeTypes.length){
var x=navigator.plugins["Shockwave Flash"];
if(x&&x.description){_25=new deconcept.PlayerVersion(x.description.replace(/([a-z]|[A-Z]|\s)+/,"").replace(/(\s+r|\s+b[0-9]+)/,".").split("."));}
}else{try{
var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
for(var i=3;axo!=null;i++){
axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash."+i);
_25=new deconcept.PlayerVersion([i,0,0]);}}
catch(e){}
if(_23&&_25.major>_23.major){return _25;}
if(!_23||((_23.minor!=0||_23.rev!=0)&&_25.major==_23.major)||_25.major!=6||_24){
try{_25=new deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));}
catch(e){}}}
return _25;};
deconcept.PlayerVersion=function(_29){
this.major=parseInt(_29[0])!=null?parseInt(_29[0]):0;
this.minor=parseInt(_29[1])||0;
this.rev=parseInt(_29[2])||0;};
deconcept.PlayerVersion.prototype.versionIsValid=function(fv){
if(this.major<fv.major){return false;}
if(this.major>fv.major){return true;}
if(this.minor<fv.minor){return false;}
if(this.minor>fv.minor){return true;}
if(this.rev<fv.rev){return false;}return true;};
deconcept.util={getRequestParameter:function(_2b){
var q=document.location.search||document.location.hash;
if(q){
var _2d=q.indexOf(_2b+"=");
var _2e=(q.indexOf("&",_2d)>-1)?q.indexOf("&",_2d):q.length;
if(q.length>1&&_2d>-1){
return q.substring(q.indexOf("=",_2d)+1,_2e);
}}return "";}};
if(Array.prototype.push==null){
Array.prototype.push=function(_2f){
this[this.length]=_2f;
return this.length;};}
var getQueryParamValue=deconcept.util.getRequestParameter;
var FlashObject=deconcept.SWFObject; // for backwards compatibility
var SWFObject=deconcept.SWFObject;





    /*
        DESCRIPTION:    Find all flash files, strip and re-write for IE7
        DEVELOPED BY:   Jeremiah La Bresh
        Implemented less code thanks to - http://www.thepixelage.com/blog/well_microsoft_lost_the_battle_and_we_have_to_suff.html
    */
    document.write("<style type=\"text/css\">object{visibility:hidden;}</style>");
    
    function ieFlashFix777(){       
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
        if(is_ie){
            var htmlNodes = new Array("object","applet");
            var htmlNodesN = htmlNodes.length;
            for(var x =0; x<htmlNodesN; x++){
                var htmlType = document.getElementsByTagName(htmlNodes[x]);
                for (var i = 0; i < htmlType.length; i++) {
                    var oldObj = htmlType[i];
                    if(oldObj.outerHTML.indexOf("type=") == -1){
                        oldObj.outerHTML = oldObj.outerHTML;
                    } else {
                        if(oldObj.outerHTML.indexOf("flash") != -1){
                            var movW = ((oldObj.getAttribute("width")!="")?oldObj.getAttribute("width"):0);
                            var movH = ((oldObj.getAttribute("height")!="")?oldObj.getAttribute("height"):0);
                            var bgCol = "";
                            var movS = "";
                            
                            var oldObjChildren = oldObj.childNodes;
                            for(var n=0; n<oldObjChildren.length; n++){
                                if(oldObjChildren[n].getAttribute("name") == "movie"){
                                    movS = oldObjChildren[n].getAttribute("value");
                                }
                                if(oldObjChildren[n].getAttribute("bgcolor") != ""){
                                    bgCol = oldObjChildren[n].getAttribute("BGColor");
                                }
                                if((movS != "") && (bgCol != "")){
                                    break;
                                }
                            }
                            
                            if(movS != ""){
                                var newObj = document.createElement("div");
                                    newObj.id = "span_" + x + "_" + i;
                                    var repObj = oldObj.replaceNode(newObj);
                            		var so = new SWFObject(movS, "mov_" + x + "_" + i, movW, movH, "7", bgCol);
                            		        so.write("span_" + x + "_" + i);
                            }
                        }
                        else if(oldObj.outerHTML.indexOf("silverlight") != -1){
                            //don't rewrite outerHTML for Silverlight objects
                        } 
                        else {
                            oldObj.outerHTML = oldObj.outerHTML;
                        }
                    }
                        
                } 
            }   
        }
        
        //Create new style and append it to <head>
        var objectStyle = document.createElement("style");
        objectStyle.setAttribute("type", "text/css");
        var objectStyleText = "object{visibility:visible}";
        
        if (objectStyle.styleSheet) { //IE
            objectStyle.styleSheet.cssText = objectStyleText;
        } 
        else { //FF,Safari etc.
            objectStyle.appendChild(document.createTextNode(objectStyleText));
        }
        document.getElementsByTagName("head")[0].appendChild(objectStyle); 

    }

//Runs ieFlashFix777 function after page loaded
var onLoadEvent = function() {
    if (window.attachEvent) {
        window.attachEvent("onload", ieFlashFix777);
    }
    else if (window.addEventListener) { 
        window.addEventListener( "load", ieFlashFix777, false );
    }
}

onLoadEvent();