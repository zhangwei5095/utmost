/*
Copyright (c) 2008 www.kingnare.com  See:
http://code.google.com/p/kingnarestyle
or http://www.kingnare.com/auzn

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the NumericStepperSkin in
the Software without restriction, NumericStepperSkinmitation the rights to
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
	
	public class NumericStepperUpSkin extends Border
	{
		public function NumericStepperUpSkin()
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
				case "upArrowUpSkin":
				{
					break;
				}
				case "upArrowOverSkin":
				{
					arrowColors = [0xFFFFFF, 0xFFFFFF];
					break;
				}
				case "upArrowDownSkin":
				{
					highlightAlphas = [0.05, 0.01];
					arrowColors = [0x666666, 0x666666];
					break;
				}
				case "upArrowDisabledSkin":
				{
					blackFrameAlphas = [0.4, 0.4];
					highlightAlphas = [0.05, 0.01];
					arrowColors = [0x666666, 0x666666];
					arrowAlphas = [0.6, 0.6];
					break;
				}
			}
			//frame
			DrawUtil.drawDoubleRect(g, blackFrameColors, blackFrameAlphas, 0, 0, rectW-1, rectH, 1, 1, rectW-3, rectH-2);
			DrawUtil.drawDoubleRect(g, highlightColors, highlightAlphas, 1, 1, rectW-3, rectH-2, 2, 2, rectW-5, rectH-4);
			DrawUtil.drawSingleRect(g, innerRectColors, innerRectAlphas, 2, 2, rectW-5, rectH-4);
			DrawUtil.drawSingleRect(g, rightLineColors, rightLineAlphas, rectW-1, 0, 1, rectH);
			//arrow
			DrawUtil.drawArrow(g, 5, arrowColors, arrowAlphas, Math.floor(((rectW-1) - 7)/2), Math.floor(rectH/2) - Math.floor(5/2), false);
		} 
	}
}