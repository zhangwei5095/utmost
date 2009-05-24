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
			ShortcutKeyUtil.setShortcutKey(container,13,func);
		}
		public static function setFocusAndCancel(container:Container, func:Function):void
		{
			ShortcutKeyUtil.setShortcutKey(container,27,func);
		}

		public static function setShortcutKey(container:Container, charCode:int, func:Function):void
		{
			container.setFocus();
			container.addEventListener(KeyboardEvent.KEY_DOWN, function(e:KeyboardEvent):void
				{
					if (e.charCode == charCode)
						func.call();
			});
		}
	}
}

