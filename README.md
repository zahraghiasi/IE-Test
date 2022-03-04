Quadtrees are treedata structure in which used to efficiently store data of points on a two-dimensional space. In this tree, each node has at most four children.
We can construct a quadtree from a two-dimensional area using the following steps:
1.Divide the current two dimensional space into four boxes.
2.If a box contains one or more points in it, create a child object, storing in it the two dimensional space of the box
3.If a box does not contain any points, do not create a child for it.
4.Recurse for each of the children.

Quadtrees are used in image compression, where each node contains the average colour of each of its children. The deeper you traverse in the tree, the more the detail of the image.
Quadtrees are also used in searching for nodes in a two-dimensional area. For instance, if you wanted to find the closest point to given coordinates, you can do it using quadtrees.


---java-simple-quadtree---

A quadtree written in Java. Supports insert, search, Area, Subdivide, and more! Plenty of unit tests and also a performance report to compare with a brute force method.


---Creating the QuadTree and inserting items---

Default quadTree is 10000 * 10000, will store a maximum of 10 objects per node, and will grow to a depth of 5.
QuadTree qt = new QuadTree(root);

* Insert some items that implement the RectangleObject interface.
* For demonstration, I will pretend there is a MockRectangleObject implementation of the 
* RectangleObject interface. We will use this to demonstrate the QuadTree functions.
* You can easily implement the interface and insert your own objects. 
* For example, if you were making a game you would make an implementation for each of your game's 
* objects that needed to be in the QuadTree. 

qt.sw = new Node(x1, y1, (x2 - x1)/2 + x1, (y2 - y1)/2 + y1);
qt.swqt = new QuadTree(qt.sw);
qt.se = new Node((x2 - x1)/2 + x1, y1, x2, (y2 - y1)/2 + y1);
qt.seqt = new QuadTree(qt.se);
qt.nw = new Node(x1, (y2 - y1)/2 + y1, (x2 - x1)/2 + x1, y2);
qt.nwqt = new QuadTree(qt.nw);
qt.ne = new Node((x2 - x1)/2 + x1, (y2 - y1)/2 + y1, x2, y2);
qt.neqt = new QuadTree(qt.ne);


---Querying the QuadTree---

* The QuadTree can be easily queried using the search method. Pass in a SearchRectangleObject with
* the bounds you want to search. It will return the RectangleObjects that overlap with 
* your SearchRectangleObject.
*
* The following quereis the 100 * 100 area with the top-left corner at x = 0, y = 0

boolean b = search(p, qt);

.
.
.