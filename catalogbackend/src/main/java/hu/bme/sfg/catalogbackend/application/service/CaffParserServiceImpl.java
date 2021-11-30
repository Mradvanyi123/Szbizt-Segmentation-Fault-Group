package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.util.PictureFile;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${catalog.caff.parser.exe.path}")
    private String parserExePath;

    @Value("${catalog.caff.file.input.path}")
    private String parserInputFilePath;

    @Value("${catalog.caff.file.output.path}")
    private String parserOutputFilePath;

    @Override
    public PictureFile convertCaff(byte[] picture) throws ParseException {
        try {
            PictureFile pictureFile = parsePicture(picture);
            log.info("CAFF is parsed");
            log.info("*** Parsing is ended *** ");
            return pictureFile;
        } catch (ParseException e) {
            log.error("There was  an error during parsing" + e.getMessage());
            throw e;
        }
    }

    private PictureFile parsePicture(byte[] data) throws ParseException {
        try {
            String parserURI = parserExePath;
//            Files.write(Paths.get("temp.caff"), data);
            Files.write(Paths.get(parserInputFilePath), data);
            String[] command = {parserURI, parserInputFilePath, parserOutputFilePath};
            log.info("*** Parsing is started *** ");
            return execute(command);
        } catch (IOException | InterruptedException e) {
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
        int exitValue = process.exitValue();
        if (exitValue == 0) {
            try (Scanner input = new Scanner(process.getInputStream())) {
                String[] result = input.nextLine().split(";");
                PictureFile pre = new PictureFile();
                //pre.setName(result[0]);
                pre.setName("File_" + UUID.randomUUID());
                pre.setData(Files.readAllBytes(Paths.get(parserOutputFilePath)));
                return pre;
            }
        } else {
            throw new ParseException(errorMessage.toString(), 0);
        }
    }
}
