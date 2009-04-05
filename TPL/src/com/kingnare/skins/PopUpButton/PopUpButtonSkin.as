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
package com.kingnare.skins.PopUpButton
{
	import com.kingnare.skins.util.DrawUtil;
	import flash.display.Graphics;
	import mx.skins.Border;

	public class PopUpButtonSkin extends Border
	{
		public function PopUpButtonSkin()
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
            
            var rectW:Number = w;
			var rectH:Number = h;
			var hightlightAlphas:Array = [0.1, 0.1];
			var innerRectColors:Array = [0xFFFFFF, 0xFFFFFF];
			var innerRectAlphas:Array = [0.08, 0.03];
			var arrowColors:Array = [0x808080, 0x808080];
			
			switch(name)
			{
				case "upSkin":
				{
					break;
				}
				case "popUpOverSkin":
				case "overSkin":
				{
					innerRectAlphas = [0.15, 0.05];
					arrowColors = [0xFFFFFF, 0xFFFFFF];
					break;
				}
				case "popUpDownSkin":
				case "downSkin":
				{
					hightlightAlphas = [0.15, 0.15];
					innerRectAlphas = [0.05, 0.0];
					arrowColors = [0xCCCCCC, 0xCCCCCC];
					break;
				}
				case "disabledSkin":
				{
					hightlightAlphas = [0.05, 0.05];
					innerRectColors = [0x000000, 0x000000];
					innerRectAlphas = [0.3, 0.3];
					arrowColors = [0x000000, 0x000000];
					break;
				}
			}
			
			//
			DrawUtil.drawDoubleRect(g, [0xFFFFFF, 0xFFFFFF], hightlightAlphas, 0, 0, rectW, rectH, 1, 1, rectW-2, rectH-2);
			DrawUtil.drawDoubleRect(g, [0x000000, 0x000000], [0.6, 0.6], 1, 1, rectW-2, rectH-2, 2, 2, rectW-4, rectH-4);
			DrawUtil.drawSingleRect(g, innerRectColors, innerRectAlphas, 2, 2, rectW-4, rectH-4);
			DrawUtil.drawDoubleRect(g, [0xFFFFFF, 0xFFFFFF], [0.08, 0.03], 2, 2, rectW-4, rectH-4, 3, 3, rectW-6, rectH-6);
			//vertical lines
			DrawUtil.drawSingleRect(g, [0x000000, 0x000000], [0.85, 0.6], rectW-18, 4, 1, rectH-8);
			DrawUtil.drawSingleRect(g, [0xFFFFFF, 0xFFFFFF], [0.15, 0.05], rectW-18+1, 4, 1, rectH-8);
			//arrow
			DrawUtil.drawArrow(g, 5, arrowColors, [1, 1], rectW - 13, Math.floor(rectH/2) - Math.floor(5/2));
		} 
		
	}
}