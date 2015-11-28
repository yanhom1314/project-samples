include_directories(../include /usr/include /usr/local/include)

set(TEST_SRC_LIST test.c lib.c hello.cc ../include/hello.h)

add_executable(test ${TEST_SRC_LIST})

link_directories(/usr/lib64/)

target_link_libraries(test pcap)

install(TARGETS test
	LIBRARY DESTINATION ${CMAKE_INSTALL_PREFIX}/lib
	RUNTIME DESTINATION ${CMAKE_INSTALL_PREFIX}/bin
	ARCHIVE DESTINATION ${CMAKE_INSTALL_PREFIX}/include)
