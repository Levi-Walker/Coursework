import src.routing_utils as helpers
from src.router import Router


def createNetwork4():
    a = Router("a")
    b = Router("b")
    c = Router("c")
    d = Router("d")
    e = Router("e")

    a.addNeighbors(b, 2)
    b.addNeighbors(a, 2)

    b.addNeighbors(c, 3)
    c.addNeighbors(b, 3)

    c.addNeighbors(d, 2)
    d.addNeighbors(c, 2)

    d.addNeighbors(e, 3)
    e.addNeighbors(d, 3)

    a.addNeighbors(c, 5)
    c.addNeighbors(a, 5)

    b.addNeighbors(d, 6)
    d.addNeighbors(b, 6)

    c.addNeighbors(e, 7)
    e.addNeighbors(c, 7)

    return [a, b, c, d, e]

def printNetwork4(rounds):
    print("Network 4")
    network = createNetwork4()
    helpers.updateRoutes(network, rounds)
    helpers.printRoutingTables(network)
