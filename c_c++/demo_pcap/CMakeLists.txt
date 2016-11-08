cmake_minimum_required(VERSION 3.0)
project(demo)
SET(CMAKE_C_COMPILER g++)
add_compile_options(-std=c++11)
add_subdirectory(src bin)
set(CMAKE_INSTALL_PREFIX /usr/local)

install(DIRECTORY include/ DESTINATION ${CMAKE_INSTALL_PREFIX}/include)
