package org.utmost.effect
{
	import flash.display.DisplayObject;
	
	import mx.core.IFlexDisplayObject;
	import mx.effects.Blur;
	import mx.events.TweenEvent;
	import mx.managers.PopUpManager;

	public class PopUpEffect
	{

		public function PopUpEffect()
		{
		}

		public static function show(control:IFlexDisplayObject, parent:DisplayObject, modal:Boolean=true):void
		{
			PopUpEffect.showByBlur(control,parent,modal);
		}
		public static function remove(control:IFlexDisplayObject):void
		{
			PopUpEffect.removeByBlur(control);
		}
		public static function showByBlur(control:IFlexDisplayObject, parent:DisplayObject, modal:Boolean=true):void
		{
			var mShowEffect:Blur=new Blur();
			mShowEffect.blurXFrom=255;
			mShowEffect.blurYFrom=255;
			mShowEffect.blurXTo=0;
			mShowEffect.blurYTo=0;
			mShowEffect.target=control;
			mShowEffect.duration=300;
			PopUpManager.addPopUp(control, parent, modal);
			PopUpManager.centerPopUp(control);
			mShowEffect.play();
		}
		
		public static function removeByBlur(control:IFlexDisplayObject):void
		{
			var ect:Blur=new Blur();
			ect.blurXFrom=0;
			ect.blurYFrom=0;
			ect.blurXTo=255;
			ect.blurYTo=255;
			ect.addEventListener(TweenEvent.TWEEN_END, function():void
				{
					PopUpManager.removePopUp(control);
			});
			ect.duration=300;
			ect.target=control;
			ect.play();
		}
		public static function removeByTest(control:IFlexDisplayObject):void
		{
			var ect:Blur=new Blur();
			ect.blurXFrom=0;
			ect.blurYFrom=0;
			ect.blurXTo=255;
			ect.blurYTo=255;
			ect.addEventListener(TweenEvent.TWEEN_END, function():void
				{
					PopUpManager.removePopUp(control);
			});
			ect.duration=300;
			ect.target=control;
			ect.play();
		}
	}
}

