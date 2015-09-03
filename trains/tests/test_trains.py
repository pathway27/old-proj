import random
import unittest

from graph.graph import Graph


class TestGivenGraph(unittest.TestCase):

    def setUp(self):
        self.g = Graph()
        self.g.add_node_edge('A', 'B', 5)
        self.g.add_node_edge('B', 'C', 4)
        self.g.add_node_edge('C', 'D', 8)
        self.g.add_node_edge('D', 'C', 8)
        self.g.add_node_edge('D', 'E', 6)
        self.g.add_node_edge('A', 'D', 5)
        self.g.add_node_edge('C', 'E', 2)
        self.g.add_node_edge('E', 'B', 3)
        self.g.add_node_edge('A', 'E', 7)

    def test_distance_1(self):
        self.assertEqual(self.g.distance(['A', 'B', 'C']), 9)

    def test_distance_2(self):
        self.assertEqual(self.g.distance(['A', 'D']), 5)

    def test_distance_3(self):
        self.assertEqual(self.g.distance(['A', 'D', 'C']), 13)

    def test_distance_4(self):
        self.assertEqual(self.g.distance(['A', 'E', 'B', 'C', 'D']), 22)

    def test_distance_5(self):
        self.assertEqual(self.g.distance(['A', 'E', 'D']), 'NO SUCH ROUTE')

    def test_trips_1(self):
        self.assertEqual(self.g.trips('C', 'C', stop_condition=('<=', 3)),
                         (['CEBC', 'CDC'], 2))

    def test_trips_extra(self):
        self.assertEqual(self.g.trips('C', 'C', stop_condition=('<=', 4)),
                         (['CEBC', 'CDC', 'CDCDC', 'CDEBC'], 4))
        self.assertEqual(self.g.trips('A', 'B', stop_condition=('<=', 3)),
                         (['AB', 'AEB', 'ADEB'], 3))

    def test_trips_2(self):
        self.assertEqual(self.g.trips('A', 'C', stop_condition=('==', 4)),
                         (['ABCDC', 'ADCDC', 'ADEBC'], 3))

    def test_shortest_route_1(self):
        self.assertEqual(self.g.shortest_route('A', 'C'), ('ABC', 9))

    def test_shortest_route_2(self):
        self.assertEqual(self.g.shortest_route('B', 'B'), ('BCEB', 9))

    def test_routes_with_distance(self):
        self.assertEqual(
            self.g.routes_with_distance('C', 'C', distance=('<', 30))[1],
            7)

class TestACoolerGraph(unittest.TestCase):

    def setUp(self):
        self.g = Graph()
        self.g.add_node_edge('A', 'B', 2)
        self.g.add_node_edge('B', 'C', 2)
        self.g.add_node_edge('C', 'D', 2)
        self.g.add_node_edge('D', 'A', 2)
        self.g.add_node_edge('A', 'C', 3)
        self.g.add_node_edge('C', 'A', 3)
        self.g.add_node_edge('D', 'B', 3)
        self.g.add_node_edge('B', 'D', 3)

    def test_distance_1(self):
        self.assertEqual(self.g.distance(['A', 'B', 'C']), 4)

    def test_trips_1(self):
        self.assertEqual(self.g.trips('A', 'C', stop_condition=('<=', 3)),
                         (['AC', 'ACAC', 'ABC'], 3))

    def test_shortest_route_1(self):
        self.assertEqual(self.g.shortest_route('A', 'A'), ('ACA', 6))

    def test_routes_with_distance(self):
        self.assertEqual(
            self.g.routes_with_distance('D', 'D', distance=('<', 8))[1], 4)
    

if __name__ == '__main__':
    unittest.main()
