import sys, os
from PyQt5.QtWidgets import (QWidget, QPushButton,
                            QApplication, QLabel, QGridLayout, QLineEdit,
                            QListWidget, QTextEdit, QFileDialog)

class Editor_texto(QWidget):

    def __init__(self):
        super().__init__()

        self.initUI()

    def initUI(self):

        self.opened_file = ""

        # Creación de elementos de la ventana
        txtFolder = QLabel('Carpeta:', self)

        folderButton = QPushButton("Carpeta")
        openButton = QPushButton("Abrir")
        saveButton = QPushButton("Guardar")
        saveAsButton = QPushButton("Guardar como")
        closeButton = QPushButton("Cerrar")
        exitButton = QPushButton("Salir")

        self.lineFolder = QLineEdit("")
        self.lineFolder.setReadOnly(True)
        self.listFile = QListWidget()
        self.lineText = QTextEdit("")

        # Posicionamos los elementos creados anteriormente
        gridla = QGridLayout(self)
        gridla.addWidget(txtFolder, 1, 1, 1, 1) 
        gridla.addWidget(self.lineFolder, 1, 2, 1, 24)
        gridla.addWidget(self.listFile, 2, 1, 24, 5)
        gridla.addWidget(self.lineText, 2, 6, 24, 20)

        gridla.addWidget(folderButton, 1, 26, 1, 4)
        gridla.addWidget(openButton, 2, 26, 1, 4)
        gridla.addWidget(saveButton, 3, 26, 1, 4)
        gridla.addWidget(saveAsButton, 4, 26, 1, 4)
        gridla.addWidget(closeButton, 5, 26, 1, 4)
        gridla.addWidget(exitButton, 6, 26, 1, 4)

        # Añadimos acciones a los botones
        exitButton.clicked.connect(QApplication.instance().quit)
        folderButton.clicked.connect(self.openFolder)
        closeButton.clicked.connect(self.closeFolder)
        openButton.clicked.connect(self.openFile)
        self.listFile.itemDoubleClicked.connect(self.openFile)
        saveButton.clicked.connect(self.saveFile)
        saveAsButton.clicked.connect(self.saveAsFile)

        self.setGeometry(300, 300, 600, 600)
        self.setWindowTitle('Editor de texto')
        self.show()

    def openFolder(self):
        folder = QFileDialog.getExistingDirectory(self, "Seleccionar Carpeta")

        if folder:
            self.lineFolder.clear()
            self.lineFolder.setText(folder)
            self.listFile.clear()
            # Filtrar solo archivos
            files = [f for f in os.listdir(folder) if os.path.isfile(os.path.join(folder, f))]  
            self.listFile.addItems(files)

    def closeFolder(self):
        self.lineFolder.clear()
        self.listFile.clear()
        self.lineText.clear()
        self.opened_file = ""

    def openFile(self):
        self.opened_file = self.listFile.currentItem()
        if self.opened_file:
            with open(os.path.join(self.lineFolder.text(), self.opened_file.text()), "r", encoding="utf-8") as file:
                content = file.read()
                self.lineText.setText(content)

    def saveFile(self):
        if self.opened_file:
            with open(os.path.join(self.lineFolder.text(), self.opened_file.text()), "w", encoding="utf-8") as file:
                    file.write(self.lineText.toPlainText())

    def saveAsFile(self):
        if self.lineText.toPlainText():
            options = QFileDialog.Options()
            # Ponemos como segunda variable _ para ignorar lo que devuelve
            file_name, _ = QFileDialog.getSaveFileName(self, "Guardar Como", "", "Archivos de texto (*.txt);;Todos los archivos (*)", options=options)
            if file_name:
                with open(file_name, "w", encoding="utf-8") as file:
                    file.write(self.lineText.toPlainText())



def main():
    app = QApplication(sys.argv)
    ex = Editor_texto()
    sys.exit(app.exec_())


if __name__ == '__main__':
    main()
