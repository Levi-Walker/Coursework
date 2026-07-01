import src.routing_utils as helpers
from src.router import Router

def createNetwork3():
    z = Router("z")
    x = Router("x")
    y = Router("y")
    w = Router("w")
    v = Router("v")
    t = Router("t")
    u = Router("u")


    z.addNeighbors(x,8).addNeighbors(y,12)
    x.addNeighbors(z,8).addNeighbors(y,6).addNeighbors(v,3).addNeighbors(w,6)
    y.addNeighbors(z,12).addNeighbors(x,6).addNeighbors(t,7).addNeighbors(v,8)
    w.addNeighbors(x,6).addNeighbors(v,4).addNeighbors(u,3)
    v.addNeighbors(w,4).addNeighbors(x,3).addNeighbors(y,8).addNeighbors(t,4).addNeighbors(u,3)
    t.addNeighbors(y,7).addNeighbors(v,4).addNeighbors(u,2)
    u.addNeighbors(w,3).addNeighbors(v,3).addNeighbors(t,2)

    return [z,x,y,w,v,t,u]

# Expected routing results for topology 3
# z route table -> {'t': 15, 'u': 14, 'v': 11, 'w': 14, 'x': 8, 'y': 12}
# z next hop    -> {'t': 'x', 'u': 'x', 'v': 'x', 'w': 'x'}
# x route table -> {'t': 7, 'u': 6, 'v': 3, 'w': 6, 'y': 6, 'z': 8}
# x next hop    -> {'t': 'v', 'u': 'v'}
# y route table -> {'t': 7, 'u': 9, 'v': 8, 'w': 12, 'x': 6, 'z': 12}
# y next hop    -> {'u': 't', 'w': 'x'}
# w route table -> {'t': 5, 'u': 3, 'v': 4, 'x': 6, 'y': 12, 'z': 14}
# w next hop    -> {'t': 'u', 'y': 'x', 'z': 'x'}
# v route table -> {'t': 4, 'u': 3, 'w': 4, 'x': 3, 'y': 8, 'z': 11}
# v next hop    -> {'z': 'x'}
# t route table -> {'u': 2, 'v': 4, 'w': 5, 'x': 7, 'y': 7, 'z': 15}
# t next hop    -> {'w': 'u', 'x': 'v', 'z': 'v'}
# u route table -> {'t': 2, 'v': 3, 'w': 3, 'x': 6, 'y': 9, 'z': 14}
# u next hop    -> {'x': 'v', 'y': 't', 'z': 'v'}

def printNetwork3(rounds):
    print("Network 3")
    network = createNetwork3()
    helpers.updateRoutes(network, rounds)
    helpers.printRoutingTables(network)
