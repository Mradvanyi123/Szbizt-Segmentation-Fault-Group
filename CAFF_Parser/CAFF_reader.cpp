//
// Created by linuxxxer on 6. 11. 2021.
//

#include <cstdio>
#include "CAFF_reader.h"
#include "BmpParser.h"


/// The constructor of the CAFF_reader class. It opens the given CAFF file if possible.
/// \param fileName - the name of the CAFF file
/// \param logFile - optional: name of the logfile
CAFF_reader::CAFF_reader(Logger &logFile, std::string &inputFile, std::string &outputFile) {
    this->inputFile = inputFile;
    this->outputFile = outputFile;
    this->caffFile = fopen(this->inputFile.c_str(), "rb");
    if (this->caffFile == nullptr) {
        throw std::invalid_argument("File path wrong or file does not exists!");
    }
    this->logger = Logger(logFile);
}

/// The destructor of the CAFF_reader class. It closes the opened CAFF file.
CAFF_reader::~CAFF_reader() {
    std::cout << "Closing the CAFF file" << std::endl;
//    if (this->caffFile != nullptr)
//        fclose(this->caffFile);
}

/// parse is the main function of the CAFF_reader class
/// It parser the input file
void CAFF_reader::parse() {
    int blockId;
//    skip to the end, read the size, then jump back to beginning
    caff_fseek(this->caffFile, 0L, SEEK_END);
    this->caffFileSize = ftell(this->caffFile);
    caff_fseek(this->caffFile, 0L, SEEK_SET);
//    and here we have the size of the file
    std::cout << "Size of the file: " << this->caffFileSize << std::endl;
    while ( (blockId = readCaffBlock()) != 3 ) {
        switch (blockId) {
            case CAFF_HEADER_BLOCK:
                readHeader();
                break;
            case CAFF_CREDITS_BLOCK:
                readCredits();
                break;
            default:

                break;
        }
    }
    readData();
}

/// readCaffBlock reads the ID of the CAFF block, then returns it
/// Note: it actually reads only one byte from the file. If the position
/// indicator is not before a CAFF block ID byte, it won't tell the difference
/// \return - the ID of the CAFF block
int CAFF_reader::readCaffBlock() {
    char buff;
    size_t readBytes = caff_fread(&buff, 1, 1, this->caffFile);
    if (readBytes <= 0 & (int)buff != 1 & (int)buff != 2 & (int)buff != 3) {
        return -1;
    }
    return (int)buff;
}

/// readHeader can read the header block of a CAFF file
/// TODO the header now is just skipped, all the information is lost
void CAFF_reader::readHeader() {
    int64_t length;
    caff_fread(&length, sizeof(int64_t), 1, this->caffFile);
    std::cout << "h len: " << length << std::endl;
//    skip the header for now
    caff_fseek(this->caffFile, length, SEEK_CUR);
}

/// readCredits can read the credits block of a CAFF file
/// TODO the credits now is just skipped, all the information is lost
void CAFF_reader::readCredits() {
    int64_t length;
    caff_fread(&length, sizeof(int64_t), 1, this->caffFile);
    std::cout << "c len: " << length << std::endl;
//    skip the credits for now
    caff_fseek(this->caffFile, length, SEEK_CUR);
}

/// readData can read the data block of a CAFF file and parse it
void CAFF_reader::readData() {
    int64_t length;
    caff_fread(&length, sizeof(int64_t), 1, this->caffFile);
    std::cout << "Data length: " << length << std::endl;
//    skip the duration part
    caff_fseek(this->caffFile, CIFF_DURATION_FIELD_LEN, SEEK_CUR);
    caff_fseek(this->caffFile, CIFF_HEADER_MAGIC_LEN, SEEK_CUR);
    int64_t ciffHeadLen, contentSize;
    caff_fread(&ciffHeadLen, sizeof(int64_t), 1, this->caffFile);
    caff_fread(&contentSize, sizeof(int64_t), 1, this->caffFile);
    caff_fread(&this->width, sizeof(int64_t), 1, this->caffFile);
    caff_fread(&this->height, sizeof(int64_t), 1, this->caffFile);
    std::cout << "Width x Height: " << this->width << "x" << this->height << std::endl;
    if (this->width <= 0 || this->height <= 0) {
        this->logger.logger("[readData] Invalid height or width value in the file", true);
        throw std::invalid_argument("Invalid height or width value in the file");
    }
    caff_fseek(this->caffFile, ciffHeadLen - CIFF_HEADER_CAPTION_OFFSET, SEEK_CUR);
}

/// processToBmp sets up a BmpParser class and sets up the header then writes out the
/// data to the destination file (given as the second program argument)
void CAFF_reader::processToBmp() {
    BmpParser bmp = BmpParser(this->logger);
    FILE* bmpFile = fopen(this->outputFile.c_str(), "wb");
    bmp.prepareBmpHeader((int32)this->width, (int32)this->height, bmpFile);
    bmp.prepareDmpData(this->caffFile, bmpFile, (int32)this->width, (int32)this->height, (int32)this->caffFileSize);

}

/// caff_fread implements the fread function. When reading the CAFF files this function should
/// be used, as this one makes some additional security checks during the reading
/// \param ptr - the destination where the read data will be written
/// \param size - size of the data we want to read
/// \param count - number of bytes to read
/// \param source - the source file
/// \return number of successfully read bytes
int CAFF_reader::caff_fread(void *ptr, size_t size, size_t count, FILE *source) {
    size_t bytesRead;
    long int currentSeek = ftell(source);
    if (this->caffFileSize < (currentSeek + count)) {
        this->logger.logger("[caff_fread] Unable to read, potential end of file", true);
        throw std::out_of_range("Unable to read - End Of File");
    }
    bytesRead = fread(ptr, size, count, source);
    if (bytesRead != count) {
        this->logger.logger("[caff_fread] File reading error", true);
        throw std::runtime_error("File reading error");
    }
    return (int)bytesRead;
}

/// caff_fwrite implements the fwrite function. It adds some extra security measures when
/// writing to a file.
/// \param ptr - the source from where we take the data
/// \param size - size of the data
/// \param count - number of bytes to be written
/// \param dest - the destination file
/// \return the number of successfully written bytes
[[maybe_unused]] int CAFF_reader::caff_fwrite(void* ptr, size_t size, size_t count, FILE* dest) {
    size_t bytesWritten;
    if (count <= 0) {
        this->logger.logger("[caff_fwrite] Invalid number of bytes to be written", true);
        throw std::invalid_argument("Invalid number of bytes to be written");
    }
    bytesWritten = fwrite(ptr, size, count, dest);
    if (bytesWritten != count) {
        this->logger.logger("[caff_fwrite] File writing error!", true);
        throw std::runtime_error("File writing error!");
    }
    return (int)bytesWritten;
}

/// caff_fseek implements the fseek function. It adds some extra security measures when
/// trying to seek to a certain position in the file.
/// \param file - the file we are trying to move the position
/// \param offset - number of bytes we want to move
/// \param seek - the relative position from where we want to move (SEEK_CUR, SEEK_SET, SEEK_END)
/// \return upon succession 0, in case of any error -1
int CAFF_reader::caff_fseek(FILE *file, long int offset, int seek) {
    long int currentPosition = ftell(file);
    if (offset < 0) {
        this->logger.logger("[caff_fseek] Seek error: cannot operate with negative offset values!", true);
        throw std::invalid_argument("Seek error: cannot operate with negative offset values!");
    }
    switch (seek) {
        case SEEK_CUR:
            if (offset > this->caffFileSize - currentPosition) {
                this->logger.logger("[caff_fseek] Seek error: moving the pointer over the end of the file!", true);
                throw std::out_of_range("Seek error: moving the pointer over the end of the file!");
            }
            break;
        case SEEK_SET:
            if (offset > this->caffFileSize) {
                this->logger.logger("[caff_fseek] Seek error: given offset bigger than the file size!", true);
                throw std::out_of_range("Seek error: given offset bigger than the file size!");
            }
            break;
        case SEEK_END:
            if (offset != 0) {
                this->logger.logger("[caff_fseek] Seek error: offset should be 0 when moving to the end of the file!", true);
                throw std::out_of_range("Seek error: offset should be 0 when moving to the end of the file!");
            }
            break;
        default:
            this->logger.logger("[caff_fseek] Invalid seeking!", true);
            throw std::runtime_error("Invalid seeking!");
    }
    return fseek(file, offset, seek);
}