//
// Created by linuxxxer on 6. 11. 2021.
//

#ifndef CAFF_PARSER_LOGGER_H
#define CAFF_PARSER_LOGGER_H

#include <iostream>
#include <fstream>

class Logger {

public:

    Logger() =default;

    explicit Logger(const char* logFileName);

    void logger(const char* msg, bool err = false);

    virtual ~Logger();

private:
//    Variables
    FILE* logfile = nullptr;

//    Functions
    static std::string timestamp();
    void logErr(const char* msg);
    void logStand(const char* msg);

};


#endif //CAFF_PARSER_LOGGER_H
