#!/usr/bin/env python

import numpy as np

def up_path(i): #returns R(s) for the upper path
    if(i==0):
        return 50
    else:
        return -1

def down_path(i):
    if(i==0):
        return -50
    else:
        return 1


def eval(l):
    sum_up = 0
    sum_down = 0
    for i in range(102):
        sum_up += up_path(i) * pow(l,i)
        sum_down += down_path(i) * pow(l,i)
    return sum_up, sum_down


l_values = np.arange(0.95, 1.005, 0.005)

for i in l_values:
    a,b = eval(i)

    print("====lambda: " + str(i) + "====")
    print("sum up: " + str(a))
    print("sum down: " + str(b))


