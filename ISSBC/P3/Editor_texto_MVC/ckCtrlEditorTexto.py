# -*- coding: utf-8 -*-
"""
@author: Manuel Peinado Cuenca
"""

import ckModAppEditorTexto as modAp


def saveFileEvent(fileName,text):
    modAp.saveFile(fileName,text)

def readEvent(fileName):
    text=modAp.readText(fileName)
    return text
    
    