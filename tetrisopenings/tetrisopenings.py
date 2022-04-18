"""
Odd piece of code that determines how many possile combinations of the 7 opening pieces in Tetris can be used for specific openers.
TODO: rework the entire program such that it doesn't require inputting each dependency.
"""

import itertools
pieces = "ILJOSZT"

def mirror(opener):
    newopener = []
    for dependency in opener:
        newdep = [[]]
        for piece in dependency[0]:
            newdep[0].append(mirrorchar(piece))
        newdep.append(mirrorchar(dependency[1]))
        newdep.append(dependency[2])
        newopener.append(newdep)
    return newopener
    
def mirrorchar(piece):
    if piece == 'S':
        return 'Z'
    elif piece == 'Z':
        return 'S'
    elif piece == 'J':
        return 'L'
    elif piece == 'L':
        return 'J'
    return piece
    
def allin(pieces, bag, hold):
    for i in pieces:
        if i not in bag or i in hold:
            return False
    return True

def onein(pieces, bag, hold):
    for i in pieces:
        if i in bag and i not in hold:
            return True
    return False

def opening(order, dependencies):
    onhold = []
    for i in range(7):
        for j in dependencies:
            if (order[i] == j[1] and not allin(j[0], order[0:i], onhold)):
                if j[2] == "all" and not allin(j[0], order[0:i], onhold):
                    onhold.append(order[i])
                elif j[2] == "one" and not onein(j[0], order[0:i], onhold):
                    onhold.append(order[i])
            elif order[i] in j[0] and j[1] in onhold:
                if j[2] == "all" and allin(j[0], order[0:i], onhold):
                    del onhold[onhold.index(j[1])]
                elif j[2] == "one" and onein(j[0], order[0:i], onhold):
                    del onhold[onhold.index(j[1])]
        if len(onhold) >= 2:
            return False
    return True

bags = ["".join(permutation) for permutation in list(itertools.permutations(list(pieces)))]
non_t = ['L', 'J', 'S', 'Z', 'O', 'I']
mko = [[['L'], 'S', 'all'], [['J'], 'Z', 'all'], [non_t, 'T', 'all']]
hachi = [[['L'], 'J', 'all'], [['O'], 'Z', 'all'], [non_t, 'T', 'all']]
#mirrored_hachi = [[['J'], 'L', 'all'], [['O'], 'S', 'all'], [['L', 'J', 'S', 'Z', 'O', 'I'], 'T', 'all']]
mirrored_hachi = mirror(hachi)
sailboat = [[['J'], 'L', 'all'], [['J'], 'O', 'all'], [non_t, 'T', 'all']]
mirrored_sailboat = mirror(sailboat)
"""
[
[[before, before], after], 
[[before], after]
]

"""

openings = [mko, hachi, sailboat]
notworkingbags = []
for bag in bags:
    works = False
    for opener in openings:
        if opening(bag, opener) or opening(bag, mirror(opener)):
            break
    else:
        print(bag, end=", ")
        notworkingbags.append(bag)
totalnotwork = len(notworkingbags)
totalwork = 5040-totalnotwork
print ("Your openings cover " + str(totalwork) + " out of 5040 bags, or " + str((totalwork * 100) // 5040) + "% of them")
