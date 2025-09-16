#!/usr/bin/python

import sys
from PyQt5.QtWidgets import QMainWindow, QAction, QMenu, QApplication
from PyQt5.QtWidgets import QPushButton, QMessageBox

class Example(QMainWindow):

    def __init__(self):
        super().__init__()

        self.initUI()

    def initUI(self):

        # Configuramos en la barra de herramientas un menu "File", 
        # con un submenu "Opciones" donde tendra una opcion para 
        # cerrar la ventana
        menubar = self.menuBar()
        fileMenu = menubar.addMenu('File')

        impMenu = QMenu('Opciones', self)
        impAct = QAction('Salir', self)
        impAct.triggered.connect(self.close)

        impMenu.addAction(impAct)

        fileMenu.addMenu(impMenu)

        # Configuramos un boton para cerrar la ventana
        btn = QPushButton("Salir", self)
        btn.resize(btn.sizeHint())
        btn.move(50,50)
        btn.clicked.connect(self.close)
        btn.setToolTip("Este boton <b>Sale</b> de la aplicacion")
        

        self.setGeometry(300, 300, 300, 200)
        self.setWindowTitle('Ejemplo')
        self.show()    

    #Añadimos una ventana emergente para 
    #asegurarnos que queremos cerrar la ventana
    def closeEvent(self, event):

        reply = QMessageBox.question(self, 'Message',
                        "¿Seguro que quieres salir de la aplicación?",
                        QMessageBox.Yes |
                        QMessageBox.No, QMessageBox.No)

        if reply == QMessageBox.Yes:

            event.accept()
        else:

            event.ignore()

def main():
    app = QApplication(sys.argv)
    ex = Example()
    sys.exit(app.exec_())


if __name__ == '__main__':
    main()