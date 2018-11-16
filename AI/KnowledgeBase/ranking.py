__author__ = 'peiyulin'
from z3 import *

#Four people: Lisa Jim Bob Mary
# we use nlj to denote Lisa is immediately ahead of Jim
# we use bmj to denote Mary majors in biology

nlj=Bool('nlj')
nlb=Bool('nlb')
nlm=Bool('nlm')

njl=Bool('njl')
njb=Bool('njb')
njm=Bool('njm')

nbj=Bool('nbj')
nbl=Bool('nbl')
nbm=Bool('nbm')

nmj=Bool('nmj')
nml=Bool('nml')
nmb=Bool('nmb')

bml=Bool('bml')
bmj=Bool('bmj')
bmb=Bool('bmb')
bmm=Bool('bmm')

s=Solver()

# Lisa is not next to Bob in the ranking
s.add(And(Not(nlb),Not(nbl)))

# Jim is ranked immediately ahead of some guy
s.add(Or(njl,njb,njm))

#This guy is majors in Bio
s.add(And(Implies(njl,bml),Implies(njb,bmb),Implies(njm,bmm)))

# Bob is ranked immediately ahead of Jim
s.add(nbj)

# One of the women (Lisa and Mary) is a biology major /////Suppose: It is ok if they all major in Bio
s.add(Or(bml,bmm))

# One of the women is ranked first
s.add(Or(Not(Or(njl,nbl,nml)),Not(Or(njm,nbm,nlm))))
s.add(Or(nlb,nlj,nlm,nml,nmb,nmj))


#each person can have exactly one rank
#Lisa
s.add(Implies(nlj,And(Not(nlm),Not(nlb),Not(nmj),Not(nbj),Not(njl))))
s.add(Implies(nlb,And(Not(nlm),Not(nlj),Not(nmb),Not(njb),Not(nbl))))
s.add(Implies(nlm,And(Not(nlj),Not(nlb),Not(njm),Not(nbm),Not(nml))))
#Jim
s.add(Implies(njl,And(Not(njm),Not(njb),Not(nbl),Not(nml),Not(nlj))))
s.add(Implies(njb,And(Not(njm),Not(njl),Not(nmb),Not(nlb),Not(nbj))))
s.add(Implies(njm,And(Not(njl),Not(njb),Not(nlm),Not(nbm),Not(nmj))))
#Bob
s.add(Implies(nbl,And(Not(nbm),Not(nbj),Not(nml),Not(njl),Not(nlb))))
s.add(Implies(nbj,And(Not(nbm),Not(nbl),Not(nmj),Not(nlj),Not(njb))))
s.add(Implies(nbm,And(Not(nbl),Not(nbj),Not(njm),Not(nlm),Not(nmb))))
#Mary
s.add(Implies(nml,And(Not(nmb),Not(nmj),Not(nbl),Not(njl),Not(nlm))))
s.add(Implies(nmj,And(Not(nmb),Not(nml),Not(nbj),Not(nlj),Not(njm))))
s.add(Implies(nmb,And(Not(nml),Not(nmj),Not(nlb),Not(njb),Not(nbm))))

print (s.check())

m = s.model()
for d in m.decls():
    print ("%s = %s" % (d.name(), m[d]))
print ("The ranking is Mary, Bob, Jim, Lisa and Lisa majors in biology")

#Output
# sat
# nlb = False
# nmb = True
# nml = False
# nmj = False
# njm = False
# njl = True
# nbj = True
# nbm = False
# nbl = False
# nlm = False
# nlj = False
# njb = False
# bml = True
# bmm = False

# The ranking is Mary, Bob, Jim, Lisa and Lisa majors in biology