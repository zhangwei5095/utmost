package com.sephiroth.controls
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import com.sephiroth.skin.CheckBoxExtendedIcon;
	
	import mx.controls.Button;
	import mx.controls.ButtonLabelPlacement;
	import mx.core.FlexVersion;
	import mx.core.IFlexDisplayObject;
	import mx.core.mx_internal;
	import mx.events.FlexEvent;
	import mx.styles.ISimpleStyleClient;
	
	use namespace mx_internal;
	
	/* middleUpIcon
	 * the default icon to show when middle state is
	 * selected
	 */
	[Style(name="middleUpIcon", type="Class", inherit="no")]
	
	/* middleOverIcon
	 * middle state with mouse over
	 */
	[Style(name="middleOverIcon", type="Class", inherit="no")]
	
	/* middleDownIcon
	 * middle state with mouse pressed
	 */
	[Style(name="middleDownIcon", type="Class", inherit="no")]
	
	/* disabledMiddleIcon
	 * middle selected but component is disabled
	 */
	[Style(name="disabledMiddleIcon", type="Class", inherit="no")]
	
	
	[Exclude(name="emphasized", kind="property")]
	[Exclude(name="toggle", kind="property")]
	
	[DefaultBindingProperty(source="selected", destination="selected")]
	[DefaultTriggerEvent("click")]

	[IconFile("CheckBox.png")]
	public class CheckBoxExtended extends Button
	{
		mx_internal var _middle:Boolean = false;
		protected var _selected:Boolean = false;
		private var selectedSet:Boolean;
		private var swatch:CheckBoxExtendedIcon;
		private var icons:Array = [];
		private var skins:Array = [];
		
		// icons
		protected var upIcon:IFlexDisplayObject;
		protected var overIcon:IFlexDisplayObject;
		protected var downIcon:IFlexDisplayObject;
		protected var disabledIcon:IFlexDisplayObject;
		protected var selectedUpIcon:IFlexDisplayObject;
		protected var selectedOverIcon:IFlexDisplayObject;
		protected var selectedDownIcon:IFlexDisplayObject;
		protected var selectedDisabledIcon:IFlexDisplayObject;		
		protected var middleUpIcon:IFlexDisplayObject;
		protected var middleDownIcon:IFlexDisplayObject;
		protected var middleOverIcon:IFlexDisplayObject;
		protected var disabledMiddleIcon:IFlexDisplayObject;
		
		protected var upIconName:String = "upIcon";
		protected var overIconName:String = "overIcon";
		protected var downIconName:String = "downIcon";
		protected var disabledIconName:String = "disabledIcon";
		protected var selectedUpIconName:String = "selectedUpIcon";
		protected var selectedOverIconName:String = "selectedOverIcon";
		protected var selectedDownIconName:String = "selectedDownIcon";
		protected var selectedDisabledIconName:String = "selectedDisabledIcon";		
		protected var middleUpIconName:String   = "middleUpIcon";
		protected var middleDownIconName:String = "middleDownIcon";
		protected var middleOverIconName:String = "middleOverIcon";
		protected var disabledMiddleIconName:String = "disabledMiddleIcon";

		
		// skins
		protected var upSkinName:String = null;
		protected var overSkinName:String = "";
		protected var downSkinName:String = "";
		protected var disabledSkinName:String = "";
		protected var selectedUpSkinName:String = "";
		protected var selectedOverSkinName:String = "";
		protected var selectedDownSkinName:String = "";
		protected var selectedDisabledSkinName:String = "";
		
		mx_internal var ALLOW_3RD_STATE_FOR_USER:Boolean = false;
		
		/**
		 * Constructor
		 * 
		 */
		public function CheckBoxExtended()
		{
			super();
			_toggle = true;
			centerContent = false;
			extraSpacing = 8;
			skins = null;
		}    


		override protected function createChildren():void
		{
			super.createChildren();
			upIcon = createSkin( "upIcon", CheckBoxExtendedIcon );
			overIcon = createSkin( "overIcon", CheckBoxExtendedIcon );
			downIcon = createSkin( "downIcon", CheckBoxExtendedIcon );
			disabledIcon = createSkin( "disabledIcon", CheckBoxExtendedIcon );
			selectedUpIcon = createSkin( "selectedUpIcon", CheckBoxExtendedIcon );
			selectedOverIcon = createSkin( "selectedOverIcon", CheckBoxExtendedIcon );
			selectedDownIcon = createSkin( "selectedDownIcon", CheckBoxExtendedIcon );
			selectedDisabledIcon = createSkin( "selectedDisabledIcon", CheckBoxExtendedIcon );
			middleUpIcon = createSkin( "middleUpIcon", CheckBoxExtendedIcon );
			middleOverIcon = createSkin( "middleOverIcon", CheckBoxExtendedIcon );
			middleDownIcon = createSkin( "middleDownIcon", CheckBoxExtendedIcon );
			disabledMiddleIcon = createSkin( "disabledMiddleIcon", CheckBoxExtendedIcon );
		}

		/**
		 * 
		 * @param skinName
		 * @param defaultSkin
		 * @return 
		 * 
		 */
		protected function createSkin( skinName:String, defaultSkin:Class ) : IFlexDisplayObject
		{
			// Look up the skin by its name to see if it is already created. Note
			// below where addChild() is called; this makes getChildByName possible.
			var newSkin:IFlexDisplayObject = IFlexDisplayObject(getChildByName(skinName));
			// if the skin needs to be created it will be null...			
			if (!newSkin)
			{
				// Attempt to get the class for the skin. If one has not been supplied
				// by a style, use the default skin.	
				var newSkinClass:Class = Class(getStyle(skinName));
				if( !newSkinClass ) newSkinClass = defaultSkin;
				if (newSkinClass)
				{
					// Create an instance of the class.
					newSkin = IFlexDisplayObject(new newSkinClass());
					if( !newSkin ) newSkin = new defaultSkin();
					// Set its name so that we can find it in the future
					// using getChildByName().
					newSkin.name = skinName;
					// Make the getStyle() calls in the skin class find the styles
					// for this Gauge instance. In other words, but setting the styleName
					// to 'this' it allows the skin to query the component for styles. For
					// example, when the skin code does getStyle('backgroundColor') it 
					// retrieves the style from this Gauge and not from the skin.
					var styleableSkin:ISimpleStyleClient = newSkin as ISimpleStyleClient;
					if (styleableSkin)
					{
						styleableSkin.styleName = this;
					}
					// Make sure the skin is a proper child of the component
					newSkin.visible = false
					addChild(DisplayObject(newSkin));
				}
			}
			return newSkin;
		}
	    
		[Inspectable(environment="none")]
		override public function set emphasized(value:Boolean):void
	    {
	    }
	    
	    
	    [Inspectable(environment="none")]
	    override public function set toggle(value:Boolean):void
	    {
	    }
	    
	
	    override protected function measure():void
	    {
	        super.measure();
	
	        if ( FlexVersion.compatibilityVersion < FlexVersion.VERSION_3_0 )
	        {
	            var textHeight:Number = measureText(label).height;
	            var iconHeight:Number = currentIcon ? currentIcon.height : 0;
	    
	            var h:Number = 0;
	    
	            if (labelPlacement == ButtonLabelPlacement.LEFT ||
	                labelPlacement == ButtonLabelPlacement.RIGHT)
	            {
	                h = Math.max(textHeight, iconHeight);
	            }
	            else
	            {
	                h = textHeight + iconHeight;
	    
	                var verticalGap:Number = getStyle("verticalGap");
	                if (iconHeight != 0 && !isNaN(verticalGap))
	                    h += verticalGap;
	            }
	    
	            measuredMinHeight = measuredHeight = Math.max(h, 18);
	        }
	    }
	    
	    /**
	    * Return the current check state including
	    * both the middle and selected status
	    * 1 << 1 for selected
	    * 2 << 1 for middle selection
	    * 1 << 1 | 2 << 1 for both states
	    */
	    public function get checkState( ): uint
	    {
	    	return ( selected ? 1 << 1 : 0 ) | ( middle ? 2 << 1 : 0 );
	    }

		public function get middle():Boolean
		{
			return _middle;
		}

		public function set middle(value:Boolean):void
		{
			selectedSet = true;
			if (_middle != value)
			{
				_selected = false;				
				_middle = value;
				invalidateDisplayList();
				dispatchEvent(new Event(Event.CHANGE));
				dispatchEvent(new FlexEvent(FlexEvent.VALUE_COMMIT));
			}
		}
		
		override public function set selected(value:Boolean):void
		{
			selectedSet = true;
			if (_selected != value)
			{
				_selected = value;
				invalidateDisplayList();
				dispatchEvent(new Event(Event.CHANGE));
				dispatchEvent(new FlexEvent(FlexEvent.VALUE_COMMIT));
			}
		}
		
		override public function get selected():Boolean
		{
			return _selected;
		}


	    override mx_internal function viewSkin():void
	    {
	    }

		override mx_internal function getCurrentIconName():String
		{
			var tempIconName:String;
			if(!middle)
			{
				if (!enabled)
				{
					tempIconName = selected ? selectedDisabledIconName : disabledIconName;
				} else if (phase == "up")
				{
					tempIconName = selected ? selectedUpIconName : upIconName;
				} else if (phase == "over")
				{
					tempIconName = selected ? selectedOverIconName : overIconName;
				} else if (phase == "down")
				{
					tempIconName = selected ? selectedDownIconName : downIconName;
				}
			} else {
				if (!enabled)
				{
					tempIconName = disabledMiddleIconName;
				} else if (phase == "up")
				{
					tempIconName = middleUpIconName;
				} else if (phase == "over")
				{
					tempIconName = middleOverIconName;
				} else if (phase == "down")
				{
					tempIconName = middleDownIconName;
				}				
			}	
			return tempIconName;
		}
		

		override mx_internal function getCurrentIcon():IFlexDisplayObject
		{
			// Determine which icon will get displayed, based on whether this
			// Button is enabled or disabled, whether it is
			// selected or unselected, and how it is currently interacting
			// with the mouse (i.e., the up/over/down state).	
			var tempIconName:String = getCurrentIconName();	
	
			if (!tempIconName)
			{
				return null;
			}
	
		 	// Has this icon already been created?
			var newIcon:IFlexDisplayObject = IFlexDisplayObject(getChildByName(tempIconName));
	
			// If not, create it.
			if (newIcon == null)
			{
				//get specific icon style
				var newIconClass:Class = Class(getStyle(tempIconName));
				//default to the more general icon style
				if (newIconClass == null)
					newIconClass = Class(getStyle(iconName));
	
				if (newIconClass != null)
				{
					newIcon = IFlexDisplayObject(new newIconClass());
	
					// Set its name so that we can find it in the future
					// using getChildByName().
					newIcon.name = tempIconName;
	
					if (newIcon is ISimpleStyleClient)
						ISimpleStyleClient(newIcon).styleName = this;
	
					addChild(DisplayObject(newIcon));
	
					// Keep track of all skin children that have been created.
					icons.push(newIcon);
				}
			}
	
			return newIcon;
		}


		/**
		 * Default click handler
		 * @param event
		 * 
		 */
		override protected function clickHandler(event:MouseEvent):void
		{
			if (!enabled)
			{
				event.stopImmediatePropagation();
				return;
			}
	
			if (toggle)
			{
				if(allow3StateForUser)
				{
					if(!middle)
					{
						if(!selected)
						{
							middle = true;
						} else {
							selected = false;
						}
					} else {
						middle = false;
						selected = true;
					}
				} else {
					selected = !selected;
				}
			}
		}

		
		
		/**
		 * By default 3rd state must be enabled by code, user cannot
		 * enable it by clicking the component.
		 * setting this property to true, users will be able to select
		 * the 3rd state.
		 * 
		 * @param value
		 * 
		 */
		public function set allow3StateForUser(value:Boolean):void
		{
			ALLOW_3RD_STATE_FOR_USER = value;
		}
		
		public function get allow3StateForUser():Boolean
		{
			return ALLOW_3RD_STATE_FOR_USER;
		}
		
	}
}