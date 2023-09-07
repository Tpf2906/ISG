import logger

import matplotlib.pyplot as plt
from matplotlib.patches import RegularPolygon
import numpy as np

def crete_coordinates():
    pos = []
    row, col = -5, 1
    for i in range(6):
        for j in range(i+5):
            pos.append([row, col])
            col += 1
        row += 1
        col = - i
    row = 1 
    col = -4
    for i in range(5):
        for j in range(9-i):
            pos.append([row,col])
            col = -3+j
        row = 2 + i
        col = -4
    return pos


crete_coordinates()




