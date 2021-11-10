//
// Created by linuxxxer on 6. 11. 2021.
//

#ifndef CAFF_PARSER_CAFF_READER_H
#define CAFF_PARSER_CAFF_READER_H

#include <fstream>
#include <iostream>
#include <vector>
#include <cstdio>
#include "definitions.h"
#include "Logger.h"

class CAFF_reader {
private:
    FILE* caffFile = nullptr;
    long int caffFileSize = -1;
    Logger logger;
    int64_t width = -1, height = -1;
    std::string outputFile;
    std::string inputFile;
    std::string logFile;

    int readCaffBlock();
    void readHeader();
    void readCredits();
    void readData();

public:
    CAFF_reader() = default;;
    explicit CAFF_reader(std::string &logFile, std::string &inputFile, std::string &outputFile);
    ~CAFF_reader();

    void parse();

    void processToBmp();

    [[maybe_unused]] int caff_fread(void *ptr, size_t size, size_t count, FILE *source);

    [[maybe_unused]] int caff_fwrite(void *ptr, size_t size, size_t count, FILE *dest);

    [[maybe_unused]] int caff_fseek(FILE *file, long int offset, int seek);
};


#endif //CAFF_PARSER_CAFF_READER_H
