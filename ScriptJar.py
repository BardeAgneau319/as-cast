from subprocess import *
import subprocess

def jarWrapper(*args):
    subprocess.Popen(['java', '-jar']+list(args), stdout=PIPE)

def startServers(numberServers):
    counter = 0
    for x in range(numberServers):
        args = ['target/as-cast-demo-0.0.1-SNAPSHOT.jar', '--server.port='+str(8080+counter), '--NODE_ID='+str(counter), '--TOPOLOGY_FILE=topology/perso.xml'] 
        print("server : "+str(counter))
        jarWrapper(*args)
        counter+=1

startServers(4)