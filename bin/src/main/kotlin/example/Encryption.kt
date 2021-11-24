package example;

// Java program to perform the
// encryption and decryption
// using asymmetric key
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

class Encryption{
  val RSA: String = "RSA"
  lateinit var keypair: KeyPair

  constructor() {
    this.keypair = generateRSAKkeyPair()
  }

  @Throws(Exception::class)
  fun generateRSAKkeyPair(): KeyPair
  {
      val secureRandom: SecureRandom = SecureRandom();
      val keyPairGenerator: KeyPairGenerator = KeyPairGenerator.getInstance(RSA);

      keyPairGenerator.initialize(2048, secureRandom);

      return keyPairGenerator.generateKeyPair();
  }

  // Encryption function which converts
  // the plainText into a cipherText
  // using private Key.
  @Throws(Exception::class)
  fun do_RSAEncryption( plainText: String): ByteArray
  {
      val cipher: Cipher = Cipher.getInstance(RSA);

      cipher.init(Cipher.ENCRYPT_MODE, this.keypair.private );

      val encrypted: ByteArray = cipher.doFinal(plainText.toByteArray());

      print("The Encrypted Text is: ");

      val rsaText: String = DatatypeConverter.printHexBinary(encrypted);

      println(rsaText);

      println(String.format("The Encrypted Text length is: %d", rsaText.length));

      return encrypted
  }

  // Decryption function which converts
  // the ciphertext back to the
  // original plaintext.
  @Throws(Exception::class)
  fun do_RSADecryption(cipherText: ByteArray): String
  {
      val cipher: Cipher = Cipher.getInstance(RSA);

      cipher.init(Cipher.DECRYPT_MODE, this.keypair.public);

      val result: ByteArray = cipher.doFinal(cipherText);

      val decrypted: String = String(result);
      
      return decrypted;
  }
}
