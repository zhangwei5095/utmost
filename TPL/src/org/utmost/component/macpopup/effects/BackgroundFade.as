////////////////////////////////////////////////////////////////////////////////
//
// Copyright(c) 2006 Adobe Systems Incorporated. All Rights Reserved.
//
////////////////////////////////////////////////////////////////////////////////

package org.utmost.component.macpopup.effects
{
	import mx.effects.TweenEffect;
	import mx.effects.IEffectInstance;
	
	/**
 *  The SaturationFade effect changes the <code>filters</code> property of a component,
 *  adding a ColorMatrixFilter to affect it's color saturation level. A saturation
 *  level of 1.0 indicates the current color saturation level and a saturnation level
 *  of 0.0 indicates no color saturation (ie. grayscale). A saturation of -1.0 inverts
 *  the colors of the target.
 *  
 *  <p>If you omit values for the
 *  <code>saturationFrom</code> and <code>saturationTo</code> properties, 
 *  the effect automatically transitions from a saturation level of 1.0 to 0.0
 *
 *  @mxml
 *
 *  <p>The <code>&lt;mx:SaturationFade&gt;</code> tag
 *  inherits the tag attributes of its superclass,
 *  and adds the following tag attributes:</p>
 *  
 *  <pre>
 *  &lt;mx:SaturationFade 
 *    id="ID"
 *    saturationFrom="val"
 *    saturationTo="val"
 *  /&gt;
 *  </pre>
 *  
 *  @see com.adobe.effects.SaturationFadeInstance
 *
 */

	public class BackgroundFade extends TweenEffect
	{
		private static var AFFECTED_PROPERTIES:Array = [ "filters" ];
		
		public var fadeFrom:Number;
		public var fadeTo:Number;

		public function BackgroundFade( target : Object = null ){
	      	super( target );
	      	instanceClass = BackgroundFadeInstance;
	   	}
	   	
	   	override public function getAffectedProperties():Array {
	      	return AFFECTED_PROPERTIES;
	   	}
	   	
		override protected function initInstance(instance:IEffectInstance):void{
	      	super.initInstance( instance );
	      
	      	var backgroundFadeInstance:BackgroundFadeInstance = BackgroundFadeInstance( instance );
	
	      	backgroundFadeInstance.fadeFrom = fadeFrom;
	      	backgroundFadeInstance.fadeTo = fadeTo;
	   	}
		
	}
}