package org.utmost.util
{
	import flash.events.NetStatusEvent;
	import flash.net.SharedObject;
	import flash.net.SharedObjectFlushStatus;
	import flash.net.registerClassAlias;
	
	import org.utmost.util.UtmostLSOBean;
	
	public class UtmostLSO
	{
		//public var lso:SharedObject=SharedObject.getLocal("utmost");
		public function UtmostLSO()
		{
		}
		private var lso:SharedObject;
		public function saveLSO(obj:UtmostLSOBean):void {
			flash.net.registerClassAlias("org.utmost.lso.UtmostLSOBean",UtmostLSOBean);
			lso=SharedObject.getLocal("utmost");
			lso.data.UtmostLSOBean=obj;
			try {
				var flashResult:String=lso.flush();
				//trace(flashResult);
				if(flashResult==SharedObjectFlushStatus.PENDING) {
					//trace("允许保存");
					lso.addEventListener(NetStatusEvent.NET_STATUS,onStatus);
				}else if(flashResult==SharedObjectFlushStatus.FLUSHED) {
					//trace("保存完成");			
				}else {
					trace(flashResult+"未知");
				}
			}catch(e:Error) {
				trace(e.message);
			}
		}
		public function onStatus(e:NetStatusEvent):void {
			trace(e.info.code);
			if(e.info.code=="SharedObject.Flush.Success") {
				
			}else if(e.info.code=="SharedObject.Flush.Failed") {
			
			}
			lso.removeEventListener(NetStatusEvent.NET_STATUS,onStatus);
		}
		public function defaultObj():Object {
			lso=SharedObject.getLocal("utmost");
			return lso.data.UtmostLSOBean;
		}
	}
}