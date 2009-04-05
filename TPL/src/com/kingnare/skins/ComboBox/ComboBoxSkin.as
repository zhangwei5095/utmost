/*
Copyright (c) 2008 www.kingnare.com  See:
http://code.google.com/p/kingnarestyle
or http://www.kingnare.com/auzn

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

Except as contained in this notice, the Software shall not be used in any commercial purposes.
*/

package com.kingnare.skins.ComboBox
{
	import com.kingnare.skins.util.DrawUtil;
	import flash.display.GradientType;
	import flash.display.Graphics;
	import flash.geom.Matrix;
	import flash.utils.getTimer;
	import mx.skins.Border;

	public class ComboBoxSkin extends Border
	{
		public function ComboBoxSkin()
		{
			super();
		}
		
		
		/**
	     *  @private
	     */    
	    override public function get measuredWidth():Number
	    {
	        return 22;
	    }
	    
	    
	    /**
	     *  @private
	     */        
	    override public function get measuredHeight():Number
	    {
	        return 22;
	    }
	    
	    
		/**
		 * 
		 * @param w
		 * @param h
		 * 
		 */		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			var g:Graphics = graphics;
            g.clear();

			var hightlightAlphas:Array = [0.1, 0.1];
			var innerRectColors:Array = [0xFFFFFF, 0xFFFFFF];
			var innerRectAlphas:Array = [0.08, 0.03];
			var arrowColors:Array = [0x808080, 0x808080];

			var editable:Boolean = true;
			if (name.indexOf("editable") < 0)
			{
				editable = false;
			}
			
			switch(name)
			{
				case "editableUpSkin":
				case "upSkin":
				{
					break;
				}
				case "editableOverSkin":
				case "overSkin":
				{
					innerRectAlphas = [0.15, 0.05];
					arrowColors = [0xFFFFFF, 0xFFFFFF];
					break;
				}
				case "editableDownSkin":
				case "downSkin":
				{
					hightlightAlphas = [0.15, 0.15];
					innerRectAlphas = [0.05, 0.0];
					arrowColors = [0xCCCCCC, 0xCCCCCC];
					break;
				}
				case "editableDisabledSkin":
				case "disabledSkin":
				{
					hightlightAlphas = [0.05, 0.05];
					innerRectColors = [0x000000, 0x000000];
					innerRectAlphas = [0.3, 0.3];
					arrowColors = [0x000000, 0x000000];
					break;
				}
			}
			
			if(!editable)
			{
				/* var old:Number = flash.utils.getTimer();
				for(var i:uint=0;i<1000;i++)
				{
					DrawUtil.drawDoubleRect(g, [0x000000, 0x000000], [0.6, 0.6], 1, 1, w-2, h-2, 2, 2, w-4, h-4);
					//drawRoundRect(1, 1, w-2, h-2, null, [0x000000, 0x000000], [0.6, 0.6], gradientBoxMatrix, GradientType.LINEAR, null, {x:2,y:2,w:w-4,h:h-4,r:{tl:0,tr:0,bl:0,br:0}});
				}
				trace(flash.utils.getTimer()-old); */
				
				DrawUtil.drawDoubleRect(g, [0xFFFFFF, 0xFFFFFF], hightlightAlphas, 0, 0, w, h, 1, 1, w-2, h-2);
				DrawUtil.drawDoubleRect(g, [0x000000, 0x000000], [0.6, 0.6], 1, 1, w-2, h-2, 2, 2, w-4, h-4);
				DrawUtil.drawSingleRect(g, innerRectColors, innerRectAlphas, 2, 2, w-4, h-4);
				DrawUtil.drawDoubleRect(g, [0xFFFFFF, 0xFFFFFF], [0.08, 0.03], 2, 2, w-4, h-4, 3, 3, w-6, h-6);
				//
				DrawUtil.drawSingleRect(g, [0x000000, 0x000000], [0.85, 0.6], w-18, 4, 1, h-8);
				DrawUtil.drawSingleRect(g, [0xFFFFFF, 0xFFFFFF], [0.15, 0.05], w-18+1, 4, 1, h-8);
				//
				DrawUtil.drawArrow(g, 5, arrowColors, [1, 1], w - 13, Math.floor(h/2) - Math.floor(5/2));
			}
			else
			{
				DrawUtil.drawDoubleRect(g, [0xFFFFFF, 0xFFFFFF], hightlightAlphas, 0, 0, w, h, 0, 1, w-1, h-2);
				DrawUtil.drawDoubleRect(g, [0x000000, 0x000000], [0.6, 0.6], 0, 1, w-1, h-2, 0, 2, w-2, h-4);
				DrawUtil.drawSingleRect(g, innerRectColors, innerRectAlphas, 0, 2, w-2, h-4);
				DrawUtil.drawDoubleRect(g, [0xFFFFFF, 0xFFFFFF], [0.08, 0.03], 0, 2, w-2, h-4, 1, 3, w-4, h-6);
				//
				//DrawUtil.drawSingleRect(g, [0x000000, 0x000000], [0.85, 0.65], 0, 1, 1, h-2);
				//
				DrawUtil.drawArrow(g, 5, arrowColors, [1, 1], Math.floor(((w-1) - 7)/2), Math.floor(h/2) - Math.floor(5/2));
			}
		} 
	}
}