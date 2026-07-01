import src.routing_utils as helpers
from src.router import Router


def createNetwork1():

    a = Router("a")
    b = Router("b")
    c = Router("c")
    d = Router("d")

    #  a--9--b--1--d
    #  |   /
    #  2  3
    #  | /
    #  c 

    a.addNeighbors(b,9).addNeighbors(c,2)
    b.addNeighbors(a,9).addNeighbors(d,1).addNeighbors(c,3)
    c.addNeighbors(a,2).addNeighbors(b,3)
    d.addNeighbors(b,1)

    return [a,b,c,d]


# Expected routing results for topology 1
# a route table -> {'b': 5, 'c': 2, 'd': 6}
# a next hop    -> {'b': 'c', 'd': 'b'}
# b route table -> {'a': 5, 'c': 3, 'd': 1}
# b next hop    -> {'a': 'c'}
# c route table -> {'a': 2, 'b': 3, 'd': 4}
# c next hop    -> {'d': 'b'}
# d route table -> {'a': 6, 'b': 1, 'c': 4}
# d next hop    -> {'a': 'b', 'c': 'b'}

def printNetwork1(rounds):
    print("Network 1")
    network = createNetwork1()
    helpers.updateRoutes(network, rounds)
    helpers.printRoutingTables(network)
