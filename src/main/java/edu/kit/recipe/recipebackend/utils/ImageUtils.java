package edu.kit.recipe.recipebackend.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Utility class for image compression
 * @author Johannes Stephan
 */
public class ImageUtils {

    private ImageUtils() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * Compresses the image
     * @param data the image
     * @return the compressed image
     */
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
            return new byte[0];
        }
        return outputStream.toByteArray();
    }


    /**
     * Decompresses the image
     * @param data the image
     * @return the decompressed image
     */
    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
            return new byte[0];
        }
        return outputStream.toByteArray();
    }

}
