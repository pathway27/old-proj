README

Requirements:
	python 2.7+ (Not 3+)

Run Tests:
	python -m unittest discover

if you have multiple versions of python
	py -2 -m unittest discover


Run with a input data, check the example input text in input1.txt for syntax
	python trains.py input1.txt

if you have multiple versions of python
	py -2 trains.py input1.txt 


Design

I used a dictionary for representing graphs, each key is a node of the graph,
representing a town.

Most of the design patterns are built into python such as:
	- iterators
I was going to make the reading and making of the graph using the command design 
pattern, but i didn't have the time.

Algorithms

My algorithms used recursion with a defined limit.

Tests

Passes given data and a graph of my own.