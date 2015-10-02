import java.awt.geom.Point2D;
import java.util.*;

public class PointSet {

  // returns the PointPair for which the points are the closest together
  public static PointPair closestPair(java.awt.geom.Point2D.Double[] pts) {

    System.out.println("Determining closest pair...");

    Point2D.Double[] xArr = new Point2D.Double[pts.length];
    Point2D.Double[] yArr = new Point2D.Double[pts.length];
    for (int i = 0; i < pts.length; i++) {
      xArr[i] = pts[i];
      yArr[i] = pts[i];
    }

    //Preprocessing: sorting points by x and by y
    xArr = mergeSortStart(xArr, "x");
    yArr = mergeSortStart(yArr, "y");

    // now that the arrays are sorted by x and y, we find the closest pair
    return cp (xArr, yArr);
  }


  public static PointPair cp(java.awt.geom.Point2D.Double[] xArr, java.awt.geom.Point2D.Double[] yArr) {
    if (Math.abs(xArr.length) <= 3) {
      return bruteForce(xArr);  // using brute force if the number of points is small
    } else {
      Point2D.Double[] xLeft = new Point2D.Double[xArr.length/2];
      int xArrTraverse = 0;
      for (int i = 0; i < xLeft.length; i++) {
        xLeft[i] = xArr[xArrTraverse++];
      }

      Point2D.Double[] xRight = new Point2D.Double[xArr.length - (xArr.length/2)];
      for (int i = 0; i < xRight.length; i++) {
        xRight[i] = xArr[xArrTraverse++];
      }

      Point2D.Double[] yLeft = mergeSortStart(xLeft.clone(), "y");
      Point2D.Double[] yRight = mergeSortStart(xRight.clone(), "y");

      PointPair closestLeftPair = cp(xLeft, yLeft);
      PointPair closestRightPair = cp(xRight, yRight);

      PointPair closest = new PointPair(new Point2D.Double(0, 0), new Point2D.Double(1, 1)); // initialize variable that will hold the closest of the point pairs

      if (closestLeftPair.closerThan(closestRightPair) < 1) {
        closest = closestLeftPair;
      } else {
        closest = closestRightPair;
      }

      // Now we will look for closer points accross the partition
      double d = Math.abs(closest.getDistance()); // shortest distance between pair so far
      ArrayList<Point2D.Double> yPrime = new ArrayList<Point2D.Double>(); // will store points close to the division
      int division = xLeft.length-1;

      // checking for points within d distance to the left of the partition, and adding any that are found to yPrime
      int currentX = division-1; // this variable will keep track of when we are no longer looking within d distance of the partition
      while ((currentX >= 0) && (xArr[currentX].getX() >= (xArr[division].getX()-d))) {
            yPrime.add(xArr[currentX]);
        currentX--;
      }

      // now checking for points within d distance to the right of the partition
      currentX = division+1;
      while ((currentX <= xArr.length-1) && (xArr[currentX].getX() <= (xArr[division].getX()+d))) {
          yPrime.add(xArr[currentX]);
        currentX++;
      }

      yPrime.add(xArr[division]);

      // now that we have all of the points within d distance of the partition (in terms of x), we want to change the arraylist yPrime into an array
      Point2D.Double yPrimeArr[] = yPrime.toArray(new Point2D.Double[yPrime.size()]);

      // we want this array sorted in terms of y
      yPrimeArr = mergeSortStart(yPrimeArr, "y");

      // now we check each point in this array for any that are closer to it than distance d
      if (yPrimeArr.length > 1) {
        for (int i = 0; i < yPrimeArr.length-1; i++){
          int j = i+1;
          while ((j <= yPrimeArr.length-1) && (yPrimeArr[j].getY() <= (yPrimeArr[i].getY()+d))) {
            PointPair currentPair = new PointPair(yPrimeArr[i], yPrimeArr[j]);
            if(currentPair.closerThan(closest) <1 ) {
              closest = currentPair;
              d = Math.abs(closest.getDistance());
            }
            j++;
          }
        }
      }


      closest=closest.normalize();
      return closest;
    }

  }

  // finds the closest pair of a given set of points by checking each pair of points
  public static PointPair bruteForce(java.awt.geom.Point2D.Double[] pts) {
    boolean first = true; // to allow for a one-time automatic store of the distance of the first pair examined
    PointPair result = new PointPair(pts[0], pts[1]);

    for (int i = 0; i < pts.length; i++) {
      for (int j = 0; j <pts.length; j++) {
        if (i != j) {
          PointPair tempPair = new PointPair(pts[i], pts[j]);
          if (first == false && tempPair.closerThan(result) < 0) {
            result = tempPair;
          }
          if (first == true) {
            result = tempPair;
            first = false;
          }
        }
      }
    }

    return result;

  }



  // Merge Sort code begins
  public static Point2D.Double[] mergeSortStart(java.awt.geom.Point2D.Double[] pts, String param) {
    Point2D.Double[] tempArray = new Point2D.Double[pts.length];
    mergeSort(pts, tempArray, 0, pts.length-1, param);
    return pts;
  }

  public static void mergeSort(java.awt.geom.Point2D.Double[] pts, java.awt.geom.Point2D.Double[] tempArray, int low, int high, String param) {
    if (high > low) {
      int midpoint = low+(high-low)/2;
      mergeSort(pts, tempArray, low, midpoint, param);
      mergeSort(pts, tempArray, midpoint+1, high, param);
      merge(pts, tempArray, low, midpoint, high, param);
    }
  }


  public static void merge(java.awt.geom.Point2D.Double[] pts, java.awt.geom.Point2D.Double[] tempArray, int low, int midpoint, int high, String param) {
    int r = low; // used to place the points into the new merged array
    int l = low;
    int m = midpoint + 1;
    int h = high;

    for (int i = low; i <= high; i++) {
      tempArray[i] = pts[i];
    }

    while (l <= midpoint && m <= h) {
      if (param == "x") {
        if (tempArray[l].getX() < tempArray[m].getX()) {
          pts[r++] = tempArray[l++];
        } else {
          pts[r++] = tempArray[m++];
        }
      } else if (param == "y") {
        if (tempArray[l].getY() < tempArray[m].getY()) {
          pts[r++] = tempArray[l++];
        } else {
          pts[r++] = tempArray[m++];
        }
      } else {
        System.err.println("Sort parameter error");
        System.exit(1);
      }
    }

    while (l <= midpoint) {
      pts[r++] = tempArray[l++];
    }

    while (m <= h) {
      pts[r++] = tempArray[m++];
    }

  } // End of Merge Sort code

}
