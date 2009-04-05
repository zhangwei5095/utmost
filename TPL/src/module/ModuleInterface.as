package module
{
	import flash.events.IEventDispatcher;
	
	public interface ModuleInterface extends IEventDispatcher
	{
		function set $modulename(_name:String):void;
		function set $modulecode(_code:String):void;
		function set $moduletree(_tree:XML):void;
	}
}