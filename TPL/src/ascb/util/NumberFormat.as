package ascb.util {
	
  public class NumberFormat {

    private var _sMask:String;

    /**
     *  Get and set the mask for the formatting. The mask can consist of 0's, #'s,
     *  commas, and dots.
     */
    public function get mask():String {
      return _sMask;
    }

    public function set mask(sMask:String):void {
      _sMask = sMask;
    }

    public function NumberFormat(sMask:String = null) {
      _sMask = sMask;
    }

    /**
     *  Format a number. If no mask has been set then the standard formatting
     *  for the locale is used. Optionally, you may specify radix, prefix, locale,
     *  and/or symbol object parameters.
     *  <p>
     *  Example usage: <br />
     *  trace(nfFormatter.format(1000)); // Displays 1,000 <br />
     *  trace(nfFormatter.format(1000, 16));  // Displays: 0x3E8 <br />
     *  trace(nfFormatter.format(1000, 16, "#"));  // Displays: #3E8 <br />
     *  trace(nfFormatter.format(1000, new Locale("fr")));  // Displays: 1.000 <br />
     *  trace(nfFormatter.format(1000, {group: "|", decimal: "%"}));  // Displays: 1|000 <br />   
     *  </p>
     *  to the nearest of a specified interval.
     *  @param  number          The number you want to format.
     *  @param  radix           (optional) The radix by which to display the number.
     *  @param  prefix          (optional) The prefix to use when specifying the radix.ber.
     *  @param  locale          (optional) A Locale object.
     *  @param  symbols object  (optional) An object specifying the group and decimal symbols.
     *  @return                 The formatted number as a string.
     */
    public function format(nNumber:Number, oParameter1:Object = null, oParameter2:Object = null):String {
      // Check to see if the second parameter is a number. If so, that means it's the radix,
      // so format the number based on the radix.
      if(typeof oParameter1 == "number") {
        var nRadix:Number = Number(oParameter1);
        var sNumber:String = nNumber.toString(nRadix);

        // See if there's an approprate prefix of either 0x or 0.
        // Optionally, the prefix may be specified as a third parameter.
        var sPrefix:String = "";
        if(nRadix == 16) {
          sPrefix = "0x";
        }
        if(nRadix == 8) {
          sPrefix = "0";
        }

        // If a prefix is specified, use that instead.
        if(oParameter2 != null) {
          sPrefix = String(oParameter2);
        }

        // Return the formatted number as a string.
        return sPrefix + sNumber.toUpperCase();
      }

      var sNumber:String;
      var sDecimal:String = ",";
      var sGroup:String = ".";

      // Check to see if the second parameter is a symbols object.
      if(oParameter1 != null && oParameter1.hasOwnProperty("group")) {
        var oSymbols:Object = oParameter1;
      }
      else {
        // If the second parameter was not the radix and not a symbols
        // object, then it's a locale.
        var lLocale:Locale = Locale(oParameter1);

        // If the locale is undefined, create a new locale with default settings.
        if(lLocale == null) {
          lLocale = new Locale();
        }
        var lStyle:Locale = Locale(lLocale);

        // Get the symbols for the formatting based on the locale - includes grouping,
        // decimal, etc.
        var oSymbols:Object = getSymbols(false, lStyle);
      }

      sDecimal = oSymbols.decimal;
      sGroup = oSymbols.group;

      // Split the number into two arrays of characters.
      var aParts:Array = String(nNumber).split(".");
      var aPart0:Array = aParts[0].split("");
      var aPart1:Array = (aParts.length > 1) ? aParts[1].split("") : new Array();

      // If the mask is not defined, then use default formatting.
      if(_sMask == null) {
        var nCounter:Number = 1;
        aPart0.reverse();

        // Loop through the characters of the first array in reverse order.
        // Every third number add a grouping symbol.
        for(var i:Number = 0; i < aPart0.length; i++) {
          if(nCounter > 3) {
            nCounter = 0;
            aPart0.splice(i, 0, sGroup);
          }
          nCounter++;
        }
        aPart0.reverse();

        // Join the characters back to a string, then concatenate the decimal symbol
        // and the second part of the number.
        sNumber = aPart0.join("");
        if(aParts[1] != null) {
          sNumber += sDecimal + aParts[1];
        }
      }
      else {

        // Otherwise, the mask was specified, so use it to format the number.

        // Split the mask into arrays of characters.
        var aMask:Array = _sMask.split("");
        for(var i:Number = 0; i < aMask.length; i++) {
          if(aMask[i] != "0" && aMask[i] != "#" && aMask[i] != ".") {
            aMask.splice(i, 1);
            i--;
          }
        }
        aMask = aMask.join("").split(".");
        var aMask0:Array = aMask[0].split("");
        var aMask1:Array = (aMask.length > 1) ? aMask[1].split("") : new Array();
        var nCounter:Number = aMask0.length;

        var nPart0Index:Number = 0;
        var nMaskIndex:Number = 0;
        sNumber = "";

        // If nCounter is less than the length of the first part of the number string,
        // then that means that several characters of the first part of the number string
        // need to get added to the return string before dealing with the mask.
        if(nCounter < aPart0.length) {
          for(var i:Number = 0; i < aPart0.length - nCounter; i++) {
            sNumber += aPart0[i];
            nPart0Index++;
          }
        }
        else if(nCounter > aPart0.length) {
          // Otherwise, if the number of mask character is greater than the digit in the number,
          // Add leading zeros or spaces.
          for(var i:Number = 0; i < nCounter - aPart0.length; i++) {
            if(aMask0[i] == "0") {
              sNumber += "0";
            }
            else if (aMask0[i] == "#") {
              sNumber += " ";
            }
            nMaskIndex++;
          }
        }

        var bNumeric:Boolean = false;

        // Loop through each of the remaining characters in the mask.
        for(var i:Number = nMaskIndex; i < aMask0.length; i++) {

          // If the mask character is anything other than a # or 0, and no other
          // numeric character has yet been encountered, then use a space. Otherwise
          // if it's a 0 or # add the number, and if it's a comma add the grouping
          // symbol.
          if(aMask0[i] == "0" || aMask0[i] == "#") {
            sNumber += (aPart0[nPart0Index] == undefined) ? "" : aPart0[nPart0Index];
            nPart0Index++;
          }
        }

        // Split the mask string into an array using the dot as the
        // delimiter. Then split the first element of that array into an array
        // of characters.
        aMask = _sMask.split(".");
        aMask = aMask[0].split("");

        // Split the number string into an array of characters.
        var aNumber:Array = sNumber.split("");

        // Declare a variable and initialize it to false. This variable is
        // to keep track of whether or not a numeric value has been encountered
        // yet.
        var bNumeric:Boolean = false;

        // Loop through each element of the array of mask characters.
        for(var i:Number = 0; i < aMask.length; i++) {

          // Check to see if the element of the mask is one of the special
          // mask characters.
          if(aMask[i] != "0" && aMask[i] != "#" && aMask[i] != ".") {

            // If a numeric character has been encountered then add a grouping
            // symbol to the number. Otherwise add a space.
            if(bNumeric) {
              aNumber.splice(i, 0, sGroup);
            }
            else {
              aNumber.splice(i, 0, " ");
            }
          }

          // Check to see if the current character is numeric (and non-zero).
          if(aNumber[i] != " " && aNumber[i] != "0") {
            bNumeric = true;
          }
        }

        // Join the characters in the array back to a string.
        sNumber = aNumber.join("");

        // If there's a second part to the mask, then append the decimal
        // symbol to the number.
        if(aMask1.length > 0) {
          sNumber += sDecimal;
        }

        var nDigits:Number;

        // Loop through each element of the second mask part.
        for(var i:Number = 0; i < aMask1.length; i++) {

          // Check to see if the character in the second number part is
          // defined.
          if(aPart1[i] == null) {

            // If the character is undefined then append either a 0 or a space
            // if the corresponding character in the mask array is either 0 or
            // a #.
            if(aMask1[i] == "0") {
              sNumber += "0";
            }
            else if(aMask1[i] == "#") {
              sNumber += " ";
            }
          }
          else {

            // Otherwise, the character in the number array is defined, so
            // append the number to the number string. If it happens to be the
            // last element in the mask string then round the next two digits.
            // Otherwise, just append the next digit.
            nDigits = Number(aPart1[i] + "" + aPart1[i + 1]);
            if(i == aMask1.length - 1 && !isNaN(nDigits)) {
              sNumber += String(Math.round(nDigits/10));
            }
            else {
              sNumber += aPart1[i];
            }
          }
        }
      }

      // Return the number string.
      return sNumber;
    }

    /**
     *  Format a number as currency. This method works very similarly to the 
     *  standard format( ) method.
     *  <p>
     *  Example usage: <br />
     *  trace(nfFormatter.currencyFormat(1000)); // Displays $1,000.00 <br />
     *  trace(nfFormatter.currencyFormat(1000, new Locale("fr")));  // Displays: 1.000,00€ <br />
     *  trace(nfFormatter.currencyFormat(1000, {group: "|", decimal: "%", before: true, currency: "^"}));  // Displays: ^1|000%00 <br />
     *  </p>
     *  to the nearest of a specified interval.
     *  @param  number          The number you want to format.
     *  @param  locale          (optional) A Locale object.
     *  @param  symbols object  (optional) An object specifying the group, decimal, and currency symbols.
     *                          Can also include a before (Boolean) property specifying whether or not
     *                          ths currency symbol should be placed at the beginning or end.
     *  @return                 The formatted number as a string.
     */
    public function currencyFormat(nAmount:Number, oParameter1:Object = null):String {

      // If the locale is passed to the method, use that. Otherwise, create a new,
      // default Locale object.
      var lStyle:Locale = (arguments[1] is Locale) ? arguments[1] : new Locale();

      // If the symbols object is passed to the method, use that. Otherwise, retrieve
      // the symbols object based on the locale.
      if(oParameter1 != null && oParameter1.hasOwnProperty("group")) {
        var oSymbols:Object = oParameter1;
      }
      else {
        var oSymbols:Object = getSymbols(true, lStyle);
      }

      var sCurrencySymbol:String = oSymbols.currency;
      var sGroup:String = oSymbols.group;
      var sDecimal:String = oSymbols.decimal;
      var sTempMask:String = _sMask;
      _sMask = null;

      // Create a Locale object that uses US formatting, then format the
      // amount using that locale.
      var lLocale:Locale = new Locale();
      lLocale.language = "en";
      lLocale.variant = "US";
      var sAmount:String = format(nAmount, lLocale);  
      _sMask = sTempMask;

      // Split the formatter string into parts using the dot as the
      // delimiter.
      var aParts:Array = sAmount.split(".");

      // If there were no decimal places, use a default of 00. Otherwise,
      // Round the decimal places to two.
      if(aParts[1] == undefined) {
        aParts[1] = "00";
      }
      else {
        aParts[1] = Number(aParts[1]);
        var nPart1Length:Number = String(aParts[1]).length;
        if(nPart1Length > 2) {
          aParts[1] /= Math.pow(10, (nPart1Length - 2));
          aParts[1] = Math.round(aParts[1]);
        }
      }

      // Join the parts pack to a new string. Then split that into an array
      // of characters.
      sAmount = aParts.join(".");
      var aAmount:Array = sAmount.split("");

      // Loop through each of the elements of the array, and replace commas
      // with the appropriate grouping symbol and dots with the appropriate
      // decimal marker.
      for(var i:Number = 0; i < aAmount.length; i++) {
        if(aAmount[i] == ",") {
          aAmount[i] = sGroup;
        }
        else if(aAmount[i] == ".") {
          aAmount[i] = sDecimal;
        }
      }

      // Add the current symbol.
      var sReturnString:String = ((oSymbols.before) ? sCurrencySymbol : "") + aAmount.join("") + ((!oSymbols.before) ? sCurrencySymbol : "");

      // Return the string.
      return sReturnString;
    }

    /**
     *  Parse a string to a number. Numbers can be parsed using localized
     *  settings.
     *  <p>
     *  Example usage: <br />
     *  trace(nfFormatter.currencyFormat(1000)); // Displays $1,000.00 <br />
     *  trace(nfFormatter.currencyFormat(1000, new Locale("fr")));  // Displays: 1.000,00€ <br />
     *  trace(nfFormatter.currencyFormat(1000, {group: "|", decimal: "%", before: true, currency: "^"}));  // Displays: ^1|000%00 <br />
     *  </p>
     *  to the nearest of a specified interval.
     *  @param  number          The number string you want to parse.
     *  @param  radix           (optional) The radix to use when parsing the string. 10 is the default.
     *  @param  currency        (optional) A Boolean indicating whether or not the number string is formatted
     *                          as currency. The default is false.
     *  @param  locale          (optional) A Locale object.
     *  @return                 The number.
     */
    public function parse(sNumber:String, nRadix:Number, bCurrency:Boolean, lStyle:Locale):Number {

      // If the locale parameter is unspecified, use a default Locale object.
      if(lStyle == null) {
        lStyle = new Locale();
      }

      // Get the symbols.
      var oSymbols:Object = getSymbols(bCurrency, lStyle);

      // Split the string into an array of characters.
      var aCharacters:Array = sNumber.split("");

      // If the radix is undefined then use default radix interpretation.
      if(isNaN(nRadix)) {

        // If the first two characters are 0x, use a radix of 16. If the
        // first character is 0, use a radix of 8. If the first character
        // is a # then use a radix of 16. Otherwise use the default radix of
        // 10.
        if(aCharacters[0] == "O") {
          if(aCharacters[1] == "x") {
            nRadix = 16;
          }
          else {
            nRadix = 8;
          }
        }
        else if(aCharacters[0] == "#") {
          nRadix = 16;
        }
        else {
          nRadix = (isNaN(nRadix)) ? 10 : nRadix;
        }
      }

      // Loop through each character. If the character is a digit, don't do anything.
      // If the character is a decimal point, replace it with a dot. If the radix
      // Is greater than 10, allow alphabetic characters to remain. Otherwise, remove
      // the character from the array.
      for(var i:Number = 0; i < aCharacters.length; i++) {
        switch(aCharacters[i]) {
          case "0":
          case "1":
          case "2":
          case "3":
          case "4":
          case "5":
          case "6":
          case "7":
          case "8":
          case "9":
            break;
          case oSymbols.decimal:
            aCharacters[i] = ".";
            break;
          default:
            if(nRadix > 10) {
              if((aCharacters[i].charCodeAt(0) > 64 && aCharacters[i].charCodeAt(0) < 91) || (aCharacters[i].charCodeAt(0) > 96 && aCharacters[i].charCodeAt(0) < 123)) {
                break;
              }
            }
            aCharacters.splice(i, 1);
            i--;
        }
      }

      // If the radix is 10, simply return the array of characters joined and
      // cast as a number. Otherwise, use parseInt( ).
      if(nRadix == 10) {
        return Number(aCharacters.join(""));
      }
      else {
        return parseInt(aCharacters.join(""), nRadix);
      }
    }

    // This method is used to retreive grouping, decimal, and currency symbols
    // based on the locale.
    private function getSymbols(bCurrency:Boolean, lStyle:Locale):Object {
      var oSymbols:Object = new Object();
      switch(lStyle.languageVariant) {
        case "en-US":
          oSymbols.currency = "$";
          oSymbols.group = ",";
          oSymbols.decimal = ".";
          oSymbols.before = true;
          break;
        case "en-UK":
          oSymbols.currency = "\u00A3";
          oSymbols.group = ",";
          oSymbols.decimal = ".";
          oSymbols.before = true;
          break;
        case "es-MX":
          oSymbols.currency = "$";
          oSymbols.group = ",";
          oSymbols.decimal = ".";
          oSymbols.before = true;
          break;
        case "es-ES":
          oSymbols.currency = "\u20AC";
          oSymbols.group = ".";
          oSymbols.decimal = ",";
          oSymbols.before = false;
          break;
        case "fr":
          oSymbols.currency = "\u20AC";
          oSymbols.group = ".";
          oSymbols.decimal = ",";
          oSymbols.before = false;
          break;
        case "sv":
          oSymbols.currency = "kr";
          oSymbols.group = bCurrency ? "," : " ";
          oSymbols.decimal = ".";
          oSymbols.before = false;
          break;
        case "jp":
          oSymbols.currency = "\u200A5";
          oSymbols.group = ",";
          oSymbols.decimal = ".";
          oSymbols.before = true;
          break;
        case "nl":
          oSymbols.currency = "€";
          oSymbols.group = ".";
          oSymbols.decimal = ",";
          oSymbols.before = true;
          break; 
        default:
          oSymbols.currency = "\u20AC";
          oSymbols.group = ".";
          oSymbols.decimal = ",";
          oSymbols.before = true;
          break;
      }
      return oSymbols;
    }

  }
}