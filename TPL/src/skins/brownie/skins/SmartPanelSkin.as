package skins.brownie.skins
{
	import flash.display.DisplayObject;
	import flash.filters.DropShadowFilter;
	
	import mx.containers.Panel;
	import mx.core.mx_internal;
	import mx.skins.halo.PanelSkin;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.IStyleClient;
	import mx.styles.StyleManager;
	use namespace mx_internal;
	
	public class SmartPanelSkin extends PanelSkin
	{	
		private var backgroundComplete:Boolean;
		
		public function SmartPanelSkin()
		{
			super();
		}
		override mx_internal function drawBorder(w:Number, h:Number):void
		{
			super.drawBorder(w,h);

			var dropShadowStyleName:* = getStyle("dropShadowStyleName");
			if(dropShadowStyleName)
			{
				var dropShadowCSS:CSSStyleDeclaration = StyleManager.getStyleDeclaration("." + dropShadowStyleName);
				var dropShadow:DropShadowFilter = new DropShadowFilter();
				dropShadow.quality = 3;
				var size:* = dropShadowCSS.getStyle("size");
				var distance:* = dropShadowCSS.getStyle("distance");
				var alpha:* = dropShadowCSS.getStyle("alpha");
				var angle:* = dropShadowCSS.getStyle("angle");
				if(size != undefined)
				{
					dropShadow.blurX = size;
					dropShadow.blurY = size;
				} 
				if(distance != undefined) dropShadow.distance = distance;
				
				if(alpha != undefined) dropShadow.alpha = alpha;
				
				if(angle != undefined) dropShadow.angle = angle;
				filters = [dropShadow];
			}
		}
		override mx_internal function drawBackground(w:Number, h:Number):void
	    	{
	    		super.drawBackground(w,h);
	    		if(!parent || backgroundComplete) return;
	    		
	    		backgroundComplete = true; 
	    		var headerSkin:Class = getStyle("headerSkin");
	    		if(headerSkin && parent is Panel)
	    		{
	    			var headerInstance:DisplayObject = new headerSkin();
	    			//if(headerInstance is IStyleClient) IStyleClient(headerInstance).styleName = parent;
	    			headerInstance.width = w;
	    			headerInstance.height = getStyle("headerHeight");
	    			var panel:Panel = Panel(parent);
	    			panel.rawChildren.addChildAt(headerInstance,2);
	    		}
	    	}
	}
}