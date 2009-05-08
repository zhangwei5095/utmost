package skins.brownie.skins
{
	import flash.display.BitmapData;
	
	import mx.core.BitmapAsset;
	import mx.skins.ProgrammaticSkin;

	public class ApplicationSkin extends ProgrammaticSkin
	{
		public function ApplicationSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number,    unscaledHeight:Number):void
		{
			graphics.clear();
			var backgroundColor:uint = getStyle("backgroundColor");
			var backgroundAlpha:Number = 1.0;
			graphics.beginFill(backgroundColor, backgroundAlpha);
			graphics.drawRect(0, 0, unscaledWidth, unscaledHeight);
			
			
			var backgroundImageClass:* = getStyle("backgroundImage");
			var backgroundRepeat:* = getStyle("backgroundRepeat");
			if(backgroundImageClass && backgroundRepeat == "repeat-x")
			{
				var backgroundImage:BitmapAsset = new backgroundImageClass();
				var bitmapData:BitmapData = backgroundImage.bitmapData;
				
				//bitmapData = new BitmapData(backgroundImageInstance.width,backgroundImageInstance.height, true);
				//bitmapData.draw(backgroundImageInstance,new Matrix());
				
				graphics.beginBitmapFill(bitmapData,null,true);
				graphics.drawRect(0, 0,unscaledWidth,backgroundImage.height);
				graphics.endFill();
			}
		}
	}
}