package org.utmost.util
{
	import flash.display.DisplayObject;
	
	import mx.controls.ComboBox;
	import mx.core.Container;
	
	/**
	 * 页面 组件 value 映射工具类
	 * */
	public class AutoUtil
	{
		public function AutoUtil()
		{
		}
		
		/**
		 * 获取元素ID
		 * */
		private static function getId(obj:Object):String {
			var tmp:String=obj.toString();
			var arr:Array=tmp.split(".");
			if(arr.length>0) 
				tmp=arr[arr.length-1];
			return tmp;
		}
		/**
		 * 获取元素Name
		 * */
		private static function getName(obj:Object):String {
			var tmp:String;
			if(obj.hasOwnProperty("name")) {
				tmp=obj.name;
			}
			return tmp;
		}
		/**
		 * 获得元素类型
		 * */
		private static function getType(obj:Object):String {
			var tmp:String=obj.toLocaleString();
			tmp=tmp.replace("[object ","").replace("]","");
			return tmp;
		}
		
		/**
		 * 根据元素类型获得ValueType
		 * */
		private static function getVT(s:String):String {
			if(s=="TextInput"||s=="TextArea")
				return "text";
			if(s=="Button") 
				return "label";
			if(s=="NumericStepper")
				return "value";
			if(s=="ComboBox")
				return "value";
			return null;
		}
		/**
		 * 获取容器内组件id-value 映射
		 * */
		public static function findValue(s:Container,_obj:Object=null):Object {
			var obj:Object;
			if(_obj==null) {
				obj=new Object();
			}else {
				obj=_obj;
			}
			var arr:Array=s.getChildren();
			for(var i:int=0;i<arr.length;i++) {
				if(arr[i].hasOwnProperty("getChildren"))
					if(arr[i].getChildren()!==null) {
						findValue(arr[i],obj);
					}
				var id:String=getId(arr[i]);
				var name:String=getName(arr[i]);
				var type:String=getType(arr[i]);
				var vp:String=getVT(type);
				var value:String;
				if(type=="FormItem"||type=="Button"||type=="FormHeading") 
					continue;
				//ComboBox特殊处理
				if(type=="ComboBox"&&arr[i].selectedItem!=null) {
					if(arr[i].hasOwnProperty("@value")) {
						value=arr[i].selectedItem.@value;
					}else if(arr[i].hasOwnProperty("value")) {
						value=arr[i].selectedItem.value;
					}else {
						trace(id+" not find property value or @value !");
					}
					
				}
				if(vp!=null&&type!="ComboBox") 
					value=arr[i][vp];
				if(type=="TextInput"||type=="TextArea"||type=="ComboBox"||type=="NumericStepper") {
					obj[id]=value;
				}
				trace(id+"--"+name+"--"+type+"--"+value);
			}
			return obj;
		}
		
		/**
		 * 清除容器内所有元素value
		 * */
		public static function clearValue(_o:Container):void {
			var arr:Array=_o.getChildren();
				for(var i:int=0;i<arr.length;i++) {
					if(arr[i].hasOwnProperty("getChildren")) {
						if(arr[i].getChildren()!==null) {
							clearValue(arr[i]);
						}
					}
					var o:Object=arr[i];
					var id:String=AutoUtil.getId(o);
					var name:String=getName(o);
					var type:String=AutoUtil.getType(o);
					var valuetype:String=AutoUtil.getVT(type);
					//var value:String=data[id];
					if(type=="TextInput"||type=="TextArea"||type=="NumericStepper") {
						o[valuetype]="";
					}
					if(type=="ComboBox") {
						ComponentUtil.selectValue(o as ComboBox,"none");
					}
			}
		}
		/**
		 * 根据id 获取组件实例
		 * */
		public static function getObjById(_id:String,_o:Container):DisplayObject {
			var arr:Array=_o.getChildren();
				for(var i:int=0;i<arr.length;i++) {
					if(arr[i].hasOwnProperty("getChildren")) {
						if(arr[i].getChildren()!==null) {
							var tempObj:Object=getObjById(_id,arr[i]);
							if(tempObj!=null) {
								return tempObj as DisplayObject;
							}
						}
					}
					var o:Object=arr[i];
					var id:String=AutoUtil.getId(o);
					//trace("getObjById:"+id+"--"+_id);
					if(id==_id) {
						trace("return getObjById:"+id+"--"+_id);
						return o as DisplayObject;
					}
			}
			return null;
		}
		/**
		 * 填充元素值
		 * */
		public static function fillValue(_o:Container,data:Object):void {
			var arr:Array=_o.getChildren();
			for(var i:int=0;i<arr.length;i++) {
				if(arr[i].hasOwnProperty("getChildren")) {
					if(arr[i].getChildren()!==null) {
						fillValue(arr[i],data);
					}
				}
				var o:Object=arr[i];
				var id:String=AutoUtil.getId(o);
				var name:String=getName(o);
				var type:String=AutoUtil.getType(o);
				var valuetype:String=AutoUtil.getVT(type);
				var value:String=data[id];
				//trace(id+"-->"+name+"-->"+type+"-->"+valuetype+"-->"+value);
				if(data[id]!=undefined) {
					trace(id+"-->"+name+"-->"+type+"-->"+valuetype+"-->"+value);
					if(data.hasOwnProperty(id)) {
						if(type=="TextInput"||type=="TextArea"||type=="NumericStepper") {
							o[valuetype]=value;
						}
						if(type=="ComboBox") {
							ComponentUtil.selectValue(o as ComboBox,value);
						}
					}
				}
				/*else {
						if(type=="TextInput"||type=="TextArea"||type=="NumericStepper") {
							o[valuetype]="";
						}
						if(type=="ComboBox") {
							//ComponentUtil.selectValue(o as ComboBox,"none");
						}
				}*/
			}
		}
		
	}
}