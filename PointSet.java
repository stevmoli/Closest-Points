import java.awt.geom.Point2D;
import java.util.*;

public class PointSet {

  // returns the PointPair for which the points are the closet together
  public static PointPair closestPair(java.awt.geom.Point2D.Double[] pts) {

    Point2D.Double[] xArr = new Point2D.Double[pts.length];
    Point2D.Double[] yArr = new Point2D.Double[pts.length];
    for (int i = 0; i < pts.length; i++) {
      xArr[i] = pts[i];
      yArr[i] = pts[i];
    }

    //Preprocessing: sorting points by x and by y
    xArr = mergeSortStart(xArr, "x");
    yArr = mergeSortStart(yArr, "y");

    // Testing sort
    // System.out.println("Sorted by x: ");
    // for (int i=0; i < xArr.length; i++) {
    //   System.out.println(xArr[i]);
    // }
    // System.out.println("Sorted by y: ");
    // for (int i=0; i < yArr.length; i++) {
    //   System.out.println(yArr[i]);
    // }

    // now that the arrays are sorted by x and y, we find the closest pair
    return cp (xArr, yArr);
  }


  public static PointPair cp(java.awt.geom.Point2D.Double[] xArr, java.awt.geom.Point2D.Double[] yArr) {
    if (Math.abs(xArr.length) <= 3) {
      System.out.println("Brute force with the following array:");
      for (int i = 0; i < xArr.length; i++) {
        System.out.println(xArr[i]);
      }
      return bruteForce(xArr);  // using brute force if the number of points is small
    } else {
      
    }

    //TODO remember to normalize the solution

    PointPair test = new PointPair(new Point2D.Double(1, 3), new Point2D.Double(3, 1));
    return test;  //THIS IS TEST CODE
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
            //TODO if distance is equal, save both into PointPair array to check against any result found?
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
