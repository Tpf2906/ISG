import logger
import translator

import matplotlib.pyplot as plt
from matplotlib.patches import RegularPolygon
import numpy as np
import math
from operator import itemgetter

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

def get_player_move():
    position = input("Enter a position: ")
    return position

def get_player_input():
    player = input("Enter a player: ")
    return player

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

def check_winner(board):
    upwards_positions = [0,5,6,11,12,13,18,19,20,21,26,27,28,29,30,35,36,37,38,39,40
                         ,45,46,47,48,49,54,55,56,57,62,63,64,69,70,71]
    nw_positions = [26,27,28,29,30,35,36,37,38,39,40,45,46,47,48,49,50,54,55,56
                    ,57,58,59,62,63,64,65,66,67,69,70,71,72,73,74,75,76,77,78,79]
    ne_positions = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,18,19,20,21,22,23,26,27,28
                    ,29,30,31,35,36,37,38,39,40,45,46,47,48,49]
    for i in range(len(upwards_positions)):
        pos = str(board[i][:3])
        next1 = get_next_position(pos, "up")
        next2 = get_next_position(next1, "up")
        next3 = get_next_position(next2, "up")
        next4 = get_next_position(next3, "up")
        if (translator.board_dict[pos] == translator.board_dict[next1] == translator.board_dict[next2]
            == translator.board_dict[next3] == translator.board_dict[next4] != 0):
            return translator.board_dict[pos]
   
    for i in range(len(nw_positions)):
        pos = str(board[i][:3])
        next1 = get_next_position(pos, "nw")
        next2 = get_next_position(next1, "nw")
        next3 = get_next_position(next2, "nw")
        next4 = get_next_position(next3, "nw")
        if (translator.board_dict[pos] == translator.board_dict[next1] == translator.board_dict[next2]
            == translator.board_dict[next3] == translator.board_dict[next4] != 0):
            return translator.board_dict[pos]
    
    for i in range(len(ne_positions)):
        pos = str(board[i][:3])
        next1 = get_next_position(pos, "ne")
        next2 = get_next_position(next1, "ne")
        next3 = get_next_position(next2, "ne")
        next4 = get_next_position(next3, "ne")
        if (translator.board_dict[pos] == translator.board_dict[next1] == translator.board_dict[next2]
            == translator.board_dict[next3] == translator.board_dict[next4] != 0):
            return translator.board_dict[pos]
        
    return 0

def check_capture(position,player):
    list_of_moves=[]
    up_pieces = []
    down_pieces = []
    nw_pieces = []
    ne_pieces = []
    sw_pieces = []
    se_pieces = []
    back_up_position = position
    
    for i in range(5):
        next_up_pos = get_next_position(str(position), "up")
        next_down_pos = get_next_position(str(position), "down")
        if player == -translator.board_dict[str(next_up_pos)]:
            up_pieces.append(str(position))
            position = next_up_pos
        elif player == translator.board_dict[str(next_up_pos)]:
            list_of_moves.append(up_pieces)
        else:
            break
    position = back_up_position
    for i in range(5):
        next_down_pos = get_next_position(str(position), "down")
        if player == -translator.board_dict[str(next_down_pos)]:
            down_pieces.append(str(position))
            position = next_down_pos
        elif player == translator.board_dict[str(next_down_pos)]:
            list_of_moves.append(down_pieces)
        else:   
            break
    position = back_up_position
    for i in range(5):
        next_nw_pos = get_next_position(str(position), "nw")
        if player == -translator.board_dict[str(next_nw_pos)]:
            nw_pieces.append(str(position))
            position = next_nw_pos
        elif player == translator.board_dict[str(next_nw_pos)]:
            list_of_moves.append(nw_pieces)
        else:
            break
    position = back_up_position
    for i in range(5):
        next_ne_pos = get_next_position(str(position), "ne")
        if player == -translator.board_dict[str(next_ne_pos)]:
            ne_pieces.append(str(position))
            position = next_ne_pos
        elif player == translator.board_dict[str(next_ne_pos)]:
            list_of_moves.append(ne_pieces)
        else:
            break
    position = back_up_position
    for i in range(5):
        next_sw_pos = get_next_position(str(position), "sw")
        if player == -translator.board_dict[str(next_sw_pos)]:
            sw_pieces.append(str(position))
            position = next_sw_pos
        elif player == translator.board_dict[str(next_sw_pos)]:
            list_of_moves.append(sw_pieces)
        else:
            break
    position = back_up_position
    for i in range(5):
        next_se_pos = get_next_position(str(position), "se")
        if player == -translator.board_dict[str(next_se_pos)]:
            se_pieces.append(str(position))
            position = next_se_pos
        elif player == translator.board_dict[str(next_se_pos)]:
            list_of_moves.append(se_pieces)
        else:
            break

    return list_of_moves

def get_next_position(position, direction): 
    
    up_vector = [0,1,-1]
    nw_vector = [-1,1,0]
    ne_vector = [1,0,-1]
    pos= []
    if direction == "up":
        pos = [position[0] + up_vector[0], position[1] + up_vector[1], position[2] + up_vector[2]]
    elif direction == "nw":
        pos = [position[0] + nw_vector[0], position[1] + nw_vector[1], position[2] + nw_vector[2]]
    elif direction == "ne":
        pos = [position[0] + ne_vector[0], position[1] + ne_vector[1], position[2] + ne_vector[2]]
    elif direction == "down":
        pos = [position[0] - up_vector[0], position[1] - up_vector[1], position[2] - up_vector[2]]
    elif direction == "sw":
        pos = [position[0] - ne_vector[0], position[1] - ne_vector[1], position[2] - ne_vector[2]]
    else:
        pos = [position[0] - nw_vector[0], position[1] - nw_vector[1], position[2] - nw_vector[2]]
    return pos

def best_move(board, ai,human):
    best_score = float("-inf")
    for pos in board:
        if pos[3] == 0:
            pos[3] = ai
            score = minimax(board,0,-math.inf,math.inf,True,ai,human)
            pos[3] = 0
            if score > best_score:
                best_score = score
                best_move = pos
    return best_move

def minimax(board, depth,alpha,beta, isMaximizing,ai,human):
    result = check_winner(board)
    if result != 0:
        return result

    if (isMaximizing):
        bestScore = float("-inf")
        for pos in board:
            if pos[3] == 0:
                temp_board = board.copy()
                place_piece(temp_board, pos, ai)
                score = minimax(board, depth + 1,alpha,beta, False,ai,human)
                if score > bestScore:
                    bestScore = score
                    ret_pos = pos
                alpha = max(alpha, bestScore)
                if beta <= alpha:
                    break
        return bestScore
    else:
        bestScore = float("inf")
        for pos in board:
            if pos[3] == 0:
                temp_board = board.copy()
                place_piece(temp_board, pos, human)
                score = minimax(board, depth + 1,alpha,beta, True,ai,human)
                if score < bestScore:
                    bestScore = score
                    ret_pos = pos
                beta = min(beta, bestScore)
                if beta <= alpha:
                    break
        return bestScore

def start_game():
    board , positions = create_board()
    board = sorted(board, key=itemgetter(0,1,2))
    print(board)
    my_positions = []
    my_available_moves = positions
    my_available_moves_copy = positions
    moves = 0
    capture = False
    log = logger.create_log()
    
    player = int(get_player_input())
    if player == 1:
        myTurn = True
    else:
        myTurn = False	
    while True:
        if not myTurn:
            position = get_player_move()
            place_piece(board, position, player)
            log.append(logger.Move(position, player, False))
            logger.write_log()
            myTurn = True
        else:
            return
    

        
start_game()
