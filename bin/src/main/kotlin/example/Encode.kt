package example

import de.mkammerer.argon2.Argon2;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;

class Encode {

  @Throws(IOException::class)
  fun compress(hash: String): ByteArray {
    val compress: ByteArray? = GZIPCompression.compress(hash);

    if(compress == null)
    {
      println(String.format("The Encrypted Compressed Text length is: %d", -1));

      return ByteArray(0)
    }

    return compress
  }

  @Throws(IOException::class)
  fun decompress(hash: ByteArray): String {
    return GZIPCompression.decompress(hash);
  }

  @Throws(Exception::class)
  fun encrypt(rsa: Encryption, hash: String): ByteArray {

    val cipherText: ByteArray = rsa.do_RSAEncryption(hash);

    val newHash: String = DatatypeConverter.printHexBinary(cipherText);

    return compress(newHash);
  }

  @Throws(Exception::class)
  fun decrypt(rsa: Encryption, hash: ByteArray): String {

    val decompress: String = decompress(hash);

    return rsa.do_RSADecryption(DatatypeConverter.parseHexBinary(decompress));
  }

  fun hashpw(rsa: Encryption, argon2: Argon2, pass: String): String {
    val passwordChars = pass.toCharArray();
    val hash = argon2.hash(22, 65536, 1, passwordChars);
    argon2.wipeArray(passwordChars);

    try {

      val newHash = encrypt(rsa, hash);

      return DatatypeConverter.printHexBinary(newHash);

    } catch (e: Exception) {}

    return String()
  }

  fun verify(rsa: Encryption, argon2: Argon2, pass: String, hash: String): Boolean {

    val hashArray = DatatypeConverter.parseHexBinary(hash);

    try{

      val newHash = decrypt(rsa, hashArray);

      return argon2.verify(newHash, pass.toCharArray());

    } catch (e: Exception) {

      println("Encode verify error");

      e.printStackTrace();

      return false;
    }
  }
}
