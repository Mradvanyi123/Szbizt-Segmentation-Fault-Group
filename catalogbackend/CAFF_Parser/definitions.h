//
// Created by linuxxxer on 6. 11. 2021.
//

#ifndef CAFF_PARSER_DEFINITIONS_H
#define CAFF_PARSER_DEFINITIONS_H

/// Definitions for general macros. Some are unused but will be left here for the time being
/// as the code may be extended one day...

#define CAFF_HEADER_BLOCK 0x1
#define CAFF_CREDITS_BLOCK 0x2
#define CAFF_ANIMATION_BLOCK 0x3

#define CAFF_BLOCK_LENGTH_OFFSET 1
#define CAFF_BLOCK_LENGTH_LENGTH 8
#define CAFF_BLOCK_DATA_OFFSET 9
#define CAFF_HEADER_SIZE_OFFSET 4
#define CAFF_HEADER_ANIMATION_NUMBER_OFFSET 12

#define CIFF_DURATION_FIELD_LEN 8
#define CIFF_HEADER_MAGIC_LEN 4
#define CIFF_HEADER_SIZE_LENGTH 8
#define CIFF_HEADER_CONTENT_SIZE_LENGTH 8
#define CIFF_HEADER_CAPTION_OFFSET 36
#define BYTES_IN_PIXEL 3

#define NO_COMPRESSION 0
#define PIXEL_RESOLUTION_BASE 11811
#define MAX_NUMBER_OF_COLORS 0
#define IMPORTANT_COLORS_ALL 0

typedef uint32_t uint32;
typedef int32_t int32;
typedef uint8_t int8;
typedef uint16_t int16;

#endif //CAFF_PARSER_DEFINITIONS_H
