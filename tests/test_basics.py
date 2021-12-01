import networkx as nx
import pytest
import requests
from string import ascii_uppercase
from time import sleep


class TestBasics:

    @pytest.fixture
    def graph(self):
        return nx.read_graphml("../perso.xml")

    def test_initialization(self, graph):
        for node, data in graph.nodes(data=True):
            r = requests.get(to_url(node) + "/admin/is-source")
            r = r.json()
            assert data["isSource"] == r

            r = requests.get(to_url(node) + "/admin/neighbors")
            r = r.json()
            r = [node.get('address') for node in r]

            for neighbor in graph.neighbors(node):
                assert to_url(neighbor) in r

    def test_post_source(self, graph):
        r = requests.post(to_url("D") + "/admin/source")
        assert r.status_code == 200
        r = requests.get(to_url("D") + "/admin/is-source")
        assert r.json() == True
        sleep(1)
        for neighbor in graph.neighbors("D"):
            r = requests.get(to_url(neighbor) + "/admin/source")
            r = r.json()
            assert r.get('distance') == 1
            
            if neighbor == "C":
                assert r.get('node').get('address') == to_url("D")
            else:
                assert r.get('node').get('address') != to_url("D")
    
    def test_delete_source(self, graph):
        r = requests.delete(to_url("A") + "/admin/source")
        assert r.status_code == 200
        r = requests.get(to_url("A") + "/admin/is-source")
        assert r.json() == False
        wait_for_node('A')
        r = requests.get(to_url("A") + "/admin/source")
        r = r.json()
        assert r.get('node').get('address') == to_url("D")
        assert r.get('distance') == 2
        for neighbor in graph.neighbors("A"):
            r = requests.get(to_url(neighbor) + "/admin/source")
            r = r.json()
            assert r.get('node').get('address') == to_url("D")


LETTERS = {letter: index for index,
           letter in enumerate(ascii_uppercase, start=1)}


def letter_to_number(letter):
    return LETTERS[letter] - 1


def to_url(node):
    return "http://localhost:" + str(8080 + letter_to_number(node))

def wait_for_node(node):
    while True:
        r = requests.get(to_url(node) + "/admin/source")
        if r.json():
            return
        sleep(0.5)