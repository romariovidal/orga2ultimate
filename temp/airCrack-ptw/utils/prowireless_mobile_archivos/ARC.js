/*
Alternate Row Colors -- ARC

When I am added to a page, I look for tables that have a CSS class of "ARC".
I then alternate the background colors for each row in the table.

To do this I manipulate the table row's bgColor property. Note however, that 
applying other classes or using inline styles can override the bgColor 
property, which would negate my effects.

Default colors are hardcoded in my code, but can be changed using the method calls
defined below.

Options:

ARC.setColor1( "#000000" );
   I set color one to the hex value specified.

ARC.setColor2( "#000000" );
   I set color two to the hex value specified.

ARC.setFirstRowColor( "#FF0000" );
   I set the first row, usually a header row, to the hex value specified.

ARC.noFirstRow();
   For tables without a header row, I don't treat the first as special.
  
*/


// ===================================================== AlternateRowColors

function AlternateRowColors()
{
  this.arcClass       = "ARC";     // default class name
  this.color1         = "#FFFFFF"; // default
  this.color2         = "#E3E3E3"; // default
  this.firstRowColor  = "#FFFFFF"; // default
  
  this.allTables      = [];
  this.arcTables      = [];
  this.firstRow       = true;
  this.old_onload     = window.onload;
  window.onload       = this.init;
  window.arcReference = this;
};
AlternateRowColors.prototype.noFirstRow       = ARC_noFirstRow;
AlternateRowColors.prototype.init             = ARC_init;
AlternateRowColors.prototype.setColor1        = ARC_setColor1;
AlternateRowColors.prototype.setColor2        = ARC_setColor2;
AlternateRowColors.prototype.setFirstRowColor = ARC_setFirstRowColor;
AlternateRowColors.prototype.quickHexTest     = function( hexColor )
	{
		if( hexColor.indexOf("#") == -1 ){ hexColor = "#" + hexColor};
		var RE1 = /#[0-9A-Ga-g]{6}/;
		return RE1.test(hexColor);
	};

	
// ------------------------------------------------------ ARC_init
function ARC_init()
{
	if( !document.getElementsByTagName ){ return; };
	
	var ARC = window.arcReference;
	
	ARC.allTables = document.getElementsByTagName("table");
	
	for(var i=0; i < ARC.allTables.length; i++)
	{
		if( ARC.allTables[i].className.toLowerCase().indexOf(ARC.arcClass.toLowerCase()) != -1 )
		{ 
			ARC.arcTables = ARC.arcTables.concat( ARC.allTables[i] );
		};
	};
  
	if(ARC.arcTables.length < 1){ return; };
	
	for(var jab = 0; jab < ARC.arcTables.length; jab++)
	{
		var table = ARC.arcTables[jab];

		if(ARC.firstRow) { table.rows[0].bgColor = ARC.firstRowColor; };
		
		var rowCount;
		(ARC.firstRow) ? (rowCount = 1) : (rowCount = 0); 
		
		for(rowCount; rowCount < table.rows.length; rowCount++)
		{
			var tableRow = table.rows[rowCount];
			var rowColor;
			(rowCount%2 == 0) ? (rowColor = ARC.color1) : (rowColor = ARC.color2);
			tableRow.bgColor = rowColor;
		};
	};
};

// ------------------------------------------------------ ARC_noFirstRow
function ARC_noFirstRow()
{
	this.firstRow = false;
};	

// ------------------------------------------------------ ARC_setColor1
function ARC_setColor1( hexColor )
{
	if( this.quickHexTest(hexColor) ){ this.color1 = hexColor; };
	return;
};

// ------------------------------------------------------ ARC_setColor2
function ARC_setColor2( hexColor )
{
	if( this.quickHexTest(hexColor) ){ this.color2 = hexColor; };
	return;
};

// ------------------------------------------------------ ARC_setFirstRowColor
function ARC_setFirstRowColor( hexColor )
{
	if( this.quickHexTest(hexColor) ){ this.firstRowColor = hexColor; };
	return;
};


// instantiation call
var ARC = new AlternateRowColors();