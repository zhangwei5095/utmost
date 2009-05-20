package org.utmost.util
{
	import mx.collections.ArrayCollection;
	
	public class TypeUtil
	{
		public function TypeUtil()
		{
		}
		public static function ArrayToArrayCollectionByUUID(array:Array):ArrayCollection {
			var ac:ArrayCollection=new ArrayCollection();
			for each (var obj:Object in array) {
				ac.addItem(obj.uuid);
			}
			return ac;
		}
	}
}