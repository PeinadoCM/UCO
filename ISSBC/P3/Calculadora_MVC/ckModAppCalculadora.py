def calcular(num1, num2, operador):
        try:
            num1, num2 = float(num1), float(num2)
            if operador == '+':
                return num1 + num2
            elif operador == '-':
                return num1 - num2
            elif operador == '*':
                return num1 * num2
            elif operador == '/':
                return num1 / num2 if num2 != 0 else 'Error: División por cero'
            else:
                return 'Error: Operador no válido'
        except ValueError:
            return 'Error: Entrada no válida'
