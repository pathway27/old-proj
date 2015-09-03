import operator


class Graph(object):
    def __init__(self):
        """ Graph class, made up of nodes (keys) and its neighbours (values) 
            represented in a dictionary (self.graph)
        """

        self.graph = {}

    def add_node(self, node):
        if node not in self.graph:
            self.graph[node] = {}

    def add_edge(self, node1, node2, distance):
        if node1 in self.graph:
            self.graph[node1][node2] = distance

    def add_node_edge(self, node1, node2, distance):
        self.add_node(node1)
        self.add_edge(node1, node2, distance)

    def size(self):
        """ Number of nodes in graph
        
        :rtype: integer
        """
        return len(self.graph.keys())

    # TODO: NoRouteException
    # TODO: Add Iterator?
    # Procedural patern
    # Change if to dict.get('item', 'nosuch')
    # raise no such route
    def distance(self, route):
        """Follows a route of the graph and returns the distance.

        :param route: ['A', 'B', 'C']
        """
        neighbours = self.graph.get(route[0])
        if len(route) == 2:
            return neighbours.get(route[1])
        else:
            try:
                return neighbours.get(route[1]) + self.distance(route[1:])
            except TypeError:
                return 'NO SUCH ROUTE'

    def shortest_route(self, node1, node2):
        """Finds the shortest route from node1 to node2, based on the
        weight of the edges. Assuming the size of the graph as the limit to trips. 

        :rtype: tuple: (the shortest trip, weight of the trip)
        """
        sorted_trips = {}
        for trip in self.trips(node1, node2,
                               stop_condition=('<=', self.size()))[0]:
            sorted_trips[trip] = self.distance(list(trip))
        trip = min(sorted_trips, key=sorted_trips.get)
        return trip, sorted_trips[trip]

    def routes_with_distance(self, node1, node2, distance):
        """ Finds all the paths from node1 to node2 based on
        a condition of the weights.

        :params: distance: (condition, maximum)
                 the conditon can be: <= or ==

        :rtype: tuple: (list of routes path, number of routes)
        """
        sorted_trips = {}
        op_map = {'<': operator.lt, '<=': operator.le, '==': operator.eq}
        # Filter condition based on nodes with weight 1 vs nodes with weight
        # size(graph) 10 |vs| distance required 30
        for trip in self.trips(node1, node2,
                               stop_condition=('<=', self.size() * 2))[0]:
            sorted_trips[trip] = self.distance(list(trip))
        # TODO: Add operator based on distance
        sorted_trips = dict((k, v) for k, v in sorted_trips.items()
                            if op_map[distance[0]](v, distance[1]))
        return sorted_trips, len(sorted_trips)

    def trips(self, start, end, all_paths=None, cpath="",
              stop_condition=('==', 10)):
        """Finds all the trips possible from start node to end.
        Follows Cycles. Limited by recursion depth and is 
        exponentially related to number of nodes in the graph.

        :params: stop_condition: (condition, maximum)
                 the conditon can be: <= or ==

        :rtype: tuple: (list of every path, number of paths)
        """
        op_map = {'<': operator.lt, '<=': operator.le, '==': operator.eq}
        op, maximal = stop_condition[0], stop_condition[1]
        
        if all_paths is None:
            all_paths = []

        cpath += start
        for node in self.graph[start]:
            if len(cpath) > maximal:
                continue
            if node == end and op_map[op](len(cpath), maximal):
                all_paths.append(cpath + node)
                self.trips(node, end, all_paths, cpath, stop_condition)
            else:
                self.trips(node, end, all_paths, cpath, stop_condition)
        return all_paths, len(all_paths)

if __name__ == '__main__':
    g = Graph()
    g.add_node_edge('A', 'B', 5)
    g.add_node_edge('B', 'C', 4)
    g.add_node_edge('C', 'D', 8)
    g.add_node_edge('D', 'C', 8)
    g.add_node_edge('D', 'E', 6)
    g.add_node_edge('A', 'D', 5)
    g.add_node_edge('C', 'E', 2)
    g.add_node_edge('E', 'B', 3)
    g.add_node_edge('A', 'E', 7)
    # g.graph
    # print g.distance(['A', 'B', 'C', 'D'])
    #paths = []
    #paths(g, 'C', 'C', p)
    # print p
    #print g.trips('C', 'C', stop_condition=('<=', 5))[1]
    print g.trips('C', 'C', stop_condition=('<=', 3))
    #print g.routes_with_distance('C', 'C', distance=('<', 30))
    # print g.graph
    gg = {}
    for k, v in g.graph.items():
        gg[k] = v.keys()
    # print gg
    # print paths
    # print g.trips('C', 'C', ('EQ', 4))
    # print g.shortest_route('A', 'C')
    # print g.shortest_route('B', 'B')
    # print routes('C', 'C', ('LT', 30))
