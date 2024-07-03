add_library(seller seller.cc seller.h)
target_link_libraries(seller PUBLIC basket)
target_link_libraries(seller PUBLIC person)
target_include_directories(seller PUBLIC ${CMAKE_CURRENT_LIST_DIR})

add_executable(seller-main seller-main.cc)
target_link_libraries(seller-main PUBLIC seller)
