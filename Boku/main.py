import logger


#coluna vai ser fila + 6 -input
col_translator = {
    "A": 0,
    "B": 1,
    "C": 2,
    "D": 3,
    "E": 4,
    "F": 5,
    "G": 6,
    "H": 7,
    "I": 8,
    "J": 9,
}

def create_board():
    board = []
    for i in range(6):
        row = []
        row.append([0] * (i+5))
        board.append(row[0])
    for i in range(5):
        row = []
        row.append([0] * (9-i))
        board.append(row[0])
    return board

def show_board(board):
    for i in range(11):
        if i >= 6:
            print(" " * (i-5))
        print("__" * len(board[i]))
        for j in range(len(board[i])):
            print("|", end="")
            if board[i][j] == 0:
                print("0", end="")
            elif board[i][j] == 1:
                print("X", end="")
            else:
                print("O", end="")
            
        print("|")
    print("___________")
    


board = create_board()
show_board(board)