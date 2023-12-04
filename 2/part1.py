import re

REDLIMIT = 12
GREENLIMIT = 13
BLUELIMIT = 14

class Round:
    def __init__(self, r: int, g: int, b: int):
        self.red = r
        self.green = g
        self.blue = b
    
    def __str__(self):
        return "r" + str(self.red) + ",g" + str(self.green) + ",b" + str(self.blue)
    
    def is_possible(self, red_limit: int, green_limit: int, blue_limit: int) -> bool:
        if self.red > red_limit or self.blue > blue_limit or self.green > green_limit:
            return False

        return True

def get_game_id(line: str) -> str:
    return re.findall(r'Game (\d+)', line)[0]

def is_game_possible(line: str) -> bool:
    rounds = []
    for rStr in line.split('; '):
        r = re.findall(r'(\d+) red', rStr)
        g = re.findall(r'(\d+) green', rStr)
        b = re.findall(r'(\d+) blue', rStr)

        round = Round(
            0 if len(r) == 0 else int(r[0]),
            0 if len(g) == 0 else int(g[0]),
            0 if len(b) == 0 else int(b[0]))
        
        print("round: " + str(round))
        if not round.is_possible(REDLIMIT, GREENLIMIT, BLUELIMIT):
            return False

    return True

total = 0
with open("input.txt") as f:
    data = f.readlines()
    for line in data:
        print("TOTAL: " + str(total))
        game_id = get_game_id(line)
        print(get_game_id(line))
        if is_game_possible(line):
            print("game " + game_id + " is possible!!")
            total += int(game_id)
        else:
            print("game " + game_id + " is not possible!")

print(total)

