package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.util.PictureFile;

import java.text.ParseException;

public interface CaffParserService {

    /**
     * Creates the command to invoke the CAFF_Parser.exe,
     * and gives back a PictureFile, which is just a wrapper for the parsed file,
     * and later we pass its converted value, to a Picture entity.
     *
     * @param pictureBytes byte array
     * @return PictureFile
     * @throws ParseException Exception
     */
    PictureFile convertCaff(byte[] pictureBytes) throws ParseException;
}
