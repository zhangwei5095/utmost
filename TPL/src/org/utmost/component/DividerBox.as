package org.utmost.component
{

	import flash.events.MouseEvent;

	import mx.containers.Box;
	import mx.containers.DividedBox;
	import mx.controls.Image;
	import mx.core.Application;

	public class DividerBox extends Box
	{
		[Embed(source='img/portal/left.jpg')]
		public static const DividerIcon_left:Class;

		[Embed(source='img/portal/right.jpg')]
		public static const DividerIcon_right:Class;

		private var image:Image=new Image();
		private var bOpened:Boolean=true;

		private var dividbox:DividedBox=null;

		public function DividerBox()
		{
			super();
			this.width=12;
			this.height=72;
			this.clipContent=true;
			image.width=12;
			image.height=72;
			this.addChild(image);

			//我这个是在一个HdividedBox中，其默认对dividerbox进行了旋转，所以需要旋转回来。  
			image.rotation=90;
//			
//			image.addEventListener(MouseEvent.MOUSE_OVER,function():void {
//				thissetCursor();
//				trace("over");
//			});
//			image.addEventListener(MouseEvent.MOUSE_OUT,function():void {
//				clearCursor();
//				trace("out");
//			});
			image.addEventListener(MouseEvent.CLICK, onMouseClick);
		}

		override protected function createChildren():void
		{
			super.createChildren();
			dividbox=this.parent.parent.parent as DividedBox;
			image.source=DividerIcon_left;
		}

		public function onMouseClick(event:MouseEvent):void
		{
			if (bOpened)
			{
				image.source=DividerIcon_right;
				dividbox.getChildAt(0).width=0;
				bOpened=false;
			}
			else
			{
				bOpened=true;
				image.source=DividerIcon_left;
				dividbox.getChildAt(0).width=220;
			}
		}

//		import mx.managers.CursorManager;
//
//		private function setCursor(cursorClass:Class=null):void
//		{
//
//			CursorManager.setCursor(cursorClass);
//
//		}
//
//		private function clearCursor():void
//		{
//
//			CursorManager.removeAllCursors();
//
//		}
	}
}

