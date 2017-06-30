#!/usr/bin/env python
import numpy as np

S_0 = np.array([1.0, 0, 0, 0, 0, 0, 0, 0, 0])

def trans(old):
    if(old == 0):
        return np.array([0, 0.5, 0.5, 0, 0, 0, 0, 0, 0])
    if(old == 1):
        return np.array([0, 0, 0.5, 0.5, 0, 0, 0, 0, 0])
    if(old == 2):
        return np.array([0, 0.5, 0, 0, 0.5, 0, 0, 0, 0])
    if(old == 3):
        return np.array([0, 0, 0, 0.5, 0, 0.5, 0, 0, 0])
    if(old == 4):
        return np.array([0, 0.5, 0, 0, 0, 0, 0.5, 0, 0])
    if(old == 5):
        return np.array([0, 0.5, 0, 0, 0, 0, 0, 0.5, 0])
    if(old == 6):
        return np.array([0, 0.5, 0, 0, 0, 0, 0, 0, 0.5])
    if(old == 7):
        return np.array([0, 0, 0, 0, 0, 0, 0, 1.0, 0])
    if(old == 8):
        return np.array([0, 0, 0, 0, 0, 0, 0, 0, 1.0])
    else:
        print("SHOULD NEVER HAPPEN")
        exit()
        return np.array(0)


def norm(vec):
    return vec/np.sum(vec)


def step(old):
    sum = np.array([0, 0, 0, 0, 0, 0, 0, 0, 0.0])
    for i in range(9):
        sum += trans(i) * old[i]
    return norm(sum)

print(S_0)
vec = S_0
for i in range(100):
    print(step(vec))
    vec = step(vec)
    print(str(i) + ": " + str(vec))
