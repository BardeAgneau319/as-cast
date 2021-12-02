from subprocess import *

def jarWrapper(*args):
    process = Popen(['java', '-jar']+list(args), stdout=PIPE, stderr=PIPE)
    ret = []
    while process.poll() is None:
        line = process.stdout.readline()
        if line.decode() != '' and line.decode.endswith("\n"):
            ret.append(line[:-1])
    stdout, stderr = process.communicate()
    ret += stdout.decode().split('\n')
    if stderr != '':
        ret += stderr.decode().split('\n')
    ret.remove('')
    return ret


def startServers(numberServers):
    counter = 0
    for x in range(numberServers - 1):
        args = ['target/as-cast-demo-0.0.1-SNAPSHOT.jar', '--server.port='+str(8080+counter), '--NODE_ID='+str(counter), '--TOPOLOGY_FILE=topology/perso.xml'] 
        print("server : "+str(counter))
        jarWrapper(*args)
        counter+=1

startServers(4)