# Closest Points

## A program written for the class CS301 that implements a divide-and-conquer algorithm to find the closest pair of points out of all of the points in a 2D plane

To run this program, you have two options:
* **Manual point input**: Use the terminal command:
```shell
$ java FindClosest
```
and then enter the number of points you would like to input.  Then, individually input the x and y coordinate of each point.  So, to input the points (4, 5), (9, 23), and (45, 104), your input would look like this:
```shell
$ java FindClosest
3
4 5
9 23
45 104
```
You can paste these values if you would like.
* **Reading points from a text file**: Use the terminal command:
```shell
$ java FindClosest < your_text_file.txt
```
The text file must be formatted in the style of the manual point input above: start with the number of points and then list the x and y coordinates of each point.

To generate text files that can be used to input a set of points, you can use the included MakeRandomPointSet program.  When using this program, you must specify the number of points you want to generate, and can then include the x range and y range of the points if you'd like.  You should then specify the file you would like to output these points to.  To generate 10000 points with an x range of 10 and a y range of 500, you would use the following terminal command:
```shell
$ java MakeRandomPointSet 10000 10 500 > generated_point_set.txt
```
