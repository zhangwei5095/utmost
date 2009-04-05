package ascb.util {

	public class DateFormat {
		
		public static const DAYS:Array = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
		public static const DAYS_ABBREVIATED:Array = ["Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"];
  		public static const MONTHS:Array = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
		public static const MONTHS_ABBREVIATED:Array = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  		public static const DAYSINMONTH:Array = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

		private var _sMask:String;
		
		public function set mask(sMask:String):void {
			_sMask = sMask;
		}
		
		public function get mask():String {
		    return _sMask;
		}
		
		public function DateFormat(sMask:String) {
		    _sMask = sMask;
		}
		
		public static function formatMilliseconds(nMilliseconds:Number):String {
		    // Determine the minutes and seconds portions of the time.
		    var nSeconds:Number = nMilliseconds / 1000;
		    return formatSeconds(nSeconds);
		}
		
		public static function formatSeconds(nTotalSeconds:Number):String {
		    var nfFormatter:ascb.util.NumberFormat = new ascb.util.NumberFormat("00");
		    var sMinutes:String = nfFormatter.format(Math.floor(nTotalSeconds/60));
		    var sSeconds:String = nfFormatter.format(Math.round(nTotalSeconds - (parseInt(sMinutes) * 60)));
		
		    return sMinutes + ":" + sSeconds;
		
		}
		
		private function getMaskElements(bIgnoreNonAlpha:Boolean = false):Array {
		    var aMaskElements:Array = _sMask.split("'");
		    var aMask:Array = new Array();
		    var aTemporary:Array;
		    var reParser:RegExp = new RegExp("[a-zA-Z]");
		    for(var i:Number = 0; i < aMaskElements.length; i++) {
		      if(i % 2 != 0) {
		          aMask.push({type: "quoted", element: aMaskElements[i]});
		      }
		      else {
		        aTemporary = aMaskElements[i].split("");
		        for(var j:Number = 0; j < aTemporary.length; j++) {
		          if(reParser.test(aTemporary[j]) && bIgnoreNonAlpha) {
		            aMask.push({type: "regular", element: aTemporary[j]});
		          }
		          else if(!bIgnoreNonAlpha) {
		            aMask.push({type: "regular", element: aTemporary[j]});
		          }
		        }
		      }
		    }
		    return aMask;
		}
		
		public function format(dDate:Date):String {
		    var aMask:Array = getMaskElements();
		    var aFormatted:Array = new Array();
		    for(var i:Number = 0; i < aMask.length; i++) {
		      switch (aMask[i].element) {
		        case "a":
		          if(dDate.getHours() < 12) {
		            aFormatted.push("am");
		          }
		          else {
		            aFormatted.push("pm");
		          }
		          break;
		        case "A":
		          if(dDate.getHours() < 12) {
		            aFormatted.push("AM");
		          }
		          else {
		            aFormatted.push("PM");
		          }
		          break;
		        case "d":
		          aFormatted.push(dDate.getDate());
		          break;
		        case "D":
		          aFormatted.push(ascb.util.DateFormat.DAYS_ABBREVIATED[dDate.getDay()]);
		          break;
		        case "F":
		          aFormatted.push(ascb.util.DateFormat.MONTHS[dDate.getMonth()]);
		          break;
		        case "g":
		          var nHours:Number = dDate.getHours();
		          if(nHours == 12) {
		            aFormatted.push(12);
		          }
		          else if(nHours <= 12) {
		            aFormatted.push(nHours);
		          }
		          else {
		            aFormatted.push(nHours - 12);
		          }
		          break;
		        case "G":
		          aFormatted.push(dDate.getHours());
		          break;
		        case "h":
		          var nfFormatter:ascb.util.NumberFormat = new ascb.util.NumberFormat("00");
		          var nHours:Number = dDate.getHours();
		          if(nHours == 12) {
		            aFormatted.push(12);
		          }
		          else if(nHours <= 12) {
		            aFormatted.push(nfFormatter.format(nHours));
		          }
		          else {
		            aFormatted.push(nfFormatter.format(nHours - 12));
		          }
		          break;
		        case "H":
		          var nfFormatter:ascb.util.NumberFormat = new ascb.util.NumberFormat("00");
		          aFormatted.push(nfFormatter.format(dDate.getHours()));
		          break;
		        case "i":
		          var nfFormatter:ascb.util.NumberFormat = new ascb.util.NumberFormat("00");
		          aFormatted.push(nfFormatter.format(dDate.getMinutes()));
		          break;
		        case "j":
		          aFormatted.push(dDate.getDate());
		          break;
		        case "l":
		          aFormatted.push(ascb.util.DateFormat.DAYS[dDate.getDay()]);
		          break;
		        case "m":
		          var nfFormatter:ascb.util.NumberFormat = new ascb.util.NumberFormat("00");
		          aFormatted.push(nfFormatter.format(dDate.getMonth() + 1));
		          break;
		        case "M":
		          aFormatted.push(ascb.util.DateFormat.MONTHS_ABBREVIATED[dDate.getMonth()]);
		          break;
		        case "n":
		          aFormatted.push(dDate.getMonth() + 1);
		          break;
		        case "s":
		          var nfFormatter:ascb.util.NumberFormat = new ascb.util.NumberFormat("00");
		          aFormatted.push(nfFormatter.format(dDate.getSeconds()));
		          break;
		        case "t":
		          aFormatted.push(ascb.util.DateFormat.DAYSINMONTH[dDate.getMonth()]);
		          break;
		        case "w":
		          aFormatted.push(dDate.getDay());
		          break;
		        case "y":
		          aFormatted.push(String(dDate.getFullYear()).substring(2));
		          break;
		        case "Y":
		          aFormatted.push(dDate.getFullYear());
		          break;
		        case "\\":
		          aFormatted.push(aMask[i].element + ((aMask[i + 1].element == undefined) ? "" : aMask[i + 1].element));
		          i++;
		          break;
		        default:
		          aFormatted.push(aMask[i].element);
		      }
		    }
		    return aFormatted.join("");
		}
		  
		
		public function parse(sDate:String):Date {
		    var aMask:Array = getMaskElements(true);
		    var nIndex:Number;
		    var sDate:String;
		    for(var i:Number = 0; i < aMask.length; i++) {
		      nIndex = sDate.indexOf(aMask[i].element);
		      if(aMask[i].type == "quoted") {
		        if(nIndex != -1) {
		          sDate = sDate.substring(0, nIndex) + " " + sDate.substring(nIndex + aMask[i].element.length);
		          aMask.splice(i, 1);
		          i--;
		        } 
		      }
		    }
		    var reParser:RegExp = new RegExp("[^a-zA-Z0-9]+");
		    var aElements:Array = sDate.split(reParser);
		    var dDate:Date = new Date(0, 0, 0, 0, 0, 0, 0);
		    var sAMPM:String;
		    var sElement:String;
		    for(var i:Number = 0; i < aMask.length; i++) {
		      sElement = String(aElements.shift());
		      switch (aMask[i].element) {
		        case "a":
		        case "A":
		          sAMPM = sElement.toLowerCase();
		          break;
		        case "d":
		          dDate.setDate(parseInt(sElement));
		          break;
		        case "F":
		          var sMonth:String = aElements.shift().toLowerCase();
		          for(var j:Number = 0; j < ascb.util.DateFormat.MONTHS.length; j++) {
		            if(sMonth == ascb.util.DateFormat.MONTHS[j].toLowerCase()) {
		              dDate.setMonth(j);
		              break; 
		            }
		          }
		          break;
		        case "g":
		        case "G":
		        case "h":
		        case "H":
		          dDate.setHours(parseInt(sElement));
		          break;
		        case "i":
		          dDate.setMinutes(parseInt(sElement));
		          break;
		        case "j":
		          dDate.setDate(parseInt(sElement));
		          break;
		        case "m":
		        case "n":
		          dDate.setMonth(parseInt(sElement) - 1);
		          break;
		        case "M":
		          var sMonth:String = sElement.toLowerCase();
		          for(var j:Number = 0; j < ascb.util.DateFormat.MONTHS_ABBREVIATED.length; j++) {
		            if(ascb.util.DateFormat.MONTHS_ABBREVIATED[j].toLowerCase() == sMonth) {
		              dDate.setMonth(j);
		              break;
		            }
		          }
		          break;
		        case "s":
		          dDate.setSeconds(parseInt(sElement));
		          break;
		        case "y":
		          dDate.setFullYear(2000 + (parseInt(sElement)));
		          break;
		        case "Y":
		          dDate.setFullYear(parseInt(sElement));
		          break;
		      }
		    }
		    if(sAMPM == "pm") {
		      if(dDate.getHours() < 12) {
		        dDate.setHours(dDate.getHours() + 12); 
		      }
		    }
		    else if(sAMPM == "am") {
		      if(dDate.getHours() == 12) {
		        dDate.setHours(0);
		      }
		    }
		    return dDate;
		}
		
	}
}