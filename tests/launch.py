from subprocess import *
import sys
import networkx as nx
from utils import TOPOLOGY_PATH

def jarWrapper(*args):
    Popen(['java', '-jar']+list(args), stdout=PIPE)

def start(topology):
    graph = nx.read_graphml(TOPOLOGY_PATH + "/" + topology)
    x = 0
    while x < len(graph.nodes):
        args = ['../target/as-cast-demo-0.0.1-SNAPSHOT.jar', '--server.port='+str(8080+x), '--NODE_ID='+str(x), '--TOPOLOGY_FILE=topology/' + topology]
        print("Server: "+ list(graph.nodes)[x] + " => " + str(8080+x))
        jarWrapper(*args)
        x += 1

if (len(sys.argv) > 1):
    topology = sys.argv[1]
else:
    topology = 'perso.xml'

start(topology)