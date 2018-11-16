__author__ = 'peiyulin'

from z3 import *

# if l1 is True then the lady is in the Room 1
l1=Bool('l1')
l2=Bool('l2')
l3=Bool('l3')

s=Solver()

#Each contains either a lady or a tiger but not both
#Furthermore, one room contained a lady and the other two contained tigers.
#Actually we only have three possible state: LTT, TLT, TTL
s.add(Or(And(l1,Not(l2),Not(l3)),And(Not(l1),l2,Not(l3)),And(Not(l1),Not(l2),l3)))

#Room1: Not(l1)
#Room2: l2
#Room2: Not(l2)

#at most one of the three signs was true means that
#no sign is true or one of them is true
s.add(Or(Not(Or(Not(l1),l2,Not(l2))),And(Not(l1),Not(l2),l2),And(l1,l2,Not(l2)),And(l1,Not(l2),Not(l2))))

print (s.check())


print("Here is what z3 reasons:")
m = s.model()
for d in m.decls():
    print ("%s = %s" % (d.name(), m[d]))

#Output:
# sat
# l2 = False
# l1 = True
# l3 = False
# The lady is in Room1.


print("Suppose the lady is in the Room1. Then the S will be false")
#Suppose the lady is in the Room1, Then KB add Not(l1) will be false
s.add(Not(l1))
print (s.check())
if str(s.check())=='unsat':
    print("The lady is in the Room1")
else:
    print("The lady is not in the Room1")








