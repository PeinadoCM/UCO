from PyQt5.QtWidgets import (QWidget, QPushButton, QLabel, QLineEdit,QVBoxLayout)
import ckControladorCalculadora as ctrl

class Calculadora(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Calculadora MVC')
        self.setGeometry(100, 100, 300, 200)
        
        self.layout = QVBoxLayout()
        
        self.num1_input = QLineEdit(self)
        self.num1_input.setPlaceholderText('Ingrese el primer número')
        self.layout.addWidget(self.num1_input)
        
        self.operador_input = QLineEdit(self)
        self.operador_input.setPlaceholderText('Ingrese el operador (+, -, *, /)')
        self.layout.addWidget(self.operador_input)
        
        self.num2_input = QLineEdit(self)
        self.num2_input.setPlaceholderText('Ingrese el segundo número')
        self.layout.addWidget(self.num2_input)
        
        self.boton_calcular = QPushButton('Calcular', self)
        self.boton_calcular.clicked.connect(self.calcular)
        self.layout.addWidget(self.boton_calcular)
        
        self.resultado_label = QLabel('Resultado: ', self)
        self.layout.addWidget(self.resultado_label)
        
        self.setLayout(self.layout)
        self.show()
    
    def mostrar_resultado(self, resultado):
        self.resultado_label.setText(f'Resultado: {resultado}')

    def calcular(self):
        num1 = self.num1_input.text()
        num2 = self.num2_input.text()
        operador = self.operador_input.text()
        resultado = ctrl.calcular(num1, num2, operador)
        self.mostrar_resultado(resultado)




 