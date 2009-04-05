package ascb.util {

	public class StringUtilities {
		
		private static var codeMap:Object;
		private static var reverseCodeMap:Object;

		public static function encode( original:String ):String {
		
			// The codeMap property is assigned to the StringUtils class when the encode() method
			// is first run. Therefore, if no codeMap is yet defined, it needs to be created.
			if ( codeMap == null ) {
				
				// The codeMap property is an associative array that maps each original code
				// point to another code point.
				codeMap = new Object();
				
				// Create an array of all the code points from 0 to 255.
				var originalMap:Array = new Array();
				for ( var i:int = 0; i < 256 ; i++ ) {
					originalMap.push( i );
				}
			
				// Create a temporary array that is a copy of the originalMap array.
				var tempChars:Array = originalMap.concat();
				
				// Loop through all the character code points in originalMap.
				for ( var i:int = 0; i < originalMap.length; i++ ) {
					// Create a random number that is between 0 and the last index of tempChars.
					var randomIndex:int = Math.floor( Math.random() * ( tempChars.length - 1 ) );
					
					// Assign to codeMap values such that the keys are the original code points,
					// and the values are the code points to which they should be mapped.
					codeMap[ originalMap[i] ] = tempChars[ randomIndex ];
					
					// Remove the elements from tempChars that was just assigned to codeMap. 
					// This prevents duplicates.
					tempChars.splice( randomIndex, 1 );
				}
			}
				
			// Split the string into an array of characters.
			var characters:Array = original.split("");
			
			// Replace each character in the array with the corresponding value from codeMap.
			for ( i = 0; i < characters.length; i++ ) {
				characters[i] = String.fromCharCode( codeMap[ characters[i].charCodeAt( 0 ) ] );
			}
			
			// Return the encoded string.
			return characters.join( "" );
		}
		
		public static function decode( encoded:String ):String {
		
		  // Split the encoded string into an array of characters.
		  var characters:Array = encoded.split( "" );
		
		  // The reverseCodeMap property is assigned the first time the decode() method is 
		  // first run. Therefore, if no reverseCodeMap is yet defined, it needs to be created.
		  if ( reverseCodeMap == null ) {
		  	
		  	// Create an associative array that reverses the keys and values of codeMap.
		    // This allows you to do a reverse lookup based on the encoded character
		    // rather than the original character.
		    reverseCodeMap = new Object();
		    for ( var key in codeMap ) {
		      reverseCodeMap[ codeMap[key] ] = key;
		    }
		  }
		
		  // Loop through all the characters in the array, and replace them
		  // with the corresponding value from reverseCodeMap, thus recovering
		  // the original character values.
		  for ( var i:int = 0; i < characters.length; i++ ) {
		    characters[i] = String.fromCharCode( reverseCodeMap[ characters[i].charCodeAt( 0 ) ] );
		  }
		
		  // Return the decoded string.
		  return characters.join( "" );
		}
		
		// Returns true if the character is a whitespace character
		public static function isWhitespace( ch:String ):Boolean {
			return ch == '\r' || 
					ch == '\n' ||
					ch == '\f' || 
					ch == '\t' ||
					ch == ' '; 
		}
		
		public static function trim( original:String ):String {
			// Split the string into an array of characters.
			var characters:Array = original.split( "" );
		
			// Remove any whitespace elements from the beginning of the array using
			// splice(). Use a break statement to exit the loop when you reach a
			// non-whitespace character to prevent it from removing whitespace
			// in the middle of the string.
			for ( var i:int = 0; i < characters.length; i++ ) {
				if ( isWhitespace( characters[i] ) ) {
					characters.splice( i, 1 );
					i--;
				} else {
					break;
				}
			}
		
			// Loop backward through the array removing whitespace elements until a
			// non-whitespace character is encountered. Then break out of the loop.
			for ( i = characters.length - 1; i >= 0; i-- ) {
				if ( isWhitespace( characters[i] ) ) {
					characters.splice( i, 1 );
				} else {
					break;
				}
			}
		
			// Recreate the string with the join() method and return the result.
			return characters.join("");
		}
		
		public static function toTitleCase( original:String ):String {
			var words:Array = original.split( " " );
			for ( var i:int = 0; i < words.length; i++ ) {
				words[i] = toInitialCap( words[i] );
			}
		  return ( words.join( " " ) );
		}
				
		public static function toInitialCap( original:String ):String {
			return original.charAt( 0 ).toUpperCase() + original.substr( 1 ).toLowerCase();
		}
		
		public static function removeExtension( filename:String ):String {
			// Find the location of the period.
			var extensionIndex:Number = filename.lastIndexOf( '.' );
			if ( extensionIndex == -1 ) {
				// Oops, there is no period. Just return the filename.
				return filename;
			} else {
				return filename.substr( 0, extensionIndex );
			} 
		}
		
		// This function returns everything after the last period, if any.
		public static function extractExtension( filename:String ):String {
			// Find the location of the period.
			var extensionIndex:Number = filename.lastIndexOf( '.' );
			if ( extensionIndex == -1 ) {
				// Oops, there is no period, so return the empty string.
				return "";
			} else {
				return filename.substr( extensionIndex + 1, filename.length );
			}
		}

	}
}