package com.sephiroth.controls.treeClasses
{
	import mx.controls.listClasses.ListBase;
	import mx.controls.treeClasses.TreeListData;

	public class TreeCheckListData extends TreeListData
	{
		public var checkedState: uint = 0;
		
		public function TreeCheckListData( text: String, uid: String, owner: ListBase, rowIndex: int = 0, columnIndex: int = 0 )
		{
			super( text, uid, owner, rowIndex, columnIndex );
		}
		
	}
}