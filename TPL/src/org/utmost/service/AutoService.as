package org.utmost.service
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.Container;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	import org.utmost.util.AutoUtil;
	/**
	 * 前台 自动调用 后台AutoService
	 * */
	public class AutoService
	{
		private var resultFun:Function;
		private var faultFun:Function;
		private var ro:RemoteObject=new RemoteObject();
		
		public static function  getInstance():AutoService {
			return new AutoService();
		}

		//private var ro:HessianService=new HessianService();
		public function AutoService()
		{
			ro.destination="AutoService";
			ro.showBusyCursor=true;
			ro.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.addEventListener(ResultEvent.RESULT, resultHandler);
		}

		//保存或更新
		public function saveOrUpdate(entityName:String, obj:Object, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.saveOrUpdate.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.saveOrUpdate.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.saveOrUpdate(entityName, obj);
		}
		//保存或更新所有
		public function saveOrUpdateAll(entityName:String, obj:Object, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.saveOrUpdateAll.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.saveOrUpdateAll.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.saveOrUpdateAll(entityName, obj);
		}
		//保存
		public function save(entityName:String, obj:Object, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.save.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.save.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.save(entityName, obj);
		}

		//保存所有
		public function saveAll(entityName:String, obj:ArrayCollection, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.saveAll.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.saveAll.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.saveAll(entityName, obj);
		}


		//自动保存
		public function autoSave(entityName:String, container:Container, resultHandler:Function=null, faultHandler:Function=null):void
		{
			var obj:Object=AutoUtil.findValue(container);
			this.save(entityName, obj, resultHandler, faultHandler);
		}
		//自动保存或更新
		public function autoSaveOrUpdate(entityName:String, container:Container, resultHandler:Function=null, faultHandler:Function=null):void
		{
			var obj:Object=AutoUtil.findValue(container);
			this.saveOrUpdate(entityName, obj, resultHandler, faultHandler);
		}

		//更新
		public function update(entityName:String, obj:Object, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.update.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.update.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.update(entityName, obj);
		}
		//更新所有
		public function updateAll(entityName:String, obj:ArrayCollection, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.updateAll.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.updateAll.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.updateAll(entityName, obj);
		}
		//自动更新
		public function autoUpdate(entityName:String, container:Container, resultHandler:Function=null, faultHandler:Function=null):void
		{
			var obj:Object=AutoUtil.findValue(container);
			this.update(entityName, obj, resultHandler, faultHandler);
		}

		//根据UUID 查询
		public function findByUUID(tableName:String, uuid:String, resultHandler:Function, faultHandler:Function=null):void
		{
			if (faultHandler != null)
				ro.findByUUID.addEventListener(FaultEvent.FAULT, faultHandler);

			ro.findByUUID.addEventListener(ResultEvent.RESULT, resultHandler);
			ro.findByUUID(tableName, uuid);
		}

		//根据UUID 删除
		public function deleteByUUID(tableName:String, uuid:String, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.deleteByUUID.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.deleteByUUID.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.deleteByUUID(tableName, uuid);
		}

		//根据UUID 集合删除所有
		public function deleteByAllUUID(tableName:String, uuidArr:ArrayCollection, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.deleteByAllUUID.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.deleteByAllUUID.addEventListener(FaultEvent.FAULT, faultHandler);

			ro.deleteByAllUUID(tableName, uuidArr);
		}

		//根据HQL 删除
		public function deleteByHql(hql:String, resultHandler:Function=null, faultHandler:Function=null):void
		{
			if (resultHandler != null)
				ro.deleteByHql.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.deleteByHql.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.deleteByHql(hql);
		}

		//根据HQL 查询
		public function findByHql(hql:String, resultHandler:Function, faultHandler:Function=null):void
		{
			ro.findByHql.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.findByHql.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.findByHql(hql);
		}
		
		//根据命名 查询
		public function findByNamedQuery(queryName:String, resultHandler:Function, faultHandler:Function=null):void
		{
			ro.findByNamedQuery.addEventListener(ResultEvent.RESULT, resultHandler);
			if (faultHandler != null)
				ro.findByNamedQuery.addEventListener(FaultEvent.FAULT, faultHandler);
			ro.findByNamedQuery(queryName);
		}

		//唯一性校验  successHandler 为校验通过执行的函数
		public static function unique(tableName:String,field:String,value:String,successHandler:Function,errorHandler:Function=null):void {
			var service:AutoService=new AutoService();
			var hql:String="from "+tableName +" v where v."+field+"='"+value+"'";
			service.findByHql(hql,function(e:ResultEvent):void {
				var ac:ArrayCollection=e.result as ArrayCollection;
				if(ac.length<=0) {
					successHandler.call();
				}else {
					if(errorHandler!=null) {
						errorHandler.call();
					}
				}
			});
		}
		private function resultHandler(event:ResultEvent):void
		{
			trace("AutoService->resultHandler->" + event.result);
		//Alert.show("AutoService->resultHandler->"+event.result);
		}

		private function faultHandler(event:FaultEvent):void
		{
			//trace("AutoService->faultHandler->"+event.fault);
			Alert.show("AutoService->faultHandler->" + event.fault + "-->" + event.currentTarget);
		}
	}
}

