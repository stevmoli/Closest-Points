import java.util.*;
import java.awt.geom.Point2D;

public class FindClosest
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

	// read number of points and make arrays
	int numPoints = scan.nextInt();
	Point2D.Double[] points = new Point2D.Double[numPoints];

	// read points and add to array
	for (int p = 0; p < numPoints; p++)
            {
                points[p] = new Point2D.Double(scan.nextDouble(), scan.nextDouble());
	    }

	System.out.println(PointSet.closestPair(points));
    }
}
