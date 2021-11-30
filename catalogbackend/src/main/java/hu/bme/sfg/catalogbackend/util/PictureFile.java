package hu.bme.sfg.catalogbackend.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PictureFile {

    private byte[] data;

    private String name;
}