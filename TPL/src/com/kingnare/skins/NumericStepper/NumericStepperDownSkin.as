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
package com.kingnare.skins.NumericStepper
{
	import com.kingnare.skins.util.DrawUtil;
	import flash.display.Graphics;
	import mx.skins.Border;
	import mx.skins.halo.NumericStepperDownSkin;

	public class NumericStepperDownSkin extends Border
	{
		public function NumericStepperDownSkin()
		{
			super();
		}
		
		
		/**
		 *  @private
		 */
		override public function get measuredWidth():Number
		{
			return 18;
		}
		
	
		/**
		 *  @private
		 */
		override public function get measuredHeight():Number
		{
			return 11;
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
			var blackFrameColors:Array = [0x000000, 0x000000];
			var blackFrameAlphas:Array = [0.6, 0.6];
			var highlightColors:Array = [0xFFFFFF, 0xFFFFFF];
			var highlightAlphas:Array = [0.08, 0.03];
			var innerRectColors:Array = [0x000000, 0x000000];
			var innerRectAlphas:Array = [0.1, 0.1];
			var rightLineColors:Array = [0xFFFFFF, 0xFFFFFF];
			var rightLineAlphas:Array =  [0.1, 0.1];
			var arrowColors:Array = [0x808080, 0x808080];
			var arrowAlphas:Array = [1, 1];
			
			switch(name)
			{
				case "downArrowUpSkin":
				{
					break;
				}
				case "downArrowOverSkin":
				{
					arrowColors = [0xFFFFFF, 0xFFFFFF];
					break;
				}
				case "downArrowDownSkin":
				{
					highlightAlphas = [0.05, 0.01];
					arrowColors = [0x666666, 0x666666];
					break;
				}
				case "downArrowDisabledSkin":
				{
					blackFrameAlphas = [0.4, 0.4];
					highlightAlphas = [0.05, 0.01];
					arrowColors = [0x666666, 0x666666];
					arrowAlphas = [0.6, 0.6];
					break;
				}
			}
			
			DrawUtil.drawDoubleRect(g, blackFrameColors, blackFrameAlphas, 0, 0, rectW-1, rectH-1, 1, 0, rectW-3, rectH-2);
			DrawUtil.drawDoubleRect(g, highlightColors, highlightAlphas, 1, 0, rectW-3, rectH-2, 2, 1, rectW-5, rectH-4);
			DrawUtil.drawSingleRect(g, innerRectColors, innerRectAlphas, 2, 1, rectW-5, rectH-4);
			DrawUtil.drawDoubleRect(g, rightLineColors, rightLineAlphas, 0, 0, rectW, rectH, 0, 0, rectW-1, rectH-1); 
			//arrow
			DrawUtil.drawArrow(g, 5, arrowColors, arrowAlphas, Math.floor(((rectW-1) - 7)/2), Math.floor(rectH/2) - Math.floor(5/2));
		} 
	}
}