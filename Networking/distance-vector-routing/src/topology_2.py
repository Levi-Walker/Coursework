import src.routing_utils as helpers
from src.router import Router

def createNetwork2():
    u = Router("u")
    v = Router("v")
    y = Router("y")
    x = Router("x")
    z = Router("z")

    # u---1---v-\
    # |       |  \
    # |       |   6
    # |       |    \
    # 2       3     z
    # |       |    /
    # |       |   2
    # |       |  /
    # y---3---x-/

    u.addNeighbors(v,1).addNeighbors(y,2)
    v.addNeighbors(u,1).addNeighbors(z,6).addNeighbors(x,3)
    y.addNeighbors(u,2).addNeighbors(x,3)
    x.addNeighbors(y,3).addNeighbors(v,3).addNeighbors(z,2)
    z.addNeighbors(v,6).addNeighbors(x,2)

    return [u,v,y,x,z]

# Expected routing results for topology 2
# u route table -> {'v': 1, 'x': 4, 'y': 2, 'z': 6}
# u next hop    -> {'x': 'v', 'z': 'v'}
# v route table -> {'u': 1, 'x': 3, 'y': 3, 'z': 5}
# v next hop    -> {'y': 'u', 'z': 'x'}
# y route table -> {'u': 2, 'v': 3, 'x': 3, 'z': 5}
# y next hop    -> {'v': 'u', 'z': 'x'}
# x route table -> {'u': 4, 'v': 3, 'y': 3, 'z': 2}
# x next hop    -> {'u': 'v'}
# z route table -> {'u': 6, 'v': 5, 'x': 2, 'y': 5}
# z next hop    -> {'u': 'v', 'v': 'x', 'y': 'x'}

def printNetwork2(rounds):
    print("Network 2")
    network = createNetwork2()
    helpers.updateRoutes(network, rounds)
    helpers.printRoutingTables(network)
