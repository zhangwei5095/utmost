package com.sephiroth.controls
{
	import flash.events.Event;
	
	import com.sephiroth.controls.treeClasses.TreeCheckListData;
	import com.sephiroth.renderers.TreecheckboxItemRenderer;
	
	import mx.controls.Tree;
	import mx.controls.listClasses.BaseListData;
	import mx.controls.treeClasses.TreeListData;
	import mx.core.ClassFactory;
	import mx.core.mx_internal;
	import mx.events.TreeEvent;
	
	use namespace mx_internal;

	[Event(name="checkFieldChanged", type="flash.events.Event")]
	[Event(name="checkFunctionChanged", type="flash.events.Event")]
	[Event(name="itemCheck", type="mx.events.TreeEvent")]
	
	public class TreeCheckBox extends Tree
	{
		protected var _checkField: String;
		private var _checkFunction: Function;
		
		public function TreeCheckBox()
		{
			super( );
			this.itemRenderer = new ClassFactory( TreecheckboxItemRenderer );
			addEventListener( "itemCheck", checkHandler );
		}
		
	    mx_internal function isBranch( item: Object ): Boolean
	    {
	        if ( item != null )
	            return _dataDescriptor.isBranch( item, iterator.view );
	        return false;
	    }		
		
 		private function checkHandler( event: TreeEvent ): void
	    {
	    	var value: String;
	        var state: int = ( event.itemRenderer as TreecheckboxItemRenderer ).checkBox.checkState;
	        var middle: Boolean = ( state & 2 << 1 ) == ( 2 << 1 );
	        var selected: Boolean = ( state & 1 << 1 ) == ( 1 << 1 );

			if( isBranch( event.item ) )
			{
				middle = false;
			}  
	        
	        if( middle )
	        {
	            value = "2";
	        } else {
	            value = selected ? "1" : "0";
	        }
	        
	        var data:Object = event.item;

	        if (data == null) {
	            return;
	        }
	
	        if( checkField )
	        {
	            if ( data is XML )
	            {
	                try
	                {
	                       data[ checkField ] = value;
	                }
	                catch ( e: Error )
	                {
	                }
	            } else if ( data is Object ) {
	                try
	                {
	                    data[ checkField ] = value;
	                }
	                catch( e: Error )
	                {
	                }
	            }
	         }
	
	        if ( data is String ) {
	            data = String( value );
	        }
	        commitProperties( );
	    }
		
		
	    override protected function makeListData( data: Object, uid: String, rowNum: int): BaseListData
	    {
	        var treeListData: TreeListData = new TreeCheckListData( itemToLabel( data ), uid, this, rowNum );
	        initListData( data, treeListData );
	        return treeListData;
	    }
	    
	    override protected function initListData( item: Object, treeListData: TreeListData ): void
	    {
	    	super.initListData( item, treeListData );
	    	
	        if (item == null)
	            return;
	
	        ( treeListData as TreeCheckListData ).checkedState = itemToCheck( item );
	    }	    
		
    	[Bindable("checkFieldChanged")]
    	[Inspectable(category="Data", defaultValue="checked")]		
	    public function get checkField( ): String
	    {
	        return _checkField;
	    }
	
	    public function set checkField( value: String ): void
	    {
	        _checkField = value;
	        itemsSizeChanged = true;
	        invalidateDisplayList( );
	        dispatchEvent( new Event("checkFieldChanged") );
	    }
	    
	    public function itemToCheck( data: Object ): int
	    {
	        if (data == null )
	            return 0;
	
	        if ( checkFunction != null )
	            return checkFunction( data );
	
	        if ( data is XML )
	        {
	            try
	            {
	                if ( data[ checkField ].length( ) != 0 )
	                    data = data[ checkField ];
	            } catch( e: Error )
	            {
	            }
	        }
	        else if ( data is Object )
	        {
	            try
	            {
	                if ( data[ checkField ] != null )
	                    data = data[ checkField ];
	            } catch( e: Error )
	            {
	            }
	        }
	
	        if ( data is String )
	            return parseInt( String( data ) );
	
	        try
	        {
	            return parseInt( String( data ) );
	        }
	        catch( e: Error )
	        {
	        }
	        return 0;
	    }
	    
	    [Bindable("checkFunctionChanged")]
	    [Inspectable(category="Data")]
	
	    public function get checkFunction( ): Function
	    {
	        return _checkFunction;
	    }
	
	    public function set checkFunction( value: Function ): void
	    {
	        _checkFunction = value;
	        itemsSizeChanged = true;
	        invalidateDisplayList( );
	        dispatchEvent( new Event("checkFunctionChanged") );
	    }	    
	    
	    
	    
	    //----------------------------
	    public function updateTreeStatus(xml:XML, event:TreeEvent):void
			{
				if (xml.children().length() > 0)
				{
					for each(var x:XML in xml.node)
					{
						trace("x:" + x);
						if (x.@checked == "1")
							updateParents(x, (event.itemRenderer as TreecheckboxItemRenderer).checkBox.checkState);
						//updateChilds(x, (event.itemRenderer as TreecheckboxItemRenderer).checkBox.checkState);
						updateTreeStatus(x, event);
					}
				}
			}

			/**
			 * Called on checkbox click
			 * check and update for both parents and child nodes
			 * according to the checkbox status
			 */
			public function onItemCheck(event:TreeEvent):void
			{
				updateParents(event.item as XML, (event.itemRenderer as TreecheckboxItemRenderer).checkBox.checkState);
				updateChilds(event.item as XML, (event.itemRenderer as TreecheckboxItemRenderer).checkBox.checkState);
			}

			/**
			 * @see it.sephiroth.controls.CheckBoxExtended#checkState
			 *
			 */
			public function updateChilds(item:XML, value:uint):void
			{
				var middle:Boolean=(value & 2 << 1) == (2 << 1);
				var selected:Boolean=(value & 1 << 1) == (1 << 1);
				//trace("item.children():"+item.children());
				if (item.children().length() > 0 && !middle)
				{
					for each(var x:XML in item.node)
					{
						x.@checked=value == (1 << 1 | 2 << 1) ? "2" : value == (1 << 1) ? "1" : "0";
						updateChilds(x, value);
					}
				}
			}

			public function updateParents(item:XML, value:uint):void
			{
				var checkValue:String=(value == (1 << 1 | 2 << 1) ? "2" : value == (1 << 1) ? "1" : "0");
				var parentNode:XML=item.parent();
				//trace("item.toXMLString():" + item.toXMLString());
				//trace("item.parent():"+item.parent());
				if (parentNode)
				{
					//trace("t1:"+parentNode.toXMLString());
					for each(var x:XML in parentNode.node)
					{
						if (x.@checked != checkValue)
						{
							checkValue="2"
						}
					}
					parentNode.@checked=checkValue;
					updateParents(parentNode, value);
				}
			}
			public function flashTree(_tree:Tree):void
			{
				//trace(mytree.itemRenderer);
				var e:TreeEvent=new TreeEvent("itemCheck");
				_tree.selectedIndex=1;
				e.itemRenderer=_tree.itemToItemRenderer(_tree.selectedItem);
				var xml:XML=_tree.selectedItem as XML;
				updateTreeStatus(xml, e);
			}
	}
}