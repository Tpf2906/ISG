import logger
import translator

import matplotlib.pyplot as plt
from matplotlib.patches import RegularPolygon
import numpy as np

def create_coordinates():
    pos = []
    x , y , z= 0,0,0
    for i in range(6):
        x , y , z = i,0,-i
        for j in range(10-i):
           pos.append([x,y,z])
           y += 1
           z -= 1
    x , y , z= 0,0,0
    for i in range(1,6):
        x , y , z = -i ,i,0
        for j in range(10-i):
            pos.append([x,y,z])
            y += 1
            z -= 1
    return pos

def create_board():
    board = []
    positions = create_coordinates()
    for position in positions:
        position.append(0)
        board.append(position)
    return board , positions

def occupied_positions(board):
    occupied = []
    for position in board:
        if position[3] != 0:
            occupied.append(position[:3])
        
    return occupied

def is_occupied(board, position):
    for pos in occupied_positions(board):
        if pos == position:
            return True
    return False

def free_positions(board):
    free = []
    for position in board:
        if position[3] == 0:
            free.append(position[:3])
    return free

def is_free(board, position):
    for pos in free_positions(board):
        if pos == position:
            return True
    return False
   
def draw_board(board):
    
    colors = []
    for position in board:
        if position[3] == 0:
            colors.append(["green"])
        elif position[3] == 1:
            colors.append(["red"])
        else:
            colors.append(["blue"])
    # Horizontal cartesian coords
    hcoord = [c[0] for c in board]

    # Vertical cartersian coords
    vcoord = [2. * np.sin(np.radians(60)) * (c[1] - c[2]) /3. for c in board]


    fig, ax = plt.subplots(1, figsize=(10, 10))
    ax.set_aspect('equal')

    # Add some coloured hexagons
    for x, y, c in zip(hcoord, vcoord, colors):
        color = c[0]
        hex = RegularPolygon((x, y), numVertices=6, radius=2. / 3, 
                             orientation=np.radians(30), facecolor = color,
                             alpha=0.3, edgecolor='k')
        ax.add_patch(hex)
        # Also add a text label

    # Also add scatter points in hexagon centres
    ax.scatter(hcoord, vcoord, alpha=0.3)

    plt.show()


def remove_piece(board, position):
    position = list(translator.position_to_coordinate[position])
    if is_occupied(board, position):
        for pos in board:
            if pos[:3] == position:
                logger.log_move(pos[0], pos[1], pos[2], pos[3], True)
                pos[3] = 0
                break
    else:
        print("Position is not occupied")



def place_piece(board, position, player):
    letter_pos = position
    position = list(translator.position_to_coordinate[position])
    if is_free(board, position):
        for pos in board:
            if pos[:3] == position:
                logger.log_move(pos[0], pos[1], pos[2], player, False)
                pos[3] = player
                break
    else:
        print("Position is not free")


def get_player_input():
    position = input("Enter a position: ")
    return position


def get_adjacent_pieces(position,positions):
    adjacent = []
    for pos in positions:
        if pos[0] == position[0] + 1 and pos[1] == position[1] - 1 and pos[2] == position[2]:
            adjacent.append(pos)
        elif pos[0] == position[0] - 1 and pos[1] == position[1] + 1 and pos[2] == position[2]:
            adjacent.append(pos)
        elif pos[0] == position[0] and pos[1] == position[1] - 1 and pos[2] == position[2] + 1:
            adjacent.append(pos)
        elif pos[0] == position[0] and pos[1] == position[1] + 1 and pos[2] == position[2] - 1:
            adjacent.append(pos)
        elif pos[0] == position[0] + 1 and pos[1] == position[1] and pos[2] == position[2] - 1:
            adjacent.append(pos)
        elif pos[0] == position[0] - 1 and pos[1] == position[1] and pos[2] == position[2] + 1:
            adjacent.append(pos)
    return adjacent


def start_game():
    player_1_positions = []
    player_2_positions = []
    board , positions = create_board()
    draw_board(board)

start_game()