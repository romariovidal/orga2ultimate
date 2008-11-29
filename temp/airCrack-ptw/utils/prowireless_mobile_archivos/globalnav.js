/* ver 07.18.03 */
/* updated 10.14.03 GRF */

var gn_MENU_LEFT=177;
var gn_MENU_WIDTH=195;
var gn_MENU_OVERLAP=1;
var gn_MENU_BASEMARGIN=15;
var gn_MENU_INDENT=10;
var gn_MENU_MARGIN=0;
if(!gn_MENU_DEFURL) { var gn_MENU_DEFURL='http://www.intel.com/sites/nav/pix/'; }
// could be cache-www.intel.com/plt/cd/ids/eng/images/
if(!gn_MENU_PREFIX) { var gn_MENU_PREFIX=''; }

//******* Rollover Functions ********//
var gn_cacheArray=new Array(0);
function gn_imageGroup() { if (this.total==null) { this.total=this.cached=this.rollovers=0; this.names=new Array(0); this.urls=new Array(0); this.count=new Array(0); this.i=new Object(); this.index=gn_cacheArray.length; gn_cacheArray[this.index]=this; this.defURL=''; this.add=gn_imageGroup; } var a,j,k,args=arguments; for (j=0; j<args.length; j++) { k=this.total; a=args[j].split('|'); this.names[k]=a[0]; this.urls[k]=this.defURL+a[1]; this.total++; } }
function gn_loadImages() { if (document.images) { var j,c=gn_cacheArray; for (j=0; j<c.length; j++) gn_loadGroup(c[j]); } }
function gn_loadGroup(obj,pre) { if (obj.loaded) return; obj.loaded=1; var k,img,stuff=''; for (k=0; k<obj.total; k++) { img=obj.i[obj.names[k]]=new Image(); if (!gn_NS4) { img.group=obj.index; img.num=k; img.onload=gn_countImg; } if (gn_NS4) { stuff+='<IMG SRC="'+ obj.urls[k] +'" BORDER="0" ONLOAD="gn_countImg('+obj.index+','+k+');"><BR>'; } img.src=obj.urls[k]; } if (gn_NS4) { if (pre) { document.gn_newLayer('gn_cacher'+obj.index,stuff,0); } else { var cacher=new Layer(0),c=cacher.document; c.write(stuff); c.close(); } } }
function gn_countImg(n,k) { if (!gn_NS4) {n=this.group; k=this.num;} var obj=gn_cacheArray[n]; if (obj.count[k]!=1) { obj.count[k]=1; obj.cached++; if (obj.cached==obj.total) {obj.rollovers=1; if (obj.onload) eval(obj.onload);} } }
function gn_swapImg(obj,who,n) { var img=document[who+'_bttn']; if ((obj.rollovers)&&(img)) {img.src = obj.i[who+((n)?'_on':'_off')].src;} }
//***********************************//

document.gn_js = true; var gn_navReady=0;

var gn_IE=(document.all)?1:0;
var gn_NS4=(document.layers)?1:0;
var gn_NS6=((document.getElementById)&&(navigator.appName=='Netscape'))?1:0;
var gn_NS=(gn_NS4)?1:0;
var gn_DHTML=(gn_IE||gn_NS4||gn_NS6)?1:0;
var gn_Mac=(navigator.userAgent.indexOf('Mac')!=-1)?1:0;
var gn_IEMac=(gn_Mac&&gn_IE)?1:0;
var gn_IE4Mac=(gn_IEMac&&(navigator.appVersion.indexOf('4.',4)!=-1))?1:0;
var gn_IE5Mac=(gn_IEMac&&(navigator.appVersion.indexOf('5.')!=-1))?1:0;
document.gn_dhtml = gn_DHTML;

if (!gn_NS4) document.write('<style type="text/css">\n.headersearch { border:1px solid #999; }\n.cinput72, .cinput147 { border:1px #8E8E8E solid; } \n</style>\n');
if (gn_IEMac) document.write('<style type="text/css">\n.cinput72 { width:68px; }\n.cinput147 { width:142px; }\n.cselect147 { width:142px; }\n</style>\n');

if (document.images) { 
  gn_navPics=new gn_imageGroup(); gn_navPics.defURL=gn_MENU_DEFURL;
  gn_navPics.add('resources_on|resources_on.gif', 'resources_off|resources_off.gif', 'products_on|products_on.gif', 'products_off|products_off.gif', 'solutions_on|solutions_on.gif', 'solutions_off|solutions_off.gif', 'tech_on|tech_on.gif', 'tech_off|tech_off.gif', 'support_on|support_on.gif', 'support_off|support_off.gif');
}

if (gn_NS4) { 
  document.classes.gnav1.all.fontSize='11px';
  document.classes.gnav2.all.fontSize='11px';
  document.classes.gnav3.all.fontSize='12px';
  document.classes.gnav1.all.textDecoration='underline';
}


if (gn_DHTML) {

  var gn_xmargin=10;
  var gn_ymargin=15;
  gn_hidden = (gn_NS4)? 'hide':'hidden'; gn_visible = (gn_NS4)? 'show':'visible';

  gn_currentNav=new Array(0);

  gn_mouseY=0; gn_nsFix=0;

  gn_navTimer=setTimeout("null",10);
  gn_moveNavTimer = setInterval('null',1); clearInterval(gn_moveNavTimer);
  gn_popupTimer = setTimeout("null",10);

  gn_navtabletop = '<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0"><TR><TD BGCOLOR="#C5C5C5" ROWSPAN="3">'+gn_spacer()+'</TD><TD BGCOLOR="#C5C5C5">'+gn_spacer()+'</TD><TD BGCOLOR="#C5C5C5" ROWSPAN="3">'+gn_spacer()+'</TD></TR><TR><TD BGCOLOR="#E6E6E6">';
  gn_navtablebot = '</TD></TR><TR><TD BGCOLOR="#C5C5C5">'+gn_spacer()+'</TD></TR></TABLE>';

  gn_HDnavtabletop = '<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0"><TR><TD BGCOLOR="#C5C5C5">'+gn_spacer()+'</TD><TD BGCOLOR="#E6E6E6">';
  gn_HDnavtablebot = '</TD><TD BGCOLOR="#C5C5C5">'+gn_spacer()+'</TD></TR><TR><TD BGCOLOR="#C5C5C5" COLSPAN="3">'+gn_spacer()+'</TD></TR></TABLE>';

  gn_boxPics=new gn_imageGroup(); gn_boxPics.defURL=gn_MENU_DEFURL;
  gn_boxPics.add('navarrow|nav_arrow.gif', 'su|nav_s_up.gif', 'sd|nav_s_dn.gif');

  gn_cascadeNav=new Object(); gn_menuList = new Object(); gn_layers = new Object(); gn_layers.EQ=new Array();
  var gn_TempArgs;

}

//******* Basic DHTML Functions ********//

function gn_equalizeLayers(n) { var k,l; var list=(n==null)?gn_layers.EQ:arguments; for (k=0; k<list.length; k++) {l=gn_getLayer(list[k]); if(l) gn_equalize(l); } if (n==null) gn_layers.EQ=new Array(); }
function gn_getLayer(spec,base) { if (!gn_NS4) return gn_getIt(spec); var j=0,temp=null; if (!base) base=document; if (base.layers[spec]) return base.layers[spec]; for (j=0; j<base.layers.length; j++) { temp=gn_getLayer(spec,base.layers[j].document); if (temp) return temp; } return null; }
function gn_getIt(id) { if (gn_IE) return document.all[id]; if (gn_NS6) return document.getElementById(id); }

function gn_equalize(layer) { if (gn_layers[layer.id]) return;
  layer.getTop = new Function("return(parseInt(this.style.top))");
  layer.getLeft = new Function("return(parseInt(this.style.left))");
  layer.getHeight = new Function("if (gn_IE) return this.scrollHeight; if (gn_NS4) return this.document.height; if (gn_NS6) return this.offsetHeight;");
  layer.setClip = new Function ("l","t","r","b","if (gn_NS4) {this.clip.left=l; this.clip.top=t; this.clip.right=r; this.clip.bottom=b;} else { this.style.clip='rect('+t+' '+r+' '+b+' '+l+')'; }");
  layer.rewrite = new Function ("html","if (gn_IE||gn_NS6) this.innerHTML=html; if (gn_NS4) {this.document.write(html); this.document.close();}");
  layer.vis = new Function("n","this.style.visibility=(n)?gn_visible:gn_hidden; if (gn_NS6) {this.style.zIndex=(n)?this.z:eval(this.z)-1;}");
  gn_layers[layer.id]=layer; if (gn_NS4)layer.style=layer; if (gn_NS6) { layer.z=layer.style.zIndex; if(layer.style.visibility==gn_hidden)layer.vis(0); } }

function gn_layerTag(id,content,z,vis,l,t,w,h,extra) {gn_layers.EQ[gn_layers.EQ.length]=id; if(w==0)w=1; if(!t)t=0; if(!l)l=0; if(!vis)vis=gn_hidden; if(!z)z=10; if(!extra)extra='';
  if (!gn_NS4) {var stuff = '<DIV ID="'+id+'" STYLE="position:absolute; overflow:visible; left:'+l+'px; top:'+t+'px;'+((w)?(' width:'+w+'px;'):'')+((h)?(' height:'+h+'px;'):'')+' visibility:'+vis+'; z-index:'+z+'" '+extra+'>'; }
  if (gn_NS4)  {var stuff = '<LAYER NAME="'+id+'" left="'+l+'" top="'+t+'"'+((w)?(' width="'+w+'"'):'')+((h)?(' height="'+h+'"'):'')+' visibility="'+vis+'" z-index="'+z+'" '+extra+'>'; } return (stuff+content+((gn_NS4)?'</LAYER>':'</DIV>')); }

function gn_newLayer(id,content,z,vis,l,t,w,h,extra) { var layer; /*?*/
  if (gn_IE) { document.body.insertAdjacentHTML('BeforeEnd',gn_layerTag(id,content,z,vis,l,t,w,h,extra)); layer=document.all[id]; }
  if (gn_NS4) { if (w==null)w=2000; layer=new Layer(w); layer.document.write(content); layer.document.close(); layer.left=(l)?l:0; layer.top=(t)?t:0; layer.height=(h!=null)?h:1; layer.zIndex=(z)?z:10; layer.width=layer.document.width; layer.visibility=(vis)?vis:gn_visible; } gn_equalize(layer); return (layer);  }

function gn_writeLayer(id,content,z,vis,l,t,w,h,extra) { this.writeln(gn_layerTag(id,content,z,vis,l,t,w,h,extra)); gn_equalizeLayers();}
if (gn_DHTML) {  document.gn_newLayer=gn_writeLayer; }

//******* End Basic DHTML Functions ********//

function gn_init() { gn_navReady=1; if (document.gn_loaded)return; document.gn_loaded=1; gn_setMM(); }

function gn_navEnter(who) { if ((who==null)||(who==gn_currentNav[0])) { clearTimeout(gn_navTimer); if (gn_NS4) { if (gn_nsFix==1) gn_nsFix=2; else gn_nsFix=1; } } }
function gn_navExit(who) { if (!gn_navReady) return;  if (document.noDHTML) return; clearTimeout(gn_popupTimer); if ((who!=null)&&(who!=gn_currentNav[0])) return; if (gn_NS4) {if (gn_nsFix==2) {gn_nsFix=0; return;}  gn_nsFix=0;} clearTimeout(gn_navTimer); gn_navTimer=setTimeout("gn_endNav();",1000); }
function gn_navOver(who) { if (gn_DHTML) gn_startNav(who,arguments); }
function gn_navOut(who) { if (gn_DHTML) gn_navExit(who); }
function gn_startNav(who,args) { if (!gn_navReady) return; if (document.noDHTML) return; gn_navEnter(who); if (who==gn_currentNav[0]) return; gn_TempArgs=args; gn_popupTimer=setTimeout("gn_beginNav('"+who+"',1)",200); }
function gn_beginNav(who,n) { gn_HDendNav(); gn_navEnter(); gn_endNav(); gn_currentNav[0]=who; gn_cascadeNav.args=(n)?gn_TempArgs:new Object(); gn_popupNav(who,1); }
function gn_endNav() { var c=gn_currentNav[0]; if (c==null) return; if (!gn_menuList[c]) return; gn_hideLev(0); gn_currentNav[0]=null; }
function gn_popupNav(who,lev,delay) { if (lev>3) return; if (!gn_menuList[who]) return; if (delay!=null) { gn_popupTimer=setTimeout("gn_popupNav('"+who+"',"+lev+")",delay); return; } var l=gn_layers[(gn_Mac)?('GNML_'+who):('gn_menuLayer'+lev)]; if (!l) return; if (gn_Mac) l.lev=lev; if (who==gn_currentNav[l.lev]) return; gn_hideLev(l.lev-1); gn_currentNav[l.lev]=who; if (!gn_Mac) gn_writeNavLayer(who,l.lev,l.id); gn_setNavY(l); if(gn_Mac){ l.style.zIndex=(60+2*lev); l.style.left=(gn_xmargin+gn_MENU_LEFT+(lev-1)*(gn_MENU_WIDTH-gn_MENU_OVERLAP))} l.vis(1); if (l.lev>0) gn_toggleArrow(l.lev,1); }
function gn_hideLev(num,delay) { if (num>3) return; if (delay) { gn_popupTimer=setTimeout("gn_hideLev("+num+")",500); return; } var l,k=0; for (k=3; k>=(num+1); k--) { if (gn_currentNav[k]!=null) { l=gn_getML(k); l.vis(0); if (k>0) gn_toggleArrow(k,0); if (l.clipped != null) { l.clipIt(0,2000); gn_layers['gn_menuLayer'+l.lev+'up'].vis(0); gn_layers['gn_menuLayer'+l.lev+'dn'].vis(0); l.clipped=null; } gn_currentNav[k]=null; } } }

gn_HDcurrentNav='';
gn_HDnavTimer=setTimeout("null",10);
gn_HDpopupTimer = setTimeout("null",10);

var overHD=0; var overBTN=0;
function gn_HDnavEnter(who) { if ((who==null)||(who==gn_HDcurrentNav)) { clearTimeout(gn_HDnavTimer); } }
function gn_HDnavExit(who) { if (!gn_navReady) return; if (document.noDHTML) return; clearTimeout(gn_HDpopupTimer); if ((who!=null)&&(who!=gn_HDcurrentNav)) return; if (overHD||overBTN)return; clearTimeout(gn_HDnavTimer); gn_HDnavTimer=setTimeout("gn_HDendNav();",1000); }
function gn_HDnavOver(who,x) { overBTN=0; gn_HDnavEnter(who); if (who==gn_HDcurrentNav) return; gn_HDpopupTimer=setTimeout("gn_HDbeginNav('"+who+"',"+x+",1)",200); }
function gn_HDnavOut(who) { overBTN=0; gn_HDnavExit(who); }
function gn_HDbeginNav(who,x) { if (!gn_navReady) return; if (document.noDHTML) return; gn_HDnavEnter(); gn_HDendNav(); gn_endNav(); gn_HDcurrentNav=who; gn_swapImg(gn_navPics,who,1); var l=gn_layers['HDNavLayer_'+who]; if (!l)return; l.style.left=gn_xmargin+177+x; l.vis(1); }
function gn_HDendNav() { if (!gn_navReady) return; if (document.noDHTML) return; var c=gn_HDcurrentNav; if (c==null) return; if (gn_HDmenuList[c]) { gn_swapImg(gn_navPics,c,0); var l=gn_layers['HDNavLayer_'+c]; if (!l)return; l.vis(0); } gn_HDcurrentNav=null; }

function gn_toggleArrow(lev,show) { gn_hLink(gn_currentNav[lev]+'_link',show,1); }

function gn_setNavY(l) { var db=document.body; var st=(gn_IE)?db.scrollTop:window.pageYOffset; var wh=(gn_IE)?db.clientHeight:window.innerHeight; var pb=((gn_IE)?db[(gn_Mac)?'offsetHeight':'scrollHeight']:document.height)-((l.lev==1)?43:22)-((gn_NS6)?-1:(gn_IEMac)?0:1)*gn_ymargin; var lh=l.getHeight(); var wb=wh+st; if (wb>pb)wb=pb; var y=gn_mouseY-Math.round(lh/2); var a2=gn_cascadeNav.args[2]; if ((l.lev==1)&&(a2)&&(y<a2+gn_ymargin)) y=a2+gn_ymargin;  if (l.lev>1){ var prevTop=gn_getML(l.lev-1).getTop(); if (y<(prevTop)) y=prevTop; } if ((y+lh)>wb)y=wb-lh; if (y<(gn_ymargin+70))y=gn_ymargin+70; if (y<st)y=st; l.style.top=y; if ((y+lh)>wb) { l.clipped=1; gn_layers['gn_menuLayer'+l.lev+'up'].style.top=y; gn_layers['gn_menuLayer'+l.lev+'dn'].style.top=wb-14; gn_moveNav(l.id,0); } }
function gn_moveNav(id,s) { var who=gn_layers[id]; if (who==null) { clearInterval(gn_moveNavTimer); return; } var lh=who.getHeight(); var up=gn_layers['gn_menuLayer'+who.lev+'up'];  var dn=gn_layers['gn_menuLayer'+who.lev+'dn']; var y=who.getTop()+s; var uptop=up.getTop(); var dnbot=dn.getTop()+14; if(y>uptop) y=uptop; if((y+lh)<dnbot) y=dnbot-lh; up.vis((y<uptop)); dn.vis(((y+lh)>dnbot)); who.style.top=y; who.clipIt((y<uptop)?(uptop-y+8):0,((y+lh)>dnbot)?(dnbot-y-10):2000); if (((s<0)&&((y+lh)==dnbot))||((s>0)&&(y==uptop))) clearInterval(gn_moveNavTimer); if (gn_IE4Mac) { var z=who.style.zIndex; who.style.zIndex=(z-1); who.style.zIndex=z; } }
function gn_scrollNav(lev,s) { clearInterval(gn_moveNavTimer); var l=gn_getML(lev); if ((lev==0)||(!l)) return; gn_moveNavTimer=setInterval("gn_moveNav('"+l.id+"',"+s+")",50); }
function gn_getIy(img) {if (gn_IE4Mac) return(gn_parseXY(img)); return(img[(gn_NS4)?'y':'offsetTop']+((gn_NS6)?gn_NS6offY:(gn_IE5Mac)?gn_ymargin:0));} 
function gn_getIx(img) {if (gn_IE4Mac) return(gn_parseXY(img,'X')); return(img[(gn_NS4)?'x':'offsetLeft']+((gn_NS6)?gn_NS6offX:(gn_IE5Mac)?gn_xmargin:0));}
function gn_parseXY(who,n) {var val=0; var num=who[(n=='X')?'offsetLeft':'offsetTop']; if(num)val+=num;  var oP=who.parentElement; if (oP) val+=gn_parseXY(oP,n); return(val); }

function gn_getML(lev) { return(gn_layers[(gn_Mac)?('GNML_'+gn_currentNav[lev]):('gn_menuLayer'+lev)]); }

function gn_IMG(url,w,h,alt,ex) { return('<img src="'+gn_MENU_DEFURL+url+'" width="'+((w)?w:1)+'" height="'+((h)?h:1)+'" border="0" alt="'+((alt)?alt:'')+'"'+((ex)?' '+ex:'')+'/>');}
function gn_spacer(w,h,ex) {return(gn_IMG('spacer.gif',w,h,'',ex));}

var leftMenus=0;
function gn_NavMenu(who) { leftMenus=1; if(gn_menuList[who]!=null) { alert('Warning: Nav Menu \''+who+'\' is already defined'); return; } gn_menuList[who]=new Array(); var j; for (j=1; j<arguments.length; j++) { gn_menuList[who][j-1]=arguments[j]; } }

gn_HDmenuList=new Array();
function gn_HeaderMenu(who) { gn_HDmenuList[who]=new Array(); var j; for (j=1; j<arguments.length; j++) { gn_HDmenuList[who][j-1]=arguments[j]; } }

gn_ChannelMenuList=new Array();
function gn_ChannelMenu() { gn_MENU_MARGIN = gn_MENU_BASEMARGIN; gn_ChannelMenuList=new Array(); var j; for (j=0; j<arguments.length; j++) { gn_ChannelMenuList[j]=arguments[j]; } gn_writeLeftNav(); }
function gn_ChannelMenuIndent() { gn_MENU_MARGIN = gn_MENU_BASEMARGIN+gn_MENU_INDENT; gn_ChannelMenuList=new Array(); var j; for (j=0; j<arguments.length; j++) { gn_ChannelMenuList[j]=arguments[j]; } gn_writeLeftNav(); }

var gn_BoxMenuList;
function gn_BoxMenu(who) { var a=gn_BoxMenuList=new Array(); var j; for (j=0; j<arguments.length; j++) { a[j]=arguments[j]; } gn_writeBoxNav(a); }

if (gn_DHTML) gn_NavMenu('blank',"blank|/|blank"); var leftMenus=0;

function gn_genNavLayer(lev,who) {
  var w=gn_MENU_WIDTH; var id=(who)?('GNML_'+who):('gn_menuLayer'+lev);
  var build=gn_navtabletop +((gn_Mac||gn_NS4)?'':'<SPAN ID="'+id+'Area">')+gn_writeNavLayer(((who)?who:'blank'),lev,id)+((gn_Mac||gn_NS4)?'':'</SPAN>')+gn_navtablebot;
  document.gn_newLayer(id,build,(60+2*lev),gn_hidden,(gn_xmargin+gn_MENU_LEFT+(lev-1)*(gn_MENU_WIDTH-gn_MENU_OVERLAP)),0,w,null,'ONMOUSEOVER="gn_navEnter();" ONMOUSEOUT="gn_navExit();"');
  var l=gn_layers[id]; l.lev=lev; if (!(gn_Mac||gn_NS4)) gn_equalizeLayers(id+'Area');
  l.clipIt=new Function ("t","b"," if (gn_NS4) {this.clip.top=t; this.clip.bottom=b;} else { this.style.clip='rect('+t+' "+gn_MENU_WIDTH+" '+b+' 0)'; }");
  if (!who) { gn_genNavScroller(id,lev,'up',w); gn_genNavScroller(id,lev,'dn',w); }
}

function gn_genNavScroller(id,lev,which,w) {

  var build='<table border="0" cellspacing="0" cellpadding="0"><tr><td bgcolor="#C5C5C5">'
   + '<table border="0" cellspacing="1" cellpadding="0" width="'+w+'"><tr><td bgcolor="#E6E6E6" align="center">'
   + gn_IMG('nav_s_'+which+'.gif',9,12)
   + '</td></tr></table>'
   + '</td></tr></table>'

  document.gn_newLayer(id+which,build,(60+2*lev+1),gn_hidden,(gn_xmargin+gn_MENU_LEFT+(lev-1)*(gn_MENU_WIDTH-gn_MENU_OVERLAP)),0,null,null,'ONMOUSEOVER="gn_scrollNav(\''+lev+'\','+((which=='up')?8:-8)+'); gn_navEnter();" ONMOUSEOUT="gn_scrollNav(0); gn_navExit();"');
}


function gn_genHDNavLayers() {
  var w=gn_MENU_WIDTH; var i,build,id;
  for (i in gn_HDmenuList) {
    var build = gn_HDnavtabletop
      + gn_buildHDNavLayer(i)
    build += gn_HDnavtablebot;
    document.gn_newLayer('HDNavLayer_'+i,build,70,gn_hidden,gn_xmargin+174,85,w,null,'ONMOUSEOVER="overHD=1; gn_HDnavEnter();" ONMOUSEOUT="overHD=0; gn_HDnavExit();"');
  }
}


function gn_buildHDNavLayer(id) { var w=gn_MENU_WIDTH;

  var stuff='',menu = gn_HDmenuList[id]; if (menu==null) return null;
    var j,a,url,subhead,txt;
    var stuff='<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="'+(w-9)+'">' + '<TR><TD WIDTH="'+(w-9)+'">'+gn_spacer(w-9,5)+'</TD></TR>';
    for (j=0; j<menu.length; j++) {
      a=menu[j].split('|');
      url=a[1];
// mike
      if(url.substr(0,1) == '/') { url = gn_MENU_PREFIX + url; }
      if (url=='*')url=null;
      subhead=(url=='!')?1:0;

      itemid=id+'_item'+j;
      if (subhead) { stuff+='<TR><TD>'; }
      else {
        var temp='/*gn_hideLev(0,1);*/';
        stuff+='<TR><TD ID="'+itemid+'_HDcell"'+((gn_IE&&url)?' STYLE="cursor:hand;" ONCLICK="window.location=\''+url.replace(/\'/g,"//'")+'\';"':'')+' ONMOUSEOVER="gn_hoverLink(\''+itemid+'_HDlink\',1); '+temp+'" ONMOUSEOUT="gn_hoverLink(\''+itemid+'_HDlink\',0); clearTimeout(gn_HDpopupTimer);">';
      }
      
      if (subhead) {
        stuff+='<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0"><TR><TD HEIGHT="3"></TD></TR><TR><TD>'+gn_spacer(5,2)+'</TD><TD><SPAN CLASS="gnav3"><B>'+getLinkName(a[0])+'</B></SPAN></TD><TD>'+ gn_spacer(8,10) +'</TD></TR><TR><TD HEIGHT="3"></TD></TR></TABLE>';
      }
      else {
        stuff+='<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0"><TR><TD HEIGHT="3"></TD></TR><TR><TD VALIGN="top">'+ gn_IMG('bluebullet.gif',15,10) +'</TD>';
        if (url) { stuff+= '<TD><A CLASS="gnav1" HREF="' + ((url)? url:'javascript: // no link') + '" NAME="'+itemid+'_HDlink">'+getLinkName(a[0])+'</A></TD><TD>'+ gn_spacer(8,10) +'</TD>'; }
        else { stuff+='<TD><SPAN CLASS="gnav3">'+a[0]+'</SPAN></TD>'; }
        stuff+='</TR><TR><TD HEIGHT="3"></TD></TR></TABLE>';
      }


      stuff+='</TD></TR>'
    }
    stuff+='<TR><TD HEIGHT="8"></TD></TR></TABLE>';
    return stuff;
}


/*********************/
var gn_curHover=null; var gn_hoverTimer=setTimeout('',10);
function gn_hoverLink(id,n) { if (!(gn_IE||gn_NS6)) return;
  clearTimeout(gn_hoverTimer);
  if (n) { gn_hLink(gn_curHover,0); gn_hLink(id,1); }
  else { gn_hoverTimer=setTimeout('gn_hLink(gn_curHover,0);',1); }
}

function gn_hLink(id,n,menu) { if (!(gn_IE||gn_NS6))return; if (!id)return; 
  gn_curHover=(n)?id:null;
  var lnk=(gn_IE)?document.all[id]:document.links[id]; if (!lnk)return;
  if (!lnk.cls) { lnk.origcls=lnk.className;  lnk.cls=lnk.origcls; }
  if (menu) { lnk.cls=lnk.origcls+((n)?'On':''); } 
  lnk.className=lnk.cls+((n)? 'Over':'');
}
/*********************/


function gn_writeNavLayer(who,lev,id) { var w=gn_MENU_WIDTH;
  
  var stuff='',menu = gn_menuList[who]; if (menu==null) return null; var itemid;
    var j,a,name,url,submenu,subhead,txt;
    var stuff='<table border="0" cellspacing="0" cellpadding="0" width="'+(w-2)+'">' + '<tr><td width="15">'+gn_spacer(15,5)+'</TD><TD WIDTH="'+(w-32)+'">'+gn_spacer(w-32,5)+'</TD><TD WIDTH="15">'+gn_spacer(15,5)+'</TD></TR>';
    for (j=0; j<menu.length; j++) {
      a=menu[j].split('|'); 
      url=a[1];
// mike
      if(url.substr(0,1) == '/') { url = gn_MENU_PREFIX + url; }
      if (url=='*')url=null;
      name=getLinkName(a[0]);

      subhead=(url=='!')?1:0; submenu=a[2]; if (lev==3) submenu=null;
      var LEV=(gn_Mac)?('gn_layers.'+id+'.lev'):lev;

      if (submenu) itemid=submenu; else itemid=id+'_item'+j;
      if (subhead) { stuff+='<tr><td colspan="2"><TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0"><TR><TD HEIGHT="3"></TD></TR><TR><TD>'+gn_spacer(5,2)+'</TD><TD><SPAN CLASS="gnav3"><B>'+name+'</B></SPAN></TD><TD>'+ gn_spacer(8,10) +'</TD></TR><TR><TD HEIGHT="3"></TD></TR></TABLE></td></tr>'; }
      else {
        var temp=(submenu)?('gn_popupNav('+"'"+submenu+"'"+','+(LEV+((gn_Mac)?'+1':1))+',200);'):('gn_hideLev('+LEV+',1);');

        var MO = ' ONMOUSEOVER="gn_hoverLink(\''+itemid+'_link\',1); '+temp+'" ONMOUSEOUT="gn_hoverLink(\''+itemid+'_link\',0); clearTimeout(gn_popupTimer);"'
        stuff+='<tr'+(((gn_IE||gn_NS6)&&url)?' STYLE="cursor:hand;" ONCLICK="window.location=\''+url.replace(/\'/g,"//'")+'\';"'+MO:'')+'>'
        stuff+='<td></td>'
        stuff+='<td><table border="0" cellspacing="0" cellpadding="0"><tr><td height="3"></td></tr><tr><td>';

        if (url||submenu) { stuff+= '<A CLASS="gnav'+((url)?1:2)+'" HREF="' + ((url)? url:'javascript: // no link') + '" NAME="'+itemid+'_link"'+((gn_IE||gn_NS6)?'':MO)+'>'+name+'</A>'; }
        else { stuff+='<SPAN CLASS="gnav3">'+name+'</SPAN>'; }

        stuff+='</td></tr><tr><td height="3"></td></tr></table></td>';
        stuff+='<td>'+ ((submenu)? gn_IMG('nav_arrow.gif',15,10):'')+'</td>';
        stuff+='</tr>'

      }

    }

    stuff+='<tr><td height="8"></td></tr></table>'; if ((who=='blank')||(gn_Mac)) return stuff;
    
  if (gn_NS4) stuff=gn_navtabletop+stuff+gn_navtablebot;
  var l=gn_layers[id+((gn_NS4)?'':'Area')]; l.rewrite(stuff); return null; }


var gn_NS6offX, gn_NS6offY;
function gn_writeLayers() { if (!(gn_DHTML)) return;

  if (gn_IE) { gn_xmargin=eval(document.body.leftMargin); gn_ymargin=eval(document.body.topMargin); }
  if (gn_NS6) { gn_NS6offX=eval(document.body.offsetLeft); gn_NS6offY=eval(document.body.offsetTop); }

  //var mc=document.images.gn_margincheck;
  //if (gn_NS6) { if(mc) { gn_xmargin=gn_NS6offX+mc.offsetLeft-1; gn_ymargin=gn_NS6offY+mc.offsetTop-70; } }
  //if (gn_NS4) { if(mc) { gn_xmargin=mc.x-1; gn_ymargin=mc.y-70; } }

  if (gn_Mac) { var i; for(i in gn_menuList) gn_genNavLayer(1,i); }
  gn_genNavLayer(1); gn_genNavLayer(2); gn_genNavLayer(3);
  gn_genHDNavLayers();
  gn_init(); 
}


function gn_r(s,f,r) { var p=s.indexOf(f); while (p>=0) { s=s.substring(0,p)+r+s.substring(p+f.length); p=s.indexOf(f,p+r.length); } return(s); }
function gn_spRow() { var s='<tr>'; for (var j=0; j<arguments.length; j++) s+='<td>'+gn_spacer(arguments[j])+'</td>'; return(s+'</tr>'); }

function gn_writeBoxNav(list) { 

  var j,a,stuff='';
  var name,url,submenu,itemid; var id='99999';

  for (j=0; j<list.length; j++) {
    a=list[j].split('|');
    url=a[1];
// mike
      if(url.substr(0,1) == '/') { url = gn_MENU_PREFIX + url; }
      if (url=='*')url=null;

    if (url=='!') { stuff+='<tr><td height="3"></td></tr><tr><td></td><td class="gnav3"><b>'+a[0]+'</b></td></tr><tr><td height="3"></td></tr>'; }
    else {
      name=getLinkName(a[0]);
      submenu=a[2];
      if (submenu) itemid=submenu; 
      else itemid=id+'_item'+j;

      var temp=(submenu)?('gn_navOver('+"'"+submenu+"'"+',1,90);'):('/*gn_hideLev(0,1);*/');
      var MO=' ONMOUSEOVER="gn_hoverLink(\''+itemid+'_link\',1); '+temp+'" ONMOUSEOUT="gn_hoverLink(\''+itemid+'_link\',0); gn_navOut(\''+itemid+'\'); clearTimeout(gn_popupTimer);"'
      stuff+='<tr'+(((gn_IE||gn_NS6)&&url)?' STYLE="cursor:hand;" ONCLICK="window.location=\''+url.replace(/\'/g,"//'")+'\';"'+MO:'') +'>'
      + '<td></td>'
      + '<td><table border="0" cellspacing="0" cellpadding="0"><tr><td height="3"></td></tr><tr><td><a href="'+url+'" class="gnav1" name="'+itemid+'_link"'+((gn_IE||gn_NS6)?'':MO)+'>'+name+'</a></td></tr><tr><td height="3"></td></tr></table></td>'
      + '<td>'+(((a[2]))?gn_IMG('boxnav_arrow.gif',10,10):'')+'</td>'
      + '</tr>'
    }
  }

    var build='<table border="0" cellspacing="0" cellpadding="0" width="163">'
    + '<tr><td width="8">'+gn_spacer(8,2)+'</td> <td width="'+(139)+'">'+gn_spacer(139)+'</td> <td>'+gn_spacer(10)+'</td> <td>'+gn_spacer(6)+'</td> </tr>'
    + stuff
    + '<tr><td height="5"></td></tr>'
    + '</table>'

  document.write(build);
}

function inArray(A,v) { for (var j=0; j<A.length; j++) { if (A[j]==v)return(true); } return(false); }

var EnglishIcon = "E";
var EnglishALT = "Content In English";
var LockALT = "Password Protected Content";

function getEnglishIcon() {
  var EI=EnglishIcon.toLowerCase();
  var i='e'; var w=12; var h=12;
  if (EI=='i') { i='i'; }
  if (EI=='a') { i='a'; }
  if (EI=='kor') { i='kor'; w=34; h=14; }
  if (EI=='schn') { i='schn'; w=34; h=14; }
  if (EI=='tchn') { i='tchn'; w=34; h=14; }
  return('<img src="http://www.intel.com/sites/nav/pix/channel/inenglish_'+i+'.gif" width="'+w+'" height="'+h+'" align="top" border="0" alt="'+EnglishALT+'"/>');
}

function getLockIcon() { return('<img src="http://www.intel.com/sites/nav/pix/channel/navlock.gif" width="10" height="12" align="top" border="0" alt="'+LockALT+'"/>'); }

function getLinkName(name) {
  var flags=new Array();
  if (name.charAt(0)=='[') { var p=name.indexOf(']'); if (p>0) { flags=name.substring(1,p).toUpperCase().split(','); name=name.substring(p+1); } }
  if (inArray(flags,'E')) name+=getEnglishIcon();
  if (inArray(flags,'L')) name+=getLockIcon();
  return(name);
}


function gn_siteMenu(name,url,submenu) { 
// mike
  if(url.substr(0,1) == '/') { url = gn_MENU_PREFIX + url; }
  if (url=='*') url=null; var name=getLinkName(name); if (submenu) itemid=submenu; 
  var temp=(submenu)?('gn_navOver('+"'"+submenu+"'"+',1,73);'):('');
  var MO=' ONMOUSEOVER="gn_hoverLink(\''+submenu+'_link\',1); '+temp+'" ONMOUSEOUT="gn_hoverLink(\''+submenu+'_link\',0); gn_navOut(\''+submenu+'\'); clearTimeout(gn_popupTimer);"'
  var build='<table border="0" cellspacing="0" cellpadding="0">'
  + '<tr><td>'+gn_spacer(162,4)+'</td> <td>'+gn_spacer(15)+'</td> </tr>'
  + '<tr'+(((gn_IE||gn_NS6)&&url)?' STYLE="cursor:hand;" ONCLICK="window.location=\''+url.replace(/\'/g,"//'")+'\';"'+MO:'') +'>'
  + '<td><table border="0" cellspacing="0" cellpadding="0"><tr><td height="3"></td></tr><tr><td valign="top"><img src="http://www.intel.com/sites/nav/pix/bluebullet.gif" width="15" height="10"/></td><td><a href="'+url+'" class="siteidmenu" name="'+submenu+'_link"'+((gn_IE||gn_NS6)?'':MO)+'>'+name+'</a></td></tr><tr><td height="4"></td></tr></table></td>'
  + '<td>'+(((submenu))?gn_IMG('nav_arrow.gif',15,10):'')+'</td>'
  + '</tr></table>'
  document.write(build);
}

// this is the channel one
function gn_writeLeftNav() { 

  var j,a,o=gn_ChannelMenuList; 

  var name,url,submenu,itemid; var id='99999';
  var build='<table border="0" cellspacing="0" cellpadding="0" width="177">'
  + '<tr><td nowrap="nowrap" width="'+gn_MENU_MARGIN+'">'+gn_spacer(gn_MENU_MARGIN,2)+'</td><td width="100%">'+gn_spacer(100)+'</td> <td nowrap="nowrap">'+gn_spacer(15)+'</td> </tr>'

  for (j=0; j<o.length; j++) {
    a=o[j].split('|');
    url=a[1];
// mike
    if(url.substr(0,1) == '/') { url = gn_MENU_PREFIX + url; }
    if (url=='*')url=null;
    name=getLinkName(a[0]);
    
    submenu=a[2];
    if (submenu) itemid=submenu; 
    else itemid=id+'_item'+j;

    var temp=(submenu)?('gn_navOver('+"'"+submenu+"'"+',1,90);'):('/*gn_hideLev(0,1);*/');
    var MO=' ONMOUSEOVER="gn_hoverLink(\''+itemid+'_link\',1); '+temp+'" ONMOUSEOUT="gn_hoverLink(\''+itemid+'_link\',0); gn_navOut(\''+itemid+'\'); clearTimeout(gn_popupTimer);"'
    build+='<tr'+(((gn_IE||gn_NS6)&&url)?' STYLE="cursor:hand;" ONCLICK="window.location=\''+url.replace(/\'/g,"//'")+'\';"'+MO:'') +'>'
    + '<td></td>'
    + '<td><table border="0" cellspacing="0" cellpadding="0"><tr><td height="3"></td></tr><tr><td><a href="'+url+'" class="gnav1" name="'+itemid+'_link"'+((gn_IE||gn_NS6)?'':MO)+'>'+name+'</a></td></tr><tr><td height="3"></td></tr></table></td>'
    + '<td>'+(((a[2]))?gn_IMG('nav_arrow.gif',15,10):'')+'</td>'
    + '</tr>'

  }
    build+= '</table>'
  document.write(build);
}



function gn_ChannelMenu_NoScript() { 
  var name, url, j,a,o=gn_ChannelMenuList; 
  var build='<table border="0" cellspacing="0" cellpadding="0">\n'
  + '<tr><td>'+gn_spacer(15,5)+'</td><td>'+gn_spacer(147)+'</td><td>'+gn_spacer(15)+'</td></tr>\n'
  for (j=0; j<o.length; j++) {
    a=o[j].split('|'); url=a[1];
// mike
    if(url.substr(0,1) == '/') { url = gn_MENU_PREFIX + url; }
    if (url=='*')url=null;
    name=getLinkName(a[0]);
    build+='<tr><td></td><td><table border="0" cellspacing="0" cellpadding="0"><tr><td height="3"></td></tr><tr><td><a href="'+url+'" class="gnav1">'+name+'</a></td></tr><tr><td height="3"></td></tr></table></td></tr>\n'
  }
  build+= '</table>\n'
  document.write('<textarea rows="20" cols="80">'+build+'</textarea>');
}

function gn_BoxMenu_NoScript() { 
  var name, url, k,j,a,o;
  var stuff='';
  for (j=0; j<gn_BoxMenuList.length; j++) {
    a=gn_BoxMenuList[j].split('|'); url=a[1];
// mike
    if(url.substr(0,1) == '/') { url = gn_MENU_PREFIX + url; }
    if (url=='*')url=null;
    if (url=='!') { stuff+='<tr><td height="3"></td></tr><tr><td></td><td class="gnav3"><b>'+a[0]+'</b></td></tr><tr><td height="3"></td></tr>'; }
    else {
      stuff+='<tr><td></td><td><table border="0" cellspacing="0" cellpadding="0"><tr><td height="3"></td></tr><tr><td><a href="'+url+'" class="gnav1">'+getLinkName(a[0])+'</a></td></tr><tr><td height="3"></td></tr></table></td></tr>\n'
    }
  }

  var build='<table border="0" cellspacing="0" cellpadding="0">\n'
  + '<tr><td height="2">'+gn_spacer(8)+'</td><td></td><td>'+gn_spacer(8)+'</td></tr>\n'
  + stuff
  + '</table>\n'

  document.write('<textarea rows="20" cols="80">'+build+'</textarea>');
}


function gn_MM(e) { gn_findMouse(e); }
function gn_findMouse(e) { if (gn_NS4||gn_NS6) { gn_mouseY=e.pageY;} if (gn_IE) { gn_mouseY=event.clientY+document.body.scrollTop; } if (document.gn_oldmousemove) document.gn_oldmousemove(); }
function gn_setMM() { if (!leftMenus)return; if (document.onmousemove!=gn_MM) { document.gn_oldmousemove=document.onmousemove; document.onmousemove=gn_MM; } }
function gn_checkMM() { setInterval('gn_setMM()',1000); }

if (gn_NS4) document.captureEvents(Event.MOUSEMOVE);

if (gn_DHTML) {
  gn_loadGroup(gn_navPics,1);
  gn_loadGroup(gn_boxPics,1);
}

if (gn_NS4) {var gn_winW=window.innerWidth; var gn_winH=window.innerHeight; window.onresize=new Function("if ((gn_winW!=window.innerWidth)||(gn_winH!=window.innerHeight)) history.go(0);"); }
