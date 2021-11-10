#include <iostream>
#include "Logger.h"
#include "CAFF_reader.h"

int main(int argc, char* argv[]) {
    std::string logFile = "logfile.txt", input, output;
    CAFF_reader caff;
    Logger logger = Logger(logFile.c_str());
    logger.logger("[main] Program started");
    if (argc == 3) {
        input = argv[1];
        output = argv[2];
        try {
            caff = CAFF_reader(logFile, input, output);
            caff.parse();
            caff.processToBmp();
        } catch (...) {
            std::cerr << "Received an error!" << std::endl;
        }
    } else {
        logger.logger("[main] Invalid number of program arguments!", true);
        throw std::invalid_argument("This program takes exactly 3 arguments! CAFF file, BMP file, [optional: LogFile]");
    }
    logger.logger("[main] Program ended");
    return 0;
}
