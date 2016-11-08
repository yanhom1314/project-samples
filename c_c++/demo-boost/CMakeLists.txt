cmake_minimum_required(VERSION 3.0)
project(demo_boost)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")
find_package(Boost REQUIRED filesystem system chrono thread)
if(NOT Boost_FOUND)
  message("未发现Boost")
endif()
INCLUDE_DIRECTORIES(${Boost_INCLUDE_DIR})

set(SOURCE_FILES main.cpp)
add_executable(demo_boost ${SOURCE_FILES})
target_link_libraries(demo_boost ${Boost_FILESYSTEM_LIBRARY} ${Boost_SYSTEM_LIBRARY})

