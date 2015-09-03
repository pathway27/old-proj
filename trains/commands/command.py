from graph.graph import Graph

class Macro:
    def __init__(self):
        self.commands = []
    def add(self, command):
        self.commands.append(command)
    def run(self):
        for c in self.commands:
            c.execute()

class Command(object):
	def execute(self): pass

class CreateGraphCommand(object):
	def execute(self):
		return Graph()

class DistanceCommand(object):
	pass

class TripsCommand(object):
	pass

class ShortestRouteCommand(object):
	pass

class TripsDistanceCommand(object):
	pass