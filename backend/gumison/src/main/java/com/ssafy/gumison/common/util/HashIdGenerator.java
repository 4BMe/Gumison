package com.ssafy.gumison.common.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.MessageDigest;


/**
 * 해시 ID 생성기.
 *
 * @author cherrytomato1
 * @version 1.0 해시 ID 생성
 */
public class HashIdGenerator implements IdentifierGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {

    final String PREFIX = "G";
    final int ID_LENGTH = 16;
    String baseString = String.valueOf(System.currentTimeMillis());

    try {
      //SHA-256 해싱 함수 인스턴스
      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      //해싱 결과를 byte[]애 2자리 15진수로 저장
      byte[] hash = digest.digest(baseString.getBytes(StandardCharsets.UTF_8));

      StringBuilder hexStringBuilder = new StringBuilder(PREFIX);

      for (byte hex : hash) {
        //byte to integer 형 변환시 1로 빈공간이 채워지는 것을 제거하기 위해 & 0xff
        String hexString = Integer.toHexString(0xff & hex);
        //한 자리 해시값에 0 붙이기
        if (hexString.length() == 1) {
          hexStringBuilder.append('0');
        }
        hexStringBuilder.append(hexString);
      }

      return hexStringBuilder.substring(0, ID_LENGTH);

    } catch (NoSuchAlgorithmException ex) {
      throw new RuntimeException(
          String.format("exception while id generate : %s", ex.getMessage()));
    }
  }
}