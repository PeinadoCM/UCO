//============================================================================
// Name        : main.cpp
// Author      : Manuel Peinado Cuenca
// Version     :
// Date        :
//============================================================================

/////////////////////////////////////////////////////
//EDITA ÚNICAMENTE LAS LÍNEAS ASOCIADAS A TAREAS POR HACER (marcadas entre un bloque TODO)
/////////////////////////////////////////////////////


#include "State.h"
#include <iostream>
#include <cstdlib>
#include <vector>
#include <queue> //Fíjate en que se está utilizando la clase stack para implementar frontera como una pila
using namespace std;

class QueensState: private State {

public:
	/**
	 * This function allows the user to set the number of queens of the problem.
	 * It should be passed as a pointer to an int variable with the desired value.
	 */
	static void setParameters(void *parameters);

	/**
	 * This function returns the initial state for the queens problem. An empty board
	 */
	static State* getInitialState();

	/**
	 * Destructor
	 */
	virtual ~QueensState();
};

int main() {

	int numQueens;
	cout << "¿Con cuántas reinas deseas que se resuelva el problema?" << endl;
	cin >> numQueens;

	QueensState::setParameters(&numQueens);

	State *initialState = QueensState::getInitialState();

    /////////////////////////////////////////
	// TODO programar aquí la búsqueda que se desee aplicar
    /////////////////////////////////////////
    std::queue<State*> frontier;
	std::vector<State*>* successors;
	bool foundSolution=false;
    frontier.push(initialState);
	State* currentstate;

    while(foundSolution==false && frontier.empty()==false){
		currentstate=frontier.front();
		frontier.pop();
		if(currentstate->isObjective()==true){
			currentstate->print();
			foundSolution=true;
		}
		else{
			successors=currentstate->getSuccessors();
			for(int i=0; successors->size()>i;i++){
				frontier.push((*successors)[i]);
			}
			delete(successors);
		}

    }
	
	
    //////////////////////////////////////////
    // Fin bloque TODO
    //////////////////////////////////////////
    
    if (frontier.empty())
		cout << "There is no solution for " << numQueens << " queens" << endl;
    
	return 0;
}
