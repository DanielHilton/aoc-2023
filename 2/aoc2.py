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

def get_game_id(game_line: str) -> str:
    return re.findall(r'Game (\d+)', game_line)[0]

def parse_round(round_str: str) -> Round:
    r = re.findall(r'(\d+) red', round_str)
    g = re.findall(r'(\d+) green', round_str)
    b = re.findall(r'(\d+) blue', round_str)

    return Round(
        0 if len(r) == 0 else int(r[0]),
        0 if len(g) == 0 else int(g[0]),
        0 if len(b) == 0 else int(b[0]))

def is_game_possible(game_line: str) -> bool:
    for rStr in game_line.split('; '):
        round = parse_round(rStr)
        if not round.is_possible(REDLIMIT, GREENLIMIT, BLUELIMIT):
            return False

    return True

def part1():
    total = 0
    with open("input.txt") as f:
        data = f.readlines()
        for line in data:
            print("TOTAL: " + str(total))
            game_id = get_game_id(line)
            print(game_id)
            if is_game_possible(line):
                total += int(game_id)
            else:
                print("game " + game_id + " is not possible!")

    print(total)

part1()

def part2():
    total_power = 0
    with open("input.txt") as f:
        data = f.readlines()
        for game_line in data:
            print("ROUND:" + get_game_id(game_line))
            print(game_line)

            min_r = 0
            min_g = 0
            min_b = 0

            for round_str in game_line.split('; '):
                round = parse_round(round_str)
                if round.red > min_r:
                    min_r = round.red
                if round.green > min_g:
                    min_g = round.green
                if round.blue > min_b:
                    min_b = round.blue
            
            print("MIN R: " + str(min_r) + ", MIN G: " + str(min_g) + ", MIN_B: " + str(min_b))
            power = min_r * min_g * min_b
            print(power)
            total_power += power

    print(total_power)    

part2()
