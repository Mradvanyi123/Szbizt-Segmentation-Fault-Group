//
// Created by linuxxxer on 9. 11. 2021.
//

#ifndef CAFF_PARSER_BMPPARSER_H
#define CAFF_PARSER_BMPPARSER_H

#include <iostream>
#include <cstdio>
#include "definitions.h"
#include "Logger.h"

class BmpParser {
private:
//    Private variables
    Logger logger;

//    Private structures
    struct BMPHeader {
        char BMPSignature[2] = {'B', 'M'};
        uint32 sizeOfBitmap = -1;
        uint32 reserved = 0x000;
        uint32 pixelDataOffset = 54;
    } bmpHeader;

    struct BmpInfoHeader {
        uint32 sizeOfThisHeader = 40;
        int32 width = -1; // in pixels
        int32 height = -1; // in pixels
        int16 numberOfColorPlanes = 1; // must be 1
        int16 colorDepth = 8 * BYTES_IN_PIXEL;
        uint32 compressionMethod = NO_COMPRESSION;
        uint32 rawBitmapDataSize = 0; // generally ignored
        int32 horizontalResolution = PIXEL_RESOLUTION_BASE; // in pixel per meter
        int32 verticalResolution = PIXEL_RESOLUTION_BASE; // in pixel per meter
        uint32 colorTableEntries = MAX_NUMBER_OF_COLORS;
        uint32 importantColors = IMPORTANT_COLORS_ALL;
    } bmpInfoHeader;

    struct Pixel {
        int8 blue = -1;
        int8 green = -1;
        int8 red = -1;
    } pixel;

//    Private functions
    void writeFileHeader(FILE *bmp);
    void applyPadding(FILE *bmpFile, int numberOfPadding);
    static int calculatePadding(int32 width);
    [[maybe_unused]] int bmp_fwrite(void *ptr, size_t size, size_t count, FILE *dest);
    [[maybe_unused]] int bmp_fread(void *ptr, size_t size, size_t count, FILE *source, int fileSize);

    [[maybe_unused]] [[maybe_unused]] int bmp_fseek(FILE *file, long int offset, int seek, int fileSize);
public:
//    Constructor
    BmpParser() =default;
    explicit BmpParser(std::string& logFile);
//    Public functions
    void prepareBmpHeader(int32 width, int32 height, FILE *bmpFile);
    void prepareDmpData(FILE *caffFile, FILE *bmpFile, int32 width, int32 height, int fileSize);
};


#endif //CAFF_PARSER_BMPPARSER_H
