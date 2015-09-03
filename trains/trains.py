import sys
from graph.graph import Graph

def main():
	graphs = []
	with open(sys.argv[1], 'r') as f:
		content = f.readlines()
	
	for cmd in content:
		if cmd == '\n':
			continue
		command, params = cmd.split(':')
		print command
		if command == 'graph':
			g = Graph()
			graphs.append(g)
			for node in params.split(', '):
				a, b, c = tuple(node.strip())
				g.add_node_edge(a, b, int(c))
			print g.graph
		elif command == 'distance':
			for route in params.split(', '):
				route = route.strip()
				print route + ': ' + str(g.distance(list(route)))
		elif command == 'trips':
			start, end, condition = tuple(params.strip().split(','))
			oper, maxima = tuple(condition.strip().split(' '))
			start, end, condition = start.strip(), end.strip(), condition.strip()
			oper, maxima = oper.strip(), int(maxima.strip())
			trips = str(g.trips(start, end, stop_condition=(oper, maxima))[1])
			print params.strip() + ': ' + trips
		elif command == 'shortest':
			start, end = tuple(params.split(','))
			start, end = start.strip(), end.strip()
			print params.strip() + ': ' + str(g.shortest_route(start, end)[1])
		elif command == 'trips_distance':
			start, end, condition = tuple(params.strip().split(','))
			oper, maxima = tuple(condition.strip().split(' '))
			start, end, condition = start.strip(), end.strip(), condition.strip()
			oper, maxima = oper.strip(), int(maxima.strip())
			trips = str(g.routes_with_distance(start, end, distance=(oper, maxima))[1])
			print params.strip() + ': ' + trips
		print

if __name__ == '__main__':
	main()