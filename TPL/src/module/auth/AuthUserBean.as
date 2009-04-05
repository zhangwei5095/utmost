package module.auth
{
	import mx.collections.ArrayCollection;
	
	public class AuthUserBean
	{
		//登录名称
		public static var loginname:String=null;
		//用户名称
		public static var username:String=null;
		//用户编码
		public static var usercode:String=null;
		//密码
		public static var password:String=null;
		//角色
		public static var role:ArrayCollection=null;
		//组织结构
		public static var org:ArrayCollection=null; 
	}
}