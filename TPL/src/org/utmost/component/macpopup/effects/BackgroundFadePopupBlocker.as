////////////////////////////////////////////////////////////////////////////////
//
// Copyright(c) 2006 Adobe Systems Incorporated. All Rights Reserved.
//
////////////////////////////////////////////////////////////////////////////////

package org.utmost.component.macpopup.effects
{
	import mx.core.UIComponent;
	import org.utmost.component.macpopup.effects.BackgroundFade;
	import flash.events.Event;
	import mx.effects.EffectManager;

	public class BackgroundFadePopupBlocker extends UIComponent
	{
	   /**
	    *  The original filters, before playing the effect
	    */ 
	   private var originalFilters : Array;
	   
	   /**
	    *  The currently playing effect, if any
	    */ 
	   private var effect : BackgroundFade;
	 
	   /** 
	    *  The duration of the saturation fade effect
	    */
	   public static var FADE_DURATION : Number = 1000;
	
	   /** 
	    *  The starting saturation level. 1 is the original level
	    */
	   public static var FADE_FROM : Number = 1;
	
	   /** 
	    *  The end saturation level. 0 is grayscale
	    */
	   public static var FADE_TO : Number = 0;
	 
	 
	   /**
	    * Constructor. Add listener for removal of popup.
	    */
	   public function BackgroundFadePopupBlocker(){
	      super();         
	      addEventListener( Event.REMOVED, popUpRemoved );
	   }
	    
	   /**
	    * Clean up when popup is removed.
	    */
	   private function popUpRemoved(event:Event):void {
	      if ( effect != null ){
	         effect.end();
	         effect = null;
	      }
	      parentApplication.filters = originalFilters;
	   }
	 
	 
	   /**
	    * Store existing filters and start playing the saturation
	    * effect.
	    */
	   protected override function createChildren():void {
	      super.createChildren();
	    
	      originalFilters = parentApplication.filters;
	    
	      effect = new BackgroundFade( parentApplication );
	      effect.duration = FADE_DURATION;
	      effect.fadeFrom = FADE_FROM;
	      effect.fadeTo = FADE_TO;
	    
	      effect.play();
	   }
	}
}
