package org.utmost.component
{
	import flexlib.controls.tabBarClasses.SuperTab;

	import mx.preloaders.DownloadProgressBar;

	public class MyPreloader extends DownloadProgressBar
	{

		public function MyPreloader()
		{
			super();
			downloadingLabel="正在下载中......";
			initializingLabel="正在初始化内容......";
		}
	}
}

