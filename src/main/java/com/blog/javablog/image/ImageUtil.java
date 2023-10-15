package com.blog.javablog.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {
  public static byte[] compressImage(byte[] data) throws IOException {
    Deflater deflater = new Deflater();
    deflater.setLevel(Deflater.BEST_COMPRESSION);
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[4 * 1024];

    while (!deflater.finished()) {
      int size = deflater.deflate(tmp);
      outputStream.write(tmp, 0, size);
    }

    outputStream.close();
    return outputStream.toByteArray();
  }

  public static byte[] decompressImage(byte[] data) throws DataFormatException, IOException {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[4 * 1024];

    while (!inflater.finished()) {
      int count = inflater.inflate(tmp);
      outputStream.write(tmp, 0, count);
    }
    outputStream.close();
    return outputStream.toByteArray();
  }

  public static File createImageFile(String base64Image) throws NoSuchElementException, IOException {
    if (base64Image == null) {
      throw new NoSuchElementException("No base64 image provided");
    }

    byte[] decodeBase64Image = Base64.getDecoder().decode(base64Image);

    File imageFile = File.createTempFile("image-", ".jpeg");

    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
      fos.write(decodeBase64Image);
    }

    return imageFile;
  }
}
