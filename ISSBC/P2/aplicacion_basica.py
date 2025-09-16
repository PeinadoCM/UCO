import sys
from PyQt5.QtWidgets import (QPushButton, QMessageBox, QMainWindow, 
                             QMenu, QAction, QApplication, QHBoxLayout, QVBoxLayout,
                             QWidget)
from PyQt5.QtGui import QIcon

class Example(QMainWindow):

    def __init__(self):
        super().__init__()

        self.initUI()

    def initUI(self):

        # Creamos la barra de estados y hacemos que siempre tenga un mensaje
        self.statusbar = self.statusBar()
        self.statusbar.showMessage("Esta es la barra de estados")

        # Creamos una barra de herramientas y le ponemos una opcion para salir
        exitActToolbar = QAction(QIcon('exit.png'), '&Exit', self)
        exitActToolbar.triggered.connect(QApplication.instance().quit)
        toolbar = self.addToolBar('Exit')
        toolbar.addAction(exitActToolbar)

        # Creamos un menu de opciones que contiene un submenu para ocultar la barra de estados
        # y tambien tiene una opcion para salir
        menubar = self.menuBar()
        fileMenu = menubar.addMenu('File')

        statAct = QAction('Ver barra de estados', self, checkable=True)
        statAct.setChecked(True)
        statAct.triggered.connect(self.toggleMenu)
        exitActMenu = QAction('Salir', self)
        exitActMenu.triggered.connect(self.close)

        impMenu = QMenu('Opciones', self)
        impMenu.addAction(statAct)

        fileMenu.addMenu(impMenu)
        fileMenu.addAction(exitActMenu)

        # Configuramos un boton para cerrar la ventana
        btn = QPushButton("Salir", self)
        btn.resize(btn.sizeHint())
        btn.clicked.connect(self.close)
        btn.setToolTip("Este boton <b>Sale</b> de la aplicacion")
        
        # Configuramos un boton que nos cree una ventana emergente con un mensaje
        btn2 = QPushButton("Hola", self)
        btn2.resize(btn2.sizeHint())
        btn2.clicked.connect(self.saludo)

        # Utilizamos el widget para poder posicionar los botones ya que en un QMainWindow
        # no se puede utilizar QVBoxLayout/QHBoxLayout
        central_widget = QWidget()
        self.setCentralWidget(central_widget)

        # POsicionamos el boton de Hola arriba a la derecha y el de Salir abajo a la derecha
        vbox = QVBoxLayout()
        vbox.addWidget(btn2)
        vbox.addStretch(1)
        vbox.addWidget(btn)

        hbox = QHBoxLayout()
        hbox.addStretch(1)
        hbox.addLayout(vbox)

        central_widget.setLayout(hbox)

        self.setGeometry(300, 300, 300, 200)
        self.setWindowTitle('Aplicacion Basica')
        self.show()    

    def toggleMenu(self, state):
        if state:
            self.statusbar.show()
            self.statusbar.showMessage("Esta es la barra de estados")
        else:
            self.statusbar.hide()

    def saludo(self):
        QMessageBox.about(self, 'Saludo', 'Hola, ¿como estas?')

def main():
    app = QApplication(sys.argv)
    ex = Example()
    sys.exit(app.exec_())


if __name__ == '__main__':
    main()