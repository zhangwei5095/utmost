package org.utmost.util
{
	public class TreeUtil
	{
		public function TreeUtil()
		{
		}
		//角色树参数
		public static function getRoleMainByTree():Object {
			var main:Object=new Object();
			main.tableName="U_ROLE";//*
			main.rootField="rolecode";//*
			main.rootValue="root";//格外注意
			main.idField="uuid";//*
			main.pidField="parentid";//*
			return main;
		}
		public static function getRoleKvByTree():Object {
			var kv:Object=new Object();
			kv.uuid="uuid";//*
			kv.rolecode="nodecode";//*
			kv.parentid="parentid";//*
			kv.rolename="nodename";//*
			return kv;
		}
		//功能树参数
		public static function getFunMainByTree():Object {
			var main:Object=new Object();
			main.tableName="U_FUN";//*
			main.rootField="funcode";//*
			main.rootValue="root";//格外注意
			main.idField="uuid";//*
			main.pidField="parentid";//*
			return main;
		}
		public static function getFunKvByTree():Object {
			var kv:Object=new Object();
			kv.uuid="uuid";//*
			kv.funcode="nodecode";//*
			kv.parentid="parentid";//*
			kv.funname="nodename";//*
			return kv;
		}
		
		//机构树参数
		public static function getOrgMainByTree():Object {
			var main:Object=new Object();
			main.tableName="U_ORG";//*
			main.rootField="orgcode";//*
			main.rootValue="root";//格外注意
			main.idField="uuid";//*
			main.pidField="parentid";//*
			return main;
		}
		public static function getOrgKvByTree():Object {
			var kv:Object=new Object();
			kv.uuid="uuid";//*
			kv.orgcode="nodecode";//*
			kv.parentid="parentid";//*
			kv.orgname="nodename";//*
			return kv;
		}
		
		//差错种类树参数
		public static function getErrorTypeMainByTree():Object {
			var main:Object=new Object();
			main.tableName="QAM_ERRORTYPE";//*
			main.rootField="errortypecode";//*
			main.rootValue="root";//格外注意
			main.idField="uuid";//*
			main.pidField="parentid";//*
			return main;
		}
		public static function getErrorTypeKvByTree():Object {
			var kv:Object=new Object();
			kv.uuid="uuid";//*
			kv.errortypecode="nodecode";//*
			kv.parentid="parentid";//*
			kv.errortypename="nodename";//*
			return kv;
		}
	}
}