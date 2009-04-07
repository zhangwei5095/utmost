package skins.undefined.assets.programmatic
{
	import mx.controls.DateField;

	public class MyDateField extends DateField
	{
		public function MyDateField()
		{
			super();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number,
                                                  unscaledHeight:Number):void
	    {
	        super.updateDisplayList(unscaledWidth, unscaledHeight);
	
	       
	        textInput.setActualSize(textInput.width+2, textInput.height);
	    }
		
	}
}