cmake_minimum_required(VERSION 3.6)

project(demo_c_cpp)

add_compile_options(-std=c++11)
add_subdirectory(io)
add_subdirectory(md5)
add_subdirectory(pcre)

#set(CMAKE_INSTALL_PREFIX /usr/local)
#install(DIRECTORY include/ DESTINATION ${CMAKE_INSTALL_PREFIX}/include)
