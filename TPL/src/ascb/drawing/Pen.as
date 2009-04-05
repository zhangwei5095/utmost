package ascb.drawing {

  import flash.display.Graphics;
  import flash.geom.Matrix;
  import flash.display.BitmapData;

  public class Pen {

    private var _gTarget:Graphics;
    private var _bLineStyleSet:Boolean;

    /**
     *  Get or set the target movie clip. You can also set the target
     *  movie clip when calling the constructor. When calling the
     *  constructor you can also have the Pen automatically create a
     *  new empty movie clip in a parent.
     *  @example
     *  <pre>
     *  import ascb.drawing.Pen;
     *  
     *  // Have the Pen create a new empty movie clip in this.
     *  var pCircle:Pen = new Pen(this, true);
     *  pCircle.drawCircle(10);
     *  trace(pCircle.target);
     */
    public function set target(gTarget:Graphics):void {
      _gTarget = gTarget;
    }

    public function get target():Graphics {
      return _gTarget;
    }

    public function Pen(gTarget:Graphics) {
      _gTarget = gTarget;
    }

    /**
     *  The lineStyle() method is optional. The Pen class uses a default
     *  line style of you don't call the method.
     *  @param  thickness   (optional) The point thickness of the line (default 0)
     *  @param  rgb         (optional) The RGB value of the line (default 0)
     *  @param  alpha       (optional) The alpha of the line (default 100)
     */
    public function lineStyle(nThickness:Number = 1, nRGB:Number = 0, nAlpha:Number = 1, bPixelHinting:Boolean = false, sScaleMode:String = "normal", sCaps:String = null, sJoints:String = null, nMiterLimit:Number = 3):void {
      _gTarget.lineStyle(nThickness, nRGB, nAlpha, bPixelHinting, sScaleMode, sCaps, sJoints, nMiterLimit);
      _bLineStyleSet = true;
    }
    
    public function lineGradientStyle(sType:String, aColors:Array, aAlphas:Array, aRatios:Array, mtxTransform:Matrix = null, sMethod:String = "pad", sInterpolation:String = "rgb", nFocalPoint:Number = 0):void {
      if(!_bLineStyleSet) {
      	lineStyle();
      }
      _gTarget.lineGradientStyle(sType, aColors, aAlphas, aRatios, mtxTransform, sMethod, sInterpolation, nFocalPoint);
    }

    public function beginFill(nRGB:Number, nAlpha:Number = 1):void {
      _gTarget.beginFill(nRGB, nAlpha);
    }

    public function beginGradientFill(sFillType:String, aColors:Array, aAlphas:Array, aRatios:Array, mtxTransform:Matrix = null, sMethod:String = "pad", sInterpolation:String = "rgb", nFocalPoint:Number = 0):void {
      _gTarget.beginGradientFill(sFillType, aColors, aAlphas, aRatios, mtxTransform, sMethod, sInterpolation, nFocalPoint);
    }
    
    public function beginBitmapFill(bmpData:BitmapData, mtxTransform:Matrix = null, bRepeat:Boolean = true, bSmooth:Boolean = false):void {
      _gTarget.beginBitmapFill(bmpData, mtxTransform, bRepeat, bSmooth);
    }

    public function endFill():void {
      _gTarget.endFill();
    }

    public function clear():void {
      _gTarget.clear();
      _bLineStyleSet = false;
    }

    public function moveTo(nX:Number, nY:Number):void {
      _gTarget.moveTo(nX, nY);
    }

    public function lineTo(nX:Number, nY:Number):void {
      // Make sure the line style is set. Otherwise, use the default values.
      if(!_bLineStyleSet) {
        lineStyle();
      }
      _gTarget.lineTo(nX, nY);
    }

    public function curveTo(nCtrlX:Number, nCtrlY:Number, nAnchorX:Number, nAnchorY:Number):void {
      // Make sure the line style is set. Otherwise, use the default values.
      if(!_bLineStyleSet) {
        lineStyle();
      }
      _gTarget.curveTo(nCtrlX, nCtrlY, nAnchorX, nAnchorY);
    }

    /**
     *  Draw a line from one point to another.
     */
    public function drawLine(nX0:Number, nY0:Number, nX1:Number, nY1:Number):void {
      // Make sure the line style is set. Otherwise, use the default values.
      if(!_bLineStyleSet) {
        lineStyle();
      }
      _gTarget.moveTo(nX0, nY0);
      _gTarget.lineTo(nX1, nY1);
    }

    /**
     *  Draw a curve from one point to another.
     */
    public function drawCurve(nX:Number, nY:Number, nCtrlX:Number, nCtrlY:Number, nAnchorX:Number, nAnchorY:Number):void {
      // Make sure the line style is set. Otherwise, use the default values.
      if(!_bLineStyleSet) {
        lineStyle();
      }
      _gTarget.moveTo(nX, nY);
      _gTarget.curveTo(nCtrlX, nCtrlY, nAnchorX, nAnchorY);
    }

    public function drawRect(nX:Number, nY:Number, nWidth:Number, nHeight:Number):void {
      if(!_bLineStyleSet) {
        lineStyle();
      }
      _gTarget.drawRect(nX, nY, nWidth, nHeight);
    }
    
    public function drawRoundRect(nX:Number, nY:Number, nWidth:Number, nHeight:Number, nRadius:Number):void {
      if(!_bLineStyleSet) {
        lineStyle();
      }
      _gTarget.drawRoundRect(nX, nY, nWidth, nHeight, nRadius);
    }

    public function drawRoundRectComplex(nX:Number, nY:Number, nWidth:Number, nHeight:Number, nA:Number, nB:Number, nC:Number, nD:Number):void {
      if(!_bLineStyleSet) {
        lineStyle();
      }
      _gTarget.drawRoundRectComplex(nX, nY, nWidth, nHeight, nA, nB, nC, nD);
    }

    public function drawCircle(nX:Number, nY:Number, nRadius:Number):void {
      if(!_bLineStyleSet) {
        lineStyle();
      }
      _gTarget.drawCircle(nX, nY, nRadius);
    }

    public function drawSlice (nArc:Number, nRadius:Number, nStartingAngle:Number, nX:Number, nY:Number):void {
      drawArc(nX, nY, nRadius, nArc, nStartingAngle, true);
    }

    public function drawArc (nX:Number, nY:Number, nRadius:Number, nArc:Number, nStartingAngle:Number = 0, bRadialLines:Boolean = false):void  {

      // The angle of each of the eight segments is 45 degrees (360 divided by eight),
      // which equals p/4 radians.
      if(nArc > 360) {
        nArc = 360;
      }
      nArc = Math.PI/180 * nArc;
      var nAngleDelta:Number = nArc/8;

      // Find the distance from the circle's center to the control points
      // for the curves.
      var nCtrlDist:Number = nRadius/Math.cos(nAngleDelta/2);

      nStartingAngle *= Math.PI / 180;

      var nAngle:Number = nStartingAngle;
      var nCtrlX:Number;
      var nCtrlY:Number;
      var nAnchorX:Number;
      var nAnchorY:Number;

      var nStartingX:Number = nX + Math.cos(nStartingAngle) * nRadius;
      var nStartingY:Number = nY + Math.sin(nStartingAngle) * nRadius;

      if(bRadialLines) {
        moveTo(nX, nY);
        lineTo(nStartingX, nStartingY);
      }
      else {
        // Move to the starting point, one radius to the right of the circle's center.
        moveTo(nStartingX, nStartingY);
      }

      // Repeat eight times to create eight segments.
      for (var i:Number = 0; i < 8; i++) {

        // Increment the angle by angleDelta (p/4) to create the whole circle (2p).
        nAngle += nAngleDelta;

        // The control points are derived using sine and cosine.
        nCtrlX = nX + Math.cos(nAngle-(nAngleDelta/2))*(nCtrlDist);
        nCtrlY = nY + Math.sin(nAngle-(nAngleDelta/2))*(nCtrlDist);

        // The anchor points (end points of the curve) can be found similarly to the
        // control points.
        nAnchorX = nX + Math.cos(nAngle) * nRadius;
        nAnchorY = nY + Math.sin(nAngle) * nRadius;

        // Draw the segment.
        curveTo(nCtrlX, nCtrlY, nAnchorX, nAnchorY);
      }
      if(bRadialLines) {
        lineTo(nX, nY);
      }
    }


    public function drawEllipse (nX:Number, nY:Number, nRadiusX:Number, nRadiusY:Number):void {

      var nAngleDelta:Number = Math.PI / 4;

      var nAngle:Number = 0;

      // Whereas the circle has only one distance to the control point 
      // for each segment, the ellipse has two distances: one that 
      // corresponds to xRadius and another that corresponds to yRadius.
      var nCtrlDistX:Number = nRadiusX / Math.cos(nAngleDelta/2);
      var nCtrlDistY:Number = nRadiusY / Math.cos(nAngleDelta/2);
      var nCtrlX:Number;
      var nCtrlY:Number;
      var nAnchorX:Number;
      var nAnchorY:Number;

      moveTo(nX + nRadiusX, nY);

      for (var i:Number = 0; i < 8; i++) {
        nAngle += nAngleDelta;
        nCtrlX = nX + Math.cos(nAngle-(nAngleDelta/2))*(nCtrlDistX);
        nCtrlY = nY + Math.sin(nAngle-(nAngleDelta/2))*(nCtrlDistY);
        nAnchorX = nX + Math.cos(nAngle) * nRadiusX;
        nAnchorY = nY + Math.sin(nAngle) * nRadiusY;
        this.curveTo(nCtrlX, nCtrlY, nAnchorX, nAnchorY);
      }
    }

    /**
     *  Draw a triangle given the lengths of two sides and the angle between them.
     */
    public function drawTriangle (nX:Number, nY:Number, nAB:Number, nAC:Number, nAngle:Number, nRotation:Number = 0):void {

      nRotation = nRotation * Math.PI / 180;

      // Convert the angle between the sides from degrees to radians.
      nAngle = nAngle * Math.PI / 180;


      // Calculate the coordinates of points b and c.
      var nBx:Number = Math.cos(nAngle - nRotation) * nAB;
      var nBy:Number = Math.sin(nAngle - nRotation) * nAB;
      var nCx:Number = Math.cos(-nRotation) * nAC;
      var nCy:Number = Math.sin(-nRotation) * nAC;

      // Calculate the centroid's coordinates.
      var nCentroidX:Number = 0;
      var nCentroidY:Number = 0;

      // Move to point a, then draw line ac, then line cb, and finally ba (ab).
      drawLine(-nCentroidX + nX, -nCentroidY + nY, nCx - nCentroidX + nX, nCy - nCentroidY + nY);
      lineTo(nBx - nCentroidX + nX, nBy - nCentroidY + nY);
      lineTo(-nCentroidX + nX, -nCentroidY + nY);
    }

    public function drawRegularPolygon (nX:Number, nY:Number, nSides:Number, nLength:Number, nRotation:Number = 0):void {

      nRotation = nRotation * Math.PI / 180;

      // The angle formed between the segments from the polygon's center as shown in 
      // Figure 4-5. Since the total angle in the center is 360 degrees (2p radians),
      // each segment's angle is 2p divided by the number of sides.
      var nAngle:Number = (2 * Math.PI) / nSides;

      // Calculate the length of the radius that circumscribes the polygon (which is
      // also the distance from the center to any of the vertices).
      var nRadius:Number = (nLength/2)/Math.sin(nAngle/2);

      // The starting point of the polygon is calculated using trigonometry where 
      // radius is the hypotenuse and nRotation is the angle.
      var nPx:Number = (Math.cos(nRotation) * nRadius) + nX;
      var nPy:Number = (Math.sin(nRotation) * nRadius) + nY;

      // Move to the starting point without yet drawing a line.
      moveTo(nPx, nPy);

      // Draw each side. Calculate the vertex coordinates using the same trigonometric
      // ratios used to calculate px and py earlier.
      for (var i:Number = 1; i <= nSides; i++) {
        nPx = (Math.cos((nAngle * i) + nRotation) * nRadius) + nX;
        nPy = (Math.sin((nAngle * i) + nRotation) * nRadius) + nY;
        lineTo(nPx, nPy);
      }
    }

    public function drawStar(nX:Number, nY:Number, nPoints:Number, nInnerRadius:Number, nOuterRadius:Number, nRotation:Number = 0):void {

      if(nPoints < 3) {
        return;
      }

      var nAngleDelta:Number = (Math.PI * 2) / nPoints;
      nRotation = Math.PI * (nRotation - 90) / 180;

      var nAngle:Number = nRotation;

      var nPenX:Number = nX + Math.cos(nAngle + nAngleDelta / 2) * nInnerRadius;
      var nPenY:Number = nY + Math.sin(nAngle + nAngleDelta / 2) * nInnerRadius;

      moveTo(nPenX, nPenY);

      nAngle += nAngleDelta;

      for(var i:Number = 0; i < nPoints; i++) {
        nPenX = nX + Math.cos(nAngle) * nOuterRadius;
        nPenY = nY + Math.sin(nAngle) * nOuterRadius;
        lineTo(nPenX, nPenY);
        nPenX = nX + Math.cos(nAngle + nAngleDelta / 2) * nInnerRadius;
        nPenY = nY + Math.sin(nAngle + nAngleDelta / 2) * nInnerRadius;
        lineTo(nPenX, nPenY);
        nAngle += nAngleDelta;
      }

    }

  }
  
}