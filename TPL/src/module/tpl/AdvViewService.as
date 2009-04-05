package module.tpl
{
	import mx.collections.ArrayCollection;
	import mx.rpc.events.ResultEvent;
	
	import org.utmost.service.AutoService;
	
	public class AdvViewService
	{
		public function AdvViewService(_mName:String,_fun:Function)
		{
			mName=_mName;
			fun=_fun;
		}
		public var mName:String;
		public var fun:Function;
		public function viewCopyToAdvView(cid:String):void {
			trace("viewCopyToAdvView:"+mName+"  "+cid);
			var service:AutoService=new AutoService();
			var hql:String="from U_TPL_TEMPLATEVIEW v where v.cid='"+cid+"'";
			service.findByHql(hql,saveToAdvView);
		}
		private function saveToAdvView(e:ResultEvent):void {
			if(e.result==null) {
				return;
			}
			var ac:ArrayCollection=e.result as ArrayCollection;
			var newAc:ArrayCollection=new ArrayCollection();
			for(var i:int=0;i<ac.length;i++) {
				var o:Object=ac.getItemAt(i);
				o.view=mName;
				o.vpid=o.uuid;
				o.uuid=null;
				newAc.addItem(o);
			}
			var service:AutoService=new AutoService();
			service.saveOrUpdateAll("U_TPL_TEMPLATEVIEWBYADV",newAc,function(e:ResultEvent):void{
				fun.call();
			});
		}
	}
}