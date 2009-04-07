package skins.undefined.assets.programmatic
{
	import mx.preloaders.DownloadProgressBar;
	import flash.display.Sprite;
	import mx.preloaders.IPreloaderDisplay;
	import flash.display.Loader;
	import flash.events.ProgressEvent;
	import flash.events.Event;
	import mx.events.FlexEvent;
	import flash.net.URLRequest;
	import flash.display.Shape;
	import flash.text.TextField;
	import flash.text.TextFormat;
	
	public class CustomPreloader extends DownloadProgressBar implements IPreloaderDisplay
	{
		private var dpbImageControl:Loader;
				
		public function CustomPreloader()
		{
			super();
		}
		
		protected function draw():void
		{
		}
		
		override public function set preloader(preloader:Sprite):void 
		{
			// Escuchar los eventos relevantes.
			preloader.addEventListener( ProgressEvent.PROGRESS, handleProgress );
			preloader.addEventListener( Event.COMPLETE, handleComplete );
			preloader.addEventListener( FlexEvent.INIT_PROGRESS, handleInitProgress );
			preloader.addEventListener( FlexEvent.INIT_COMPLETE, handleInitComplete );
		}
		
		// Inicializar el control Loader en el método initialize del interface IPreloaderDisplay
		override public function initialize():void 
		{	
			dpbImageControl = new Loader();
			dpbImageControl.contentLoaderInfo.addEventListener( Event.COMPLETE, loader_completeHandler );
			dpbImageControl.load(new URLRequest("skins/undefined/assets/LoadingUndefinedSkin.swf"));
		}
		
		// Después de cargar el SWF
		private function loader_completeHandler(event:Event):void 
		{	
			dpbImageControl.content["barra"].scaleX = 0;
			addChild( dpbImageControl );
			
			this.x = this.stage.stageWidth/2 - dpbImageControl.contentLoaderInfo.width/2;
			this.y = this.stage.stageHeight/2 - dpbImageControl.contentLoaderInfo.height/2;
		}
		
		// Definir los oyentes vacíos.
		private function handleProgress(event:ProgressEvent):void 
		{
			var relacion:Number = event.bytesLoaded / event.bytesTotal;
			
			if (dpbImageControl.content)
			{
				dpbImageControl.content["barra"].scaleX = relacion;
				dpbImageControl.content["porcentaje"].text = relacion * 100;
			}
		}
		
		private function handleComplete(event:Event):void 
		{
		}
		private function handleInitProgress(event:Event):void 
		{
		}
		
		private function handleInitComplete(event:Event):void 
		{
			dispatchEvent(new Event(Event.COMPLETE));
		}
	}
}