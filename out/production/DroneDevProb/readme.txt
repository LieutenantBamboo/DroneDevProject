
The Drone Delivery Problem (a challenge)
----------------------------------------

We have a collection of customers, each with an x/y coordinate. Customers have to be 
serviced by drones, i.e. goods are delivered to customers via drones. 

The problem is to specify where depots have to be placed, such that (1) customers
are serviced by drones from the customer's nearest depot (2) there is a cost of 2500
units (fixed cost) for setting up a depot, (3) delivery cost to a customer is the
Euclidean distance from their nearest depot (4) a drone can fly a distance of 
at most 150 units (i.e. no customer can be more than 150 units of distance away from a depot).

The object is to find the lowest cost solution: sum of distances plus sum of depot costs

Software and problems
---------------------
Given a file of customers (such as p22.txt or p317.txt) and a solution
(such as depot22.txt or depotP317) the program

 > java Warehouses depot317.txt p317.txt
 
 will cost and display the solution.
 
 You can also look at a file of customers as follows
 
 > java DisplayCustomers p317.txt
 
 
 What you have to do
 --------------------
 Email me a solution to p317.txt, attached to an email with subject: "Drone Delivery Problem", where the 
 file is in the following format (see depotP317.txt or depotP22.txt):
 
 (1) first line is number of depots
 (2) subsequent lines give the x and y location of a depot
 
 You can email me a solution as often as you want
 
 Patrick
 