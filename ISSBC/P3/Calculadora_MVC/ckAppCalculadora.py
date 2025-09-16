import sys
from PyQt5.QtWidgets import (QApplication)
import ckVtsCalculadora as vts

app = QApplication(sys.argv) 
w = vts.Calculadora()
w.show()
sys.exit(app.exec_())