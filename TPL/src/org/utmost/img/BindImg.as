package org.utmost.img
{
	/**
	 * 图标资源
	 * */
	[Bindable]
	public class BindImg
	{
		public function BindImg()
		{
		}
		[Embed(source="img/mini_icons/page_new.gif")]
		public static var page_new:Class;
		[Embed(source="img/mini_icons/page_edit.gif")]
		public static var page_edit:Class;
		[Embed(source="img/mini_icons/page_delete.gif")]
		public static var page_delete:Class;
		[Embed(source="img/mini_icons/icon_details.gif")]
		public static var icon_details:Class;
		
		[Embed(source="img/mini_icons/action_refresh.gif")]
		public static var action_refresh:Class;
		[Embed(source="img/mini_icons/action_refresh_blue.gif")]
		public static var action_refresh_blue:Class;
		
		
		[Embed(source="img/mini_icons/page_next.gif")]
		public static var page_next:Class;
		[Embed(source="img/mini_icons/page_prev.gif")]
		public static var page_prev:Class;
		
	}
}