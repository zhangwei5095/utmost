package skins.brownie.skins
{
	import mx.skins.Border;
	import flash.display.Graphics;
	import mx.styles.StyleManager;
	import mx.utils.ColorUtil;

	public class ProgressIndeterminateSkin extends Border
	{
		public function ProgressIndeterminateSkin()
		{
			super();
		}
		//----------------------------------
		//  measuredWidth
	    //----------------------------------
    	/**
	    *  @private
    	*/    
	    override public function get measuredWidth():Number
	    {
	        return 195;
	    }
	
	    //----------------------------------
		//  measuredHeight
	    //----------------------------------
	
	    /**
	     *  @private
	     */        
	    override public function get measuredHeight():Number
	    {
	        return 6;
	    }
	
	    //--------------------------------------------------------------------------
	    //
	    //  Overridden methods
	    //
	    //--------------------------------------------------------------------------
	
	
	    /**
	     *  @private
	     */        
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			// User-defined styles
			var barColorStyle:* = getStyle("barColor");
			var barColor:uint = StyleManager.isValidStyleValue(barColorStyle) ?
								barColorStyle :
								getStyle("themeColor");
	
			var barColor0:Number = ColorUtil.adjustBrightness2(barColor, 60);
			var hatchInterval:Number = getStyle("indeterminateMoveInterval");
			if (isNaN(hatchInterval))
				hatchInterval = 28;
			var g:Graphics = graphics;
			g.clear();
	
			// Hatches
			for (var i:int = 0; i < w; i += hatchInterval)
			{
				g.beginFill(barColor, 0.8);
				g.moveTo(i, 1);
				g.lineTo(Math.min(i + 14, w), 1);
				g.lineTo(Math.min(i + 10, w), h - 1);
				g.lineTo(Math.max(i - 4, 0), h - 1);
				g.lineTo(i, 1);
				g.endFill();
			}
		}
	}
}