cmake_minimum_required(VERSION 3.6)

add_executable(demo_log log.c)
add_executable(demo_read read.c)
add_executable(demo_tok tok.c)

install(TARGETS demo_log demo_read demo_tok
	LIBRARY DESTINATION ${CMAKE_INSTALL_PREFIX}/lib
	RUNTIME DESTINATION ${CMAKE_INSTALL_PREFIX}/bin
	ARCHIVE DESTINATION ${CMAKE_INSTALL_PREFIX}/include)
