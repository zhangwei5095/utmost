package org.utmost.util
{
	import flash.events.KeyboardEvent;

	import mx.core.Container;

	public class ShortcutKeyUtil
	{

		public function ShortcutKeyUtil()
		{
		}

		public static function setFocusAndSubmit(container:Container, func:Function):void
		{
			ShortcutKeyUtil.setShortcutKey(container,13,func);//Enter
		}
		public static function setFocusAndCancel(container:Container, func:Function):void
		{
			ShortcutKeyUtil.setShortcutKey(container,27,func);//ESC
		}
		public static function setFocusAndDelete(container:Container, func:Function):void
		{
			ShortcutKeyUtil.setShortcutKey(container,127,func);//Delete
		}

		public static function setShortcutKey(container:Container, charCode:int, func:Function):void
		{
			container.setFocus();
			container.addEventListener(KeyboardEvent.KEY_DOWN, function(e:KeyboardEvent):void
				{
					//trace("e.charCode:"+e.charCode);
					if (e.charCode == charCode)
						func.call();
			});
		}
	}
}

