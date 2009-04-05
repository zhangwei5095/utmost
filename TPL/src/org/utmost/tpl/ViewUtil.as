package org.utmost.tpl
{
	import mx.collections.ArrayCollection;
	import mx.containers.Form;
	import mx.containers.FormItem;
	import mx.controls.AdvancedDataGrid;
	import mx.controls.ComboBox;
	import mx.controls.DataGrid;
	import mx.controls.NumericStepper;
	import mx.controls.TextArea;
	import mx.controls.TextInput;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.UIComponent;
	import mx.managers.ToolTipManager;
	import mx.rpc.events.ResultEvent;
	
	import org.utmost.service.AutoService;
	
	/**
	 * 自动生成View 工具类
	 * */
	public class ViewUtil
	{
		public function ViewUtil()
		{
		}
		
		public static function buildComponent(o:Object):UIComponent {
			var type:String=o.fieldtype;
			var ui:Object=new Object();
			if(type=="TextInput") {
				ui=new TextInput();
				ui.text=o.defaultvalue;
				ui.maxChars=o.fieldlength;
				//约束
				if(o.restrict!="") {
					(ui as TextInput).restrict=o.restrict;
				}
//				(ui as TextInput)..=o.fieldname;
//				ToolTipManager.createToolTip(o.fieldname,ui.x,ui.y);
			}
			
			if(type=="TextArea") {
				ui=new TextArea();
				ui.text=o.defaultvalue;
				ui.maxChars=o.fieldlength;
			}
			
			if(type=="ComboBox") {
				ui=new ComboBox();
				if(o.reference!="none") {
					ViewUtil.setReference(ui,o.reference);
				}
			}
			
			if(type=="NumericStepper") {
				ui=new NumericStepper();
				ui.maximum=o.fieldlength;
				ui.width=80;
			}
			ui.name=o.fieldcode;
			ui.id=o.fieldcode;
			
			//处理显示状态
			if(o.state=="03") {
				ui.enabled=false;
			}
			return ui as UIComponent;
		}
		private static function setReference(_ui:Object,_reference:String):void {
				var service:AutoService=new AutoService();
				service.findByHql("from U_TPL_REFERENCE v where " + 
						"v.refname='"+_reference+"' order by dataorder",function(e:ResultEvent):void {
					var ac:ArrayCollection=e.result as ArrayCollection;
					var dp:ArrayCollection=new ArrayCollection();
					for(var i:int=0;i<ac.length;i++) {
						var o:Object=ac.getItemAt(i);
						var obj:Object=new Object();
						obj.label=o.datalabel;
						obj.value=o.datavalue;
						dp.addItem(obj);
					}
					(_ui as ComboBox).dataProvider=dp;
				});
		}
		public static function buildForm(ac:ArrayCollection):Form {
			var form:Form=new Form();
			form.id="form";
			for(var i:int=0;i<ac.length;i++) {
				var o:Object=ac.getItemAt(i);
				var fi:FormItem=new FormItem();
				//处理隐藏
				if(o.state=="02") {
					fi.visible=false;
					fi.includeInLayout=false;
				}
				if(o.required=="true") {
					fi.required=true;
				}
				fi.label=o.fieldname;
				fi.addChild(ViewUtil.buildComponent(o));
				form.addChild(fi);
			}
			return form;
		}
		
		public static function buildDataGrid(ac:ArrayCollection):DataGrid {
			var dg:DataGrid=new DataGrid();
			var arrCol:ArrayCollection=new ArrayCollection();
			for(var i:int=0;i<ac.length;i++) {
				var col:DataGridColumn=new DataGridColumn();
				var o:Object=ac.getItemAt(i);
				col.headerText=(String)(o.fieldname);
				col.dataField=(String)(o.fieldname);
				arrCol.addItem(col);
			}
			dg.columns=arrCol.toArray();
			return dg;
		}
		
		public static function buildAdvancedDataGrid(ac:ArrayCollection):AdvancedDataGrid {
			var dg:AdvancedDataGrid=new AdvancedDataGrid();
			var arrCol:ArrayCollection=new ArrayCollection();
			for(var i:int=0;i<ac.length;i++) {
				var col:AdvancedDataGridColumn=new AdvancedDataGridColumn();
				var o:Object=ac.getItemAt(i);
				//处理隐藏
				if(o.state=="02") {
					continue;
				}
				col.headerText=(String)(o.fieldname);
				col.dataField=(String)(o.fieldcode);
				arrCol.addItem(col);
			}
			dg.columns=arrCol.toArray();
			return dg;
		}
	}
}