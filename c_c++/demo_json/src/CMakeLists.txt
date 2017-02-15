include_directories(../include /usr/include /usr/local/include)
#set(DEMO_JSON_SRC_LIST cJSON.c test.c ../include/cJSON.h hello.cpp)
set(DEMO_JSON_SRC_LIST ../include/cJSON.h cJSON.c hello.cpp)
add_executable(demo_json ${DEMO_JSON_SRC_LIST})
link_directories(/usr/lib64/)

install(TARGETS demo_json
	LIBRARY DESTINATION ${CMAKE_INSTALL_PREFIX}/lib
	RUNTIME DESTINATION ${CMAKE_INSTALL_PREFIX}/bin
	ARCHIVE DESTINATION ${CMAKE_INSTALL_PREFIX}/include)
