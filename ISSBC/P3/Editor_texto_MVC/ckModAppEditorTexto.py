# -*- coding: utf-8 -*-
"""
@author: Manuel Peinado Cuenca
"""

def saveFile(fileName, text):
    f = open(fileName, 'w')
    f.write(text)
    f.close()
    return True
    
def readText(fileName):
    f = open(fileName, 'r')
    text=f.read()
    f.close()
    return text
    
if __name__=="__main__":
    pass
