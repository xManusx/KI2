#!/usr/bin/env python


def DM(state):  #true = BM
    if(state):
        return 0.6
    else:
        return 0.4

def ev(state, music):
    if((state == 1) & (music == 1)):
        return 0.6
    elif((state == 1) & (music == 0)):
        return 0.3
    elif((state == 0) & (music == 1)):
        return 0.4
    elif((state == 0) & (music == 0)):
        return 0.7
    else:
        print("SOMETHING REEEEEALLY WRONG")
        exit()
        return 0xDEADBEEF  #never happens


def nextStep(now_state, prev_state):
    if( (now_state == 1) & (prev_state == 1)):
        return 0.7
    elif((now_state == 0) & (prev_state == 1)):
        return 0.3
    elif((now_state == 1) & (prev_state == 0)):
        return 0.15
    else:
        return 0.85


"""
x_0 = 0

sum = 0
for x_1 in range(2):
    for x_2 in range(2):
        for x_3 in range(2):
            temp = DM(x_1) * DM(x_2) * DM(x_3) * \
                    nextStep(x_1, x_0) * nextStep(x_2, x_1) * nextStep(x_3, x_2)
            print(temp)
            sum += temp

print(sum)
"""


ev_seq = [0, 1, 1, 1, 0] 

def out(t, evidences, state, temp):
    mus = "BM" if(evidences[t]) else "J"
    st = "B" if(state) else "H"
    print("t=" + str(t) + ", " + mus + ", " + st + "     |   " + str(temp))


def filtering(t, evidences, state):
    if(t==0):
        if(state == 1):
            return 0
        else:
            return 1

    temp = 0
    for i in range(2):
        temp += nextStep(state, i) * filtering(t-1, evidences, i)
    temp *= ev(state, evidences[t])
    out(t, evidences, state, temp)
    return temp


print(filtering(4, ev_seq, 1))
