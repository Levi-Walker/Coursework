def updateRoutes(network, times):
    for _ in range(times):
        for n in network:
            n.getNeighborsRoutingTables()

        for n in network:
            n.updateRoutingTables()

def printRoutingTables(network):
    for r in network:
        print(r.name, "route table ->", r.getRoutingTable())
        print(r.name, "next hop    ->", r.getNextHopTable())
