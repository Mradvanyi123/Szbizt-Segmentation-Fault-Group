package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.domain.PictureFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Scanner;
import java.util.UUID;


@Service
@Slf4j
public class CaffParserServiceImpl implements CaffParserService {

    @Override
    public PictureFile convertCaff(byte[] picture) throws ParseException {
        try {
            PictureFile pictureFile = parsePicture(picture);
            log.info("CAFF parsed");
            return pictureFile;
        } catch (ParseException e) {
            log.error("There was  an error during parsing" + e.getMessage());
            throw e;
        }
    }

    private PictureFile parsePicture(byte[] data) throws ParseException {
        try {
            String parserURI = "C:\\Users\\stell\\Documents\\GitHub\\Szbizt-Segmentation-Fault-Group\\catalogbackend\\CAFF_Parser\\CAFF_Parser.exe";
            Files.write(Paths.get("temp.caff"), data);
            String[] command = {parserURI, "C:\\Users\\stell\\Documents\\GitHub\\Szbizt-Segmentation-Fault-Group\\catalogbackend\\temp.caff", "C:\\Users\\stell\\Documents\\GitHub\\Szbizt-Segmentation-Fault-Group\\catalogbackend\\temp_output.bmp"};
            log.info("Start to parse file: ");
            return execute(command);
        } catch (IOException e) {
            log.error("Could not save the preview image");
        } catch (InterruptedException e) {
            log.error("There was an error during the execution of the parse process");
        }
        return null;
    }

    public PictureFile execute(String[] command) throws ParseException, InterruptedException, IOException {

        Process process = new ProcessBuilder(command).start();

        Scanner error = new Scanner(process.getErrorStream());
        StringBuilder errorMessage = new StringBuilder();
        while (error.hasNextLine()) {
            errorMessage.append(error.nextLine());
        }

        process.waitFor();
        int exitvalue = process.exitValue();
        if (exitvalue == 0) {
            try (Scanner input = new Scanner(process.getInputStream())) {
                String[] result = input.nextLine().split(";");
                PictureFile pre = new PictureFile();
                //pre.setName(result[0]);
                pre.setName("File_" + UUID.randomUUID());
                pre.setData(Files.readAllBytes(Paths.get("C:\\Users\\stell\\Documents\\GitHub\\Szbizt-Segmentation-Fault-Group\\catalogbackend\\temp_output.bmp")));
                return pre;
            }
        } else {
            throw new ParseException(errorMessage.toString(), 0);
        }
    }
}
