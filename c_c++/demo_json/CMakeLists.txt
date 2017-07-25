cmake_minimum_required(VERSION 3.6)

project(demo)

#add_compile_options(-std=c++11)
add_subdirectory(src bin)
set(CMAKE_INSTALL_PREFIX /usr/local)

install(DIRECTORY include/ DESTINATION ${CMAKE_INSTALL_PREFIX}/include)
