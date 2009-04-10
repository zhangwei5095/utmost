package org.utmost.service
{
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	
	public class StaticChannelSet
	{
		private static var uri:String=null;
		private static var address:String=null;
		public static  function setUri(_uri:String):void {
			uri=_uri;
		}
		public static  function getUri():String {
			return uri;
		}
		public static function setAddress(_address:String):void {
			address=_address;
		}
		public static function getAddress():String {
			return address;
		}
		public static function getChannelSet():ChannelSet {
			var channelSet:ChannelSet = new ChannelSet();
			var amfChannel:AMFChannel = new AMFChannel("spring-amf", uri);
			amfChannel.pollingEnabled = true;
			amfChannel.pollingInterval = 2000;
			channelSet.addChannel(amfChannel);
			return channelSet;
		}
	}
}