
class Router():
    def __init__(self, name):
        self.name = name
        self.neighbors = []
        self.routingTable = {}
        self.nextHop = {}
        self.neighborsRoutingTables = {}

    def addNeighbors(self, neighbor, cost):
        self.neighbors.append(neighbor)
        self.routingTable[neighbor.name] = cost
        return self

    def getRoutingTable(self):
        return dict(sorted(self.routingTable.items()))

    def getNextHopTable(self):
        return dict(sorted(self.nextHop.items()))

    def getNeighborsRoutingTables(self):        
        for n in self.neighbors:
            table = n.getRoutingTable()
            table.pop(self.name)
            self.neighborsRoutingTables[n.name] = table

    def updateRoutingTables(self):
        if not self.neighborsRoutingTables:
            return

        for r in self.neighborsRoutingTables:
            first_leg = self.routingTable[r]

            for n, cost in self.neighborsRoutingTables[r].items():
                current_cost = self.routingTable.get(n, None)
                total_cost = first_leg + cost

                # Add newly discovered destinations
                if current_cost is None:
                    self.nextHop[n] = r
                    self.routingTable[n] = total_cost

                elif total_cost < current_cost:
                    self.nextHop[n] = r
                    self.routingTable[n] = total_cost




        
