////////////////////////////////////////////////////////////////////////////////
//
// Copyright(c) 2006 Adobe Systems Incorporated. All Rights Reserved.
//
////////////////////////////////////////////////////////////////////////////////

package org.utmost.component.macpopup.effects
{
	import flash.filters.ColorMatrixFilter;
	import mx.core.mx_internal;
	import mx.effects.effectClasses.PropertyChanges;
	import mx.effects.effectClasses.TweenEffectInstance;
	
	public class BackgroundFadeInstance extends TweenEffectInstance
	{
		public function BackgroundFadeInstance( target : Object ){
	      	super( target );
	   	}
	   	
	   	public static const IDENTITY_MATRIX : Array = [ 1, 0, 0, 0, 0,
                                                     0, 1, 0, 0, 0,
                                                     0, 0, 1, 0, 0,
                                                     0, 0, 0, 1, 0 ];
                                                     
	   	private static const R_LUM : Number = 0.1;
	   	private static const G_LUM : Number = 0.1;
	   	private static const B_LUM : Number = 0.1;
	   	private var originalFilters : Array;
	   	
	   	public var fadeFrom:Number;

   		public var fadeTo:Number;
   		
   		override public function play():void{
		      super.play();
		
		      originalFilters = target.filters;      
		      var values:PropertyChanges = propertyChanges;
		      
		      if ( isNaN( fadeFrom ) ) {
		         if ( fadeTo > 0 )
		            fadeFrom = 0;
		         else
		            fadeFrom = 1;
		      }
		      if ( isNaN( fadeTo ) ) {
		         if ( fadeFrom > 0 )
		            fadeTo = 0;
		         else
		            fadeTo = 1;
		      }      
		      
		      tween = createTween( this, fadeFrom, fadeTo, duration );
		      applyColorMatrixFilter( Number( tween.mx_internal::getCurrentValue( 0 ) ) );
		   }

		   override public function onTweenUpdate( value : Object ):void{
		      applyColorMatrixFilter( Number( value ) );
		   }

		   private function applyColorMatrixFilter( saturation : Number ) : void{
		      var newFilters : Array = new Array();
		      for ( var i : uint = 0; i < originalFilters.length; i++ ){
		         if ( originalFilters[ i ] is ColorMatrixFilter == false )   
		            newFilters.push( originalFilters[ i ] );
		      }
		      
		      newFilters.push( buildColorMatrixFilter( saturation ) ); 
		      target.filters = newFilters;      
		   }

		   private function buildColorMatrixFilter( s : Number ) : ColorMatrixFilter {
		      var inverseS : Number = 1 - s;
		      
		      var irlum:Number = inverseS * R_LUM;
		      var iglum:Number = inverseS * G_LUM;
		      var iblum:Number = inverseS * B_LUM;
		      
		      var mat:Array =  [ irlum + s, iglum    , iblum    , 0, 0,
		                         irlum    , iglum + s, iblum    , 0, 0,
		                         irlum    , iglum    , iblum + s, 0, 0,
		                         0        , 0        , 0        , 1, 0 ];
		     
		      var temp:Array = new Array();
		      var i:Number = 0;
		
		      for ( var y : Number = 0; y < 4; y++ ){
		         for (var x : Number = 0; x < 5; x++ ){
		            temp[ i + x ] = mat[ i ]     * IDENTITY_MATRIX[ x ] + 
		                            mat[ i + 1 ] * IDENTITY_MATRIX[ x +  5 ] + 
		                            mat[ i + 2 ] * IDENTITY_MATRIX[ x + 10 ] + 
		                            mat[ i + 3 ] * IDENTITY_MATRIX[ x + 15 ] +
		                            ( x == 4 ? mat[ i + 4 ] : 0 );
		         }
		         i+=5;
		      }
		      
		      return new ColorMatrixFilter( temp );
		   }
   		
	}
}