package ascb.util {

  public class NumberUtilities {

	private static var _aUniqueIDs:Array;

    /**
     *  Round a number. By default the number is rounded to the nearest
     *  integer. Specifying a roundToInterval parameter allows you to round
     *  to the nearest of a specified interval.
     *  @param  number             The number you want to round.
     *  @param  nRoundToInterval   (optional) The interval to which you want to
     *                             round the number. The default is 1.
     *  @return                    The number rounded to the nearest interval.
     */
    public static function round(nNumber:Number, nRoundToInterval:Number = 1):Number {
      // Return the result
      return Math.round(nNumber / nRoundToInterval) * nRoundToInterval;
    }
    

    /**
     *  Get the floor part of a number. By default the integer part of the
     *  number is returned just as if calling Math.floor( ). However, by specifying
     *  a roundToInterval, you can get non-integer floor parts.
     *  to the nearest of a specified interval.
     *  @param  number             The number for which you want the floor part.
     *  @param  nRoundToInterval   (optional) The interval to which you want to
     *                             get the floor part of the number. The default is 1.
     *  @return                    The floor part of the number.
     */
    public static function floor(nNumber:Number, nRoundToInterval:Number = 1):Number {
    
      // Return the result
      return Math.floor(nNumber / nRoundToInterval) * nRoundToInterval;
    }

    /**
     *  Get the ceiling part of a number. By default the next highested integer
     *  number is returned just as if calling Math.ceil( ). However, by specifying
     *  a roundToInterval, you can get non-integer ceiling parts.
     *  to the nearest of a specified interval.
     *  @param  number             The number for which you want the ceiling part.
     *  @param  nRoundToInterval   (optional) The interval to which you want to
     *                             get the ceiling part of the number. The default is 1.
     *  @return                    The ceiling part of the number.
     */
    public static function ceil(nNumber:Number, nRoundToInterval:Number = 1):Number {

      // Return the result
      return Math.ceil(nNumber / nRoundToInterval) * nRoundToInterval;
    }

    /**
     *  Generate a random number within a specified range. By default the value
     *  is rounded to the nearest integer. You can specify an interval to which
     *  to round the value.
     *  @param  minimum            The minimum value in the range.
     *  @param  maximum            (optional) The maxium value in the range. If
                                   omitted, the minimum value is used as the maximum,
                                   and 0 is used as the minimum.
     *  @param  roundToInterval    (optional) The interval to which to round.
     *  @return                    The random number.
     */
    public static function random(nMinimum:Number, nMaximum:Number = 0, nRoundToInterval:Number = 1):Number {

      // If the minimum is greater than the maximum, switch the two.
      if(nMinimum > nMaximum) {
        var nTemp:Number = nMinimum;
        nMinimum = nMaximum;
        nMaximum = nTemp;
      }

        // Calculate the range by subtracting the minimum from the maximum. Add
        // 1 times the round to interval to ensure even distribution.
        var nDeltaRange:Number = (nMaximum - nMinimum) + (1 * nRoundToInterval);

        // Multiply the range by Math.random(). This generates a random number
        // basically in the range, but it won't be offset properly, nor will it
        // necessarily be rounded to the correct number of places yet.
        var nRandomNumber:Number = Math.random() * nDeltaRange;

        // Add the minimum to the random offset to generate a random number in the correct range.
        nRandomNumber += nMinimum;

        // Return the random value. Use the custom floor( ) method to ensure the
        // result is rounded to the proper number of decimal places.
        return floor(nRandomNumber, nRoundToInterval);
      }

    /**
     *  Generate a unique number.
     *  @return                    The unique number
     */
    public static function getUnique():Number {

      if(_aUniqueIDs == null) {
      	_aUniqueIDs = new Array();
      }

      // Create a number based on the current date and time. This will be unique
      // in most cases.
      var dCurrent:Date = new Date();
      var nID:Number = dCurrent.getTime();

      // It is possible that the value may not be unique if it was generated
      // within the same millisecond as a previous number. Therefore, check to
      // make sure. If it is not unique, then generate a random value and concatenate
      // it with the previous one.
      while(!isUnique(nID)) {
        nID += NumberUtilities.random(dCurrent.getTime(), 2 * dCurrent.getTime());
      }
      
      _aUniqueIDs.push(nID);

      // Return the number.
      return nID;  
    }

  /**
   *  Check to see if a number is unique within the array of stored numbers.
   *  @param  number            The number to compare.
   *  @return                   True or false
   */
  private static function isUnique(nNumber:Number):Boolean {
    for(var i:Number = 0; i < _aUniqueIDs.length; i++) {
      if(_aUniqueIDs[i] == nNumber) {
        return false;
      }
    }
    return true;
  }

  }
}