//
// Created by linuxxxer on 6. 11. 2021.
//

#include "Logger.h"
#include <iomanip>

// =============================================
// Constructor and destructor block
/// Logger class implements a simple logging interface for the program.
/// The Constructor opens the log file for appending (so the older logs are not overwritten).
/// One log row always has a timestamp
/// \param logFileName - the name of the log file
Logger::Logger(const char *logFileName) {
    this->logfile = fopen(logFileName, "a");
}
// =============================================

// =============================================
// Public functions
/// logger writes out the given message to the logfile. The message should be constructed
/// so then it is possible to identify the source.
/// \param msg - the message string
void Logger::logger(const char* msg, bool err) {
    if (err) {
        logErr(msg);
    } else {
        logStand(msg);
    }
}

// =============================================

// =============================================
// Private functions
/// timestamp returns the time at the moment of calling
/// \return - the current time (format: [dayName Month day time year timeZone])
std::string Logger::timestamp() {
    std::string timestamp = "[";

    time_t now = time(nullptr);
    std::stringstream timeNow;
    timeNow << std::put_time( localtime(&now), "%c %Z");
    timestamp += timeNow.str();

    timestamp += "]";

    return timestamp;
}

void Logger::logErr(const char *msg) {
    if (logfile != nullptr) {
        std::string time = timestamp();
        fprintf(logfile, "%s [ERROR] %s\n", time.c_str(), msg);
    }
}

void Logger::logStand(const char *msg) {
    if (logfile != nullptr) {
        std::string time = timestamp();
        fprintf(logfile, "%s [INFO] %s\n", time.c_str(), msg);
    }
}
// =============================================