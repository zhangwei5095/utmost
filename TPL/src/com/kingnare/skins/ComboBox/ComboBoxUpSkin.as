package com.kingnare.skins.ComboBox
{
	import flash.display.MovieClip;
	import flash.display.StageAlign;
    import flash.display.StageScaleMode;
    import flash.events.Event;
	
	public class ComboBoxUpSkin extends MovieClip
	{
		private var backgroundMovieClip:ComboBoxUpBackgroundSkin;
		private var upArrow:ComboBox_Arrow;
		
		
		public function ComboBoxUpSkin()
		{
			stage.scaleMode = StageScaleMode.NO_SCALE;
			stage.align = StageAlign.TOP_LEFT;
			stage.addEventListener(Event.RESIZE, resizeHandler);

			//backgroundMovieClip = new ComboBoxUpBackgroundSkin();
			//upArrow = new ComboBox_Arrow();
			trace(bg);
			updateDisplayList();
		}
		
		private function resizeHandler(event:Event):void
		{
            updateDisplayList();
        }
		
		private function updateDisplayList():void
		{
			bg.width = 50;
			bg.alpha = 0.4;
            trace("stageWidth: " + stage.stageWidth + " stageHeight: " + stage.stageHeight);
		}
	}
}