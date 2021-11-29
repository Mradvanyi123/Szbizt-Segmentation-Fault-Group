package hu.bme.sfg.catalogbackend.application.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import hu.bme.sfg.catalogbackend.domain.Caff;
import hu.bme.sfg.catalogbackend.domain.CaffFile;
import hu.bme.sfg.catalogbackend.domain.Comment;
import hu.bme.sfg.catalogbackend.repository.CaffFileRepository;
import hu.bme.sfg.catalogbackend.repository.CaffRepository;
import hu.bme.sfg.catalogbackend.repository.CommentRepository;
import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CaffService {
    Logger logger = LoggerFactory.getLogger(CaffService.class);

    @Autowired
    private CaffRepository caffRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CaffFileRepository caffFileRepository;


    public List<Caff> getAllCAFF() {
        return caffRepository.findAll();
    }


    public void delete(Caff caff) {
        caffRepository.delete(caff);
    }

    public Caff uploadCaff(CaffFile caffFile) throws ParseException {
        try {
            Caff caff = parseCaff(caffFile);
            caffRepository.save(caff);
            caffFileRepository.save(caffFile);

            caff.setFile(caffFile);
            caffFile.setCaff(caff);

            caffRepository.save(caff);
            caffFileRepository.save(caffFile);
            logger.info("CAFF file uploaded");

            return caff;
        } catch (ParseException e) {
            logger.error("There was  an error during parsing" + e.getMessage());
            throw e;
        }
    }

    private Caff parseCaff(CaffFile caffFile) throws ParseException {
        try {
            String parserURI = "C:\\Users\\stell\\Documents\\GitHub\\Szbizt-Segmentation-Fault-Group\\catalogbackend\\CAFF_Parser\\CAFF_Parser.exe";
            Files.write(Paths.get("temp.caff"), caffFile.getData());
            String[] command = {parserURI, "C:\\Users\\stell\\Documents\\GitHub\\Szbizt-Segmentation-Fault-Group\\catalogbackend\\temp.caff", "C:\\Users\\stell\\Documents\\GitHub\\Szbizt-Segmentation-Fault-Group\\catalogbackend\\temp_output.bmp"};
            return execCmd(command);
        } catch (IOException e) {
            logger.error("Could not save the preview image");
        } catch (InterruptedException e) {
            logger.error("There was an error during the execution of the parse process");
        }
        return null;
    }

    public Caff execCmd(String[] command) throws ParseException, InterruptedException, IOException {

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
                Caff caff = new Caff();
                caff.setName(result[0]);
                caff.setTags("test");
                caff.setWidth(1024);
                caff.setHeight(800);
                caff.setPreviewFile(Files.readAllBytes(Paths.get("C:\\Users\\stell\\Documents\\GitHub\\Szbizt-Segmentation-Fault-Group\\catalogbackend\\temp_output.bmp")));
                return caff;
            }
        } else {
            throw new ParseException(errorMessage.toString(), 0);
        }
    }

    public Caff getCAFFById(long id) {
        return caffRepository.findById(id).get();
    }

    public Caff persistCAFF(Caff caff) {
        return caffRepository.save(caff);
    }

    public Comment addCommentToCaff(Caff caff, Comment comment) {
        //comment.setCaff(caff);
        caffRepository.save(caff);
        return commentRepository.save(comment);
    }

    public byte[] getPreviewOfCaff(long id) {
        return caffRepository.findById(id).get().getPreviewFile();
    }


    public byte[] getCaffFileById(String id) throws SecurityException {
        Caff caff = caffRepository.findById(Long.parseLong(id)).get();

        return caffRepository.findById(Long.parseLong(id)).get().getFile().getData();
    }
}
