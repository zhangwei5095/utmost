package org.utmost.variable
{
	public class TreeVar
	{
		public function TreeVar()
		{
		}
		//常用删除Tree对象
		public static function getDelObject(tableName:String,uuid:String):Object {
			var obj:Object=new Object();
			obj.tableName=tableName;//*
			obj.idField="uuid";//*
			obj.pidField="pid";//*
			obj.idValue=uuid;//*
			return obj;
		}
		//角色树参数
		public static function getRoleMainByTree():Object {
			var main:Object=new Object();
			main.tableName="U_PORTAL_ROLE";//*
			main.rootField="rolecode";//*
			main.rootValue="root";//格外注意
			main.idField="uuid";//*
			main.pidField="pid";//*
			return main;
		}
		public static function getRoleKvByTree():Object {
			var kv:Object=new Object();
			kv.uuid="uuid";//*
			kv.rolecode="nodecode";//*
			kv.pid="pid";//*
			kv.rolename="nodename";//*
			kv.state="state";//*
			return kv;
		}
		//功能树参数
		public static function getFuncMainByTree():Object {
			var main:Object=new Object();
			main.tableName="U_PORTAL_FUNC";//*
			main.rootField="funccode";//*
			main.rootValue="root";//格外注意
			main.idField="uuid";//*
			main.pidField="pid";//*
			return main;
		}
		public static function getFuncKvByTree():Object {
			var kv:Object=new Object();
			kv.uuid="uuid";//*
			kv.funccode="nodecode";//*
			kv.pid="pid";//*
			kv.funcname="nodename";//*
			kv.state="state";//*
			return kv;
		}
		
		
		//机构树参数
		public static function getOrgMainByTree():Object {
			var main:Object=new Object();
			main.tableName="U_PORTAL_ORG";//*
			main.rootField="orgcode";//*
			main.rootValue="root";//格外注意
			main.idField="uuid";//*
			main.pidField="pid";//*
			return main;
		}
		public static function getOrgKvByTree():Object {
			var kv:Object=new Object();
			kv.uuid="uuid";//*
			kv.orgcode="nodecode";//*
			kv.pid="pid";//*
			kv.orgname="nodename";//*
			kv.state="state";//*
			return kv;
		}
	}
}