package ascb.util {
	
	import flash.display.*;

	public class DisplayObjectUtilities {
		
		// Remove all of the children in a container
		public static function removeAllChildren( container:DisplayObjectContainer ):void {
			  
			// Because the numChildren value changes after every time we remove
			// a child, save the original value so we can count correctly
			var count:int = container.numChildren;
			
			// Loop over the children in the container and remove them
			for ( var i:int = 0; i < count; i++ ) {
				container.removeChildAt( 0 );
			}
		}
		
	}
}