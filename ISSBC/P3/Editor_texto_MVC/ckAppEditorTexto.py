# -*- coding: utf-8 -*-
"""
@author: Manuel Peinado Cuenca
"""

import os, sys
from PyQt5.QtWidgets import (QApplication)
from os.path import dirname, isdir, isfile, join
from ckVtsEditorTexto import EditorTexto

app=QApplication([])
w=EditorTexto()
w.show()

sys.exit(app.exec_())