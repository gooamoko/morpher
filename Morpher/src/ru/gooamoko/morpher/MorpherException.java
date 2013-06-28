package ru.gooamoko.morpher;

/**
 * Класс, реализующий исключения при работе с сервисом склонения
 *
 * @author Воронин Леонид
 */
public class MorpherException extends Exception {

  public MorpherException(String message) {
    super("Ошибка при склонении выражения. " + message);
  }
}
