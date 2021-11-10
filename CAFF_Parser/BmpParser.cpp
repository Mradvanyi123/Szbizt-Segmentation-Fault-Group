//
// Created by linuxxxer on 9. 11. 2021.
//

#include <fstream>
#include <cstdio>
#include "BmpParser.h"

BmpParser::BmpParser(std::string &logFile) {
    if (!logFile.empty())
        this->logger = Logger(logFile.c_str());
}

/// prepareBmpHeader sets up the structure of the header then writes it out to the destination file
/// \param width - width of the image
/// \param height - height of the image
/// \param bmpFile - destination file
void BmpParser::prepareBmpHeader(int32 width, int32 height, FILE *bmpFile) {
    this->bmpHeader.sizeOfBitmap = width * height * BYTES_IN_PIXEL;
    this->bmpInfoHeader.width = width;
    this->bmpInfoHeader.height = -height;

    writeFileHeader(bmpFile);
}

/// writeFileHeader writes out the whole header currently stored in the class
///     to the destination file
/// \param bmp - the destination file
void BmpParser::writeFileHeader(FILE *bmp) {
//    Write the file header -- 14 bytes
    bmp_fwrite(&this->bmpHeader.BMPSignature[0], sizeof(char), 1, bmp);
    bmp_fwrite(&this->bmpHeader.BMPSignature[1], sizeof(char), 1, bmp);
    bmp_fwrite(&this->bmpHeader.sizeOfBitmap, sizeof(uint32), 1, bmp);
    bmp_fwrite(&this->bmpHeader.reserved, sizeof(uint32), 1, bmp);
    bmp_fwrite(&this->bmpHeader.pixelDataOffset, sizeof(uint32), 1, bmp);
//    Write the info header -- 40 bytes
    bmp_fwrite(&this->bmpInfoHeader.sizeOfThisHeader, sizeof(uint32), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.width, sizeof(int32), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.height, sizeof(int32), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.numberOfColorPlanes, sizeof(int16), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.colorDepth, sizeof(int16), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.compressionMethod, sizeof(uint32), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.rawBitmapDataSize, sizeof(uint32), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.horizontalResolution, sizeof(int32), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.verticalResolution, sizeof(int32), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.colorTableEntries, sizeof(uint32), 1, bmp);
    bmp_fwrite(&this->bmpInfoHeader.importantColors, sizeof(uint32), 1, bmp);
//    header total size is 54 bytes
    this->logger.logger("[BmpParser] Headers written successfully");
    std::cout << "[BmpParser] Headers written successfully" << std::endl;
}

/// applyPadding appends the padding to the end of the destination file
/// \param bmpFile - the destination file
/// \param numberOfPadding - the number of zero bytes to be written
void BmpParser::applyPadding(FILE* bmpFile, int numberOfPadding) {
    int padder = 0;
    bmp_fwrite(&padder, 1, numberOfPadding, bmpFile);
}

/// calculatePadding calculates how many bytes are needed for padding
/// \param width - actual width of the image
/// \return - calculated number of needed padding
int BmpParser::calculatePadding(int32 width) {
    return (width * BYTES_IN_PIXEL) % 4;
}

/// prepareDmpData reads the pixel data from a CAFF file then writes it out to the destination
/// (in the switched BGR format instead of RGB)
/// \param caffFile - the source file to be read from
/// \param bmpFile - the destination file
/// \param width - the pictures width
/// \param height - the pictures height
void BmpParser::prepareDmpData(FILE *caffFile, FILE *bmpFile, int32 width, int32 height, int fileSize) {
    int numberOfPadding = calculatePadding(width);
    for (int row = 0; row < height; row++) {
        for (int pix = 0; pix < width; pix++) {
            bmp_fread(&this->pixel.red, sizeof(int8), 1, caffFile, fileSize);
            bmp_fread(&this->pixel.green, sizeof(int8), 1, caffFile, fileSize);
            bmp_fread(&this->pixel.blue, sizeof(int8), 1, caffFile, fileSize);

            bmp_fwrite(&this->pixel.blue, sizeof(int8), 1, bmpFile);
            bmp_fwrite(&this->pixel.green, sizeof(int8), 1, bmpFile);
            bmp_fwrite(&this->pixel.red, sizeof(int8), 1, bmpFile);
        }
        applyPadding(bmpFile, numberOfPadding);
    }
    this->logger.logger("[BmpParser] Data written successfully");
    std::cout << "[BmpParser] Data written successfully" << std::endl;
}

/// bmp_fwrite implements the fwrite function. It adds some extra security measures when
/// writing to a file.
/// \param ptr - the source from where we take the data
/// \param size - size of the data
/// \param count - number of bytes to be written
/// \param dest - the destination file
/// \return the number of successfully written bytes
[[maybe_unused]] int BmpParser::bmp_fwrite(void* ptr, size_t size, size_t count, FILE* dest) {
    size_t bytesWritten;
    if (count <= 0) {
        this->logger.logger("[bmp_fwrite] Invalid number of bytes to be written", true);
        throw std::invalid_argument("Invalid number of bytes to be written");
    }
    bytesWritten = fwrite(ptr, size, count, dest);
    if (bytesWritten != count) {
        this->logger.logger("[bmp_fwrite] File writing error!", true);
        throw std::runtime_error("File writing error!");
    }
    return (int)bytesWritten;
}

/// bmp_fread implements the fread function. When reading the BMP files this function should
/// be used, as this one makes some additional security checks during the reading
/// \param ptr - the destination where the read data will be written
/// \param size - size of the data we want to read
/// \param count - number of bytes to read
/// \param source - the source file
/// \param fileSize - the size of the file
/// \return number of successfully read bytes
[[maybe_unused]] int BmpParser::bmp_fread(void *ptr, size_t size, size_t count, FILE *source, int fileSize) {
    size_t bytesRead, currentSeek = ftell(source);
    if (fileSize < (currentSeek + count)) {
        this->logger.logger("[bmp_fread] Unable to read, potential end of file", true);
        throw std::out_of_range("Unable to read - End Of File");
    }
    bytesRead = fread(ptr, size, count, source);
    if (bytesRead != count) {
        this->logger.logger("[bmp_fread] File reading error", true);
        throw std::runtime_error("File reading error.");
    }
    return (int)bytesRead;
}

/// bmp_fseek implements the fseek function. It adds some extra security measures when
/// trying to seek to a certain position in the file
/// \param file - the file we are trying to move the position
/// \param offset - number of bytes we want to move
/// \param seek = the relative position from where we want to move (SEEK_CUR, SEEK_SET, SEEK_END)
/// \param fileSize - the size of the file we passed as argument
/// \return upon successful seeking 0, otherwise -1
[[maybe_unused]] int BmpParser::bmp_fseek(FILE *file, long offset, int seek, int fileSize) {
    long int currentPosition = ftell(file);
    if (offset < 0) {
        this->logger.logger("[bmp_fseek] Seek error: cannot operate with negative offset values!", true);
        throw std::invalid_argument("Seek error: cannot operate with negative offset values!");
    }
    switch (seek) {
        case SEEK_CUR:
            if (offset > fileSize - currentPosition) {
                this->logger.logger("[bmp_fseek] Seek error: moving the pointer over the end of the file!", true);
                throw std::out_of_range("Seek error: moving the pointer over the end of the file!");
            }
            break;
        case SEEK_SET:
            if (offset > fileSize) {
                this->logger.logger("[bmp_fseek] Seek error: given offset bigger than the file size!", true);
                throw std::out_of_range("Seek error: given offset bigger than the file size!");
            }
            break;
        case SEEK_END:
            if (offset != 0) {
                this->logger.logger("[bmp] Seek error: offset should be 0 when moving to the end of the file!", true);
                throw std::out_of_range("Seek error: offset should be 0 when moving to the end of the file!");
            }
            break;
        default:
            this->logger.logger("[bmp_fseek] Invalid seeking!", true);
            throw std::runtime_error("Invalid seeking!");
    }
    return fseek(file, offset, seek);
}
