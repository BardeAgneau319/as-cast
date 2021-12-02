import requests
from string import ascii_uppercase
from time import sleep

_LETTERS = {letter: index for index,
           letter in enumerate(ascii_uppercase, start=1)}

TOPOLOGY_PATH = "../src/main/resources/topology"

def _letter_to_number(letter):
    return _LETTERS[letter] - 1


def to_url(node):
    return "http://localhost:" + str(8080 + _letter_to_number(node))

def wait_for_node(node):
    while True:
        r = requests.get(to_url(node) + "/admin/source")
        if r.json():
            return
        sleep(0.5)