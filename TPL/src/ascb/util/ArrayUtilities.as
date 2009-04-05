package ascb.util {
	
  public class ArrayUtilities {

    public static function randomize(aArray:Array):Array {
      var aCopy:Array = aArray.concat();
      var aRandomized:Array = new Array();
      var oElement:Object;
      var nRandom:Number;
      for(var i:Number = 0; i < aCopy.length; i++) {
        nRandom = NumberUtilities.random(0, aCopy.length - 1);
        aRandomized.push(aCopy[nRandom]);
        aCopy.splice(nRandom, 1);
        i--;
      }
      return aRandomized;
    }

    public static function average(aArray:Array):Number {
      return sum(aArray) / aArray.length;
    }

    public static function sum(aArray:Array):Number {
      var nSum:Number = 0;
      for(var i:Number = 0; i < aArray.length; i++) {
        if(typeof aArray[i] == "number") {
          nSum += aArray[i];
        }
      }
      return nSum;
    }

    public static function max(aArray:Array):Number {
      var aCopy:Array = aArray.concat();
      aCopy.sort(Array.NUMERIC);
      var nMaximum:Number = Number(aCopy.pop());
      return nMaximum;
    }

    public static function min(aArray:Array):Number {
      var aCopy:Array = aArray.concat();
      aCopy.sort(Array.NUMERIC);
      var nMinimum:Number = Number(aCopy.shift());
      return nMinimum;
    }

    public static function switchElements(aArray:Array, nIndexA:Number, nIndexB:Number):void {
      var oElementA:Object = aArray[nIndexA];
      var oElementB:Object = aArray[nIndexB];
      aArray.splice(nIndexA, 1, oElementB);
      aArray.splice(nIndexB, 1, oElementA);
    }

    private static function objectEquals(oInstanceA:Object, oInstanceB:Object):Boolean {
      for(var sItem:String in oInstanceA) {
        if(oInstanceA[sItem] is Object) {
          if(!objectEquals(oInstanceA[sItem], oInstanceB[sItem])) {
            return false;
          }
        }
        else {
          if(oInstanceA[sItem] != oInstanceB[sItem]) {
            return false;
          }
        }
      }
      return true;
    }

    public static function equals(aArrayA:Array, aArrayB:Array, bNotOrdered:Boolean, bRecursive:Boolean):Boolean {
      if(aArrayA.length != aArrayB.length) {
        return false;
      }
      var aArrayACopy:Array = aArrayA.concat();
      var aArrayBCopy:Array = aArrayB.concat();
      if(bNotOrdered) {
        aArrayACopy.sort();
        aArrayBCopy.sort();
      }
      for(var i:Number = 0; i < aArrayACopy.length; i++) {
        if(aArrayACopy[i] is Array && bRecursive) {
          if(!equals(aArrayACopy[i], aArrayBCopy[i], bNotOrdered, bRecursive)) {
            return false;
          }
        }
        else if(aArrayACopy[i] is Object && bRecursive) {
          if(!objectEquals(aArrayACopy[i], aArrayBCopy[i])) {
            return false;
          }
        }
        else if(aArrayACopy[i] != aArrayBCopy[i]) {
          return false;
        }
      }
      return true;
    }

    public static function findMatchIndex(aArray:Array, oElement:Object, ...rest):Number {
      var nStartingIndex:Number = 0;
      var bPartialMatch:Boolean = false;
      if(typeof rest[0] == "number") {
        nStartingIndex = rest[0];
      }    
      else if(typeof rest[1] == "number") {
        nStartingIndex = rest[1];
      }
      if(typeof rest[0] == "boolean") {
        bPartialMatch = rest[0];
      }
      var bMatch:Boolean = false;
      for(var i:Number = nStartingIndex; i < aArray.length; i++) {
        if(bPartialMatch) {
          bMatch = (aArray[i].indexOf(oElement) != -1);
        }
        else {
          bMatch = (aArray[i] == oElement);
        }
        if(bMatch) {
          return i;
        }
      }
      return -1;
    }

    public static function findLastMatchIndex(aArray:Array, oElement:Object, oParameter:Object):Number {
      var nStartingIndex:Number = aArray.length;
      var bPartialMatch:Boolean = false;
      if(typeof arguments[2] == "number") {
        nStartingIndex = arguments[2];
      }    
      else if(typeof arguments[3] == "number") {
        nStartingIndex = arguments[3];
      }
      if(typeof arguments[2] == "boolean") {
        bPartialMatch = arguments[2];
      }
      var bMatch:Boolean = false;
      for(var i:Number = nStartingIndex; i >= 0; i--) {
        if(bPartialMatch) {
          bMatch = (aArray[i].indexOf(oElement) != -1);
        }
        else {
          bMatch = (aArray[i] == oElement);
        }
        if(bMatch) {
          return i;
        }
      }
      return -1;
    }

    public static function findMatchIndices(aArray:Array, oElement:Object, bPartialMatch:Boolean = false):Array {
      var aIndices:Array = new Array();
      var nIndex:Number = findMatchIndex(aArray, oElement, bPartialMatch);
      while(nIndex != -1) {
        aIndices.push(nIndex);
        nIndex = findMatchIndex(aArray, oElement, bPartialMatch, nIndex + 1);
      }
      return aIndices;
    }

    public static function duplicate(oArray:Object, bRecursive:Boolean = false):Object {
      var oDuplicate:Object;
      if(bRecursive) {
        if(oArray is Array) {
          oDuplicate = new Array();
          for(var i:Number = 0; i < oArray.length; i++) {
            if(oArray[i] is Object) {
              oDuplicate[i] = duplicate(oArray[i]);
            }
            else {
              oDuplicate[i] = oArray[i];
            }
          }
          return oDuplicate;
        }
        else {
          var oDuplicate:Object = new Object();
          for(var sItem:String in oArray) {
            if(oArray[sItem] is Object && !(oArray[sItem] is String) && !(oArray[sItem] is Boolean) && !(oArray[sItem] is Number)) {
              oDuplicate[sItem] = duplicate(oArray[sItem], bRecursive);
            }
            else {
              oDuplicate[sItem] = oArray[sItem];
            }
          }
          return oDuplicate;
        }
      }
      else {
        if(oArray is Array) {
          return oArray.concat();
        }
        else {
          var oDuplicate:Object = new Object();
          for(var sItem:String in oArray) {
            oDuplicate[sItem] = oArray[sItem];
          }
          return oDuplicate;
        }
      }
    }

    static public function toString(oArray:Object, nLevel:uint = 0):String {
      var sIndent:String = "";
      for(var i:Number = 0; i < nLevel; i++) {
        sIndent += "\t";
      }
      var sOutput:String = "";
      for(var sItem:String in oArray) {
        if(oArray[sItem] is Object) {
          sOutput = sIndent + "** " + sItem + " **\n" + toString(oArray[sItem], nLevel + 1) + sOutput;
        }
        else {
          sOutput += sIndent + sItem + ":" + oArray[sItem] + "\n";
        }
      }
      return sOutput;
    }

  }
  
}