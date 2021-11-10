cmake_minimum_required(VERSION 3.20)
project(CAFF_Parser)

set(CMAKE_CXX_STANDARD 17)

add_executable(caff_parser main.cpp Logger.cpp Logger.h CAFF_reader.cpp CAFF_reader.h definitions.h BmpParser.cpp BmpParser.h)
