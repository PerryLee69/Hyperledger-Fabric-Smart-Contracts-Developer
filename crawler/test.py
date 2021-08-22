import urllib

def hello():
    return 'Hello'

c = 'as""\kf'

print('"''"')

print(len(c))

print('\\')

for char in c:
    print(char == '"''"')