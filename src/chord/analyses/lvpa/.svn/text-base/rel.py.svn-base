import sys, re
f = open(sys.argv[1], 'r')
c = f.read()
s1 = set(re.findall(r'\b(\w+)\([\w,]*?\)', c))
s2 = set(re.findall(r'\b(\w+)\(.*?:.*?\)', c))
p = sorted(s1 - s2)
for r in p:
  print r
f.close()
