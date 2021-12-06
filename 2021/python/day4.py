class Card(object):
    def __init__(self, c):
        self.card = []
        for r in c:
            self.card.append([[int(v.strip()), False] for v in r.strip().split()])

    def playNumber(self, n):
        for r in self.card:
            try:
                i = [n[0] for n in r].index(n)
                r[i][1] = True
            except(ValueError):
                pass

    def checkRows(self):
        for row in self.card:
            if (self.checkRow(row)):
                return True
        return False

    def checkRow(self, row):
        for v in row:
            if(v[1] == False):
                return False
        return True

    def checkColumns(self):
        for col in range(5):
            if (self.checkColumn(col)):
                return True
        return False

    def checkColumn(self, col):
        for v in [n[col] for n in self.card]:
            if(v[1] == False):
                return False
        return True

    def isWinner(self):
        if(self.checkRows()):
            return True
        if(self.checkColumns()):
            return True
        return False

    def sumUnmarked(self):
        s = 0
        for r in self.card:
            for v in r:
                if(v[1] == False):
                    s = s + v[0]
        return s
    def print(self):
        print('----------')
        for r in self.card:
            print(r)
        print('----------')



# get 5 lines
def takeFive(lines):
    return lines[0:5], lines[5:]


with open("../resources/day-04/input.txt", "r") as f:
    calls = [int(x) for x in f.readline().split(',')]
    lines = [x.strip() for x in f.readlines() if x != '\n']
    cards = []
    while(len(lines) > 0):
        card, lines = takeFive(lines)
        cards.append(Card(card))
    
    for v in calls:
        print("called {}".format(v))
        for c in cards:
            c.playNumber(v)
            if(c.isWinner()):
                print("Winner is Found")
                print(v * c.sumUnmarked())
                break
