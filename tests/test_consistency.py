import networkx as nx
import pytest
import requests
from time import sleep

from utils import to_url, wait_for_node, TOPOLOGY_PATH


class TestConsistency:

    @pytest.fixture
    def graph(self):
        return nx.read_graphml(TOPOLOGY_PATH + "/consistency.xml")

    def test_two_sources(self, graph):
        for node, data in graph.nodes(data=True):
            if data['isNode']:
                if node in ["A", "D"]:
                    r = requests.get(to_url(node) + "/admin/is-source")
                    r = r.json()
                    assert data["isSource"] == r
                    r = requests.get(to_url(node) + "/admin/source")
                    r = r.json()
                    assert r.get('distance') == 0
                    assert r.get('node').get('address') == to_url(node)
                    assert not r.get('path')
                elif node == 'B':
                    r = requests.get(to_url(node) + "/admin/source")
                    r = r.json()
                    assert r.get('distance') == 1
                    assert r.get('node').get('address') == to_url("D")
                else:  # C
                    r = requests.get(to_url(node) + "/admin/source")
                    r = r.json()
                    assert r.get('distance') == 2
                    assert r.get('node').get('address') == to_url("D")
    
    def test_two_removals(self, graph):
        r = requests.delete(to_url("A") + "/admin/source")
        assert r.status_code == 200
        r = requests.delete(to_url("D") + "/admin/source")
        assert r.status_code == 200
        sleep(5)
        for node, data in graph.nodes(data=True):
            r = requests.get(to_url(node) + "/admin/source")
            assert not r.json()
    
    def test_two_adds_and_deletes(self, graph):
        r = requests.post(to_url("A") + "/admin/source")
        assert r.status_code == 200
        r = requests.post(to_url("D") + "/admin/source")
        assert r.status_code == 200
        r = requests.delete(to_url("A") + "/admin/source")
        assert r.status_code == 200
        r = requests.delete(to_url("D") + "/admin/source")
        assert r.status_code == 200
        sleep(5)
        for node, data in graph.nodes(data=True):
            r = requests.get(to_url(node) + "/admin/source")
            assert not r.json()
    

