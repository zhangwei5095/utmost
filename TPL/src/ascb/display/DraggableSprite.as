
package ascb.display {
	
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	/**
	 * A DraggableSprite is a Sprite that has the ability to interact
	 * with the Mouse in drag and drop scenarios.  All Sprites have
	 * the startDrop() and stopDrag() methods, but those methods only
	 * update the display list during enterFrame events, instead of
	 * during mouseMove events, which leads to choppy dragging.  The
	 * DraggableSprite provides a drag() method, similar to startDrag()
	 * and a drop() method, similar to stopDrag(), that enable smooth
	 * drag and drop operations.
	 */
	public class DraggableSprite extends Sprite {
	
		// Store the location of the cursor within the sprite
		// so we can position correctly when the cursor moves
		private var x_offset:Number = 0;
		private var y_offset:Number = 0;
		
		// Keep track of the area where dragging is allowed
		// so the sprite can be kept in bounds.
		private var bounds:Rectangle;
		
		/**
		 * Constructor - nothing to do
		 */
		public function DraggableSprite() {
			// do nothing
		}
		
		/**
		 * Starts a smooth dragging operation, forcing the player to redraw
		 * the Sprite after every mouse move.  Cancel the drag() operation
		 * by calling the drop() method.
		 */
		public function drag( lockCenter:Boolean = false, rectangle:Rectangle = null ):void {
			// Save the cursor position in the sprite so we can adjust
			// the x and y locations correctly when the cursor position
			// chnages based on the lockCenter parameter.
			var pt:Point;
			if ( !lockCenter ) {
				// lockCenter is false, use the mouse coordinates at the point
				pt = localToGlobal( new Point( mouseX, mouseY ) );
			} else {
				// lockCenter is true, ignore the mouse coordinates
				// and use (0,0) instead as the point
				pt = localToGlobal( new Point( 0, 0 ) );
			}
			
			// Save the offset values so we can compute x and y correctly
			x_offset = pt.x - x;
			y_offset = pt.y - y;
			
			// Save the bounds rectangle
			bounds = rectangle;
			
			// Wire the Sprite to the mouse - whenever the mouse moves
			// invoke handleDrag to update the Sprite position
			stage.addEventListener( MouseEvent.MOUSE_MOVE, handleDrag );
			
			// Detect a drop by listening for mouse up on the stage
			stage.addEventListener( MouseEvent.MOUSE_UP, drop );
		}
		
		/**
		 * Called everytime the mouse moves after the drag() method has
		 * been called.  Updates the position of the Sprite based on
		 * the location of the mouse cursor.
		 */
		private function handleDrag( event:MouseEvent ):void {
			// Set the x and y location based on the mouse position
			x = event.stageX - x_offset;
			y = event.stageY - y_offset;
			
			// Keep sprite in bounds if bounds was specified in drag
			if ( bounds != null ) {
				if ( x < bounds.left ) {
					x = bounds.left;
				} else if ( x > bounds.right ) {
					x = bounds.right;
				}
				
				if ( y < bounds.top ) {
					y = bounds.top;	
				} else if ( y > bounds.bottom ) {
					y = bounds.bottom;	
				}
			}
			
			// Force the player to re-draw the sprite after the event.
			// This makes the movement look smooth, unlike startDrag()
			event.updateAfterEvent();
		}
	
		/**
		 * Cancels a drag() operation
		 */
		public function drop():void {
			// The mouse up indicated the drop, so remove the mouse up listener ...
			stage.removeEventListener( MouseEvent.MOUSE_UP, drop );
			
			// ... and remove the mouse move listener
			stage.removeEventListener( MouseEvent.MOUSE_MOVE, handleDrag );
		}
	}
}