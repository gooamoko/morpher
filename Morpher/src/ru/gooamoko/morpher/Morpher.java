package ru.gooamoko.morpher;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Класс для склонения по падежам посредством интернет-сервиса
 *
 * @author Воронин Леонид
 */
public class Morpher {

  final static String LINK = "http://morpher.ru/WebService.asmx/GetXml?s=";
  final static String RP = "Р";
  final static String DP = "Д";
  final static String VP = "В";
  final static String TP = "Т";
  final static String PP = "П";
  private Document doc = null;
  private String serviceLink = LINK;

  /**
   * Конструктор класса с обязательным параметром
   *
   * @param word слово или предложение в именительном падеже
   * @throws IOException
   * @throws ParserConfigurationException
   * @throws SAXException
   */
  public Morpher(String word) throws MorpherException {
    initMorpher(word, serviceLink);
  }

  /**
   * Конструктор класса с параметрами
   *
   * @param word слово или выражение для склонения
   * @param serviceLink ссылка на сервис онлайн-склонения морфер.ру
   */
  public Morpher(String word, String link) throws MorpherException {
    initMorpher(word, link);
  }

  private void initMorpher(final String word, final String link) throws MorpherException {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      // TODO Может стоит сделать замену пробелов спецсимволами
      doc = builder.parse(link + word);
    } catch (IOException | ParserConfigurationException | SAXException e) {
      throw new MorpherException(e.getMessage());
    }
  }

  /**
   * Возвращает выражение в требуемом падеже
   *
   * @param p Падеж в виде одного символа (Р - для родительного и т.п.)
   * @return выражение в требуемом падеже
   */
  public String getMorph(String p) throws MorpherException {
    if (null == doc) {
      throw new MorpherException("XML документ не был нормально иннициализирован!");
    }
    Element root = doc.getDocumentElement();
    NodeList nodes = root.getChildNodes();
    for (int x = 0; x < nodes.getLength(); x++) {
      Node item = nodes.item(x);
      if (item instanceof Element) {
        Element el = ((Element) item);
        if (el.getTagName().equals(p)) {
          return ((Text) el.getFirstChild()).getData().trim();
        }
      }
    }
    throw new MorpherException("XML документ не соответствует предполагаемому формату!");
  }

  /**
   * Возвращает слово или выражение в родительном падеже
   *
   * @return слово или выражение в родительном падеже
   */
  public String getMorphRP() throws MorpherException {
    return getMorph(RP);
  }

  /**
   * Возвращает слово или выражение в дательном падеже
   *
   * @return слово или выражение в дательном падеже
   */
  public String getMorphDP() throws MorpherException {
    return getMorph(DP);
  }

  /**
   * Возвращает слово или выражение в винительном падеже
   *
   * @return слово или выражение в винительном падеже
   */
  public String getMorphVP() throws MorpherException {
    return getMorph(VP);
  }

  /**
   * Возвращает слово или выражение в творительном падеже
   *
   * @return слово или выражение в творительном падеже
   */
  public String getMorphTP() throws MorpherException {
    return getMorph(TP);
  }

  /**
   * Возвращает слово или выражение в предложном падеже
   *
   * @return слово или выражение в предложном падеже
   */
  public String getMorphPP() throws MorpherException {
    return getMorph(PP);
  }
}
