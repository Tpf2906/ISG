class Move:
    def __init__(self, row, col, player, remove):
        self.row = row
        self.col = col
        self.player = player
        self.remove = remove

    def __str__(self):
        if self.remove:
            return "Player " + str(self.player) + " removed a piece at " + self.row + str(self.col)
        else:
            return "Player " + str(self.player) + " placed a piece at " + self.row + str(self.col)

def create_log():
    log = []
    log.append(Move("J", 6, 1, False))
    log.append(Move("A", 5, 2, True))
    return log

def write_log(log):
    with open("log.txt", "w") as f:
        for move in log:
            f.write(str(move) + "\n")

def undo_last_move(moves):
    moves = moves -1
    if (moves) <= 0:
        return
    lines = []
    with open("log.txt", "r") as f:
        lines = f.readlines()
    with open("log.txt", "w") as f:
        for number , line in enumerate(lines):
            if number != moves:
                f.write(line)

def clear_log():
    with open("log.txt", "w") as f:
        f.write("")