import translator

class Move:
    def __init__(self, pos, player, remove):
        self.pos = pos
        self.player = player
        self.remove = remove

    def __str__(self):
        if self.remove:
            return "Player " + str(self.player) + " removed a piece at " + self.pos 
        else:
            return "Player " + str(self.player) + " placed a piece at " + self.pos
def create_log():
    log = []
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