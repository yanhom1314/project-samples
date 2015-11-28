include_directories(../include /usr/include /usr/local/include)

set(DEMO_JSON_SRC_LIST cJSON.c test.c ../include/cJSON.h)

add_executable(test ${DEMO_JSON_SRC_LIST})

link_directories(/usr/lib64/)

#target_link_libraries(test pcap)

install(TARGETS test
	LIBRARY DESTINATION ${CMAKE_INSTALL_PREFIX}/lib
	RUNTIME DESTINATION ${CMAKE_INSTALL_PREFIX}/bin
	ARCHIVE DESTINATION ${CMAKE_INSTALL_PREFIX}/include)
