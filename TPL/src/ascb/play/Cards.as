package ascb.play {

  import ascb.util.NumberUtilities;
  import ascb.util.ArrayUtilities;

  public class Cards {

    private var _cdkDeck:CardDeck;

    // The Cards constructor creates a deck of cards.
    public function Cards() {
      _cdkDeck = new CardDeck();
    }

    // The deal() method needs to know the number of players in the game 
    // and the number of cards to deal per player. If the cardsPerPlayer 
    // parameter is undefined, then it deals all the cards.
    public function deal(nPlayers:Number, nPerPlayer:Number = -1):Array {

      _cdkDeck.reset();
      _cdkDeck.shuffle();

      // Create an array, players, that holds the cards dealt to each player.
      var aHands:Array = new Array();

      // If a cardsPerPlayer value was passed in, deal that number of cards.
      // Otherwise, divide the number of cards (52) by the number of players.
      var nCardsEach:Number = (nPerPlayer == -1) ? Math.floor(_cdkDeck.deck.length / nPlayers) : nPerPlayer;

      // Deal out the specified number of cards to each player.
      for (var i:uint = 0; i < nPlayers; i++) {

        aHands.push(new CardHand(_cdkDeck));

        // Deal a random card to each player. Remove that card from the 
        // tempCards array so that it cannot be dealt again.
        for (var j:Number = 0; j < nCardsEach; j++) {
          aHands[i].hand.push(_cdkDeck.deck.shift());
        }

        // Use Cards.orderHand() to sort a player's hand, and use setHand() 
        // to assign it to the card player object.
        aHands[i].orderHand();
      }

      // Return the players array.
      return aHands;
    }
  }
  
  private class Card {

    private var _nValue:Number;
    private var _sName:String;
    private var _sSuit:String;

    public function get value():Number {
      return _nValue;
    }

    public function get name():String {
      return _sName;
    }

    public function get suit():String {
      return _sSuit;
    }

    public function get display():String {
      return _sName + " " + _sSuit;
    }

    public function Card(nValue:Number, sName:String, sSuit:String) {
      _nValue = nValue;
      _sName = sName;
      _sSuit = sSuit;
    }

    public function toString():String {
      return display;
    }
  	
  }
  
  private class CardDeck {
  
    private var _aCards:Array;
  
    public function get deck():Array {
      return _aCards;
    }
    
    public function CardDeck() {
      _aCards = new Array();
      reset();
    }
    
    public function reset():Void {
      for(var i:Number = 0; i < _aCards.length; i++) {
        _aCards.shift();
      }

      // Create a local array that contains the names of the four suits.
      var aSuits:Array = ["Hearts", "Diamonds", "Spades", "Clubs"];

      // Specify the names of the cards for stuffing into the cards array later.
      var aCardNames:Array = ["2", "3", "4", "5", "6", "7", "8", "9", "10",
                              "J", "Q", "K", "A"];

      // Create a 52-card array. Each element is an object that contains
      // properties for: the card's integer value (for sorting purposes), card name, 
      // suit name, and display name. The display name combines the card's name
      // and suit in a single string for display to the user.
      for (var i:Number = 0; i < aSuits.length; i++) {
        // For each suit, add thirteen cards
        for (var j:Number = 0; j < 13; j++) {
          _aCards.push(new Card(j, aCardNames[j], aSuits[i]));
        }
      }
    }

    public function shuffle():Void {
      var aShuffled:Array = ArrayUtilities.randomize(_aCards);
      for(var i:Number = 0; i < aShuffled.length; i++) {
        _aCards[i] = aShuffled[i];
      }
    }
    
    public function push(oParameter:Object):Void {
      _aCards.push(oParameter);
    }
  
  }
  
  private class CardHand {

    private var _cdkDeck:CardDeck;
    private var _aHand:Array;
    
    public function get hand():Array {
      return _aHand;
    }
    
    public function get length():uint {
      return _aHand.length;
    }

    // When a new card player is created by way of its constructor, pass it
    // a reference to the card deck, and give it a unique player ID.
    public function CardHand(cdkDeck:CardDeck) {
      _aHand = new Array();
      _cdkDeck = cdkDeck;
    }
    
    public function getCardAt(nIndex:uint):Card {
      return _aHand[nIndex];
    }
    

    public function discard():Array {
      var aCards:Array = new Array();
      for(var i:Number = 0; i < arguments.length; i++) {
        aCards.push(_aHand[arguments[i]]);
        _cdkDeck.push(_aHand[arguments[i]]);
      }
      for(var i:Number = 0; i < arguments.length; i++) {
        _aHand.splice(arguments[i], 1);
      }
      return aCards;
    }

    public function draw(nDraw:Number = 1):Void {
    
      // Add the specified number of cards to the hand.
      for (var i:uint = 0; i < nDraw; i++) {
        _aHand.push(_cdkDeck.deck.shift());
      }

      orderHand();
    }

    public function orderHand():Void {
      _aHand.sort(sorter);
    }

    // Used by sort() in the orderHand() method to sort the cards by suit and rank.
    private function sorter(crdA:Card, crdB:Card):Number {
      if (crdA.suit > crdB.suit) {
        return 1;
      } else if (crdA.suit < crdB.suit) {
        return -1;
      } else {
        return (Number(crdA.value) > Number(crdB.value)) ? 1 : 0;
      }
    }
    
  }
  
}