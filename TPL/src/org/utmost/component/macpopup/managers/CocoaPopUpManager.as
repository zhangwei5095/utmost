package org.utmost.component.macpopup.managers
{
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.events.Event;
	
	import mx.containers.Panel;
	import mx.core.Application;
	import mx.core.FlexMovieClip;
	import mx.core.IFlexAsset;
	import mx.core.IFlexDisplayObject;
	import mx.core.Singleton;
	import mx.core.UIComponent;
	import mx.core.mx_internal;
	import mx.effects.AnimateProperty;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	import mx.managers.IPopUpManager;
	import mx.managers.PopUpManager;
	import mx.managers.SystemManager;
	
	import org.utmost.component.macpopup.effects.BackgroundFadePopupBlocker;
	
	use namespace mx_internal;
	
	/**
	 * There is a lot that can be improved on, in this class, but it is basically a proxy
	 * to the popupmanager implementation stored as a singleton in the mx_internal namespace
	 * 
	 * I did look at creating my own implementation of the popup manager but I guess its bad
	 * practice to step into the mx_internal namespace.
	 * 
	 * If you do make any improvements or find any bugs please let me know 
	 * Campbell Anderson
	 * campbell@xsive.co.nz
	 * 
	 **/
	
	public class CocoaPopUpManager
	{
		protected static var fader:Class = BackgroundFadePopupBlocker;
		protected static var storedMWC:Class;
	    
	    public function CocoaPopUpManager(){
	    }
	    
	    public static function createPopUp(parent:DisplayObject,
                                       className:Class,
                                       modal:Boolean = false,
                                       childList:String = null):IFlexDisplayObject
	    {   
			checkOverlay(parent as DisplayObjectContainer);
			var popup:IFlexDisplayObject = PopUpManager.createPopUp(parent, className, modal, childList);
			showSheet(popup, parent as DisplayObjectContainer);
            
			return popup;
	    }
	    
	    public static function addPopUp(window:IFlexDisplayObject,
                    parent:DisplayObject,
                    modal:Boolean = false,
                    childList:String = null):void
	    {
	    	checkOverlay(parent as DisplayObjectContainer);
			PopUpManager.addPopUp(window, parent, modal, childList);
			showSheet(window, parent as DisplayObjectContainer);
	    }
	    
	    public static function removePopUp(window:IFlexDisplayObject):void{
	    	removeSheet(window);
	    }
	    
	    private static function checkOverlay(parent:DisplayObjectContainer):void{
			if (parent is Application)
				setupOverlay(fader);
		}
		
		private static function showSheet(window:IFlexDisplayObject, parent:DisplayObjectContainer):void{
			
			var x:Number;
			var y:Number;
			var cornerRadius:Number = UIComponent(window).getStyle("cornerRadius") as Number;
			if (parent is SystemManager)
			{
				
				x = (Application.application.screen.width - window.width) / 2;
				y = -window.height - cornerRadius;
			}
			else
			{
				x = parent.x + ((parent.width - window.width) / 2);
				y = parent.y -window.height - cornerRadius;
				if(parent is Panel){
					var phh:Number = Panel(parent).getStyle('headerHeight');
					y += (phh)? phh : 30; // If style is not set on panel use default height, which has no accesor :(
				}
					
				window.mask = drawMask(parent as Sprite);
			}

			// Set my position, because my parent won't do it for me.
			window.move(Math.round(x), Math.round(y));
			tweenPosition(window, window.height);
			
		}
		
		private static function removeSheet(window:IFlexDisplayObject):void{
			tweenPosition(window, -window.height, CocoaPopUpManager.remove);
		}
		
		private static function remove(event:EffectEvent):void{
			PopUpManager.removePopUp(event.currentTarget.target as IFlexDisplayObject);
			setupOverlay(storedMWC);
		}
		
		// Setup for popupManager
		private static function setupOverlay(type:Object):void{
			var ipm:IPopUpManager =Singleton.getInstance("mx.managers::IPopUpManager") as IPopUpManager;
			var o:Object = ipm;
			var p:QName = new QName(mx_internal, "modalWindowClass");
			storedMWC = o[p];
			o[p] = type;
		}
		
		private static function drawMask(bounds:Sprite):UIComponent{
			var largeMask:UIComponent = new UIComponent();
			largeMask.graphics.beginFill(0x00FFFF, 0.5);
			
			var topOffset:Number = 0;
			if(bounds is Panel){
				var phh:Number = Panel(bounds).getStyle('headerHeight');
				topOffset += (phh)? phh : 30;
			}
        	largeMask.graphics.drawRoundRect(bounds.x-10, bounds.y+topOffset, bounds.width+20, 
            bounds.height+10, 0);   
        	largeMask.graphics.endFill();
			bounds.parent.addChild(largeMask);
			return largeMask;
		}
		
		private static function tweenPosition(window:IFlexDisplayObject, slidepixels:int, callback:Function = null ):void{
			//create Tweens
			var tween:AnimateProperty = new AnimateProperty();
			tween.property = "y"; // For now this popup only tweens up and down
			tween.fromValue = window.y;
			tween.toValue = window.y + slidepixels;
			tween.target = window;
			// If we have a callback add it
			if(callback != null)	
				tween.addEventListener(EffectEvent.EFFECT_END, callback );
			tween.play();
		}

	}
	
}
	