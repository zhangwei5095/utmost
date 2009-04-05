package ascb.util {

  import flash.events.EventDispatcher;
  import flash.display.Shape;
  import flash.events.Event;

  public class Tween extends flash.events.EventDispatcher {

    private var _object:Object;
    private var _property:String;
    private var _start:Number;
    private var _stop:Number;
    private var _duration:Number;
    private var _shape:Shape;
    private var _count:uint;

    public function Tween(object:Object, property:String, start:Number, stop:Number, duration:Number) {
    	_object = object;
    	_property = property;
    	_start = start;
    	_stop = stop;
    	_duration = duration;
    	_shape = new Shape();
    }
    
    public function start():void {
    	_count = 0;
    	_shape.addEventListener(Event.ENTER_FRAME, onChange);
    }
    
    private function onChange(event:Event):void {
    	_count++;
    	_object[_property] = _start + (_stop - _start) / _duration * _count;
    	_count++;
    	if(_count >= _duration) {
    		_shape.removeEventListener(Event.ENTER_FRAME, onChange);
    		dispatchEvent(new Event("complete"));
    	}
    }
	
  }
  
}