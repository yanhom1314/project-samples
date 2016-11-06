
cmake_minimum_required(VERSION 3.6)
add_executable(demo_pcre pcre.c)

install(TARGETS demo_pcre
	LIBRARY DESTINATION ${CMAKE_INSTALL_PREFIX}/lib
	RUNTIME DESTINATION ${CMAKE_INSTALL_PREFIX}/bin
	ARCHIVE DESTINATION ${CMAKE_INSTALL_PREFIX}/include)
