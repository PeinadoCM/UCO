/**
 * @file alphabet_soup_solver.cpp
 *
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */

#include "alphabet_soup_solver.hpp"
#include "trie.hpp"

/**
 * @brief scan a cell looking for the next letter of a word.
 * @param row and
 * @param col are the current cell coordinates.
 * @param dy and
 * @param dx are direction vector to follow the next letter.
 * @param soup is the alphabet soup where looking for.
 * @param trie is the current subtrie.
 * @param scan_result save the current chain. Is a pair of <word, cells_coordinates [row,col]>
 *
 */
void scan_cell(int row, int col, int dy, int dx, AlphabetSoup const &soup, Trie &trie, ScanResult &scan_result)
{
    // TODO
    // ALGORITHM
    // 1. If the trie's prefix is a key then
    //    a word is found. Save the key as the first item of the scan_result
    //    pair and return from this level of recursion.
    if(trie.is_key()){
        scan_result.first=trie.prefix();
    }
    // 2. Else if there is a sub-trie for the symbol (row,col), there is two cases:
    else if(trie.find_symbol(soup.cell(row,col))){
        auto child=trie.current();
    // 2.1.1 (dx==dy==0) The cell (row,col) is the first letter of a word.
    //       If there is a sub-trie whose prefix is this symbol, start a new
    //       recursive scanning of the 3x3 neighborhood from this position
    //       using this sub-trie.
        if(dx==0 && dy==0){
            for(int i=-1; 2>i; i++){
                for(int j=-1; 2>j; j++){
                    if(i!=0 || j!=0){
                        int row_aux=row+i;
                        int col_aux=col+j;
                        if(row_aux >= 0 && col_aux >= 0 && row_aux < soup.rows() && col_aux < soup.cols()){
                            scan_cell(row_aux, col_aux, i, j, soup, child, scan_result);
                        }
                    }
                }
            }
        }
    // 2.1.2 (dx!=0 or dy!=0) The cell (row,col) is the next letter of a
    //       started scanning following the direction (dx,dy). If there is a
    //       sub-trie whose prefix is this symbol, continue the scanning
    //       following the direction (dx,dy) recursively.
        else{
            int row_aux=row+dy;
            int col_aux=col+dx;
            if(row_aux >= 0 && col_aux >= 0 && row_aux < soup.rows() && col_aux < soup.cols()){
                scan_cell(row_aux, col_aux, dy, dx, soup, child, scan_result);
            }
            //Se comprueba por si hay algun final de palabra en la ultima columna para detectar esa palabra, sino no se pone no encuentra la palabra ecuador
            else{
                scan_cell(row, col, dy, dx, soup, child, scan_result);
            }
        }
    // 2.2 After recursion comes back, if a word was found (first item of
    //     the scan_result pair != ""), push the current cell's coordinates
    //     (row,col) into the second item of scan_result (the stack of
    //     coordinates).
        if(scan_result.first != ""){
            //Se comprueba para que no introduzca alguna posicion erronea, si no se pone da error en varias palabras
            if(soup.cell(row,col)==scan_result.first[(scan_result.first).size()-(scan_result.second).size()-1]){
                (scan_result.second).push(std::make_pair(row,col));
            }
        }
    }
    
    //
}

std::vector<ScanResult>
alphabet_soup_solver(AlphabetSoup &soup, std::vector<std::string> const &words)
{
    std::vector<ScanResult> results;
    Trie trie;
    // Generate a trie with the words to be found as keys.
    for (size_t i = 0; i < words.size(); ++i)
        trie.insert(words[i]);

    // We scan all the soup to find a first letter of any key.
    for (int row = 0; row < soup.rows(); ++row)
    {
        for (int col = 0; col < soup.cols(); ++col)
        {
            auto scan_result = std::make_pair(std::string(""),
                                              std::stack<std::pair<int, int>>());
            // Scan from this cell. This is the first letter so dx==dy==0.
            scan_cell(row, col, 0, 0, soup, trie, scan_result);
            if (scan_result.first != "")
                // A word was found so save it into the results.
                results.push_back(scan_result);
        }
    }
    return results;
}
