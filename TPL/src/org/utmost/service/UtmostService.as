package org.utmost.service
{

	/**
	 * 远程对象服务封装
	 * */
	public class UtmostService
	{
		import mx.collections.ArrayCollection;
		import mx.controls.Alert;
		import mx.core.Container;
		import mx.rpc.events.FaultEvent;
		import mx.rpc.events.ResultEvent;
		import mx.rpc.remoting.mxml.RemoteObject;

		public var ro:RemoteObject=new RemoteObject();

		public function UtmostService(destination:String, _resultHandler:Function=null, _faultHandler:Function=null)
		{
			ro.destination=destination;
			ro.showBusyCursor=true;
			if (_resultHandler != null)
			{
				ro.addEventListener(ResultEvent.RESULT, _resultHandler);
			}
			else
			{
				ro.addEventListener(ResultEvent.RESULT, resultHandler);
			}
			if (_faultHandler != null)
			{
				ro.addEventListener(FaultEvent.FAULT, _faultHandler);
			}
			else
			{
				ro.addEventListener(FaultEvent.FAULT, faultHandler);
			}

		}

		private function resultHandler(event:ResultEvent):void
		{
			trace("UtmostService->resultHandler->" + event.result);
		}

		private function faultHandler(event:FaultEvent):void
		{
			Alert.show("UtmostService->faultHandler->" + event.fault + "-->" + event.currentTarget);
		}
	}
}

