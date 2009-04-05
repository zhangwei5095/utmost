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
package com.kingnare.skins.util
{
	import flash.display.GradientType;
	import flash.display.Graphics;
	import flash.geom.Matrix;
	import flash.geom.Point;

	public class DrawUtil
	{
		public function DrawUtil()
		{
		}
		
		/**
		 * 
		 * @param g
		 * @param level
		 * @param colors
		 * @param alphas
		 * draw up or down arrow.
		 */		
		public static function drawArrow(g:Graphics, 
										 level:Number, 
										 colors:Array, 
										 alphas:Array, 
										 x:Number, 
										 y:Number, 
										 direct:Boolean = true):void
		{
			var count:Number = 0;
			var positive:int = 1;
			var total:uint = 0;
			level += 2;
			if(!direct)
			{
				total = Math.floor(level/2);
				positive = -1;
			}
			
			while (level-2>-2)
			{
				drawSingleRect(g, colors, alphas, x+count, y+total+positive*count, level, 1);
				count++;
				level -= 2;
			}
			
		}
		
		/**
		 * 
		 * @param g
		 * @param colors
		 * @param alphas
		 * @param startX
		 * @param startY
		 * @param w
		 * @param h
		 * @param innerX
		 * @param innerY
		 * @param innerW
		 * @param innerH
		 * @param needClear
		 * 
		 */
		public static function drawDoubleRect(g:Graphics,
											  colors:Array, 
											  alphas:Array,
											  startX:Number = 0, 
											  startY:Number = 0,
											  w:Number = 0, 
											  h:Number = 0,
											  innerX:Number = 0,
											  innerY:Number = 0,
											  innerW:Number = 0, 
											  innerH:Number = 0,
											  needClear:Boolean = false):void
		{
			if (needClear)
			{
				g.clear();
			}
			var gradientBoxMatrix:Matrix = new Matrix();
			gradientBoxMatrix.createGradientBox(w, h, Math.PI/2, 0, 0);
			g.beginGradientFill(GradientType.LINEAR, colors, alphas, null, gradientBoxMatrix);
			g.drawRect(startX, startY, w, h);
			g.drawRect(innerX, innerY, innerW, innerH);
			g.endFill();
		}
		
		/**
		 * 
		 * @param g
		 * @param colors
		 * @param alphas
		 * @param startX
		 * @param startY
		 * @param w
		 * @param h
		 * @param needClear
		 * 
		 */		
		public static function drawSingleRect(g:Graphics,
											  colors:Array, 
											  alphas:Array,
											  startX:Number = 0,
											  startY:Number = 0,
											  w:Number = 0, 
											  h:Number = 0,
											  needClear:Boolean = false):void
		{
			if (needClear)
			{
				g.clear();
			}
			var gradientBoxMatrix:Matrix = new Matrix();
			gradientBoxMatrix.createGradientBox(w, h, Math.PI/2, 0, 0);
			g.beginGradientFill(GradientType.LINEAR, colors, alphas, null, gradientBoxMatrix);
			g.drawRect(startX, startY, w, h);
			g.endFill();
		}
	}
}