package org.utmost.util
{
	import flash.events.MouseEvent;
	
	import flexlib.controls.ConvertibleTreeList;
	
	import mx.controls.ComboBox;
	import mx.controls.Tree;
	import mx.core.UIComponent;
	
	/**
	 * 组件工具类
	 * */
	public class ComponentUtil
	{
		public function ComponentUtil()
		{
		}
		/**
		 * 设置隐藏
		 * */
		public static function setVisible(o:UIComponent,bool:Boolean):void {
			o.visible=bool;
			o.includeInLayout=bool;
		}
		/**
		 * 设置Combox选中value
		 * */
		public static function selectValue(o:ComboBox,value:String):void {
			var arr:Array=o.dataProvider.source;
			for(var i:int=0;i<arr.length;i++) {
				if(arr[i]!=null&&arr[i].hasOwnProperty("value")) {
					if(value==arr[i].value.toString()) {
						o.selectedIndex=i;
						return;
					}
				}else {
					trace(o+"对象中未找到value属性");
				}
			}
			//o.selectedIndex=-1;
		}

		/**
		 * 全部展开 关闭tree结点  index=0
		 * */
        public static function ocAllTree(tree:Tree):void {
			tree.selectedIndex=0;
			if(tree!=null&&tree.selectedItem!=null) {
				if(tree.isItemOpen(tree.selectedItem))
					tree.expandChildrenOf(tree.selectedItem,false);
				else 
					tree.expandChildrenOf(tree.selectedItem,true);
			}
        }
        
        /**
		 *全部展开 关闭tree选择节点
		 * */
        public static function ocSelectTree(e:MouseEvent):void {
        	var tree:Tree=e.currentTarget as Tree;
        	if(tree==null) {
        		tree=(e.currentTarget as ConvertibleTreeList).tree;//特殊处理
        	}
			if(tree!=null&&tree.selectedItem!=null) {
				if(tree.isItemOpen(tree.selectedItem))
					tree.expandChildrenOf(tree.selectedItem,false);
				else 
					tree.expandChildrenOf(tree.selectedItem,true);
			}
        }
        
        
	}
}