package example;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

object GZIPCompression {

  @Throws(IOException::class)
  fun compress(str: String?): ByteArray? {
    if ((str == null) || (str.length == 0)) {
      return null;
    }
    val obj: ByteArrayOutputStream = ByteArrayOutputStream();
    val gzip: GZIPOutputStream = GZIPOutputStream(obj);
    gzip.write(str.toByteArray());
    gzip.flush();
    gzip.close();
    val compress: ByteArray? = obj.toByteArray();

    if(compress == null)
    {
      println(String.format("The Encrypted Compressed Text length is: %d", -1));

      return ByteArray(0)

    } else {
      println(String.format("The Encrypted Compressed Text length is: %d", compress.size));

    }

    return compress;
  }

  @Throws(IOException::class)
  fun decompress(compressed: ByteArray?): String {
    val outStr: StringBuilder = StringBuilder();
    if ((compressed == null) || (compressed.size == 0)) {
      return "";
    }
    val gis: GZIPInputStream = GZIPInputStream( ByteArrayInputStream(compressed));
//    val bufferedReader: BufferedReader = BufferedReader( InputStreamReader(gis, "UTF-8"));
    var line: String = gis.bufferedReader().use(BufferedReader::readText)
    println(String.format("The Decompressed Text length is: %d", line.length));
    return line
  }

}
